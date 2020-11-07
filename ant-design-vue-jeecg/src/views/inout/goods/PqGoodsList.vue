<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="商品名称">
              <a-input placeholder="请输入商品名称" v-model="queryParam.name"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="分类">
              <!-- <a-input placeholder="请输入分类" v-model="queryParam.goodsTypeId"></a-input> -->

              <j-tree-select
                placeholder="请选择分类"
                v-model="queryParam.goodsTypeId"
                dict="pq_goods_type,name,id"
                pidField="pid"
                pidValue="0"
              />

            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="商品状态">
                <j-dict-select-tag placeholder="请选择商品状态" v-model="queryParam.status" dictCode="pq_goods_stats"/>
              </a-form-item>
          </a-col>

          <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="商品条码">
                <a-input placeholder="请输入商品条码" v-model="queryParam.barcode"></a-input>
              </a-form-item>
          </a-col>
          <!-- <template v-if="toggleSearchStatus">
            
          </template> -->
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
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-button @click="handleAudit" type="primary" icon="check">审核</a-button>
      <a-button @click="handleTaotai" type="primary" icon="close">淘汰</a-button>
      <!-- <a-button type="primary" icon="download" @click="handleExportXls('商品信息')">导出</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import">导入</a-button>
      </a-upload> -->
      <!-- <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>删除</a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>
      </a-dropdown> -->
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择 <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>项
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
      </div>

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
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
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
        <template slot="pcaSlot" slot-scope="text">
          <div>{{ getPcaText(text) }}</div>
        </template>

        <span slot="action" slot-scope="text, record">
          <a @click="handleEditExt(record)">编辑</a>

          <a-divider type="vertical" />
          <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a @click="handleDetail(record)">详情</a>
              </a-menu-item>
              <a-menu-item>
                <a-popconfirm title="确定删除吗?" @confirm="() => handleDeleteExt(record.id,record.status)">
                  <a>删除</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>

      </a-table>
    </div>

    <pq-goods-modal ref="modalForm" @ok="modalFormOk"/>
  </a-card>
</template>

<script>

  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import PqGoodsModal from './modules/PqGoodsModal'
  import { deleteAction,getAction } from '@/api/manage'
  import {filterMultiDictText} from '@/components/dict/JDictSelectUtil'
  import Area from '@/components/_util/Area'
  import '@/assets/less/TableExpand.less'
  import JTreeSelect from '@/components/jeecg/JTreeSelect'

  export default {
    name: "PqGoodsList",
    mixins:[JeecgListMixin],
    components: {
      PqGoodsModal,
      JTreeSelect
    },
    data () {
      return {
        description: '商品信息管理页面',
        // 表头
        columns: [
          {
            title: '#',
            dataIndex: '',
            key:'rowIndex',
            width:60,
            align:"center",
            customRender:function (t,r,index) {
              return parseInt(index)+1;
            }
          },
          {
            title:'商品名称',
            align:"center",
            dataIndex: 'name'
          },
          // {
          //   title:'简称',
          //   align:"center",
          //   dataIndex: 'jc'
          // },
          {
            title:'分类',
            align:"center",
            dataIndex: 'goodsTypeId_dictText'
          },
          {
            title:'商品状态',
            align:"center",
            dataIndex: 'status_dictText'
          },
          {
            title:'进项税率（%）',
            align:"center",
            dataIndex: 'jxsl_dictText'
          },
          {
            title:'销项税率（%）',
            align:"center",
            dataIndex: 'xssl_dictText'
          },
          {
            title:'关税税率（%）',
            align:"center",
            dataIndex: 'gssl'
          },
          {
            title:'商品条码',
            align:"center",
            dataIndex: 'barcode'
          },
          {
            title:'多规格',
            align:"center",
            dataIndex: 'sepcification_dictText'
          },
          {
            title:'属性分类',
            align:"center",
            dataIndex: 'productAttributeCategoryId_dictText'
          },
       
          {
            title:'单位',
            align:"center",
            dataIndex: 'unit'
          },
          {
            title:'税收分类',
            align:"center",
            dataIndex: 'taxId'
          },
          {
            title:'产地',
            align:"center",
            dataIndex: 'place',
            scopedSlots: {customRender: 'pcaSlot'}
          },
          {
            title:'备注',
            align:"center",
            dataIndex: 'remark'
          },
          {
            title:'图片',
            align:"center",
            dataIndex: 'pic',
            scopedSlots: {customRender: 'imgSlot'}
          },
          
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            fixed:"right",
            width:147,
            scopedSlots: { customRender: 'action' },
          }
        ],
        url: {
          list: "/goods/pqGoods/list",
          delete: "/goods/pqGoods/delete",
          deleteBatch: "/goods/pqGoods/deleteBatch",
          exportXlsUrl: "/goods/pqGoods/exportXls",
          importExcelUrl: "goods/pqGoods/importExcel",
          auditUrl: "goods/pqGoods/audit",
        },
        dictOptions:{},
      }
    },
    created() {
      this.pcaData = new Area()
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      }
    },
    methods: {
      getPcaText(code){
        return this.pcaData.getText(code);
      },
      initDictConfig(){
      },
      handleAudit(){
        if(this.selectedRowKeys.length == 0){
          this.$message.warning("请选择商品");
        }
        else{
          
          if(!this.url.auditUrl){
            this.$message.error("请设置url.audit属性!")
            return
          }

          if( this.selectionRows[0].status == 1) {
            this.$message.error("该商品已审核，请勿重复审核!")
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


     handleTaotai(){
      if(this.selectedRowKeys.length == 0){
        this.$message.warning("请选择商品");
        return;
      }
       this.$message.warning("该功能暂未开放！");
        // if(this.selectedRowKeys.length == 0){
        //   this.$message.warning("请选择商品");
        // }
        // else{
          
        //   if(!this.url.auditUrl){
        //     this.$message.error("请设置url.audit属性!")
        //     return
        //   }

        //   if( this.selectionRows[0].status == 1) {
        //     this.$message.error("该商品已审核，请勿重复审核!")
        //     return
        //   }

        //   getAction(this.url.auditUrl, {id: this.selectionRows[0].id}).then((res) => {
        //   if (res.success) {
        //     this.$message.success(res.result);
        //     this.loadData();
        //   } else {
        //     this.$message.warning(res.message);
        //   }
        //   });

        // }
    },


    

    handleDeleteExt: function (id,status) {
      if(!this.url.delete){
        this.$message.error("请设置url.delete属性!")
        return
      }
      if(status == 1) {
        this.$message.error("该商品已审核，无法删除!")
        return
      }
      var that = this;
      deleteAction(that.url.delete, {id: id}).then((res) => {
        if (res.success) {
          that.$message.success(res.message);
          that.loadData();
        } else {
          that.$message.warning(res.message);
        }
      });
    },

    handleEditExt: function (record) {
      if(record.status == 1) {
        this.$message.error("该商品已审核，无法编辑!")
        return
      }
      this.$refs.modalForm.edit(record);
      this.$refs.modalForm.title = "编辑";
      this.$refs.modalForm.disableSubmit = false;
    },
       
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>