<template>
  <div>
    <el-form :inline="true" class="demo-form-inline" v-bind:style="{display:tf}">
      <div align="center" style="margin-top: 30px">
        <el-form-item>
          <el-input
            v-model="title"
            class="search_name"
            placeholder="请输入潭烟风貌标题">
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
          prop="smoid"
          label="序号"
          width="80">
        </el-table-column>
        <el-table-column
          prop="smotitle"
          label="标题"
          width="490">
          <template slot-scope="scope">
            <a  @click="handleEdit(scope.$index, scope.row)"
                target="_blank"
                class="buttonText" style="color: #1890ff">{{scope.row.smotitle}}</a>
          </template>
        </el-table-column>

        <el-table-column
          prop="smocreatename"
          label="创建者"
          width="180" >
        </el-table-column>
        <el-table-column
          prop="smocreatetime"
          label="发布时间">
        </el-table-column>
        <el-table-column
          prop="surl"
          label="路径" v-if="false"></el-table-column>
          <el-table-column
            prop="smoimage"
            label="图片" v-if="false">
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
          <el-input v-model="userInfo.smoid" placeholder="编号" type="hidden"></el-input>
          <el-input v-model="userInfo.surl" placeholder="地址" type="hidden"></el-input>
          <el-form-item label="文章标题" prop="smotitle">
            <el-input v-model="userInfo.smotitle" placeholder="请输入文章标题" style="width: 500px"></el-input>
          </el-form-item><br/>
          <el-form-item label="文章属性" prop="smoproperty">
            <el-checkbox-group v-model="userInfo.smoproperty" >
              <el-checkbox label="头条" name="smoproperty"></el-checkbox>
              <el-checkbox label="推荐" name="smoproperty"></el-checkbox>
              <el-checkbox label="图片" name="smoproperty"></el-checkbox>
              <el-checkbox label="幻灯" name="smoproperty"></el-checkbox>
            </el-checkbox-group>
          </el-form-item>
          <el-form-item label="文章缩略图">
            <el-upload
              class="upload-demo"
              action="https://jsonplaceholder.typicode.com/posts/"
              :on-change="handleChange"
              :on-preview="handlePreview"
              accept=".jpg,.gif,.png"
              :http-request="modeUpload"
              :limit="1"
              :file-list="fileList">
              <el-button size="small" type="primary">上传图片</el-button>
              <div slot="tip" class="el-upload__tip">只能上传单个图片，且不超过50M</div>
            </el-upload>
          </el-form-item><br/>
          <el-form-item label="文章来源" prop="smosource">
            <el-input v-model="userInfo.smosource" placeholder="请输入文章来源" style="width: 300px"></el-input>
          </el-form-item>
          <el-form-item label="文章作者" prop="smoauthor">
            <el-input v-model="userInfo.smoauthor" placeholder="请输入文章作者" style="width: 300px"></el-input>
          </el-form-item>
          <el-form-item label="创建时间">
            <el-date-picker v-model="userInfo.smocreatetime" type="datetime" placeholder="创建时间" style="width: 300px" disabled="disabled"></el-date-picker>
          </el-form-item>
          <el-form-item label="描述" prop="smodescrion">
            <el-input v-model="userInfo.smodescrion" placeholder="请输入对该文章的简短描述，以便用户查看文章简略" style="width: 300px"></el-input>
          </el-form-item>
          <el-form-item label="文章内容" prop="smocontent">
            <quill-editor v-model="userInfo.smocontent" placeholder="请输入文章内容" class="myQuillEditor" :options="editorOption" style="width: 830px; height: 400px; margin-bottom: 80px" ></quill-editor>
          </el-form-item>


        </div>
      </el-form></el-main>
    </div>
  </div>
</template>

<script>
  import qs from 'qs'
  import { mapGetters } from 'vuex'
  import { findAllSmokestyle,addSmokestyle,delSmokestyle,findSmokestyle,gxSmokestyle } from '@/api/smokestyle/smokestyle'
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
        window.open(row.surl)
      },
      //设置表格内容居中
      cellStyle({row, column, rowIndex, columnIndex}){
        return 'text-align:center';
      },
      rowClass({row, rowIndex}){//设置表头居中
        return 'text-align:center';
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
        this.userInfo.smoimage=file.name
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
          this.fileList=[{name:this.file.name,url:response.url}]
          this.userInfo.surl=response.url
        })
        console.log('ce是'+JSON.stringify(this.file))
      },
      //重置
      onrest(){
        this.title=''
        this.starttime=''
        this.endtime=''
      },
      //多条件查询
      onSearch() {
        let postData = qs.stringify({
          smotitle:this.title,
          starttime:this.starttime,
          endtime:this.endtime
        });
        this.listLoading = true
        findSmokestyle(postData).then((response) =>{
          this.currentPage = 1
          this.tableData = response.list
          console.debug(this.tableData)
          this.total=response.list.length
          this.listLoading=false
        })
      },
      //初始化页面
      initList() {
        this.listLoading = true
        findAllSmokestyle(this.listQuery).then(response =>{
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
          this.$set(this.userInfo,'smocreatetime',new Date())

      },
      //返回
      back(formName){this.ad='none',this.tf='',   this.$refs[formName].resetFields(), this.initList();
      },//新增提交
      submitUser(formName){
          this.$refs[formName].validate((valid) => {
            if (valid) {
              let endtime = new Date(this.userInfo.smocreatetime).toJSON();
              this.userInfo.smocreatetime = new Date(+new Date(endtime) + 8 * 3600 * 1000)
                .toISOString()
                .replace(/T/g, " ")
                .replace(/\.[\d]{3}Z/, "")
              let posdata=qs.stringify({
                smotitle:this.userInfo.smotitle,
                smoproperty:this.userInfo.smoproperty.toString(),
                smoimage:this.userInfo.smoimage,
                surl:this.userInfo.surl,
                smosource:this.userInfo.smosource,
                smoauthor:this.userInfo.smoauthor,
                smocreatetime:this.userInfo.smocreatetime,
                smodescrion:this.userInfo.smodescrion,
                smocontent:this.userInfo.smocontent,
                smocreateids:this.userId,
                smocreatename:this.nickname,
                sstatus:0
              })
              addSmokestyle(posdata).then((response)=>{
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
        this.userInfo=row
        console.log('this.ursInfo.surl'+this.userInfo.surl)
        this.fileList=[{name:row.smoimage,url:row.surl}]
        console.log('属性',row.smoproperty.split(','));
        this.userInfo.smoproperty=row.smoproperty.split(',')
        /* 赋值实时设置当前时间*/
        this.$set(this.userInfo,'smocreatetime',row.smocreatetime)
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
          delSmokestyle(postData).then((response) =>{
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
          smoid:this.userInfo.smoid,
          smotitle:this.userInfo.smotitle,
          smoproperty:this.userInfo.smoproperty.toString(),
          smoimage:this.userInfo.smoimage,
          surl:this.userInfo.surl,
          smosource:this.userInfo.smosource,
          smoauthor:this.userInfo.smoauthor,
          smocreatetime:this.userInfo.smocreatetime,
          smodescrion:this.userInfo.smodescrion,
          smocontent:this.userInfo.smocontent,
          smocreatetime:this.userInfo.smocreatetime,
        })
        gxSmokestyle(posdata).then((response)=>{
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
      return {
        rules: {
          smotitle: [
            { required: true, message: '请输入文章标题', trigger: 'blur' },
            { min: 3, message: '长度得大于3 字符', trigger: 'blur' }
          ],
          smosource: [
            { required: true, message: '请输入文章来源', trigger: 'blur' },
          ],
          smoproperty: [
            { type: 'array', required: true, message: '请至少选择一个文章属性', trigger: 'change' }
          ],
          smoauthor: [
            { required: true, message: '请输入文章作者', trigger: 'change' }

          ],
          smocontent: [
            { required: true, message: '请输入文章内容', trigger: 'change' }
          ],
          smodescrion: [
            { required: true, message: '请输入描述', trigger: 'change' }
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
        currentPage4: 1,
        pageSize:10,
        total:0,
        currentPage:1,
        times:'',
        tableData: [],
        options:[],
        listLoading:false,
        starttime:'',
        endtime:''
      }
    }
  }
</script>


