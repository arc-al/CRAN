<template>
  <div>
    <div id="container" style="width: 100%; height: calc(88vh)"></div>
  </div>
</template>

<script>
import basestation from '../assets/基站1.png'
import terminal from '../assets/终端.png'
import mec from '../assets/mec.png'

export default {
  name: "Map",
  data(){
    return {
      nodeData: {},
      total: 0,
    }
  },
  async mounted() {
    //加载地图
    var map = new BMap.Map("container");
    var point = new BMap.Point(113.635904,34.752442);
    map.centerAndZoom(point, 15);
    map.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放
    //添加地图控件
    map.addControl(new BMap.NavigationControl());
    map.addControl(new BMap.ScaleControl());
    map.addControl(new BMap.OverviewMapControl());
    map.addControl(new BMap.MapTypeControl());
    //将基站的点部署在地图上
    await this.getData("base-station")
    var i=0;
    while (i<this.total){
      map.addOverlay(this.addPoints(this.nodeData[i].longitude,this.nodeData[i].latitude,basestation));
      i++;
    }
    //将终端的点部署在地图上
    await this.getData("terminal")
    i=0;
    while (i<this.total){
      map.addOverlay(this.addPoints(this.nodeData[i].longitude,this.nodeData[i].latitude,terminal));
      i++;
    }
    //将MEC的点部署再地图上
    await this.getData("mec")
    i=0;
    while (i<this.total){
      map.addOverlay(this.addPoints(this.nodeData[i].longitude,this.nodeData[i].latitude,mec));
      i++;
    }
    //绘制mec与基站之间的连接关系

    //绘制基站与终端之间的连接关系

    //周期刷新所有节点的位置
  },
  methods: {
    addPoints(longitude,latitude,img){
      var point = new BMap.Point(longitude, latitude);
      var myIcon = new BMap.Icon(img, new BMap.Size(50, 52));
      // 创建标注对象并添加到地图
      var marker = new BMap.Marker(point, {icon: myIcon});
      return marker
    },
    getData(nodename){
      return this.request.get(nodename).then(res=>{
        this.nodeData = res
        this.total = res.length
      })
    }
  }
}
</script>

<style scoped>
/* 隐藏百度图片Logo */
/deep/.BMap_cpyCtrl {
  display:none;
}

/* 隐藏文字信息 */
/deep/.anchorBL{
  display:none;
}
</style>