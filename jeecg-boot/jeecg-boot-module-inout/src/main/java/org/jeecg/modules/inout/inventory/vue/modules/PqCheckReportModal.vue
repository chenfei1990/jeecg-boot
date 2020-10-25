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
            <a-form-item label="报告编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['reportId']" placeholder="请输入报告编号"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="盘点日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择盘点日期" v-decorator="['checkDate', validatorRules.checkDate]" :trigger-change="true" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="库存地点" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag type="list" v-decorator="['kcdd', validatorRules.kcdd]" :trigger-change="true" dictCode="pq_stroage_location,name,id" placeholder="请选择库存地点"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="损益方式" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag type="list" v-decorator="['lossesType', validatorRules.lossesType]" :trigger-change="true" dictCode="pq_loss_type" placeholder="请选择损益方式"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="录入完成时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择录入完成时间" v-decorator="['finishTime']" :trigger-change="true" :show-time="true" date-format="YYYY-MM-DD HH:mm:ss" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag type="list" v-decorator="['status']" :trigger-change="true" dictCode="pq_check_report_status" placeholder="请选择状态"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="账面金额" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['bookAmo']" placeholder="请输入账面金额" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="盘点金额" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['eftAmo']" placeholder="请输入盘点金额" style="width: 100%"/>
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
  import JDate from '@/components/jeecg/JDate'  
  import JDictSelectTag from "@/components/dict/JDictSelectTag"

  export default {
    name: "PqCheckReportModal",
    components: { 
      JDate,
      JDictSelectTag,
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
          checkDate: {
            rules: [
              { required: true, message: '请输入盘点日期!'},
            ]
          },
          kcdd: {
            rules: [
              { required: true, message: '请输入库存地点!'},
            ]
          },
          lossesType: {
            rules: [
              { required: true, message: '请输入损益方式!'},
            ]
          },
        },
        url: {
          add: "/inventory/pqCheckReport/add",
          edit: "/inventory/pqCheckReport/edit",
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
          this.form.setFieldsValue(pick(this.model,'reportId','checkDate','kcdd','lossesType','finishTime','status','bookAmo','eftAmo'))
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
        this.form.setFieldsValue(pick(row,'reportId','checkDate','kcdd','lossesType','finishTime','status','bookAmo','eftAmo'))
      },

      
    }
  }
</script>