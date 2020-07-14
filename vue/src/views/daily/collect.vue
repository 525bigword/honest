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
      <div></div>
      <el-table
        :data="tableData.slice((currentPage-1)*pageSize,currentPage*pageSize)"
        border
        style="width: 100%" ref="multipleTable" :cell-style='cellStyle':header-cell-style='rowClass'  v-loading="listLoading">
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
        <el-table-column v-if="false" prop="punit" label="部门路径">

        </el-table-column>
        <el-table-column
          prop="auditorrole"
          label="审核人角色"
          width="130" v-if="false">
        </el-table-column>
        <el-table-column
          prop="sstatus"
          label="状态"
          width="180"  :formatter="cstatus"></el-table-column>    <el-table-column
        prop="sauditOpinion"
        label="审核意见"
        width="180" v-if="false">
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
    <div v-bind:style="{display:ad}" style="background-color: lightgray;width: 100%" >
      <el-main>      <el-form :inline="true" :model="userInfo" class="demo-form-inline" label-width="220px">
        <div  style="z-index:2;background-color: white;width: 100%;height: 65px;background-color: white;width: 100%;height: 65px;position:fixed; top:50px; left:-1px;" >
          <br/>
          <div align="right" ><el-form-item >
            <el-button type="primary" class="el-icon-back" @click="back">返回</el-button></el-form-item></div></div>
        <br/>
        <div style="background-color: white;margin-top: 10px;z-index:3;">
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
              <el-cascader ref='cascaderUnit' :show-all-levels="false" v-if="isShowAddressInfo"
                           :placeholder="defaUnit"
                           :props="props"
                           :options="options_cascader"
                           :expandTrigger="'hover'"
                           clearable v-model="userInfo.sundertakerDeptId" @change="handleItemChange"  style="width: 300px" disabled="disabled"></el-cascader>
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
          <el-form-item label="事项摘要">
            <el-input v-model="userInfo.spaperItems" placeholder="事项摘要" style="width: 300px"disabled="disabled"></el-input>
          </el-form-item>
          <el-form-item label="耗资">
            <el-input v-model="userInfo.scost" placeholder="耗资" style="width: 300px" type="Number" disabled="disabled"></el-input>
          </el-form-item><br/><div>
          <el-form-item label="实施方式">
            <el-card class="box-card" style="margin-bottom:30px;width: 830px;text-align: left">
<div  v-html="userInfo.senforcementMode"></div>
            </el-card>
           <!-- <div v-html="userInfo.senforcementMode" style="margin-bottom: 120px"></div>-->
          <!--  <quill-editor id="editer" ref="text" v-model="userInfo.senforcementMode" class="myQuillEditor" :options="editorOption" style="width: 830px;height: 350px;margin-bottom: 90px" />
         --> </el-form-item></div>

          <div>   <el-form-item label="审核意见">
            <el-input v-model="userInfo.sauditOpinion" placeholder="审核意见"  style="width: 300px" disabled="disabled"></el-input>
          </el-form-item>

          <el-form-item label="状态" v-if="false">
            <el-input v-model="userInfo.sstatus" placeholder="状态"  style="width: 300px" disabled="disabled"></el-input>
          </el-form-item>
            <el-form-item label="审核人角色" >
              <el-input v-model="userInfo.auditorrole" placeholder="审核人角色"  style="width: 300px" disabled="disabled"></el-input>
            </el-form-item>

          <el-form-item label="创建者姓名">
            <el-input v-model="userInfo.screateName" placeholder="创建者姓名" style="width: 300px" disabled="disabled"></el-input>
          </el-form-item>
          <el-form-item label="创建时间">
            <el-date-picker v-model="userInfo.screateTime" placeholder="创建时间" style="width: 300px" type="datetime" disabled="disabled"></el-date-picker>
          </el-form-item></div>
        </div>
      </el-form></el-main>
    </div>
  </div>
</template>

<script>
  import { fileUpload,list,findbytitle,addsuper,dele,tjshme,gxme } from '@/api/daily/supervise'
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
      this.userInfo.senforcementMode=''
      this.initList()
    },
    methods:{
      handleItemChange(value){

        //点击选择时初始化谈话对象和记录人
        let checkedNodes = this.$refs['cascaderUnit'].getCheckedNodes()//选择的值

        //  console.log('zz'+ value.join(',')); //全路径value值
        //   console.log('cc'+this.$refs.cascaderUnit.getCheckedNodes()[0].pathLabels); //全路径label值
        /*  console.log(checkedNodes) // 获取当前点击的节点
          console.log(checkedNodes[0].data.label) // 获取当前点击的节点的label
          console.log(checkedNodes[0].pathLabels) // 获取由 label 组成的数组*/

        if(checkedNodes[0]!=undefined){
          this.userInfo.sundertakerDeptId=value
          console.log('zz '+  this.userInfo.sundertakerDeptId); //全路径value值
          console.log(checkedNodes[0].data.label) // 获取当前点击的节点的label
          console.log(checkedNodes[0].pathLabels) // 获取由 label 组成的数组
          console.log('checkedNodes label'+checkedNodes[0].label)
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
          return '待验收';
        }
        else{
          return '验收完毕'
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
          this.fileList=[{name:this.file.name,url:"http://localhost:8080/upload/"+response.url}]
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
          sPaperItems:this.search,
          sStatus:2
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
        this.listLoading=true
        let posdata=qs.stringify({
          sStatus:2
        })
        list(posdata).then(response =>{
          console.debug(response)
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
        console.log('this.userInfo',this.userInfo)
        this.fileList=[{name:row.saccessory,url:"http://localhost:8080/upload/"+row.url}]
        this.tf='none';
        this.ad=''//编辑/审核页面出来,

      },
      back(){
        console.log(this.userInfo.sEnforcementMode)
        console.log(this.userInfo.cfil)
        this.tf=''//父页面隐藏
        this.ad='none'},//删除

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
        defaUnit:'请选择谈话对象单位',
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
              console.log('response.list'+JSON.stringify(response.list))
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
          senforcementMode:'<p></p>'
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
