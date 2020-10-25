<template>
  <a-card :bordered="false" :class="'cust-erp-sub-tab'">
    <!-- 操作按钮区域 -->
    <!-- <div class="table-operator" v-if="mainId">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('盘点商品录入')">导出</a-button>
      <a-upload
        name="file"
        :showUploadList="false"
        :multiple="false"
        :headers="tokenHeader"
        :action="importExcelUrl"
        @change="handleImportExcel">
          <a-button type="primary" icon="import">导入</a-button>
      </a-upload>
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>删除</a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>
      </a-dropdown>
    </div> -->

    <!-- table区域-begin -->
    <div>
      <!-- <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择 <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>项
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
      </div> -->

      <!-- <a-table
        ref="table"
        size="middle"
        bordered
        rowKey="id"
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

        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">编辑</a>
          <a-divider type="vertical" />
          <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
            <a>删除</a>
          </a-popconfirm>
        </span>

      </a-table> -->

<!-- cf 新加 -->
      <j-vxe-table
      row-number
      keep-source
      async-remove
      :height="525"
      :columns="columns"
      :pagination="pagination"
      :dataSource ="dataSource"
      :loading="loading"
      @edit-closed="handleEditClosed"
      @pageChange="handleSpPageChange"
      >
        
      </j-vxe-table>

    </div>

    <pqCheckReportSp-modal ref="modalForm" @ok="modalFormOk" :mainId="mainId"></pqCheckReportSp-modal>
  </a-card>

  
</template>

<script>

  import { getAction, postAction, putAction } from '@api/manage'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import PqCheckReportSpModal from './modules/PqCheckReportSpModal'
  import { JVXETypes } from '@/components/jeecg/JVxeTable'


  export default {
    name: "PqCheckReportSpList",
    mixins:[JeecgListMixin],
    components: { PqCheckReportSpModal },
    props:{
      mainId:{
        type:String,
        default:'',
        required:false
      },
      eftDisabled:{
        type: Number,
        default: 0,
        required:false
      }
    },
    watch:{
      mainId:{
        immediate: true,
        handler(val) {
          if(!this.mainId){
            this.clearList()
          }else{
            this.queryParam['checkReportId'] = val
            this.loadSpData();
          }
        }
      },
      eftDisabled: {
        immediate: true,
        handler(val) {
          // console.log(val)
        }
      }
    },
    data () {
      return {
        description: '盘点报告管理页面',
        disableMixinCreated:true,
        // 表头
        loading: false,
        columns: [
          {
            title: '库存地点',
            type: JVXETypes.normal,
            key: 'kcdd_dictText'
          },

          {
            title: '商品名称',
            type: JVXETypes.normal,
            key: 'skuName'
          },
          {
            title:'规格值',
            type: JVXETypes.normal,
            key: 'specsVal'
          },
           {
            title:'账面数量',
            type: JVXETypes.normal,
            key: 'bookNum',
            statistics: ['sum']
          },
          {
            title:'盘点数量',
            type: JVXETypes.inputNumber,
            statistics: ['sum'],
            key: 'eftNum'
          },
          
          // {
          //   title: '#',
          //   dataIndex: '',
          //   key:'rowIndex',
          //   width:60,
          //   align:"center",
          //   customRender:function (t,r,index) {
          //     return parseInt(index)+1;
          //   }
          // },
          
          // {
          //   title:'库存地点',
          //   align:"center",
          //   dataIndex: 'kcdd_dictText',
          // },
          // {
          //   title:'商品名称',
          //   align:"center",
          //   dataIndex: 'skuName'
          // },
          // {
          //   title:'规格值',
          //   align:"center",
          //   dataIndex: 'specsVal'
          // },
          // {
          //   title:'账面数量',
          //   align:"center",
          //   dataIndex: 'bookNum'
          // },
          // {
          //   title:'盘点数量',
          //   align:"center",
          //   dataIndex: 'eftNum'
          // },
          // {
          //   title: '操作',
          //   dataIndex: 'action',
          //   align:"center",
          //   fixed:"right",
          //   width:147,
          //   scopedSlots: { customRender: 'action' },
          // }
        ],
        url: {
          list: "/inventory/pqCheckReport/listPqCheckReportSpByMainId",
          delete: "/inventory/pqCheckReport/deletePqCheckReportSp",
          deleteBatch: "/inventory/pqCheckReport/deleteBatchPqCheckReportSp",
          exportXlsUrl: "/inventory/pqCheckReport/exportPqCheckReportSp",
          importUrl: "/inventory/pqCheckReport/importPqCheckReportSp",
          edit: "/inventory/pqCheckReport/editPqCheckReportSp",
        },
        dictOptions:{
         kcdd:[],
         lossesType:[],
         status:[],
        },
        // 分页器参数
        pagination: {
          // 当前页码
          current: 1,
          // 每页的条数
          pageSize: 10,
          // 可切换的条数
          pageSizeOptions: ['10', '50', '100', '500', '5000'],
          // 数据总数（目前并不知道真实的总数，所以先填写0，在后台查出来后再赋值）
          total: 0,
        },

      }
    },
    computed: {
      importExcelUrl(){
        return `${window._CONFIG['domianURL']}/${this.url.importUrl}/${this.mainId}`;
      }
    },
    methods: {
// cf 分页
      loadSpData() {
        console.log('aaa');
        if(!this.url.list){
          this.$message.error("请设置url.list属性!")
          return
        }

        let formData = {
          pageNo: this.pagination.current,
          pageSize: this.pagination.pageSize
        }
        this.loading = true

        getAction(this.url.list, formData).then(res => {
          if (res.success) {
            // 后台查询回来的 total，数据总数量
            this.pagination.total = res.result.total
            // 将查询的数据赋值给 dataSource
            this.dataSource = res.result.records
            // 重置选择
            this.selectedRows = []
          } 
          if(res.code===510){
            this.$message.warning(res.message)
          }
          this.loading = false;
        }).finally(() => {
          // 这里是无论成功或失败都会执行的方法，在这里关闭loading
          this.loading = false
        })

      //加载数据 若传入参数1则加载第一页的内容
      // if (arg === 1) {
      //   this.ipagination.current = 1;
      // }
      // var params = this.getQueryParams();//查询条件
      // this.loading = true;
      // getAction(this.url.list, params).then((res) => {
      //   if (res.success) {
      //     this.dataSource = res.result.records;
      //     this.ipagination.total = res.result.total;
      //   }
      //   if(res.code===510){
      //     this.$message.warning(res.message)
      //   }
      //   this.loading = false;
      // })
    },

        handleSpPageChange(event) {
          // 重新赋值
          this.pagination.current = event.current
          this.pagination.pageSize = event.pageSize
          // 查询数据
          this.loadSpData()
        },

      clearList(){
        this.dataSource=[]
        // this.selectedRowKeys=[]
         this.ipagination.current = 1
      },
        // 单元格编辑完成之后触发的事件
      handleEditClosed(event) {
        let {$table, row, column} = event

        if(this.eftDisabled != 0) 
        {
          return;
        }

        let field = column.property
        let cellValue = row[field]
        // 判断单元格值是否被修改
        if ($table.isUpdateByRow(row, field)) {
          // 校验当前行
          $table.validate(row).then((errMap) => {
            // 校验通过
            if (!errMap) {
              let hideLoading = this.$message.loading(`正在保存"${column.title}"`, 0)
              console.log('即时保存数据：', row)
              putAction(this.url.edit, row).then(res => {
                if (res.success) {
                  this.$message.success(`"${column.title}"保存成功！`)
                  // 局部更新单元格为已保存状态
                  $table.reloadRow(row, null, field)
                } else {
                  this.$message.warn(`"${column.title}"保存失败：` + res.message)
                }
              }).finally(() => {
                hideLoading()
              })
            }
          })
        }
      },

    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>
