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
        <el-button type="primary" class="el-icon-plus" @click="add" v-if="this.hasPerm('supervise:add')">新增</el-button>
        <el-button type="primary" class="el-icon-delete" @click="dele" v-if="this.hasPerm('supervise:delete')">删除</el-button></el-form-item></div>
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
          label="发布时间"   >
        </el-table-column>
        <el-table-column v-if="false" prop="punit" label="部门路径">

        </el-table-column>
        <el-table-column
          prop="sstatus"
          label="状态"
          width="180"   :formatter="cstatus"></el-table-column>

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
      <div style="background-color: white;width: 100%;height: 65px;position:fixed; top:50px; left:-1px;z-index:2 ;" >
        <br/>
        <div align="right" ><el-form-item >
          <el-button type="primary" class="el-icon-edit" v-if="this.hasPerm('supervise:add')||this.hasPerm('supervise:update')" align="right" v-bind:style="{display:tj}" @click="tjshme">提交审核</el-button>
          <el-button type="primary" class="el-icon-edit" v-if="this.hasPerm('supervise:add')" align="right" @click="submitUser" v-bind:style="{display:bc}">保存</el-button>
          <el-button type="primary" class="el-icon-edit" v-if="this.hasPerm('supervise:update')" align="right" @click="gxmethod" v-bind:style="{display:gx}">更新</el-button>
          <el-button type="primary" class="el-icon-back" @click="back">返回</el-button></el-form-item></div></div>
      <br/>
      <div style="height:900px;background-color: white;margin-top: 7px;z-index:3;">
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
                       clearable v-model="userInfo.sundertakerDeptId" @change="handleItemChange"  style="width: 300px"></el-cascader>
        </el-form-item>
        <el-form-item label="承办人">
          <el-select v-model="userInfo.sundertaker" placeholder="请选择承办人" style="width: 300px">
            <el-option
              v-for="item in options"
              :key="item.sid"
              :label="item.name"
              :value="item.sid">
            </el-option>
          </el-select>
        </el-form-item><br/>
        <el-form-item label="事项摘要">
          <el-input v-model="userInfo.spaperItems" placeholder="事项摘要" style="width: 300px"></el-input>
        </el-form-item>
        <el-form-item label="耗资">
          <el-input v-model="userInfo.scost" placeholder="耗资" style="width: 300px" type="Number"></el-input>
        </el-form-item><br/>

        <div>
        <el-form-item label="实施方式">
          <el-card class="box-card" style="margin-bottom:30px;width: 830px;height: 450px;text-align: left" v-if="userInfo.sstatus!=0&&dialogTitle!='增加'">
            <div  v-html="userInfo.senforcementMode"></div>
          </el-card>
          <quill-editor v-if="dialogTitle=='增加'||userInfo.sstatus==0" id="editer" ref="text" v-model="userInfo.senforcementMode" class="myQuillEditor" :options="editorOption" style="width: 830px; height: 400px; margin-bottom: 80px" />
        </el-form-item></div><br/><div>
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

      },
      //设置表格内容居中
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
          this.tableData = response.list
          this.total = response.list.length
          this.listLoading=false
        })
      },//重置
      onrest(){
        this.search=''

      },//新增提交
      submitUser(){
        let endtime = new Date(this.userInfo.screateTime).toJSON();
        this.userInfo.screateTime = new Date(+new Date(endtime) + 8 * 3600 * 1000)
          .toISOString()
          .replace(/T/g, " ")
          .replace(/\.[\d]{3}Z/, "")
     console.log('擦擦擦' ,this.userInfo);

        let posdata=qs.stringify({
          url:this.userInfo.url,
          sFilingId:this.userInfo.sfilingId,
          sPaperItems:this.userInfo.spaperItems,
          sEnforcementMode:this.userInfo.senforcementMode,//this.$refs.text.value,
          sAccessory:this.userInfo.saccessory,
          sCost:this.userInfo.scost,
          sUndertaker:this.userInfo.sundertaker,
          sUndertakerDeptId:this.userInfo.sundertakerDeptId[this.userInfo.sundertakerDeptId.length-1],
          punit:this.userInfo.sundertakerDeptId.toString(),
          sCreateTime:this.userInfo.screateTime,
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
        this.dialogTitle='编辑'
        this.userInfo={
          sundertakerDeptId:[]
        }

        this.userInfo=row
        console.log('ces',row.punit.split(',').map(Number))
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
        this.fileList=[{name:row.saccessory,url:row.url}]
        this.tf='none';
        this.ad=''//编辑/审核页面出来,

        if(row.sstatus==0){
          this.tj=''//提交审核按钮
            this.bc='none'//保存按钮隐藏
          this.gx=''//更新按钮显示
        }
        else if(row.sstatus==1){
          this.tj='none'//提交审核按钮
          this.bc='none'//保存按钮隐藏
          this.gx='none'//更新按钮显示
        }
        else {
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
          sPaperItems:this.userInfo.spaperItems,
          sEnforcementMode:this.userInfo.senforcementMode,//this.$refs.text.value,
          sAccessory:this.userInfo.saccessory,
          sCost:this.userInfo.scost,
          sUndertaker:this.userInfo.sundertaker,
          sUndertakerDeptId:this.userInfo.sundertakerDeptId[this.userInfo.sundertakerDeptId.length-1],
          punit:this.userInfo.sundertakerDeptId.toString()
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
        console.log(this.userInfo.senforcementMode)
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
        var ids = data.map(item => { return { sstatus: item.sstatus } })
        var ids1 =true
        for(var i = 0; i < ids.length; i++) {
          console.log('ids[i].sstatus'+ids[i].sstatus)
          if(ids[i].sstatus=='1'){
            ids1=false
          }
        }
        console.log(ids1)
        if(!ids1){//判断处于审核中的不能删除

          this.$notify({
            title: '温馨提示',
            message: '该记录处于审核中不能删除',
            type: 'warning',
            duration: 2000
          })
        }else {
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
      }}
    },
      add(){
        this.fileList=[]//清空upload
        this.userInfo={

        }
        this.tf='none'//父页面隐藏
        this.ad=''//新增页面出现
        this.tj='none'
        this.gx='none'
        this.bc=''
        this.userInfo.senforcementMode=''
        this.userInfo.sfilingId='BA'+this.genID(10);
        this.userInfo.sstatus='创建'
        this.userInfo.screateName=this.name
        /* 动态赋值实时设置当前时间*/
        this.$set(this.userInfo,'screateTime',new Date())
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
        userInfo: {
          sundertakerDeptId:['']
        },
        dialogTitle: '增加',
        rowIndex: null,
        search:'',
        currentPage4: 1,
        pageSize:10,
        total:0,
        currentPage:1,
        listLoading:true,
        tableData: [],
        addorobj:2
      }
    }
  }
</script>

