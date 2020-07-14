<template>
  <div>
    <el-form :inline="true" class="demo-form-inline">
      <div align="center" style="margin-top: 30px">
        <el-form-item label="父级部门">
          <el-cascader
            :placeholder="placeholder"
            v-model="defaultvalue"
            :props="props"
            @change="Change"
            :show-all-levels="false"
            :options="bm"
          ></el-cascader>
        </el-form-item>
        <el-form-item label="岗位">
          <el-select style="width: 80%;" v-model="search.pInfomationId" placeholder="岗位">
            <el-option
              v-for="(post) in postList2"
              :key="post.pid"
              :label="post.pname"
              :value="post.pid"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="风险等级" :line="true">
          <el-select style="width: 80%;" v-model="search.pGrade" placeholder="请选择">
            <el-option label="一级风险" value="一级风险"></el-option>
            <el-option label="二级风险" value="二级风险"></el-option>
            <el-option label="三级风险" value="三级风险"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button
            type="primary"
            @click="onSearch()"
            class="el-icon-search">查询
          </el-button>
          <el-button
            type="primary"
            class="el-icon-refresh" plain @click="onrest">重置
          </el-button>
        </el-form-item>
      </div>
      <div>
        <el-form-item>
          <el-button type="primary" class="el-icon-plus" @click="add">新增</el-button>
          <el-button type="primary" class="el-icon-delete" @click="dele">删除</el-button>
        </el-form-item>
      </div>
      <el-table
        :data="tableData.slice((currentPage-1)*pageSize,currentPage*pageSize)"
        border
        style="width: 100%" ref="multipleTable">
        <el-table-column type="selection" width="50px"></el-table-column>
        <el-table-column
          prop="pid"
          label="序号id">
        </el-table-column>
        <!--<el-table-column
          prop="pstatus"
          label="状态"
          :formatter="stateFormat">
        </el-table-column>-->
        <el-table-column
          prop="priskId"
          label="岗位风险编号">
        </el-table-column>
        <el-table-column
          prop="pdeptId"
          label="部门信息"
          :formatter="deptFormat">
        </el-table-column>
        <el-table-column
          prop="pinfomationId"
          label="岗位"
          :formatter="postFormat">
        </el-table-column>
        <el-table-column
          prop="pyear"
          label="执行年份"
          :formatter="dateFormat">
        </el-table-column>
        <el-table-column
          prop="pproject"
          label="风险项目名称">
        </el-table-column>
        <el-table-column
          prop="priskPointDescription"
          label="廉政风险点描述">
        </el-table-column>
        <el-table-column
          prop="pprobableLValue"
          label="风险发生可能性L值">
        </el-table-column>
        <!-- <el-table-column
           prop="pcvalue"
           label="风险产生后果的严重性C值">
         </el-table-column>-->
        <!--<el-table-column
          prop="pdvalue"
          label="廉政风险值D值">
        </el-table-column>-->
        <el-table-column
          prop="pgrade"
          label="风险等级">
        </el-table-column>
        <el-table-column
          prop="pmeasures"
          label="预防和控制措施">
        </el-table-column>
        <el-table-column
          prop="pcreateName"
          label="创建人姓名">
        </el-table-column>
        <el-table-column
          prop="pcreateTime"
          label="创建时间">
        </el-table-column>
        <el-table-column
          label="操作">
          <template slot-scope="scope">
            <el-button
              type="primary"
              @click="handleEdit(scope.$index, scope.row)">修改
            </el-button>
          </template>

        </el-table-column>
      </el-table>
      <div class="block" align="center">
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="currentPage4"
          :page-sizes="[5, 10, 20, 50,100]"
          layout="total, sizes, prev, pager, next, jumper"
          :page-size="pageSize"
          :total="total">
        </el-pagination>
      </div>

      <!--弹窗-->
      <el-dialog :title="dialogTitle" width="65%" :visible.sync="iconFormVisible">
        <el-form :inline="true" ref="dataForm" :rules="rules" :model="userInfo" label-width="140px" width="100%">
          <el-input v-model="userInfo.pid" placeholder="编号" type="hidden"></el-input>
          <el-row>
          <el-col style="width:43%">
           <el-form-item label="父级部门" prop="defaultvalue2">
            <el-cascader style="width:100%;"
              :placeholder="placeholder2"
              v-model="defaultvalue2"
              :props="props2"
              @change="Change2"
              :show-all-levels="false"
              :options="bm"
            ></el-cascader>
          </el-form-item>
          </el-col>
          <el-col style="width:43%">
            <el-form-item  label="岗位" prop="pinfomationId">
            <el-select @change="selectSystemChanged" style="width:100%"  v-model="userInfo.pinfomationId"
                       placeholder="岗位">
              <el-option
                v-for="(post) in postList3"
                :key="post.pid"
                :label="post.pname"
                :value="post.pid"
              ></el-option>
            </el-select>
          </el-form-item>
          </el-col>
        </el-row>
          
          <el-form-item label="风险项目名称" prop="pproject">
            <el-input style="width: 560px" v-model="userInfo.pproject" placeholder="请输入风险项目名称" ></el-input>
          </el-form-item>
          <el-form-item label="廉政风险点描述" prop="priskPointDescription">
            <el-input v-model="userInfo.priskPointDescription" :rows="5" :cols="73" placeholder="请输入廉政风险点描述" type="textarea"
                      ></el-input>
          </el-form-item>
              <el-form-item label-width="40%" style="width:43%;margin-left:-4px" label="风险发生可能性L值" prop="pprobableLValue">
              <el-select  style="width:100%" v-model="userInfo.pprobableLValue" placeholder="风险发生可能性L值">
                <el-option label="可能性极小" value="可能性极小"></el-option>
                <el-option label="可能性较小" value="可能性较小"></el-option>
                <el-option label="可能性较大" value="可能性较大"></el-option>
                <el-option label="可能性极大" value="可能性极大"></el-option>
              </el-select>
              </el-form-item>
            <!-- <el-col   style="margin-left:10px;width:132px" align="right">风险产生严重性C值</el-col> -->
           <!--  <el-col style="width:200px" > -->
              <el-form-item label-width="43%" style="width:43%;margin-left:-22px" label="风险产生严重性C值" prop="pcvalue">
              <el-select style="width:100%" v-model="userInfo.pcvalue" placeholder="风险产生严重性C值">
                <el-option label="极小" value="极小"></el-option>
                <el-option label="较小" value="较小"></el-option>
                <el-option label="较大" value="较大"></el-option>
                <el-option label="极大" value="极大"></el-option>
              </el-select>
              </el-form-item>
          <!-- </el-form-item> -->
          <el-form-item label="预防和控制措施" prop="pmeasures">
            <el-input v-model="userInfo.pmeasures" :rows="5" :cols="73" placeholder="请输入预防和控制措施" type="textarea"
                      ></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" style="margin-top:-27px" class="dialog-footer">
          <el-button @click="deselect()">取消</el-button>
          <el-button style="margin-right:9%" type="primary" @click="submitUser()">确 定</el-button>
        </div>
      </el-dialog>
    </el-form>
  </div>
</template>

<script>
  import {
    add,
    del,
    findBy,
    getAllMechanism,
    getAllPost,
    list,
    update,
    getAllMechanismByParent, getSysPostByMid
  } from '@/api/risk/postRiskCombing'
  import {mapGetters} from 'vuex'
  import qs from 'qs'
  import moment from 'moment'

  export default {
    computed: {
      ...mapGetters([
        'userId',
        'nickname'
      ])
    },
    created() {
      this.initList();
      this.getSysmechanismAll();
      this.getAllPost();
      this.getAllMechanism();
    },
    methods: {
      getSysmechanismAll(val = 0) {
        this.api({
          url: "sysmechanism/get",
          method: "get"
        }).then(res => {
          this.bm = [];
          res.filter(item => {
            this.bm.push(item);
          });
        });
      },
      getPostByMid(list) {
        for (let mechanism of list) {
          let postData = qs.stringify({
            mid: mechanism.mid
          });
          getSysPostByMid(postData).then((response) => {
            for (let sysPost of response.list) {
              this.postList2.push(sysPost);
            }
          });

          if (mechanism.chilrenMechanism.length > 0) {
            this.getPostByMid(mechanism.chilrenMechanism);
          }
        }
      },
      selectSystemChanged(value) {
        //强制刷新
        this.$forceUpdate()
      },
      getPostByMid2(list) {
        for (let mechanism of list) {
          let postData = qs.stringify({
            mid: mechanism.mid
          });
          getSysPostByMid(postData).then((response) => {
            for (let sysPost of response.list) {
              this.postList3.push(sysPost);
            }
          });

          if (mechanism.chilrenMechanism.length > 0) {
            this.getPostByMid(mechanism.chilrenMechanism);
          }
        }
      },
      Change(val) {
        this.search.pDeptId = val;
        let postData = qs.stringify({
          parent: val
        });
        getAllMechanismByParent(postData).then((response) => {
          this.postList2 = [];
          this.search.pInfomationId = undefined;
          this.getPostByMid(response.list);
        })
      },
      Change2(val) {
        console.debug(val)
        this.userInfo.pdeptId = val;
        let postData = qs.stringify({
          parent: val
        });
        getAllMechanismByParent(postData).then((response) => {
          this.postList3 = [];
          this.userInfo.pinfomationId = undefined;
          this.getPostByMid2(response.list);
        })
      },
      Change3(val) {
        for (let postriskcombing of this.bm) {
            console.log(postriskcombing)
        }
        this.userInfo.pdeptId = val;
        let postData = qs.stringify({
          parent: val
        });
        getAllMechanismByParent(postData).then((response) => {
          this.postList3 = [];
          this.userInfo.pinfomationId = undefined;
          this.getPostByMid2(response.list);
        })
      },
      getAllMechanism() {
        getAllMechanism().then(response => {
          this.mechanismList = response.list
        })
      },
      change() {
        this.$forceUpdate()
      },
      getAllPost() {
        getAllPost().then(response => {
          this.postList = response.list
        })
      },
      stateFormat(row, column) {
        if (row.pstatus === 1) {
          return '正常'
        } else {
          return '禁用'
        }
      },
      postFormat(row, column) {
        for (var i = 0; i < this.postList.length; i++) {
          if (row.pinfomationId == this.postList[i].pid) {
            return this.postList[i].pname;
          }
        }

      },
      deptFormat(row, column) {
        for (var i = 0; i < this.mechanismList.length; i++) {
          if (row.pdeptId == this.mechanismList[i].mid) {
            return this.mechanismList[i].mechanismName;
          }
        }

      },
      dateFormat(row, column) {
        var date = row[column.property];
        return moment(date).format("YYYY")
      },
      onrest() {
        this.search = {
          pInfomationId: undefined,
          pGrade: undefined
        };
      },
      initList() {
        list().then(response => {
          console.log(response,"response")
          this.tableData = response.list
          this.total = response.list.length
        })

      },
      // 修改
      handleEdit(index, row) {
        this.dialogTitle = '修改';
        this.userInfo = row;
        /*if (row.pstatus === 1) {
          this.userInfo.pstatus = '正常'
        }
        else {
          this.userInfo.pstatus = '禁用'
        }*/
        this.iconFormVisible = true;
        this.rowIndex = index;
      },
      //按条件查询
      onSearch() {
        let postData = qs.stringify({
          pInfomationId: this.search.pInfomationId,
          pDeptId: this.search.pDeptId,
          pGrade: this.search.pGrade
        });
        this.listLoading = true
        findBy(postData).then((response) => {
          this.currentPage = 1
          this.tableData = response.list
          this.total = response.list.length
          this.listLoading = false
        })
      },
      dele() {
        var data = this.$refs.multipleTable.selection;
        if (JSON.stringify(data) == '[]') {
          this.$notify({
            title: '温馨提示',
            message: '请选择一行进行删除',
            type: 'success',
            duration: 2000
          })
        }
        else {
          var bool = confirm("是否确认删除？");
          if (bool == false) {
            return;
          }
          let postData = qs.stringify({
            test: JSON.stringify(data)
          });
          del(postData).then((response) => {
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
        this.userInfo.deptName = data.label;
        this.isShowSelect = false;
      },
      changeSelectTree() {
        this.isShowSelect = true;
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
      /*filterNode(value, data) {
        if (!value) return true;
        return data.label.indexOf(value) !== -1;
      },*/
      handleSizeChange(size) {
        this.pageSize = size;
      },
      handleCurrentChange(currentPage) {
        this.currentPage = currentPage;
      },
      //新增
      add() {
        this.dialogTitle = '增加';
        this.userInfo = {};
        this.userInfo.pstatus = '正常'
        this.userInfo.pcreateName = this.nickname
        //this.userInfo.pcreateTime = new Date();
        /* 动态赋值实时设置当前时间*/
        //this.$set(this.userInfo, 'pcreateTime', new Date())
        //this.timer= setInterval(this.time, 1000 );

        this.iconFormVisible = true;
      },
      //弹窗取消
      deselect() {
        this.iconFormVisible = false
        clearInterval(this.timer)
      },
      // 弹窗确定
      submitUser() {
        if(this.userInfo.pdeptId===''||this.userInfo.pdeptId===null|this.userInfo.pdeptId===undefined){
            this.$message({
          type: "error",
          message: "父级部门不能为空",
          duration:1000

        });
        return
        } 
        if(this.userInfo.pinfomationId===0||this.userInfo.pinfomationId===null||this.userInfo.pinfomationId===undefined||this.userInfo.pinfomationId===''){
            this.$message({
          type: "error",
          message: "岗位不能为空",
          duration:1000
        });
        return
        }
        let postData = qs.stringify({
          pDeptId: this.userInfo.pdeptId,
          pInfomationId:this.userInfo.pinfomationId,
          pProject: this.userInfo.pproject,
          pRiskPointDescription: this.userInfo.priskPointDescription,
          pProbableLValue: this.userInfo.pprobableLValue,
          pCValue: this.userInfo.pcvalue,
          pMeasures: this.userInfo.pmeasures,
          pid: this.userInfo.pid
        });
        if (this.dialogTitle === '增加') {
          //新增
          this.$refs["dataForm"].validate(valid => {
        // 表单校验通过
        if (valid) {
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
        })
        }
        if (this.dialogTitle === '修改') {
          this.$refs["dataForm"].validate(valid => {
        // 表单校验通过
        if (valid) {
          update(postData).then((response) => {
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
        }})
        }

      },
    },
    data() {
      return {
        timer: '',
        iconFormVisible: false,
        userInfo: {
          pdeptId:undefined,
          pinfomationId:undefined
        },
        dialogTitle: '增加',
        rowIndex: null,
        search: {
          pInfomationId: undefined,
          pDeptId: undefined,
          pGrade: undefined
        },
        placeholder: "",
        bm: [],
        defaultvalue: ["1"],
        props: {
          value: "mid",
          label: "mechanismName",
          children: "chilrenMechanism",
          emitPath: false,
          expandTrigger: "click",
          checkStrictly: true
        },
        placeholder2: "",
        bm2: [],
        defaultvalue2: ["1"],
        props2: {
          value: "mid",
          label: "mechanismName",
          children: "chilrenMechanism",
          emitPath: false,
          expandTrigger: "click",
          checkStrictly: true
        },
        currentPage4: 1,
        pageSize: 5,
        total: 1,
        postList: [],
        postList2: [],
        postList3: [],
        mechanismList: [{}],
        currentPage: 1,
        isShowSelect: false,
        tableData: [{
          pid: undefined,
          pRiskId: undefined,
          pYear: undefined,
          pDeptId: undefined,
          pInfomationId: undefined,
          pProject: undefined,
          pRiskPointDescription: undefined,
          pProbableLValue: undefined,
          pCValue: undefined,
          pDValue: undefined,
          pGrade: undefined,
          pMeasures: undefined,
          pCreateTime: undefined,
          pCreateId: undefined,
          pCreateName: undefined,
          pStatus: undefined
        }],
        listQuery: {
          page: 1,
          limit: 5
        },
        rules:{
          pproject: [
          {
            required: true,
            message: "风险项目名称不能为空",
            trigger: ["change", "blur"]
          }],
          priskPointDescription: [
          {
            required: true,
            message: "风险点描述不能为空",
            trigger: ["change", "blur"]
          }],
          pmeasures: [
          {
            required: true,
            message: "预防和控制措施不能为空",
            trigger: ["change", "blur"]
          }],
          pprobableLValue:[{
            required: true,
            message: "风险发生可能性L值不能为空",
            trigger:  ["change", "blur"]
          }],
          pcvalue:[{
            required: true,
            message: "风险发生可能性C值不能为空",
            trigger:  ["change", "blur"]
          }]
        }
        /* pDeptId: this.userInfo.pdeptId,
          pInfomationId:this.userInfo.pinfomationId,
          pProject: this.userInfo.pproject,
          pRiskPointDescription: this.userInfo.priskPointDescription,
          pProbableLValue: this.userInfo.pprobableLValue,
          pCValue: this.userInfo.pcvalue,
          pMeasures: this.userInfo.pmeasures,
          pid: this.userInfo.pid */

      }
    }
  }
</script>
