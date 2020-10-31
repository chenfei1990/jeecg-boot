<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <!-- 主表单区域 -->
      <a-form :form="form" slot="detail">
        <a-row>
          <a-col :span="8" >
            <a-form-item label="商品名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['name', validatorRules.name]" placeholder="请输入商品名称"></a-input>
            </a-form-item>
          </a-col>
          <!-- <a-col :span="8" >
            <a-form-item label="简称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['jc']" placeholder="请输入简称"></a-input>
            </a-form-item>
          </a-col> -->
          <a-col :span="8" >
            <a-form-item label="分类" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['goodsTypeId', validatorRules.goodsTypeId]" placeholder="请输入分类"></a-input>
            </a-form-item>
          </a-col>

          <a-col :span="8" >
            <a-form-item label="多规格" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag type="radio" @change="MulGuiGeChange" v-decorator="['sepcification', validatorRules.sepcification]" :trigger-change="true" dictCode="yn" placeholder="请选择多规格"/>
            </a-form-item>
          </a-col>

          <a-col :span="8" >
            <a-form-item label="进项税率（%）" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-search-select-tag v-decorator="['jxsl']" dict="pq_rate,value,value" />
            </a-form-item>
          </a-col>
          <a-col :span="8" >
            <a-form-item label="销项税率（%）" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-search-select-tag v-decorator="['xssl']" dict="pq_rate,value,value" />
            </a-form-item>
          </a-col>
          <a-col :span="8" >
            <a-form-item label="关税税率（%）" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['gssl']" placeholder="请输入关税税率（%）" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="8" >
            <a-form-item label="商品条码" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['barcode']" placeholder="请输入商品条码"></a-input>
            </a-form-item>
          </a-col>

          <a-col :span="8" >
            <a-form-item label="单位" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['unit']" placeholder="请输入单位"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="8" >
            <a-form-item label="税收分类" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['taxId']" placeholder="请输入税收分类"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="8" >
            <a-form-item label="产地" :labelCol="labelCol" :wrapperCol="wrapperCol">
		      <j-area-linkage type="cascader" v-decorator="['place']" placeholder="请输入省市区"/>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item  label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['remark']"  placeholder="请输入备注"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="8" >
            <a-form-item label="图片" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-image-upload isMultiple v-decorator="['pic']"></j-image-upload>
            </a-form-item>
          </a-col>

          <!-- <a-col :span="8" >
            <a-form-item label="多规格" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag type="radio" v-decorator="['sepcification', validatorRules.sepcification]" :trigger-change="true" dictCode="yn" placeholder="请选择多规格"/>
            </a-form-item>
          </a-col> -->
          <a-col :span="8" v-show="showMulGuiGe == 1">
            <a-form-item label="属性分类" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag type="list" @change="handleProductAttrChange" v-decorator="['productAttributeCategoryId']" :trigger-change="true" dictCode="pq_product_attribute_category,name,id" placeholder="请选择属性分类"/>
            </a-form-item>
          </a-col>

          <a-col :span="24" v-show="showMulGuiGe == 1">
            <div style="background:#ECECEC; padding:40px">
              <a-card title="商品规格" :bordered="true" >
               <a-form-item label="颜色">
                <j-checkbox
                 v-model="jcheckbox.values"
                :options="jcheckbox.options"
                />
              </a-form-item> 

              <a-form-item label="尺码">
                <j-checkbox
                 v-model="jcheckbox.values"
                :options="jcheckbox.options"
                />
              </a-form-item> 
              </a-card>
            </div>

          </a-col>

           <a-col :span="24" v-show="showMulGuiGe == 1">
            <div style="background:#ECECEC; padding:0px 40px 40px">
              <a-card title="商品参数" :bordered="true" >
               
              </a-card>
            </div>

          </a-col>

        </a-row>
      </a-form>
    </j-form-container>
      <!-- 子表单区域 -->
    <a-tabs v-model="activeKey" @change="handleChangeTabs">
      <a-tab-pane tab="商品SKU信息" :key="refKeys[0]" :forceRender="true">
        <j-editable-table
          :ref="refKeys[0]"
          :loading="pqGoodsSkuTable.loading"
          :columns="pqGoodsSkuTable.columns"
          :dataSource="pqGoodsSkuTable.dataSource"
          :maxHeight="300"
          :disabled="formDisabled"
          :rowNumber="true"
          :rowSelection="true"
          :actionButton="true"/>
      </a-tab-pane>
      <a-tab-pane tab="商品属性参数值" :key="refKeys[1]" :forceRender="true">
        <j-editable-table
          :ref="refKeys[1]"
          :loading="pqProductAttributeValueTable.loading"
          :columns="pqProductAttributeValueTable.columns"
          :dataSource="pqProductAttributeValueTable.dataSource"
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
  import JImageUpload from '@/components/jeecg/JImageUpload'
  import JDictSelectTag from "@/components/dict/JDictSelectTag"
  import JSearchSelectTag from '@/components/dict/JSearchSelectTag'
  import JAreaLinkage from '@comp/jeecg/JAreaLinkage'
  import JCheckbox from '@/components/jeecg/JCheckbox'

  export default {
    name: 'PqGoodsForm',
    mixins: [JEditableTableMixin],
    components: {
      JFormContainer,
      JImageUpload,
      JDictSelectTag,
      JSearchSelectTag,
      JAreaLinkage,
      JCheckbox,
    },
    data() {
      return {
        showMulGuiGe: 0,  //多规格展示
        jcheckbox: {
          values: 'spring,jeecgboot',
          options: [
            { label: 'Jeecg', value: 'jeecg' },
            { label: 'Jeecg-Boot', value: 'jeecgboot' },
            { label: 'Spring', value: 'spring', disabled: true },
            { label: 'MyBaits', value: 'mybatis' }
          ]
        },

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
          name: {
            rules: [
              { required: true, message: '请输入商品名称!'},
            ]
          },
          goodsTypeId: {
            rules: [
              { required: true, message: '请输入分类!'},
            ]
          },
          sepcification: {
            rules: [
              { required: true, message: '请输入多规格!'},
            ]
          },
        },
        refKeys: ['pqGoodsSku', 'pqProductAttributeValue', ],
        tableKeys:['pqGoodsSku', 'pqProductAttributeValue', ],
        activeKey: 'pqGoodsSku',
        // 商品SKU信息
        pqGoodsSkuTable: {
          loading: false,
          dataSource: [],
          columns: [
            {
              title: 'SKU名称',
              key: 'name',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
              validateRules: [{ required: true, message: '${title}不能为空' }],
            },
            {
              title: '规格值',
              key: 'specsVal',
              type: FormTypes.list_multi,
              dictCode:"pq_type_specs_val,value,value",
              width:"250px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
            {
              title: 'SKU条码',
              key: 'skuCode',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
            {
              title: '售价',
              key: 'price',
              type: FormTypes.inputNumber,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
              validateRules: [{ pattern: "money", message: "${title}格式不正确" }],
            },
            {
              title: '采购价',
              key: 'inPrice',
              type: FormTypes.inputNumber,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
              validateRules: [{ pattern: "money", message: "${title}格式不正确" }],
            },
            {
              title: '图片',
              key: 'skuPic',
              type: FormTypes.image,
              token:true,
              responseName:"message",
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
            {
              title: '商品销售属性',
              key: 'spData',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
          ]
        },
        // 商品属性参数值
        pqProductAttributeValueTable: {
          loading: false,
          dataSource: [],
          columns: [
            {
              title: '商品ID',
              key: 'goodsId',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
            {
              title: '商品属性id',
              key: 'productAttributeId',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
            {
              title: '参数值',
              key: 'value',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
          ]
        },
        url: {
          add: "/goods/pqGoods/add",
          edit: "/goods/pqGoods/edit",
          queryById: "/goods/pqGoods/queryById",
          pqGoodsSku: {
            list: '/goods/pqGoods/queryPqGoodsSkuByMainId'
          },
          pqProductAttributeValue: {
            list: '/goods/pqGoods/queryPqProductAttributeValueByMainId'
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

      handleProductAttrChange(value) {
        this.getProductAttrList(0, value);
      },

      getProductAttrList(type,cid){
        

      },

      MulGuiGeChange(val){
        if(val === '1'){
          this.showMulGuiGe  = 1;
        } else {
          this.showMulGuiGe  = 0;
        }
      },

      addBefore(){
        this.form.resetFields()
        this.pqGoodsSkuTable.dataSource=[]
        this.pqProductAttributeValueTable.dataSource=[]
      },
      getAllTable() {
        let values = this.tableKeys.map(key => getRefPromise(this, key))
        return Promise.all(values)
      },
      /** 调用完edit()方法之后会自动调用此方法 */
      editAfter() {
        let fieldval = pick(this.model,'name','jc','goodsTypeId','jxsl','xssl','gssl','barcode','sepcification','productAttributeCategoryId','unit','taxId','place','remark','pic')
        this.$nextTick(() => {
          this.form.setFieldsValue(fieldval)
        })
        // 加载子表数据
        if (this.model.id) {
          let params = { id: this.model.id }
          this.requestSubTableData(this.url.pqGoodsSku.list, params, this.pqGoodsSkuTable)
          this.requestSubTableData(this.url.pqProductAttributeValue.list, params, this.pqProductAttributeValueTable)
        }
      },
      /** 整理成formData */
      classifyIntoFormData(allValues) {
        let main = Object.assign(this.model, allValues.formValue)
        return {
          ...main, // 展开
          pqGoodsSkuList: allValues.tablesValue[0].values,
          pqProductAttributeValueList: allValues.tablesValue[1].values,
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
       this.form.setFieldsValue(pick(row,'name','jc','goodsTypeId','jxsl','xssl','gssl','barcode','sepcification','productAttributeCategoryId','unit','taxId','place','remark','pic'))
     },

    }
  }
</script>

<style scoped>
</style>