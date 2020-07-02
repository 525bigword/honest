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
      <div ><el-form-item>
       <!-- <el-button type="primary" class="el-icon-plus" @click="add">新增</el-button>
        <el-button type="primary" class="el-icon-delete" @click="del">删除</el-button>--></el-form-item></div>
      <el-table
        :data="tableData.slice((currentPage-1)*pageSize,currentPage*pageSize)"
        border
        style="width: 100%;" ref="multipleTable" :cell-style='cellStyle':header-cell-style='rowClass' v-loading="listLoading" >
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
          width="70">
        </el-table-column>
        <el-table-column
          prop="ldeptId"
          label="来访人单位/部门"
          width="80">
        </el-table-column>
        <el-table-column
          prop="lpostId"
          label="来访人岗位/职务"  width="100">
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
          label="被反映人岗位/职务"  width="100">
        </el-table-column>
        <el-table-column
        prop="lcontent"
        label="原始信访内容"  width="100">
      </el-table-column>
        <el-table-column
          prop="lsynopsis"
          label="信访内容摘要"  width="80">
        </el-table-column>
        <el-table-column prop="lcreateName" label="创建人姓名" v-if="false" >
        </el-table-column>
        <el-table-column prop="lresult" label="转办处理结果" v-if="false" >
        </el-table-column>
        <el-table-column prop="lresultTime" label="转办处理时间" v-if="false" >
        </el-table-column>
        <el-table-column prop="lsupervisionComments" label="监察科部门意见" v-if="false" >
        </el-table-column>
        <el-table-column prop="lsupervisionCommentsTime" label="监察科部门意见处理时间" v-if="false" >
        </el-table-column>
        <el-table-column prop="lsupervisionResutl" label="监察科自办结果" v-if="false" >
        </el-table-column>
        <el-table-column prop="lsupervisionResultTime" label="监察科时间" v-if="false" >
        </el-table-column>
        <el-table-column prop="ldisciplinaryComments" label="纪检组长意见" v-if="false" >
        </el-table-column>
        <el-table-column prop="ldisciplinaryTime" label="纪检组长签署时间" v-if="false" >
        </el-table-column>
        <el-table-column prop="lleadersComments" label="局领导意见" v-if="false" >
        </el-table-column>
        <el-table-column prop="lleadersTime" label="局领导签署时间" v-if="false" >
        </el-table-column>
        <el-table-column prop="lCreateTime" label="创建时间" v-if="false"></el-table-column>
        <el-table-column prop="lstatus" label="状态" :formatter="cstatus" width="80">

        </el-table-column>
        <el-table-column label="操作" fixed="right" align="center"  prop="lstatus"  >
          <template slot-scope="scope" >
            <el-button v-if="scope.row.lstatus==1" v-bind:style="{display:(role.includes('纪检监察员')?'':'none')}" type="primary" size="small" @click="handleEdit(scope.$index, scope.row)">审核</el-button>
            <el-button type="primary" size="small" v-if="scope.row.lstatus==2||scope.row.lstatus==3" v-bind:style="{display:(role.includes('纪检监察科科长')||role.includes('纪检组长')||role.includes('局领导')?'':'none')}" @click="bj(scope.$index, scope.row)">编辑</el-button>
            <el-button type="primary" size="small" v-if="scope.row.lstatus==3" v-bind:style="{display:(role.includes('单位/部门负责人')?'':'none')}" @click="bj(scope.$index, scope.row)">编辑</el-button>
            <el-button type="info" size="small"  @click="details(scope.$index, scope.row)">详情</el-button>

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
        <div style="background-color: white;width: 100%;height: 65px;position:fixed; top:50px; left:-1px;z-index:2;" >
<br/>
        <div align="right" ><el-form-item >
          <el-button type="primary" class="el-icon-edit" @click="turn" v-if="role.includes('纪检监察科科长')"  v-bind:style="{display:zb}">转办</el-button>
          <el-button type="primary" class="el-icon-edit" @click="shbc" v-if="role.includes('纪检监察员')" v-bind:style="{display:xqbc}">保存</el-button>
          <el-button type="primary" class="el-icon-edit" @click="kezhangbc" v-if="role.includes('纪检监察科科长')&&(userInfo.lstatus==2?true:false)" v-bind:style="{display:xqbc}">保存</el-button>
          <el-button type="primary" class="el-icon-edit" @click="deptbc" v-if="role.includes('单位/部门负责人')" v-bind:style="{display:xqbc}">保存</el-button>
          <el-button type="primary" class="el-icon-edit" @click="leaderbc" v-if="role.includes('局领导')" v-bind:style="{display:xqbc}">保存</el-button>
          <el-button type="primary" class="el-icon-edit" @click="zzbc" v-if="role.includes('纪检组长')" v-bind:style="{display:xqbc}">保存</el-button>
          <el-button type="primary" class="el-icon-back" @click="back">返回</el-button></el-form-item></div></div>
        <br/>
        <div style="background-color: white;margin-top: 7px;z-index:3;">
        <el-input v-model="userInfo.lid" placeholder="编号" type="hidden" ></el-input>
          <el-form-item label="状态"  v-if="false">
            <el-input v-model="userInfo.lstatus" placeholder="状态" style="width: 300px" v-if="false"></el-input>
          </el-form-item>
        <el-form-item label="信访编号">

          <el-input v-model="userInfo.letterId" placeholder="信访编号" style="width: 300px" disabled="disabled"></el-input>
        </el-form-item>
        <el-form-item label="转办部门"  v-bind:style="{display:zhuanban}" v-if="role.includes('纪检监察科科长')">
          <el-cascader ref='cascaderUnit' :show-all-levels="false"
                       :placeholder="defaUnit"
                       :props="props"
                       :options="options_cascader"
                       :expandTrigger="'hover'"
                       clearable v-model="userInfo.deptname" @change="handleItemChange"  style="width: 300px" v-bind:disabled="zhuanbanjy"></el-cascader>
        </el-form-item>
          <br />
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
          <el-form-item label="信访内容摘要"  v-bind:style="{display:nrzy}" >
            <el-input type="textarea" v-bind:disabled="sfjc" v-model="userInfo.lsynopsis" placeholder="信访内容摘要" style="width: 840px;"  ></el-input>
          </el-form-item>
          <br/>
          <el-form-item label="监察科部门意见" v-if="role.includes('纪检监察科科长')" >
            <el-input v-model="userInfo.lsupervisionComments" placeholder="监察科部门意见" style="width: 300px" ></el-input>
          </el-form-item>
          <el-form-item label="监察科部门意见签署时间" v-if="role.includes('纪检监察科科长')" >
            <el-date-picker v-model="userInfo.lsupervisionCommentsTime" placeholder="监察科部门意见签署时间" style="width: 300px" type="datetime" disabled="disabled"></el-date-picker>
          </el-form-item><br/>
          <el-form-item label="纪检组长意见" v-if="role.includes('纪检组长')" >
            <el-input v-model="userInfo.ldisciplinaryComments" placeholder="纪检组长意见" style="width: 300px" ></el-input>
          </el-form-item>
          <el-form-item label="纪检组长签署时间" v-if="role.includes('纪检组长')" >
            <el-date-picker v-model="userInfo.ldisciplinaryTime" placeholder="纪检组长签署时间" style="width: 300px" type="datetime" disabled="disabled"></el-date-picker>
          </el-form-item><br/>
          <el-form-item label="局领导意见" v-if="role.includes('局领导')" >
            <el-input v-model="userInfo.lleadersComments" placeholder="局领导意见" style="width: 300px" ></el-input>
          </el-form-item>
          <el-form-item label="局领导签署时间" v-if="role.includes('局领导')" >
            <el-date-picker v-model="userInfo.lleadersTime" placeholder="局领导签署时间" style="width: 300px" type="datetime" disabled="disabled"></el-date-picker>
          </el-form-item><br/>
          <el-form-item label="转办部门处理结果" v-if="role.includes('单位/部门负责人')" >
            <el-input v-model="userInfo.lresult" placeholder="转办部门处理结果" style="width: 300px" ></el-input>
          </el-form-item>
          <el-form-item label="转办部门处理时间"  v-if="role.includes('单位/部门负责人')" >
            <el-date-picker v-model="userInfo.lresultTime" placeholder="转办部门处理时间" style="width: 300px" type="datetime" disabled="disabled"></el-date-picker>
          </el-form-item><br/>
          <el-form-item label="监察科自办结果"  v-if="role.includes('纪检监察科科长')" >
            <el-input v-model="userInfo.lsupervisionResutl" placeholder="监察科自办结果" style="width: 300px" ></el-input>
          </el-form-item>
          <el-form-item label="监察科自办时间"  v-if="role.includes('纪检监察科科长')" >
            <el-date-picker v-model="userInfo.lsupervisionResultTime" placeholder="监察科自办时间" style="width: 300px" type="datetime" disabled="disabled"></el-date-picker>
          </el-form-item><br/>
          <el-form-item label="创建者"  v-if="false">
            <el-input v-model="userInfo.lcreateName" placeholder="创建者" style="width: 300px" disabled="disabled"></el-input>
          </el-form-item>
          <el-form-item label="创建时间"  v-if="false">
            <el-date-picker v-model="userInfo.lcreateTime" v-if="false" placeholder="创建时间" style="width: 300px" type="datetime" disabled="disabled"></el-date-picker>
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
  import {xfbh,list,add,del,findbyName,shbc,turndept,kezhangbc,deptbc,leaderbc,zzbc} from '@/api/daily/letter'
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
    //监察科自办
      kezhangbc(){
        let endtime = new Date(this.userInfo.lsupervisionCommentsTime).toJSON();
        this.userInfo.lsupervisionCommentsTime = new Date(new Date(endtime) + 8 * 3600 * 1000)
          .toISOString()
          .replace(/T/g, " ")
          .replace(/\.[\d]{3}Z/, "")
        let stime = new Date(this.userInfo.lsupervisionResultTime).toJSON();
        this.userInfo.lsupervisionResultTime = new Date(new Date(stime) + 8 * 3600 * 1000)
          .toISOString()
          .replace(/T/g, " ")
          .replace(/\.[\d]{3}Z/, "")

        let posdata=qs.stringify({
  lid:this.userInfo.lid,
  lSupervisionComments:this.userInfo.lsupervisionComments,//监察科部门意见
  lSupervisionCommentsTime:this.userInfo.lsupervisionCommentsTime,//监察科部门意见签署时间
           lSupervisionResutl:this.userInfo.lsupervisionResutl,//监察科自办结果
  lSupervisionResultTime:this.userInfo.lsupervisionResultTime//监察科自办时间
})
        kezhangbc(posdata).then((response)=>{
          this.tf='';
        this.ad='none'
        this.initList();
        this.$notify({
          title: '温馨提示',
          message: '监察科自办处理完成',
          type: 'success',
          duration: 2000
        })
        })
      },
      //转办部门处理
      deptbc(){
        let endtime = new Date(this.userInfo.lresultTime).toJSON();
        this.userInfo.lresultTime = new Date(new Date(endtime) + 8 * 3600 * 1000)
          .toISOString()
          .replace(/T/g, " ")
          .replace(/\.[\d]{3}Z/, "")

        let posdata=qs.stringify({
          lid:this.userInfo.lid,
          lResult:this.userInfo.lresult,
          lResultTime:this.userInfo.lresultTime
        })
        deptbc(posdata).then((response)=>{
          this.tf='';
        this.ad='none'
        this.initList();
          this.$notify({
          title: '温馨提示',
          message: '本部门处理完成',
          type: 'success',
          duration: 2000
        })
        })
      },//局领导签署意见
      leaderbc(){
        let endtime = new Date(this.userInfo.lleadersTime).toJSON();
        this.userInfo.lleadersTime = new Date(new Date(endtime) + 8 * 3600 * 1000)
          .toISOString()
          .replace(/T/g, " ")
          .replace(/\.[\d]{3}Z/, "")

        let posdata=qs.stringify({
          lid:this.userInfo.lid,
          lLeadersComments:this.userInfo.lleadersComments,
          lLeadersTime:this.userInfo.lleadersTime
        })
        leaderbc(posdata).then((response)=>{
          this.tf='';
          this.ad='none'
          this.initList();
          this.$notify({
            title: '温馨提示',
            message: '局领导处理完成',
            type: 'success',
            duration: 2000
          })
        })
      },//纪检组长签署意见
      zzbc(){
        let endtime = new Date(this.userInfo.ldisciplinaryTime).toJSON();
        this.userInfo.ldisciplinaryTime = new Date(new Date(endtime) + 8 * 3600 * 1000)
          .toISOString()
          .replace(/T/g, " ")
          .replace(/\.[\d]{3}Z/, "")

        let posdata=qs.stringify({
          lid:this.userInfo.lid,
          lDisciplinaryComments:this.userInfo.ldisciplinaryComments,
          lDisciplinaryTime:this.userInfo.ldisciplinaryTime
        })
        zzbc(posdata).then((response)=>{
          this.tf='';
          this.ad='none'
          this.initList();
          this.$notify({
            title: '温馨提示',
            message: '纪检组长处理完成',
            type: 'success',
            duration: 2000
          })
        })
      },
    //判断状态给提示
    cstatus: function (row, column, cellValue) {
      if (cellValue == 0){
        return '创建';
      }else if (cellValue == 1){
        return '待审';
      }
      else if(cellValue==2){
        return '审核中'
      }
      else if(cellValue==3){
        return '转办'
      }
      else {
        return '已审核'
      }
    },
    //转办部门
      turn(){
if(this.userInfo.lstatus==3){
  this.$notify({
    title: '温馨提示',
    message: '已转办部门',
    type: 'warning',
    duration: 2000
  })
}
else {
  let posdata=qs.stringify({
    lid:this.userInfo.lid
  })
        turndept(posdata).then((response)=>{
          this.tf='';
          this.ad='none'
          this.initList();
          this.$notify({
            title: '成功',
            message: response.data.message,
            type: 'success',
            duration: 2000
          })
        })}
      },
      bjbcmethod(){},//重置
     onrest(){
        this.search=''
       this.besearch=''
      },  handleItemChange(value){//点击选择时初始化谈话对象和记录人
        var checkedNodes = this.$refs['cascaderUnit'].getCheckedNodes()//
      },
      //初始化页面
      initList() {
        let status;
        if(this.role.includes('纪检监察科科长')){
          status=2
        }
        else if(this.role.includes('纪检监察员')){
          status=1
        }
        else if(this.role.includes('单位/部门负责人')){
          status=3
        }
        let posdata=qs.stringify({
          lStatus:status
        })
        this.listLoading=true
        list(posdata).then(response =>{
          console.log(JSON.stringify(response))
          this.tableData = response.list
          this.total = response.list.length
          this.listLoading=false
        })
      },//审核
      handleEdit(index, row) {
        this.tf='none';
        this.ad=''//编辑/审核页面出来,
        this.nrzy=''
        this.zhuanban=''
        this.zhuanbanjy='disabled'
        this.tj='none',//提交按钮
          this.zb='none',//转办按钮默认隐藏
          this.bc=''//审核保存按钮默认显示
        this.bjbc='none'//编辑保存按钮隐藏
        this.sfjc=false
        this.ysnr='disabled'//原始信访内容
        this.beunit='disabled',//被反映人单位
          this.beduty='disabled',//被反映人职务
          this.bename='disabled'//被反映人姓名
        this.userInfo=row

        console.debug('测试状态'+row.lStatus)
       // console.debug('id='+this.id)
      },//详情
      details(index, row) {
        this.tf='none';
        this.ad=''//编辑/审核页面出来,
        this.nrzy=''//内容摘要显示
        this.tj='none',//提交按钮
          this.zb='none',//转办按钮默认隐藏
          this.bc='none'//保存按钮默认隐藏
        this.xqbc='none'//点击详情时保存都隐藏
        this.userInfo=row
      },
      //编辑
      bj(index, row) {

        this.tf='none';
        this.ad=''//编辑/审核页面出来,
        this.nrzy=''
        this.zhuanban=''
        this.zhuanbanjy=false
        this.tj='none',//提交按钮
          this.zb='',//转办按钮默认隐藏
          this.bc='none'//保存按钮默认隐藏
        this.bjbc=''
        this.ysnr='disabled'//原始信访内容
        this.beunit='disabled',//被反映人单位
          this.beduty='disabled',//被反映人职务
          this.bename='disabled'//被反映人姓名
        this.sfjc='disabled'
        this.userInfo=row
        this.$set(this.userInfo,'lresultTime',new Date())
        this.$set(this.userInfo,'lsupervisionCommentsTime',new Date())
        this.$set(this.userInfo,'lsupervisionResultTime',new Date())
        this.$set(this.userInfo,'ldisciplinaryTime',new Date())
        this.$set(this.userInfo,'lleadersTime',new Date())
       // this.userInfo.lCreateName=this.nickname
      },//纪检监察员审核提交保存
      shbc(){
        let postData = qs.stringify({
          lid:this.userInfo.lid,
          lSynopsis:this.userInfo.lsynopsis
        });
        shbc(postData).then((response)=>{
          this.initList();
          this.$notify({
            title: '成功',
            message: response.message,
            type: 'success',
            duration: 2000
          })
          this.tf='';
          this.ad='none'//编辑/审核页面出来,
        })
      },// 删除
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
      },//多条件查询
        //按标题查询
      onSearch() {
        let postData = qs.stringify({
          lComplainantName:this.search,
          lPersonBeReported:this.besearch
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
      back(){this.ad='none',this.tf='' ,this.nrzy='none'//内容摘要文本框
         this.xqbc=''//点击返回是保存都隐藏
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
        xqbc:'',//点击详情时保存隐藏
        defaUnit:'请选择转办部门',
        options_cascader:[],//级联选择器的options属性
        options:[],
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
        listLoading:true,
        zhuanbanjy:'disabled',
        zhuanban:'none',
        ysnr:false,//原始信访内容
        beunit:false,//被反映人单位
        beduty:false,//被反映人职务
        bename:false,//被反映人姓名
        tj:'',//提交按钮
        zb:'none',//转办按钮默认隐藏
        bc:'none',//审核时的保存按钮默认隐藏
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

        tableData: [/*{
          userId:'2019/2/2 19:09',
          userDate: '黎智英',
          userName: '财务部',
          userAddress: '财务部干事',
          state:'刘罗国',
          bydept:'财务部',
          byjob:'总经理',
          letcontent:'测试',
          lettermain:'测试',
          lStatus:0
        }, {
          userId:'2019/2/2 19:09',
          userDate: '张江游',
          userName: '采购科',
          userAddress: '采购科干事',
          state:'李源',
          bydept:'市场分部',
          byjob:'总经理',
          letcontent:'测试',
          lettermain:'测试',
          lStatus:0
        }, {
          userId:'2019/2/2 19:09',
          userDate: '刘英',
          userName: '财务部',
          userAddress: '财务部干事',
          state:'韩联安',
          bydept:'财务部',
          byjob:'主任',
          letcontent:'测试',
          lettermain:'测试',
          lStatus:0
        }, {
          userId:'2019/2/2 19:09',
          userDate: '朴修夏',
          userName: '市场分部',
          userAddress: '干事',
          state:'林黎语',
          bydept:'市场分部',
          byjob:'干事',
          letcontent:'测试',
          lettermain:'测试',
          lStatus:1
        }*/]
      }
    }
  }
</script>

