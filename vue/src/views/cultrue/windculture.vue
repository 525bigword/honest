<template>
  <div >
    <div class="app-container" :style="{'display':dis}" style="width:100%">
    <div class="filter-container" align="center" style="margin-top: -30px;">
      <!-- v-waves -->
      <label>标题</label>&nbsp;&nbsp;
      <el-input v-model="wtitle" placeholder="请输入清风文苑标题" style="width: 200px;" class="filter-item"/>
       &nbsp;&nbsp;
          <el-button
            type="primary"
            @click="getList"
            class="el-icon-search" >查询
          </el-button>&nbsp;
          <el-button
            type="primary"
            class="el-icon-refresh" 
            @click="resetSou">重置
          </el-button>
    </div>
    <div style="margin-top:15px">
      <el-button type="primary" class="el-icon-plus" @click="handleCreate">新增</el-button>
      <el-button type="primary" class="el-icon-delete" @click="handleDelete">删除</el-button>
    </div>
    <el-table
      :key="tableKey"
      v-loading="listLoading"
      @selection-change="handleSelectChangeLeft"
      :default-sort = "{prop: 'dcreateTime', order: 'descending'}"
      :data="list"
      border
      highlight-current-row
      style="width: 100%;margin-top: 20px"
      ref="multipleTable"
    >
    
    <el-table-column type="selection" width="60px" align="center"></el-table-column>
      <el-table-column label="序号" prop="index"  align="center" width="90px" type="index" :index="indexMethod">
        <!-- <template slot-scope="scope">
          <span>{{ scope.row.did }}</span>
        </template> -->
      </el-table-column>
      
        <el-table-column label="标题" prop="wtitle"  align="center" width="310px">
        <template slot-scope="scope">
          <a style="color:#1890ff" @click="handleUpdate(scope.row)">{{ scope.row.wtitle }}</a>
        </template>
        </el-table-column>
        <el-table-column label="投稿人" prop="wnew"   align="center" width="200px">
        <template slot-scope="scope">
          <span>{{ scope.row.wnew.name }}</span>
        </template>
      </el-table-column>
      <el-table-column label="创建人" prop="sysStaff"   align="center" width="200px">
        <template slot-scope="scope">
          <span>{{ scope.row.sysStaff.name }}</span>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" sortable prop="wcreateTime" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.wcreateTime | comverTime('YYYY-MM-DD') }}</span>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页工具条  page当前页 total总记录数 limit每页显示多少条 pagination触发自定义事件，查询数据 @size-change="handleSizeChange"
          @current-change="handleCurrentChange"-->
     <div class="block" align="center" style="margin-top: 20px">
        <el-pagination
        v-show="total>0"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          :page-sizes="[5,10, 20, 30, 40,50]"
          :page-size="pageRow"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
        </el-pagination>
      </div>
    </div>
    <!-- @blur="onEditorBlur($event)" 
      @focus="onEditorFocus($event)"
      @change="onEditorChange($event)" -->
    <div :style="{'display':dis2}" style="background-color: lightgray;width:100%;margin-top:-9px">
      <el-main>
      <el-form ref="dataForm" :rules="rules" :model="temp" label-position="center" label-width="130px" style="width: 100%;">
        <div style="background-color: white;width: 100%;height: 65px;position:fixed; top:50px; left:-1px;z-index:2 ;">
        <!--        数据校验要求prop值和temp.属性名一致-->
       <el-form-item  style="width:100%;height:30px;margin-left: -60px;margin-top:12px" align="right">
        <el-button type="success" :disabled="isShow" class="el-icon-top"   @click="dialogStatus==='update'?updateData(3):createData(3)">
          保存
        </el-button>
         <el-button class="el-icon-back" plain @click="out()">
          返回
        </el-button>
        </el-form-item></div>
         <div style="background-color: white;margin-top: 25px;z-index:3;">
            <div style="height:20px"></div>
        <el-form-item style="font-weight: bold;" label="清风文苑标题" prop="wtitle" >
          <el-input v-model="temp.wtitle" placeholder="请输入清风文苑标题" style="width:50%" />
        </el-form-item>
        <!-- <el-form-item style="font-weight: bold;" label="所属部门" prop="dTitle" >
          <el-input placeholder="请输入清风文苑标题" style="width:30%" />
        </el-form-item> -->
        <el-form-item style="font-weight: bold;" label="投稿人" prop="sid" >
        <el-cascader 
        ref="bbb" prop="value"
        @change="bmChange"
        style="width:23%"
         v-model="value"
             :options="options"
             :props="{ expandTrigger: 'hover',checkStrictly: true }"
             :show-all-levels="false"
               clearable></el-cascader>
               &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
           <el-select v-model="temp.sid" :style="{'display':dis3}" placeholder="请选择投稿人" v-cloak style="width:23%;">
              <el-option
             v-for="item in stafflist"
            :key="item.sid"
            :label="item.name"
           :value="item.sid">
            </el-option>
           </el-select>
        </el-form-item>
        
        <el-form-item style="font-weight: bold;" label="文章内容" prop="wcontent" >
          <quill-editor class="editor"  style="height:400px;width:85%;"
        ref="myQuillEditor"
        v-model="temp.wcontent"
       >
        </quill-editor>
        </el-form-item>
        <el-form-item style="font-weight: bold;margin-top:120px;" label="创建时间" prop="wCreateTime" >
          <el-date-picker disabled="disabled" 
    style="width: 50%"
    type="date"
    v-model="temp.wCreateTime"
    :format="'yyyy-MM-dd HH:mm:ss'">
</el-date-picker>
        </el-form-item>
        <el-form-item style="font-weight: bold;" label="创建人" prop="sysStaff" >
          <el-input v-model="temp.sysStaff.name" disabled="disabled" style="width:50%"/>
        </el-form-item>
         <el-form-item style="margin-top:10px"></el-form-item>
         </div>
      </el-form>
      </el-main>
    </div>
  </div>
</template>

<script>
import { add, update, list, deleteWind,stafflist,getDid } from '@/api/culture/windculture'
import qs from 'qs'
import { mapGetters } from 'vuex'
  export default {
    name: 'userTable',
    computed:{...mapGetters([
      'nickname',
      'userId'
    ])
    },
    components: {  },
    data() {
      /* const validateRequire = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('标题不能为空!'))
      } else {
        callback()
      }
    } */
      return {
        dis:'inline-block',
        dis2:'none',
        dis3:'none',
        tableKey: 0,
        list: [], // 后台返回，给数据表格展示的数据
        total: 0, // 总记录数
        listLoading: true, // 是否使用动画
          pageNum: 1, // 分页需要的当前页
          pageRow:10, // 分页需要的每页显示多少
          wtitle: '',
          wstatus: 1,
        temp: { // 添加、修改时绑定的表单数据
          wid: undefined,
          wtitle: '',
          wcontent: '',
          wnew: {
            name: '',
            sid: null,
            mid:null
          },
          sysStaff: {
            name: '',
            sid: 0
          },
          wCreateTime:new Date(),
          wstatus:1,
          sid:null
        },
        i:0,
        title: '添加', // 对话框显示的提示 根据dialogStatus create
        dialogFormVisible: false, // 是否显示对话框
        dialogStatus: '', // 表示表单是添加还是修改的
        rules: {
          // 校验规则
          //dTitle:  [{ required: true, message: '标题必填', trigger: 'blur' }],
          //dFileName: [{ required: true, message: '请上传文件', trigger: 'change'}]
          wtitle: [{required:true,message:'标题不能为空',trigger:['blur','change']}],
          sid:[{required:true,message:'请选择投稿人',trigger:['change']}]
        },
        isShow:false,
          wew:{},
        multipleSelection:[],
        deleteid:[],
        melist:[],
        stafflist:[],
        options:[],
        value:[]
      }
    },
    // 创建实例时的钩子函数
    created() {
      this.getOption();
      this.getList()
      
      // 在创建时初始化获得部门信息
      //this.getGroupDept()
    },
    methods: {
      // 获得分好组的部门信息
      /* getGroupDept(){
        groupDept().then((response) => {
          this.deptList = response.data.deptList
        })
      }, */
      // 去后台取数据的
      getList() {
          if (!this.hasPerm('wind:list')) {
          return
        }
        // 开始转圈圈
        this.listLoading = true
        // debugger // 调试
        /* let data=qs.stringify({
          account: this.listQuery.account
        }) */
        list(this.pageNum,this.pageRow,this.wtitle).then(response => {
          this.list=response.wind.records;
          this.total=(response.wind.total)
          this.wew=response.sys
          console.debug(this.list)
          // 转圈圈结束
           this.listLoading = false
        })
       
      },
      getStaffList(){
         this.listLoading = true
        stafflist(this.temp.wnew.mid).then(response=>{
          this.stafflist=response;
          console.debug(this.stafflist)
          // 转圈圈结束
          this.listLoading = false
        })
      },
      getOption() {
      getDid().then(response => {
        this.options = response;
      });
    },
      resetSou(){
        this.wtitle=''
      },
      
      // 重置表单数据
      resetTemp() {
        this.temp = {
          wid: undefined,
          wtitle: '',
          wcontent: '',
          wnew: {
            name: '',
            sid: null,
            mid:0
          },
          sysStaff: {
            name: '',
            sid: 0,
            mid:0
          },
          wCreateTime:new Date(),
          wstatus:1,
          sid:null
        }
        this.value=[]

      },
      // 显示添加的对话框
      handleCreate () {
        // 重置表单数据
        this.resetTemp()
        this.xianshi()
        this.temp.sysStaff.name=this.nickname
        this.temp.sysStaff.sid=this.userId
        // 点击确定时，是执行添加操作
        this.dialogStatus = 'create'
        this.title="添加用户"
        // 显示对话框
        this.dialogFormVisible = true
        this.$nextTick(() => {
          // 表单清除验证
          this.$refs['dataForm'].clearValidate()
        })
      },
      // 添加对话框里，点击确定，执行添加操作
      createData(val) {
        if (!this.hasPerm('wind:add')) {
          return
        }
            this.temp.wstatus=val;
        // 表单校验
        this.$refs['dataForm'].validate((valid) => {
          // 所有的校验都通过
          if (valid) {
            this.isShow=true
            // 调用api里的sys里的user.js的ajax方法
            let posdata=qs.stringify({
            wTitle: this.temp.wtitle,
            wContent: this.temp.wcontent,
            wContributor: this.temp.sid,
            wCreateId: this.temp.sysStaff.sid,
            wStatus: this.temp.wstatus
          })
            add(posdata).then((response) => {
              
              // 关闭对话框
              this.dialogFormVisible = false
              // 刷新数据表格里的数据
              this.pageNum=1
              this.getList()
              // 显示一个通知
              this.$notify({
                title: '成功',
                message: '新增成功',
                type: 'success',
                duration: 2000
              })
              this.isShow=false
              this.yincang()
            }).catch(err=>{
              this.isShow=false
            })
          }
        })
        
      },
      // 显示修改对话框
      handleUpdate(row) {
        let oldv = row.wnew.mid;
        let sz = [];
        this.options.filter(val => {
          if (val.value === oldv) {
            sz.push(val.value);
          }else{
            if(typeof val.children!=='undefined'){
            val.children.filter(va=>{
              if (va.value === oldv) {
                sz.push(val.value);
                sz.push(va.value);
              } else {
                if(typeof va.children!=='undefined'){
                va.children.filter(v => {
                  if (v.value === oldv) {
                    sz.push(val.value);
                    sz.push(va.value);
                    sz.push(v.value);
                  }
                });
                } 
              }
            })
          }
          }
          
        });
      this.value=sz;
        this.temp = row;
        this.xianshi()
        if(!this.hasPerm('wind:update')){
          this.dis='inline-block'
        this.dis2='none'
          return
      }
      this.getStaffList()
        this.temp.wCreateTime=row.wcreateTime
        this.temp.sid=row.wnew.sid
        console.debug(this.temp)
        this.dis3='inline-block'
        // 将row里面与temp里属性相同的值，进行copy
        this.temp = Object.assign({}, row) // copy obj
        // 将对话框里的确定点击时，改为执行修改操作
        this.dialogStatus = 'update'
        // 修改标题
        this.title = '修改用户'
        this.$nextTick(() => {
          // 清除校验
          this.$refs['dataForm'].clearValidate()
        })
        
      },
      // 执行修改操作
      updateData(val) {
        if (!this.hasPerm('wind:update')) {
          return
        }
        this.$refs['dataForm'].validate((valid) => {
          // 表单校验通过
          if (valid) {
            if(val!==0){//判断状态
            this.temp.wstatus=val;
            }
            this.isShow=true
            console.debug(this.temp)
            // 进行ajax提交
            let posdata=qs.stringify({
            wTitle: this.temp.wtitle,
            wContent: this.temp.wcontent,
            wContributor: this.temp.sid,
            wid: this.temp.wid,
            wStatus: this.temp.wstatus
          })
            update(posdata).then((response) => {
             
              // 提交完毕，关闭对话框
              this.dialogFormVisible = false
              // 刷新数据表格
              this.getList()
              // 显示通知
              this.$notify({
                title: '成功',
                message:'修改成功',
                type: 'success',
                duration: 2000
              })
              this.isShow=false
              this.yincang()
            })
          }
        })
         
      },
      out(){
        this.yincang()
      },
      handleDelete() {
        if (!this.hasPerm('wind:delete')) {
          this.$message({
          showClose: true,
          message: '权限不足',
          type: 'warning'
        });
        return
        }
        // 先弹确认取消框
        let title='';
        if(this.multipleSelection.length<1){
          this.$message({
          showClose: true,
          message: '请选择删除信息',
          type: 'warning'
        });
        }else{
           if(this.multipleSelection.length==1){
              title=this.multipleSelection[0].wtitle;
              this.deleteid.push(this.multipleSelection[0].wid)
           }else{
             title='选中'
             this.multipleSelection.filter(row=>{
                this.deleteid.push(row.wid)
             })
           }
           this.$confirm('确认删除【'+title+'】的信息吗?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          // 调用ajax去后台删除
          console.debug(this.deleteid)
          deleteWind(this.deleteid).then((response) => {
            // 刷新数据表格
             this.pageNum=1;
            this.getList()
            // ajax去后台删除
            this.$notify({
              title: '成功',
              message: '删除成功',
              type: 'success',
              duration: 2000
            })
          })
          this.deleteid=[]
        })
        }
      },
    handleSizeChange(size) {
       this.deleteid=[];
      this.multipleSelection=[];
      this.pageRow = size;
      this.getList();
    },
    handleCurrentChange(currentPage) {
      this.deleteid=[];
      this.multipleSelection=[];
      this.pageNum = currentPage;
      this.getList();
    },
    handleSelectChangeLeft(rows){
      let self=this;
      self.multipleSelection= rows;
      console.debug(self.multipleSelection)
    },
    indexMethod(val){
      return ++val
    },
    xianshi(){
      this.dis='none'
      this.dis2='inline-block'
      
    },
    yincang(){
      this.dis='inline-block'
        this.dis2='none'
        this.temp.wstatus=1;
        this.sid=null
        this.dis3='none'
        this.melist=[],
        this.stafflist=[]
        this.getList()
    },
    bmChange(){
      this.temp.wnew.mid=this.$refs["bbb"].getCheckedNodes()[0].value
      this.getStaffList()
      this.dis3='inline-block'
      this.temp.sid=null
    },
    yanchi(){
      this.dis3='inline-block'
    }
    
    }
  }
</script>
<style scoped>
  [v-cloak]{
      display: none;
    }
</style>