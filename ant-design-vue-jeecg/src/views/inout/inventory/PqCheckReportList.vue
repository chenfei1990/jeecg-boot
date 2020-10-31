<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="报告编号">
              <a-input placeholder="请输入报告编号" v-model="queryParam.reportId"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="10" :lg="11" :md="12" :sm="24">
            <a-form-item label="盘点日期">
              <j-date placeholder="请选择开始日期" class="query-group-cust" v-model="queryParam.checkDate_begin"></j-date>
              <span class="query-group-split-cust"></span>
              <j-date placeholder="请选择结束日期" class="query-group-cust" v-model="queryParam.checkDate_end"></j-date>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
              <a @click="handleToggleSearch" style="margin-left: 8px">
                {{ toggleSearchStatus ? '收起' : '展开' }}
                <a-icon :type="toggleSearchStatus ? 'up' : 'down'"/>
              </a>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->
    
    <!-- 操作按钮区域 -->
    <!-- <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('盘点报告')">导出</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import">导入</a-button>
      </a-upload>
    </div> -->
    <div class="table-operator">
      <a-button type="primary" icon="check" @click="handleFinish" 
      v-bind:disabled=" !(selectedRowKeys.length > 0 && selectionRows[0].status == 0)">录入完成</a-button>

      <a-button type="primary" icon="audit" @click="handleAudit"
      v-bind:disabled=" !(selectedRowKeys.length > 0 && selectionRows[0].status == 1)">审核盘点表</a-button>

    </div>

    <!-- table区域-begin -->
    <div>
      <!-- <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择 <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>项
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
      </div> -->

      <a-table
        ref="table"
        size="middle"
        bordered
        rowKey="id"
        class="j-table-force-nowrap"
        :scroll="{x:true}"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange, type:'radio'}"
        :customRow="clickThenSelect"
        @change="handleTableChange">

        <template slot="htmlSlot" slot-scope="text">
          <div v-html="text"></div>
        </template>
        <template slot="imgSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无图片</span>
          <img v-else :src="getImgView(text)" height="25px" alt="" style="max-width:80px;font-size: 12px;font-style: italic;"/>
        </template>
        <template slot="fileSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无文件</span>
          <a-button
            v-else
            :ghost="true"
            type="primary"
            icon="download"
            size="small"
            @click="downloadFile(text)">
            下载
          </a-button>
        </template>

        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">编辑</a>

          <a-divider type="vertical" />
          <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                  <a>删除</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>

      </a-table>
    </div>

    <a-tabs defaultActiveKey="1">
      <a-tab-pane tab="盘点商品录入" key="1" >
        <PqCheckReportSpList :mainId="selectedMainId" 
        :eftDisabled="eftEdit"/>
      </a-tab-pane>
    </a-tabs>

    <pqCheckReport-modal ref="modalForm" @ok="modalFormOk"></pqCheckReport-modal>
  </a-card>
</template>

<script>

  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import PqCheckReportModal from './modules/PqCheckReportModal'
  import { getAction } from '@/api/manage'
  import PqCheckReportSpList from './PqCheckReportSpList'
  import JDate from '@/components/jeecg/JDate.vue'
  import {initDictOptions,filterMultiDictText} from '@/components/dict/JDictSelectUtil'
  import '@/assets/less/TableExpand.less'

  export default {
    name: "PqCheckReportList",
    mixins:[JeecgListMixin],
    components: {
      JDate,
      PqCheckReportSpList,
      PqCheckReportModal
    },
    data () {
      return {
        // description: '盘点报告管理页面',
        // 表头
        columns: [
          {
            title:'报告编号',
            align:"center",
            dataIndex: 'reportId'
          },
          {
            title:'盘点日期',
            align:"center",
            dataIndex: 'checkDate',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },
          {
            title:'库存地点',
            align:"center",
            dataIndex: 'kcdd_dictText',
          },
          {
            title:'损益方式',
            align:"center",
            dataIndex: 'lossesType_dictText',
          },
          {
            title:'录入完成时间',
            align:"center",
            dataIndex: 'finishTime'
          },
          {
            title:'状态',
            align:"center",
            dataIndex: 'status_dictText',
          },
          {
            title:'账面金额',
            align:"center",
            dataIndex: 'bookAmo'
          },
          {
            title:'盘点金额',
            align:"center",
            dataIndex: 'eftAmo'
          }
          // ,{
          //   title: '操作',
          //   dataIndex: 'action',
          //   align:"center",
          //   fixed:"right",
          //   width:147,
          //   scopedSlots: { customRender: 'action' },
          // }
        ],
        url: {
          list: "/inventory/pqCheckReport/list",
          delete: "/inventory/pqCheckReport/delete",
          deleteBatch: "/inventory/pqCheckReport/deleteBatch",
          exportXlsUrl: "/inventory/pqCheckReport/exportXls",
          importExcelUrl: "inventory/pqCheckReport/importExcel",
          finishUrl: "inventory/pqCheckReport/finishWrite",
          auditUrl: "inventory/pqCheckReport/audit",
        },
        dictOptions:{
         kcdd:[],
         lossesType:[],
         status:[],
        },
        /* 分页参数 */
        ipagination:{
          current: 1,
          pageSize: 5,
          pageSizeOptions: ['5', '10', '50'],
          showTotal: (total, range) => {
            return range[0] + "-" + range[1] + " 共" + total + "条"
          },
          showQuickJumper: true,
          showSizeChanger: true,
          total: 0
        },
        selectedMainId:'',
        eftEdit: 0

      }
    },
    created() {
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      }
    },
    methods: {
      handleFinish(){
        if(this.selectedRowKeys.length == 0){
          this.$message.warning("请选择盘点报告");
        }
        else{
          
          if(!this.url.finishUrl){
            this.$message.error("请设置url.finish属性!")
            return
          }
          getAction(this.url.finishUrl, {id: this.selectionRows[0].id}).then((res) => {
          if (res.success) {
            this.$message.success(res.message);
            this.loadData();
          } else {
            this.$message.warning(res.message);
          }
          });

        }
      },

      handleAudit(){
        if(this.selectedRowKeys.length == 0){
          this.$message.warning("请选择盘点报告");
        }
        else{
          
          if(!this.url.auditUrl){
            this.$message.error("请设置url.audit属性!")
            return
          }
          getAction(this.url.auditUrl, {id: this.selectionRows[0].id}).then((res) => {
          if (res.success) {
            this.$message.success(res.result);
            this.loadData();
          } else {
            this.$message.warning(res.message);
          }
          });

        }
      },

      initDictConfig(){
        initDictOptions('pq_stroage_location,name,id').then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'kcdd', res.result)
          }
        })
        initDictOptions('pq_loss_type').then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'lossesType', res.result)
          }
        })
        initDictOptions('pq_check_report_status').then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'status', res.result)
          }
        })
      },
      clickThenSelect(record) {
        return {
          on: {
            click: () => {
              this.onSelectChange(record.id.split(","), [record]);
            }
          }
        }
      },
      onClearSelected() {
        this.selectedRowKeys = [];
        this.selectionRows = [];
        this.selectedMainId='';
    
        this.eftEdit = 0;
      },
      onSelectChange(selectedRowKeys, selectionRows) {
        this.selectedMainId=selectedRowKeys[0];
        this.eftEdit = selectionRows[0].status;
        this.selectedRowKeys = selectedRowKeys;
        this.selectionRows = selectionRows;
      },
      loadData(arg) {
        console.log(arg)
        if(!this.url.list){
          this.$message.error("请设置url.list属性!")
          return
        }
        //加载数据 若传入参数1则加载第一页的内容
        if (arg === 1) {
          this.ipagination.current = 1;
        }
        this.onClearSelected()
        var params = this.getQueryParams();//查询条件
        this.loading = true;
        getAction(this.url.list, params).then((res) => {
          if (res.success) {
            this.dataSource = res.result.records;
            this.ipagination.total = res.result.total;
          }
          if(res.code===510){
            this.$message.warning(res.message)
          }
          this.loading = false;
        })
      }

    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>