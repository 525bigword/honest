<template>
  <div>
    <el-form :inline="true" class="demo-form-inline" v-bind:style="{display:tf}">
      <div align="center" style="margin-top: 30px">
        <el-form-item>

          <el-select v-model="search" class="search_name" placeholder="请选择谈话类型">
            <el-option label="例行廉政谈话" value="例行廉政谈话"></el-option>
            <el-option label="任前廉政谈话" value="任前廉政谈话"></el-option>
            <el-option label="提醒谈话" value="提醒谈话"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button
            type="primary"
            @click="onSearch()"
            class="el-icon-search" >查询
          </el-button>
          <el-button
            type="primary"
            class="el-icon-refresh" plain @click="onrest" >重置
          </el-button>
        </el-form-item></div><br/>
      <div><el-form-item>
        <el-button type="primary" class="el-icon-plus" @click="add" v-if="this.hasPerm('honestconversation:add')">新增</el-button>
        <el-button type="primary" class="el-icon-delete" @click="del" v-if="this.hasPerm('honestconversation:delete')">删除</el-button></el-form-item></div>
      <el-table
        :data="tableData.slice((currentPage-1)*pageSize,currentPage*pageSize)"
        border
        style="width: 100%" ref="multipleTable" :cell-style='cellStyle':header-cell-style='rowClass' v-loading="listLoading">
        <el-table-column type="selection" width="55px"></el-table-column>
        <el-table-column
          label="序号"
          type="index"
          width="80"></el-table-column>
        <el-table-column
          prop="id"
          label="编号"
          width="80" v-if="false">
        </el-table-column>
        <el-table-column
          prop="type"
          label="谈话类型"
          width="180">
        </el-table-column>
        <el-table-column
          prop="punit"
          label="谈话对象单位"
          width="180"   v-if="false">
        </el-table-column>
        <el-table-column v-if="false" prop="dept" label="部门路径"></el-table-column>
        <el-table-column  label="谈话类型"
                          width="180"
                          prop="users[0].mechanisms[0].mechanismName" v-if="false"
        ></el-table-column>
        <el-table-column
          prop="users[0].name"
          label="谈话对象姓名"
          width="180"   v-if="false">
        </el-table-column>
        <el-table-column
          prop="personname"
          label="记录人"
          width="180"   v-if="false">
        </el-table-column>
        <el-table-column
          prop="time"
          label="谈话时间"
          width="200">
        </el-table-column>
        <el-table-column
          prop="users[0].politicalAppearance"
          label="谈话对象政治面貌"
          width="180"   v-if="false">
        </el-table-column>
        <el-table-column
          prop="users[0].posts[0].pname"
          label="谈话对象职务"
          width="180"   v-if="false">
        </el-table-column>
        <el-table-column
          prop="site"
          label="谈话地点"
          width="180">
        </el-table-column>
        <el-table-column
          prop="syllabus"
          label="谈话提纲"  v-if="false">
        </el-table-column>
        <el-table-column
          prop="content"
          label="谈话内容"  v-if="false">
        </el-table-column>
        <el-table-column
          prop="staus"
          label="状态"  :formatter="cstatus" width="100px">
        </el-table-column>
        <el-table-column label="操作" fixed="right" align="center">
          <template slot-scope="scope">
            <el-button type="primary" size="small" @click="handleEdit(scope.$index, scope.row)" v-if=" hasPerm('honestconversation:update')&& scope.row.staus==0">编辑</el-button>
            <el-button type="primary" size="small" v-if="scope.row.staus==0 && hasPerm('honestconversation:add')|| hasPerm('honestconversation:update')&& scope.row.staus==0" @click="tjsh(scope.$index, scope.row)">提交保存</el-button>
          <el-button type="primary" size="small" v-if="scope.row.staus==1" @click="sh(scope.$index, scope.row)">编辑</el-button>
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
      </div> </el-form>
    <!--隐藏窗-->
    <div v-bind:style="{display:ad}" style="background-color: lightgray;width: 100%;" >
      <el-main>      <el-form :inline="true" :model="userInfo" class="demo-form-inline" label-width="180px" :rules="rules" ref="ruleForm">
        <div style="background-color: white;width: 100%;height: 65px;position:fixed; top:50px; left:-1px;z-index:2 ;" >
          <br/>
          <div align="right" ><el-form-item >
            <el-button type="primary" class="el-icon-edit"   v-bind:style="{display:bc}"  @click="submitUser('ruleForm')"  >保存</el-button>
            <el-button type="primary" class="el-icon-edit" @click="gxmethod('ruleForm')" v-bind:style="{display:gx}">更新</el-button>
           <!-- <el-button type="primary" class="el-icon-edit" @click="passtg('通过')" v-bind:style="{display:tg}">审核通过</el-button>
            <el-button type="primary" class="el-icon-edit" @click="passtg('驳回')" v-bind:style="{display:bh}">驳回</el-button>-->

            <el-button type="primary" class="el-icon-back" @click="back('ruleForm')">返回</el-button></el-form-item></div></div>
        <br/>
        <div style="background-color: white;margin-top: 7px;z-index:3;">
          <el-input v-model="userInfo.id" placeholder="编号" type="hidden"></el-input>
          <el-input v-model="userInfo.staus" placeholder="状态" type="hidden"></el-input>

          <el-form-item label="谈话对象单位" prop="punit">
            <el-cascader ref='cascaderUnit' :show-all-levels="false" v-if="isShowAddressInfo" :disabled="userInfo.staus==undefined||userInfo.staus==0||userInfo.staus==1&& !role.includes('单位/部门负责人')&& !role.includes('局领导')?false:'disabled'"
                         :placeholder="defaUnit"
                         :props="props"
                         :options="options_cascader"
                         :expandTrigger="'hover'"
                         clearable v-model="userInfo.punit" @change="handleItemChange"  style="width: 400px"></el-cascader>
          </el-form-item>
          <el-form-item label="谈话对象姓名" prop="pid">
            <el-select v-model="userInfo.pid" placeholder="请选择谈话对象姓名" style="width: 400px" :disabled="userInfo.staus==undefined||userInfo.staus==0||userInfo.staus==1&& !role.includes('单位/部门负责人')&& !role.includes('局领导')?false:'disabled'">
              <el-option
                v-for="item in options"
                :key="item.sid"
                :label="item.name"
                :value="item.sid">
              </el-option>
            </el-select>
          </el-form-item><br/>
          <el-form-item label="记录人" prop="personid">
            <el-select v-model="userInfo.personid" placeholder="请选择记录人" style="width: 400px" :disabled="userInfo.staus==undefined||userInfo.staus==0||userInfo.staus==1&& !role.includes('单位/部门负责人')&& !role.includes('局领导')?false:'disabled'">
              <el-option
                v-for="item in options"
                :key="item.sid"
                :label="item.name"
                :value="item.sid">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="谈话时间" prop="time">
            <el-date-picker v-model="userInfo.time" placeholder="请选择谈话时间" type="datetime"  style="width: 400px" :disabled="userInfo.staus==undefined||userInfo.staus==0||userInfo.staus==1&& !role.includes('单位/部门负责人')&& !role.includes('局领导')?false:'disabled'"></el-date-picker>
          </el-form-item><br/>
          <el-form-item label="谈话对象政治面貌" prop="zzmm">
            <el-select v-model="userInfo.zzmm" placeholder="请选择谈话对象政治面貌" style="width: 400px" :disabled="userInfo.staus==undefined||userInfo.staus==0||userInfo.staus==1&& !role.includes('单位/部门负责人')&& !role.includes('局领导')?false:'disabled'">
              <el-option label="党员" value="党员"></el-option>
              <el-option label="团员" value="团员"></el-option>
              <el-option label="群众" value="群众"></el-option>
              <el-option label="其他" value="其他"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="谈话对象职务" prop="duty">
            <el-input v-model="userInfo.duty" placeholder="请输入谈话对象职务" style="width: 400px" :disabled="userInfo.staus==undefined||userInfo.staus==0||userInfo.staus==1&& !role.includes('单位/部门负责人')&& !role.includes('局领导')?false:'disabled'"></el-input>
          </el-form-item><br/>
          <el-form-item label="谈话类型" prop="type">
            <el-select v-model="userInfo.type" placeholder="请选择谈话类型" style="width: 400px" :disabled="userInfo.staus==undefined||userInfo.staus==0||userInfo.staus==1&& !role.includes('单位/部门负责人')&& !role.includes('局领导')?false:'disabled'">
              <el-option label="例行廉政谈话" value="例行廉政谈话"></el-option>
              <el-option label="任前廉政谈话" value="任前廉政谈话"></el-option>
              <el-option label="提醒谈话" value="提醒谈话"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="谈话地点" prop="site">
            <el-input v-model="userInfo.site" placeholder="请输入谈话地点" style="width: 400px" :disabled="userInfo.staus==undefined||userInfo.staus==0||userInfo.staus==1&& !role.includes('单位/部门负责人')&& !role.includes('局领导')?false:'disabled'"></el-input>
          </el-form-item>
          <br/>
          <el-form-item label="谈话提纲" prop="syllabus">
            <el-input v-model="userInfo.syllabus"  type="textarea" placeholder="请输入400字符以内的谈话提纲" style="width: 1000px;" :rows="6" :disabled="userInfo.staus==undefined||userInfo.staus==0||userInfo.staus==1&& !role.includes('单位/部门负责人')&& !role.includes('局领导')?false:'disabled'"></el-input>
          </el-form-item>
          <br/>
          <el-form-item label="谈话内容" prop="content">
         <!--   <el-input v-model="userInfo.content" type="textarea" placeholder="请输入2000字符以内的谈话内容" style="width: 1000px" :rows="16"></el-input>-->
            <el-card class="box-card" style="margin-bottom:30px;width: 830px;text-align: left" v-if="userInfo.staus==undefined||userInfo.staus==0||userInfo.staus==1&& !role.includes('单位/部门负责人')&& !role.includes('局领导')?false:true">
              <div  v-html="userInfo.content"></div>
            </el-card>

            <quill-editor  id="editer"  v-bind:disabled='nr'  ref="text" v-model="userInfo.content" class="myQuillEditor" :options="editorOption" style="width: 1000px;height: 450px;margin-bottom: 100px" v-if="userInfo.staus==undefined||userInfo.staus==0||userInfo.staus==1&& !role.includes('单位/部门负责人')&& !role.includes('局领导')"/>

          </el-form-item><br/>
          <el-form-item label="创建人" v-if="false">
            <el-input v-model="userInfo.createname" placeholder="创建人" disabled="disabled" style="width: 400px" v-if="false"></el-input>
          </el-form-item>
          <el-form-item label="创建时间" v-if="false">
            <el-date-picker v-model="userInfo.createtime" placeholder="创建时间" type="datetime"  style="width: 400px" disabled="disabled" ></el-date-picker>
          </el-form-item>
        </div>
      </el-form></el-main>
    </div>
  </div>
</template>

<script>
  import { mapGetters } from 'vuex'
  import { list,findbytitle,del,updatecontent,passaudit,subaudit,getFileGroup,initpersons,add } from '@/api/duty/talk'
  import qs from 'qs'
  let num = 0;
  export default {
    computed: {
      ...mapGetters([
        'nickname',
        'userId', 'role','mid'
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
      submitUser(formName){
        if(this.userInfo.pid==this.userInfo.personid){
          this.$notify({
            title: '温馨提示',
            message: "记录人和谈话对象不能是同一个人",
            type: 'warning',
            duration: 2000
          })
        }else {
          this.$refs[formName].validate((valid) => {
            if (valid) {
              let endtime = new Date(this.userInfo.createtime).toJSON();
              this.userInfo.createtime = new Date(+new Date(endtime) + 8 * 3600 * 1000)
                .toISOString()
                .replace(/T/g, " ")
                .replace(/\.[\d]{3}Z/, "")
              let endtime1 = new Date(this.userInfo.time).toJSON();
              this.userInfo.time = new Date(+new Date(endtime1) + 8 * 3600 * 1000)
                .toISOString()
                .replace(/T/g, " ")
                .replace(/\.[\d]{3}Z/, "")

              let postData = qs.stringify({
                punit:this.userInfo.punit[this.userInfo.punit.length-1],//谈话对象单位
                dept:this.userInfo.punit.toString(),
                pid:this.userInfo.pid,//谈话对象姓名
                personid:this.userInfo.personid,//记录人
                time:this.userInfo.time,
                politcs:this.userInfo.zzmm,
                pduty:this.userInfo.duty,//谈话对象职务
                type:this.userInfo.type,//谈话类型
                site:this.userInfo.site,
                syllabus:this.userInfo.syllabus,
                content:this.userInfo.content,
                createid:this.userId,
                createname:this.userInfo.createname,
                createtime:this.userInfo.createtime,
                staus:0
              });
              //  console.log("postdata"+postData)
              add(postData).then((response)=>{
                this.ad='none',
                this.tf=''
              this.defaUnit='请选择谈话对象单位'
              this.initList();
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

        }

      },
      handleItemChange(value){//点击选择时初始化谈话对象和记录人
        var checkedNodes = this.$refs['cascaderUnit'].getCheckedNodes()//选择的值
        /*
        console.log('zz'+ value.join(',')); //全路径value值
         console.log('cc'+this.$refs.cascaderUnit.getCheckedNodes()[0].pathLabels); //全路径label值*/

        if(checkedNodes[0]!=undefined){
          this.userInfo.punit=value
          // console.log('checkedNodes label'+checkedNodes[0].label)
          let postdata=qs.stringify({
            id:checkedNodes[0].value
          })
          initpersons(postdata).then((response)=>{

            this.options = response.list

            //console.log('记录人'+JSON.stringify(response))

          })
        }
        else{
          this.options=[]
        }
        //
        /*       console.log(checkedNodes) // 获取当前点击的节点
                 console.log(checkedNodes[0].data.label) // 获取当前点击的节点的label
                 console.log(checkedNodes[0].pathLabels) // 获取由 label 组成的数组*/
      },
      onrest(){
        this.search=''
      },//判断状态给提示
      cstatus: function (row, column, cellValue) {
        if (cellValue == 0){
          return '创建';
        }else if (cellValue == 1){
          return '提交状态';
        }

      },
      back(formName){this.ad='none',//隐藏窗
        this.tf=''
        ,this.$refs[formName].resetFields()
        this.initList()
        this.tg='none'//通过按钮
        this.bh='none'//驳回按钮
      },
      gxmethod(formName){//更新
        if(this.userInfo.pid==this.userInfo.personid){
          this.$notify({
            title: '温馨提示',
            message: "记录人和谈话对象不能是同一个人",
            type: 'warning',
            duration: 2000
          })
        }else {
          this.$refs[formName].validate((valid) => {
            if (valid) {
              let endtime = new Date(this.userInfo.time).toJSON();
              this.userInfo.time = new Date(new Date(endtime) + 8 * 3600 * 1000)
                .toISOString()
                .replace(/T/g, " ")
                .replace(/\.[\d]{3}Z/, "")

              let postData = qs.stringify({
                id:this.userInfo.id,
                punit:this.userInfo.punit[this.userInfo.punit.length-1],//谈话对象单位
                dept:this.userInfo.punit.toString(),
                pid:this.userInfo.pid,//谈话对象姓名
                personid:this.userInfo.personid,//记录人
                time:this.userInfo.time,
                politcs:this.userInfo.zzmm,
                pduty:this.userInfo.duty,//谈话对象职务
                type:this.userInfo.type,//谈话类型
                site:this.userInfo.site,
                syllabus:this.userInfo.syllabus,
                content:this.userInfo.content,
              });
              updatecontent(postData).then((responese)=>{
                this.ad='none',//隐藏窗
                this.tf=''
              this.initList()
              this.$notify({
                title: '成功',
                message: responese.message,
                type: 'success',
                duration: 2000
              })
            })
            } else {
              console.log('error submit!!');
          return false;
        }
        });

        }

      },//提交审核
      tjsh(index,row){
        console.log('row'+JSON.stringify(row.id))
        let postData = qs.stringify({
          id:row.id
        });
        this.$confirm('确认提交保存吗？提交后将不能够再更新信息', '温馨提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
        }).then(() => {


          subaudit(postData).then((responese)=>{
            this.$notify({
              type: 'success',
              message: '提交成功!'
            });
            this.initList()
          })

        }).catch(() => {
          this.$notify({
            type: 'info',
            message: '已取消提交'
          });
        })
      },
      //通过还是驳回
      passtg(val){
        if(val=='通过'){
          let postData = qs.stringify({
            id:this.userInfo.id,
            status:2,
            auditresult:val
          });
          passaudit(postData).then((response)=>{
            this.$notify({
              type: 'success',
              message: '处理成功'
            });})
          this.ad='none'
          this.tf=''
          this.initList()
        }else{

          this.$confirm(`确定驳回吗?`, '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
          }).then(() => {

            let postData = qs.stringify({
              id:this.userInfo.id,
              status:0,
              auditresult:val
            });
            passaudit(postData).then((response)=>{
              this.$notify({
                type: 'success',
                message: '驳回成功'
              });
              this.ad='none'
              this.tf=''
              this.initList()
            })

          }).catch(() => {
            this.$notify({
              type: 'info',
              message: '已取消驳回'
            });
          })
        }
      },//审核
      sh(index,row){
        this.tg='',//通过按钮
          this.bh='',//驳回按钮

          this.userInfo={
            punit:[]
          }

        this.userInfo=row

        this.userInfo.punit = row.dept.split(',').map(Number)

        this.isShowAddressInfo = false;
        // 这里搞个定时器重新载入一下组件就可以触发组件拉取数据
        setTimeout(() => {
          this.isShowAddressInfo = true;
        }, 10);
        let postdata=qs.stringify({
          id:row.dept.split(',').map(Number)[row.dept.split(',').map(Number).length-1]
        })
        initpersons(postdata).then((response)=>{
          this.options = response.list
        })
        this.userInfo.name=Number(row.pid)
        this.userInfo.personname=Number(row.personid)
        this.userInfo.zzmm=row.users[0].politicalAppearance
        this.userInfo.duty=row.users[0].posts[0].pname
        this.bc='none'
        if(row.staus==0||row.staus==1&& !this.role.includes("单位/部门负责人")&& !this.role.includes('局领导')){
          this.gx=''
        } else{
          this.gx='none'
        }

        this.ad='',//隐藏窗
          this.tf='none'
      }
      ,
      //按标题查询
      onSearch() {
        let mid;
        if(this.role.includes('单位/部门负责人')){
          mid=this.mid;
        }
        let postData = qs.stringify({
          title:this.search,
          mid:mid
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
      //删除
      del(){ var data = this.$refs.multipleTable.selection;
        if(JSON.stringify(data)=='[]'){
          this.$notify({
            title: '温馨提示',
            message: '请选择一行进行删除',
            type: 'success',
            duration: 2000
          })
        }
        else {
          this.$confirm('请确认是否删除?', '温馨提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            var ids = data.map(item => { return { staus: item.staus } })
          var ids1 =true
          for(var i = 0; i < ids.length; i++) {
            console.log('ids[i].staus'+ids[i].staus)
            if(ids[i].staus=='1'){
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
        }).catch(() => {
            this.$message({
            type: 'info',
            message: '已取消删除'
          });
          this.initList();
        });
  }},   handleEdit(index, row) {
        this.userInfo={
          punit:[]
        }

        this.userInfo=row
        this.userInfo.punit = row.dept.split(',').map(Number)

        this.isShowAddressInfo = false;
        // 这里搞个定时器重新载入一下组件就可以触发组件拉取数据
        setTimeout(() => {
          this.isShowAddressInfo = true;
        }, 10);
        let postdata=qs.stringify({
          id:row.dept.split(',').map(Number)[row.dept.split(',').map(Number).length-1]
        })
        initpersons(postdata).then((response)=>{
          this.options = response.list
        })
        this.userInfo.name=Number(row.pid)
        this.userInfo.personname=Number(row.personid)
        this.tf='none';//表格父页面隐藏
        this.ad=''//编辑/审核页面出来,
        this.bc='none'
        if(row.staus==0||row.staus==1&& !this.role.includes("单位/部门负责人")){
          this.gx=''
        } else{
          this.gx='none'
        }

        this.userInfo.zzmm=row.users[0].politicalAppearance
        this.userInfo.duty=row.users[0].posts[0].pname
        /*    this.userInfo.name=[]
            this.userInfo.name.push(row.pid)
            this.userInfo.personname=[],
              this.userInfo.personname.push(row.personid)*/
        //  console.log('测试状态'+row.staus)

      },
      //初始化页面
      initList() {
        this.listLoading=true
        let mid;
        if(this.role.includes('单位/部门负责人')){
          mid=this.mid;
        }
        let posdata=qs.stringify({
          mid:mid
        })
        list(posdata).then(response =>{
          // console.log(response)
          this.tableData = response.list
          this.total = response.list.length
          this.listLoading=false
        })
      }, add() {
        this.tf='none';
        this.ad=''
        this.bc=''//保存按钮
        this.gx='none'
        this.dialogTitle = '增加';
        this.defaUnit='请选择谈话对象单位'
        this.userInfo={};
        this.userInfo.createname =this.nickname
        //  console.debug('id='+this.id)
        /* 动态赋值实时设置当前时间*/
        this.$set(this.userInfo,'createtime',new Date())
        //this.timer= setInterval(this.time, 1000 );

        this.iconFormVisible = true;
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
      return {rules: {
          punit: [
            {  required: true, message: '请选择谈话对象单位', trigger: 'blur' }
          ],pid: [
            {  required: true, message: '请选择谈话对象姓名', trigger: 'blur' }
          ],
          personid:[
            {  required: true, message: '请选择记录人', trigger: 'blur' }
          ],
          time:[
            {  required: true, message: '请选择谈话时间', trigger: 'blur' }
          ],
          zzmm:[
            {  required: true, message: '请输入谈话对象政治面貌', trigger: 'blur' }
          ],
          duty:[
            {  required: true, message: '请输入谈话对象职务', trigger: 'blur' }
          ],
          type:[
            {  required: true, message: '请选择谈话类型', trigger: 'blur' }

          ],
          site:[
            {  required: true, message: '请输入谈话地点', trigger: 'blur' }

          ],
          content:[
            {  required: true, message: '请输入谈话内容', trigger: 'blur' }

          ],
          syllabus:[
            {  required: true, message: '请输入谈话提纲', trigger: 'blur' }

          ]

        },
        listLoading:true,
        defaUnit:'请选择谈话对象单位',
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
        isShowAddressInfo:true,
        tg:'none',//通过按钮
        bh:'none',//驳回按钮
        gx:'none',
        bc:'',
        tf:false,
        ad:'none',//隐藏窗
        iconFormVisible: false,
        userInfo: {},
        dialogTitle: '增加',
        rowIndex: null,
        editorOption: {},
        search:'',
        currentPage4: 1,
        pageSize:10,
        total:0,
        currentPage:1,
        punits:[],//记录全路径
        tableData: [/*{
          id:'1',
          type: '任前廉政谈话',
          time: '2019/2/2 19:09',
          site: '怀化市鹤城区迎风路 1518 号',
          staus:'已审核'
        }, {
          id:'2',
          type: '任前廉政谈话',
          time: '2019/2/2 19:09',
          site: '怀化市鹤城区迎风路 1518 号',
          staus:'已审核'
        }, {
          id:'3',
          type: '例行廉政谈话',
          time: '2019/2/2 19:09',
          site: '怀化市鹤城区迎风路 1518 号',
          staus:'已审核'
        }, {
          id:'4',
          type: '任前廉政谈话',
          time: '2019/2/2 19:09',
          site: '怀化市鹤城区德贤路 123 号',
          staus:'未审核'
        }*/]
      }
    }
  }
</script>

