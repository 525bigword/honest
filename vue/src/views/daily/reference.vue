<template>
  <div>
    <el-form :inline="true" class="demo-form-inline" v-bind:style="{display:tf}">
      <div align="center" style="margin-top: 30px">
        <el-form-item>
          <el-input
            v-model="search"
            class="search_name"
            placeholder="请输入备案编号">
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
      <div></div>
      <el-table
        :data="tableData.slice((currentPage-1)*pageSize,currentPage*pageSize)"
        border
        style="width: 100%" ref="multipleTable" >
        <el-table-column type="selection" width="55px"></el-table-column>
        <el-table-column
          prop="sid"
          label="编号"
          width="80">
        </el-table-column>
        <el-table-column
          prop="sfilingId"
          label="备案编号"
          width="180">
        </el-table-column>
        <el-table-column
          prop="spaperItems"
          label="事项摘要"
          width="180"  v-if="false"></el-table-column>
        <el-table-column
          prop="url"
          label="路径"
          width="180"  v-if="false"></el-table-column>
        <el-table-column
          prop="screateName"
          label="员工姓名"
          width="180">
        </el-table-column>
        <el-table-column
          prop="sstatus"
          label="审核状态"
          width="100"   :formatter="cstatus"></el-table-column><el-table-column
        prop="auditorrole"
        label="审核人角色"
        width="130">
      </el-table-column>
        <el-table-column
          prop="sauditOpinion"
          label="审核意见"
          width="180">
        </el-table-column>
        <el-table-column
          prop="screateTime"
          label="创建时间">
        </el-table-column>
        <el-table-column label="操作" fixed="right" align="center"  prop="lstatus" >
          <template slot-scope="scope">
            <el-button v-if="scope.row.sstatus==1" v-bind:style="{display:(role.includes('纪检监察科科长')||role.includes('纪检组长')||role.includes('局领导')?'':'none')}" type="primary" size="small" @click="handleEdit(scope.$index, scope.row)">审核</el-button>
                </template>
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
    <div v-bind:style="{display:ad}" style="background-color: lightgray;width: 100%;height: 1000px" >
      <el-main>      <el-form :inline="true" :model="userInfo" class="demo-form-inline" label-width="220px">
        <div style="background-color: white;width: 100%;height: 65px" >
          <br/>
          <div align="right" ><el-form-item >
            <el-button type="primary" class="el-icon-edit" align="right" @click="tgsh(2)">通过</el-button>
            <el-button type="primary" class="el-icon-edit" align="right" @click="tgsh(-2)" >不通过</el-button>
            <el-button type="primary" class="el-icon-back" @click="back">返回</el-button></el-form-item></div></div>
        <br/>
        <div style="height:900px;background-color: white">
          <el-input v-model="userInfo.url" placeholder="地址" type="hidden"></el-input>
          <el-input v-model="userInfo.sid" placeholder="编号" type="hidden" ></el-input>
          <el-form-item label="备案编号">

            <el-input v-model="userInfo.sfilingId" placeholder="备案编号" style="width: 300px" disabled="disabled"></el-input>
          </el-form-item>
          <el-form-item label="附件">
            <el-upload
              :action="doUpload"
              :file-list="fileList"
              :on-preview="handlePreview"
              :on-change="handleChange"
              :http-request="modeUpload"
              v-model="userInfo.saccessory"
            ><el-button size="small" type="primary">上传</el-button></el-upload>
          </el-form-item><br/>
          <el-form-item label="承办部门">
            <el-input v-model="userInfo.sundertakerDeptId" placeholder="承办部门" style="width: 300px" disabled="disabled"></el-input>
          </el-form-item>
          <el-form-item label="承办人">
            <el-input v-model="userInfo.sundertaker" placeholder="承办人" style="width: 300px" disabled="disabled"> </el-input>
          </el-form-item><br/>
          <el-form-item label="事项摘要">
            <el-input v-model="userInfo.spaperItems" placeholder="事项摘要" style="width: 300px" disabled="disabled"></el-input>
          </el-form-item>
          <el-form-item label="耗资">
            <el-input v-model="userInfo.scost" placeholder="耗资" style="width: 300px" type="Number" disabled="disabled"></el-input>
          </el-form-item><br/><div>
          <el-form-item label="实施方式">
            <quill-editor id="editer" ref="text" v-model="userInfo.senforcementMode" class="myQuillEditor" :options="editorOption" style="width: 830px; height: 200px; margin-bottom: 80px" />
          </el-form-item></div><br/><div><el-form-item label="审核人角色" v-if="false">
          <el-input v-model="userInfo.auditorrole" placeholder="审核人角色"  style="width: 300px" ></el-input>
        </el-form-item>
          <el-form-item label="审核意见">
            <el-input v-model="userInfo.sauditOpinion" placeholder="审核意见"  style="width: 300px" ></el-input>
          </el-form-item>
          <el-form-item label="状态" v-if="false">
            <el-input v-model="userInfo.sstatus" placeholder="状态" style="width: 300px" disabled="disabled" ></el-input>
          </el-form-item>
          <el-form-item label="创建者姓名" v-if="false">
            <el-input v-model="userInfo.screateName" placeholder="创建者姓名" style="width: 300px" disabled="disabled" ></el-input>
          </el-form-item><br/>
          <el-form-item label="创建时间" v-if="false">
            <el-date-picker v-model="userInfo.screateTime" placeholder="创建时间" style="width: 300px" type="datetime"  disabled="disabled"></el-date-picker>
          </el-form-item></div>
        </div>
      </el-form></el-main>
    </div>
  </div>
</template>

<script>
  import { fileUpload,list,findbytitle,addsuper,dele,tgshme,gxme,findbysFilingId } from '@/api/daily/supervise'
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
    methods:{//判断状态给提示
      cstatus: function (row, column, cellValue) {
        if (cellValue == 0){
          return '创建';
        }else if (cellValue == 1){
          return '待审';
        }
        else{
          return '已审核'
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
      },
      handleChange(file, fileList) {
        this.userInfo.saccessory=file.name
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

      //按标题查询
      onSearch() {
        let postData = qs.stringify({
          sFilingId:this.search
        });
        this.listLoading = true
        findbysFilingId(postData).then((response) =>{
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

      },//点击列编辑
      handleEdit(index, row){

        this.tf='none';
        this.ad=''//编辑/审核页面出来,

        this.userInfo=row
        this.fileList=[{name:row.saccessory,url:row.url}]
        if(row.sstatus==0){

        }
        else if(row.sstatus==1){

        }
        else {

        }
      },
      tgsh(val){
        let postData = qs.stringify({
          sid:this.userInfo.sid,
          sAuditOpinion:this.userInfo.sauditOpinion,
        auditorrole:this.role
        });
        var resu;
        if(val==2){
          resu='审核通过'
        }else{
          resu='审核不通过'
        }
        gxme(postData).then((response)=>{
          this.initList();
          this.tf=''//父页面隐藏
          this.ad='none'
          this.$notify({
            title: '审核结果',
            message: resu,
            type: 'success',
            duration: 2000
          })
        })

      },
      back(){
        console.log(this.userInfo.sEnforcementMode)
        console.log(this.userInfo.cfil)
        this.tf=''//父页面隐藏
        this.ad='none'},
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

