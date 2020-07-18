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
        <!-- <el-button type="primary" class="el-icon-plus" @click="add" v-if="hasPerm('letter:add')">新增</el-button>
          <el-button type="primary" class="el-icon-delete" @click="del"  v-if="hasPerm('letter:delete')">删除</el-button>--></el-form-item></div>
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
          width="160">
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
          label="被反映人姓名"  width="100">
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
        <el-table-column prop="puni" label="单位路径" v-if="false" >
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
            <el-button v-if="scope.row.lstatus==1" v-bind:style="{display:(hasPerm('letter:firstaudit')?'':'none')}" type="primary" size="small" @click="handleEdit(scope.$index, scope.row)">审核</el-button>
            <el-button type="primary" size="small" v-if="scope.row.lstatus==2" v-bind:style="{display:(hasPerm('letter:audit')?'':'none')}" @click="bj(scope.$index, scope.row)">编辑</el-button>
            <el-button type="primary" size="small" v-if="scope.row.lstatus==3" v-bind:style="{display:(hasPerm('letter:zbaudit')?'':'none')}" @click="bj(scope.$index, scope.row)">编辑</el-button>
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
    <div v-bind:style="{display:ad}" style="background-color: lightgray;width: 100%" >
      <el-main>      <el-form :inline="true" :model="userInfo" class="demo-form-inline" label-width="220px"  :rules="rules" ref="ruleForm">
        <div style="background-color: white;width: 100%;height: 65px;position:fixed; top:50px; left:-1px;z-index:2;" >
          <br/>
          <div align="right" ><el-form-item >
            <el-button type="primary" class="el-icon-edit" align="right" @click="submitUser('ruleForm')"  v-bind:style="{display:tj}">保存</el-button>
            <el-button type="primary" class="el-icon-edit" @click="turn" v-if="hasPerm('letter:sencondadudit') && userInfo.lstatus==2"  v-bind:style="{display:zb}">转办</el-button>
            <el-button type="primary" class="el-icon-edit" @click="shbc" v-if="hasPerm('letter:firstaudit')&& userInfo.lstatus==1" v-bind:style="{display:xqbc}">保存</el-button>
            <el-button type="primary" class="el-icon-edit" @click="kezhangbc" v-if="hasPerm('letter:sencondadudit')&&(userInfo.lstatus==2?true:false)" v-bind:style="{display:xqbc}">保存</el-button>
            <el-button type="primary" class="el-icon-edit" @click="deptbc" v-if="hasPerm('letter:zbaudit') && userInfo.lstatus==3" v-bind:style="{display:xqbc}">保存</el-button>
            <el-button type="primary" class="el-icon-edit" @click="leaderbc" v-if="hasPerm('letter:leadersign') && userInfo.lstatus==2"v-bind:style="{display:xqbc}">局领导意见确认</el-button>
            <el-button type="primary" class="el-icon-edit" @click="zzbc" v-if="hasPerm('letter:groupsign') && userInfo.lstatus==2"  v-bind:style="{display:xqbc}">纪检组长意见确认</el-button>
            <el-button type="primary" class="el-icon-back" @click="back('ruleForm')">返回</el-button></el-form-item></div></div>
        <br/>
        <div style="background-color: white;margin-top: 7px;z-index:3;">
          <el-input v-model="userInfo.lid" placeholder="编号" type="hidden" ></el-input>
          <el-form-item label="状态"  v-if="false">
            <el-input v-model="userInfo.lstatus" placeholder="状态" style="width: 300px" v-if="false"></el-input>
          </el-form-item>

          <el-form-item label="信访编号">

            <el-input v-model="userInfo.letterId" placeholder="信访编号" style="width: 300px" disabled="disabled"></el-input>
          </el-form-item>
          <el-form-item label="转办部门">
            <el-cascader ref='cascaderUnit' :show-all-levels="false"
                         :placeholder="defaUnit"
                         :props="props"
                         :options="options_cascader"
                         :expandTrigger="'hover'"
                         clearable v-model="userInfo.deptname"   style="width: 300px" :disabled="hasPerm('letter:sencondadudit') && userInfo.lstatus==2? false:'disabled'"></el-cascader>
          </el-form-item>
          <br />
          <el-form-item label="来访人姓名">
            <el-input v-model="userInfo.lcomplainantName" placeholder="来访人姓名" style="width: 300px" disabled="disabled"></el-input>
          </el-form-item>
          <el-form-item label="被反映人姓名" prop="lpersonBeReported">
            <el-select v-model="userInfo.lpersonBeReported"  placeholder="请选择被反映人姓名" style="width: 300px" :disabled= "userInfo.lstatus==null?false:'disabled'">
              <el-option
                v-for="item in optionss"
                :key="item.sid"
                :label="item.name"
                :value="item.sid">
              </el-option>
            </el-select></el-form-item>
          <br/>
          <el-form-item label="来访人部门/单位">
            <el-input v-model="userInfo.ldeptId" placeholder="来访人部门/单位" style="width: 300px" disabled="disabled"></el-input>
          </el-form-item>
          <el-form-item label="被反映人部门/单位" prop="lpbrDeptId">
            <el-cascader ref='cascaderUnit' :show-all-levels="false" v-if="isShowAddressInfo"
                         placeholder="请选择被反映人部门/单位"
                         :props="props"
                         :options="options_cascader"
                         :expandTrigger="'hover'"
                         clearable v-model="userInfo.lpbrDeptId" @change="handleItemChange"  style="width: 300px" :disabled= "userInfo.lstatus==null?false:'disabled'"></el-cascader>
          </el-form-item>
          <br/>
          <el-form-item label="来访人岗位/职务">
            <el-input v-model="userInfo.lpostId" placeholder="来访人岗位/职务" style="width: 300px" disabled="disabled"></el-input>
          </el-form-item>
          <el-form-item label="被反映人岗位/职务" prop="lpbrPostId">
            <el-select v-model="userInfo.lpbrPostId"  placeholder="请选择被反映人岗位/职务" style="width: 300px" @change="change" :disabled= "userInfo.lstatus==null?false:'disabled'" >
              <el-option
                v-for="item in options"
                :key="item.pid"
                :label="item.pname"
                :value="item.pid">
              </el-option>
            </el-select>
          </el-form-item>
          <br/>
          <el-form-item label="原始信访内容" prop="lcontent">
            <el-input type="textarea" v-model="userInfo.lcontent" placeholder="请输入原始信访内容" :rows=8 style="width: 840px;" :disabled= "userInfo.lstatus==null?false:'disabled'" ></el-input>
          </el-form-item>
          <br/>
          <el-form-item label="信访内容摘要"  v-bind:style="{display:nrzy}" >
            <el-input type="textarea" :disabled= "userInfo.lstatus==1?false:'disabled'"  v-model="userInfo.lsynopsis" placeholder="请输入信访内容摘要" style="width: 840px;" :rows=8 ></el-input>
          </el-form-item>
          <br/>

          <el-form-item label="监察科部门意见" v-if="true                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             " >
            <el-input v-model="userInfo.lsupervisionComments" placeholder="请输入监察科部门意见" style="width: 300px" v-bind:disabled=" hasPerm('letter:sencondadudit')&& userInfo.lstatus==2?false:'disabled'" ></el-input>
          </el-form-item>
          <el-form-item label="监察科部门意见签署时间" >
            <el-date-picker v-model="userInfo.lsupervisionCommentsTime" placeholder="监察科部门意见签署时间" style="width: 300px" type="datetime" disabled="disabled"></el-date-picker>
          </el-form-item><br/>
          <el-form-item label="纪检组长意见" >
            <el-input v-model="userInfo.ldisciplinaryComments" placeholder="请输入纪检组长意见" style="width: 300px"  v-bind:disabled=" hasPerm('letter:groupsign')&& userInfo.lstatus==2?false:'disabled'"></el-input>
          </el-form-item>
          <el-form-item label="纪检组长签署时间" >
            <el-date-picker v-model="userInfo.ldisciplinaryTime" placeholder="纪检组长签署时间" style="width: 300px" type="datetime" disabled="disabled"></el-date-picker>
          </el-form-item><br/>
          <el-form-item label="局领导意见"  >
            <el-input v-model="userInfo.lleadersComments" placeholder="请输入局领导意见" style="width: 300px" v-bind:disabled=" hasPerm('letter:leadersign')&& userInfo.lstatus==2?false:'disabled'" ></el-input>
          </el-form-item>
          <el-form-item label="局领导签署时间" >
            <el-date-picker v-model="userInfo.lleadersTime" placeholder="局领导签署时间" style="width: 300px" type="datetime" disabled="disabled"></el-date-picker>
          </el-form-item><br/>
          <el-form-item label="转办部门处理结果"  >
            <el-input v-model="userInfo.lresult" placeholder="请输入转办部门处理结果" style="width: 300px"  v-bind:disabled=" hasPerm('letter:zbaudit')&& this.userInfo.lstatus==3?false:'disabled'"  ></el-input>
          </el-form-item>
          <el-form-item label="转办部门处理时间" >
            <el-date-picker v-model="userInfo.lresultTime" placeholder="转办部门处理时间" style="width: 300px" type="datetime" disabled="disabled"></el-date-picker>
          </el-form-item><br/>
          <el-form-item label="监察科自办结果"  >
            <el-input v-model="userInfo.lsupervisionResutl" placeholder="请输入监察科自办结果" style="width: 300px" v-bind:disabled=" hasPerm('letter:sencondadudit')&& this.userInfo.lstatus==2?false:'disabled'"  ></el-input>
          </el-form-item>
          <el-form-item label="监察科自办时间" >
            <el-date-picker v-model="userInfo.lsupervisionResultTime" placeholder="监察科自办时间" style="width: 300px" type="datetime" disabled="disabled"></el-date-picker>
          </el-form-item><br/>
          <el-form-item label="创建者"  >
            <el-input v-model="userInfo.lcreateName" placeholder="创建者" style="width: 300px" disabled="disabled"></el-input>
          </el-form-item>
          <el-form-item label="创建时间"  >
            <el-date-picker v-model="userInfo.lcreateTime" placeholder="创建时间" style="width: 300px" type="datetime" disabled="disabled"></el-date-picker>
          </el-form-item><br/>
        </div>
      </el-form></el-main>
    </div>
  </div>
</template>

<script>
  import { mapGetters } from 'vuex'
  import qs from 'qs'
  import {list,add,del,findbyName,shbc,turndept,kezhangbc,deptbc,leaderbc,zzbc,finddb} from '@/api/daily/letter'
  import {initdept,initemploy,letterreporter} from '@/api/nologin/nologin'
  import {getFileGroup} from '@/api/duty/talk'
  export default {  computed: {
      ...mapGetters([
        'nickname',
        'userId', 'role','mid'
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
      },//提交审核
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
      },//新增
      add(){
        let posdata=qs.stringify({nickname:this.nickname})
        this.userInfo={}

        letterreporter().then((response)=>{
          this.userInfo.letterId=response.letterid
          this.userInfo.lcreateName =this.nickname
          this.userInfo.lcomplainantName=this.nickname
          finddb(posdata).then((response)=>{
            // console.log('测试',response.list.mname)
            this.userInfo.lpostId=response.list.pname
            this.userInfo.ldeptId=response.list.mname
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

        })


      },
      //监察科自办
      kezhangbc(){

        let stime = new Date(this.userInfo.lsupervisionResultTime).toJSON();
        this.userInfo.lsupervisionResultTime = new Date(+new Date(stime) + 8 * 3600 * 1000)
          .toISOString()
          .replace(/T/g, " ")
          .replace(/\.[\d]{3}Z/, "")

        let posdata=qs.stringify({
          lid:this.userInfo.lid,
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
        if(this.userInfo.lresult==null||this.userInfo.lresult==''){
          this.$notify({
            title: '温馨提示',
            message: '请填写完转办部门处理结果',
            type: 'warning',
            duration: 2000
          })
        }else {
          let endtime = new Date(this.userInfo.lresultTime).toJSON();
          this.userInfo.lresultTime = new Date(+new Date(endtime) + 8 * 3600 * 1000)
            .toISOString()
            .replace(/T/g, " ")
            .replace(/\.[\d]{3}Z/, "")

          let posdata=qs.stringify({
            lid:this.userInfo.lid,
            lResult:this.userInfo.lresult,
            lResultTime:this.userInfo.lresultTime,
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
        }

      },//局领导签署意见
      leaderbc(){
        if(this.userInfo.lleadersComments==null||this.userInfo.lleadersComments==''){
          this.$notify({
            title: '温馨提示',
            message: '请填写完意见',
            type: 'warning',
            duration: 2000
          })
        }else {
          let endtime = new Date(this.userInfo.lleadersTime).toJSON();
          this.userInfo.lleadersTime = new Date(+new Date(endtime) + 8 * 3600 * 1000)
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
        }

      },//纪检组长签署意见
      zzbc(){
        if(this.userInfo.ldisciplinaryComments==''){
          this.$notify({
            title: '温馨提示',
            message: '请填写完意见',
            type: 'warning',
            duration: 2000
          })
        }else{
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
        }

      },
      //判断状态给提示
      cstatus: function (row, column, cellValue) {
        if (cellValue == 0){
          return '创建';
        }else if (cellValue == 1){
          return '初审';
        }
        else if(cellValue==2){
          return '二审'
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
          if(this.userInfo.deptname==undefined){
            this.$notify({
              title: '温馨提示',
              message: '请选择一个部门转办',
              type: 'warning',
              duration: 2000
            })
          }else {
            let endtime1 = new Date(this.userInfo.lsupervisionCommentsTime).toJSON();
            this.userInfo.lsupervisionCommentsTime = new Date(+new Date(endtime1) + 8 * 3600 * 1000)
              .toISOString()
              .replace(/T/g, " ")
              .replace(/\.[\d]{3}Z/, "")
            let posdata=qs.stringify({
            lid:this.userInfo.lid,
            lmid:this.userInfo.deptname[this.userInfo.deptname.length-1].toString(),
            lSupervisionComments:this.userInfo.lsupervisionComments,//监察科部门意见
            lSupervisionCommentsTime:this.userInfo.lsupervisionCommentsTime,//监察科部门意见签署时间
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
        }
      },
      bjbcmethod(){},//重置
      onrest(){
        this.search=''
        this.besearch=''
      } ,handleItemChange(value){//点击选择时初始化谈话对象和记录人
        var checkedNodes = this.$refs['cascaderUnit'].getCheckedNodes()//选择的值

        if(checkedNodes[0]!=undefined){
          this.userInfo.lpbrDeptId=value
          console.debug('checkedNodes value'+checkedNodes[0].value)
          console.debug('lpbrDeptID'+this.userInfo.lpbrDeptId)
          let postdata=qs.stringify({
            mid:checkedNodes[0].value
          })
          initdept(postdata).then((response)=>{

            this.options = response.list

            //  console.log('职务'+JSON.stringify(response))

          })
        }
        else{
          this.options=[]
        }
        //
        /*       console.log(checkedNodes) // 获取当前点击的节点
                 console.log(checkedNodes[0].data.label) // 获取当前点击的节点的label
                 console.log(checkedNodes[0].pathLabels) // 获取由 label 组成的数组*/
      },//新增提交
      submitUser(formName){
        this.$refs[formName].validate((valid) => {
          if (valid) {

            this.userInfo.lcreateTime = new Date(+new Date() + 8 * 3600 * 1000)

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
              lPbrDeptId:this.userInfo.lpbrDeptId[this.userInfo.lpbrDeptId.length-1].toString(),
              puni:this.userInfo.lpbrDeptId.toString(),
              lPbrPostId:this.userInfo.lpbrPostId,
              lContent:this.userInfo.lcontent,
              lCreateName:this.nickname,
              lTime: this.userInfo.lcreateTime,
              lCreateTime:this.userInfo.lcreateTime,
              lCreateId:this.userId,
              lStatus:1
            })
            add(posdata).then((response)=>{
              this.ad='none'
              this.tf=''
              this.$refs[formName].resetFields();
              this.initList()
              this.$notify({
                title: '成功',
                message: response.message,
                type: 'success',
                duration: 2000
              })
            })
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      //初始化页面
      initList() {
        console.log('测试mid',this.mid);
        let status;
        let mids;
        if(this.hasPerm('letter:sencondaudit')){
          //  status=2
        }
        else if(this.hasPerm('letter:firstaudit')){
          //status=1
        }
        else if(this.hasPerm('letter:zbaudit')&&this.role.includes('单位/部门负责人')){
          mids=this.mid
          status=3
        }
        let posdata=qs.stringify({
          mid:mids,
          lStatus:status
        })
        this.listLoading=true
        list(posdata).then(response =>{
          //  console.log(JSON.stringify(response))
          this.tableData = response.list
          this.total = response.list.length
          this.listLoading=false
        })
      },//审核
      handleEdit(index, row) {
        this.userInfo={
          lpbrDeptId:[]
        }
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
        this.userInfo.lpbrDeptId = row.puni.split(',').map(Number)
        this.isShowAddressInfo = false;
        // 这里搞个定时器重新载入一下组件就可以触发组件拉取数据
        setTimeout(() => {
          this.isShowAddressInfo = true;
        }, 10);
        /*     let postdata=qs.stringify({
               mid:row.puni.split(',').map(Number)[row.puni.split(',').map(Number).length-1]
             })
             initdept(postdata).then((response)=>{

               this.options = response.list

               //  console.log('职务'+JSON.stringify(response))

             })*/
      },//详情
      details(index, row) {
        this.nrzy=''//内容摘要显示
        this.tj='none',//提交按钮
          this.tjshButton='none'//提交审核按钮隐藏
        this.zb='none',//转办按钮默认隐藏
          this.bc='none'//保存按钮默认隐藏
        this.xqbc='none'//点击详情时保存都隐藏
        this.userInfo={
          lpbrDeptId:[]
        }

        this.userInfo=row
        console.log('测试',row.puni.split(',').map(Number))
        this.userInfo.lpbrDeptId = row.puni.split(',').map(Number)
        this.isShowAddressInfo = false;
        // 这里搞个定时器重新载入一下组件就可以触发组件拉取数据
        setTimeout(() => {
          this.isShowAddressInfo = true;
        }, 10);

        /* let postdata=qs.stringify({
           mid:row.puni.split(',').map(Number)[row.puni.split(',').map(Number).length-1]
         })
         initdept(postdata).then((response)=>{

             this.options = response.list
         let postsdata=qs.stringify({
           ppid:this.userInfo.lpbrPostId
         })
         initemploy(postsdata).then((response)=>{

           this.optionss = response.list

        // console.log('职务'+JSON.stringify(response))

       })*/

        this.tf='none';
        this.ad=''//编辑/审核页面出来,

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
        this.userInfo.lpbrDeptId = row.puni.split(',').map(Number)
        this.isShowAddressInfo = false;
        // 这里搞个定时器重新载入一下组件就可以触发组件拉取数据
        setTimeout(() => {
          this.isShowAddressInfo = true;
        }, 10);
if(this. hasPerm('letter:zbaudit')){
        this.$set(this.userInfo,'lresultTime',new Date())}
        if( this.hasPerm('letter:sencondadudit')&& this.userInfo.lstatus==2){
        this.$set(this.userInfo,'lsupervisionCommentsTime',new Date())
        this.$set(this.userInfo,'lsupervisionResultTime',new Date())}
        if(this.hasPerm('letter:groupsign')&& this.userInfo.lstatus==2){
        this.$set(this.userInfo,'ldisciplinaryTime',new Date())}
        if(this.hasPerm('letter:leadersign')&& this.userInfo.lstatus==2){
        this.$set(this.userInfo,'lleadersTime',new Date())}
        // this.userInfo.lCreateName=this.nickname
      },//纪检监察员审核提交保存
      shbc(){
        if(this.userInfo.lsynopsis==''){
          this.$notify({
            title: '温馨提示',
            message: '请填写信访内容摘要',
            type: 'warning',
            duration: 2000
          })
        }else{
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
        }

      },// 删除
      del(){
        var data = this.$refs.multipleTable.selection;
        // console.log("11"+data)
        if(JSON.stringify(data)=='[]'){
          this.$notify({
            title: '温馨提示',
            message: '请选择一行进行删除',
            type: 'success',
            duration: 2000
          })
        }
        else {
          var ids = data.map(item => { return { lstatus: item.lstatus } })
          var ids1 =true
          for(var i = 0; i < ids.length; i++) {
            // console.log('ids[i].lstatus'+ids[i].lstatus)
            if(ids[i].lstatus!='4'){
              ids1=false
            }
          }
          // console.log(ids1)
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

            //  console.debug('选中行数据'+JSON.stringify(data))
            del(postData).then((response) =>{
              this.initList();
              this.$notify({
                title: '成功',
                message: response.message,
                type: 'success',
                duration: 2000
              })
            })}
        }
      },//多条件查询
        //按标题查询
      onSearch() {
        let status;
        let mids;

        if(this.hasPerm('letter:zbaudit')&&this.role.includes('单位/部门负责人')){
          mids=this.mid
          status=3
        }
        let postData = qs.stringify({
          lComplainantName:this.search,
          lPersonBeReported:this.besearch,
          lStatus:status,
          mid:mids
        });
        this.listLoading = true
        findbyName(postData).then((response) =>{
          this.currentPage = 1
          this.tableData = response.list
          //   console.debug(this.tableData)
          this.total=response.list.length
          this.listLoading=false
        })
      },
      //返回
      back(formName){this.ad='none',this.tf='' ,this.nrzy='none'//内容摘要文本框
        this.$refs[formName].resetFields();
        this.initList()
        this.xqbc=''//点击返回是保存都隐藏
      },
      handleSizeChange(size) {
        this.pageSize = size;
        //  console.log(this.pageSize)
      },
      handleCurrentChange(currentPage) {
        this.currentPage = currentPage;
        // console.log(this.currentPage)  //点击第几页
      },   change(){
        let postdata=qs.stringify({
          ppid:this.userInfo.lpbrPostId
        })
        initemploy(postdata).then((response)=>{

          this.optionss = response.list

          //  console.log('职务'+JSON.stringify(response))

        })
      }
    },
    data() {
      return {rules: {
          lpersonBeReported: [
            { required: true, message: '请选择被反映人姓名', trigger: 'blur' }
          ],
          lpbrDeptId: [
            { required: true, message: '请选择被反映人部门/单位', trigger: 'blur' },
          ],
          lpbrPostId: [
            { required: true, message: '请选择被反映人岗位/职务', trigger: 'blur' }

          ],
          lcontent: [
            { required: true, message: '请输入原始信访内容', trigger: 'blur' }
          ]
        },
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
              // console.log('response.list'+JSON.stringify(response.list))
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
        optionss:[],
        ysnr:false,//原始信访内容
        beunit:false,//被反映人单位
        beduty:false,//被反映人职务
        bename:false,//被反映人姓名
        tj:'',//提交按钮
        tjshButton:'',//提交审核按钮
        zb:'none',//转办按钮默认隐藏
        bc:'none',//审核时的保存按钮默认隐藏
        bjbc:'none',//编辑时的保存按钮
        nrzy:'none',//内容摘要，默认不显示
        sfjc:false,
        tf:false,
        ad:'none',
        iconFormVisible: false,
        userInfo: {lpbrDeptId:['']},
        dialogTitle: '增加',
        rowIndex: null,
        search:'',
        besearch:'',
        currentPage4: 1,
        pageSize:10,
        total:0,
        currentPage:1,
        isShowAddressInfo:true,
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

