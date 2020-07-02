<template>
  <div>
    <div ></div>
    <el-form :inline="true" class="demo-form-inline" v-bind:style="{display:tf}" >
      <div align="center" style="margin-top: 30px">
        <el-form-item>
          <el-input
            v-model="search"
            class="search_name"
            placeholder="来访人姓名">
          </el-input>
        </el-form-item>
        <el-form-item>
          <el-input
            v-model="besearch"
            class="search_name"
            placeholder="被反映人姓名">
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
            class="el-icon-refresh"  plain @click="onrest" >重置
          </el-button>
        </el-form-item></div><br/>
      <div><el-form-item>
        <el-button type="primary" class="el-icon-plus" @click="add">新增</el-button>
        <el-button type="primary" class="el-icon-delete" @click="del">删除</el-button></el-form-item></div>
      <el-table
        :data="tableData.slice((currentPage-1)*pageSize,currentPage*pageSize)"
        border
        style="width: 100%" ref="multipleTable" :cell-style='cellStyle':header-cell-style='rowClass'>
        <el-table-column type="selection"  width="55px"></el-table-column>
        <el-table-column prop="lid" v-if="false">

        </el-table-column>
        <el-table-column prop="letterId" v-if="false">

        </el-table-column>
        <el-table-column
          prop="ltime"
          label="信访时间"
          width="180">
        </el-table-column>
        <el-table-column
          prop="lcomplainantName"
          label="来访人姓名"
          width="180">
        </el-table-column>
        <el-table-column
          prop="ldeptId"
          label="来访人单位/部门"
          width="180">
        </el-table-column>
        <el-table-column
          prop="lpostId"
          label="来访人岗位/职务">
        </el-table-column>
        <el-table-column
          prop="lpersonBeReported"
          label="被反映人姓名">
        </el-table-column>
        <el-table-column
          prop="lpbrDeptId"
          label="被反映人单位/部门">
        </el-table-column>
        <el-table-column
          prop="lpbrPostId"
          label="被反映人岗位/职务">
        </el-table-column>
        <el-table-column
          prop="lcontent"
          label="原始信访内容">
        </el-table-column>

        <el-table-column prop="lcreateName" label="创建人姓名" v-if="false" >

        </el-table-column>
        <el-table-column prop="lstatus" label="状态" v-if="false" >

        </el-table-column>
        <el-table-column prop="lcreateTime" label="创建时间" v-if="false"></el-table-column>
        <el-table-column label="操作" fixed="right" align="center"  prop="lStatus">
          <template slot-scope="scope">
            <el-button type="primary" size="small" v-if="scope.row.lstatus==0" @click="bj(scope.$index, scope.row)">编辑</el-button>
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
    <div v-bind:style="{display:ad}" style="background-color: lightgray;width: 100%;height: 700px" >
      <el-main>      <el-form :inline="true" :model="userInfo" class="demo-form-inline" label-width="220px">
        <div style="background-color: white;width: 100%;height: 65px" >
          <br/>
          <div align="right" ><el-form-item >
            <el-button type="primary" class="el-icon-edit" align="right" @click="submitUser"  v-bind:style="{display:tj}">保存</el-button>
            <el-button type="primary" class="el-icon-edit" @click="tjsh"  v-bind:style="{display:tjshButton}">提交审核</el-button>
            <el-button type="primary" class="el-icon-edit" @click="bjbcmethod"  v-bind:style="{display:bjbc}">更新</el-button>
            <el-button type="primary" class="el-icon-back" @click="back">返回</el-button></el-form-item></div></div>
        <br/>
        <div style="background-color: white">
          <el-input v-model="userInfo.lid" placeholder="编号" type="hidden" ></el-input>
          <el-form-item label="信访编号">

            <el-input v-model="userInfo.letterId" placeholder="信访编号" style="width: 830px" disabled="disabled"></el-input>
          </el-form-item>
          <el-form-item label="来访人姓名">
            <el-input v-model="userInfo.lcomplainantName" placeholder="来访人姓名" style="width: 300px" disabled="disabled"></el-input>
          </el-form-item>
          <el-form-item label="被反映人姓名">
            <el-input v-model="userInfo.lpersonBeReported" v-bind:disabled='bename' placeholder="被反映人姓名" style="width: 300px" ></el-input>
          </el-form-item>
          <br/>
          <el-form-item label="来访人部门/单位">
            <el-input v-model="userInfo.ldeptId" placeholder="来访人部门/单位" style="width: 300px" disabled="disabled"></el-input>
          </el-form-item>
          <el-form-item label="被反映人部门/单位">
            <el-input v-model="userInfo.lpbrDeptId"  v-bind:disabled='beunit'  placeholder="被反映人部门/单位" style="width: 300px" ></el-input>
          </el-form-item>
          <br/>
          <el-form-item label="来访人岗位/职务">
            <el-input v-model="userInfo.lpostId" placeholder="来访人岗位/职务" style="width: 300px" disabled="disabled"></el-input>
          </el-form-item>
          <el-form-item label="被反映人岗位/职务">
            <el-input v-model="userInfo.lpbrPostId" v-bind:disabled='beduty' placeholder="被反映人岗位/职务" style="width: 300px" ></el-input>
          </el-form-item>
          <br/>
          <el-form-item label="原始信访内容">
            <el-input type="textarea" v-model="userInfo.lcontent" placeholder="原始信访内容" style="width: 840px;" v-bind:disabled="ysnr"></el-input>
          </el-form-item>
          <br/>
          <el-form-item label="创建者">
            <el-input v-model="userInfo.lcreateName" placeholder="创建者" style="width: 300px" disabled="disabled"></el-input>
          </el-form-item>
          <el-form-item label="创建时间">
            <el-date-picker v-model="userInfo.lcreateTime" placeholder="创建时间" style="width: 300px" type="datetime" disabled="disabled"></el-date-picker>
          </el-form-item>
          <br/>
        </div>
      </el-form></el-main>
    </div>
  </div>
</template>

<script>
  import { mapGetters } from 'vuex'
  import qs from 'qs'
  import {xfbh,add,del,findbyName} from '@/api/daily/letter'
  import {listLetterByid,tjshme,bjbcmethod} from '@/api/daily/userReport'
  import {getFileGroup} from '@/api/duty/talk'
  export default {  computed: {
      ...mapGetters([
        'nickname',
        'userId', 'role'
      ])
    },
    created() {
      this.initList();
    },
    methods:{//设置表格内容居中
      cellStyle({row, column, rowIndex, columnIndex}){
        return 'text-align:center';
      },
      rowClass({row, rowIndex}){//设置表头居中
        return 'text-align:center';
      },
    //提交审核
      tjsh(){
        let posdata=qs.stringify({
            lid:this.userInfo.lid})
        tjshme(posdata).then((response)=>{
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
      },
    //更新
      bjbcmethod(){
        let posdata=qs.stringify({
         lid:this.userInfo.lid,
          lContent:this.userInfo.lcontent
        })
        bjbcmethod(posdata).then((response)=>{
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
      },//重置
      onrest(){
        this.search=''
        this.besearch=''
      },  handleItemChange(value){//点击选择时初始化谈话对象和记录人
        var checkedNodes = this.$refs['cascaderUnit'].getCheckedNodes()//
      },
      //初始化页面
      initList() {
        let posdata=qs.stringify({
          lid:this.userId
        })
        listLetterByid(posdata).then(response =>{
          console.debug(response)
          this.tableData = response.list
          this.total = response.list.length
        })
      },//新增
      add(){
        this.userInfo={};
        xfbh().then((response)=>{
          this.userInfo.letterId='XF'+response.xfbh
          console.log('XF'+response.xfbh)
          this.userInfo.lcreateName =this.nickname
        this.userInfo.lcomplainantName=this.nickname
        this.userInfo.lpostId=this.role
        this.userInfo.ldeptId='纪检部'
         // console.debug('id='+this.id)
          /* 动态赋值实时设置当前时间*/
          this.$set(this.userInfo,'lcreateTime',new Date())
          this.tf='none';
          this.ad=''
          this.tj='',//提交按钮
          this.bjbc='none'//编辑保存按钮隐藏
          this.ysnr=false//原始信访内容
          this.beunit=false//被反映人单位
          this.beduty=false//被反映人职务
          this.bename=false//被反映人姓名
        })

      },//新增提交
      submitUser(){
        let endtime = new Date(this.userInfo.lcreateTime).toJSON();
        this.userInfo.lcreateTime = new Date(+new Date(endtime) + 8 * 3600 * 1000)

          .toISOString()
          .replace(/T/g, " ")
          .replace(/\.[\d]{3}Z/, "")
        let posdata=qs.stringify({
          letterId:this.userInfo.letterId,
          userName:this.userInfo.userName,
          lComplainantName:this.userInfo.lcomplainantName,
          lPersonBeReported:this.userInfo.lpersonBeReported,
          lDeptId:this.userInfo.ldeptId,
          lPostId:this.userInfo.lpostId,
          lPbrDeptId:this.userInfo.lpbrDeptId,
          lPbrPostId:this.userInfo.lpbrPostId,
          lContent:this.userInfo.lcontent,
          lCreateName:this.nickname,
          lTime: this.userInfo.lcreateTime,
          lCreateTime:this.userInfo.lcreateTime,
          lCreateId:this.userId,
          lStatus:0
        })
        add(posdata).then((response)=>{
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
      },
      //编辑
      bj(index, row) {
        this.tf='none';
        this.ad=''//编辑/审核页面出来,
        this.tj='none',//提交按钮

        this.bjbc=''
        this.ysnr=false//原始信访内容
        this.beunit='disabled',//被反映人单位
          this.beduty='disabled',//被反映人职务
          this.bename='disabled'//被反映人姓名
        this.sfjc='disabled'
        this.userInfo=row
        this.userInfo.lcreateName=this.nickname
      },// 删除
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
          var ids = data.map(item => { return { lStatus: item.lStatus } })
          var ids1 =true
          for(var i = 0; i < ids.length; i++) {
            console.log('ids[i].lStatus'+ids[i].lStatus)
            if(ids[i].lStatus!='0'){
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
        }
      },//多条件查询
        //按标题查询
      onSearch() {
        let postData = qs.stringify({
          lComplainantName:this.search,
          lPersonBeReported:this.besearch,
          lCreateId:this.userId,
          lStatus:1
        });
        this.listLoading = true
        findbyName(postData).then((response) =>{
          this.currentPage = 1
          this.tableData = response.list
          console.debug(this.tableData)
          this.total=response.list.length
          this.listLoading=false
        })
      },
      //返回
      back(){this.ad='none',this.tf=''
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
        ysnr:false,//原始信访内容
        beunit:false,//被反映人单位
        beduty:false,//被反映人职务
        bename:false,//被反映人姓名
        tj:'',//提交按钮
        tjshButton:'',//提交审核按钮
        bjbc:'none',//编辑时的保存按钮
        nrzy:'none',//内容摘要，默认不显示
        sfjc:false,
        tf:false,
        ad:'none',
        iconFormVisible: false,
        userInfo: {},
        dialogTitle: '增加',
        rowIndex: null,
        search:'',
        besearch:'',
        currentPage4: 1,
        pageSize:10,
        total:0,
        currentPage:1,

        tableData: []
      }
    }
  }
</script>

