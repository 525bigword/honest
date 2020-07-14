<template>
  <div>
    <el-form :inline="true" class="demo-form-inline" v-bind:style="{display:tf}"  >
      <div align="center" style="margin-top: 30px">
        <el-form-item>
          <el-input
            v-model="search"
            class="search_name"
            placeholder="请输入工作计划标题">
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
      <el-button type="primary" class="el-icon-plus" @click="add" v-bind:style="{display:(this.hasPerm('workplan:add')?'':'none')}">新增</el-button>
      <el-button type="primary" class="el-icon-delete" @click="dele" v-bind:style="{display:(this.hasPerm('workplan:delete')?'':'none')}">删除</el-button></el-form-item></div>
      <el-table
        :data="tableData.slice((currentPage-1)*pageSize,currentPage*pageSize)"
        border
        style="width: 100%"   ref="multipleTable" :cell-style='cellStyle':header-cell-style='rowClass' v-loading="listLoading">
        <el-table-column type="selection" width="55px"></el-table-column>
        <el-table-column
          prop="id"
          label="序号"
          width="80">
        </el-table-column>

        <el-table-column
          prop="title"
          label="标题"
          width="360">
          <template slot-scope="scope">
            <a  @click="handleEdit(scope.$index, scope.row)"
               target="_blank"
               class="buttonText" style="color: #1890ff">{{scope.row.title}}</a>
          </template>
        </el-table-column>
        <el-table-column
          prop="users[0].mechanisms[0].mechanismName"
          label="部门"
          width="180">
        </el-table-column>
        <el-table-column
          prop="createname"
          label="创建者" width="140">
        </el-table-column>
        <el-table-column
          prop="createtime"
          label="发布时间">
        </el-table-column>
        <el-table-column
          prop="staus"
          label="状态"  :formatter="cstatus">
        </el-table-column>
        <el-table-column prop="auditresult" label="审核结果"></el-table-column>
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
      <div v-bind:style="{display:ad}" style="background-color: lightgray;width: 100%;height: 700px" :title="dialogTitle">
        <el-main>      <el-form :inline="true" :model="userInfo" class="demo-form-inline" label-width="180px" :rules="rules" ref="ruleForm">
          <div style="background-color: white;width: 100%;height: 65px;position:fixed; top:50px; left:-1px;z-index:2 ;" >
            <br/>
            <div align="right" >

              <el-button type="primary"  @click="tjshmethod('ruleForm')" v-bind:style="{display:tjsh}">提交审核</el-button>
              <el-button type="primary"  @click="gxmethod()" v-bind:style="{display:gx}">更新</el-button>
              <el-button type="primary"   v-bind:style="{display:bc}"  @click="submitUser('ruleForm')">保存</el-button>
              <el-button type="primary"  @click="tgmethod('通过')" v-bind:style="{display:tg}">通过</el-button>
              <el-button type="primary"  @click="tgmethod('不通过')"  v-bind:style="{display:btg}">不通过</el-button>
              <el-button type="primary" class="el-icon-back" @click="deselect('ruleForm')">返回</el-button>
            </div></div>
          <br/>
          <div style="background-color: white;margin-top: 7px;z-index:3;">
          <el-input v-model="userInfo.id" placeholder="编号" type="hidden"></el-input>

          <el-form-item label="工作计划标题" prop="title">

                <el-input style="width: 400px"  v-model="userInfo.title" placeholder="请输入工作标题" width="220px"  v-bind:disabled='bt'></el-input>
          </el-form-item><br/>
          <el-form-item label="所属部门" v-if="false">
            <el-input style="width: 400px" v-model="userInfo.deptName" placeholder="所属部门"  auto-complete="off"
                      @click.native="changeSelectTree()"
                      @blur="hideParentClick">
            </el-input>
            <el-tree id="floatTree"
                     v-show="isShowSelect"
                     empty-text="暂无数据"
                     :highlight-current = true
                     :default-expand-all = false
                     :expand-on-click-node="true"
                     :data="data"
                     :filter-node-method="filterNode"
                     @node-click="selectClassfy"
                     class="width-220 selectTree-input objectTree"
                     ref="selectTree">
            </el-tree>
          </el-form-item>
          <el-form-item label="工作计划内容" prop="content">
            <el-input v-model="userInfo.content" placeholder="请输入工作计划内容" type="textarea"  style="width: 800px" :rows="16"  v-bind:disabled='nr'></el-input>
          </el-form-item>
          <el-form-item label="状态" v-if="false">
            <el-input v-model="userInfo.staus" placeholder="状态"   disabled="disabled"  style="width: 400px" ></el-input>
          </el-form-item><br/>
          <el-form-item label="创建者姓名">
            <el-input v-model="userInfo.createname" placeholder="创建者姓名" disabled="disabled"  style="width: 400px" ></el-input>
          </el-form-item><br/>
          <el-form-item label="创建时间">
            <el-date-picker v-model="userInfo.createtime" placeholder="创建时间" type="datetime"  style="width: 400px"  disabled="disabled"></el-date-picker>
          </el-form-item>
          </div>
        </el-form></el-main>
      </div>
  </div>
</template>

<script>
  import { add, list,del,findbytitle,subaudit,updatecontent,passaudit } from '@/api/duty/plan'
  import { mapGetters } from 'vuex'
  import qs from 'qs'
  export default {  computed: {
      ...mapGetters([
        'nickname',
        'userId', 'role','mid'
      ])
    },
  created() {//创建时调用初始化页面的方法
    this.initList()
    //console.log('mid',this.mid)
  },
    methods:{//设置表格内容居中
      cellStyle({row, column, rowIndex, columnIndex}){
             return 'text-align:center';
            },
      rowClass({row, rowIndex}){//设置表头居中
            return 'text-align:center';
             },
    //重置
      onrest(){
        this.search=''
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
      //更新数据
      gxmethod(){
        let postData = qs.stringify({
          id:this.userInfo.id,
          title:this.userInfo.title,
          content:this.userInfo.content
        });
        updatecontent(postData).then((responese)=>{
          this.ad='none'//默认新增页面隐藏
          this.tf=''//表格页面显示
          this.initList()
          this.$notify({
            title: '成功',
            message: responese.message,
            type: 'success',
            duration: 2000
          })
        })
          console.log('gx'+this.userInfo.id)
      },
      //提交审核
      tjshmethod(formName){
        console.log('dialogTitle'+this.dialogTitle)
        if(this.dialogTitle=='新增'){
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
                createname: this.userInfo.createname,
                createtime:this.userInfo.createtime,
                createid:this.userId
              });
              add(postData).then((response) => {
                this.initList();
              let postData = qs.stringify({
                id:response.id
              });
              console.log('tjsh'+response.id)
              subaudit(postData).then((response)=>{
                this.ad='none'//默认新增页面隐藏
              this.tf=''//表格页面显示
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
          //编辑审核
          let postData = qs.stringify({
            id:this.userInfo.id
          });
          console.log('tjsh'+this.userInfo.id)
          subaudit(postData).then((response)=>{
            this.ad='none'//默认新增页面隐藏
            this.tf=''//表格页面显示
            this.initList()
            this.$notify({
              title: '成功',
              message: '提交成功',
              type: 'success',
              duration: 2000
            })
          })
        }

      },
      //通过审核
      tgmethod(val){
        let postData = qs.stringify({
          id:this.userInfo.id,
          auditresult:val
        });
        console.log('tg'+this.userInfo.id)
        passaudit(postData).then((response)=>{
          this.ad='none'//默认新增页面隐藏
          this.tf=''//表格页面显示
          this.initList()
        })
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
          console.debug(response.data)
          this.tableData = response.list
          this.total = response.list.length
          this.listLoading=false
        })
      },
      // 进入编辑页面
      handleEdit(index, row) {
        this.dialogTitle='编辑'
        this.userInfo = row;
        console.log('编辑status'+row.staus)
        if(row.staus==0){
          this.nr=false
          this.bt=false
          this.bc='none'//保存按钮
            this.tg='none'//通过按钮不显示
            this.btg='none'//不通过按钮不显示
          if(this.hasPerm('workplan:update')) {
            this.gx = ''//更新按钮显示
            this.tjsh = ''//提交审核按钮显示
          }
          else{
            this.gx = 'none'//更新按钮显示
            this.tjsh = 'none'//提交审核按钮显示
          }
        }
        else if(row.staus==1){
          this.nr='disabled'//内容禁用
          this.bt='disabled'//标题禁用

          this.bc='none'//保存按钮
            if(this.hasPerm('workplan:audit')){
            this.tg=''//通过按钮显示
            this.btg=''//不通过按钮显示
            }
            this.gx='none'//更新按钮不显示
            this.tjsh='none'//提交审核按钮不显示
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
        this.ad=''//新增页面显示
        this.tf='none'//表格页面隐藏
        this.rowIndex = index;
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
        console.log(postData+'postdate--------')
        this.listLoading = true
        findbytitle(postData).then((response) =>{
          this.currentPage = 1
          this.tableData = response.list
          console.debug(this.tableData)
          this.total=response.list.length
          this.listLoading = false
          this.ad='none'//默认新增页面隐藏
          this.tf=''//表格页面显示
        })
      },
      //删除
      dele(){
        var data = this.$refs.multipleTable.selection;
        if(JSON.stringify(data)=='[]'){
          this.$notify({
            title: '温馨提示',
            message: '请选择一行进行删除',
            type: 'success',
            duration: 2000
          })
        }
        else {
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
        }}
      },
      selectClassfy(data) {
        this.userInfo.deptName=data.label;
        this.isShowSelect=false;
      },
      changeSelectTree(){
        this.isShowSelect=true;
      },
      hideParentClick(e) {
        var isOther = e.relatedTarget == null || e.relatedTarget.closest("div.el-tree")
          == null || e.relatedTarget.closest("div.el-tree").id != "floatTree";

        if (isOther) {
          this.isShowSelect = false;
        } else {
          e.target.focus();
        }
      },
      // 选择器的树节点
      filterNode(value, data) {
        if (!value) return true;
        return data.label.indexOf(value) !== -1;
      },
      //分页
      handleSizeChange(size) {
        this.pageSize = size;
        console.log(this.pageSize)
      },
      handleCurrentChange(currentPage) {
        this.currentPage = currentPage;
        console.log(this.currentPage)  //点击第几页
      },
      //新增页面显示，初始化默认值
      add() {
        //新增时初始化隐藏和显示的按钮
        this.dialogTitle='新增'
        this.nr=false
        this.bt=false
        this.bc='',//保存按钮显示
           this.tg='none',//通过按钮隐藏
          this.btg='none',//不通过按钮隐藏
          this.gx='none',//更新按钮隐藏
        this.tjsh=''//提交审核按钮显示
        this.userInfo={title:''};//清空数据
        //新增时初始化默认值
        this.userInfo.title='履行全面从严治党主体责任年度工作计划和措施'
        this.userInfo.createname =this.nickname
        /* 动态赋值实时设置当前时间*/
        this.$set(this.userInfo,'createtime',new Date())
        this.ad=''//新增页面出现
        this.tf='none'//表格页面隐藏
      },
      // 新增提交
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
              createname: this.userInfo.createname,
              createtime:this.userInfo.createtime,
              createid:this.userId
            });
            add(postData).then((response) => {
              this.ad='none'//新增页面出现
            this.tf=''//表格页面隐藏
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
      //返回
      deselect(formName){
        this.ad='none'//默认新增页面隐藏
        this.tf=''//表格页面显示
          ,this.$refs[formName].resetFields()
        this.initList()
      }
    },
    data() {
      return {rules: {
          title: [
            {  required: true, message: '请输入工作标题', trigger: 'blur' }
          ],content: [
            {  required: true, message: '请输入工作内容', trigger: 'blur' }
          ]

        },
        listLoading:true,
        dialogTitle:'新增',//辨别新增还是编辑
        ad:'none',//默认新增页面隐藏
        tf:'',//表格页面显示
        nr:false,
        bt:false,
        bc:'',//保存按钮
        tg:'none',//通过按钮
        btg:'none',//不通过按钮
        gx:'none',//更新按钮
        tjsh:'',//控制提交审核按钮
        timer:'',
        iconFormVisible: false,
        userInfo: {},
        rowIndex: null,
        search:'',
        currentPage4: 1,
        pageSize:10,
        total:0,
        currentPage:1,
        data: [{
          label: 'xx市烟草专卖局',
          children: [{
            label: '辰溪县局',
            children: [{
              label: '办公室',
              children:[
              ]
            },
              {
                label: '客服部',
                children:[
                ]
              },
              {
                label: '综合室',
                children:[
                ]
              },
              {
                label: '内管组',
                children:[
                ]
              }
            ]
          },
            {
              label:'中方县局',
              children:[ {
                label: '基建办',
                children:[
                ]
              },
                {
                  label: '综合管理部',
                  children:[
                  ]
                }]
            }, {
              label:'靖江市局',
              children:[ {
                label: '纪检组',
                children:[
                ]
              },
                {
                  label: '安全保卫科',
                  children:[
                  ]
                }]
            }]
          }],
        isShowSelect:false,
      tableData: []
      }
    },
  }
</script>

