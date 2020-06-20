<template>
  <div class="app-container">
    <div class="filter-container"  align="center" style="margin-top: 20px">
      <!-- v-waves -->
      <label>标题</label>&nbsp;&nbsp;
      <el-input v-model="dtitle" placeholder="请输入资料锦集标题" style="width: 200px;" class="filter-item"/>
     <!--  <el-form-item> -->

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
       <!--  </el-form-item> -->
    </div>
    <div style="margin-top:15px">
      <el-button type="primary" class="el-icon-plus" @click="handleCreate">新增</el-button>
      <el-button type="primary" class="el-icon-delete" @click="handleDelete">删除</el-button>
      <el-button type="primary" class="el-icon-download"  @click="handleOutFile">导出文件</el-button></div>
    <!--  数据表格  :data="list.slice((listQuery.pageNum-1)*listQuery.pageRow,listQuery.pageNum*listQuery.pageRow)"-->
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
    <el-table-column type="selection" width="60px" align="center"></el-table-column>
      <el-table-column label="序号" prop="index"  align="center" width="90px" type="index" :index="indexMethod">
        <!-- <template slot-scope="scope">
          <span>{{ scope.row.did }}</span>
        </template> -->
      </el-table-column>
        <el-table-column label="标题" prop="dtitle"  align="center" width="210px">
        <template slot-scope="scope">

          <a style="color:#1890ff" @click="handleUpdate(scope.row)">{{ scope.row.dtitle }}</a>
        </template>
        </el-table-column>
        <el-table-column label="文件名"  prop="dfileName"  align="center" width="210px">
        <template slot-scope="scope">
          <span>{{ scope.row.dfileName }}</span>
        </template>
      </el-table-column>
      <el-table-column label="创建人" prop="sysStaff"   align="center" width="210px">
        <template slot-scope="scope">
          <span>{{ scope.row.sysStaff.name }}</span>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" sortable prop="dcreateTime"  align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.dcreateTime | dateFilter }}</span>
        </template>
      </el-table-column>
      <!--     自定义列-->
      <!-- <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="{row}">
          <el-button type="primary" size="mini" @click="handleUpdate(row)">
            修改
          </el-button>
          <el-button size="mini" type="danger" @click="handleDelete(row)">
            删除
          </el-button>
        </template>
      </el-table-column> -->
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

    <!--  绑定了title，是一个数组里取的，表示是修改的标题还是添加的标题
      visible.sync 对话框是否显示
    -->
    <el-dialog :title="title" :visible.sync="dialogFormVisible" style="width: 100%">
      <!--
          rules:校验规则
          model:数据绑定
      -->
      <el-form ref="dataForm" :rules="rules" :model="temp" label-position="left" label-width="70px" style="width: 80%; margin-left:40px;">
        <!--        数据校验要求prop值和temp.属性名一致-->
        
        <el-form-item label="标题" prop="dtitle" >
          <el-input placeholder="请输入资料锦集标题" v-model="temp.dtitle" style="width:80%" />
        </el-form-item>
        <el-form-item label="文件名" prop="dfileName" ref="fileName" >
          <el-upload  style="width:80%"
  class="upload-demo"
  v-model="temp.dfileName"
  ref="upload"
  action="https://localhost:8080/imp/import"
  :on-remove="fileRemove"
  :on-change="handleImgChange1"
  accept=".doc,.docx,.pdf,.txt,.xlsx"
  :file-list="fileList"
  :limit="2"
  :auto-upload="false">
  <el-button slot="trigger" class="el-icon-upload" size="small" type="primary">选取文件</el-button>
  <div slot="tip"  class="el-upload__tip">只能上传单个txt/word/pdf文件，且不超过50M</div>
  </el-upload>
        </el-form-item>
        <el-form-item label="创建时间" prop="dcreateTime" >
          <el-date-picker disabled="disabled" 
    style="width: 80%"
    type="date"
    v-model="temp.dCreateTime"
    :format="'yyyy-MM-dd HH:mm:ss'">
</el-date-picker>
        </el-form-item>
        <el-form-item label="创建人" prop="sysStaff" >
          <el-input v-model="temp.sysStaff.name" disabled="disabled" style="width:80%"/>
        </el-form-item>
        <el-form-item label="审核状态" prop="status">
          <el-input v-model="temp.status" disabled="disabled" style="width:80%"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
       
        <!--
          dialogStatus==='create'?createData():updateData()
          dialogStatus需要我们根据情况去改变
        -->
        <el-button type="primary" :disabled="isShow"  v-show="btnShowTj"  @click="dialogStatus==='update'?updateData(2):createData(2)">
          提交审核
        </el-button>
        <el-button type="primary" :disabled="isShow" v-show="btnShowTs" @click="updateData(3)">
          通过审核
        </el-button>
        <el-button type="primary" :disabled="isShow"  @click="dialogStatus==='update'?updateData(0):createData(0)">
          保存
        </el-button>
         <el-button @click="resetTemp">
          取消
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { add, update, list, deleteDatakamset,imp } from '@/api/culture/datakamset'
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
        tableKey: 0,
        list: [], // 后台返回，给数据表格展示的数据
        total: 0, // 总记录数
        listLoading: true, // 是否使用动画
          pageNum: 1, // 分页需要的当前页
          pageRow:5, // 分页需要的每页显示多少
          dtitle: '',
          dstatus: 1,
        temp: { // 添加、修改时绑定的表单数据
          uid: undefined,
          dtitle: '',
          dfileName: '',
          dFile: '',
          sysStaff: {
            name: '',
            sid: 0
          },
          dCreateTime:new Date(),
          status: '',
          dstatus:1,
          fileList: []
        },
        i:0,
        isShow:false,
        btnShowTs:false,
        btnShowTj:false,
        fileList: [],
        file:{},
        title: '添加', // 对话框显示的提示 根据dialogStatus create
        dialogFormVisible: false, // 是否显示对话框
        dialogStatus: '', // 表示表单是添加还是修改的
        rules: {
          // 校验规则
          dtitle:  [{ required: true, message: '标题不能为空', trigger: ['blur','change']}],
          dfileName: [{ required: true, message: '请上传文件', trigger: 'change'}]
        },
        multipleSelection:[],
        deleteid:[],
        formData:null,
        fileAgin:null
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
          if (!this.hasPerm('datacollection:list')) {
          return
        }
        // 开始转圈圈
        this.listLoading = true
        // debugger // 调试
        /* let data=qs.stringify({
          account: this.listQuery.account
        }) */
        list(this.pageNum,this.pageRow,this.dtitle).then(response => {
          this.list=response.records;
          this.total=(response.total)
          
          console.debug(this.list)
          // 转圈圈结束
          this.listLoading = false
        })
      },
      resetSou(){
        this.dtitle=''
      },
      // 重置表单数据
      resetTemp() {
        
        this.temp = {
          uid: undefined,
          dtitle: '',
          dfileName: '',
          dFile: '',
          sysStaff: {
            name: '',
            sid: 0
          },
          dCreateTime:new Date(),
          status: '',
          dstatus:1
        }
        this.fileList=[]
        this.file={}
        this.i=0
        this.fileAgin=''
        this.yincang()
        this.dialogFormVisible = false
      },
      // 显示添加的对话框
      handleCreate () {
        
        // 重置表单数据
        this.resetTemp()
        this.i=3
        this.xianshi()
        if(this.temp.dstatus===1){
          this.temp.status='创建'
        }else if(this.temp.dstatus===2){
          this.temp.status='待审'
        }else{
          this.temp.status='已审核'
        }
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
        
        if (!this.hasPerm('datacollection:add')) {
          return
        }
        if(val!==0){
            this.temp.dstatus=val;
        }
        // 表单校验
        this.$refs['dataForm'].validate((valid) => {
          // 所有的校验都通过
              if (valid) {
        imp(this.formData).then((response)=>{
          this.temp.dFile=response.dFile
            console.debug(this.temp)
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
              this.i=0;
          
          })
          })
          }
          
        })
      },
      // 显示修改对话框
      handleUpdate(row) {
         this.fileAgin=row.dfileName
        this.temp = row;
        if(this.temp.dstatus===1){
          this.temp.status='创建'
        }else if(this.temp.dstatus===2){
          this.temp.status='待审'
        }else{
          this.temp.status='已审核'
        }
        this.fileList=[{name:row.dfileName,url:row.dfile}];
        this.temp.dCreateTime=row.dcreateTime
        console.debug(this.temp)
        this.xianshi()
        // 将对话框里的确定点击时，改为执行修改操作
        this.dialogStatus = 'update'
        // 修改标题
        this.title = '修改用户'
        // 显示修改对话框
        this.dialogFormVisible = true
        this.$nextTick(() => {
          // 清除校验
          this.$refs['dataForm'].clearValidate()
        })
      },
      // 执行修改操作
      updateData(val) {
        if (!this.hasPerm('datacollection:update')) {
          return
        }
      if( this.fileAgin!==this.fileList[0].name){
          this.i=1;
      }
      if(val!==0){//判断状态
            this.temp.dstatus=val;
            }
      console.debug(this.i)
        if(this.i===1){
            imp(this.formData).then((response)=>{
          this.temp.dfile=response.dFile
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
                message: '修改成功',
                type: 'success',
                duration: 2000
              })
              this.isShow=false
              this.yincang()
            })
          }
        })
        })
        }else if(this.i===0){
          this.temp.dfile='1'
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
                message: '修改成功',
                type: 'success',
                duration: 2000
              })
              this.isShow=false
              this.yincang()
            })
          }
          this.temp.dfile=''
        })
        }
        this.i=0;
        
      },
      handleOutFile(){
        if (!this.hasPerm('datacollection:out')) {
          return
        }
        this.multipleSelection.forEach(row=>{
           let path=this.virtualIp+row.dfile
           var aDom = document.createElement('a')
        var evt = document.createEvent('HTMLEvents')
        evt.initEvent('click', false, false)
        aDom.download = name
        aDom.href = path
        aDom.dispatchEvent(evt)
        aDom.click()
          })
      },
      handleDelete() {
        if (!this.hasPerm('datacollection:delete')) {
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
              title=this.multipleSelection[0].dtitle;
              this.deleteid.push(this.multipleSelection[0].did)
           }else{
             title='选中'
             this.multipleSelection.filter(row=>{
                this.deleteid.push(row.did)
             })
           }
           this.$confirm('确认删除【'+title+'】的信息吗?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          // 调用ajax去后台删除
          console.debug(this.deleteid)
          deleteDatakamset(this.deleteid).then((response) => {
            // 刷新数据表格
            this.getList()
            // ajax去后台删除
            this.$notify({
              title: '成功',
              message:'删除成功',
              type: 'success',
              duration: 2000
            })
          })
        }).catch(() => {
          this.deleteid=[];
        });
        }
      },
      handleImgChange1(file, fileList, name) {
        
        const isLt2M = file.size / 1024  < 500;
      if(!isLt2M){
        console.debug(this.dfileName)
        this.$message({
          showClose:true,
          message:'文件不能超过500k',
          type: 'warning'
        })
        if(fileList.length==2){
        this.fileList=fileList.slice(0,1)
        }else{
          this.fileList=fileList.slice(1)
        }
      }else{
         this.file=file.raw
        if(fileList){
        this.fileList=fileList.slice(-1)
      }
      }
       this.formData= new FormData();
        this.formData.append("file", this.file);
        this.temp.dfileName=this.file.name
      this.$refs['dataForm'].validate((valid) => {})
       
    },
    xianshi(){
      if(this.temp.dstatus===1){
        this.btnShowTj=true;
      }
      if(this.temp.dstatus===2){
        this.btnShowTs=true;
      }
    },
    yincang(){
        this.btnShowTj=false;
        this.btnShowTs=false;
        this.temp.dstatus=1;
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
    fileRemove(file, fileList){
      this.file={}
      this.temp.dfileName=''
    },
    indexMethod(val){
      return ++val
    }
    },
    filters:{
      dateFilter(date,format="YYYY-MM-DD"){
        return moment(date).format(format||"YYYY-MM-DD")
      }
    }
  }
</script>