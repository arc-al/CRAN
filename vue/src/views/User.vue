<template>
  <div>
    <div style="margin-bottom: 30px">
      <el-breadcrumb separator="/" style="margin-bottom: 20px">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item><a href="/">用户管理</a></el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div style="margin: 10px 0">
      <el-input style="width: 200px" suffix-icon="el-icon-search" placeholder="请输入名称" v-model="userName"></el-input>
      <el-input style="width: 200px" suffix-icon="el-icon-message" placeholder="请输入邮箱" class="ml-5" v-model="email"></el-input>
      <el-input style="width: 200px" suffix-icon="el-icon-position" placeholder="请输入地址" class="ml-5" v-model="address"></el-input>
      <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
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
      <el-table-column prop="id" label="Id" width="80"></el-table-column>
      <el-table-column prop="username" label="名称" width="140"></el-table-column>
      <el-table-column prop="nickname" label="昵称" width="120"></el-table-column>
      <el-table-column prop="email" label="邮箱" width="220"></el-table-column>
      <el-table-column prop="phone" label="电话" width="120"></el-table-column>
      <el-table-column prop="address" label="地址" ></el-table-column>
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
    <el-dialog title="用户信息" :visible.sync="dialogFormVisible" width="30%" >
      <el-form label-width="80px" size="small">
        <el-form-item label="用户名" >
          <el-input v-model="form.username" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="昵称" >
          <el-input v-model="form.nickname" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" >
          <el-input v-model="form.email" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="电话" >
          <el-input v-model="form.phone" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="地址" >
          <el-input v-model="form.address" autocomplete="off"></el-input>
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
  name: "User",
  data() {
    return {
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 5,
      userName: "",
      email: "",
      address: "",
      form: {},
      multipleSelection: [],
      dialogFormVisible: false,
      headerBg:'headerBg'
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
      this.request.get("http://localhost:8090/User/page", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          userName: this.userName,
          email: this.email,
          address: this.address
        }
      }).then(res=>{
        this.tableData = res.records;
        this.total = res.total;
      })
    },
    reset(){
      this.userName = ""
      this.email = ""
      this.address = ""
      this.load()

    },
    handleadd(){
      this.dialogFormVisible = true
      this.form = {}
    },
    save(){
      this.request.post("http://localhost:8090/User", this.form).then(res=>{
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
      this.request.delete(""+id).then(res=>{
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
      let ids = this.multipleSelection.map(v => v.id)  //扁平化处理  [{email:xx , id:1},{email:xx , id:2},{email:xx , id:3}] => [1,2,3]
      this.request.post("/deleteBatch", ids).then(res=>{
        if(res){
          this.$message.success("批量删除成功！")
          this.load()
        } else {
          this.$message.success("批量删除失败！")
        }
      })
    }
  },
  created() {
    //请求分页查询数据
    this.load()
  },
}
</script>

<style scoped>
.headerBg {
  background-color: #eee!important;
}
</style>