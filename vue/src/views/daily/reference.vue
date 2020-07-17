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
        style="width: 100%" ref="multipleTable" :cell-style='cellStyle':header-cell-style='rowClass' v-loading="listLoading">
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
            <el-button v-if="scope.row.sstatus==1" v-bind:style="{display:( hasPerm('reference:audit')?'':'none')}" type="primary" size="small" @click="handleEdit(scope.$index, scope.row)">审核</el-button>
            <el-button v-if="scope.row.sstatus==3" v-bind:style="{display:( hasPerm('reference:leadersign')?'':'none')}" type="primary" size="small" @click="handleEdit(scope.$index, scope.row)">验收</el-button>
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
    <div v-bind:style="{display:ad}" style="background-color: lightgray;width: 100%;" >
      <el-main>      <el-form :inline="true" :model="userInfo" class="demo-form-inline" label-width="220px"  :rules="rules" ref="ruleForm">
        <div style="background-color: white;width: 100%;height: 65px;position:fixed; top:50px; left:-1px;z-index:2;" >
          <br/>
          <div align="right" ><el-form-item >
            <el-button type="primary" class="el-icon-edit" align="right" @click="signleader()" v-if=" hasPerm('reference:leadersign')&& userInfo.sstatus==3">验收</el-button>
            <el-button type="primary" class="el-icon-edit" align="right" @click="tgsh('ruleForm')" v-if=" hasPerm('reference:audit')&& userInfo.sstatus==1">保存</el-button>
            <el-button type="primary" class="el-icon-back" @click="back">返回</el-button></el-form-item></div></div>
        <br/>
        <div style="background-color: white;margin-top: 7px;z-index:3;">
          <el-input v-model="userInfo.url" placeholder="地址" type="hidden"></el-input>
          <el-input v-model="userInfo.sid" placeholder="编号" type="hidden" ></el-input>
          <el-input v-model="userInfo.sstatus" type="hidden"></el-input>
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
            <el-cascader ref='cascaderUnit' :show-all-levels="false" v-if="isShowAddressInfo"
                         :placeholder="defaUnit"
                         :props="props"
                         :options="options_cascader"
                         :expandTrigger="'hover'"
                         clearable v-model="userInfo.sundertakerDeptId" @change="handleItemChange" disabled="disabled"  style="width: 300px"></el-cascader>
          </el-form-item>
          <el-form-item label="承办人">
            <el-select v-model="userInfo.sundertaker" placeholder="请选择承办人" style="width: 300px" disabled="disabled">
              <el-option
                v-for="item in options"
                :key="item.sid"
                :label="item.name"
                :value="item.sid">
              </el-option>
            </el-select>
          </el-form-item><br/>
          <el-form-item label="事项摘要" disabled="disabled">
            <el-input v-model="userInfo.spaperItems" placeholder="事项摘要" style="width: 300px" disabled="disabled"></el-input>
          </el-form-item>
          <el-form-item label="耗资" disabled="disabled">
            <el-input v-model="userInfo.scost" placeholder="耗资" style="width: 300px" type="Number" disabled="disabled"></el-input>
          </el-form-item><br/><div>
          <el-form-item label="实施方式">
            <el-card class="box-card"  style="margin-bottom:30px;width: 830px;text-align: left">
              <div v-html="userInfo.senforcementMode"></div>
            </el-card>
            <!--   <quill-editor id="editer" ref="text" v-model="userInfo.senforcementMode" class="myQuillEditor" :options="editorOption" style="width: 830px; height: 300px; margin-bottom: 80px" />
           -->  </el-form-item></div><br/><div><el-form-item label="审核人角色" v-if="false">
          <el-input v-model="userInfo.auditorrole" placeholder="审核人角色"  style="width: 300px" ></el-input>
        </el-form-item>
          <el-form-item label="审核意见" prop="sauditOpinion">
            <el-input v-model="userInfo.sauditOpinion" placeholder="请填写审核意见"  style="width: 300px" :disabled="userInfo.sstatus==1?false:'disabled'" ></el-input>
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
  import { fileUpload,list,findbytitle,addsuper,dele,tgshme,gxme,findbysFilingId,signleader } from '@/api/daily/supervise'
  import {getFileGroup,initpersons} from '@/api/duty/talk'
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
    methods:{ handleItemChange(value){

        //点击选择时初始化谈话对象和记录人
        let checkedNodes = this.$refs['cascaderUnit'].getCheckedNodes()//选择的值
        if(checkedNodes[0]!=undefined){
          this.userInfo.sundertakerDeptId=value
          let postdata=qs.stringify({
            id:checkedNodes[0].value
          })
          initpersons(postdata).then((response)=>{

            this.options = response.list
          })
        }
        else{
          this.options=[]
          //this.userInfo.sundertakerDeptId=[]

        }

      },//设置表格内容居中
      cellStyle({row, column, rowIndex, columnIndex}){
        return 'text-align:center';
      },
      rowClass({row, rowIndex}){//设置表头居中
        return 'text-align:center';
      },//判断状态给提示
      cstatus: function (row, column, cellValue) {
        if (cellValue == 0){
          return '创建';
        }else if (cellValue == 1){
          return '待审';
        }
        else if (cellValue == 3){
          return '待验收'
        }
        else{
          return '验收完毕'
        }
      },
      //文件预览
      handlePreview(file) {
        const userAgent = navigator.userAgent;
        if (!!window.ActiveXObject || "ActiveXObject" in window) {
          alert('推荐谷歌进行文件预览')
        }else{
          window.open(file.url) //blob格式地址
        }
      },
      handleChange(file, fileList) {
        this.userInfo.saccessory=file.name

      },
      modeUpload: function(item) {

        this.file = item.file

        const fd = new FormData()
        fd.append('filename', this.file)
        fileUpload(fd
        ).then(response => {
          this.$message.success(response.message)
          this.fileList=[{name:this.file.name,url:this.uploadimage+response.url}]
          this.userInfo.url=response.url
        })
      },

      //按标题查询
      onSearch() {
      let sta=1;
      if(this.hasPerm('reference:leadersign'&&this.userInfo.sStatus==3)){
        sta=3
      }
      else if(this.hasPerm('reference:audit'&&this.userInfo.sStatus==2)){
        sta=1;
      }
        let postData = qs.stringify({
          sFilingId:this.search,
          sStatus: sta

        });
        this.listLoading = true
        findbysFilingId(postData).then((response) =>{
          this.currentPage = 1
          this.tableData = response.list
          this.total=response.list.length
          this.listLoading=false
        })
      },
      //初始化页面
      initList() {
        this.listLoading=true
        let sta=1;
        if(this.hasPerm('reference:leadersign')){
          sta=3
        }
        let posdata=qs.stringify({
          sStatus:sta
        })
        list(posdata).then(response =>{

          this.tableData = response.list
          this.total = response.list.length
          this.listLoading=false
        })
      },//重置
      onrest(){
        this.search=''

      },//点击列编辑
      handleEdit(index, row){

        this.userInfo={
          sundertakerDeptId:[]
        }

        this.userInfo=row
        this.userInfo.sundertakerDeptId = row.punit.split(',').map(Number)

        this.isShowAddressInfo = false;
        // 这里搞个定时器重新载入一下组件就可以触发组件拉取数据
        setTimeout(() => {
          this.isShowAddressInfo = true;
        }, 10);
        let postdata=qs.stringify({
          id:row.punit.split(',').map(Number)[row.punit.split(',').map(Number).length-1]
        })
        initpersons(postdata).then((response)=>{
          this.options = response.list
        })
        this.userInfo.sundertaker=Number(row.sundertaker)
        this.fileList=[{name:row.saccessory,url:this.uploadimage+row.url}]
        this.tf='none';
        this.ad=''//编辑/审核页面出来,
        if(row.sstatus==0){

        }
        else if(row.sstatus==1){

        }
        else {

        }
      },
      tgsh(formName){
        this.$refs[formName].validate((valid) => {
          if (valid) {
            let postData = qs.stringify({
              sid:this.userInfo.sid,
              sAuditOpinion:this.userInfo.sauditOpinion,
              auditorrole:this.role
            });

            tgshme(postData).then((response)=>{
              this.initList();
              this.tf=''//父页面隐藏
              this.ad='none'
              this.$notify({
                title: '审核结果',
                message: '审核完成',
                type: 'success',
                duration: 2000
              })
            })

          } else {
            console.log('error submit!!');
            return false;
          }
        });

      },signleader(){
        let postData = qs.stringify({
          sid:this.userInfo.sid,
        });

        signleader(postData).then((response)=>{
          this.initList();
          this.tf=''//父页面隐藏
          this.ad='none'
          this.$notify({
            title: '验收结果',
            message: '验收完毕',
            type: 'success',
            duration: 2000
          })
        })
      },
      back(){
        this.tf=''//父页面隐藏
        this.ad='none'},
      handleSizeChange(size) {
        this.pageSize = size;
      },
      handleCurrentChange(currentPage) {
        this.currentPage = currentPage;
      }
    },
    data() {

      return { rules: {
          sauditOpinion: [
            {  required: true, message: '请填写审核意见', trigger: 'blur' }
          ]

        },   defaUnit:'请选择谈话对象单位',
        options_cascader:[],//级联选择器的options属性
        options:[],
        isShowAddressInfo:true,
        props: {
          checkStrictly:true,
          lazy: true,
          lazyLoad (node, resolve) {
            const { level } = node;
            const {data}=node;
            let parentId=data?data.value:'0'
            let postData=qs.stringify({
              parent:parentId
            });
            getFileGroup(postData).then((response)=>{
              let nodes = level ===0?[]:[]

              if(response.list&&Array.isArray(response.list)){

                for(let item of response.list){
                  nodes.push({
                    value:item.mid,
                    label: item.mechanismName
                  });
                }
              }
              // 通过调用resolve将子节点数据返回，通知组件数据加载完成
              resolve(nodes);
            })


          }
        },
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
        userInfo: {
          senforcementMode:''
        },
        dialogTitle: '增加',
        rowIndex: null,
        search:'',
        currentPage4: 1,
        pageSize:10,
        total:0,
        currentPage:1,
        listLoading:true,
        tableData: []
      }
    }
  }
</script>

