<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <!-- 主表单区域 -->
      <a-form :form="form" slot="detail">
        <a-row :gutter="24">
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
              <!-- <a-input v-decorator="['goodsTypeId', validatorRules.goodsTypeId]" placeholder="请输入分类"></a-input> -->
              <j-tree-select
                placeholder="请选择分类"
                v-decorator="['goodsTypeId', validatorRules.goodsTypeId]"
                dict="pq_goods_type,name,id"
                pidField="pid"
                pidValue="0"
              />
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

          <!-- <a-col :span="24" v-show="showMulGuiGe == 1 && selectProductAttr.length > 0">
            <div style="background:#ECECEC; padding:40px">
              <a-card   title="商品规格" :bordered="false" >

               <a-button-group  slot="extra">
                  <a-button  type="danger" @click="handleRefreshProductSkuList">
                    刷新列表
                  </a-button>
                  <a-button  type="primary" @click="handleSetPrice">
                    同步价格
                  </a-button>
               </a-button-group>
               <a-form-item v-for="(productAttr,idx) in selectProductAttr" :label="productAttr.name">         
                <a-checkbox-group
                  v-model="productAttr.values"
                  :options="productAttr.options"
                />
              </a-form-item> 

              </a-card>
            </div>

          </a-col> -->


    
        </a-row>


        <a-row :gutter="24" style="background:#ECECEC; padding:40px" v-show="showMulGuiGe == 1 && selectProductAttr.length > 0">
            <a-card   title="商品规格" :bordered="false" >      
               <a-button-group  slot="extra">
                  <a-button  type="danger" @click="handleRefreshProductSkuList">
                    刷新列表
                  </a-button>
                  <a-button  type="primary" @click="handleSetPrice">
                    同步价格
                  </a-button>
               </a-button-group>
               <a-form-item v-for="(productAttr,idx) in selectProductAttr" :label="productAttr.name">
                <a-checkbox-group
                  v-model="productAttr.values"
                  :options="productAttr.options"
                />
              </a-form-item> 
            </a-card>
        </a-row>


        <a-row :gutter="24" style="background:#ECECEC; padding:0px 40px 40px" v-show="showMulGuiGe == 1 && selectProductParam.length > 0">
            <a-card title="商品参数" :bordered="false" >      
              <a-col :span="6" v-for="(item,index) in selectProductParam">
                <a-form-item  :label="item.name">
                  <j-select-multiple  v-if="item.inputType===1" v-model="item.value" :options="item.options"/>
                  <a-input v-else v-model="item.value"></a-input>
                </a-form-item>
              </a-col>
            </a-card>
        </a-row>

       

      </a-form>
    </j-form-container>
      <!-- 子表单区域 -->
    <a-tabs  v-model="activeKey" @change="handleChangeTabs">
      <a-tab-pane tab="商品SKU信息" :key="refKeys[0]" :forceRender="true">
        <j-editable-table
          :ref="refKeys[0]"
          :loading="pqGoodsSkuTable.loading"
          :columns="pqGoodsSkuTable.autoColumns.concat(pqGoodsSkuTable.columns)"
          :dataSource="pqGoodsSkuTable.dataSource"
          :maxHeight="300"
          :disabled="formDisabled"
          :rowNumber="true"
          :rowSelection="true"/>
      </a-tab-pane>

      
      <!-- <a-tab-pane tab="商品属性参数值" :key="refKeys[1]" :forceRender="false" >
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
      </a-tab-pane> -->


    </a-tabs>
  </a-spin>
</template>

<script>

  import pick from 'lodash.pick'
  import { getAction } from '@/api/manage'
  import { flatten } from '@/utils/sku'
  import { FormTypes,getRefPromise } from '@/utils/JEditableTableUtil'
  import { JEditableTableMixin } from '@/mixins/JEditableTableMixin'
  import { validateDuplicateValue } from '@/utils/util'
  import JFormContainer from '@/components/jeecg/JFormContainer'
  import JImageUpload from '@/components/jeecg/JImageUpload'
  import JDictSelectTag from "@/components/dict/JDictSelectTag"
  import JSearchSelectTag from '@/components/dict/JSearchSelectTag'
  import JAreaLinkage from '@comp/jeecg/JAreaLinkage'
  import JCheckbox from '@/components/jeecg/JCheckbox'
  import JTreeSelect from '@/components/jeecg/JTreeSelect'
  import JSelectMultiple from '@/components/jeecg/JSelectMultiple'

  export default {
    name: 'PqGoodsForm',
    mixins: [JEditableTableMixin],
    components: {
      JSelectMultiple,
      JFormContainer,
      JImageUpload,
      JDictSelectTag,
      JSearchSelectTag,
      JAreaLinkage,
      JCheckbox,
      JTreeSelect
    },
    data() {
      return {

        jselectMultiple: {
          options: [
            { text: '字符串', value: 'String' },
            { text: '整数型', value: 'Integer' },
            { text: '浮点型', value: 'Double' },
            { text: '布尔型', value: 'Boolean' }
          ],
          value: 'Integer,Boolean'
        },
        //选中的商品属性
        selectProductAttr: [],
        //选中的商品参数
        selectProductParam: [],

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
        refKeys: ['pqGoodsSku', 
        // 'pqProductAttributeValue', 
        ],
        tableKeys:['pqGoodsSku',
        //  'pqProductAttributeValue', 
         ],
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
              type: FormTypes.input,
              width:"250px",
              placeholder: '请输入${title}',
              disabled:true,
            },
            {
              title: 'SKU条码',
              key: 'skuCode',
              type: FormTypes.input,
              width:"200px",
              placeholder: '可由系统生成编码',
              defaultValue: '',
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
              title: '售价',
              key: 'price',
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
            // {
            //   title: '商品销售属性',
            //   key: 'spData',
            //   type: FormTypes.input,
            //   width:"200px",
            //   placeholder: '请输入${title}',
            //   defaultValue: '',
            // },
          ],
          autoColumns:[]
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
          queryProductAttr: '/goods/pqProductAttributeCategory/queryPqProductAttributeByMainId',
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

      handleRefreshProductSkuList(){
        var that = this;
        this.$confirm({
          title: '提示',
          content: '刷新列表将导致sku信息重新生成，是否要刷新 ?',
          onOk() {

            that.refreshProductSkuList(that.selectProductAttr);
          },
          onCancel() {
            
          },
        });
      },

      handleSetPrice() {
        var that = this;
        this.$confirm({
          title: '提示',
          content: '将同步第一个sku的库存到所有sku,是否继续 ?',
          onOk() {

            that.refreshSkuPrice();
          },
          onCancel() {
            
          },
        });

      },

      refreshSkuPrice()
      {
        //如何获取一行数据，并且设置值
        let rows = this.$refs.pqGoodsSku.getValuesSync().values;
        if(rows.length > 0) {
          let r1 =  rows[0];
          for (let j = 0; j < rows.length; j++) {
            const element = rows[j];
            this.$refs.pqGoodsSku.setValues([{
              rowKey:element.id,
              values:  {
                'inPrice': r1.inPrice,
                'price': r1.price
              }}]
            )
          }
        }
      },
      refreshProductSkuList(sel){
          let len = sel.length;
          this.pqGoodsSkuTable.dataSource=[];
          let skus = [];
          this.pqGoodsSkuTable.autoColumns = [];
          for (let x = 0; x < sel.length; x++) {
            let elt = sel[x];
            if(elt.values.length > 0) {
              this.pqGoodsSkuTable.autoColumns.push({
                title: elt.name,
                key: elt.name,
                type: FormTypes.input,
                width:"100px",
                disabled:true
              }); //动态添加表头
            }
          }


          for( let i = 0 ; i < len ; i++) {

            let ii = i + 1;
            let one = {};
            one.id = sel[i].id;
            one.value=sel[i].name;
            one.leaf = [];
            for(let j = 0 ;  j < sel[i].values.length ; j ++ ){
                let val = sel[i].values[j];
                let jj = j + 1;
                let oneLeaf = {};
                oneLeaf.id = ii * jj ;
                oneLeaf.value = val;
                one.leaf.push(oneLeaf);
            } 
            if(sel[i].values.length > 0) skus.push(one); 

          }

          let skusList = flatten(skus);
          for(let i = 0 ; i < skusList.length; i ++) {
              let lskus = skusList[i].skus;
              let r = {};
              
              let specs = [];
              for(let j = 0 ; j < lskus.length ; j ++ ) {
                //     {
                //     "k_id":"1322368431424708610",
                //     "k":"颜色",
                //     "v_id":1,
                //     "v":"红色"
                // }
                let sku = lskus[j];
                r[sku.k] = sku.v;  
                specs.push(sku.v);
              }
              let skname = this.form.getFieldValue('name');
              skname = skname + '-' + specs.join('-');
              r.name = skname; //sku名称
              r.specsVal = specs.join('-');
              this.pqGoodsSkuTable.dataSource.push(r); // 直接加就能显示
                      
          }

      },

      

      

      getProductAttrList(type,cid,mainId,success){
        let params = {id:cid};
        getAction(this.url.queryProductAttr,params).then((res)=>{
          if(res.success){
            let list = res.result;
            if(type == 0) {
              //规格的
              this.selectProductAttr = [];
              //参数 
              if(!mainId)  this.selectProductParam = []; 

               for (let i =0 ; i < list.length; i++ ){
                
                 //此处判断 类型
                 if(list[i].type === 0) {
                  let options = [];
                  let values = [];
                  options = this.getAttrValueOptions(list[i].inputList);
                  this.selectProductAttr.push({
                    id: list[i].id,
                    name: list[i].name,
                    handAddStatus: list[i].handAddStatus,
                    inputList: list[i].inputList,
                    values: values,
                    options: options
                  }); 
                 } else {
                    
                    if(!mainId) {

                      let value = '';
                      let options = this.getAttrValueOptions(list[i].inputList);
                      // 规格的
                      this.selectProductParam.push({
                        id: list[i].id,
                        name: list[i].name,
                        inputType: list[i].inputType,
                        inputList: list[i].inputList,
                        options: options,
                        value : value 
                      });
                    }
                 }
            
              }
            } 
            typeof success === 'function' ? success(res) : '' ; //执行回调

          }
        })

      },

      getAttrValueOptions(v){
        let options = [];

        if(!v) return options;

        let vl = v.split(',');

        for(let i = 0 ; i< vl.length;i++){
          let option = {label:vl[i],value:vl[i]};
          options.push(option);
        }
        return options;
      },

      MulGuiGeChange(val){
        if(val === '1'){
          this.showMulGuiGe  = 1;
        } else {
          this.showMulGuiGe  = 0;
        }
      },

      addBefore(){
        // 进入页面的时候
        this.selectProductAttr = [];
        this.selectProductParam = [];
        this.showMulGuiGe = 0;
        this.form.resetFields()
        this.pqGoodsSkuTable.dataSource=[];
        this.pqProductAttributeValueTable.dataSource=[]; //清空
         this.pqGoodsSkuTable.autoColumns = []; 
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
          let params = { id: this.model.id };
          //可以执行回调
          this.requestSubTableData(this.url.pqGoodsSku.list, params, this.pqGoodsSkuTable,this.editShow);
          // this.requestSubTableData(this.url.pqProductAttributeValue.list,
          //  params, this.pqProductAttributeValueTable,this.editShowVal);
        }      
      },

      editShow(res) {
        if(this.model.id) 
        {
          //需要展示 多规格属性,编辑或者展示详情
          this.MulGuiGeChange(this.model.sepcification);   
          //没判断是否成功   
          this.getProductAttrList(0, this.model.productAttributeCategoryId,this.model.id,this.editShowAutoColunm); //展示 规则等数据 conlumn已经完成  
          //用于显示商品参数
          let params = { id: this.model.id };
          this.requestSubTableData(this.url.pqProductAttributeValue.list,
           params, this.pqProductAttributeValueTable,this.editShowVal);

        }


      },
      //显示参数用
      editShowVal(res) {
        this.selectProductParam = [];
          const valList = res.result;
        
          if(valList.length > 0) {

            for (let i = 0; i < valList.length; i++) {
              const et = valList[i];
              const id = et.productAttributeId;
              let options = this.getAttrValueOptions(et.inputList);
              // 规格的
              this.selectProductParam.push({
                id: id,
                name: et.name,
                inputType: et.inputType,
                options: options,
                value : et.value 
              });
            }
          }
         
      },

      editShowAutoColunm(){
           
        if(this.model.id) {
          //编辑状态显示 TODO
          let data1 = this.pqGoodsSkuTable.dataSource;
          this.pqGoodsSkuTable.autoColumns = [];
          for (let x = 0; x < this.selectProductAttr.length; x++) {
            let elt = this.selectProductAttr[x];
            let colKeyVal = [];
            let colKey = elt.name ;

            for (let z = 0; z < data1.length; z++) {
              let skudata = data1[z];
              let pspData = JSON.parse(skudata.spData);
              for (let u = 0; u < pspData.length; u++) {
                const kv = pspData[u];

                if(kv.key === colKey) {
                  skudata[kv.key] = kv.value;
                  if(colKeyVal.indexOf(kv.value) == -1) {
                    colKeyVal.push(kv.value);
                  }

                }
              }                  
            }

            if(colKeyVal.length > 0 ){
              this.pqGoodsSkuTable.autoColumns.push({
              title: elt.name,
              key: elt.name,
              type: FormTypes.input,
              width:"100px",
              disabled:true
              }); //动态添加表头

              elt.values = colKeyVal;
            }  
          }
          this.pqGoodsSkuTable.dataSource = [];
          this.$nextTick(() => {
            this.pqGoodsSkuTable.dataSource = data1;
          }); //这样才能渲染，可以用setvalues

        } 


      },


      /** 整理成formData */
      classifyIntoFormData(allValues) {
       
      //  保存之前
        let main = Object.assign(this.model, allValues.formValue)
        let skutb = allValues.tablesValue[0].values;

        for (let i = 0; i < skutb.length; i++) {
          const sku = skutb[i];
          let data = [];
           for (let j = 0;j < this.selectProductAttr.length; j++) {
            const elm = this.selectProductAttr[j];
            let key = elm.name;
            let value = sku[elm.name];

            if(value)
            {
              data.push({key,value});
            }
          
          }
          sku.spData = JSON.stringify(data);
        }
        
        let valTab = {};
        valTab.deleteIds = [];
        valTab.values = [];
        for (let x = 0; x < this.selectProductParam.length; x++) {
            const p = this.selectProductParam[x]; 
            let oneAttr = {};
            oneAttr.productAttributeId = p.id;
            oneAttr.value= p.value;
            oneAttr.productAttributeName = p.name;
            valTab.values.push(oneAttr);
        }
        allValues.tablesValue.push(valTab);

     

        //需要把spData 复制
        return {
          ...main, // 展开
          pqGoodsSkuList: allValues.tablesValue[0].values,
          // 加入属性tab
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