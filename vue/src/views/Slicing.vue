<template>
  <div style="padding: 20px 20px ">

    <div style="margin: 10px 0">
      <el-input style="width: 200px" suffix-icon="el-icon-search" placeholder="请输入切片ID" v-model="id"></el-input>
      <el-input style="width: 200px" suffix-icon="el-icon-message" placeholder="请输入类型" class="ml-5" v-model="type"></el-input>
      <el-input style="width: 200px" suffix-icon="el-icon-position" placeholder="请输入服务类型" class="ml-5" v-model="service"></el-input>
      <el-button class="ml-5" type="primary" @click="load" style="margin-top:10px ">搜索</el-button>
      <el-button class="ml-5" type="primary" @click="reset">重置</el-button>
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
      <el-table-column prop="id" label="切片Id" width="80" align="center">
        <template slot-scope="scope">
          nsi-sg-{{scope.row.id}}
        </template>
      </el-table-column>
      <el-table-column prop="type" label="切片类型" width="80" align="center"></el-table-column>
      <el-table-column prop="service" label="服务类型" width="200" align="center"></el-table-column>
      <el-table-column prop="connSla" label="绑定SLA ID" width="150" align="center">
        <template slot-scope="scope">
          sla-sg-{{scope.row.connSla}}
        </template>
      </el-table-column>
      <el-table-column prop="state" label="切片状态" width="150" align="center"></el-table-column>
      <el-table-column prop="message" label="备注"  align="center"></el-table-column>
      <el-table-column prop="operation" label="操作" align="center" width="200">
        <template slot-scope="scope">
          <el-button type="primary" @click="handleEdit(scope.row)">编辑<i class="el-icon-edit" style="margin-left: 2px"></i></el-button>
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
    <el-dialog title="基站信息" :visible.sync="dialogFormVisible" width="50%" >
      <el-form label-width="80px" size="small" style="margin-right: 30px">
        <el-form-item label="切片类型" label-width="100px">
          <el-select v-model="form.type" autocomplete="off" placeholder="请选择切片类型" style="width: 100%">
            <el-option v-for="item in typeOptions" :value="item.label" :key="item.value" :label="item.label">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="绑定的SLA ID" label-width="100px">
          <el-input v-model="form.connSla" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="服务类型" label-width="100px">
          <el-select v-model="form.service" autocomplete="off" placeholder="请选择切片类型" style="width: 100%">
            <el-option v-for="item in serviceOptions" :value="item.label" :key="item.value" :label="item.label">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="备注" label-width="100px">
          <el-input v-model="form.message" autocomplete="off"></el-input>
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
  name: "Slicing",
  data(){
    return {
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 5,
      id: "",
      type: "",
      service: "",
      typeOptions: [
        {
          value: 1,
          label: "URLLC"
        },
        {
          value: 2,
          label: "eMBB"
        },
        {
          value: 3,
          label: "mMTC"
        }
      ],
      serviceOptions: [
        {
          value: 1,
          label: "智能分布式配电自动化"
        },
        {
          value: 2,
          label: "毫米级精准负荷控制"
        },
        {
          value: 3,
          label: "低压用电信息采集"
        },
        {
          value: 4,
          label: "分布式电源"
        },
        {
          value: 5,
          label: "视频监控业务"
        }
      ],
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
      this.request.get("slicing/page", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          id: this.id,
          type: this.type,
          service: this.service,
        }
      }).then(res=>{
        this.tableData = res.records
        // console.log(res)
        this.total = res.total
      })
    },
    reset(){
      this.id = ""
      this.type = ""
      this.service = ""
      this.load()

    },
    handleadd(){
      this.dialogFormVisible = true
      this.form = {}
    },
    save(){
      this.request.post("slicing", this.form).then(res=>{
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
      this.request.delete("slicing/"+id).then(res=>{
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
      this.request.post("slicing/deleteBatch", ids).then(res=>{
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