<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form :inline="true" :model="formInline" class="demo-form-inline">
        <el-form-item label="姓名">
          <el-input v-model="formInline.name" placeholder="姓名"></el-input>
        </el-form-item>
        <el-form-item label="员工状态">
          <el-select v-model="formInline.staus" placeholder="活动区域">
            <el-option label="正常" value="1"></el-option>
            <el-option label="删除" value="2"></el-option>
            <!-- <el-option label="离退" value="3"></el-option>
            <el-option label="借调" value="4"></el-option>-->
          </el-select>
        </el-form-item>
        <el-form-item label="部门">
          <el-cascader
            placeholder="部门"
            v-model="formInline.bm"
            :props="props"
            @change="Change"
            :show-all-levels="false"
            :options="bm"
          ></el-cascader>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="get">查询</el-button>
          <el-button type="primary" v-if="hasPerm('staff:add')" @click="addOrUpdateHandle()">添加</el-button>
          <el-button type="primary" v-if="hasPerm('staff:delete')" @click="deleteHandle()">删除</el-button>
        </el-form-item>
      </el-form>
    </div>
    <el-table
      @selection-change="toggleRowExpansion"
      :data="users"
      v-loading.body="listLoading"
      element-loading-text="拼命加载中"
      border
      fit
      highlight-current-row
    >
      <el-table-column prop="pid" type="selection" width="55"></el-table-column>
      <el-table-column type="index" :index="indexMethod" align="center" label="序号"></el-table-column>
      <el-table-column align="center" prop="name" label="员工名"></el-table-column>
      <el-table-column align="center" prop="sex" label="性别"></el-table-column>
      <el-table-column align="center" prop="age" label="年龄"></el-table-column>
      <el-table-column align="center" prop="educationalBackground" label="学历"></el-table-column>
      <el-table-column align="center" prop="politicalAppearance" label="政治面貌"></el-table-column>
      <el-table-column align="center" prop="phone" label="手机"></el-table-column>
      <el-table-column align="center" prop="mechanismname" label="部门"></el-table-column>
      <el-table-column align="center" prop="postname" label="角色"></el-table-column>
      <el-table-column align="center" prop="ppname" label="岗位"></el-table-column>
      <el-table-column align="center" label="管理" width="180" v-if="hasPerm('staff:update')">
        <template slot-scope="scope">
          <div v-if="scope.row.staus==1">
            <el-button type="primary" size="small" icon="edit" @click="showUpdate(scope)">修改</el-button>
          </div>
          <div v-if="scope.row.staus==2">
            <el-row>
              <el-button type="success" size="small" icon="edit" @click="huifu(scope)">恢复</el-button>
              <el-button type="danger" size="small" icon="edit" @click="yjdelete(scope)">永久删除</el-button>
            </el-row>
          </div>
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

    <el-dialog @closed="Close" :title="textMap[dialogStatus]" :visible.sync="dialogTableVisible">
     <el-form ref="dataForm" :rules="rules" :model="form" label-width="80px">
        <el-row>
          <el-col style="width:45%">
            <el-form-item label="员工名" prop="name">
              <el-input placeholder="员工名" v-model="form.name"></el-input>
            </el-form-item>
          </el-col>
          <el-col style="margin-left:10px;width:45%">
            <el-form-item label="年龄" prop="age">
              <el-input
                onkeyup="value=value.replace(/[^\d]/g,'')"
                placeholder="年龄"
                v-model="form.age"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col style="width:45%">
            <el-form-item label="学历" :line="true" prop="educationalBackground">
              <el-select style="width: 100%;" v-model="form.educationalBackground" placeholder="学历">
                <el-option label="初中" value="初中"></el-option>
                <el-option label="高中" value="高中"></el-option>
                <el-option label="专科" value="专科"></el-option>
                <el-option label="本科" value="本科"></el-option>
                <el-option label="本科以上" value="本科以上"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col style="margin-left:10px;width:45%">
            <el-form-item label="政治面貌" prop="politicalAppearance">
              <el-select style="width:100%" v-model="form.politicalAppearance" placeholder="政治面貌">
                <el-option label="团员" value="团员"></el-option>
                <el-option label="党员" value="党员"></el-option>
                <el-option label="其他" value="其他"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
          <el-form-item label="电话号码" style="width:91.5%" prop="phone">
          <el-input v-model="form.phone" placeholder="电话号码"></el-input>
        </el-form-item>
        <el-row>
          <el-col style="width:45%">
          <el-form-item label="所属部门" prop="mid">
              <el-cascader
                placeholder="部门"
                :props="props"
                v-model="form.mid"
                @change="Change"
                :show-all-levels="false"
                :options="bm"
                style="width:100%"
              ></el-cascader>
            </el-form-item>
          </el-col>
          <el-col style="margin-left:10px;width:45%">
            <el-form-item label="性别" prop="sex">
              <el-radio-group v-model="form.sex">
            <el-radio   label="男">男</el-radio>
            <el-radio label="女">女</el-radio>
            </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col style="width:45%">
            <el-form-item label="登录名" prop="username">
              <el-input
                style="width:100%"
                v-model="form.username"
                :disabled="dialogStatus==='create'?false:true"
                placeholder="登录名"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col style="margin-left:10px;width:45%" >
            <el-form-item label="登录密码" prop="password">
              <el-input
                type="password"
                style="width:100%"
                v-model="form.password"
                placeholder="登录密码"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col style="width:45%">
            <el-form-item label="岗位" prop="ppid">
              <el-select style="width: 100%;" v-model="form.ppid" placeholder="岗位">
                <el-option
                  v-for="(post) in postList"
                  :key="post.pid"
                  :label="post.pname"
                  :value="post.pid"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col style="margin-left:10px;width:45%">
            <el-form-item label="角色" prop="pid">
              <el-select style="width: 100%;" v-model="form.pid" placeholder="角色">
                <el-option
                  v-for="(post) in ppostList"
                  :key="post.pid"
                  :label="post.pname"
                  :value="post.pid"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <!-- <el-form-item label="状态" v-if="dialogStatus==='update'">
          <el-radio v-model="form.staus" label="1">正常</el-radio>
          <el-radio v-model="form.staus" label="2">删除</el-radio>
          <el-radio v-model="form.staus" label="3">离退</el-radio>
          <el-radio v-model="form.staus" label="4">借调</el-radio>
        </el-form-item>-->
        <el-form-item style="margin-top:15px">
          <el-button
            style="margin-left:63%"
            type="primary"
            @click="dialogStatus==='create'?add():update()"
          >{{dialogStatus==='create'?'添加':'修改'}}</el-button>
          <el-button type="primary" @click="dialogTableVisible = false;">取消</el-button>
        </el-form-item>
      </el-form>
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
        name: "",
        staus: "1"
      },
      totalCount: 0, //分页组件--数据总条数
      users: [], //表格的数据
      listLoading: false, //数据加载等待动画
      pageNum: 1, //页码
      pageRow: 10, //每页条数
      name: "",
      region: "",
      dialogTableVisible: false,
      dialogStatus: "create",
      dialogFormVisible: false,
      textMap: {
        update: "编辑员工信息",
        create: "添加员工"
      },
      ppostList: [],
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
        pname: "",
        createId: store.getters.userId,
        pid: undefined,
        ppid: undefined,
        mid: undefined
      },
      rules:{
        name: [{
            required: true,
            message: "员工名不能为空",
            trigger: ["blur", "change"]
          }],
          age: [{
            required: true,
            message: "年龄不能为空",
            trigger: ["blur", "change"]
          }],
          educationalBackground: [{
            required: true,
            message: "请选择学历",
            trigger: [ "change"]
          }],
          politicalAppearance: [{
            required: true,
            message: "请选择政治面貌",
            trigger: [ "change"]
          }],
          phone: [{
            required: true,
            message: "电话号码不能为空",
            trigger: ["blur", "change"]
          }],
          mid: [{
            required: true,
            message: "请选择所属部门",
            trigger: "change"
          }],
          sex: [{
            required: true,
            message: "请选择员工性别",
            trigger: ["change"]
          }],
          username: [{
            required: true,
            message: "登录名不能为空",
            trigger: ["blur", "change"]
          }],
          password: [{
            required: true,
            message: "登录密码不能为空",
            trigger: ["blur", "change"]
          }],
          ppid: [{
            required: true,
            message: "请选择岗位",
            trigger: [ "change"]
          }],
          pid: [{
            required: true,
            message: "请选择角色",
            trigger: ["change"]
          }],
      deleteList: []
    }
    }
  },
  components: {
    // add
  },
  created() {
    this.getList();
    this.getSysmechanismAll();
    this.getppost();
  },
  computed: {
    staus(val) {
      if (val === 1) {
        return "正常";
      }
      if (val === 2) {
        return "删除";
      }
      if (val === 3) {
        return "离退";
      }
      if (val === 4) {
        return "借调";
      }
    }
  },
  methods: {
    getppost() {
      this.api({
        url: "syspost/get",
        method: "get"
      }).then(res => {
        console.log(res, "res1");
        this.ppostList = [];
        res.filter(item => {
          this.ppostList.push(item);
        });
      });
    },
    Change(val) {
      console.log(this.form.mid);
      console.log(val);
      this.form.pname = "";
      // this.form.pid = undefined;
      this.form.ppid=undefined;
      this.api({
        url: "SysPostPermission/getPpost/" + val,
        method: "get"
      }).then(res => {
        console.log(res, "res");
        this.postList = [];
        res.filter(item => {
          this.postList.push(item);
        });
      });
      // this.temp.defaultvalue = val;
    },
    toggleRowExpansion(val) {
      console.log(val);
      this.deleteList = [];
      val.filter(item => {
        this.deleteList.push(item);
      });
    },
    deleteHandle() {
      if (this.deleteList.length < 1) {
        this.$message({
          type: "error",
          message: "请先勾选需要删除的用户"
        });
      } else {
        let arr = [];
        this.deleteList.filter(item => {
          arr.push(item.sid);
        });
        let str = arr.join(",");
        console.log(str);
        this.api({
          url: "SysStaff/delete",
          method: "post",
          params: {
            str: str
          }
        }).then(res => {
          if (res == 2) {
            this.$message({
              type: "error",
              message: "请确保该员工当前没有进行中的任务或业务"
            });
          } else {
            {
              this.$message({
                type: "suucess",
                message: "删除成功"
              });
              this.getList();
            }
          }
        });
      }
    },
    huifu(row) {
      console.log(row);
      this.$confirm("此操作将恢复用户以及用户信息", "是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          this.api({
            url: "SysStaff/hf/" + row.row.sid,
            method: "get"
          }).then(res => {
            this.getList();
          });
          this.$message({
            type: "success",
            message: "删除成功!"
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除"
          });
        });
    },
    yjdelete(row) {
      console.log(row);
      this.$confirm("此操作将永久删除用户以及用户信息", "是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          this.api({
            url: "SysStaff/del/" + row.row.sid,
            method: "get"
          }).then(res => {
            this.getList();
          });
          this.$message({
            type: "success",
            message: "删除成功!"
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除"
          });
        });
    },
    add() {
      this.$refs["dataForm"].validate(valid => {
        // 表单校验通过
        if (valid) {
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
      }})
    },
    init(id) {
      this.form.id = id || 0;
      this.visible = true;
      this.$nextTick(() => {
        console.log("弹出");
      });
    },
    getSysmechanismAll() {
      this.api({
        url: "sysmechanism/get",
        method: "get"
      }).then(res => {
        console.log("sysmechanism", res);
        // this.bm.concat(res)
        this.bm = [];
        res.filter(item => {
          this.bm.push(item);
        });
      });
    },
    addOrUpdateHandle(id) {
      this.dialogTableVisible = true;
      this.dialogStatus = "create";
      // this.addOrUpdateVisible=true
      // this.$nextTick(()=>{
      //   this.$refs.add.init(id)
      // })
      this.$refs["dataForm"].clearValidate();
    },
    get() {
      this.getList();
      console.log("submit!");
    },
    getList() {
      //查询列表
      if (!this.hasPerm("staff:list")) {
        return;
      }
      this.listLoading = true;
      console.log(this.formInline);
      this.api({
        url:
          "SysStaff/get/" +
          this.pageNum +
          "/" +
          this.pageRow +
          "/" +
          this.formInline.staus,
        method: "get",
        params: {
          name: this.formInline.name,
          mid: this.formInline.bm
        }
      }).then(data => {
        console.log(data);
        this.users = [];
        this.total = data.total;
        data.records.filter(item => {
          this.users.push(item);
        });
        //this.users.concat(data.records);
        console.log(this.users);
        // this.list = data.records;
        this.totalCount = data.total;
        this.listLoading = false;
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
      // this.form = {};
      // this.postList = [];
      this.getList();
      this.form={
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
        pname:"",
        createId: store.getters.userId,
        pid: undefined,
        ppid:undefined,
        mid: undefined
      }
    },
    showUpdate(scope, $index) {
      console.log(scope);
      this.api({
        url: "SysPostPermission/getPpost/" + scope.row.mid,
        method: "get"
      }).then(res => {
        console.log(res, "res");
        this.postList = [];
        res.filter(item => {
          this.postList.push(item);
        });
        console.log(this.postList);
      });
      
      //显示修改对话框
      this.form = scope.row;
      this.form.mid=scope.row.mid
      this.form.pid = parseInt(scope.row.pid);
      this.dialogStatus = "update";
      this.dialogTableVisible = true;
      // this.$refs["dataForm"].clearValidate();
    },
    update() {
      this.$refs["dataForm"].validate(valid => {
        // 表单校验通过
        if (valid) {
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
            this.dialogTableVisible = false;
          }
        });
      }
      })
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
.div {
  display: inline-block;
}
.th {
  float: left;
}
.right {
  margin-left: 10px;
}
</style>