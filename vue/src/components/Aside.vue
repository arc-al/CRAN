<template>

  <el-menu :default-openeds="['1', '3']"
           style="min-height:100%;overflow-x: hidden"
           background-color="rgb(48,65,86)"
           text-color="#fff"
           active-text-color="#ffd04b"
           :collapse-transition="false"
           :collapse="isCollapse"
           router
           @select="handleSelect"
  >
    <div style="height: 60px ;line-height: 60px ;text-align: center">
      <img src="../assets/logo.jpg" alt="" style="height: 32px; position: relative; top: 9px; margin-right: 7px">
      <b style="color: white" v-show="logoTextShow">CRAN系统</b>
    </div>



    <el-submenu index="1">
      <template slot="title">
        <i class="el-icon-set-up"></i>
        <span slot="title">网络部署</span>
      </template>
      <el-menu-item index="/manage/BaseStation">
        <i class="el-icon-s-custom"></i>
        <span slot="title">基站信息</span>
      </el-menu-item>
      <el-menu-item index="/manage/Terminal">
        <i class="el-icon-s-custom"></i>
        <span slot="title">终端信息</span>
      </el-menu-item>
      <el-menu-item index="/manage/Mec">
        <i class="el-icon-s-custom"></i>
        <span slot="title">MEC信息</span>
      </el-menu-item>
    </el-submenu>
    <el-menu-item index="/manage/Slicing">
      <template slot="title">
        <i class="el-icon-house"></i>
        <span slot="title">切片信息</span>
      </template>
    </el-menu-item>
    <el-menu-item index="/manage/home">
      <template slot="title">
        <i class="el-icon-house"></i>
        <span slot="title">性能展示</span>
      </template>
    </el-menu-item>
    <el-submenu index="2">
      <template slot="title">
        <i class="el-icon-set-up"></i>
        <span slot="title">SLA适配性分析</span>
      </template>
      <el-menu-item index="/manage/SLA">
        <i class="el-icon-s-custom"></i>
        <span slot="title">SLA信息</span>
      </el-menu-item>
      <el-menu-item index="/manage/SLASelect">
        <i class="el-icon-s-custom"></i>
        <span slot="title">适配性分析</span>
      </el-menu-item>
    </el-submenu>


    <el-menu-item index="/manage/map">
      <template slot="title">
        <i class="el-icon-map-location"></i>
        <span slot="title">百度地图</span>
      </template>
    </el-menu-item>



  </el-menu>
</template>

<script>
export default {
  name: "Aside",
  props:{
    isCollapse: Boolean,
    logoTextShow: Boolean
  },
  methods:{
    handleSelect(index){
      // console.log(index)
      // console.log(this.$router.options.routes)
    }
  }
}
</script>

<style scoped>

</style>