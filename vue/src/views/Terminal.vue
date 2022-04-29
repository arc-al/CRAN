<template>
  <div style="padding: 20px 20px ">
    <div style="margin: 10px 0">
      <el-input style="width: 200px" suffix-icon="el-icon-search" placeholder="请输入终端ID" v-model="id"></el-input>
      <el-input style="width: 200px" suffix-icon="el-icon-message" placeholder="请输入终端业务类型" class="ml-5" v-model="businessType"></el-input>
      <el-input style="width: 200px" suffix-icon="el-icon-position" placeholder="请输入经度" class="ml-5" v-model="longitude"></el-input>
      <el-input style="width: 200px" suffix-icon="el-icon-position" placeholder="请输入经度" class="ml-5" v-model="latitude"></el-input>
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
      <el-table-column prop="id" label="终端Id" width="80" align="center">
        <template slot-scope="scope">
          UE-{{scope.row.id}}
        </template>
      </el-table-column>
      <el-table-column prop="longitude" label="经度" width="150" align="center"></el-table-column>
      <el-table-column prop="latitude" label="纬度" width="150" align="center"></el-table-column>
      <el-table-column prop="businessType" label="业务类型" width="200" align="center"></el-table-column>
      <el-table-column prop="connSlicing" label="关联切片" width="120" align="center">
        <template slot-scope="scope">
          nsi-sg-{{scope.row.connSlicing}}
        </template>
      </el-table-column>
      <el-table-column prop="connBs" label="关联基站"  align="center">
        <template slot-scope="scope">
          gNB-{{scope.row.connBs}}
        </template>
      </el-table-column>
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
    <el-dialog title="终端信息" :visible.sync="dialogFormVisible" width="30%" >
      <el-form label-width="80px" size="small" style="margin-right: 30px">
          <el-form-item label="经度" >
            <el-input v-model="form.longitude" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="维度" >
            <el-input v-model="form.latitude" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="终端类型" >
            <el-select v-model="form.businessType" autocomplete="off" placeholder="请选择终端类型" style="width: 100%">
              <el-option v-for="item in businessTypeOptions" :value="item.label" :key="item.value" :label="item.label">
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
      businessType: "",
      connSlicing: "",
      connBs: "",
      longitude: "",
      latitude: "",
      businessTypeOptions: [
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
      this.request.get("terminal/page", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          id: this.id,
          businessType: this.businessType,
          longitude: this.longitude,
          latitude: this.latitude
        }
      }).then(res=>{
        this.tableData = res.records
        this.total = res.total
      })
    },
    reset(){
      this.id = ""
      this.businessType = ""
      this.connSlicing = ""
      this.connBs  = ""
      this.longitude = ""
      this.latitude = ""
      this.load()

    },
    handleadd(){
      this.dialogFormVisible = true
      this.form = {}
    },
    save(){
      this.request.post("terminal", this.form).then(res=>{
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
      this.request.delete("terminal/"+id).then(res=>{
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
      this.request.post("terminal/deleteBatch", ids).then(res=>{
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