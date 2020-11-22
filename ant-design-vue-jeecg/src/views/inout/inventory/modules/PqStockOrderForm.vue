<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <!-- 主表单区域 -->
      <a-form :form="form" slot="detail">
        <a-row>
          <a-col :span="12" >
            <a-form-item label="批次号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['orderNo',{  initialValue: batchNo }]" placeholder="请输入批次号" disabled></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12" >
            <a-form-item label="供应商名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-popup
                v-decorator="['supplierName']"
                :trigger-change="true"
                org-fields="id,name"
                dest-fields="supplierId,supplierName"
                code="supplier_rp"
                @callback="popupCallback"/>
            </a-form-item>
          </a-col>
          <a-col :span="12" >
            <a-form-item label="库存地点" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag type="list" v-decorator="['storage', validatorRules.storage]" :trigger-change="true" dictCode="pq_stroage_location,name,id" placeholder="请选择库存地点"/>
            </a-form-item>
          </a-col>
          <a-col :span="12" >
            <a-form-item label="采购日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择采购日期" v-decorator="['orderDate']" :trigger-change="true" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="12" >
            <a-form-item label="总金额" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number :disabled="true" v-decorator="['zongAmo']" placeholder="请输入总金额" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="12" >
            <a-form-item label="收货时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date :disabled="true" placeholder="请选择收货时间" v-decorator="['inDate']" :trigger-change="true" :show-time="true" date-format="YYYY-MM-DD HH:mm:ss" style="width: 100%"/>
            </a-form-item>
          </a-col>
        </a-row>
      </a-form>
    </j-form-container>
      <!-- 子表单区域 -->
    <a-tabs v-model="activeKey" @change="handleChangeTabs">
      <a-tab-pane tab="采购订单商品" :key="refKeys[0]" :forceRender="true">
        <div class="action-button">
            <a-button icon="plus" @click="openSkuRp" type="primary">添加SKU</a-button>
            <span class="gap"></span>
            <template v-if="selectedSkuRowIds.length>0">
            <a-popconfirm
              :title="`确定要删除这 ${selectedSkuRowIds.length} 项吗?`"
              @confirm="handleConfirmSkuDelete">
              <a-button type="primary" icon="minus" :disabled="disabled">删除</a-button>
              <span class="gap"></span>
            </a-popconfirm>
            <!-- <template v-if="showClearSelectButton">
              <a-button icon="delete" @click="handleClickClearSelection">清空选择</a-button>
              <span class="gap"></span>
            </template> -->
          </template>


          <j-popup-onl-report ref="SkuRp" :code="'sku_rp'" :multi="true" @ok="addSkuRow"/>
         </div>

        <j-editable-table
          :ref="refKeys[0]"
          :loading="pqStockOrderSkuTable.loading"
          :columns="pqStockOrderSkuTable.columns"
          :dataSource="pqStockOrderSkuTable.dataSource"
          :maxHeight="300"
          :disabled="formDisabled"
          :rowNumber="true"
          :rowSelection="true"
          @added="skuRowAdded" 
          @valueChange="skuRowValueChange"
          @selectRowChange="handleSelectRowChange"
          />
      </a-tab-pane>
    </a-tabs>
  </a-spin>
</template>

<script>

  import pick from 'lodash.pick'
  import { getAction,putAction } from '@/api/manage'
  import { FormTypes,getRefPromise } from '@/utils/JEditableTableUtil'
  import { JEditableTableMixin } from '@/mixins/JEditableTableMixin'
  import { validateDuplicateValue } from '@/utils/util'
  import JFormContainer from '@/components/jeecg/JFormContainer'
  import JDate from '@/components/jeecg/JDate'  
  import JDictSelectTag from "@/components/dict/JDictSelectTag"
  import JPopupOnlReport from '@/components/jeecg/modal/JPopupOnlReport'

  export default {
    name: 'PqStockOrderForm',
    mixins: [JEditableTableMixin],
    components: {
      JFormContainer,
      JDate,
      JDictSelectTag,
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
        batchNo :'',
        selectedSkuRowIds: [],
        validatorRules: {
          storage: {
            rules: [
              { required: true, message: '请输入库存地点!'},
            ]
          },
        },
        refKeys: ['pqStockOrderSku', ],
        tableKeys:['pqStockOrderSku', ],
        activeKey: 'pqStockOrderSku',
        // 采购订单商品
        pqStockOrderSkuTable: {
          loading: false,
          dataSource: [],
          columns: [
            {
              title: 'SKU名称',
              key: 'skuName',
              type: FormTypes.input,
              // popupCode:"sku_rp",
              // destFields:"skuId,skuName,inPrice,specsVal,jxsl",
              // orgFields:"sku_id,sku_name,in_price,specs_val,jxsl",
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
            {
              title: '规格值',
              key: 'specsVal',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
            {
              title: '采购单价（含税）',
              key: 'inPrice',
              type: FormTypes.inputNumber,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
              validateRules: [{ pattern: "money", message: "${title}格式不正确" }],
            },
            {
              title: '采购数量',
              key: 'num',
              type: FormTypes.inputNumber,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
              validateRules: [{ required: true, message: '${title}不能为空' }],
            },
            {
              title: '收货数量',
              key: 'inNum',
              type: FormTypes.inputNumber,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
              validateRules: [{ pattern: "n", message: "${title}格式不正确" }],
            },
            {
              title: '收货金额',
              key: 'inAmo',
              type: FormTypes.inputNumber,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
              validateRules: [{ pattern: "money", message: "${title}格式不正确" }],
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
              title: 'in_price',
              key: 'in_price',
              type:"hidden"
            },

            {
              title: 'specs_val',
              key: 'specs_val',
              type:"hidden"
            },

            {
              title: 'jxsl',
              key: 'jxsl',
              type:"hidden"
            },
          ]
        },
        url: {
          add: "/inventory/pqStockOrder/add",
          edit: "/inventory/pqStockOrder/edit",
          queryById: "/inventory/pqStockOrder/queryById",
          stock_num:"/sys/fillRule/executeRuleByCode/shop_order_num",
          pqStockOrderSku: {
            list: '/inventory/pqStockOrder/queryPqStockOrderSkuByMainId'
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
        this.pqStockOrderSkuTable.dataSource=[];
        //进入页面的适合
        this.setBatchNo();
      },
      setBatchNo() {
        putAction(this.url.stock_num,{}).then((res) => {
          if(res.success){
            this.batchNo = res.result; //设置批次号
          }
        })
      },
    openSkuRp() {
      //打开online报表   
      this.$refs.SkuRp.show()   
      this.$refs.SkuRp.loadData(1) 
    },

    addSkuRow(rows) {
      //将返回的数据添加到sku_tab中
      //添加行数
      if(rows.length >0 )
      {

      // union key
      let oldRows = this.$refs.pqStockOrderSku.getValuesSync().values;
    

      for (let j = 0; j < rows.length; j++) {
        const r = rows[j];
        let canAdd = true;
        for (let x = 0; x < oldRows.length; x++) {
          const element = oldRows[x];   
          if(r.sku_id == element.skuId){
            canAdd = false;
            break;
          }
        } 
        if(canAdd)
        {
          this.pqStockOrderSkuTable.oneSkuRow = r ;
          this.$refs.pqStockOrderSku.add(1,true);
        }
      
      }
      
      }
    },

    skuRowAdded(r)
    {
      let one = this.pqStockOrderSkuTable.oneSkuRow;
      this.$refs.pqStockOrderSku.setValues([{
          rowKey:r.row.id,
          values:  {
            'skuName': one.sku_name,
            'specsVal': one.specs_val,
            'skuId': one.sku_id,
            'jxsl': one.jxsl,
            'inPrice': one.in_price
          }}]
      )
    },

    skuRowValueChange(c){
      //行数据变化是触发
       
      
       if(c.column.key === "inNum"){
        //  进货数量
        let id = c.row.id;
        let num = c.row.inNum;
        let price = c.row.inPrice;
        let nval = price*num;
   
        this.$refs.pqStockOrderSku.setValues([{
        rowKey:id,
        values:  {
          'inAmo': nval.toFixed(2)
        }}]
        )
       }
    },

     handleSelectRowChange(selectedRowIds) {
        this.selectedSkuRowIds = selectedRowIds
    },

    handleConfirmSkuDelete(){
      this.$refs.pqStockOrderSku.handleConfirmDelete()
    },

      getAllTable() {
        let values = this.tableKeys.map(key => getRefPromise(this, key))
        return Promise.all(values)
      },
      /** 调用完edit()方法之后会自动调用此方法 */
      editAfter() {
        let fieldval = pick(this.model,'orderNo','supplierName','storage','orderDate','zongAmo','inDate')
        this.$nextTick(() => {
          this.form.setFieldsValue(fieldval)
        })
        // 加载子表数据
        if (this.model.id) {
          let params = { id: this.model.id }
          this.requestSubTableData(this.url.pqStockOrderSku.list, params, this.pqStockOrderSkuTable)
        }
      },
      /** 整理成formData */
      classifyIntoFormData(allValues) {
        let main = Object.assign(this.model, allValues.formValue)
        return {
          ...main, // 展开
          pqStockOrderSkuList: allValues.tablesValue[0].values,
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
       this.form.setFieldsValue(pick(row,'orderNo','supplierName','storage','orderDate','zongAmo','inDate'))
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