<template>
  <div>
    <el-button round @click="addBS" style="margin: 10px 10px " size="medium ">添加基站</el-button>
    <el-button round @click="getPosition" style="margin: 10px 10px " size="medium ">查看经纬度</el-button>
    <div id="container" style="width: 100%; height: calc(88vh)" class="test111"></div>
  </div>
</template>

<script>
import baseStation from '../assets/基站1.png'
import terminal from '../assets/终端.png'
import mec from '../assets/mec.png'

export default {
  name: "Map",
  data(){
    return {
      nodeData: {},
      total: 0,
      isAddBs: false,
      map: {},
      bsObject: {},
      getPos: false,
    }
  },
  async mounted() {
    //加载地图
    var map = new BMap.Map("container");
    this.map = map;
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
      map.addOverlay(this.addPoints(this.nodeData[i].longitude,this.nodeData[i].latitude,baseStation));
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
      var marker = new BMap.Marker(point, {
        icon: myIcon,
        enableDragging: true
      });
      return marker
    },
    getData(nodename){
      return this.request.get(nodename).then(res=>{
        this.nodeData = res
        this.total = res.length
      })
    },
    addBS(){
      this.isAddBs = !this.isAddBs;
    },
    handleClick(e){
      this.map.addOverlay(this.addPoints(e.point.lng, e.point.lat,baseStation));
      this.bsObject.longitude=e.point.lng;
      this.bsObject.latitude=e.point.lat;
      this.request.post("base-station", this.bsObject).then(res=>{
        if(res){
          this.$message.success("添加基站成功！")
        } else {
          this.$message.error("添加基站失败！")
        }
      })
      // console.log(e)
    },

    getPosition(){
      // this.map.addEventListener('click', function (e){
      //   alert("经度:"+e.point.lng+", 纬度:"+e.point.lat)
      // });
      this.getPos = !this.getPos;
    },
    handleClickPos(e){
      alert("经度:"+e.point.lng+", 纬度:"+e.point.lat)
    }
  },
  watch: {
    'isAddBs':{
      handler: function (val) {
        if(val){
          this.map.addEventListener('click', this.handleClick);
        } else {
          this.map.removeEventListener('click', this.handleClick);
        }
      },
    },
    'getPos':{
      handler: function (val) {
        if(val){
          this.map.addEventListener('click', this.handleClickPos);
        } else {
          this.map.removeEventListener('click', this.handleClickPos);
        }
      },
    },
  }
}
</script>

<style >
/* 隐藏百度图片Logo */
/deep/.BMap_cpyCtrl {
  display:none;
}

/* 隐藏文字信息 */
/deep/.anchorBL{
  display:none;
}
/*div{*/
/*  cursor: url('../assets/终端.png'),auto;*/
/*}*/
</style>