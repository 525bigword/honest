<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form :inline="true" :model="formInline" class="demo-form-inline">
        <el-form-item label="账号">
          <el-input v-model="formInline.username" placeholder="账号"></el-input>
        </el-form-item>
        <el-form-item label="ip">
          <el-input v-model="formInline.ip" placeholder="ip地址"></el-input>
        </el-form-item>
        <el-form-item label="日期">
          <el-date-picker
            v-model="formInline.enddate"
            type="daterange"
            format="yyyy-MM-dd HH:mm:ss"
            value-format="yyyy-MM-dd"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
          ></el-date-picker>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="get">查询</el-button>
        </el-form-item>
      </el-form>
    </div>
    <el-table
      @selection-change="toggleRowExpansion"
      :data="logs"
      v-loading.body="listLoading"
      element-loading-text="拼命加载中"
      border
      fit
      highlight-current-row
    >
      <el-table-column type="index" :index="indexMethod" align="center" label="序号"></el-table-column>
      <el-table-column align="center" prop="sname" label="登陆人"></el-table-column>
      <el-table-column align="center" prop="url" label="访问接口"></el-table-column>
      <el-table-column align="center" prop="method" label="请求方式"></el-table-column>
      <el-table-column align="center" prop="ip" label="ip"></el-table-column>
      <el-table-column align="center" prop="data" label="参数"></el-table-column>
      <el-table-column align="center" prop="terminal" label="终端"></el-table-column>
      <el-table-column align="center" prop="type" label="浏览器"></el-table-column>
      <el-table-column align="center" prop="time" label="执行时间"></el-table-column>
      <el-table-column
        v-if="hasPerm('log:list')"
        label="操作"
        align="center"
        width="230"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="{row,$index}">
          <el-button
            v-if="hasPerm('log:list')"
            size="mini"
            type="primary"
            @click="getUser(row,$index)"
          >查看</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="pageNum"
      :page-size="pageRow"
      :total="totalCount"
      :page-sizes="[10, 20, 50, 100]"
      layout="total, sizes, prev, pager, next, jumper"
    ></el-pagination>

    <el-dialog @closed="Close"  :visible.sync="dialogVisible">
      <el-row>
        <el-col :span="4">姓名：</el-col>
         <el-col :span="4">{{dialogobj.name}}</el-col>
        <el-col :span="4">性别：</el-col>
        <el-col :span="4" >{{dialogobj.sex}}</el-col>
         <el-col :span="4">年龄：</el-col>
        <el-col :span="4">{{dialogobj.age}}</el-col>
    </el-row>
    <el-row>
        <el-col :span="4">学历：</el-col>
         <el-col :span="4">{{dialogobj.educationalBackground}}</el-col>
        <el-col :span="4">政治面貌：</el-col>
        <el-col :span="4" >{{dialogobj.politicalAppearance}}</el-col>
         <el-col :span="4">手机号码：</el-col>
        <el-col :span="4">{{dialogobj.phone}}</el-col>
    </el-row>
    <el-row>
        <el-col :span="4">部门名称：</el-col>
         <el-col :span="4">{{dialogobj.mechanismname}}</el-col>
        <el-col :span="4">岗位名称：</el-col>
        <el-col :span="4" >{{dialogobj.postname}}</el-col>
        <el-col :span="4">用户状态：</el-col>
        <el-col :span="4">{{dialogobj.staus}}</el-col>
    </el-row>
    <el-row>
        <el-col :span="4">登录名：</el-col>
         <el-col :span="4">{{dialogobj.username}}</el-col>
        <el-col :span="4">登录密码：</el-col>
        <el-col :span="4" >{{dialogobj.password}}</el-col>
        <el-col :span="4">创建时间：</el-col>
        <el-col :span="4">{{dialogobj.createTime}}</el-col>
    </el-row>
    </el-dialog>
  </div>
</template>
<script>
import store from "../../../store";
// import add from './add'
export default {
  data() {
    return {
      bm: [],
      dialogVisible:false,
      props: {
        value: "mid",
        label: "mechanismName",
        children: "chilrenMechanism",
        emitPath: false,
        expandTrigger: "click",
        checkStrictly: true,
        collapseTags: true,
        clearable: true
      },
      formInline: {
        username: "",
        enddate: "",
        ip: ""
      },
      totalCount: 0, //分页组件--数据总条数
      logs: [], //表格的数据
      listLoading: false, //数据加载等待动画
      pageNum: 1, //页码
      pageRow: 10, //每页条数
      name: "",
      region: "",
      dialogTableVisible: false,
      dialogStatus: "create",
      dialogFormVisible: false,
      postList: [],
      tempArticle: {
        id: "",
        content: ""
      },
      form: {
        staus: "",
        name: "",
        sex: "男",
        age: "",
        educationalBackground: "",
        politicalAppearance: "",
        phone: "",
        username: "",
        password: "",
        phone: "",
        createId: store.getters.userId,
        pid: undefined,
        mid: undefined
      },
      dialogobj:{},
      deleteList: []
    };
  },
  components: {
    
  },
  created() {
    this.getList();
  },
  computed: {
    
  },
  methods: {
      staus(val) {
      if (val === '1') {
        return "正常";
      }
      if (val === '2') {
        return "删除";
      }
      if (val === '3') {
        return "离退";
      }
      if (val === '4') {
        return "借调";
      }
    },
    getUser(row,index){
        this.dialogVisible=true
        console.log(row)
        this.api({
            url:'SysStaff/get/'+row.sid,
            method:'get'
        }).then(res=>{
            console.log(res)
            this.dialogobj=res
            this.dialogobj.staus=this.staus(res.staus);
        })
    },
    Change(val) {
      console.log(this.form.mid);
      console.log(val);
      // this.temp.defaultvalue = val;
    },
    toggleRowExpansion(val) {
      console.log(val);
      this.deleteList = [];
      val.filter(item => {
        this.deleteList.push(item);
      });
    },
    add() {
      console.log(this.form);
      if (
        !this.form.name ||
        !this.form.sex ||
        !this.form.age ||
        !this.form.educationalBackground ||
        !this.form.username ||
        !this.form.mid ||
        !this.form.password ||
        !this.form.phone ||
        !this.form.pid ||
        !this.form.politicalAppearance
      ) {
        this.$message({
          type: "error",
          message: "请确保信息填写完整"
        });
      } else {
        this.api({
          url: "SysStaff/add",
          method: "post",
          data: this.form
        }).then(res => {
          console.log(res);
          if (res === 1) {
            this.$message({
              type: "success",
              message: "添加成功"
            });
            this.getList();
            this.dialogTableVisible = false;
          } else {
            this.$message({
              type: "error",
              message: "添加失败，该登录名重复"
            });
          }
        });
      }
    },
    init(id) {
      this.form.id = id || 0;
      this.visible = true;
      this.$nextTick(() => {
        console.log("弹出");
      });
    },

    get() {
      this.getList();
      console.log("submit!");
    },
    getList() {
      //查询列表
      if (!this.hasPerm("log:list")) {
        return;
      }
      this.listLoading = true;
      console.log(this.formInline);
      this.api({
        url: "syslog/get/" + this.pageNum + "/" + this.pageRow,
        method: "get",
        params: {
          username: this.formInline.username,
          ip: this.formInline.ip,
          begin: this.formInline.enddate[0],
          end: this.formInline.enddate[1]
        }
      }).then(data => {
        console.log(data);
        this.totalCount = data.total;
        this.logs = [];
        data.records.filter(item => {
          this.logs.push(item);
        });
        this.listLoading = false;
        // this.users = [];
        // this.total = data.total;
        // data.records.filter(item => {
        //   this.users.push(item);
        // });
        // //this.users.concat(data.records);
        // console.log(this.users);
        // // this.list = data.records;
        // this.totalCount = data.total;
        // this.listLoading = false;
      });
    },
    handleSizeChange(val) {
      //改变每页数量
      this.pageRow = val;
      this.getList();
      4;
    },
    handleCurrentChange(val) {
      //改变页码
      this.pageNum = val;
      this.getList();
    },
    getIndex($index) {
      //表格序号
      return (this.pageNum - 1) * this.pageRow + $index + 1;
    },
    showCreate() {
      //显示新增对话框
      this.tempArticle.content = "";
      this.dialogStatus = "create";
      this.dialogFormVisible = true;
    },
    Close() {
      this.form = {};
    },
    update() {
      if (
        !this.form.sid ||
        !this.form.name ||
        !this.form.sex ||
        !this.form.age ||
        !this.form.educationalBackground ||
        !this.form.username ||
        !this.form.mid ||
        !this.form.password ||
        !this.form.phone ||
        !this.form.pid ||
        !this.form.politicalAppearance
      ) {
        this.$message({
          type: "error",
          message: "请先完善信息"
        });
      } else {
        console.log(this.form.staus);
        this.api({
          url: "SysStaff/update",
          method: "put",
          data: this.form
        }).then(res => {
          console.log(res);
          if (res === 1) {
            this.$message({
              type: "success",
              message: "添加成功"
            });
            this.getList();
            this.dialogTableVisible = false;
          }
        });
      }
    },
    createArticle() {
      //保存新文章
      this.api({
        url: "/article/addArticle",
        method: "post",
        data: this.tempArticle
      }).then(() => {
        this.getList();
        this.dialogFormVisible = false;
      });
    },
    updateArticle() {
      //修改文章
      this.api({
        url: "/article/updateArticle",
        method: "post",
        data: this.tempArticle
      }).then(() => {
        this.getList();
        this.dialogFormVisible = false;
      });
    },
    indexMethod(val) {
      return ++val;
    }
  }
};
</script>
<style scoped>
.th {
  float: left;
}
.right {
  margin-left: 10px;
}
 .el-row {
    margin-bottom: 20px;
    &:last-child {
      margin-bottom: 0;
    }
  }
  .el-col {
    border-radius: 4px;
  }
  .bg-purple-dark {
    background: #99a9bf;
  }
  .bg-purple {
    background: #d3dce6;
  }
  .bg-purple-light {
    background: #e5e9f2;
  }
  .grid-content {
    border-radius: 4px;
    min-height: 36px;
  }
  .row-bg {
    padding: 10px 0;
    background-color: #f9fafc;
  }
</style>