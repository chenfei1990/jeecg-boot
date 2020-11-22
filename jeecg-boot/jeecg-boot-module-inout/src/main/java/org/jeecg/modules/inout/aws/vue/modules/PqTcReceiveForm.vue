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
              <a-input-number v-decorator="['lossAmo', validatorRules.lossAmo]" placeholder="请输入损溢金额" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="24" >
            <a-form-item label="是否确认" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['status']" placeholder="请输入是否确认" style="width: 100%"/>
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
        <j-editable-table
          :ref="refKeys[0]"
          :loading="pqTcReceiveSkuTable.loading"
          :columns="pqTcReceiveSkuTable.columns"
          :dataSource="pqTcReceiveSkuTable.dataSource"
          :maxHeight="300"
          :disabled="formDisabled"
          :rowNumber="true"
          :rowSelection="true"
          :actionButton="true"/>
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

  export default {
    name: 'PqTcReceiveForm',
    mixins: [JEditableTableMixin],
    components: {
      JFormContainer,
      JDate,
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
        addDefaultRowNum: 1,
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
              title: 'SKU名称',
              key: 'skuName',
              type: FormTypes.popup,
              popupCode:"tou_cheng_mx_rp",
              destFields:"outStockId,mpackNo,packNo,skuId,skuName,skuCode,orderNo,packNum,sites,account,platSku,platAsin,platFnsku,storage",
              orgFields:"out_stock_id,mpack_no,pack_no,sku_id,sku_name,sku_code,order_no,pack_num,sites,account,plat_sku,plat_asin,plat_fnsku,storage_id",
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
            {
              title: '头程箱号',
              key: 'mpackNo',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
            {
              title: '打包箱号',
              key: 'packNo',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
            {
              title: 'SKU编码',
              key: 'skuCode',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
            {
              title: '批次号',
              key: 'orderNo',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
            {
              title: '出库数量',
              key: 'packNum',
              type: FormTypes.inputNumber,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
              validateRules: [{ pattern: "n", message: "${title}格式不正确" }],
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
            },
            {
              title: '适用账号',
              key: 'account',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
            {
              title: '平台SKU',
              key: 'platSku',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
            {
              title: '平台asin',
              key: 'platAsin',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
            {
              title: '平台fnsku',
              key: 'platFnsku',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
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
              key: 'out_stock_id',
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
              key: 'sku_id',
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

<style scoped>
</style>