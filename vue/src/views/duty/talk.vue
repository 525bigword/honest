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
        <el-button type="primary" class="el-icon-plus" @click="add" v-if="role.includes('单位/部门负责人')">新增</el-button>
        <el-button type="primary" class="el-icon-delete" @click="del" v-if="role.includes('单位/部门负责人')">删除</el-button></el-form-item></div>
      <el-table
        :data="tableData.slice((currentPage-1)*pageSize,currentPage*pageSize)"
        border
        style="width: 100%" ref="multipleTable">
        <el-table-column type="selection" width="55px"></el-table-column>
        <el-table-column
          prop="id"
          label="编号"
          width="180">
        </el-table-column>
        <el-table-column
          prop="type"
          label="谈话类型"
          width="180">
        </el-table-column>
        <el-table-column
          prop="punits"
          label="谈话对象单位"
          width="180"   v-if="false">
        </el-table-column>
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
          width="180">
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
          label="状态"  :formatter="cstatus">
        </el-table-column>
        <el-table-column label="操作" fixed="right" align="center">
          <template slot-scope="scope">
            <el-button type="primary" size="small" @click="handleEdit(scope.$index, scope.row)" v-if="scope.row.staus==0&&role.includes('单位/部门负责人')">编辑</el-button>
            <el-button type="primary" size="small" v-if="scope.row.staus==0 &&role.includes('单位/部门负责人')" @click="tjsh(scope.$index, scope.row)">提交审核</el-button>
            <el-button type="primary" size="small" v-if="scope.row.staus==1&&role.includes('纪检监察科科长')" @click="sh(scope.$index, scope.row)">审核</el-button>
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
      <div v-bind:style="{display:ad}" style="background-color: lightgray;width: 100%;height: 700px" >
        <el-main>      <el-form :inline="true" :model="userInfo" class="demo-form-inline" label-width="180px">
          <div style="background-color: white;width: 100%;height: 65px" >
            <br/>
            <div align="right" ><el-form-item >
              <el-button type="primary" class="el-icon-edit"   v-bind:style="{display:bc}"  @click="submitUser()"  >提交</el-button>
              <el-button type="primary" class="el-icon-edit" @click="gxmethod()" v-bind:style="{display:gx}">更新</el-button>
              <el-button type="primary" class="el-icon-back" @click="back">返回</el-button></el-form-item></div></div>
          <br/>
          <div style="background-color: white">
          <el-input v-model="userInfo.id" placeholder="编号" type="hidden"></el-input>

          <el-form-item label="谈话对象单位" >
            <el-cascader ref='cascaderUnit' :show-all-levels="false"
                         :placeholder="defaUnit"
                         :props="props"
                         :options="options_cascader"
                         :expandTrigger="'hover'"
                                clearable v-model="userInfo.deptname" @change="handleItemChange"  style="width: 400px"></el-cascader>
          </el-form-item>
          <el-form-item label="谈话对象姓名">
            <el-select v-model="userInfo.pid" placeholder="谈话对象姓名" style="width: 400px">
              <el-option
                v-for="item in options"
                :key="item.sid"
                :label="item.name"
                :value="item.sid">
              </el-option>
            </el-select>
          </el-form-item><br/>
          <el-form-item label="记录人">
            <el-select v-model="userInfo.personid" placeholder="记录人" style="width: 400px">
              <el-option
                v-for="item in options"
                :key="item.sid"
                :label="item.name"
                :value="item.sid">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="谈话时间">
            <el-date-picker v-model="userInfo.time" placeholder="谈话时间" type="datetime"  style="width: 400px" ></el-date-picker>
          </el-form-item><br/>
          <el-form-item label="谈话对象政治面貌">
            <el-select v-model="userInfo.zzmm" placeholder="谈话对象政治面貌" style="width: 400px">
              <el-option label="党员" value="党员"></el-option>
              <el-option label="团员" value="团员"></el-option>
              <el-option label="群众" value="群众"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="谈话对象职务">
            <el-input v-model="userInfo.duty" placeholder="谈话对象职务" style="width: 400px"></el-input>
          </el-form-item><br/>
          <el-form-item label="谈话类型">
            <el-select v-model="userInfo.type" placeholder="谈话类型" style="width: 400px">
              <el-option label="例行廉政谈话" value="例行廉政谈话"></el-option>
              <el-option label="任前廉政谈话" value="任前廉政谈话"></el-option>
              <el-option label="提醒谈话" value="提醒谈话"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="谈话地点">
            <el-input v-model="userInfo.site" placeholder="谈话地点" style="width: 400px"></el-input>
          </el-form-item>
            <br/>
            <el-form-item label="谈话提纲">
          <el-input v-model="userInfo.syllabus" placeholder="谈话提纲" style="width: 1000px"></el-input>
        </el-form-item>
            <br/>
            <el-form-item label="谈话内容">
              <el-input v-model="userInfo.content" placeholder="谈话内容" style="width: 1000px"></el-input>
            </el-form-item><br/>
          <el-form-item label="创建人">
            <el-input v-model="userInfo.createname" placeholder="创建人" disabled="disabled" style="width: 400px"></el-input>
          </el-form-item>
          <el-form-item label="创建时间">
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
        'userId', 'role'
      ])
    },
    created() {
      this.initList()
    },
    methods:{
      submitUser(){
        console.log('puni'+this.$refs.cascaderUnit.getCheckedNodes()[0].value)
        var puni='';
        puni=this.$refs.cascaderUnit.getCheckedNodes()[0].value;

        let postData = qs.stringify({
         punit:puni,//谈话对象单位
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
        console.log("postdata"+postData)
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
      },
      handleItemChange(value){//点击选择时初始化谈话对象和记录人
        var checkedNodes = this.$refs['cascaderUnit'].getCheckedNodes()//选择的值
      /*
      console.log('zz'+ value.join(',')); //全路径value值
       console.log('cc'+this.$refs.cascaderUnit.getCheckedNodes()[0].pathLabels); //全路径label值*/

       if(checkedNodes[0]!=undefined){
         console.log('checkedNodes label'+checkedNodes[0].label)
         let postdata=qs.stringify({
           id:checkedNodes[0].value
         })
         initpersons(postdata).then((response)=>{

           this.options = response.list

           console.log('记录人'+JSON.stringify(response))

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
          return '待审';
        }
        else{
          return '已审核'
        }
      },
      back(){this.ad='none',//隐藏窗
        this.tf='' },
      gxmethod(){//更新
        var puni='';
        if(this.$refs.cascaderUnit.getCheckedNodes()[0]!=undefined){
        puni=this.$refs.cascaderUnit.getCheckedNodes()[0].value;}
        else {
          puni=this.userInfo.punit
        }
        let postData = qs.stringify({
          id:this.userInfo.id,
          punit:puni,//谈话对象单位
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
        console.log('gx'+this.userInfo.id)
      },//提交审核
      tjsh(index,row){
      console.log('row'+JSON.stringify(row.id))
        let postData = qs.stringify({
          id:row.id
        });
        this.$confirm(`确定提交审核?`, '提示', {
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
            message: '已取消提交审核'
          });
        })
      },//通过审核
      sh(index,row){
        this.$confirm(`请选择审核通过或撤回！`, '提示', {
          confirmButtonText: '审核通过',
          cancelButtonText: '撤回',
         distinguishCancelAndClose:true,
          type: 'warning',
        }).then(() => {
          let postData = qs.stringify({
            id:row.id,
            status:2,
          });
          passaudit(postData).then((response)=>{
            this.$notify({
              type: 'success',
              message: '审核成功'
            });
            this.initList()
          })

        }).catch((action) => {
          let postData = qs.stringify({
            id:row.id,
            status:0,
          });
          if(action==='cancel'){
          passaudit(postData).then((response)=> {
            this.$notify({
              type: action==='cancel'?'success':'info',
              message: action === 'cancel' ? '撤回成功' : '已取消审核'
            });
            this.initList()
          })}

        })

      }
,
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
      //删除
      del(){ var data = this.$refs.multipleTable.selection;
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
        }},   handleEdit(index, row) {
        this.tf='none';//表格父页面隐藏
        this.ad=''//编辑/审核页面出来,
        this.bc='none'
        this.gx=''
        this.userInfo = row;
       this.userInfo.deptname=[],
         this.userInfo.deptname.push(row.punits)
        this.defaUnit=row.users[0].mechanisms[0].mechanismName
        this.userInfo.zzmm=row.users[0].politicalAppearance
        this.userInfo.duty=row.users[0].posts[0].pname
        this.userInfo.name=[]
        this.userInfo.name.push(row.pid)
        this.userInfo.personname=[],
          this.userInfo.personname.push(row.personid)
        console.log('测试状态'+row.staus)

      },
      //初始化页面
      initList() {
        list(this.listQuery).then(response =>{
          console.log(response)
          this.tableData = response.list
          this.total = response.list.length
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
      return {
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
        gx:'none',
        bc:'',
        tf:false,
        ad:'none',//隐藏窗
        iconFormVisible: false,
        userInfo: {},
        dialogTitle: '增加',
        rowIndex: null,
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

