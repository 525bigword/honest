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
       <!-- <el-button type="primary" class="el-icon-plus" @click="add">新增</el-button>
        <el-button type="primary" class="el-icon-delete" @click="del">删除</el-button>--></el-form-item></div>
      <el-table
        :data="tableData.slice((currentPage-1)*pageSize,currentPage*pageSize)"
        border
        style="width: 100%" ref="multipleTable">
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
        <el-table-column
          prop="lsynopsis"
          label="信访内容摘要">
        </el-table-column>
        <el-table-column prop="lcreateName" label="创建人姓名" v-if="false" >

        </el-table-column>

        <el-table-column prop="lCreateTime" label="创建时间" v-if="false"></el-table-column>
        <el-table-column prop="lstatus" label="状态" :formatter="cstatus">

        </el-table-column>
        <el-table-column label="操作" fixed="right" align="center"  prop="lstatus" >
          <template slot-scope="scope">
            <el-button v-if="scope.row.lstatus==1" v-bind:style="{display:(role.includes('纪检监察员')?'':'none')}" type="primary" size="small" @click="handleEdit(scope.$index, scope.row)">审核</el-button>
            <el-button type="primary" size="small" v-if="scope.row.lStatus==2||scope.row.lStatus==3" v-bind:style="{display:(role.includes('纪检监察科科长')||role.includes('单位/部门负责人')?'':'none')}" @click="bj(scope.$index, scope.row)">编辑</el-button>
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
          <el-button type="primary" class="el-icon-edit" align="right" @click="submitUser"  v-bind:style="{display:tj}">提交</el-button>
          <el-button type="primary" class="el-icon-edit" @click="turn" v-if="role.includes('纪检监察科科长')"  v-bind:style="{display:zb}">转办</el-button>
          <el-button type="primary" class="el-icon-edit" @click="shbc"  v-bind:style="{display:bc}">保存</el-button>
          <el-button type="primary" class="el-icon-edit" @click="bjbcmethod"  v-bind:style="{display:bjbc}">保存</el-button>
          <el-button type="primary" class="el-icon-back" @click="back">返回</el-button></el-form-item></div></div>
        <br/>
        <div style="background-color: white">
        <el-input v-model="userInfo.lid" placeholder="编号" type="hidden" ></el-input>
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
          <el-input v-model="userInfo.lComplainantName" placeholder="来访人姓名" style="width: 300px" disabled="disabled"></el-input>
        </el-form-item>
          <el-form-item label="被反映人姓名">
            <el-input v-model="userInfo.lPersonBeReported" v-bind:disabled='bename' placeholder="被反映人姓名" style="width: 300px" ></el-input>
          </el-form-item>
          <br/>
          <el-form-item label="来访人部门/单位">
            <el-input v-model="userInfo.lDeptId" placeholder="来访人部门/单位" style="width: 300px" disabled="disabled"></el-input>
          </el-form-item>
          <el-form-item label="被反映人部门/单位">
            <el-input v-model="userInfo.lPbrDeptId"  v-bind:disabled='beunit'  placeholder="被反映人部门/单位" style="width: 300px" ></el-input>
          </el-form-item>
          <br/>
          <el-form-item label="来访人岗位/职务">
            <el-input v-model="userInfo.lPostId" placeholder="来访人岗位/职务" style="width: 300px" disabled="disabled"></el-input>
          </el-form-item>
          <el-form-item label="被反映人岗位/职务">
            <el-input v-model="userInfo.lPbrPostId" v-bind:disabled='beduty' placeholder="被反映人岗位/职务" style="width: 300px" ></el-input>
          </el-form-item>
          <br/>
          <el-form-item label="原始信访内容">
            <el-input type="textarea" v-model="userInfo.lContent" placeholder="原始信访内容" style="width: 840px;" v-bind:disabled="ysnr"></el-input>
          </el-form-item>
          <br/>
          <el-form-item label="信访内容摘要"  v-bind:style="{display:nrzy}" >
            <el-input type="textarea" v-bind:disabled="sfjc" v-model="userInfo.lSynopsis" placeholder="信访内容摘要" style="width: 840px;"  ></el-input>
          </el-form-item>
          <br/>
          <el-form-item label="监察科部门意见" v-if="role.includes('纪检监察科科长')" >
            <el-input v-model="userInfo.yj" placeholder="监察科部门意见" style="width: 300px" ></el-input>
          </el-form-item>
          <el-form-item label="意见签署时间" v-if="role.includes('纪检监察科科长')" >
            <el-date-picker v-model="userInfo.lCreateTime" placeholder="创建时间" style="width: 300px" type="datetime" disabled="disabled"></el-date-picker>
          </el-form-item><br/>
          <el-form-item label="纪检组长意见" v-if="role.includes('纪检组长')" >
            <el-input v-model="userInfo.yj" placeholder="纪检组长意见" style="width: 300px" ></el-input>
          </el-form-item>
          <el-form-item label="纪检组长签署时间" v-if="role.includes('纪检组长')" >
            <el-date-picker v-model="userInfo.lCreateTime" placeholder="纪检组长签署时间" style="width: 300px" type="datetime" disabled="disabled"></el-date-picker>
          </el-form-item><br/>
          <el-form-item label="局领导意见" v-if="role.includes('局领导')" >
            <el-input v-model="userInfo.yj" placeholder="局领导意见" style="width: 300px" ></el-input>
          </el-form-item>
          <el-form-item label="局领导签署时间" v-if="role.includes('局领导')" >
            <el-date-picker v-model="userInfo.lCreateTime" placeholder="局领导签署时间" style="width: 300px" type="datetime" disabled="disabled"></el-date-picker>
          </el-form-item><br/>
          <el-form-item label="转办部门处理结果" v-if="role.includes('单位/部门负责人')" >
            <el-input v-model="userInfo.yj" placeholder="转办部门处理结果" style="width: 300px" ></el-input>
          </el-form-item>
          <el-form-item label="转办部门处理时间"  v-if="role.includes('单位/部门负责人')" >
            <el-date-picker v-model="userInfo.lCreateTime" placeholder="转办部门处理时间" style="width: 300px" type="datetime" disabled="disabled"></el-date-picker>
          </el-form-item><br/>
          <el-form-item label="监察科自办结果"  v-if="role.includes('纪检监察员')" >
            <el-input v-model="userInfo.yj" placeholder="监察科自办结果" style="width: 300px" ></el-input>
          </el-form-item>
          <el-form-item label="监察科自办时间"  v-if="role.includes('纪检监察员')" >
            <el-date-picker v-model="userInfo.lCreateTime" placeholder="监察科自办时间" style="width: 300px" type="datetime" disabled="disabled"></el-date-picker>
          </el-form-item><br/>
          <el-form-item label="创建者"  v-if="false">
            <el-input v-model="userInfo.lCreateName" placeholder="创建者" style="width: 300px" disabled="disabled"></el-input>
          </el-form-item>
          <el-form-item label="创建时间"  v-if="false">
            <el-date-picker v-model="userInfo.lCreateTime" v-if="false" placeholder="创建时间" style="width: 300px" type="datetime" disabled="disabled"></el-date-picker>
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
  import {xfbh,list,add,del,findbyName,shbc,turndept} from '@/api/daily/letter'
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
    methods:{//判断状态给提示
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
    //转办部门
      turn(){
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
        })
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
        list(posdata).then(response =>{
          console.log(JSON.stringify(response))
          this.tableData = response.list
          this.total = response.list.length
        })
      },//新增
       add(){
        this.userInfo={};
         xfbh().then((response)=>{
           this.userInfo.letterId='XF'+response.xfbh
           console.log('XF'+response.xfbh)
           this.userInfo.lCreateName =this.nickname

          // console.debug('id='+this.id)
           /* 动态赋值实时设置当前时间*/
           this.$set(this.userInfo,'lCreateTime',new Date())
           this.tf='none';
           this.ad=''
           this.zhuanban='none'
           this.zhuanbanjy='disabled'
          this.tj='',//提交按钮
             this.zb='none'//转办按钮默认隐藏
             this.bc='none'//保存按钮默认隐藏
           this.bjbc='none'//编辑保存按钮隐藏
           this.ysnr=false//原始信访内容
           this.beunit=false//被反映人单位
             this.beduty=false//被反映人职务
             this.bename=false//被反映人姓名
         })

      },//新增提交
      submitUser(){
        let endtime = new Date(this.userInfo.lCreateTime).toJSON();
        this.userInfo.lCreateTime = new Date(+new Date(endtime) + 8 * 3600 * 1000)
          .toISOString()
          .replace(/T/g, " ")
          .replace(/\.[\d]{3}Z/, "")
        let posdata=qs.stringify({
          letterId:this.userInfo.letterId,
          userName:this.userInfo.userName,
          lComplainantName:this.userInfo.lComplainantName,
          lPersonBeReported:this.userInfo.lPersonBeReported,
          lDeptId:this.userInfo.lDeptId,
          lPostId:this.userInfo.lPostId,
          lPbrDeptId:this.userInfo.lPbrDeptId,
          lPbrPostId:this.userInfo.lPbrPostId,
          lContent:this.userInfo.lContent,
          lCreateName:this.nickname,
          lTime: this.userInfo.lCreateTime,
          lCreateTime:this.userInfo.lCreateTime,
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
      //审核
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
       // this.userInfo.lCreateName=this.nickname
      },//纪检监察员审核提交保存
      shbc(){
        let postData = qs.stringify({
          lid:this.userInfo.lid,
          lSynopsis:this.userInfo.lSynopsis
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
                    label: item.mechanismname
                  });
                }
              }
              // 通过调用resolve将子节点数据返回，通知组件数据加载完成
              resolve(nodes);
            })


          }
        },
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

