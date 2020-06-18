<template>
  <div>
    <el-form :inline="true" class="demo-form-inline">
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
      <el-button type="primary" class="el-icon-plus" @click="add">新增</el-button>
      <el-button type="primary" class="el-icon-delete" @click="dele">删除</el-button></el-form-item></div>
      <el-table
        :data="tableData.slice((currentPage-1)*pageSize,currentPage*pageSize)"
        border
        style="width: 100%"   ref="multipleTable">
        <el-table-column type="selection" width="55px"></el-table-column>
        <el-table-column
          prop="id"
          label="序号"
          width="180">
        </el-table-column>
        <el-table-column
          prop="staus"
          label="状态" v-if="false">
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
          width="200">
        </el-table-column>
        <el-table-column
          prop="createname"
          label="创建者">
        </el-table-column>
        <el-table-column
          prop="createtime"
          label="发布时间">
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
        <el-form :inline="true" :model="userInfo"  label-width="120px" width="240px">
          <el-input v-model="userInfo.id" placeholder="编号" type="hidden"></el-input>

          <el-form-item label="工作计划标题">

                <el-input style="width: 400px"  v-model="userInfo.title" placeholder="标题" width="220px" ></el-input>
          </el-form-item><br/>
          <el-form-item label="所属部门">
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
          </el-form-item><br/>
          <el-form-item label="工作计划内容">
            <el-input v-model="userInfo.content" placeholder="内容" type="textarea"  style="width: 400px" ></el-input>
          </el-form-item><br/>
          <el-form-item label="状态">
            <el-input v-model="userInfo.staus" placeholder="状态"  disabled="disabled"  style="width: 400px" ></el-input>
          </el-form-item><br/>
          <el-form-item label="创建者姓名">
            <el-input v-model="userInfo.createname" placeholder="创建者姓名" disabled="disabled"  style="width: 400px" ></el-input>
          </el-form-item><br/>
          <el-form-item label="创建时间">
            <el-date-picker v-model="userInfo.createtime" placeholder="创建时间" type="datetime"  style="width: 400px"  disabled="disabled"></el-date-picker>
          </el-form-item><br/>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="deselect()">返回</el-button>
          <el-button >提交审核</el-button>
          <el-button >更新</el-button>
          <el-button >保存</el-button>
          <el-button >通过</el-button>
          <el-button type="primary" @click="submitUser()" >确 定</el-button>
        </div>
      </el-dialog>
    </el-form>
  </div>
</template>

<script>
  import { add, list,del,findbytitle,subaudit } from '@/api/duty/plan'
  import { mapGetters } from 'vuex'
  import qs from 'qs'
  export default {  computed: {
      ...mapGetters([
        'userId',
        'nickname'
      ])
    },
  created() {
    this.initList()
  },
    methods:{
      onrest(){
        this.search=''
      },
      initList() {
        list(this.listQuery).then(response =>{
          console.log('查询返回'+JSON.stringify(response.list))
          this.tableData = response.list
          this.total = response.list.length
        })
      },
      // 编辑
      handleEdit(index, row) {
        this.dialogTitle = '编辑';
        this.userInfo = row;
        if(row.staus==0){
          this.userInfo.staus='创建'
        }
        else if(row.staus==1){
          this.userInfo.staus='待审'
        }
        else{
          this.userInfo.staus='已审核'
        }
        this.iconFormVisible = true;
        this.rowIndex = index;
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
      },
      dele(){
        var data = this.$refs.multipleTable.selection;
        console.log('data.lent'+data.length)
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
      handleSizeChange(size) {
        this.pageSize = size;
        console.log(this.pageSize)
      },
      handleCurrentChange(currentPage) {
        this.currentPage = currentPage;
        console.log(this.currentPage)  //点击第几页
      },
  /*赋值当前时间*/
      time(){
        this.$set(this.userInfo,'createtime',new Date())

       console.debug("11")
      },
      //新增
      add() {
        this.dialogTitle = '增加';
        this.userInfo={};
        this.userInfo.title='履行全面从严治党主体责任年度工作计划和措施'
        this.userInfo.staus='创建'
        this.userInfo.createname =this.nickname
        console.debug('userId='+this.userId)
        /* 动态赋值实时设置当前时间*/
        this.$set(this.userInfo,'createtime',new Date())
       //this.timer= setInterval(this.time, 1000 );

        this.iconFormVisible = true;
      },
      //弹窗取消
      deselect(){
        this.iconFormVisible = false
        clearInterval(this.timer)
      },
      // 弹窗确定
      submitUser() {
        let postData = qs.stringify({
          title: this.userInfo.title,
          content: this.userInfo.content,
          staus:this.userInfo.staus=='创建'?0:'1',
          createname: this.userInfo.createname,
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
    },
    data() {
      return {
        timer:'',
        iconFormVisible: false,
        userInfo: {},
        dialogTitle: '增加',
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
      tableData: [{
          id:'1',
          title: '履行全面从严治党主体责任年度工作计划和措施',
          deptName: '市场分部',
        createname: '系统管理员',
          createtime:'2020/6/13'
        }, {
          id:'2',
          title: '履行全面从严治党主体责任年度工作计划和措施',
          deptName: '市场分部',
        createname: '系统管理员',
          createtime:'2020/6/14'
        }, {
          id:'3',
          title: '履行全面从严治党主体责任年度工作计划和措施',
        deptName: '怀化市烟草专卖局（公司）',
          createName: '系统管理员',
          createtime:'2020/6/15'
        }, {
          id:'4',
          title: '履行全面从严治党主体责任年度工作计划和措施',
          deptName: '综合管理部',
        createname: '系统管理员',
          createtime:'2020/6/23'
        }]
      }
    },
  }
</script>

