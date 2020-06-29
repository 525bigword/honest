<template>
  <div>
    <el-form :inline="true" class="demo-form-inline" v-bind:style="{display:tf}">
      <div align="center" style="margin-top: 30px">
        <el-form-item>
          <el-input
            v-model="search"
            class="search_name"
            placeholder="请输入事项摘要">
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
        <el-button type="primary" class="el-icon-delete" @click="dele">删除</el-button></el-form-item></div>
      <el-table
        :data="tableData.slice((currentPage-1)*pageSize,currentPage*pageSize)"
        border
        style="width: 100%" ref="multipleTable" >
        <el-table-column type="selection" width="55px"></el-table-column>
        <el-table-column
          prop="sid"
          label="序号"
          width="180">
        </el-table-column>
        <el-table-column
          prop="spaperItems"
          label="事项摘要"
          width="180">
          <template slot-scope="scope">
            <a  @click="handleEdit(scope.$index, scope.row)"
                target="_blank"
                class="buttonText" style="color: #1890ff">{{scope.row.spaperItems}}</a>
          </template>
        </el-table-column>
        <el-table-column
          prop="url"
          label="路径"
          width="180"  v-if="false"></el-table-column>
        <el-table-column
          prop="screateName"
          label="创建者"
          width="180">
        </el-table-column>
        <el-table-column
          prop="screateTime"
          label="发布时间">
        </el-table-column>
        <el-table-column
          prop="sstatus"
          label="路径"
          width="180"  v-if="false"></el-table-column>
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
    <div v-bind:style="{display:ad}" style="background-color: lightgray;width: 100%;height: 1000px" >
      <el-main>      <el-form :inline="true" :model="userInfo" class="demo-form-inline" label-width="220px">
        <div style="background-color: white;width: 100%;height: 65px" >
          <br/>
          <div align="right" ><el-form-item >
            <el-button type="primary" class="el-icon-edit" align="right" v-bind:style="{display:tj}" @click="tjshme">提交审核</el-button>
            <el-button type="primary" class="el-icon-edit" align="right" @click="submitUser" v-bind:style="{display:bc}">保存</el-button>
            <el-button type="primary" class="el-icon-edit" align="right" @click="gxmethod" v-bind:style="{display:gx}">更新</el-button>
            <el-button type="primary" class="el-icon-back" @click="back">返回</el-button></el-form-item></div></div>
        <br/>
        <div style="height:900px;background-color: white">
          <el-input v-model="userInfo.url" placeholder="地址" type="hidden"></el-input>
          <el-input v-model="userInfo.sid" placeholder="编号" type="hidden" ></el-input>
          <el-form-item label="备案编号">

            <el-input v-model="userInfo.sFilingId" placeholder="备案编号" style="width: 300px" disabled="disabled"></el-input>
          </el-form-item><br/>
          <el-form-item label="附件">
            <el-upload
              :action="doUpload"
              :file-list="fileList"
              :on-preview="handlePreview"
              :on-change="handleChange"
              :http-request="modeUpload"
              v-model="userInfo.sAccessory"
            ><el-button size="small" type="primary">上传</el-button></el-upload>
          </el-form-item><br/>
          <el-form-item label="承办部门">
            <el-input v-model="userInfo.sUndertakerDeptId" placeholder="承办部门" style="width: 300px"></el-input>
          </el-form-item><br/>
          <el-form-item label="承办人">
            <el-input v-model="userInfo.sUndertaker" placeholder="承办人" style="width: 300px"></el-input>
          </el-form-item><br/>
          <el-form-item label="事项摘要">
            <el-input v-model="userInfo.sPaperItems" placeholder="事项摘要" style="width: 300px"></el-input>
          </el-form-item><br/>
          <el-form-item label="耗资">
            <el-input v-model="userInfo.sCost" placeholder="耗资" style="width: 300px" type="Number"></el-input>
          </el-form-item><br/><div>
          <el-form-item label="实施方式">
            <quill-editor id="editer" ref="text" v-model="userInfo.sEnforcementMode" class="myQuillEditor" :options="editorOption" style="width: 600px;" />
          </el-form-item></div><br/><div>
          <el-form-item label="状态">
            <el-input v-model="userInfo.sStatus" placeholder="状态" style="width: 300px" disabled="disabled"></el-input>
          </el-form-item><br/>
          <el-form-item label="创建者姓名">
            <el-input v-model="userInfo.sCreateName" placeholder="创建者姓名" style="width: 300px" disabled="disabled"></el-input>
          </el-form-item><br/>
          <el-form-item label="创建时间">
            <el-date-picker v-model="userInfo.sCreateTime" placeholder="创建时间" style="width: 300px" type="datetime" disabled="disabled"></el-date-picker>
          </el-form-item></div>
        </div>
      </el-form></el-main>
    </div>
  </div>
</template>

<script>
  import { fileUpload,list,findbytitle,addsuper,dele,tjshme,gxme } from '@/api/daily/supervise'
  import qs from 'qs'
  import { quillEditor } from 'vue-quill-editor'
  import 'quill/dist/quill.core.css'
  import 'quill/dist/quill.snow.css'
  import 'quill/dist/quill.bubble.css'
  import { mapGetters } from 'vuex'
  export default {
    components:{quillEditor},
    computed: {
      ...mapGetters([
        'nickname',
        'userId', 'role'
      ])
    },
    created() {
      this.initList()
    },
    methods:{
      //文件预览
      handlePreview(file) {
        console.log('file'+JSON.stringify(file))
        const userAgent = navigator.userAgent;
        if (!!window.ActiveXObject || "ActiveXObject" in window) {
          alert('推荐谷歌进行文件预览')
        }else{
          window.open(file.url) //blob格式地址
        }
      },
      handleChange(file, fileList) {
        this.userInfo.sAccessory=file.name
        console.log('localFile'+JSON.stringify(file.name))

      },
      modeUpload: function(item) {
        // console.log(item.file);
        this.file = item.file
        console.log('ce是'+this.file)
        console.log('this.file'+JSON.stringify(item))
        const fd = new FormData()
        fd.append('filename', this.file)
        fileUpload(fd
        ).then(response => {
          this.$message.success(response.message)
          this.fileList=[{name:this.file.name,url:response.url}]
          this.userInfo.url=response.url
        })
      },
      //随机生成备案编号
      genID(length){
        return Number(Math.random().toString().substr(3,length) + Date.now()).toString(36);
      },
      //按标题查询
      onSearch() {
        let postData = qs.stringify({
          sPaperItems:this.search
        });
        this.listLoading = true
        findbytitle(postData).then((response) =>{
          this.currentPage = 1
          this.tableData = response.list
          console.debug(this.tableData)
          this.total=response.list.length
          this.listLoading=false
        })
      },
      //初始化页面
      initList() {
        list(this.listQuery).then(response =>{
          console.debug(response)
          this.tableData = response.list
          this.total = response.list.length
        })
      },//重置
      onrest(){
        this.search=''

      },//新增提交
      submitUser(){
        let endtime = new Date(this.userInfo.sCreateTime).toJSON();
        this.userInfo.sCreateTime = new Date(+new Date(endtime) + 8 * 3600 * 1000)
          .toISOString()
          .replace(/T/g, " ")
          .replace(/\.[\d]{3}Z/, "")
        let posdata=qs.stringify({
          url:this.userInfo.url,
          sFilingId:this.userInfo.sFilingId,
          sPaperItems:this.userInfo.sPaperItems,
          sEnforcementMode:this.userInfo.sEnforcementMode,//this.$refs.text.value,
          sAccessory:this.userInfo.sAccessory,
          sCost:this.userInfo.sCost,
          sUndertaker:this.userInfo.sUndertaker,
          sUndertakerDeptId:this.userInfo.sUndertakerDeptId,
          sCreateTime:this.userInfo.sCreateTime,
          sCreateName:this.nickname,
          sStatus:0,
          sCreateId:this.userId
        })
        addsuper(posdata).then((response)=>{
          this.tf='';
          this.ad='none'
          this.initList();
          this.$notify({
            title: '成功',
            message: response.message,
            type: 'success',
            duration: 2000
          })
        })
      },//点击列编辑
      handleEdit(index, row){

        this.tf='none';
        this.ad=''//编辑/审核页面出来,

        this.userInfo=row
        this.fileList=[{name:row.sAccessory,url:row.url}]
        if(row.sStatus==0){
          this.userInfo.sStatus='创建'
          this.tj=''//提交审核按钮
          this.bc='none'//保存按钮隐藏
          this.gx=''//更新按钮显示
        }
        else if(row.sStatus==1){
          this.userInfo.sStatus='待审'
          this.tj='none'//提交审核按钮
          this.bc='none'//保存按钮隐藏
          this.gx='none'//更新按钮显示
        }
        else {
          this.userInfo.sStatus='已审核'
          this.tj='none'//提交审核按钮
          this.bc='none'//保存按钮隐藏
          this.gx='none'//更新按钮显示
        }
        /* 赋值实时设置当前时间*/
        //   this.$set(this.userInfo,'sCreateTime',row.sCreateTime)
        //  this.userInfo.lCreateName=row.eid[0].name
      },
      tjshme(){
        let postData = qs.stringify({
          sid:this.userInfo.sid,
        });
        tjshme(postData).then((response)=>{
          this.initList();
          this.tf=''//父页面隐藏
          this.ad='none'
          this.$notify({
            title: '成功',
            message: response.message,
            type: 'success',
            duration: 2000
          })
        })

      },//更新数据
      gxmethod(){
        let posdata=qs.stringify({
          url:this.userInfo.url,
          sid:this.userInfo.sid,
          sPaperItems:this.userInfo.sPaperItems,
          sEnforcementMode:this.userInfo.sEnforcementMode,//this.$refs.text.value,
          sAccessory:this.userInfo.sAccessory,
          sCost:this.userInfo.sCost,
          sUndertaker:this.userInfo.sUndertaker,
          sUndertakerDeptId:this.userInfo.sUndertakerDeptId,
        })
        gxme(posdata).then((response)=>{
          this.initList();
          this.tf=''//父页面隐藏
          this.ad='none'
          this.$notify({
            title: '成功',
            message: response.message,
            type: 'success',
            duration: 2000
          })
        })
      },
      back(){
        console.log(this.userInfo.sEnforcementMode)
        console.log(this.userInfo.cfil)
        this.tf=''//父页面隐藏
        this.ad='none'},//删除
      dele(){
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
          dele(postData).then((response) =>{
            this.initList();
            this.$notify({
              title: '成功',
              message: response.message,
              type: 'success',
              duration: 2000
            })
          })
        }
      },
      add(){
        this.fileList=[]//清空upload
        this.userInfo={}
        this.tf='none'//父页面隐藏
        this.ad=''//新增页面出现
        this.tj='none'
        this.gx='none'
        this.bc=''
        this.userInfo.sEnforcementMode=''
        this.userInfo.sFilingId='BA'+this.genID(10);
        this.userInfo.sStatus='创建'
        this.userInfo.sCreateName=this.nickname
        /* 动态赋值实时设置当前时间*/
        this.$set(this.userInfo,'sCreateTime',new Date())
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
        tj:'',//提交审核按钮
        bc:'',
        gx:'none',
        editorOption: {},
        file:{},
        fileList: [],
        dialogImageUrl: "",
        dialogVisible: false,
        disabled: false
        ,doUpload:'https://jsonplaceholder.typicode.com/posts/',
        //---------------------------------------------
        tf:'',//
        ad:'none',//新增、编辑页面
        iconFormVisible: false,
        userInfo: {},
        dialogTitle: '增加',
        rowIndex: null,
        search:'',
        currentPage4: 1,
        pageSize:10,
        total:0,
        currentPage:1,

        tableData: []
      }
    }
  }
</script>
