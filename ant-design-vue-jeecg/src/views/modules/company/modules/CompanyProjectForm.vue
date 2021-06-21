<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form-model
        ref="form"
        :model="model"
        :rules="validatorRules"
        slot="detail"
        style="height: 600px; overflow-y: auto"
      >
        <a-row>
          <a-col :span="24">
            <a-form-model-item label="项目隶属公司" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="companyId">
              <!-- <a-input v-model="model.companyId" placeholder="请选择项目隶属公司"></a-input> -->
              <a-select
                v-model="model.companyId"
                class="code-mode-select"
                placeholder="请选择项目隶属公司"
              >
                <a-select-option v-for="mode in modes" :key="mode.companyTitle" :value="mode.id">
                  {{ mode.companyTitle }}
                </a-select-option>
              </a-select>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="项目名称" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="projectName">
              <a-input v-model="model.projectName" placeholder="请输入项目名称"></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="项目类型" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="projectType">
              <a-input v-model="model.projectType" placeholder="请输入项目类型"></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="项目描述" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="projectDescription">
              <a-input v-model="model.projectDescription" placeholder="请输入项目描述"></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item
              label="项目位置选择"
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
              prop="projectLatitude"
            >
              <MyMap v-model="position" :searchText="searchText" @valueChange="valueChange"></MyMap>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item
              label="项目所在位置（经度）"
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
              prop="projectLongitude"
            >
              <a-input v-model="model.projectLongitude" placeholder="请输入项目所在位置（经度）"></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item
              label="项目所在位置（纬度）"
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
              prop="projectLatitude"
            >
              <a-input v-model="model.projectLatitude" placeholder="请输入项目所在位置（纬度）"></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24" v-if="model.id">
            <a-form-model-item
              label="项目展示图片"
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
              prop="projectLatitude"
            >
              <div class="inline-img" v-for="(item, index) in model.companyProjectImageList" :key="index">
                <j-image-upload
                  class="avatar-uploader"
                  @change="handleChange(item, index)"
                  text="上传"
                  v-model="item.imageUrl"
                ></j-image-upload>
              </div>
              <div class="inline-img">
                <j-image-upload
                  bizPath="scott/pic"
                  @change="handleChange2"
                  :multiple="true"
                  :isMultiple="true"
                ></j-image-upload>
              </div>
            </a-form-model-item>
          </a-col>
          <a-col :span="24" v-if="model.id">
            <a-form-model-item
              label="项目进度"
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
              prop="companyProjectSchedules"
            >
              <div class="track-rcol">
                <div class="track-list">
                  <ul>
                    <div v-for="(item, index) in model.companyProjectSchedules" :key="index">
                      <li class="first">
                        <div></div>
                        <i class="node-icon"></i>
                        <span class="txt">{{ item.title }}</span>
                        <span class="time">时间: {{ item.createTime }}</span>
                        <span class="time">进度百分比: {{ item.schedule }}</span>
                      </li>
                    </div>
                  </ul>
                </div>
              </div>
            </a-form-model-item>
          </a-col>
          <a-col :span="24" v-if="model.id">
            <a-form-model-item
              label="项目完成进度"
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
              prop="projectSchedule"
            >
              <a-input
                :value="model.projectSchedule + '%'"
                placeholder="请计算项目完成进度"
                disabled="disabled"
              ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24" v-if="model.id">
            <a-form-model-item
              label="项目事件记录"
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
              prop="companyProjectEventRecords"
            >
              <div class="track-rcol">
                <div class="track-list">
                  <ul>
                    <div v-for="(item, index) in model.companyProjectEventRecords" :key="index">
                      <li class="first">
                        <div></div>
                        <i class="node-icon"></i>
                        <span class="txt">{{ item.content }}</span>
                        <span class="time">{{ item.createTime }}</span>
                      </li>
                    </div>
                  </ul>
                </div>
              </div>
            </a-form-model-item>
          </a-col>
        </a-row>
      </a-form-model>
    </j-form-container>
  </a-spin>
</template>

<script>
import { deleteAction, httpAction, getAction } from '@/api/manage'
import { validateDuplicateValue } from '@/utils/util'
import MyMap from '@/components/map/my-map'

export default {
  name: 'CompanyProjectForm',
  components: {
    MyMap,
  },
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
      position: [],
      searchText: '',
      modes: [], // 公司列表
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
        companyId: [{ required: true, message: '请选择项目隶属公司!' }],
        projectName: [{ required: true, message: '请输入项目名称!' }],
        projectType: [{ required: true, message: '请输入项目类型!' }],
      },
      url: {
        add: '/company/companyProject/add',
        edit: '/company/companyProject/edit',
        queryById: '/company/companyProject/queryById',
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
    this.getCompanyList()
  },
  methods: {
    // 获取子组件的地图坐标
    valueChange(val) {
      console.log('获取子组件的地图坐标', val)
      this.model.projectLongitude = val[0]
      this.model.projectLatitude = val[1]
    },
    getCompanyList() {
      getAction('/company/companyProfile/queryList').then((res) => {
        if (res.success) {
          this.modes = res.result
        } else {
          this.modes = []
        }
      })
    },
    handleChange(info, index) {
      //如有需要新增 删除逻辑
      console.log('要删除的对象', info, index)
      if (info.id) {
        this.delImg(info.id, index)
      }
    },
    handleChange2(info) {
      console.log('上传成功', info)
      var fileList = info.split(',')
      if (this.model.id) {
        fileList.forEach((i) => {
          console.log(i)
          // 数据库插入
          this.addImg(i)
        })
      }
    },
    // 添加图片请求
    addImg(url) {
      var that = this
      httpAction('/company/companyProjectImage/add', { imageUrl: url, projectId: that.model.id }, 'post').then(
        (res) => {
          if (res.success) {
            that.$message.success(res.message)
            that.$emit('ok')
          } else {
            that.$message.warning(res.message)
          }
        }
      )
    },
    // 删除图片请求
    delImg(id, index) {
      var that = this
      deleteAction('/company/companyProjectImage/delete', { id: id }).then((res) => {
        if (res.success) {
          this.model.companyProjectImageList.splice(index, 1)
          console.log('删除成功', res)
          that.$message.success('删除成功')
        } else {
          that.$message.warning(res.message)
        }
      })
    },
    add() {
      this.edit(this.modelDefault)
    },
    edit(record) {
      this.model = Object.assign({}, record)
      this.searchText = this.model.address;
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

<style scoped>
.track-list li .time {
  position: relative;
  display: inline-block;
  vertical-align: middle;
  color: #999;
  width: 100%;
  text-align: left;
  height: 20px;
  line-height: 20px;
}
.track-list li .txt {
  position: relative;
  display: inline-block;
  vertical-align: top;
  color: #999;
}
/* .track-list li.first .time {
  text-align: left;
  width: 94%;
}
.track-list li.first .txt {
  text-align: left;
  width: 94%;
} */
.inline-img {
  display: inline-block;
}
</style>