<template>
  <div style="width: 100%">
    <el-button round @click="addBS" style="margin: 10px 10px " size="medium ">添加基站</el-button>
    <el-button round @click="getPosition" style="margin: 10px 10px " size="medium ">查看经纬度</el-button>
    <el-button round @click="addTemplate" style="margin: 10px 10px " size="medium ">实例导入</el-button>
    <div id="container" style="width: 100%; height: calc(88vh)"  ></div>

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
    return {
      nodeData: {},
      radio: '1',
      total: 0,
      bsList: [],
      terminalList: [],
      meclList: [],
      isShow: false,
      caseNum: 0,
      dialogFormVisible: false,
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

    await this.mapDeploy();
    //绘制mec与基站之间的连接关系

    //绘制基站与终端之间的连接关系

    //周期刷新所有节点的位置

    //监听Esc按键，移除添加基站等按钮的焦点等
    let self = this;
    this.$nextTick(function () {
      document.addEventListener('keyup', function (e) {
        //此处填写你的业务逻辑即可
        if (e.keyCode == 27) {
          self.isAddBs = self.isAddBs?!self.isAddBs:self.isAddBs;
          self.getPos = self.getPos?!self.getPos:self.isAddBs;
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
        })
      });
      return marker
    },
    getData(nodename){
      return this.request.get(nodename).then(res=>{
        this.nodeData = res
        this.total = res.length
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
    },
    addBS(){
      this.isAddBs = !this.isAddBs;
    },
    handleClick(e){
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
          this.map.addEventListener('click', this.handleClick);
          this.map.setDefaultCursor("crosshair");
        } else {
          this.map.removeEventListener('click', this.handleClick);
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
    }
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

</style>