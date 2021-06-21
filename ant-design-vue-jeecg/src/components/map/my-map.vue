<template>
  <div class="amap-page-container">
    <!-- 搜索框 -->
    <el-amap-search-box
      ref="searchbox"
      class="search-box"
      :search-option="searchOption"
      :on-search-result="onSearchResult"
    ></el-amap-search-box>
    <!-- 高德地图组件 -->
    <el-amap vid="amapDemo" :zoom="zoom" :center="center" :events="events" class="amap-demo">
      <!-- 标记点，可以使用v-for循环设置多个 -->
      <el-amap-marker
        vid="component-marker"
        :position="marker.position"
        :events="marker.events"
        :draggable="true"
      ></el-amap-marker>
    </el-amap>
  </div>
</template>

<script>
const defaultPosition = [113.681423, 34.729528]

export default {
  name: 'myAmap',
  props: {
    value: {
      type: Array,
      default: () => defaultPosition,
    },
    searchText: {
      type: String,
      default: '',
    },
  },
  data() {
    return {
      zoom: 14, // 视角高度，越小视角越高
      center: defaultPosition, // 地图中心点位置
      events: {
        click: (e) => {
          // 鼠标点击地图事件
          this.marker.position = [e.lnglat.lng, e.lnglat.lat]
          this.positionChange()
        },
      },
      searchOption: {
        // 搜索框信息
        city: '北京', // 设置你要搜索的城市。默认全国
        citylimit: false, // 是否限制城市内搜索
      },
      marker: {
        // 坐标点信息
        position: defaultPosition, // 坐标的位置
        events: {
          dragend: (e) => {
            // 移动坐标点
            this.marker.position = [e.lnglat.lng, e.lnglat.lat]
            this.positionChange()
          },
        },
      },
    }
  },
  model: {
    prop: 'value',
    event: 'valueChange',
  },
  watch: {
    value(newVal) {
      if (newVal.length == 2) {
        this.marker.position = newVal
        this.center = newVal
      } else {
        this.marker.position = defaultPosition
        this.center = defaultPosition
      }
    },
    // 监听父组件传入的关键字进行搜索
    searchText(newVal) {
      // 将关键字赋值到输入框内
      this.$refs.searchbox._data.keyword = newVal
      // 调用搜索实例
      if (this.$refs.searchbox._data.keyword) {
        this.$refs.searchbox.search()
      }
    },
  },
  methods: {
    // 搜索获取到的位置信息，只取了第一条数据，需要的话可以设置多个标记点
    onSearchResult(pois) {
      if (pois.length > 0) {
        let { lng, lat } = pois[0]
        this.marker.position = [lng, lat]
        this.center = [lng, lat]
        console.log('内容', this.center)
        this.positionChange()
      }
    },
    // 当标记点发生变化时，将数据传给父组件
    positionChange() {
      this.$emit('valueChange', this.marker.position)
    },
  },
}
</script>

<style scoped>
.amap-page-container {
  position: relative;
  width: 100%;
  height: 100%;
}
.amap-demo {
  width: 100%;
  height: 100%;
}
.search-box {
  /* position: absolute; */
  /* top: 25px; */
  /* left: 20px; */
  /* width: 300px; */
}
.amap-page-container {
  position: relative;
}
</style>