<template>
  <div>
    <el-table :data="tableData" border stripe :header-cell-class-name="headerBg" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column prop="typicalName" label="典型业务名称" width="120" align="center"></el-table-column>
      <el-table-column prop="uplinkBw" label="上行带宽需求" width="120" align="center">
        <el-select v-model="form.type" autocomplete="off" placeholder="请选择SLA站型" style="width: 100%">
          <el-option v-for="item in typeOptions" :value="item.label" :key="item.value" :label="item.label">
          </el-option>
        </el-select>
      </el-table-column>
      <el-table-column prop="downlinkBw" label="下行带宽需求" width="150" align="center"></el-table-column>
      <el-table-column prop="eteDelay" label="端到端时延需求" width="150" align="center"></el-table-column>
      <el-table-column prop="resIsoLevel" label="资源隔离等级"  align="center"></el-table-column>
      <el-table-column prop="secIsoLevel" label="安全隔离等级"  align="center"></el-table-column>
      <el-table-column prop="pirority" label="业务优先级"  align="center"></el-table-column>
    </el-table>
    <div style="padding: 10px 0">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          :page-sizes="[5, 10, 15, 20]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>

    </div>
    <el-dialog title="SLA信息" :visible.sync="dialogFormVisible" width="40%" >
      <el-form label-width="80px" size="small" style="margin-right: 30px">
        <el-form-item label="类别" label-width="100px">
          <el-select v-model="form.type" autocomplete="off" placeholder="请选择SLA站型" style="width: 100%">
            <el-option v-for="item in typeOptions" :value="item.label" :key="item.value" :label="item.label">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="上行带宽" label-width="100px">
          <el-select v-model="form.uplinkBw" autocomplete="off" placeholder="请选择SLA上行带宽" style="width: 100%">
            <el-option v-for="item in selectoptions" :value="item.label" :key="item.value" :label="item.label">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="下行带宽" label-width="100px">
          <el-select v-model="form.downlinkBw" autocomplete="off" placeholder="请选择SLA下行带宽" style="width: 100%">
            <el-option v-for="item in selectoptions" :value="item.label" :key="item.value" :label="item.label">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="端到端时延" label-width="100px">
          <el-select v-model="form.eteDelay" autocomplete="off" placeholder="请选择SLA端到端时延" style="width: 100%">
            <el-option v-for="item in selectoptions" :value="item.label" :key="item.value" :label="item.label">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="资源隔离等级" label-width="100px">
          <el-select v-model="form.resIsoLevel" autocomplete="off" placeholder="请选择SLA资源隔离等级" style="width: 100%">
            <el-option v-for="item in selectoptions" :value="item.label" :key="item.value" :label="item.label">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="安全隔离等级" label-width="100px">
          <el-select v-model="form.secIsoLevel" autocomplete="off" placeholder="请选择SLA安全隔离等级" style="width: 100%">
            <el-option v-for="item in selectoptions" :value="item.label" :key="item.value" :label="item.label">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="save">确 定</el-button>
        <el-button @click="dialogFormVisible = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "BaseStation",
  data(){
    return {
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 5,
      id: "",
      type: "",
      area: "",
      typeOptions: [
        {value: 1, label: "URLLC"},
        {value: 2, label: "eMBB"},
        {value: 3, label: "mMTC"}
      ],
      selectoptions: [
        {value: 0, label: "低"},
        {value: 1, label: "中低"},
        {value: 2, label: "中"},
        {value: 2, label: "中高"},
        {value: 2, label: "高"},
      ],
      longitude: "",
      latitude: "",
      form: {},
      multipleSelection: [],
      dialogFormVisible: false,
      headerBg:"headerBg"
    }
  },
  methods: {
    handleSizeChange(pageSize){
      this.pageSize = pageSize,
          this.load()
    },
    handleCurrentChange(pageNum){
      this.pageNum = pageNum,
          this.load()
    },
    load(){
      this.request.get("sla/page", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          id: this.id,
          type: this.type
        }
      }).then(res=>{
        this.tableData = res.records
        this.total = res.total
      })
    },
    reset(){
      this.id = ""
      this.type = ""
      this.area = ""
      this.longitude  = ""
      this.latitude = ""
      this.load()

    },
    handleadd(){
      this.dialogFormVisible = true
      this.form = {}
    },
    save(){
      this.request.post("sla", this.form).then(res=>{
        if(res){
          this.$message.success("保存成功！")
          this.dialogFormVisible = false
          this.load()
        } else {
          this.$message.error("保存失败！")
        }
      })
    },
    handleEdit(row){
      this.form = row
      this.dialogFormVisible = true
    },
    handleDelete(id){
      this.request.delete("sla/"+id).then(res=>{
        if(res){
          this.$message.success("删除成功！")
          this.load()
        } else {
          this.$message.success("删除失败！")
        }
      })
    },
    handleSelectionChange(val){
      this.multipleSelection = val

    },
    delBatch(){
      let ids = this.multipleSelection.map(v => v.id)
      this.request.post("/deleteBatch", ids).then(res=>{
        if(res){
          this.$message.success("批量删除成功！")
          this.load()
        } else {
          this.$message.success("批量删除失败！")
        }
      })
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
</style>