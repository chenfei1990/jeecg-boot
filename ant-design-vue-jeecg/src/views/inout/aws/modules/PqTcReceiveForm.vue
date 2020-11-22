<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <!-- 主表单区域 -->
      <a-form :form="form" slot="detail">
        <a-row>
          <a-col :span="24" >
            <a-form-item label="到货日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择到货日期" v-decorator="['receiveTime', validatorRules.receiveTime]" :trigger-change="true" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="24" >
            <a-form-item label="损溢金额" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number disabled v-decorator="['lossAmo', validatorRules.lossAmo]" placeholder="请输入损溢金额" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="24" >
            <a-form-item label="是否确认" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number disabled v-decorator="['status']" placeholder="请输入是否确认" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="24" >
            <a-form-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['bz']" placeholder="请输入备注"></a-input>
            </a-form-item>
          </a-col>
        </a-row>
      </a-form>
    </j-form-container>
      <!-- 子表单区域 -->
    <a-tabs v-model="activeKey" @change="handleChangeTabs">
      
      <a-tab-pane tab="到货商品明细" :key="refKeys[0]" :forceRender="true">
         <div class="action-button">
            <a-button icon="plus" @click="openTouChengRp" type="primary">选择头程商品</a-button>
            <span class="gap"></span>
            <template v-if="selectedTCRowIds.length>0">
            <a-popconfirm
              :title="`确定要删除这 ${selectedTCRowIds.length} 项吗?`"
              @confirm="handleConfirmSkuDelete">
              <a-button type="primary" icon="minus" :disabled="disabled">删除</a-button>
              <span class="gap"></span>
            </a-popconfirm>
          </template>
          <j-popup-onl-report ref="TouChengRp" :code="'tc_sku_rp'" :multi="true"  @ok="addSkuRow"/>
         </div>
        <j-editable-table
          :ref="refKeys[0]"
          :loading="pqTcReceiveSkuTable.loading"
          :columns="pqTcReceiveSkuTable.columns"
          :dataSource="pqTcReceiveSkuTable.dataSource"
          :maxHeight="300"
          :disabled="formDisabled"
          :rowNumber="true"
          :rowSelection="true"
           @added="skuRowAdded" 
           @selectRowChange="handleSelectRowChange"
          />
      </a-tab-pane>
    </a-tabs>
  </a-spin>
</template>

<script>

  import pick from 'lodash.pick'
  import { getAction } from '@/api/manage'
  import { FormTypes,getRefPromise } from '@/utils/JEditableTableUtil'
  import { JEditableTableMixin } from '@/mixins/JEditableTableMixin'
  import { validateDuplicateValue } from '@/utils/util'
  import JFormContainer from '@/components/jeecg/JFormContainer'
  import JDate from '@/components/jeecg/JDate'  
  import JPopupOnlReport from '@/components/jeecg/modal/JPopupOnlReport'


  export default {
    name: 'PqTcReceiveForm',
    mixins: [JEditableTableMixin],
    components: {
      JFormContainer,
      JDate,
      JPopupOnlReport,//添加报表组件
    },
    data() {
      return {
        labelCol: {
          xs: { span: 24 },
          sm: { span: 6 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
        labelCol2: {
          xs: { span: 24 },
          sm: { span: 3 },
        },
        wrapperCol2: {
          xs: { span: 24 },
          sm: { span: 20 },
        },
        // 新增时子表默认添加几行空数据
        addDefaultRowNum: 0,
        selectedTCRowIds: [],
        validatorRules: {
          receiveTime: {
            rules: [
              { required: true, message: '请输入到货日期!'},
            ]
          },
          lossAmo: {
            rules: [
              { required: false},
              { pattern: /^(([1-9][0-9]*)|([0]\.\d{0,2}|[1-9][0-9]*\.\d{0,2}))$/, message: '请输入正确的金额!'},
            ]
          },
        },
        refKeys: ['pqTcReceiveSku', ],
        tableKeys:['pqTcReceiveSku', ],
        activeKey: 'pqTcReceiveSku',
        // 到货商品明细
        pqTcReceiveSkuTable: {
          loading: false,
          dataSource: [],
          columns: [
            {
              title: '头程箱号',
              key: 'mpackNo',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
              disabled:true,
            },
            {
              title: '打包箱号',
              key: 'packNo',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
              disabled:true,
            },
            {
              title: 'SKU名称',
              key: 'skuName',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
              disabled:true,
            },
            
            {
              title: 'SKU编码',
              key: 'skuCode',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
              disabled:true,
            },
            {
              title: '批次号',
              key: 'orderNo',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
              disabled:true,
            },
            {
              title: '出库数量',
              key: 'packNum',
              type: FormTypes.inputNumber,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
              validateRules: [{ pattern: "n", message: "${title}格式不正确" }],
              disabled:true,
            },
            {
              title: '实际到货数',
              key: 'receiveNum',
              type: FormTypes.inputNumber,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
              validateRules: [{ required: true, message: '${title}不能为空' }],
            },
            {
              title: '适用站点',
              key: 'sites',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
              disabled:true,
            },
            {
              title: '适用账号',
              key: 'account',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
              disabled:true,
            },
            {
              title: '平台SKU',
              key: 'platSku',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
              disabled:true,
            },
            {
              title: '平台asin',
              key: 'platAsin',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
              disabled:true,
            },
            {
              title: '平台fnsku',
              key: 'platFnsku',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
              disabled:true,
            },
            {
              title: '备注',
              key: 'remark',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },

            {
              title: 'out_stock_id',
              key: 'outStockId',
              type:"hidden"
            },

            {
              title: 'mpack_no',
              key: 'mpack_no',
              type:"hidden"
            },

            {
              title: 'pack_no',
              key: 'pack_no',
              type:"hidden"
            },

            {
              title: 'sku_id',
              key: 'skuId',
              type:"hidden"
            },

            {
              title: 'sku_name',
              key: 'sku_name',
              type:"hidden"
            },

            {
              title: 'sku_code',
              key: 'sku_code',
              type:"hidden"
            },

            {
              title: 'order_no',
              key: 'order_no',
              type:"hidden"
            },

            {
              title: 'pack_num',
              key: 'pack_num',
              type:"hidden"
            },

            {
              title: 'plat_sku',
              key: 'plat_sku',
              type:"hidden"
            },

            {
              title: 'plat_asin',
              key: 'plat_asin',
              type:"hidden"
            },

            {
              title: 'plat_fnsku',
              key: 'plat_fnsku',
              type:"hidden"
            },

            {
              title: 'storage',
              key: 'storage',
              type:"hidden"
            },
          ]
        },
        url: {
          add: "/aws/pqTcReceive/add",
          edit: "/aws/pqTcReceive/edit",
          queryById: "/aws/pqTcReceive/queryById",
          pqTcReceiveSku: {
            list: '/aws/pqTcReceive/queryPqTcReceiveSkuByMainId'
          },
        }
      }
    },
    props: {
      //流程表单data
      formData: {
        type: Object,
        default: ()=>{},
        required: false
      },
      //表单模式：false流程表单 true普通表单
      formBpm: {
        type: Boolean,
        default: false,
        required: false
      },
      //表单禁用
      disabled: {
        type: Boolean,
        default: false,
        required: false
      }
    },
    computed: {
      formDisabled(){
        if(this.formBpm===true){
          if(this.formData.disabled===false){
            return false
          }
          return true
        }
        return this.disabled
      },
      showFlowSubmitButton(){
        if(this.formBpm===true){
          if(this.formData.disabled===false){
            return true
          }
        }
        return false
      }
    },
    created () {
      //如果是流程中表单，则需要加载流程表单data
      this.showFlowData();
    },
    methods: {
    handleSelectRowChange(selectedRowIds) {
        this.selectedTCRowIds = selectedRowIds
    },
    handleConfirmSkuDelete(){
      this.$refs.pqTcReceiveSku.handleConfirmDelete()
    },
    addSkuRow(rows) {
      //将返回的数据添加到sku_tab中
      //添加行数
      if(rows.length >0 )
      {
   
      // union key
      let oldRows = this.$refs.pqTcReceiveSku.getValuesSync().values;
      for (let j = 0; j < rows.length; j++) {
        const r = rows[j];
        let canAdd = true;
        for (let x = 0; x < oldRows.length; x++) {
          const element = oldRows[x];   
          if(r.out_stock_id == element.outStockId){
            canAdd = false;
            break;
          }
        } 
        if(canAdd)
        {
          this.pqTcReceiveSkuTable.oneSkuRow = r ;
          this.$refs.pqTcReceiveSku.add(1,true);
        }
      
      }
      
      }
    },

     skuRowAdded(r)
    {
      
      let one = this.pqTcReceiveSkuTable.oneSkuRow;
      this.$refs.pqTcReceiveSku.setValues([{
          rowKey:r.row.id,
          values:  {
            'skuName': one.sku_name,
            'skuId':one.sku_id,
            'mpackNo': one.mpack_no,
            'packNo': one.pack_no,
            'skuCode': one.sku_code,
            'orderNo': one.order_no,
            'packNum': one.pack_num,
            'sites': one.sites,
            'account': one.account,
            'platSku': one.plat_sku,
            'platAsin': one.plat_asin,
            'platFnsku': one.plat_fnsku,
            'outStockId': one.out_stock_id,
            'storage': one.kcdd
          }}]
      )
    },



      openTouChengRp() {
      //打开online报表   
      this.$refs.TouChengRp.show()   
      this.$refs.TouChengRp.loadData(1) 
    },

      addBefore(){
        this.form.resetFields()
        this.pqTcReceiveSkuTable.dataSource=[]
      },
      getAllTable() {
        let values = this.tableKeys.map(key => getRefPromise(this, key))
        return Promise.all(values)
      },
      /** 调用完edit()方法之后会自动调用此方法 */
      editAfter() {
        let fieldval = pick(this.model,'receiveTime','lossAmo','status','bz')
        this.$nextTick(() => {
          this.form.setFieldsValue(fieldval)
        })
        // 加载子表数据
        if (this.model.id) {
          let params = { id: this.model.id }
          this.requestSubTableData(this.url.pqTcReceiveSku.list, params, this.pqTcReceiveSkuTable)
        }
      },
      /** 整理成formData */
      classifyIntoFormData(allValues) {
        let main = Object.assign(this.model, allValues.formValue)
        return {
          ...main, // 展开
          pqTcReceiveSkuList: allValues.tablesValue[0].values,
        }
      },
      //渲染流程表单数据
      showFlowData(){
        if(this.formBpm === true){
          let params = {id:this.formData.dataId};
          getAction(this.url.queryById,params).then((res)=>{
            if(res.success){
              this.edit (res.result);
            }
          })
        }
      },
      validateError(msg){
        this.$message.error(msg)
      },
     popupCallback(row){
       this.form.setFieldsValue(pick(row,'receiveTime','lossAmo','status','bz'))
     },

    }
  }
</script>

<style lang="less" scoped>
  .action-button {
    margin-bottom: 8px;

    .gap {
      padding-left: 8px;
    }

  }
</style>