<template>
  <div class="app-container">
    <div :style="{'display':dis}" style="width:100%">
    <div class="filter-container"  style="margin-top: -30px;margin-left:60px">
      <!-- v-waves -->
      <label>标题</label>&nbsp;&nbsp;
      <el-input v-model="sysTitle" placeholder="请输入制度流程标题" style="width: 200px;" class="filter-item"/>
      &nbsp;&nbsp;&nbsp;&nbsp;
       <label style="margin-left:60px">内容</label>&nbsp;&nbsp;
      <el-input v-model="sysContent" placeholder="请输入制度流程完善内容" style="width: 200px;" class="filter-item"/>
     <!--  <el-form-item> -->

       &nbsp;&nbsp;&nbsp;&nbsp;
          <el-button style="margin-left:60px"
            type="primary"
            @click="getList"
            class="el-icon-search" >查询
          </el-button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          <el-button
            type="primary"
            class="el-icon-refresh" 
            @click="resetSou">重置
          </el-button>
       <!--  </el-form-item> -->
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
      fit
      highlight-current-row
      style="width: 100%;margin-top: 20px"
      ref="multipleTable"
    >
    <el-table-column type="selection" width="70px" align="center"></el-table-column>
      <el-table-column label="序号" prop="index"  align="center" width="120px" type="index" :index="indexMethod">
        <!-- <template slot-scope="scope">
          <span>{{ scope.row.did }}</span>
        </template> -->
        
      </el-table-column>
        <el-table-column label="制度信息标题" prop="sysTitle"  align="center" width="450px">
        <template slot-scope="scope">

          <a style="color:#1890ff" @click="handleUpdate(scope.row)">{{ scope.row.sysTitle }}</a>
        </template>
        </el-table-column>
        
      <el-table-column label="创建人" prop="sysStaff"   align="center" width="230px">
        <template slot-scope="scope">
          <span>{{ scope.row.sysStaff.name }}</span>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" sortable prop="sysCreateTime"   align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.wcreateTime | comverTime('YYYY-MM-DD') }}</span>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页工具条  page当前页 total总记录数 limit每页显示多少条 pagination触发自定义事件，查询数据 @size-change="handleSizeChange"
          @current-change="handleCurrentChange"-->
     <div class="block" align="center" >
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
    <div :style="{'display':dis2}" style="margin-top: -30px">
      <el-form ref="dataForm" :rules="rules" :model="temp" label-position="center" label-width="130px" style="width: 95%; margin-left:40px;">
        <!--        数据校验要求prop值和temp.属性名一致-->
        <el-form-item  style="width:100%;height:30px;margin-left: -80px" align="right">
        <el-button type="success" :disabled="isShow" class="el-icon-top"  @click="dialogStatus==='update'?updateData():createData()">
          保存
        </el-button>
         <el-button class="el-icon-back" plain @click="out()">
          返回
        </el-button>
        </el-form-item>
        <el-form-item style="font-weight: bold;" label="制度信息标题" prop="sysTitle" >
          <el-input v-model="temp.sysTitle" placeholder="请输入制度信息标题" style="width:50%" />
        </el-form-item>
        <el-form-item style="font-weight: bold;" label="内容" prop="sysContent" >
          <quill-editor class="editor"  style="height:400px;width:85%;"
        ref="myQuillEditor"
        v-model="temp.sysContent"
       >
        </quill-editor>
        </el-form-item>
        <el-form-item style="font-weight: bold;margin-top:120px;" label="创建时间" prop="wcreateTime" >
          <el-date-picker disabled="disabled" 
    style="width: 50%"
    type="date"
    v-model="temp.sysCreateTime"
    :format="'yyyy-MM-dd HH:mm:ss'">
</el-date-picker>
        </el-form-item>
        <el-form-item style="font-weight: bold;" label="创建者ID" prop="sysStaff" >
          <el-input v-model="temp.sysStaff.sid" disabled="disabled" style="width:50%"/>
        </el-form-item>
        <el-form-item style="font-weight: bold;" label="创建者姓名" prop="sysStaff" >
          <el-input v-model="temp.sysStaff.name" disabled="disabled" style="width:50%"/>
        </el-form-item>
      </el-form>
       
    </div>
  </div>
</template>

<script>
import { add, update, list, deleteSystemprocess } from '@/api/riskmanagement/systemprocess'
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
      return {
        dis:'inline-block',
        dis2:'none',
        tableKey: 0,
        list: [], // 后台返回，给数据表格展示的数据
        total: 0, // 总记录数
        listLoading: true, // 是否使用动画
          pageNum: 1, // 分页需要的当前页
          pageRow:5, // 分页需要的每页显示多少
          sysTitle: '',
          sysContent:'',
        temp: { // 添加、修改时绑定的表单数据
          sysid: undefined,
          sysTitle: '',
          sysContent: '',
          sysStaff: {
            name: '',
            sid: 0
          },
          sysCreateTime:new Date(),
          sid:null
        },
        title: '添加', // 对话框显示的提示 根据dialogStatus create
        dialogStatus: '', // 表示表单是添加还是修改的
        rules: {
          // 校验规则
          //dTitle:  [{ required: true, message: '标题必填', trigger: 'blur' }],
          //dFileName: [{ required: true, message: '请上传文件', trigger: 'change'}]
          sysTitle: [{required:true,message:'标题不能为空',trigger:['blur','change']}]
        },
        isShow:false,
          wew:{},
        multipleSelection:[],
        deleteid:[]
      }
    },
    // 创建实例时的钩子函数
    created() {
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
          if (!this.hasPerm('systemprocess:list')) {
          return
        }
        // 开始转圈圈
        this.listLoading = true
        // debugger // 调试
        /* let data=qs.stringify({
          account: this.listQuery.account
        }) */
        list(this.pageNum,this.pageRow,this.sysTitle,this.sysContent).then(response => {
          this.list=response.systemprocess.records;
          this.total=(response.systemprocess.total)
          console.debug(this.list)
          // 转圈圈结束
          this.listLoading = false
        })
      },
      resetSou(){
        this.sysTitle=''
        this.sysContent=''
      },
      // 重置表单数据
      resetTemp() {
        this.temp = {
          sysid: undefined,
          sysTitle: '',
          sysContent: '',
          sysStaff: {
            name: '',
            sid: 0
          },
          sysCreateTime:new Date(),
          sid:null
        }

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
      createData() {
        // 表单校验
        this.$refs['dataForm'].validate((valid) => {
          // 所有的校验都通过
          if (valid) {
            this.isShow=true
            // 调用api里的sys里的user.js的ajax方法
            add(this.temp).then((response) => {
              
              // 关闭对话框
              this.dialogFormVisible = false
              // 刷新数据表格里的数据
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
            })
          }
        })
        
      },
      // 显示修改对话框
      handleUpdate(row) {
        this.temp = row;
        this.xianshi()
        console.debug(this.temp)
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
      updateData() {
        this.$refs['dataForm'].validate((valid) => {
          // 表单校验通过
          if (valid) {
            this.isShow=true
            // 进行ajax提交
            update(this.temp).then((response) => {
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
              title=this.multipleSelection[0].sysTitle;
              this.deleteid.push(this.multipleSelection[0].sysid)
           }else{
             title='选中'
             this.multipleSelection.filter(row=>{
                this.deleteid.push(row.sysid)
             })
           }
           this.$confirm('确认删除【'+title+'】的信息吗?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          // 调用ajax去后台删除
          console.debug(this.deleteid)
          deleteSystemprocess(this.deleteid).then((response) => {
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
        this.sid=null
    }
    
    }
  }
</script>