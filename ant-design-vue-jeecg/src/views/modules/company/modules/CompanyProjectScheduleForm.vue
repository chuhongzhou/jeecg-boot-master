<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form-model ref="form" :model="model" :rules="validatorRules" slot="detail">
        <a-row>
          <a-col :span="24">
            <a-form-model-item label="隶属项目" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="projectId">
              <!-- <a-input v-model="model.projectId" placeholder="请选择项目隶属公司"></a-input> -->
              <a-select v-model="model.projectId" class="code-mode-select" placeholder="请选择隶属项目">
                <a-select-option v-for="mode in modes" :key="mode.projectName" :value="mode.id">
                  {{ mode.projectName }}
                </a-select-option>
              </a-select>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="项目进度名称" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="title">
              <a-input v-model="model.title" placeholder="请输入项目进度名称"></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="权重" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="weight">
              <a-input-number v-model="model.weight" placeholder="请输入完成权重" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="完成进度" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="schedule">
              <a-input-number v-model="model.schedule" placeholder="请输入完成进度" style="width: 100%" />
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
  name: 'CompanyProjectScheduleForm',
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
      modes: [], // 项目列表
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
        projectId: [{ required: true, message: '请输入选择项目!' }],
        weight: [{ required: true, message: '请输入权重!' }],
        schedule: [{ required: true, message: '请输入完成进度!' }],
        title: [{ required: true, message: '请输入项目进度名称!' }],
      },
      url: {
        add: '/company/companyProjectSchedule/add',
        edit: '/company/companyProjectSchedule/edit',
        queryById: '/company/companyProjectSchedule/queryById',
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
    this.getProjectList()
  },
  methods: {
    getProjectList() {
      getAction('/company/companyProject/queryList').then((res) => {
        if (res.success) {
          console.log('项目列表', res.result)
          this.modes = res.result
        } else {
          this.modes = []
        }
      })
    },
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
          if (this.model.schedule < 0 || this.model.schedule > 100) {
            return that.$message.warning('进度必须是0-100的整数')
          }
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