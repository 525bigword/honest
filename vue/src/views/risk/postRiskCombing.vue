<template>
  <div>
    <div class="app-container" :style="{'display':dis}" style="width:100%">
      <el-form :inline="true" class="demo-form-inline">
        <!--查询条件-->
        <div align="center" style="margin-top: 30px">
          <!--<el-form-item label="部门">
            <el-cascader
              :placeholder="placeholder"
              v-model="search.pdeptid"
              :props="props"
              @change="Change"
              :show-all-levels="false"
              :options="bm"
            ></el-cascader>
          </el-form-item>-->
          <el-form-item label="父级部门" prop="defaultvalue">
            <!-- //temp.parent -->
            <el-cascader
              style="width:60%;margin-left:10px"
              :placeholder="placeholder"
              v-model="search.pdeptid"
              :props="props"
              @change="Change"
              :show-all-levels="false"
              :options="parentDept"
            ></el-cascader>
          </el-form-item>
          <el-form-item label="岗位">
            <el-select style="width: 80%;" v-model="search.pinfomationid" placeholder="岗位">
              <el-option
                v-for="(post) in postList2"
                :key="post.pid"
                :label="post.pname"
                :value="post.pid"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="风险等级" :line="true">
            <el-select style="width: 80%;" v-model="search.pgrade" placeholder="请选择">
              <el-option label="一级风险" value="一级风险"></el-option>
              <el-option label="二级风险" value="二级风险"></el-option>
              <el-option label="三级风险" value="三级风险"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="onSearch()" class="el-icon-search">查询</el-button>
            <el-button type="primary" class="el-icon-refresh" plain @click="resetTemp">重置</el-button>
          </el-form-item>
        </div>
        <!--操作按钮-->
        <div>
          <el-form-item>
            <el-button type="primary" class="el-icon-plus" @click="add">新增</el-button>
            <el-button type="primary" class="el-icon-delete" @click="handleDelete">删除</el-button>
          </el-form-item>
        </div>
        <!--数据表格-->
        <el-table
          :data="list"
          border
          fit
          highlight-current-row
          style="width: 100%;"
          @selection-change="changeFun"
        >
          <el-table-column type="selection" />
          <el-table-column label="序号" type="index" align="center" width="60px"></el-table-column>
          <el-table-column label="执行年份" width="100px" align="center">
            <template slot-scope="{row}">
              <span class="link-type" v-show="false">{{ row.pid }}</span>
              <span class="link-type">{{ row.pyear.substring(0,4) }}</span>
            </template>
          </el-table-column>
          <el-table-column label="风险项目名称" align="center">
            <template slot-scope="scope">
              <el-tooltip content="点击查看详情或修改" placement="right" effect="dark">
                <a
                  @click="handleUpdate(scope.row)"
                  target="_blank"
                  class="buttonText"
                  style="color: #1890ff"
                >{{scope.row.pproject}}</a>
              </el-tooltip>
            </template>
          </el-table-column>
          <el-table-column label="部门名称" align="center">
            <template slot-scope="{row}">
              <span class="link-type">{{ row.deptName ?row.deptName:'公共风险'}}</span>
            </template>
          </el-table-column>
          <el-table-column label="岗位名称" align="center">
            <template slot-scope="{row}">
              <span class="link-type">{{ row.postname ?row.postname:'公共岗位' }}</span>
            </template>
          </el-table-column>
          <el-table-column label="风险等级" align="center">
            <template slot-scope="{row}">
              <span class="link-type">{{ row.pgrade }}</span>
            </template>
          </el-table-column>
          <!--操作按钮-->
          <!--<el-table-column label="操作" align="center" width="230" class-name="small-padding fixed-width">
            <template slot-scope="{row}">
              &lt;!&ndash;<el-button size="mini" @click="particulars(row)">
                详情
              </el-button>&ndash;&gt;
              <el-button type="primary" size="mini" @click="handleUpdate(row)">
                修改
              </el-button>
            </template>
          </el-table-column>-->
        </el-table>
        <!--分页-->
        <div class="block" align="center">
          <el-pagination
            v-show="total>0"
            @size-change="changeSizeHandle"
            @current-change="currentChangeHandle"
            :current-page="listQuery.pageNum"
            :page-sizes="[5, 10, 20, 50,100]"
            layout="total, sizes, prev, pager, next, jumper"
            :page-size="listQuery.pageSize"
            :total="total"
          ></el-pagination>
        </div>
      </el-form>
    </div>
    <!--增加修改页面-->
    <div :style="{'display':dis2}" style="background-color: lightgray;width:100%;margin-top:-9px">
      <el-main>
        <!--  margin-left:40px; -->
        <el-form
          :inline="true"
          ref="dataForm"
          :rules="rules"
          :model="userInfo"
          label-width="150px"
          width="100%"
        >
          <!--操作按钮-->
          <div
            style="background-color: white;width: 100%;height: 65px;position:fixed; top:50px; left:-1px;z-index:2 ;"
          >
            <el-form-item
              style="width:100%;height:30px;margin-left: -60px;margin-top:12px"
              align="right"
            >
              <el-button type="success" class="el-icon-top" @click="submitUser()">保存</el-button>
              <el-button class="el-icon-back" @click="deselect()">返回</el-button>
            </el-form-item>
          </div>
          <div style="background-color: white;margin-top: 25px;z-index:3;">
            <div style="height:20px"></div>
            <h2 v-text="h1Text" align="center"></h2>
            <!--表单数据-->
            <el-input v-model="userInfo.pid" placeholder="编号" type="hidden"></el-input>
            <el-row style="margin-left:5%">
              <el-col :span="12" style="width:43%">
                <el-form-item style="font-weight: bold;" label="风险项目名称" prop="pproject">
                  <el-input
                    style="width: 350px"
                    v-model="userInfo.pproject"
                    placeholder="请输入风险项目名称"
                  ></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="12" style="width:43%">
                <el-form-item style="font-weight: bold;" label="执行年份" prop="pyear">
                  <el-date-picker
                    :editable="false"
                    style="width: 350px"
                    v-model="userInfo.pyear"
                    type="year"
                    format="yyyy"
                    value-format="yyyy"
                    placeholder="选择年份"
                  ></el-date-picker>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row style="margin-left:5%">
              <el-col :span="12" style="width:43%">
                <el-form-item style="font-weight: bold;" label="部门" prop="pdeptId">
                  <el-cascader
                    style="width: 350px"
                    placeholder="部门"
                    v-model="userInfo.pdeptId"
                    :props="props2"
                    @change="Change2"
                    :show-all-levels="false"
                    :options="bm2"
                  ></el-cascader>
                </el-form-item>
              </el-col>
              <el-col :span="12" style="width:43%">
                <el-form-item style="font-weight: bold;" label="岗位" prop="pinfomationId">
                  <el-select
                    @change="selectSystemChanged"
                    style="width: 350px"
                    v-model="userInfo.pinfomationId"
                    placeholder="岗位"
                  >
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
            <el-row style="margin-left:5%">
              <el-col :span="24">
                <el-form-item
                  style="font-weight: bold;"
                  label="廉政风险点描述"
                  prop="priskPointDescription"
                >
                  <el-input
                    v-model="userInfo.priskPointDescription"
                    :rows="3"
                    style="width: 870px"
                    placeholder="请输入廉政风险点描述"
                    type="textarea"
                  ></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row style="margin-left:5%">
              <el-col :span="12" style="width:43%">
                <el-form-item style="font-weight: bold;" label="风险发生可能性L值" prop="pprobableLValue">
                  <el-select
                    v-model="userInfo.pprobableLValue"
                    placeholder="风险发生可能性L值"
                    style="width: 350px"
                    class="filter-item"
                    @change="changValueRisk()"
                  >
                    <el-option
                      v-for="item in lValue"
                      :key="item.lId"
                      :label="item.lName"
                      :value="item.lId"
                    />
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="12" style="width:43%">
                <el-form-item style="font-weight: bold;" label="风险产生严重性C值" prop="pcvalue">
                  <el-select
                    v-model="userInfo.pcvalue"
                    placeholder="风险产生严重性C值"
                    style="width: 350px"
                    class="filter-item"
                    @change="changValueRisk()"
                  >
                    <el-option
                      v-for="item in cValue"
                      :key="item.cId"
                      :label="item.cName"
                      :value="item.cId"
                    />
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row style="margin-left:5%">
              <el-col :span="12" style="width:43%">
                <el-form-item style="font-weight: bold;" label="廉政风险值D值" prop="pdvalue">
                  <span class="link-type" v-if="false">{{ row.pid }}</span>
                  <el-input
                    placeholder="系统自动算出"
                    v-model="userInfo.pdvalue"
                    style="width: 350px"
                    disabled="disabled"
                  />
                </el-form-item>
              </el-col>

              <el-col :span="12" style="width:43%">
                <el-form-item style="font-weight: bold;" label="风险等级" prop="pgrade">
                  <el-select
                    v-model="userInfo.pgrade"
                    placeholder="系统计算风险值后自动选择"
                    style="width: 350px"
                    class="filter-item"
                    disabled="disabled"
                  >
                    <el-option
                      v-for="item in riskGrades"
                      :key="item.rgId"
                      :label="item.rgName"
                      :value="item.rgId"
                    />
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row style="margin-left:5%">
              <el-col :span="24">
                <el-form-item style="font-weight: bold;" label="预防和控制措施" prop="pmeasures">
                  <el-input
                    v-model="userInfo.pmeasures"
                    :rows="8"
                    style="width: 870px"
                    placeholder="请输入预防和控制措施"
                    type="textarea"
                  ></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-form-item style="margin-top:30px"></el-form-item>
          </div>
        </el-form>
      </el-main>
    </div>
  </div>
</template>

<script>
import qs from "qs";
import {
  add,
  del,
  findBy,
  getAllMechanism,
  getAllPost,
  list,
  update,
  getAllMechanismByParent,
  getSysPostByMid
} from "@/api/risk/postRiskCombing";
import { mapGetters } from "vuex";

import moment from "moment";
import user from "../../store/modules/user";

export default {
  computed: {
    ...mapGetters(["userId", "nickname"])
  },
  data() {
    return {
      h1Text:'',
      dis: "inline-block",
      dis2: "none",
      iconFormVisible: false,
      dialogTitle: "增加",
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
      postList: [],
      postList2: [],
      postList3: [],
      isShowSelect: false,
      tableData: [
        {
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
        }
      ],
      rules: {
        pproject: [
          {
            required: true,
            message: "风险项目名称不能为空",
            trigger: ["change", "blur"]
          }
        ],
        pyear: [
          {
            required: true,
            message: "执行年份必选",
            trigger: ["change", "blur"]
          }
        ],
        priskPointDescription: [
          {
            required: true,
            message: "风险点描述不能为空",
            trigger: ["blur"]
          }
        ],
        pmeasures: [
          {
            required: true,
            message: "预防和控制措施不能为空",
            trigger: ["change", "blur"]
          }
        ],
        pprobableLValue: [
          {
            required: true,
            message: "风险发生可能性L值不能为空",
            trigger: ["change", "blur"]
          }
        ],
        pcvalue: [
          {
            required: true,
            message: "风险发生可能性C值不能为空",
            trigger: ["change", "blur"]
          }
        ],
        pdvalue: [
          {
            required: true
          }
        ],
        pgrade: [
          {
            required: true
          }
        ]
      },
      /*---------------------------------------------------------*/
      createId: undefined,
      userName: "", // 当前登录用户
      createDate: "", // 创建时间（系统当前时间）
      PostRiskNumber: "", // 添加时的岗位风险编号
      listQuery: {
        // 分页数据
        pageNum: 1,
        pageSize: 5
      },
      total: 0, // 数据总数
      userInfo: {
        // 添加、修改时绑定的表单数据
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
      },
      mechanismList: [{}], // 部门集合
      list: [], // 数据列表
      checkBoxData: [], //多选框
      riskGrades: [
        { rgId: "一级风险", rgName: "高度风险(一级)" },
        { rgId: "二级风险", rgName: "中度风险(二级)" },
        { rgId: "三级风险", rgName: "一般风险(三级)" }
      ],
      lValue: [
        { lId: "完全可能(10)", lName: "完全可能" },
        { lId: "可能性较大(6)", lName: "可能性较大" },
        { lId: "可能性一般(3)", lName: "可能性一般" },
        { lId: "可能性小(1)", lName: "可能性小" }
      ],
      cValue: [
        { cId: "重大(100)", cName: "重大" },
        { cId: "较大(40)", cName: "较大" },
        { cId: "一般(15)", cName: "一般" },
        { cId: "较轻(7)", cName: "较轻" }
      ],
      parentDept:[],
      parentDept2:[]
    };
  },
  created() {
    this.getSysmechanismAll().then(res => {

    });
    // this.getAllPost();
    // this.getAllMechanism();
  },
  methods: {
    /*// 生成岗位风险编号
      getRiskId() {
        // 获得uuid(随机数)
        var s = [];
        var hexDigits = "0123456789abcdef";
        for (var i = 0; i < 36; i++) {
          s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
        }
        s[14] = "4"; // bits 12-15 of the time_hi_and_version field to 0010
        s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1); // bits 6-7 of the clock_seq_hi_and_reserved to 01
        s[8] = s[13] = s[18] = s[23] = "-"
        var uuid = s.join("")
        // 岗位风险编号前缀
        let letStr = "PRN" + Moment(new Date()).format("YYYY-MM-DD-")
        // 返回岗位风险编号
        return letStr + uuid
      },*/
    //动态计算风险值
    changValueRisk() {
      //console.log("this.userinfo----",this.userInfo)
      //console.log(this.userInfo.pprobableLValue.substring(1))
      let lValue = this.userInfo.pprobableLValue.substring(
        this.userInfo.pprobableLValue.indexOf("(") + 1,
        this.userInfo.pprobableLValue.indexOf(")")
      );
      let cValue = this.userInfo.pcvalue.substring(
        this.userInfo.pcvalue.indexOf("(") + 1,
        this.userInfo.pcvalue.indexOf(")")
      );
      var s = (this.userInfo.pdvalue = parseInt(lValue) * parseInt(cValue));
      //console.log("s----"+s)
      if (s >= 240) {
        this.userInfo.pgrade = this.riskGrades[0].rgId;
      } else if (s < 240 && s >= 90) {
        this.userInfo.pgrade = this.riskGrades[1].rgId;
      } else if (s < 90) {
        this.userInfo.pgrade = this.riskGrades[2].rgId;
      }
    },
    // 重置表单数据
    resetTemp() {
      let deptId = this.$route.query.pDeptId;
      let grade = this.$route.query.pGrade;
      //console.log(deptId+"---------"+grade)
      if (deptId != undefined && grade != undefined) {
        this.$router.push({ path: "/risk/postRiskCombing" });
      }
      (this.search = {
        pGrade: undefined,
        pDeptId: undefined,
        pInfomationId: undefined
      }),
        (this.userInfo = {
          pid: undefined,
          priskid: undefined,
          pyear: "",
          pdeptid: undefined,
          pinfomationid: undefined,
          pproject: "",
          priskpointdescription: "",
          pprobablelvalue: undefined,
          pcvalue: undefined,
          pdvalue: "",
          pgrade: null,
          pcreatetime: "",
          pcreateid: undefined,
          pcreatename: "",
          pstatus: ""
        });
    },
    // 设置风险值的默认选中和默认值
    defaultSelect() {
      this.userInfo.pprobableLValue = this.lValue[0].lId;
      this.userInfo.pcvalue = this.cValue[0].cId;
      let lValue = this.userInfo.pprobableLValue.substring(
        this.userInfo.pprobableLValue.indexOf("(") + 1,
        this.userInfo.pprobableLValue.indexOf(")")
      );
      let cValue = this.userInfo.pcvalue.substring(
        this.userInfo.pcvalue.indexOf("(") + 1,
        this.userInfo.pcvalue.indexOf(")")
      );
      this.userInfo.pdvalue = parseInt(lValue) * parseInt(cValue);
      this.userInfo.pgrade = this.riskGrades[0].rgId;
    },
    // 加载数据列表
    initList() {
      let deptId = this.$route.query.pDeptId;
      let grade = this.$route.query.pGrade;
      console.log(deptId+"---------"+grade)
      if (deptId != undefined && grade != undefined) {
        this.search.pdeptid = deptId;
        this.search.pgrade = grade;
        this.onSearch();
      } else {
        list(this.listQuery.pageNum, this.listQuery.pageSize).then(response => {
          console.log("response:"+response)
          this.list = response.list;
          this.tableData = response.list;
          this.total = response.total;
          // 当前登录用户
          this.userName = response.loginStaff.name;
          this.createId = response.loginStaff.sid;
        });
      }
    },
    //按条件查询
    onSearch() {
      let deptId = this.$route.query.pDeptId;
      let grade = this.$route.query.pGrade;
      //console.log(deptId+"---------"+grade)
      if (deptId != undefined && grade != undefined) {
        this.search.pdeptid = deptId;
        this.search.pgrade = grade;
      }
      if(this.search.pdeptid===undefined&&this.search.pinfomationid===undefined&&this.search.pgrade===undefined){
        this.initList()
        //alert("initList")
        return
      }
      let postData = qs.stringify({
        pdeptid: this.search.pdeptid,
        pinfomationid: this.search.pinfomationid,
        pgrade: this.search.pgrade,
        pageNum: this.listQuery.pageNum,
        pageSize: this.listQuery.pageSize
      });
      this.listLoading = true;
      console.log("postData:" + postData);

      findBy(postData).then(response => {
        this.currentPage = 1;
        this.list = response.list;
        this.tableData = response.list;
        console.log(response);
        this.total = response.total;
        this.listLoading = false;
      });
    },
    //新增
    add() {
      this.$refs["dataForm"].clearValidate();
      this.dialogTitle = "添加岗位风险";
      this.h1Text="添加岗位风险"
      //this.userInfo.pstatus = '正常'
      this.resetTemp();
      // 默认选中风险值
      this.defaultSelect();
      this.iconFormVisible = true;
      this.dis = "none";
      this.dis2 = "inline-block";
    },
    //弹窗取消
    deselect() {
      this.resetTemp();
      this.dis = "inline-block";
      this.dis2 = "none";
      this.initList()
    },
    // 弹窗确定
    submitUser() {
      let postData =qs.stringify({
        pid: this.userInfo.pid,
        pYear: this.userInfo.pyear,
        pDeptId: this.userInfo.pdeptid,
        pInfomationId: this.userInfo.pinfomationId,
        pProject: this.userInfo.pproject,
        pRiskPointDescription: this.userInfo.priskPointDescription,
        pProbableLValue: this.userInfo.pprobableLValue,
        pCValue: this.userInfo.pcvalue,
        pDValue: this.userInfo.pdvalue,
        pGrade: this.userInfo.pgrade,
        pMeasures: this.userInfo.pmeasures,
        pCreateId: this.createId,
        pCreateName: this.userName
      });
      console.log("postData",postData)
      console.log("this.userInfo",this.userInfo)

      if (this.dialogTitle === "添加岗位风险") {
        //新增
        this.$refs["dataForm"].validate(valid => {
          // 表单校验通过
          if (valid) {
            add(postData).then(response => {
              this.iconFormVisible = false;
              this.initList();
              this.$notify({
                title: "成功",
                message: response.message,
                type: "success",
                duration: 2000
              });
              this.deselect()// 弹窗取消
            });
          }
        });
      }
      if (this.dialogTitle === "修改岗位风险") {
        console.log("postData----"+this.userInfo.pDeptId)
        this.$refs["dataForm"].validate(valid => {
          // 表单校验通过
          if (valid) {
            update(postData).then(response => {
              this.iconFormVisible = false;
              this.initList();
              this.$notify({
                title: "成功",
                message: response.message,
                type: "success",
                duration: 2000
              });
              this.deselect()// 弹窗取消
            });
          }
        });
      }
    },
    // 显示修改对话框
    handleUpdate(row) {
      // 将row里面与temp里属性相同的值，进行copy
      console.log("row",row)
      this.userInfo.pProbableLValue=row.pprobableLValue
      //console.log("row.pdeptid:"+row.pdeptId)
      this.Change2()
      getSysPostByMid(row.pdeptId).then(response => {
        this.postList3 = [];
        for (let sysPost of response.list) {
          this.postList3.push(sysPost);
        }
      });
      //this.userInfo.pdeptid=row.pdeptId
      /*if(row.pinfomationId===0){
        this.userInfo.pinfomationId=''
      }else {
        this.userInfo.pinfomationId = row.pinfomationId
      }*/
      this.dialogTitle = "修改岗位风险";
      this.h1Text="修改岗位风险"
      this.iconFormVisible = true;
      this.$nextTick(() => {
        // 清除校验
        this.$refs["dataForm"].clearValidate();
      });
      this.dis = "none";
      this.dis2 = "inline-block";
      this.userInfo = Object.assign({}, row);
      if(row.pinfomationId===0){
        this.userInfo.pinfomationId=undefined
      }
    },
    // 多选框赋值
    changeFun(val) {
      this.checkBoxData = val;
    },
    // 删除操作
    handleDelete() {
      var ids = this.checkBoxData.map(item => {
        return { pid: item.pid };
      });
      var ids1 = "";
      for (var i = 0; i < ids.length; i++) {
        ids1 += ids[i].pid + ",";
      }
      if (ids1 == "") {
        /*this.$message({
            type: 'warning',
            message: '请选择要删除的数据'
          })*/
        this.$notify({
          title: "温馨提示",
          message: "请至少选择一行进行删除",
          type: "success",
          duration: 2000
        });
      } else {
        // 先弹确认取消框
        var bool = confirm("是否确认删除？");
        if (!bool) {
          return;
        }
        // 调用ajax去后台删除
        del(ids1).then(response => {
          // 刷新数据表格
          this.onSearch();
          this.$notify({
            title: "成功",
            message: response.message,
            type: "success",
            duration: 2000
          });
        });
      }
    },
    // 每页显示的条数改变
    changeSizeHandle(val) {
      this.listQuery.pageSize = val; // 改变每页显示的条数
      this.listQuery.pageNum = 1; // 注意：在改变每页显示的条数时，要将页码显示到第一页
      this.onSearch(); // 点击每页显示的条数时，显示第一页
      //console.log(`每页 ${val} 条`)
    },
    // current-change用于监听页数改变，而内容也发生改变
    currentChangeHandle(val) {
      this.listQuery.pageNum = val; // 改变默认的页数
      this.onSearch(); // 切换页码时，要获取每页显示的条数
      //console.log(`当前页: ${val}`)
    },
    // 获得部门2
    getSysmechanismAll(val = 0) {
      return new Promise((resolve, reject) => {
        this.api({
          url: "sysmechanism/get",
          method: "get"
        }).then(res => {
          console.log("sysmechanism", res);
          // this.bm.concat(res)
          this.bm2 = [];
          res.filter(item => {
            this.bm2.push(item);
          });
          resolve()
        });
      });
      },
    // 获得部门1
    getSysmechanismAll(val = 0) {
      return new Promise((resolve, reject) => {
        this.api({
          url: "sysmechanism/get",
          method: "get"
        }).then(res => {
          console.log("sysmechanism", res);
          // this.bm.concat(res)
          this.bm = [];
          res.filter(item => {
            this.bm.push(item);
          });
          let detp={
            mid:0,
            mechanismName:"公共风险",
            chilrenMechanism:this.bm
          }
          this.parentDept.push(detp)
          /*this.getAllStaff(res[0].mid);*/
          // this.getAllld(res[0].mid)
          console.log(this.bm,"this.bm");
          this.initList(); // 加载数据列表
          resolve()
        });
      });
    },

    selectSystemChanged(value) {
      //强制刷新
      this.$forceUpdate();
    },
    Change(val) {
      this.search.pdeptid = val;
      let postData = qs.stringify({
        parent: val
      });
      /*console.log("第一个下拉框选中部门val"+val)
      console.log("第一个下拉框选中部门parent"+val)*/
      /*getAllMechanismByParent(postData).then(response => {
        //console.log(response, "response.postData");
        this.postList2 = [];
        this.search.pinfomationid = undefined;
        this.getPostByMid(response.list);
      });*/
      this.postList2 = [];
      this.search.pinfomationid = undefined;
      getSysPostByMid(val).then(response => {
        //console.log("response:", response);
        for (let sysPost of response.list) {
          this.postList2.push(sysPost);
        }
      });
    },
    getPostByMid(list) {
      for (let mechanism of list) {
        let postData = qs.stringify({
          mid: mechanism.mid
        });
        getSysPostByMid(postData).then(response => {
          //console.log("response:", response);
          for (let sysPost of response.list) {
            this.postList2.push(sysPost);
          }
        });

        if (mechanism.chilrenMechanism.length > 0) {
          this.getPostByMid(mechanism.chilrenMechanism);
        }
      }
    },
    Change2(val) {
      if(val===undefined)
        return
      console.debug(val);
      this.userInfo.pinfomationid=undefined
      this.userInfo.pdeptid = val;
      let postData = qs.stringify({
        parent: val
      });
      //console.log("parent: val:"+ val)
      /*getAllMechanismByParent(postData).then(response => {
        this.postList3 = [];
        this.getPostByMid2(response.list);
      });*/
      this.postList3 = [];
      this.search.pinfomationid = undefined;
      getSysPostByMid(val).then(response => {
        //console.log("response:", response);
        for (let sysPost of response.list) {
          this.postList3.push(sysPost);
        }
      });
    },
    getPostByMid2(list) {
      for (let mechanism of list) {
        let postData = qs.stringify({
          mid: mechanism.mid
        });
        getSysPostByMid(postData).then(response => {
          for (let sysPost of response.list) {
            this.postList3.push(sysPost);
          }
        });

        if (mechanism.chilrenMechanism.length > 0) {
          this.getPostByMid(mechanism.chilrenMechanism);
        }
      }
    },

    getAllMechanism() {
      getAllMechanism().then(response => {
        this.mechanismList = response.list;
      });
    },
    change() {
      this.$forceUpdate();
    },
    getAllPost() {
      getAllPost().then(response => {
        this.postList = response.list;
      });
    },
    stateFormat(row, column) {
      if (row.pstatus === 1) {
        return "正常";
      } else {
        return "禁用";
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
      return moment(date).format("YYYY");
    },

    dele() {
      var data = this.$refs.multipleTable.selection;
      if (JSON.stringify(data) == "[]") {
        this.$notify({
          title: "温馨提示",
          message: "请选择一行进行删除",
          type: "success",
          duration: 2000
        });
      } else {
        var bool = confirm("是否确认删除？");
        if (bool == false) {
          return;
        }
        let postData = qs.stringify({
          test: JSON.stringify(data)
        });
        del(postData).then(response => {
          this.initList();
          this.$notify({
            title: "成功",
            message: response.message,
            type: "success",
            duration: 2000
          });
        });
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
      var isOther =
        e.relatedTarget == null ||
        e.relatedTarget.closest("div.el-tree") == null ||
        e.relatedTarget.closest("div.el-tree").id != "floatTree";

      if (isOther) {
        this.isShowSelect = false;
      } else {
        e.target.focus();
      }
    }
  }
};
</script>
<style>
</style>
