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
            <el-option label="离退" value="3"></el-option>
            <el-option label="借调" value="4"></el-option>
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
          <el-button type="primary"  v-if="hasPerm('staff:add')" @click="addOrUpdateHandle()">添加</el-button>
          <el-button type="primary"  v-if="hasPerm('staff:delete')" @click="deleteHandle()">删除</el-button>
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
      <el-table-column type="index" :index="indexMethod" align="center" label="序号">
        
      </el-table-column>
      <el-table-column align="center" prop="name" label="员工名"></el-table-column>
      <el-table-column align="center" prop="sex" label="性别"></el-table-column>
      <el-table-column align="center" prop="age" label="年龄"></el-table-column>
      <el-table-column align="center" prop="educationalBackground" label="学历"></el-table-column>
      <el-table-column align="center" prop="politicalAppearance" label="政治面貌"></el-table-column>
      <el-table-column align="center" prop="phone" label="手机"></el-table-column>
      <el-table-column align="center" prop="mechanismname" label="部门"></el-table-column>
      <el-table-column align="center" prop="postname" label="岗位"></el-table-column>
      <el-table-column align="center" label="管理" width="200" v-if="hasPerm('staff:update')">
        <template slot-scope="scope">
          <el-button type="primary" icon="edit" @click="showUpdate(scope)">修改</el-button>
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
      <el-form ref="form" :model="form" label-width="80px">
        <el-form-item label="员工名">
          <el-col :span="8">
            <el-input placeholder="员工名" v-model="form.name"></el-input>
          </el-col>
          <el-col class="right" :span="2">年龄</el-col>
          <el-col :span="8">
            <el-input  onkeyup="value=value.replace(/[^\d]/g,'')" placeholder="年龄" v-model="form.age"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="学历" :line="true">
          <el-col :span="8">
            <el-select style="width: 80%;" v-model="form.educationalBackground" placeholder="学历">
              <el-option label="初中" value="初中"></el-option>
              <el-option label="高中" value="高中"></el-option>
              <el-option label="专科" value="专科"></el-option>
              <el-option label="本科" value="本科"></el-option>
              <el-option label="本科以上" value="本科以上"></el-option>
            </el-select>
          </el-col>
          <el-col class="line right" style="width: 20%;" :span="1">政治面貌</el-col>
          <el-col :span="8">
            <el-select v-model="form.politicalAppearance" placeholder="政治面貌">
              <el-option label="团员" value="团员"></el-option>
              <el-option label="党员" value="党员"></el-option>
              <el-option label="其他" value="其他"></el-option>
            </el-select>
          </el-col>
        </el-form-item>
        <el-form-item label="电话号码" :line="true">
          <el-input v-model="form.phone" placeholder="电话号码"></el-input>
        </el-form-item>
        <el-form-item label="所属部门">
          <el-col :span="8">
            <el-cascader
              placeholder="部门"
              :props="props"
              v-model="form.mid"
              @change="Change"
              :show-all-levels="false"
              :options="bm"
            ></el-cascader>
          </el-col>
          <el-col :span="2">性别</el-col>
          <el-col :span="8">
            <el-radio v-model="form.sex" label="男">男</el-radio>
            <el-radio v-model="form.sex" label="女">女</el-radio>
          </el-col>
        </el-form-item>
        <el-form-item label="登录名">
          <el-col :span="8">
            <el-input v-model="form.username" :disabled="dialogStatus==='create'?false:true" placeholder="登录名"></el-input>
          </el-col>
          <el-col :span="4" class="line right" style="width: 15%;">登录密码</el-col>
          <el-col :span="8">
            <el-input type="password" v-model="form.password" placeholder="登录密码"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="岗位">
          <el-select style="width: 80%;" v-model="form.pid" placeholder="岗位">
            <el-option
              v-for="(post) in postList"
              :key="post.pid"
              :label="post.pname"
              :value="post.pid"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="状态" v-if="dialogStatus==='update'">
          <el-radio v-model="form.staus" label="1">正常</el-radio>
          <el-radio v-model="form.staus" label="2">删除</el-radio>
          <el-radio v-model="form.staus" label="3">离退</el-radio>
          <el-radio v-model="form.staus" label="4">借调</el-radio>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="dialogStatus==='create'?add():update()">{{dialogStatus==='create'?'添加':'修改'}}</el-button>
          <el-button type="primary" @click="dialogTableVisible = false;">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

   
  </div>
</template>
<script>
import store from '../../../store';
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
        staus: "1",
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
      postList: [],
      tempArticle: {
        id: "",
        content: ""
      },
      form: {
        staus:"",
        name: "",
        sex:'男',
        age: "",
        educationalBackground: "",
        politicalAppearance: "",
        phone: "",
        username: "",
        password: "",
        phone: "",
        createId:store.getters.userId,
        pid: undefined,
        mid: undefined
      },
      deleteList:[]
    };
  },
  components: {
    // add
  },
  created() {
    this.getList();
    this.getSysmechanismAll();
    // this.getPost();
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
    getPost() {
      this.api({
        url: "syspost/get",
        method: "get"
      }).then(res => {
        console.log(res);
        this.postList = [];
        res.filter(item => {
          this.postList.push(item);
        });
      });
    },
    Change(val) {
      console.log(this.form.mid);
      console.log(val);
      this.api({
        url:'syspost/get/'+val,
        method:'get'
      }).then(res=>{
        console.log(res)
        this.postList=[];
        res.filter(item=>{
          this.postList.push(item)
        })
      })
      // this.temp.defaultvalue = val;
    },
    toggleRowExpansion(val){
      console.log(val)
      this.deleteList=[];
      val.filter(item=>{
        this.deleteList.push(item)
      })
    },
    deleteHandle(){
      if(this.deleteList.length<1){
        this.$message({
          type:'error',
          message:'请先勾选需要删除的用户'
        })
      }else{
        let arr=[];
        this.deleteList.filter(item=>{
          arr.push(item.sid)
        })
        let str=arr.join(",");
        console.log(str)
        this.api({
          url:'SysStaff/delete',
          method:'post',
          params:{
            str:str
          }
        }).then(res=>{

          if(res==2){
            this.$message({
              type:'error',
              message:'请确保该员工当前没有进行中的任务'
            })
          }else{{
            this.$message({
              type:'suucess',
              message:'删除成功'
            })
            this.getList();
          }}
        })
      }
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
          if(res===1){
            this.$message({
              type: "success",
              message: "添加成功"
            })
            this.getList();
            this.dialogTableVisible = false;
          }else{
            this.$message({
              type: "error",
              message: "添加失败，该登录名重复"
            })
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
      this.dialogStatus='create';
      // this.addOrUpdateVisible=true
      // this.$nextTick(()=>{
      //   this.$refs.add.init(id)
      // })
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
      console.log(this.formInline)
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
          mid:this.formInline.bm
          
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
    Close(){
      this.form={}
      this.postList=[]
    },
    showUpdate(scope,$index) {
      console.log(scope)
      console.log($index)
      this.api({
        url:'syspost/get/'+scope.row.mid,
        method:'get'
      }).then(res=>{
        console.log("res",res)
        this.postList=[];
        res.filter(item=>{
          this.postList.push(item)
        })
      })
      // //显示修改对话框
      this.form=scope.row
      this.form.pid=scope.row.postname
      this.dialogStatus = "update";
      this.dialogTableVisible = true;
    },
    update(){
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
      ){
        this.$message({
          type:'error',
          message:'请先完善信息'
        })
      }else{
        console.log(this.form.staus)
        this.api({
          url:'SysStaff/update',
          method:'put',
          data:this.form
        }).then(res=>{
          console.log(res)
          if(res===1){
            this.$message({
              type:'success',
              message:'添加成功'
            })
            this.getList()
            this.dialogTableVisible=false
          }
        })
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
    indexMethod(val){
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
</style>