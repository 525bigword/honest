<template>
  <div>
    <el-form :inline="true" class="demo-form-inline" v-bind:style="{display:tf}">
      <div align="center" style="margin-top: 30px">
        <el-form-item>
          <el-input
            v-model="search"
            class="search_name"
            placeholder="请输入主体责任标题">
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
            class="el-icon-refresh" plain @click="onrest" >重置
          </el-button>
        </el-form-item></div><br/>
      <div><el-form-item>
        <el-button type="primary" class="el-icon-plus" @click="add"  v-bind:style="{display:(this.hasPerm('entityresponsibility:add')?'':'none')}">新增</el-button>
        <el-button type="primary" class="el-icon-delete" @click="dele"  v-bind:style="{display:(this.hasPerm('entityresponsibility:delete')?'':'none')}">删除</el-button></el-form-item></div>
      <el-table
        :data="tableData.slice((currentPage-1)*pageSize,currentPage*pageSize)"
        border
        style="width: 100%"  ref="multipleTable" :cell-style='cellStyle':header-cell-style='rowClass' v-loading="listLoading">
        <el-table-column type="selection" width="55px"></el-table-column>
        <el-table-column
          label="序号"
          type="index"
          width="80"></el-table-column>
        <el-table-column
          prop="id"
          label="序号"
          width="80" v-if="false">
        </el-table-column>
        <el-table-column
          prop="title"
          label="标题"
          width="360">
          <template slot-scope="scope"><el-tooltip :content="scope.row.staus==0||scope.row.staus==1&& !role.includes('单位/部门负责人')&& !role.includes('局领导')?'点击标题可修改主题责任信息':'点击标题可查看主体责任信息'" placement="top">
          <a  @click="handleEdit(scope.$index, scope.row)"
                target="_blank"
              class="buttonText" style="color: #1890ff">{{scope.row.title}}</a></el-tooltip>
          </template>
        </el-table-column>
        <el-table-column
          prop="users[0].name"
          label="创建者"
          width="180">
        </el-table-column>
        <el-table-column
          prop="createtime"
          label="发布时间">
        </el-table-column>
        <el-table-column
          prop="staus"
          label="状态"  :formatter="cstatus">
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
      </div></el-form>
    <!--隐藏窗-->
    <div v-bind:style="{display:ad}" style="background-color: lightgray;width: 100%;" :title="dialogTitle">
      <el-main>      <el-form :inline="true" :model="userInfo" class="demo-form-inline" label-width="180px" :rules="rules" ref="ruleForm">
        <div style="background-color: white;width: 100%;height: 65px;position:fixed; top:50px; left:-1px;z-index:2 ;" >
          <br/>
          <div align="right">
            <el-button type="primary"  @click="tjshmethod('ruleForm')" v-bind:style="{display:tjsh}">提交保存</el-button>
            <el-button type="primary"  @click="gxmethod('ruleForm')" v-bind:style="{display:gx}">更新</el-button>
            <el-button type="primary"   v-bind:style="{display:bc}"  @click="submitUser('ruleForm')">保存</el-button>
            <!--<el-button type="primary"  @click="tgmethod('通过')" v-bind:style="{display:tg}">通过</el-button>
            <el-button type="primary"  @click="tgmethod('不通过')"v-bind:style="{display:btg}">不通过</el-button>-->
            <el-button type="primary" class="el-icon-back" @click="deselect('ruleForm')">返回</el-button>
          </div></div>
        <br/>
        <div style="background-color: white;margin-top: 7px;z-index:3;">
          <el-input v-model="userInfo.id" placeholder="编号" type="hidden"></el-input>
          <el-form-item label="主体责任标题" prop="title">

            <el-input v-model="userInfo.title" placeholder="请输入主体责任标题" v-bind:disabled='bt' style="width: 300px"></el-input>
          </el-form-item><br/>
          <el-form-item label="主体责任内容" prop="content">
          <el-card class="box-card" style="margin-bottom:30px;width: 830px;text-align: left" v-if="userInfo.staus==undefined|| userInfo.staus==0||userInfo.staus==1&& !role.includes('单位/部门负责人')&& !role.includes('局领导')?false:true">
              <div  v-html="userInfo.content"></div>
            </el-card>

            <quill-editor  id="editer"    v-if="userInfo.staus==undefined|| userInfo.staus==0||userInfo.staus==1&& !role.includes('单位/部门负责人')&& !role.includes('局领导')"  ref="text" v-model="userInfo.content" class="myQuillEditor" :options="editorOption" style="width: 800px;height: 450px;margin-bottom: 100px" />

          </el-form-item><br/>
          <el-form-item label="创建者姓名">
            <el-input v-model="userInfo.createname" placeholder="创建者姓名" disabled="disabled"  style="width: 300px" ></el-input>
          </el-form-item><br/>
          <el-form-item label="创建时间">
            <el-date-picker v-model="userInfo.createtime" placeholder="创建时间" type="datetime"  style="width: 300px"  disabled="disabled"></el-date-picker>
          </el-form-item><br/>
          <el-form-item label="状态" v-if="false">
            <el-input v-model="userInfo.staus"  placeholder="状态"  disabled="disabled"  style="width: 300px" ></el-input>
          </el-form-item>
        </div>
      </el-form></el-main>
    </div>
  </div>
</template>

<script>
  import { quillEditor } from 'vue-quill-editor'
  import 'quill/dist/quill.core.css'
  import 'quill/dist/quill.snow.css'
  import 'quill/dist/quill.bubble.css'
  import qs from 'qs'
  import {loading} from 'element-ui'
  import { add, list,del,findbytitle,subaudit,passaudit,updatecontent } from '@/api/duty/main'
  import { mapGetters } from 'vuex'
  export default {
    components:{quillEditor},
    computed: {
      ...mapGetters([
        'nickname',
        'userId', 'role','mid'
      ])
    },  created() {
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
      gxmethod(formName){
        this.$refs[formName].validate((valid) => {
          if (valid) {
            let postData = qs.stringify({
              id:this.userInfo.id,
              title:this.userInfo.title,
              content:this.userInfo.content
            });
            updatecontent(postData).then((responese)=>{
              this.ad='none'//新增页面隐藏
            this.tf=''//表格页面显示
            this.initList()
            this.$notify({
              title: '成功',
              message: '更新成功',
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
      tjshmethod(formName){
        this.$confirm('确认提交保存吗？提交后将不能够再更新信息', '温馨提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          let endtime = new Date(this.userInfo.createtime).toJSON();
          this.userInfo.createtime = new Date(+new Date(endtime) + 8 * 3600 * 1000)
            .toISOString()
            .replace(/T/g, " ")
            .replace(/\.[\d]{3}Z/, "")
          if (this.dialogTitle === '增加') {
            this.$refs[formName].validate((valid) => {
              if (valid) {
                let postData = qs.stringify({
                  title: this.userInfo.title,
                  content: this.userInfo.content,
                  staus:0,
                  createtime:this.userInfo.createtime,
                  createid:this.userId
                });

                //新增
                add(postData).then((response) => {
                  let postData = qs.stringify({
                    id:response.id
                  });
                  console.log('tjsh'+response.id)
                  subaudit(postData).then((response)=>{
                    this.ad='none'//新增页面隐藏
                    this.tf=''//表格页面
                    this.initList()
                    this.$notify({
                      title: '成功',
                      message: '提交成功',
                      type: 'success',
                      duration: 2000
                    })
                  })
                })

              } else {
                console.log('error submit!!');
                return false;
              }
            });

          }
          else {
            this.$refs[formName].validate((valid) => {
              if (valid) {
                let postData = qs.stringify({
                  id:this.userInfo.id,
                  title:this.userInfo.title,
                  content:this.userInfo.content
                });
                updatecontent(postData).then((responese)=>{
                  let postData = qs.stringify({
                    id:this.userInfo.id
                  });
                  console.log('tjsh'+this.userInfo.id)
                  subaudit(postData).then((response)=>{
                    this.ad='none'//新增页面出现
                    this.tf=''//表格页面隐藏
                    this.initList()
                    this.$notify({
                      title: '成功',
                      message: '提交成功',
                      type: 'success',
                      duration: 2000
                    })
                  })
                })

              } else {
                console.log('error submit!!');
                return false;
              }
            });

          }
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消提交'
          });
        });
       },
      tgmethod(val){
        let postData = qs.stringify({
          id:this.userInfo.id,
          auditresult:val
        });
        console.log('tg'+this.userInfo.id)
        passaudit(postData).then((response)=>{
          this.ad='none'//编辑页面隐藏
          this.tf=''//表格页面出现
          this.initList()
          this.$notify({
            title: '成功',
            message: '处理成功',
            type: 'success',
            duration: 2000
          })
        })
      },
      //初始化页面
      initList() {
        let mid;
        if(this.role.includes('单位/部门负责人')){
          mid=this.mid;
        }
        let postData = qs.stringify({
          mid:mid
        });
        this.listLoading=true
        list(postData).then(response =>{
          console.debug(response)
          this.tableData = response.list
          this.total = response.list.length
          this.listLoading=false
        })
      }, add() {
        this.nr=false
        this.bt=false
        this.bc='',//保存按钮
          this.tg='none',//通过按钮不显示
          this.btg='none',//不通过按钮不显示
          this.gx='none',//更新按钮不显示
          this.tjsh=''//提交审核按钮显示
        this.dialogTitle = '增加';
        this.userInfo={};
        this.userInfo.createname =this.nickname
        /* 动态赋值实时设置当前时间*/
        this.$set(this.userInfo,'createtime',new Date())
        this.ad=''//新增页面出现
        this.tf='none'//表格页面隐藏
      },
      // 编辑
      handleEdit(index, row) {
        this.dialogTitle = '编辑';
        this.userInfo = row;
        this.userInfo.createname=row.users[0].name

        if(row.staus==0){

          this.nr=false
          this.bt=false
          this.bc='none',//保存按钮
            this.tg='none',//通过按钮不显示
            this.btg='none'//不通过按钮不显示
          if(this.hasPerm('entityresponsibility:update')) {
            this.gx=''//更新按钮显示
            this.tjsh=''//提交审核按钮显示
          } else {
            this.gx = 'none'//更新按钮显示
            this.tjsh = 'none'//提交审核按钮显示
          }
        }
        else if(row.staus==1){

          if(this.role.includes('单位/部门负责人')|| this.role.includes('局领导')){
            this.nr='disabled'//内容禁用
            this.bt='disabled'//标题禁用
            this.gx = 'none'//更新按钮显示
          }
          else{
            this.nr=false
            this.bt=false
            this.gx = ''//更新按钮显示

          }

          this.tjsh = 'none'//提交审核按钮显示
          this.bc='none'//保存按钮

        } else if(row.staus==undefined){
          this.nr=false
          this.bt=false
          this.bc=''//保存按钮

          this.gx = 'none'//更新按钮显示
          this.tjsh = ''//提交审核按钮显示

        }
        else{

          this.nr='disabled'
          this.bt='disabled'
          this.bc='none',//保存按钮
            this.tg='none',//通过按钮不显示
            this.btg='none',//不通过按钮不显示
            this.gx='none',//更新按钮不显示
            this.tjsh='none'//提交审核按钮显示
        }
        this.ad=''//编辑页面出现
        this.tf='none'//表格页面隐藏
        this.rowIndex = index;
      },
      dele(){
        var data = this.$refs.multipleTable.selection;
        console.debug("11"+data)
        if(JSON.stringify(data)=='[]'){
          this.$notify({
            title: '温馨提示',
            message: '请选择一行进行删除',
            type: 'success',
            duration: 2000
          })
        }
        else {      this.$confirm('请确认是否删除?', '温馨提示', {
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
 }
      },
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
      },//返回
      deselect(formName){
        this.ad='none'//默认新增页面隐藏
        this.tf=''//表格页面显示
          ,this.$refs[formName].resetFields()
        this.initList()
      },
      // 新增
      submitUser(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            let endtime = new Date(this.userInfo.createtime).toJSON();
            this.userInfo.createtime = new Date(+new Date(endtime) + 8 * 3600 * 1000)
              .toISOString()
              .replace(/T/g, " ")
              .replace(/\.[\d]{3}Z/, "")
            let postData = qs.stringify({
              title: this.userInfo.title,
              content: this.userInfo.content,
              staus:0,
              createtime:this.userInfo.createtime,
              createid:this.userId
            });

            //新增
            add(postData).then((response) => {
              this.ad='none'//新增页面隐藏
              this.tf=''//表格页面出现
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
          title: [
            {  required: true, message: '请输入主题责任标题', trigger: 'blur' }
          ],content: [
            {  required: true, message: '请输入主题责任内容', trigger: 'blur' }
          ]

        },
        listLoading:true,
        ad:'none',//默认新增页面隐藏
        tf:'',//表格页面显示
        nr:'disabled',
        bt:'disabled',
        bc:'none',//保存按钮
        tg:'none',//通过按钮不显示
        btg:'none',//不通过按钮不显示
        gx:'none',//更新按钮不显示
        tjsh:'none',//提交审核按钮显示
        editorOption: {},
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

