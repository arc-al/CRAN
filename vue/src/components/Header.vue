<template>
  <div style="font-size: 12px; line-height: 60px; display: flex;" >
    <div style="flex:1; font-size: 22px ">
      <span :class="collapseBtnClass" style="cursor: pointer;  "@click="collapse()"></span>
    </div>
  </div>
</template>

<script>
export default {
  name: "Header",
  props: {
    collapseBtnClass: String,
    pathName: String

  },
  data(){
    return {
      paths: []
    }
  },
  methods:{
    collapse(){
      this.$parent.collapse();
    }
  },

}
</script>

<style scoped>

</style>