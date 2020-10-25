<template>
  <j-modal
    :title="title"
    :width="width"
    :visible="visible"
    :confirmLoading="confirmLoading"
    switchFullscreen
    @ok="handleOk"
    @cancel="handleCancel"
    cancelText="关闭">
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
        <a-row>
          <a-col :span="24">
            <a-form-item label="库存地点" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag type="list" v-decorator="['kcdd']" :trigger-change="true" dictCode="pq_stroage_location,name,id" placeholder="请选择库存地点"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="商品名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-popup
                v-decorator="['skuName']"
                :trigger-change="true"
                org-fields="kcdd,sku_id,sku_name,specs_val,book_num,book_amo"
                dest-fields="kcdd,skuId,skuName,specsVal,bookNum,bookAmo"
                code="pq_check_rp_sp"
                @callback="popupCallback"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="规格值" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['specsVal']" placeholder="请输入规格值"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="账面数量" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['bookNum']" placeholder="请输入账面数量" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="盘点数量" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['eftNum']" placeholder="请输入盘点数量" style="width: 100%"/>
            </a-form-item>
          </a-col>
        </a-row>
      </a-form>
    </a-spin>
  </j-modal>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import { validateDuplicateValue } from '@/utils/util'
  import JDictSelectTag from "@/components/dict/JDictSelectTag"

  export default {
    name: "PqCheckReportSpModal",
    components: {
      JDictSelectTag,
    },
    props:{
      mainId:{
        type:String,
        required:false,
        default:''
      }
    },
    data () {
      return {
        form: this.$form.createForm(this),
        title:"操作",
        width:800,
        visible: false,
        model: {},
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },

        confirmLoading: false,
        validatorRules: {
        },
        url: {
          add: "/inventory/pqCheckReport/addPqCheckReportSp",
          edit: "/inventory/pqCheckReport/editPqCheckReportSp",
        }

      }
    },
    created () {
    },
    methods: {
      add () {
        this.edit({});
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'createBy','createTime','updateBy','updateTime','sysOrgCode','kcdd','skuId','skuName','specsVal','bookNum','eftNum','checkReportId','bookAmo','eftAmo','markLoss'))
        })
      },
      close () {
        this.$emit('close');
        this.visible = false;
      },
      handleOk () {
        const that = this;
        // 触发表单验证
        this.form.validateFields((err, values) => {
          if (!err) {
            that.confirmLoading = true;
            let httpurl = '';
            let method = '';
            if(!this.model.id){
              httpurl+=this.url.add;
              method = 'post';
            }else{
              httpurl+=this.url.edit;
               method = 'put';
            }
            let formData = Object.assign(this.model, values);
            formData['checkReportId'] = this.mainId
            console.log("表单提交数据",formData)
            httpAction(httpurl,formData,method).then((res)=>{
              if(res.success){
                that.$message.success(res.message);
                that.$emit('ok');
              }else{
                that.$message.warning(res.message);
              }
            }).finally(() => {
              that.confirmLoading = false;
              that.close();
            })
          }

        })
      },
      handleCancel () {
        this.close()
      },
      popupCallback(row){
        this.form.setFieldsValue(pick(row,'createBy','createTime','updateBy','updateTime','sysOrgCode','kcdd','skuId','skuName','specsVal','bookNum','eftNum','checkReportId','bookAmo','eftAmo','markLoss'))
      },


    }
  }
</script>
