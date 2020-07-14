<template>
  <div>
    <el-form :inline="true" class="demo-form-inline" v-bind:style="{display:tf}">
      <div align="center" style="margin-top: 30px">
        <el-form-item>
          <el-select
            v-model="search"
            class="search_name"
            placeholder="报表类型">
            <el-option label="季报" value="季报"></el-option>
            <el-option label="月报" value="月报"></el-option>
            <el-option label="半年报" value="半年报"></el-option>
            <el-option label="及时报" value="及时报"></el-option>
            <el-option label="重要节点报" value="重要节点报"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-date-picker
            v-model="starttime"
            type="datetime"
            class="search_name"
            placeholder="请选择开始时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item>
          <el-date-picker
            v-model="endtime"
            type="datetime"
            class="search_name"
            placeholder="请选择结束时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item>
          <el-button
            type="primary"
            @click="onSearch()"
            class="el-icon-search" >查询
          </el-button>
          <el-button
            type="primary"
            class="el-icon-refresh" @click="onrest">重置
          </el-button>
        </el-form-item></div><br/>
      <div><el-form-item>
        <el-button type="primary" class="el-icon-plus" @click="add">新增</el-button>
        <el-button type="primary" class="el-icon-delete" @click="del">删除</el-button></el-form-item></div>
      <el-table
        :data="tableData.slice((currentPage-1)*pageSize,currentPage*pageSize)"
        border
        style="width: 100%"  ref="multipleTable" :cell-style='cellStyle':header-cell-style='rowClass' v-loading="listLoading">
        <el-table-column type="selection" width="55px"></el-table-column>
        <el-table-column
          prop="id"
          label="序号"
          width="80">
        </el-table-column>
        <el-table-column
          prop="reportType"
          label="报表类型"
          width="180">
          <template slot-scope="scope">
            <a  @click="handleEdit(scope.$index, scope.row)"
                target="_blank"
                class="buttonText" style="color: #1890ff">{{scope.row.reportType}}</a>
          </template>
        </el-table-column>
        <el-table-column
          prop="report"
          label="文件名"
          width="260">
          <template slot-scope="scope">
            <a  @click="to(scope.$index, scope.row)"
                target="_blank"
                class="buttonText" style="color: #1890ff">{{scope.row.report}}</a>
          </template>
        </el-table-column>
        <el-table-column
          prop="url"
          label="路径"
          width="180"  v-if="false">
        </el-table-column>
        <el-table-column
          prop="newTime"
          label="创建时间">
        </el-table-column>
        <el-table-column
          prop="eid[0].name"
          label="创建人">
        </el-table-column>
        <el-table-column prop="typeconcrete" label="详细类型" v-if="false">

        </el-table-column>
      </el-table>
      <div class="block" align="center">
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="currentPage4"
          :page-sizes="[10, 20, 30, 40,50]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
        </el-pagination>
      </div>
    </el-form>
    <div v-bind:style="{display:ad}" style="background-color: lightgray;width: 100%;height: 700px" >
      <el-main>      <el-form :inline="true" :model="userInfo" class="demo-form-inline" label-width="220px" :rules="rules" ref="ruleForm">
        <div style="background-color: white;width: 100%;height: 65px;position:fixed; top:50px; left:-1px;z-index:2 ;"  >
          <br/>
          <div align="right" ><el-form-item >
            <el-button type="primary" class="el-icon-edit" align="right" @click="submitUser('ruleForm')" v-bind:style="{display:bc}">保存</el-button>
            <el-button type="primary" class="el-icon-edit" align="right" @click="gxMethod" v-bind:style="{display:gx}">更新</el-button>
            <el-button type="primary" class="el-icon-back" @click="back('ruleForm')">返回</el-button></el-form-item></div></div>
        <br/>
        <div style="background-color: white;margin-top: 7px;z-index:3;">
          <el-input v-model="userInfo.id" placeholder="编号" type="hidden"></el-input>
          <el-input v-model="userInfo.url" placeholder="地址" type="hidden"></el-input>

          <el-form-item label="报表文件">
            <el-upload
              class="upload-demo"
              action="https://jsonplaceholder.typicode.com/posts/"
              :on-change="handleChange"
              :on-preview="handlePreview"
              accept=".doc,.docx,.pdf,.txt,.xlsx"
              :http-request="modeUpload"
              :limit="1"
              :file-list="fileList">
              <el-button size="small" type="primary">上传文件</el-button>
              <div slot="tip" class="el-upload__tip">只能上传单个word,.doc,.docx,.pdf,.txt,.xlsx文件，且不超过50M</div>
            </el-upload>
          </el-form-item><br/>

          <el-form-item label="报表类型" prop="reportType">
            <el-select
              v-model="userInfo.reportType"
              class="search_name"
              placeholder="请选择报表类型" @change="changes">
              <el-option label="季报" value="季报"></el-option>
              <el-option label="月报" value="月报"></el-option>
              <el-option label="半年报" value="半年报"></el-option>
              <el-option label="及时报" value="及时报"></el-option>
              <el-option label="重要节点报" value="重要节点报"></el-option>
            </el-select>
          </el-form-item><br/>

          <el-form-item :label="placeholders">
            <el-select
              v-model="userInfo.typeconcrete"
              class="bbxq"
              :placeholder="placeholders">
              <el-option
                v-for="item in options"
                :key="item.id"
                :label="item.name"
                :value="item.id">
              </el-option>
            </el-select>
          </el-form-item><br/>
          <el-form-item label="创建者">
            <el-input v-model="userInfo.lCreateName" placeholder="创建者" style="width: 300px" disabled="disabled"></el-input>
          </el-form-item><br/>
          <el-form-item label="创建时间">
            <el-date-picker v-model="userInfo.lCreateTime" placeholder="创建时间" style="width: 300px" type="datetime" disabled="disabled"></el-date-picker>
          </el-form-item>

        </div>
      </el-form></el-main>
    </div>
  </div>
</template>

<script>
  import qs from 'qs'
  import { mapGetters } from 'vuex'
  import { list,findwhere,addreport,del,gxpost } from '@/api/inspect/report'
  import { fileUpload } from '@/api/daily/supervise'

  export default {
    computed: {
      ...mapGetters([
        'nickname',
        'userId'
      ])
    },
    created() {
      this.initList()
    },
    methods:{
      to(index,row){
        window.open("http://localhost:8080/upload/"+row.url)
      },
      //设置表格内容居中
      cellStyle({row, column, rowIndex, columnIndex}){
        return 'text-align:center';
      },
      rowClass({row, rowIndex}){//设置表头居中
        return 'text-align:center';
      },
      //el-选择框选中值变化
      changes(val){
        console.log('val'+val)
        if(val==='季报'){
          this.placeholders='请选择季度'
          this.options=[{id:1,name:'第一季度'},{id:2,name:'第二季度'},{id:3,name:'第三季度'},{id:4,name:'第四季度'}]

        }
        else if(val==='月报'){
          this.placeholders='请选择月份'
          this.options=[{id:1,name:'一月'},{id:2,name:'二月'},{id:3,name:'三月'},{id:4,name:'四月'},{id:5,name:'五月'},{id:6,name:'六月'},{id:7,name:'七月'},{id:8,name:'八月'},{id:9,name:'九月'},{id:10,name:'十月'},{id:11,name:'十一月'},{id:12,name:'十二月'}]
        }
        else if(val==='半年报'){
          this.placeholders='请选择半年'
          this.options=[{id:1,name:'上半年'},{id:2,name:'下半年'}]
        }
        else if(val==='重要节点报'){
          this.placeholders='请选择重要节点'
          this.options=[{id:1,name:'春节'},{id:2,name:'端午节'},{id:3,name:'中秋节'},{id:4,name:'国庆节'},{id:5,name:'元旦节'},{id:6,name:'劳动节'},{id:7,name:'端午节'}]
        }
        else if(val==='及时报'){
          this.placeholders='请选择重要时间'
          this.options=[{id:1,name:'1点'},{id:2,name:'两点'},{id:3,name:'三点'},{id:4,name:'四点'},{id:5,name:'五点'}]
        }
      },
      //文件预览
      handlePreview(file) {
        console.log('file'+JSON.stringify(file))
        const userAgent = navigator.userAgent;
        if (!!window.ActiveXObject || "ActiveXObject" in window) {
          alert('推荐谷歌进行文件预览')
        }else{
          window.open(file.url) //blob格式地址
        }
      },handleChange(file, fileList) {
        this.userInfo.report=file.name
      },
      modeUpload: function(item) {
        // console.log(item.file);
        this.file = item.file
        const fd = new FormData()
        fd.append('filename', this.file)
        fileUpload(fd
        ).then(response => {
          this.$message({
            title: '成功',
            message: response.message,
            type: 'success',
            duration: 2000

          })
          this.fileList=[{name:this.file.name,url:"http://localhost:8080/upload/"+response.url}]
          this.userInfo.url=response.url
        })
        console.log('ce是'+JSON.stringify(this.file))
      },
      //重置
      onrest(){
        this.search=''
        this.starttime=''
        this.endtime=''
      },
      //多条件查询
      onSearch() {

        let postData = qs.stringify({
          reportType:this.search,
          starttime:this.starttime,
          endtime:this.endtime
        });
        this.listLoading = true
        findwhere(postData).then((response) =>{
          this.currentPage = 1
          this.tableData = response.list
          console.debug(this.tableData)
          this.total=response.list.length
          this.listLoading=false
        })
      },
      //初始化页面
      initList() {
        this.listLoading=true
        list(this.listQuery).then(response =>{
          console.debug(response.info)
          this.tableData = response.list
          this.total = response.list.length
          this.listLoading=false
        })
      },add(){
        this.fileList=[]//清空upload
        this.tf='none'
        this.ad=''
        this.bc=''
        this.gx='none'
        this.userInfo={}
        this.userInfo.lCreateName=this.nickname,
          this.$set(this.userInfo,'lCreateTime',new Date())

      },
      //返回
      back(formName){this.ad='none',this.tf='' ,this.$refs[formName].resetFields(), this.initList();
      },//新增提交
      submitUser(formName){
        this.$refs[formName].validate((valid) => {
          if (valid) {
            let endtime = new Date(this.userInfo.lCreateTime).toJSON();
            this.userInfo.lCreateTime = new Date(+new Date(endtime) + 8 * 3600 * 1000)
              .toISOString()
              .replace(/T/g, " ")
              .replace(/\.[\d]{3}Z/, "")
            let posdata=qs.stringify({
              reportType:this.userInfo.reportType,
              report:this.userInfo.report,
              newTime:this.userInfo.lCreateTime,
              sid:this.userId,
              status:0,
              typeconcrete:this.userInfo.typeconcrete,
              url:this.userInfo.url

            })
            addreport(posdata).then((response)=>{
              this.tf='';
              this.ad='none'
              this.bc='',//保存按钮显示
                this.gx='none',//更新按钮不显示
                this.$refs[formName].resetFields()
              this.initList();
              this.$notify({
                title: '成功',
                message: '新增成功',
                type: 'success',
                duration: 2000
              })
            })
          } else {
            console.log('error submit!!');
            return false;
          }
        });

      },//点击列编辑
      handleEdit(index, row){
        this.tf='none';
        this.ad=''//编辑/审核页面出来,
        this.bc='none'//保存按钮隐藏
        this.gx=''//更新按钮显示
        this.changes(row.reportType)
        this.userInfo=row
        this.userInfo.typeconcrete=Number(row.typeconcrete)
        console.log('this.ursInfo.url'+this.userInfo.url)
        this.fileList=[{name:this.userInfo.report,url:"http://localhost:8080/upload/"+this.userInfo.url}]
        /* 赋值实时设置当前时间*/
        this.$set(this.userInfo,'lCreateTime',row.newTime)
        this.userInfo.lCreateName=row.eid[0].name
      },//删除
      del(){
        var data = this.$refs.multipleTable.selection;
        console.log("11"+data)
        if(JSON.stringify(data)=='[]'){
          this.$notify({
            title: '温馨提示',
            message: '请选择一行进行删除',
            type: 'success',
            duration: 2000
          })
        }
        else {
          let postData = qs.stringify({
            test:JSON.stringify(data)
          });

          console.debug('选中行数据'+JSON.stringify(data))
          del(postData).then((response) =>{
            this.initList();
            this.$notify({
              title: '成功',
              message: response.message,
              type: 'success',
              duration: 2000
            })
          })
        }
      },//更新
      gxMethod(){
        let posdata=qs.stringify({
          id:this.userInfo.id,
          reportType:this.userInfo.reportType,
          report:this.userInfo.report,
          url:this.userInfo.url,
          typeconcrete:this.userInfo.typeconcrete
        })
        gxpost(posdata).then((response)=>{
          this.initList();
          this.$notify({
            title: '成功',
            message: response.message,
            type: 'success',
            duration: 2000
          })
          this.tf='';
          this.ad='none'
        })
      },
      handleSizeChange(size) {
        this.pageSize = size;
        console.log(this.pageSize)
      },
      handleCurrentChange(currentPage) {
        this.currentPage = currentPage;
        console.log(this.currentPage)  //点击第几页
      }
    },
    data() {
      return { rules: {
          reportType: [
            {  required: true, message: '请选择报表类型', trigger: 'blur' }
          ]

        },
        placeholders:'请选择',
        fileList:[],
        tf:'',//父页面
        ad:'none',//新增、审核页面
        bc:'',//保存按钮显示
        gx:'none',//更新按钮不显示
        iconFormVisible: false,
        userInfo: {},
        dialogTitle: '增加',
        rowIndex: null,
        search:'',
        currentPage4: 1,
        pageSize:10,
        total:0,
        currentPage:1,
        tableData: [],
        options:[],
        starttime:'',
        endtime:'',
        listLoading:true
      }
    }
  }
</script>

