<template>
  <div>
    <el-form :inline="true" class="demo-form-inline">
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
        <el-button type="primary" class="el-icon-plus" @click="add"  v-bind:style="{display:(role.includes('单位/部门负责人')?'':'none')}">新增</el-button>
        <el-button type="primary" class="el-icon-delete" @click="dele"  v-bind:style="{display:(role.includes('单位/部门负责人')?'':'none')}">删除</el-button></el-form-item></div>
      <el-table
        :data="tableData.slice((currentPage-1)*pageSize,currentPage*pageSize)"
        border
        style="width: 100%"  ref="multipleTable">
        <el-table-column type="selection" width="55px"></el-table-column>
        <el-table-column
          prop="id"
          label="序号"
          width="180">
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
      </div>

      <!--弹窗-->
      <el-dialog :title="dialogTitle" width="50%" :visible.sync="iconFormVisible">
        <el-form :inline="true" :model="userInfo" class="demo-form-inline" label-width="120px">
          <el-input v-model="userInfo.id" placeholder="编号" type="hidden"></el-input>
          <el-form-item label="主体责任标题">

            <el-input v-model="userInfo.title" placeholder="请输入主体责任标题" v-bind:disabled='bt'></el-input>
          </el-form-item><br/>
          <el-form-item label="主体责任内容">
            <quill-editor id="editer"  v-bind:disabled='nr'  ref="text" v-model="userInfo.content" class="myQuillEditor" :options="editorOption" style="width: 400px;" />

          </el-form-item><br/>
          <el-form-item label="创建者姓名">
            <el-input v-model="userInfo.createname" placeholder="创建者姓名" disabled="disabled"  style="width: 300px" ></el-input>
          </el-form-item><br/>
          <el-form-item label="创建时间">
            <el-date-picker v-model="userInfo.createtime" placeholder="创建时间" type="datetime"  style="width: 300px"  disabled="disabled"></el-date-picker>
          </el-form-item><br/>
          <el-form-item label="状态" v-if="false">
            <el-input v-model="userInfo.staus"  placeholder="状态"  disabled="disabled"  style="width: 300px" ></el-input>
          </el-form-item><br/>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary"  @click="tjshmethod()" v-bind:style="{display:tjsh}">提交审核</el-button>
          <el-button type="primary"  @click="gxmethod()" v-bind:style="{display:gx}">更新</el-button>
          <el-button type="primary"   v-bind:style="{display:bc}"  @click="submitUser()">保存</el-button>
          <el-button type="primary"  @click="tgmethod()" v-bind:style="{display:tg}">通过</el-button>
          <el-button type="primary"  @click="tgmethod()"v-bind:style="{display:btg}">不通过</el-button>
          <el-button type="primary" class="el-icon-back" @click="deselect()">返回</el-button>
        </div>
      </el-dialog>
    </el-form>
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
        'userId', 'role'
      ])
    },  created() {
      this.initList()
    },
    methods:{
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
      gxmethod(){
        let postData = qs.stringify({
          id:this.userInfo.id,
          title:this.userInfo.title,
          content:this.userInfo.content
        });
        updatecontent(postData).then((responese)=>{
          this.iconFormVisible = false;
          this.initList()
        })
        console.log('gx'+this.userInfo.id)
      },
      tjshmethod(){
        let postData = qs.stringify({
          id:this.userInfo.id
        });
        console.log('tjsh'+this.userInfo.id)
        subaudit(postData).then((response)=>{
          this.iconFormVisible = false;
          this.initList()
        })
      },
      tgmethod(){
        let postData = qs.stringify({
          id:this.userInfo.id
        });
        console.log('tg'+this.userInfo.id)
        passaudit(postData).then((response)=>{
          this.iconFormVisible = false;
          this.initList()
        })
      },
      //初始化页面
      initList() {
        list(this.listQuery).then(response =>{
          console.debug(response)
          this.tableData = response.list
          this.total = response.list.length
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
        this.userInfo.staus='创建'
       // console.debug('id='+this.id)
        /* 动态赋值实时设置当前时间*/
        this.$set(this.userInfo,'createtime',new Date())
        //this.timer= setInterval(this.time, 1000 );

        this.iconFormVisible = true;
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
          if(this.role.includes('单位/部门负责人')) {
            this.gx=''//更新按钮显示
            this.tjsh=''//提交审核按钮显示
             }
        }
        else if(row.staus==1){

          this.nr='disabled'
          this.bt='disabled'
          this.bc='none'//保存按钮
            if(this.role.includes('纪检监察科科长')){
            this.tg='',//通过按钮显示
            this.btg=''//不通过按钮显示
               }
            this.gx='none',//更新按钮不显示
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
        this.iconFormVisible = true;
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
      },
      //按标题查询
      onSearch() {
        let postData = qs.stringify({
          title:this.search
        });
        this.listLoading = true
        findbytitle(postData).then((response) =>{
          this.currentPage = 1
          this.tableData = response.list
          console.debug(this.tableData)
          this.total=response.list.length
          this.listLoading=false
        })
      },//弹窗取消
      deselect(){
        this.iconFormVisible = false
        this.initList()
        //  clearInterval(this.timer)
      },
      // 弹窗确定
      submitUser() {
        let postData = qs.stringify({
          title: this.userInfo.title,
          content: this.userInfo.content,
          staus:0,
          createtime:this.userInfo.createtime,
          createid:this.userId
        });
        if (this.dialogTitle === '增加') {
          //新增
          add(postData).then((response) => {
            this.iconFormVisible = false;
            clearInterval(this.timer)
            this.initList();
            this.$notify({
              title: '成功',
              message: response.message,
              type: 'success',
              duration: 2000
            })
          })

        }

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
        nr:'disabled',
      bt:'disabled',
      bc:'none',//保存按钮
        tg:'none',//通过按钮不显示
      btg:'none',//不通过按钮不显示
       gx:'none',//更新按钮不显示
        tjsh:'none',//提交审核按钮显示
        editorOption: {},
      iconFormVisible: false,
          userInfo: {},
          dialogTitle: '增加',
          rowIndex: null,
          search:'',
          currentPage4: 1,
          pageSize:10,
          total:0,
          currentPage:1,
        tableData: [/*{
          userId:'1',
          userDate: '烟草行业“不忘初心，牢记使命”主题教育工作会议在北京召开',
          userName: '名非烟草',
          userAddress: '2010/2/2'
        }, {
          userId:'2',
          userDate: '对十二届全国人大五次会议第1211号建议的答复',
          userName: '烟草',
          userAddress: '2010/4/2'
        }, {
          userId:'3',
          userDate: '福建中烟召开2019年第六次党组中心理论学习',
          userName: '烟草',
          userAddress: '2010/2/22'
        }, {
          userId:'4',
          userDate: '对十二届全国人大五次会议第1211号建议的答复',
          userName: '烟草',
          userAddress: '2010/5/2'
        }*/]
      }
    }
  }
</script>

