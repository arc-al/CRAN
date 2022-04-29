<template>
  <div style="padding: 20px 20px ">
    <el-table :data="tableData"
              border
              stripe
              :row-class-name="tableRowClassName"
              :header-cell-class-name="headerBg"
              highlight-current-row
              @row-click="onRowClick">
      <el-table-column width="60" align="center" label="">
        <template slot-scope="scope">
          <el-radio :label="scope.$index" v-model="radio">{{""}}</el-radio>
        </template>
      </el-table-column>
      <el-table-column prop="typicalName" label="典型业务名称" width="250" align="center"></el-table-column>
      <el-table-column prop="uplinkBw" label="上行带宽需求"  align="center">
        <template slot-scope="scope">
          <el-select v-model="scope.row.uplinkBw" autocomplete="off" placeholder="请选择上行带宽需求" style="width: 100%">
            <el-option v-for="item in selectoptions" :value="item.label" :key="item.value" :label="item.label"></el-option>
          </el-select>
        </template>
      </el-table-column>
      <el-table-column prop="downlinkBw" label="下行带宽需求"  align="center">
        <template slot-scope="scope">
          <el-select v-model="scope.row.downlinkBw" autocomplete="off" placeholder="请选择下行带宽需求" style="width: 100%">
            <el-option v-for="item in selectoptions" :value="item.label" :key="item.value" :label="item.label"></el-option>
          </el-select>
        </template>
      </el-table-column>
      <el-table-column prop="eteDelay" label="端到端时延需求"  align="center">
        <template slot-scope="scope">
          <el-select v-model="scope.row.eteDelay" autocomplete="off" placeholder="请选择端到端时延需求" style="width: 100%">
            <el-option v-for="item in selectoptions" :value="item.label" :key="item.value" :label="item.label"></el-option>
          </el-select>
        </template>
      </el-table-column>
      <el-table-column prop="resIsoLevel" label="资源隔离等级"  align="center">
        <template slot-scope="scope">
          <el-select v-model="scope.row.resIsoLevel" autocomplete="off" placeholder="请选择资源隔离等级" style="width: 100%">
            <el-option v-for="item in selectoptions" :value="item.label" :key="item.value" :label="item.label"></el-option>
          </el-select>
        </template>
      </el-table-column>
      <el-table-column prop="secIsoLevel" label="安全隔离等级"  align="center">
        <template slot-scope="scope">
          <el-select v-model="scope.row.secIsoLevel" autocomplete="off" placeholder="请选择安全隔离等级" style="width: 100%">
            <el-option v-for="item in selectoptions" :value="item.label" :key="item.value" :label="item.label"></el-option>
          </el-select>
        </template>
      </el-table-column>
      <el-table-column prop="priority" label="业务优先级"  align="center">
        <template slot-scope="scope">
          <el-select v-model="scope.row.priority" autocomplete="off" placeholder="请选择业务优先级" style="width: 100%">
            <el-option v-for="item in selectoptions" :value="item.label" :key="item.value" :label="item.label"></el-option>
          </el-select>
        </template>
      </el-table-column>
    </el-table>

    <div style="height: 100px;" class="match">
      <el-button type="primary" style="width: 100px" @click="matchSlicing">匹配</el-button>
    </div>
    <hr/>

    <el-descriptions title="匹配结果分析">
      <el-descriptions-item label="匹配建议">经过需求匹配分析，建议您使用 <span style="color: crimson">{{bestSuggest}}</span> 类型的切片承载该业务，可使用的所有切片如下：</el-descriptions-item>
    </el-descriptions>

    <el-table :data="matchTableData" border stripe :header-cell-class-name="headerBg" >
      <el-table-column prop="id" label="切片Id" width="80" align="center">
        <template slot-scope="scope">
          nsi-sg-{{scope.row.id}}
        </template>
      </el-table-column>
      <el-table-column prop="type" label="切片类型" align="center"></el-table-column>
      <el-table-column prop="service" label="服务类型"  align="center"></el-table-column>
      <el-table-column prop="connSla" label="绑定SLA ID"  align="center">
        <template slot-scope="scope">
          sla-sg-{{scope.row.connSla}}
        </template>
      </el-table-column>
      <el-table-column prop="state" label="切片状态"  align="center"></el-table-column>
      <el-table-column prop="message" label="备注"  align="center"></el-table-column>
      <el-table-column prop="operation" label="操作" align="center" width="200">
        <template slot-scope="scope">
          <el-button type="primary" @click="bind(scope.row.id)">绑定</el-button>
        </template>

      </el-table-column>
    </el-table>


  </div>
</template>

<script>
export default {
  name: "selectSla",
  data(){
    return {
      tableData: [],
      radio: '',
      matchTableData: [],
      total: 0,
      bestSuggest: '',
      selectoptions: [
        {value: 0, label: "低"},
        {value: 1, label: "中低"},
        {value: 2, label: "中"},
        {value: 3, label: "中高"},
        {value: 4, label: "高"},
      ],
      multipleSelection: [],
      dialogFormVisible: false,
      headerBg:"headerBg"
    }
  },
  methods: {
    load(){
      this.request.get("typical-business").then(res=>{
        this.tableData = res
        this.total = res.total
      })
      if(this.multipleSelection.length!=0){
        this.request.post("sla/selectSla",this.multipleSelection).then(res=>{
          if(res[0].length==0){
            this.matchTableData = '';
          }else{
            this.matchTableData = res[0];
            console.log(this.matchTableData)
            this.bestSuggest = res[1]
          }
        })
      }

    },
    handleadd(){
      this.dialogFormVisible = true
      this.form = {}
    },
    tableRowClassName({row, rowIndex}) {
      row.index = rowIndex
    },
    matchSlicing(){
      this.request.post("sla/selectSla",this.multipleSelection).then(res=>{
        if(res[0].length==0){
          this.$message.error("匹配完成，未发现匹配项");
          this.matchTableData = '';
        }else{
          this.$message.success("匹配完成")
          this.matchTableData = res[0];
          this.bestSuggest = res[1]
        }
      })
    },
    onRowClick(row) {
      this.radio = row.index
      this.multipleSelection = row
      delete this.multipleSelection.index
    },
    bind(id){
      this.request.get("slicing/bindSlicing",{
        params:{
          businessName: this.multipleSelection.typicalName,
          slicingId: id
        }
      }).then(res=>{
        if(res){
          this.$message.success("绑定成功");
        }else{
          this.$message.error("绑定失败")
        }
      })
      setTimeout(()=>{
        this.load()
      },200)
    },
  },
  created() {
    //请求分页查询数据
    this.load()
  }
}
</script>

<style scoped>
.headerBg {
  background-color: #eee!important;
}
.el-dropdown-link {
  cursor: pointer;
  color: #409EFF;
}
.el-icon-arrow-down {
  font-size: 12px;
}
.match{
  /*flex 布局*/
  display: flex;
  /*实现垂直居中*/
  align-items: center;
  /*实现水平居中*/
  justify-content: center;
}
</style>