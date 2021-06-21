<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form-model ref="form" :model="model" :rules="validatorRules" slot="detail">
        <a-row>
          <a-col :span="24">
            <a-form-model-item label="业务范围" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="scopeBusiness">
              <a-input v-model="model.scopeBusiness" placeholder="请输入业务范围"></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item
              label="项目成果"
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
              prop="projectAchievements"
            >
              <a-input v-model="model.projectAchievements" placeholder="请输入项目成果"></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="公司名称" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="companyTitle">
              <a-input v-model="model.companyTitle" placeholder="请输入公司名称"></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="公司背景图" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="companyImage">
              <!-- <a-input v-model="model.companyImage" placeholder="请输入公司背景图"></a-input> -->
              <j-image-upload class="avatar-uploader" text="上传" v-model="model.companyImage"></j-image-upload>
            </a-form-model-item>
          </a-col>
        </a-row>
      </a-form-model>
    </j-form-container>
  </a-spin>
</template>

<script>
import { httpAction, getAction } from '@/api/manage'
import { validateDuplicateValue } from '@/utils/util'

export default {
  name: 'CompanyProfileForm',
  components: {},
  props: {
    //表单禁用
    disabled: {
      type: Boolean,
      default: false,
      required: false,
    },
  },
  data() {
    return {
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
        scopeBusiness: [{ required: true, message: '请输入业务范围!' }],
        companyTitle: [{ required: true, message: '请输入公司名称!' }],
        projectAchievements: [{ required: true, message: '请输入项目成果!' }],
      },
      url: {
        add: '/company/companyProfile/add',
        edit: '/company/companyProfile/edit',
        queryById: '/company/companyProfile/queryById',
      },
    }
  },
  computed: {
    formDisabled() {
      return this.disabled
    },
  },
  created() {
    //备份model原始值
    this.modelDefault = JSON.parse(JSON.stringify(this.model))
  },
  methods: {
    add() {
      this.edit(this.modelDefault)
    },
    edit(record) {
      this.model = Object.assign({}, record)
      this.visible = true
    },
    submitForm() {
      const that = this
      // 触发表单验证
      this.$refs.form.validate((valid) => {
        if (valid) {
          that.confirmLoading = true
          let httpurl = ''
          let method = ''
          if (!this.model.id) {
            httpurl += this.url.add
            method = 'post'
          } else {
            httpurl += this.url.edit
            method = 'put'
          }
          httpAction(httpurl, this.model, method)
            .then((res) => {
              if (res.success) {
                that.$message.success(res.message)
                that.$emit('ok')
              } else {
                that.$message.warning(res.message)
              }
            })
            .finally(() => {
              that.confirmLoading = false
            })
        }
      })
    },
  },
}
</script>