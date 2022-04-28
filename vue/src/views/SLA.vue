<template>
  <div style="padding: 20px 20px ">
    <div style="margin: 10px 0">
      <el-input style="width: 200px" suffix-icon="el-icon-search" placeholder="请输入SLA ID" v-model="id"></el-input>
      <el-input style="width: 200px" suffix-icon="el-icon-message" placeholder="请输入类别" class="ml-5" v-model="type"></el-input>
      <el-button class="ml-5" type="primary" @click="load" style="margin-top:10px ">搜索</el-button>
      <el-button class="ml-5" type="warning" @click="reset">重置</el-button>
    </div>
    <div style="margin: 10px 0">
      <el-button type="primary" @click="handleadd">新增<i class="el-icon-circle-plus-outline" style="margin-left: 2px"></i></el-button>
      <el-popconfirm
          style="margin: 10px"
          confirm-button-text='确认'
          cancel-button-text='取消'
          icon="el-icon-info"
          icon-color="red"
          title="确认删除数据吗？"
          @confirm="delBatch">
        <el-button type="danger" slot="reference">批量删除<i class="el-icon-remove-outline" style="margin-left: 2px"></i></el-button>
      </el-popconfirm>
      <el-button type="primary">导入文件<i class="el-icon-bottom" style="margin-left: 2px"></i></el-button>
    </div>
    <el-table :data="tableData" border stripe :header-cell-class-name="headerBg" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column prop="id" label="SLA配置Id" width="100" align="center">
        <template slot-scope="scope">
          sla-sg-{{scope.row.id}}
        </template>
      </el-table-column>
      <el-table-column prop="type" label="SLA类别" width="80" align="center"></el-table-column>
      <el-table-column prop="uplinkBw" label="上行带宽" width="120" align="center"></el-table-column>
      <el-table-column prop="downlinkBw" label="下行带宽" width="150" align="center"></el-table-column>
      <el-table-column prop="eteDelay" label="端到端时延" width="150" align="center"></el-table-column>
      <el-table-column prop="resIsoLevel" label="资源隔离等级"  align="center"></el-table-column>
      <el-table-column prop="secIsoLevel" label="安全隔离等级"  align="center"></el-table-column>
      <el-table-column prop="pirority" label="优先级"  align="center"></el-table-column>
      <el-table-column prop="connSlicNum" label="绑定切片个数"  align="center"></el-table-column>
      <el-table-column prop="message" label="备注"  align="center"></el-table-column>
      <el-table-column prop="operation" label="操作" align="center" width="200">
        <template slot-scope="scope">
          <el-button type="success" @click="handleEdit(scope.row)">编辑<i class="el-icon-edit" style="margin-left: 2px"></i></el-button>
          <el-popconfirm
              class="ml-5"
              confirm-button-text='确认'
              cancel-button-text='取消'
              icon="el-icon-info"
              icon-color="red"
              title="确认删除数据吗？"
              @confirm="handleDelete(scope.row.id)">
            <el-button type="danger" slot="reference">删除<i class="el-icon-close" style="margin-left: 2px"></i></el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
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
        {value: 3, label: "中高"},
        {value: 4, label: "高"},
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
          console.log(this.form)
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
      this.request.post("sla/deleteBatch", ids).then(res=>{
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
</style>