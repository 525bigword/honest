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
  <el-form-item>
    <el-button type="primary" @click="get">查询</el-button>
    <el-button type="primary" @click="addOrUpdateHandle()">添加</el-button>
  </el-form-item>
</el-form>
    </div>
    <el-table :data="users" v-loading.body="listLoading" element-loading-text="拼命加载中" border fit
              highlight-current-row>
      <el-table-column align="center" label="序号" width="80">
        <template slot-scope="scope">
          <span v-text="getIndex(scope.$index)"> </span>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="name" label="员工名" width="80"></el-table-column>
      <el-table-column align="center" prop="sex" label="性别" width="50"></el-table-column>
      <el-table-column align="center" prop="age" label="年龄" width="50"></el-table-column>
      <el-table-column align="center" prop="educationalBackground" label="学历" width="50"></el-table-column>
      <el-table-column align="center" prop="politicalAppearance" label="政治面貌" width="50"></el-table-column>
      <el-table-column align="center" prop="phone" label="手机" width="50"></el-table-column>
      <el-table-column align="center" prop="mechanismname" label="部门" width="50"></el-table-column>
      <el-table-column align="center" prop="postname" label="岗位" width="50"></el-table-column>
      <el-table-column align="center" label="管理" width="200" v-if="hasPerm('article:update')">
        <template slot-scope="scope">
          <el-button type="primary" icon="edit" @click="showUpdate(scope.$index)">修改</el-button>
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
      layout="total, sizes, prev, pager, next, jumper">
    </el-pagination>


    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogTableVisible">
      <el-form ref="form" :model="form" label-width="80px">
  <el-form-item label="员工名">
    <el-input v-model="form.name"></el-input>
  </el-form-item>
  <el-form-item label="年龄" :line="true">
    <el-col :span="8">
      <el-select style="width: 80%;" v-model="form.educational_background" placeholder="学历">
        <el-option label="初中" value="初中"></el-option>
        <el-option label="高中" value="高中"></el-option>
        <el-option label="专科" value="专科"></el-option>
        <el-option label="本科" value="本科"></el-option>
        <el-option label="本科以上" value="本科以上"></el-option>
      </el-select>
    </el-col>
    <el-col class="line" style="width: 20%;" :span="2">政治面貌</el-col>
    <el-col :span="8" >
    <el-select style="width: 80%;" v-model="form.political_appearance" placeholder="政治面貌">
      <el-option label="团员" value="团员"></el-option>
      <el-option label="党员" value="党员"></el-option>
      <el-option label="其他" value="其他"></el-option>
    </el-select>
    </el-col>
  </el-form-item>
  <el-form-item label="电话号码">
    <el-input v-model="form.phone"></el-input>
  </el-form-item>
  <!-- <el-form-item label="活动时间">
    <el-col :span="11">
      <el-date-picker type="date" placeholder="选择日期" v-model="form.date1" style="width: 100%;"></el-date-picker>
    </el-col>
    <el-col class="line" :span="2">-</el-col>
    <el-col :span="11">
      <el-time-picker placeholder="选择时间" v-model="form.date2" style="width: 100%;"></el-time-picker>
    </el-col>
  </el-form-item> -->
  <el-form-item label="即时配送">
    <el-switch v-model="form.delivery"></el-switch>
  </el-form-item>
  <el-form-item label="活动性质">
    <el-checkbox-group v-model="form.type">
      <el-checkbox label="美食/餐厅线上活动" name="type"></el-checkbox>
      <el-checkbox label="地推活动" name="type"></el-checkbox>
      <el-checkbox label="线下主题活动" name="type"></el-checkbox>
      <el-checkbox label="单纯品牌曝光" name="type"></el-checkbox>
    </el-checkbox-group>
  </el-form-item>
  <el-form-item label="特殊资源">
    <el-radio-group v-model="form.resource">
      <el-radio label="线上品牌商赞助"></el-radio>
      <el-radio label="线下场地免费"></el-radio>
    </el-radio-group>
  </el-form-item>
  <el-form-item label="活动形式">
    <el-input type="textarea" v-model="form.desc"></el-input>
  </el-form-item>
  <el-form-item>
    <el-button type="primary" @click="add">立即创建</el-button>
    <el-button>取消</el-button>
  </el-form-item>
</el-form>
    </el-dialog>



    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form class="small-space" :model="tempArticle" label-position="left" label-width="60px"
               style='width: 300px; margin-left:50px;'>
        <el-form-item label="文章">
          <el-input type="text" v-model="tempArticle.content">
          </el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button v-if="dialogStatus=='create'" type="success" @click="createArticle">创 建</el-button>
        <el-button type="primary" v-else @click="updateArticle">修 改</el-button>
      </div>
      <!-- <add v-if="addOrUpdateVisible" ref="add"></add> -->
    </el-dialog>
  </div>
</template>
<script>
// import add from './add'
  export default {
    data() {
      return {
        formInline: {
          name: "",
          staus: '1'
        },
        totalCount: 0, //分页组件--数据总条数
        users: [],//表格的数据
        listLoading: false,//数据加载等待动画
        pageNum: 1,//页码
        pageRow: 10,//每页条数
        name: '',
        region:'',
        dialogTableVisible:false,
        dialogStatus: 'create',
        dialogFormVisible: false,
        textMap: {
          update: '编辑',
          create: '创建文章'
        },
        tempArticle: {
          id: "",
          content: ""
        },
        form: {
          name: '',
          age: '',
          educational_background:'',
          political_appearance:"",
          phone:"",
          date1: '',
          date2: '',
          delivery: false,
          type: [],
          resource: '',
          desc: ''
        }
      }
    },
    components:{
      // add
    },
    created() {
       this.getList();
    },
    computed:{
      staus(val){
        if(val===1){
          return "正常"
        }if(val===2){
          return "删除"
        }if(val===3){
          return "离退"
        }if(val===4){
          return "借调"
        }
      }
    },
    methods: {
      add(){},
      init(id){
          this.form.id=id||0
          this.visible=true
          this.$nextTick(()=>{
              console.log("弹出")
          })
      },
      addOrUpdateHandle(id){
        this.dialogTableVisible=true
        // this.addOrUpdateVisible=true
        // this.$nextTick(()=>{
        //   this.$refs.add.init(id)
        // })
      },
       get() {
         this.getList()
        console.log('submit!');
      },
      getList() {
        //查询列表
        if (!this.hasPerm('staff:list')) {
          return
        }
        this.listLoading = true;
        // if(this.formInline.name){
        //   url="SysStaff/get/"+this.pageNum+"/"+this.pageRow+"/"+this.formInline.staus+"/"+this.formInline.name
        // }else{
        //   url="SysStaff/get/"+this.pageNum+"/"+this.pageRow+"/"+this.formInline.staus
        // }
        this.api({
          url: "SysStaff/get/"+this.pageNum+"/"+this.pageRow+"/"+this.formInline.staus,
          method: "get",
          params:{
            name:this.formInline.name
          }
        }).then(data => {
          console.log(data.records);
          this.users=[]
          data.records.filter(item=>{
            this.users.push(item)
          })
          //this.users.concat(data.records);
          console.log(this.users);
          // this.list = data.records;
          this.totalCount = data.total;
          this.listLoading = false;
        })
      },
      handleSizeChange(val) {
        //改变每页数量
        this.pageRow = val
        this.getList();4

      },
      handleCurrentChange(val) {
        //改变页码
        this.pageNum = val
        this.getList();
      },
      getIndex($index) {
        //表格序号
        return (this.pageNum - 1) * this.pageRow + $index + 1
      },
      showCreate() {
        //显示新增对话框
        this.tempArticle.content = "";
        this.dialogStatus = "create"
        this.dialogFormVisible = true
      },
      showUpdate($index) {
        //显示修改对话框
        this.tempArticle.id = this.list[$index].id;
        this.tempArticle.content = this.list[$index].content;
        this.dialogStatus = "update"
        this.dialogFormVisible = true
      },
      createArticle() {
        //保存新文章
        this.api({
          url: "/article/addArticle",
          method: "post",
          data: this.tempArticle
        }).then(() => {
          this.getList();
          this.dialogFormVisible = false
        })
      },
      updateArticle() {
        //修改文章
        this.api({
          url: "/article/updateArticle",
          method: "post",
          data: this.tempArticle
        }).then(() => {
          this.getList();
          this.dialogFormVisible = false
        })
      },
    }
  }
</script>
<style scoped>
.th{
	float: left;
}
</style>