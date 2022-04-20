<template>
  <el-container style="height: 100%; border: 1px solid #eee">

    <el-aside :width="sideWidth+'px'" style="background-color: rgb(238, 241, 246); ">
      <Aside :isCollapse="isCollapse" :logoTextShow="logoTextShow"/>
    </el-aside>

    <el-container>
      <el-header width="100%" style=" border-bottom: 1px solid #ccc;" >
<!--        <Header :collapseBtnClass="collapseBtnClass"  />-->
        <div style="font-size: 12px; line-height: 60px; display: flex;" >
          <div style="flex:1; font-size: 22px ">
            <span :class="collapseBtnClass" style="cursor: pointer;  "@click="collapse()"></span>
          </div>
        </div>
      </el-header>

      <el-main>
        <router-view/>
      </el-main>

    </el-container>
  </el-container>
</template>

<script>
import Aside from "@/components/Aside";
import Header from "@/components/Header";

export default {
  name:'name',
  components: {
    Aside,
    Header
  },
  data(){
    return {
      collapseBtnClass:'el-icon-s-fold',
      isCollapse:false,
      sideWidth :200,
      logoTextShow :true,

    }
  },
  methods:{
    collapse(){
      this.isCollapse=!this.isCollapse;
      if(this.isCollapse){
        this.sideWidth = 64
        this.collapseBtnClass = 'el-icon-s-unfold'
        this.logoTextShow=false
      }else{
        this.sideWidth = 200
        this.collapseBtnClass = 'el-icon-s-fold'
        this.logoTextShow=true
      }
    },

  },
};
</script>
<style>
.headerBg {
  background-color: #eee!important;
}

</style>