<template>
  <div style="padding: 20px 20px ">

    <div style="margin: 10px 0">
      <el-input style="width: 200px" suffix-icon="el-icon-search" placeholder="请输入基站ID" v-model="id"></el-input>
      <el-input style="width: 200px" suffix-icon="el-icon-message" placeholder="请输入基站站型" class="ml-5" v-model="type"></el-input>
      <el-input style="width: 200px" suffix-icon="el-icon-position" placeholder="请输入基站片区" class="ml-5" v-model="area"></el-input>
      <el-input style="width: 200px" suffix-icon="el-icon-position" placeholder="请输入基站经度" class="ml-5" v-model="longitude"></el-input>
      <el-input style="width: 200px" suffix-icon="el-icon-position" placeholder="请输入基站纬度" class="ml-5" v-model="latitude"></el-input>
      <el-button class="ml-5" type="primary" @click="load" style="margin-top:10px ">搜索</el-button>
      <el-button class="ml-5" type="primary" @click="reset">重置</el-button>
    </div>
    <div style="margin: 10px 0">
      <el-button type="primary" @click="handleadd" icon="el-icon-circle-plus-outline">新增<i class="el-icon-circle-plus-outline" style="margin-left: 2px"></i></el-button>
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
      <el-table-column type="selection" ></el-table-column>
      <el-table-column prop="id" label="基站Id"  align="center">
        <template slot-scope="scope">
          gNB-{{scope.row.id}}
        </template>
      </el-table-column>
      <el-table-column prop="type" label="基站站型"  align="center"></el-table-column>
      <el-table-column prop="area" label="基站片区"  align="center"></el-table-column>
      <el-table-column prop="longitude" label="经度"  align="center"></el-table-column>
      <el-table-column prop="latitude" label="纬度"  align="center"></el-table-column>
      <el-table-column prop="connTerminalNum" label="连接终端数量"  align="center"></el-table-column>
      <el-table-column prop="connMecId" label="关联的MEC ID"  align="center">
        <template slot-scope="scope">
          mec-{{scope.row.connMecId}}
        </template>
      </el-table-column>
      <el-table-column prop="pci" label="PCI"  align="center"></el-table-column>
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
    <el-dialog title="基站信息" :visible.sync="dialogFormVisible" width="30%" >
      <el-form label-width="80px" size="small" style="margin-right: 30px">
        <el-form-item label="经度" >
          <el-input v-model="form.longitude" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="维度" >
          <el-input v-model="form.latitude" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="站型" >
          <el-select v-model="form.type" autocomplete="off" placeholder="请选择基站站型" style="width: 100%">
            <el-option v-for="item in typeOptions" :value="item.label" :key="item.value" :label="item.label">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="片区" >
          <el-select v-model="form.area" autocomplete="off" placeholder="请选择基站片区" style="width: 100%">
            <el-option v-for="item in areaOptions" :value="item.label" :key="item.value" :label="item.label">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="PCI" >
          <el-input v-model="form.pci" autocomplete="off"></el-input>
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
        {
          value: 1,
          label: "室分"
        },
        {
          value: 2,
          label: "宏站"
        }
      ],
      areaOptions: [
        {
          value: 0,
          label: "管城区"
        },
        {
          value: 1,
          label: "金水区"
        },
        {
          value: 2,
          label: "二七区"
        },
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
      this.request.get("base-station/page", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          id: this.id,
          type: this.type,
          area: this.area,
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
      this.request.post("base-station", this.form).then(res=>{
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
      this.request.delete("base-station/"+id).then(res=>{
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
      this.request.post("base-station/deleteBatch", ids).then(res=>{
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