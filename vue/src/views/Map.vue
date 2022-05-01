<template>
  <div style="width: 100%">
    <el-button round @click="addBS" style="margin: 10px 10px " size="medium ">添加基站</el-button>
    <el-button round @click="addTerminal" style="margin: 10px 10px " size="medium ">添加终端</el-button>
    <el-button round @click="getPosition" style="margin: 10px 10px " size="medium ">查看经纬度</el-button>
    <el-button round @click="addTemplate" style="margin: 10px 10px " size="medium ">实例导入</el-button>
    <el-button round @click="getCover" style="margin: 10px 10px " size="medium ">添加覆盖</el-button>
    <el-button round @click="postCover" style="margin: 10px 10px " size="medium ">提交覆盖</el-button>
    <el-button round @click="clearcover" style="margin: 10px 10px " size="medium ">清空覆盖</el-button>
    <el-divider></el-divider>
    <div id="container" style="width: 100%; height: calc(91vh)"  ></div>

    <el-dialog title="实例导入" :visible.sync="dialogFormVisible" width="30%" >
      <template>
        <div style="margin: 30px 30px">
          <el-radio v-model="radio" label="1" >默认模板</el-radio>
        </div>
        <div style="margin: 30px 30px">
          <el-radio v-model="radio" label="2">导入文件</el-radio>
          <el-upload
              class="upload-demo"
              ref="bsUpload"
              action="http://localhost:8090/file/base-station/upload"
              :on-preview="handlePreview"
              :on-remove="handleRemove"
              :before-remove="beforeRemove"
              :auto-upload="false"
              multiple
              :limit="1"
              :on-exceed="handleExceed"
              :file-list="bsList"
              style="margin: 30px 23px"
              v-show="isShow"
          >
            <el-button size="small" type="primary">上传基站模板文件</el-button>
            <div slot="tip" class="el-upload__tip">（只能上传xls文件，且不超过500kb）</div>
          </el-upload>

          <el-upload
              class="upload-demo"
              ref="terminalUpload"
              action="http://localhost:8090/file/terminal/upload"
              :on-preview="handlePreview"
              :on-remove="handleRemove"
              :before-remove="beforeRemove"
              :auto-upload="false"
              multiple
              :limit="1"
              :on-exceed="handleExceed"
              :file-list="terminalList"
              style="margin: 30px 23px"
              v-show="isShow"
          >
            <el-button size="small" type="primary">上传终端模板文件</el-button>
            <div slot="tip" class="el-upload__tip">（只能上传xls文件，且不超过500kb）</div>
          </el-upload>

          <el-upload
              class="upload-demo"
              ref="mecUpload"
              action="http://localhost:8090/file/mec/upload"
              :on-preview="handlePreview"
              :on-remove="handleRemove"
              :before-remove="beforeRemove"
              :auto-upload="false"
              multiple
              :limit="1"
              :on-exceed="handleExceed"
              :file-list="meclList"
              style="margin: 30px 23px"
              v-show="isShow"
          >
            <el-button size="small" type="primary">上传MEC模板文件</el-button>
            <div slot="tip" class="el-upload__tip">（只能上传xls文件，且不超过500kb）</div>
          </el-upload>
        </div>
      </template>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="save" >确 定</el-button>
        <el-button @click="dialogFormVisible = false">取 消</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import baseStation from '../assets/基站1.png'
import terminal from '../assets/终端.png'
import mec from '../assets/mec.png'

export default {
  name: "Map",
  data(){
    let chart;
    return {
      nodeData: {},
      nodeConnData: {},
      radio: '1',
      total: 0,
      nodeConnDataTotal: {},
      bsList: [],
      terminalList: [],
      meclList: [],
      isShow: false,
      caseNum: 0,
      dialogFormVisible: false,
      isAddBs: false,
      isAddTerminal: false,
      isCover:false,
      map: {},
      bsObject: {},
      terminalObject: {},
      CoverObject:{},
      longitude:[],//覆盖四个经纬度
      latitude:[],
      coverdata:[[]],//覆盖数据
      row:0,    //覆盖格子长宽
      colum:0,
      latitudeUnit:0,//覆盖格子最小单位
      longitudeUnit:0,
      getPos: false,
      clickCount: 0,
      chart,
    }
  },
  async mounted() {
    //隐藏顶栏
    this.$store.commit('SetIsHeaderShowFalse');
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

    await this.mapDeploy();


    //监听Esc按键，移除添加基站等按钮的焦点等
    let self = this;
    this.$nextTick(function () {
      document.addEventListener('keyup', function (e) {
        //此处填写你的业务逻辑即可
        if (e.keyCode == 27) {
          self.isAddBs = self.isAddBs?!self.isAddBs:self.isAddBs;
          self.isAddTerminal = self.isAddTerminal?!self.isAddTerminal:self.isAddTerminal;
          self.getPos = self.getPos?!self.getPos:self.getPos;
        }
      })
    })


  },
  methods: {
    submitUpload(){
      this.$refs.upload.submit();
    },
    addPoints(id,longitude,latitude,img,nodename){
      var point = new BMap.Point(longitude, latitude);
      var myIcon = new BMap.Icon(img, new BMap.Size(50, 52));
      // 创建标注对象并添加到地图
      var marker = new BMap.Marker(point, {
        icon: myIcon,
        enableDragging: true
      });
      marker.addEventListener('dragend', () => {
        var nowPoint = marker.getPosition(); // 拖拽完成之后坐标的位置
        var tempObject = {};
        tempObject.longitude = nowPoint.lng;
        tempObject.latitude = nowPoint.lat;
        tempObject.id=id;
        this.request.post(nodename, tempObject).then(res=>{
          if(res){
            this.$message.success("修改位置成功！")
          } else {
            this.$message.error("修改位置失败！")
          }
        });
      });
      return marker
    },
    getData(nodename){
      return this.request.get(nodename).then(res=>{
        this.nodeData = res
        this.total = res.length
      })
    },
    getNodeConnData(nodename){
      return this.request.get(nodename).then(res=>{
        this.nodeConnData = res
        this.nodeConnDataTotal = res.length
      })
    },
    async mapDeploy(){
      //将基站的点部署在地图上
      await this.getData("base-station")
      var i=0;
      while (i<this.total){
        this.map.addOverlay(this.addPoints(this.nodeData[i].id,this.nodeData[i].longitude,this.nodeData[i].latitude,baseStation,"base-station"));
        i++;
      }
      //将终端的点部署在地图上
      await this.getData("terminal")
      i=0;
      while (i<this.total){
        this.map.addOverlay(this.addPoints(this.nodeData[i].id,this.nodeData[i].longitude,this.nodeData[i].latitude,terminal,"terminal"));
        i++;
      }
      //将MEC的点部署再地图上
      await this.getData("mec")
      i=0;
      while (i<this.total){
        this.map.addOverlay(this.addPoints(this.nodeData[i].id,this.nodeData[i].longitude,this.nodeData[i].latitude,mec,"mec"));
        i++;
      }
      //绘制mec与基站之间的连接关系
      await this.getData("base-station")
      await this.getNodeConnData("mec")
      i=0;
      var j = 0;
      while (i<this.total){
        j=0;
        while (j<this.nodeConnDataTotal){
          if(this.nodeData[i].connMecId == this.nodeConnData[j].id){
            var polyline = new BMap.Polyline([ new BMap.Point(this.nodeData[i].longitude,this.nodeData[i].latitude),
            new BMap.Point(this.nodeConnData[j].longitude,this.nodeConnData[j].latitude)], {
              strokeColor : "black",
                  strokeWeight : 1,
                  strokeOpacity : 0.5
            }); //创建折线
            this.map.addOverlay(polyline)
          }
          j++
        }
        i++;
      }

    },
    addBS(){
      this.isAddBs = !this.isAddBs;
    },
    addTerminal(){
      this.isAddTerminal = !this.isAddTerminal;
    },
    getCover(){
      this.isCover=!this.isCover;
    },
    async postCover(){
      await this.request.get("base-station/cover",{
        params:{
          longitude1:this.longitude[0],
          longitude2:this.longitude[1],
          latitude1:this.latitude[0],
          latitude2:this.latitude[1]
        }
      }).then(res=>{
        if(res[0].length!=0){
          this.$message.success("提交区域成功！")
          this.coverdata=res[0];
          this.row=res[1];
          this.colum=res[2];
          this.latitudeUnit=res[3];
          this.longitudeUnit=res[4];
        } else {
          this.$message.error("提交区域失败！")
        }
      })
      var allOverlay = this.map.getOverlays(); //去除区域
      allOverlay.map(item => {
        if(item.name === 'line') {
          this.map.removeOverlay(item)
        }
      })
      //记录区域边缘
      var latExtent = [this.longitude[0], this.longitude[1]];
      var lngExtent = [this.latitude[1],this.latitude[0]];
      //清空数组
      this.longitude.length=0;
      this.latitude.length=0;
      //绘制覆盖图
      let dom = document.getElementById("container");
      this.chart = echarts.init(dom);
      var data=this.coverdata;
      var option;
      var COLORS = ['#070093', '#1c3fbf', '#1482e5', '#70b4eb', '#b4e0f3', '#ffffff'];
      var cellCount = [this.row, this.colum];
      var cellSizeCoord = [
        this.latitudeUnit,this.longitudeUnit
      ];
      var gapSize = 0;
      // prettier-ignore
      function renderItem(params, api) {
        var context = params.context;
        var lngIndex = api.value(1);
        var latIndex = api.value(0);
        var coordLeftTop = [
          +(latExtent[0] + lngIndex * cellSizeCoord[0]).toFixed(6),
          +(lngExtent[0] + latIndex * cellSizeCoord[1]).toFixed(6) ];
        var pointLeftTop = getCoord(params, api, lngIndex, latIndex);
        var pointRightBottom = getCoord(params, api, lngIndex + 1,
            latIndex + 1);

        return {
          type : 'rect',
          shape : {
            x : pointLeftTop[0],
            y : pointLeftTop[1],
            width : pointRightBottom[0] - pointLeftTop[0],
            height : pointRightBottom[1] - pointLeftTop[1]
          },
          style : api.style({
            stroke : 'rgba(0,0,0,0.1)'
          }),
          styleEmphasis : api.styleEmphasis()
        };
      }
      function getCoord(params, api, lngIndex, latIndex) {
        var coords = params.context.coords || (params.context.coords = []);
        var key = lngIndex + '-' + latIndex;
        // bmap returns point in integer, which makes cell width unstable.
        // So we have to use right bottom point.
        return coords[key]
            || (coords[key] = api.coord([
              +(latExtent[0] + latIndex * cellSizeCoord[1])
                  .toFixed(8),
              +(lngExtent[0] + lngIndex * cellSizeCoord[0])
                  .toFixed(8) ]));
      }
      option = {
        tooltip : {},
        visualMap : {//设置图例
          type : 'piecewise',
          inverse : true,
          top : 250,//图例的位置
          left : 10,
          pieces : [ {
            value : 0,
            color : COLORS[0],
            label:"-40~0dB"
          }, {
            value : 1,
            color : COLORS[1],
            label:"-60~-40dB"
          }, {
            value : 2,
            color : COLORS[2],
            label:"-80~-60dB"
          }, {
            value : 3,
            color : COLORS[3],
            label:"-100~-80dB"
          }, {
            value : 4,
            color : COLORS[4],
            label:"-120~-100dB"
          }, {
            value : 5,
            color : COLORS[5],
            label:"<-120dB"
          } ],
          borderColor : '#ccc',
          borderWidth : 2,
          //show : false,//关闭图例
          backgroundColor : '#eee',
          dimension : 2,
          inRange : {
            color : COLORS,
            opacity : 0.5
          }
        },
        series : [ {
          type : 'custom',
          coordinateSystem : 'bmap',
          renderItem : renderItem,
          animation : false,
          itemStyle : {
            emphasis : {
              color : 'yellow'
            }
          },
          encode : {
            tooltip : 2
          },
          data : data
        } ],
        bmap : {
          center : [(latExtent[0]+latExtent[1])/2,(lngExtent[0]+lngExtent[0])/2],
          zoom : 14,
          roam : true
        }
      };
      this.chart.setOption(option);
    },
    clearcover(){
      if(!this.chart.isDisposed()){
        this.chart.clear();//清空该实例的组件和图表
        this.coverdata=[[]];
      }
    },
    handleBSClick(e){
      this.map.addOverlay(this.addPoints("",e.point.lng, e.point.lat,baseStation,"base-station"));
      this.bsObject.longitude=e.point.lng;
      this.bsObject.latitude=e.point.lat;
      this.request.post("base-station", this.bsObject).then(res=>{
        if(res){
          this.$message.success("添加基站成功！")
        } else {
          this.$message.error("添加基站失败！")
        }
      })
    },
    handleTerminalClick(e){
      this.map.addOverlay(this.addPoints("",e.point.lng, e.point.lat,terminal,"terminal"));
      this.terminalObject.longitude=e.point.lng;
      this.terminalObject.latitude=e.point.lat;
      this.request.post("terminal", this.terminalObject).then(res=>{
        if(res){
          this.$message.success("添加终端成功！")
        } else {
          this.$message.error("添加终端失败！")
        }
      })
    },
    handleClickPosCover(e){
      this.longitude.push(e.point.lng);
      this.latitude.push(e.point.lat);
      this.clickCount++;
    },
    getPosition(){
      this.getPos = !this.getPos;
    },
    handleClickPos(e){
      alert("经度:"+e.point.lng+", 纬度:"+e.point.lat)
    },
    addTemplate(){
      this.dialogFormVisible = !this.dialogFormVisible
    },
    async save() {
      this.dialogFormVisible = false;
      if (this.radio == 1) {
        this.request.post("case-set").then(res => {
          if (res) {
            this.$message.success("成功导入")
          } else {
            this.$message.error("导入失败")
          }
        })
      } else {
        this.$refs.bsUpload.submit();
        this.$refs.terminalUpload.submit();
        this.$refs.mecUpload.submit();
        this.$message.success("导入文件成功")
      }
      // await this.mapDeploy();
      this.$router.go(0)  //刷新页面，这个地方不知道用什么方法可以实现网络请求后的标记点添加
    },
    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    handlePreview(file) {
      console.log(file);
    },
    handleExceed(files, fileList) {
      this.$message.warning(`当前限制选择 3 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
    },
    beforeRemove(file, fileList) {
      return this.$confirm(`确定移除 ${ file.name }？`);
    },
    submit(){

    }
  },
  watch: {
    'isAddBs':{
      handler: function (val) {
        if(val){
          this.map.addEventListener('click', this.handleBSClick);
          this.map.setDefaultCursor("crosshair");
        } else {
          this.map.removeEventListener('click', this.handleBSClick);
          this.map.setDefaultCursor("pointer");
        }
      },
    },
    'isAddTerminal':{
      handler: function (val) {
        if(val){
          this.map.addEventListener('click', this.handleTerminalClick);
          this.map.setDefaultCursor("crosshair");
        } else {
          this.map.removeEventListener('click', this.handleTerminalClick);
          this.map.setDefaultCursor("pointer");
        }
      },
    },
    'getPos':{
      handler: function (val) {
        if(val){
          this.map.addEventListener('click', this.handleClickPos);
          this.map.setDefaultCursor("crosshair");
        } else {
          this.map.removeEventListener('click', this.handleClickPos);
          this.map.setDefaultCursor("pointer");
        }
      },
    },
    'radio': {
      handler: function (val) {
        if(val == 1){
          this.isShow = false;
        }else{
          this.isShow = true;
        }
      }
    },
    'isCover':{
      handler: function (val) {
        if(val){
          this.map.addEventListener('click', this.handleClickPosCover);
          this.map.setDefaultCursor("crosshair");
        } else {
          this.map.removeEventListener('click', this.handleClickPosCover);
          this.map.setDefaultCursor("pointer");
        }
      },
    },
    "clickCount":{
      handler: function(val){
        if(val==2){
          this.map.removeEventListener('click', this.handleClickPosCover);
          this.map.setDefaultCursor("pointer");
          var polygon = new BMap.Polygon([
            new BMap.Point(this.longitude[0],this.latitude[0]),
            new BMap.Point(this.longitude[0],this.latitude[1]),
            new BMap.Point(this.longitude[1],this.latitude[1]),
            new BMap.Point(this.longitude[1],this.latitude[0]),
          ], {strokeColor:"blue", strokeWeight:2, strokeOpacity:0.5});   //创建折线
          polygon.name = 'line'
          this.map.addOverlay(polygon);   //增加折线
        }

      }
    }
  },
  beforeRouteLeave(to, from, next){
    //显示顶栏
    this.$store.commit('SetIsHeaderShowTrue');
    next()
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
.el-divider--horizontal{
  margin: 7px 0
}
</style>