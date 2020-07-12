<template>
  <div>
    <el-form :inline="true" class="demo-form-inline" v-bind:style="{display:tf}">
      <div align="center" style="margin-top: 30px">
        <el-form-item>
          <el-input
            v-model="title"
            class="search_name"
            placeholder="请输入廉政教育标题">
          </el-input>
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
          <el-input
            v-model="content"
            class="search_name"
            placeholder="请输入廉政教育内容">
          </el-input>
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
          prop="title"
          label="标题"
          width="490">
          <template slot-scope="scope">
            <a  @click="handleEdit(scope.$index, scope.row)"
                target="_blank"
                class="buttonText" style="color: #1890ff">{{scope.row.title}}</a>
          </template>
        </el-table-column>
        <el-table-column
          prop="source"
          label="资讯来源" v-if="false"></el-table-column>

        <el-table-column
          prop="type"
          label="资讯类型" ></el-table-column>

        <el-table-column
          prop="createname"
          label="创建者"
          width="180" >
        </el-table-column>
        <el-table-column
          prop="time"
          label="发布时间">
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
      <el-main>      <el-form :inline="true" :model="userInfo" :rules="rules" ref="ruleForm" class="demo-form-inline" label-width="220px">
        <div style="background-color: white;width: 100%;height: 65px;position:fixed; top:50px; left:-1px;z-index:2 ;"  >
          <br/>
          <div align="right" ><el-form-item >
            <el-button type="primary" class="el-icon-edit" align="right" @click="submitUser('ruleForm')" v-bind:style="{display:bc}">保存</el-button>
            <el-button type="primary" class="el-icon-edit" align="right" @click="gxMethod" v-bind:style="{display:gx}">更新</el-button>
            <el-button type="primary" class="el-icon-back" @click="back('ruleForm')">返回</el-button></el-form-item></div></div>
        <br/>
        <div style="background-color: white;margin-top: 7px;z-index:3;">
          <el-input v-model="userInfo.id" placeholder="序号" type="hidden"></el-input>
          <el-form-item label="资讯标题" prop="title">
            <el-input v-model="userInfo.title" placeholder="请输入资讯标题" style="width: 400px"></el-input>
          </el-form-item><br/>
          <el-form-item label="资讯来源" prop="source">
            <el-input v-model="userInfo.source" placeholder="请输入资讯来源" style="width: 400px"></el-input>
          </el-form-item><br/>
          <el-form-item label="资讯类型" prop="type">
            <el-select v-model="userInfo.type" placeholder="请选择资讯类型" style="width: 400px">
              <el-option label="警钟长鸣" value="警钟长鸣"></el-option>
              <el-option label="领导讲话" value="领导讲话"></el-option>
              <el-option label="文件制度" value="文件制度"></el-option>
              <el-option label="廉政要闻" value="廉政要闻"></el-option>
            </el-select>
          </el-form-item><br/>
          <el-form-item label="资讯发布时间" prop="time">
            <el-date-picker v-model="userInfo.time" type="datetime" placeholder="请选择资讯发布时间" style="width: 400px"></el-date-picker>
          </el-form-item>
          <el-form-item label="创建时间" v-if="false">
            <el-date-picker v-model="userInfo.createTime" type="datetime" placeholder="请选择资讯发布时间" style="width: 300px"></el-date-picker>
          </el-form-item>
          <el-form-item label="资讯内容" prop="content">
            <quill-editor v-model="userInfo.content" placeholder="资讯内容" class="myQuillEditor" :options="editorOption" style="width: 830px; height: 400px; margin-bottom: 80px" ></quill-editor>
          </el-form-item>


        </div>
      </el-form></el-main>
    </div>
  </div>
</template>

<script>
  import qs from 'qs'
  import { mapGetters } from 'vuex'
import {findAllEducation,addEcucation,delEcucation,updateEducation,findwhereEducation} from '@/api/educationpolitics/educationpolitics'
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
    //设置表格内容居中
    cellStyle({row, column, rowIndex, columnIndex}){
      return 'text-align:center';
    },
    rowClass({row, rowIndex}){//设置表头居中
      return 'text-align:center';
    },
    //重置
    onrest(){
      this.title=''
      this.content=''
      this.starttime=''
      this.endtime=''
    },
    //多条件查询
    onSearch() {
      let postData = qs.stringify({
        title:this.title,
        content:this.content,
        starttime:this.starttime,
        endtime:this.endtime
      });
      this.listLoading = true
      findwhereEducation(postData).then((response) =>{
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
      findAllEducation().then(response =>{
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
      this.$set(this.userInfo,'createTime',new Date())
      this.$set(this.userInfo,'time',new Date())

    },
    //返回
    back(formName){this.ad='none',this.$refs['ruleForm'].resetFields();  this.tf='', this.initList();
    },//新增提交
    submitUser(formName){
      this.$refs[formName].validate((valid) => {
        if (valid) {
          let createTime = new Date(this.userInfo.createTime).toJSON();
          this.userInfo.createTime = new Date(+new Date(createTime) + 8 * 3600 * 1000)
            .toISOString()
            .replace(/T/g, " ")
            .replace(/\.[\d]{3}Z/, "")
          let time = new Date(this.userInfo.time).toJSON();
          this.userInfo.time = new Date(+new Date(time) + 8 * 3600 * 1000)
            .toISOString()
            .replace(/T/g, " ")
            .replace(/\.[\d]{3}Z/, "")
          let posdata=qs.stringify({
            title:this.userInfo.title,
            source:this.userInfo.source,
            type:this.userInfo.type,
            time:this.userInfo.time,
            createTime:this.userInfo.createTime,
            content:this.userInfo.content,
            createId:this.userId,
            createname:this.nickname,
            staus:0
          })
          addEcucation(posdata).then((response)=>{
            this.tf='';
          this.ad='none'
          this.bc='',//保存按钮显示
            this.gx='none',//更新按钮不显示
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
      this.userInfo=row
    },//删除
    del(){
      var data = this.$refs.multipleTable.selection;
      console.log("11"+JSON.stringify(data))
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
        delEcucation(postData).then((response) =>{
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

      let endtime1 = new Date(this.userInfo.time).toJSON();
      this.userInfo.time = new Date(+new Date(endtime1)+ 8 * 3600 * 1000)
        .toISOString()
        .replace(/T/g, " ")
        .replace(/\.[\d]{3}Z/, "")
      let posdata=qs.stringify({
        id:this.userInfo.id,
        title:this.userInfo.title,
        source:this.userInfo.source,
        type:this.userInfo.type,
        time:this.userInfo.time,
        content:this.userInfo.content,
      })
      updateEducation(posdata).then((response)=>{
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
        title: [
          { required: true, message: '请输入资讯标题', trigger: 'blur' },
          { min: 3, message: '长度得大于3 字符', trigger: 'blur' }
        ],
        source: [
          { required: true, message: '请输入资讯标题', trigger: 'blur' },
        ],
        type: [
          { required: true, message: '请选择资讯类型', trigger: 'change' }
        ],
        content: [
          { required: true, message: '请输入资讯内容', trigger: 'change' }
        ]
      },
      editorOption: {},
      fileList:[],
      tf:'',//父页面
      ad:'none',//新增、审核页面
      bc:'',//保存按钮显示
      gx:'none',//更新按钮不显示
      iconFormVisible: false,
      userInfo: {smoproperty:[]},
      dialogTitle: '增加',
      rowIndex: null,
      title:'',
      starttime:'',
      endtime:'',
      content:'',
      currentPage4: 1,
      pageSize:10,
      total:0,
      currentPage:1,
      times:'',
      tableData: [],
      options:[],
      listLoading:true
    }
  }
  }
</script>


