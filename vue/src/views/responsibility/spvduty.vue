<template>
  <div class="app-container">
    <div :style="{'display':dis}" style="width:100%">
      <div class="filter-container" align="center" style="margin-top: 20px;">
        <!-- v-waves -->
        <label>责任监督标题</label>&nbsp;&nbsp;
        <el-input
          v-model="dutyTitle"
          placeholder="请输入责任监督标题"
          style="width: 200px;"
          class="filter-item"
        />&nbsp;&nbsp;&nbsp;&nbsp;
        <el-button
          style="margin-left:60px"
          type="primary"
          @click="getList"
          class="el-icon-search"
        >查询</el-button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <el-button type="primary" class="el-icon-refresh" @click="resetSou">重置</el-button>
        <!--  </el-form-item> -->
      </div>
      <div style="margin-top:15px">
        <el-button type="primary" class="el-icon-plus" @click="handleCreate">新增</el-button>
        <el-button type="primary" class="el-icon-delete" @click="handleDelete">删除</el-button>
      </div>
      <el-table
        :key="tableKey"
        v-loading="listLoading"
        @selection-change="handleSelectChangeLeft"
        :default-sort="{prop: 'dcreateTime', order: 'descending'}"
        :data="list"
        border
        fit
        highlight-current-row
        style="width: 100%;margin-top: 20px"
        ref="multipleTable"
      >
        <el-table-column type="selection" width="60px" align="center"></el-table-column>
        <el-table-column
          label="序号"
          prop="index"
          align="center"
          width="100px"
          type="index"
          :index="indexMethod"
        >
          <!-- <template slot-scope="scope">
          <span>{{ scope.row.did }}</span>
          </template>-->
        </el-table-column>
        <el-table-column label="责任监督编号" prop="dnumId" align="center" width="170px">
          <template slot-scope="scope">
            <span>{{ scope.row.dnumId }}</span>
          </template>
        </el-table-column>
        <el-table-column label="责任监督标题" prop="dutyTitle" align="center" width="250px">
          <template slot-scope="scope">
            <span>{{ scope.row.dutyTitle }}</span>
          </template>
        </el-table-column>
        <el-table-column label="责任监督类型" prop="dutyType" align="center" width="200px">
          <template slot-scope="scope">
            <span>{{ scope.row.dutyType }}</span>
          </template>
        </el-table-column>
        <el-table-column label="状态" prop="status" align="center" width="140px">
          <template slot-scope="scope">
            <span>{{ scope.row.status===0?'结束':(scope.row.status===1?'通知':(scope.row.status===2?'自查':(scope.row.status===3?'再检查':(scope.row.status===4?'整改通知':(scope.row.status===5?'提整改报告':'通报')))))}}</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" >
          <template slot-scope="scope">
            <a
              style="color:#1890ff"
              @click="handleUpdate(scope.row)"
            >{{ scope.row.status===1?'编辑':''}}&nbsp;&nbsp;</a>
            <a
              style="color:#1890ff"
              @click="handBackList(scope.row)"
            >{{ scope.row.status===2||scope.row.status===4?'查看反馈':''}}&nbsp;&nbsp;</a>
            <a
              style="color:#1890ff"
              @click="handleStatusUpdate(scope.row)"
            >{{ scope.row.status===1?'发布通知':''}}</a>
            <a
              style="color:#1890ff" href="#"
            >{{ scope.row.status===6?'已通报':''}}</a>
          </template>
        </el-table-column>
      </el-table>
      <!-- 分页工具条  page当前页 total总记录数 limit每页显示多少条 pagination触发自定义事件，查询数据 @size-change="handleSizeChange"
      @current-change="handleCurrentChange"-->
      <div class="block" align="center" style="margin-top: 20px">
        <el-pagination
          v-show="total>0"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          :page-sizes="[5,10, 20, 30, 40,50]"
          :page-size="pageRow"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
        ></el-pagination>
      </div>
    </div>
    <!-- @blur="onEditorBlur($event)" 
      @focus="onEditorFocus($event)"
    @change="onEditorChange($event)"-->
    <div :style="{'display':dis2}">
      <el-form
        ref="dataForm"
        :rules="rules"
        :model="temp"
        label-position="center"
        label-width="130px"
        style="width: 95%; margin-left:40px;"
      >
        <!--        数据校验要求prop值和temp.属性名一致-->
        <el-form-item style="width:100%;height:30px;margin-left: -80px" align="right">
          <el-button
            type="success"
            :disabled="isShow"
            class="el-icon-top"
            @click="dialogStatus==='update'?updateData():createData()"
          >更新</el-button>
          <el-button class="el-icon-back" plain @click="out()">返回</el-button>
        </el-form-item>
        <el-row>
          <el-col style="width:43%">
            <el-form-item style="font-weight: bold;" label="责任监督编号" prop="dnumId">
              <el-input v-model="temp.dnumId" placeholder="请输入制度信息标题" style="width:100%" />
            </el-form-item>
          </el-col>
          <el-col style="width:44%">
            <el-form-item style="font-weight: bold;" label="责任监督类型" prop="dutyType">
              <el-select v-model="temp.dutyType" placeholder="请输入制度信息标题" style="width:100%">
                <el-option value="作风建设"></el-option>
                <el-option value="纪检再监督"></el-option>
                <el-option value="执行力检查"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col style="width:43%">
            <el-form-item style="font-weight: bold;" label="责任监督标题" prop="dutyTitle">
              <el-input v-model="temp.dutyTitle" placeholder="请输入制度信息标题" style="width:100%" />
            </el-form-item>
          </el-col>
          <el-col style="width:44%">
            <el-form-item style="font-weight: bold;" label="所属部门" prop="value">
              <el-cascader
                ref="bbb"
                v-model="value"
                style="width:100%"
                :options="options"
                :props="{ expandTrigger: 'hover',multiple: true,checkStrictly:true }"
                :show-all-levels="false"
              ></el-cascader>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item style="font-weight: bold;" label="责任监督内容" prop="dutyContent">
          <quill-editor
            class="editor"
            style="height:400px;width:85%;"
            ref="myQuillEditor"
            v-model="temp.dutyContent"
          ></quill-editor>
        </el-form-item>
        <el-row style="margin-top: 125px">
          <el-col style="width:43%">
            <el-form-item style="font-weight: bold;" label="创建时间" prop="newTime">
              <el-date-picker
                disabled="disabled"
                style="width: 100%"
                type="date"
                v-model="temp.newTime"
                :format="'yyyy-MM-dd HH:mm:ss'"
              ></el-date-picker>
            </el-form-item>
          </el-col>
          <el-col style="width:44%">
            <el-form-item style="font-weight: bold;" label="创建人" prop="sysStaff">
              <el-input
                v-model="temp.sysStaff.name"
                placeholder="请输入创建人"
                style="width:100%"
                disabled="disabled"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col style="width:43%">
            <el-form-item style="font-weight: bold;" label="附件相关" prop="dutyAccessoryName">
              <el-upload
                style="width:100%"
                class="upload-demo"
                v-model="temp.dutyAccessoryName"
                ref="upload"
                action="https://localhost:8080/imp/importDuty"
                :on-remove="fileRemove"
                :on-change="handleImgChange1"
                accept=".doc, .docx, .pdf, .txt, .xlsx"
                :file-list="fileList"
                :limit="2"
                :auto-upload="false"
              >
                <el-button slot="trigger" class="el-icon-upload" size="small" type="primary">选取文件</el-button>
                <div slot="tip" class="el-upload__tip">只能上传单个doc/docx/pdf文件，且不超过10M</div>
              </el-upload>
            </el-form-item>
          </el-col>
          <el-col style="width:44%">
            <el-form-item style="font-weight: bold;" label="状态" prop="dstatus">
              <el-input
                v-model="temp.dstatus"
                placeholder="请输入制度信息标题"
                style="width:100%"
                disabled="disabled"
              />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </div>
    <div :style="{'display':dis3}" style="width:100%">
      <div class="filter-container" align="right" style="margin-top: 20px;">
        <el-button type="primary" :style="{'display':checkShow}" @click="agincheck(3,4)">再检查</el-button>
        <el-button type="primary" @click="agincheck(4,0)">结束任务</el-button>
        <el-button type="primary" :style="{'display':tongShow}" @click="fabutongbao()">发布通报</el-button>
        <el-button type="primary" class="el-icon-back" @click="goback">返回</el-button>
        <!--  </el-form-item> -->
      </div>
      <el-table
        :key="tableKey"
        v-loading="listLoading"
        @selection-change="handleSelectChangeLeft"
        :data="blist"
        border
        fit
        highlight-current-row
        style="width: 100%;margin-top: 30px"
        ref="multipleTable"
      >
        <el-table-column type="selection" width="60px" align="center"></el-table-column>
        <el-table-column
          label="序号"
          prop="index"
          align="center"
          width="100px"
          type="index"
          :index="indexMethod"
        >
          <!-- <template slot-scope="scope">
          <span>{{ scope.row.did }}</span>
          </template>-->
          <!-- <el-table-column label="责任反馈标题" prop="backTitle" align="center" width="250px">
          <template slot-scope="scope">
            <span>{{ scope.row.backTitle }}</span>
          </template>-->
        </el-table-column>
        <el-table-column label="责任监督类型" prop="backType" align="center" width="200px">
          <template slot-scope="scope">
            <span>{{ scope.row.backType }}</span>
          </template>
        </el-table-column>
        <el-table-column label="部门" prop="sysMechanism" align="center" width="200px">
          <template slot-scope="scope">
            <span>{{ scope.row.sysMechanism.mechanismName }}</span>
          </template>
        </el-table-column>
        <el-table-column label="创建人" prop="sysStaff" align="center" width="200px">
          <template slot-scope="scope">
            <span>{{ scope.row.sysStaff.name }}</span>
          </template>
        </el-table-column>
        <el-table-column label="状态" prop="status" align="center" width="140px">
          <template slot-scope="scope">
            <span>{{ scope.row.status===1?'待提交':(scope.row.status===2?'已提交':(scope.row.status===3?'待检查':(scope.row.status===4?'结束':'已通报')))}}</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" >
          <template slot-scope="scope">
            <a
              style="color:#1890ff"
              @click="handleShow(scope.row)"
            >{{ scope.row.status===2?'查看详情':''}}&nbsp;&nbsp;</a>
          </template>
        </el-table-column>
      </el-table>
      <!-- 分页工具条  page当前页 total总记录数 limit每页显示多少条 pagination触发自定义事件，查询数据 @size-change="handleSizeChange"
      @current-change="handleCurrentChange"-->
      <div class="block" align="center" style="margin-top: 20px">
        <el-pagination
          v-show="btotal>0"
          @size-change="handleSizeChange1"
          @current-change="handleCurrentChange1"
          :current-page="bpageNum"
          :page-sizes="[5,10, 20, 30, 40,50]"
          :page-size="bpageRow"
          layout="total, sizes, prev, pager, next, jumper"
          :total="btotal"
        ></el-pagination>
      </div>
    </div>
    <div :style="{'display':dis4}" style="width:100%">
      <el-form
        ref="dataForm"
        :rules="rules"
        :model="back"
        label-position="center"
        label-width="130px"
        style="width: 95%; margin-left:40px;"
      >
        <!--        数据校验要求prop值和temp.属性名一致-->
        <el-form-item style="width:100%;height:30px;margin-left: -80px" align="right">
          <el-button class="el-icon-back" plain @click="back2()">返回</el-button>
        </el-form-item>
        <el-row>
          <el-col style="width:43%">
            <el-form-item style="font-weight: bold;" label="责任反馈标题" prop="backTitle">
              <el-input
                v-model="back.backTitle"
                disabled="disabled"
                placeholder="请输入反馈信息标题"
                style="width:100%"
              />
            </el-form-item>
          </el-col>
          <el-col style="width:44%">
            <el-form-item style="font-weight: bold;" label="责任监督类型" prop="backType">
              <el-input v-model="back.backType" disabled="disabled"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col style="width:43%">
            <el-form-item style="font-weight: bold;" label="所属部门" prop="sysMechanism">
              <el-input
                v-model="back.sysMechanism.mechanismName"
                placeholder="请输入制度信息标题"
                disabled="disabled"
                style="width:100%"
              />
            </el-form-item>
          </el-col>
          <el-col style="width:44%">
            <el-form-item style="font-weight: bold;" label="附件相关" prop="dutyAccessoryName">
              <el-upload
                style="width:100%"
                class="upload-demo"
                v-model="back.backAccessoryName"
                ref="upload"
                action="https://localhost:8080/imp/importDuty"
                :on-remove="fileRemove"
                :on-change="handleImgChange1"
                accept=".doc, .docx, .pdf, .txt, .xlsx"
                :file-list="fileList"
                :limit="2"
                :auto-upload="false"
                disabled="disabled"
              >
                <el-button slot="trigger" class="el-icon-upload" size="small" type="primary">选取文件</el-button>
                <div slot="tip" class="el-upload__tip">只能上传单个doc/docx/pdf文件，且不超过10M</div>
              </el-upload>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item style="font-weight: bold;" label="通知内容" prop="dutyContent">
          <el-card class="box-card"
          style="width:85%;"
            ref="myQuillEditor"
            v-html="back.gettop">
        </el-card>
        </el-form-item>
        <el-form-item style="font-weight: bold;" label="责任监督内容" prop="backContent">
          <el-card class="box-card"
          style="width:85%;"
            ref="myQuillEditor"
            v-html="back.backContent">
        </el-card>
        </el-form-item>
        
      </el-form>
    </div>
    <div :style="{'display':dis5}">
      <el-form
        ref="dataForm"
        :rules="rules"
        :model="back"
        label-position="center"
        label-width="130px"
        style="width: 95%; margin-left:40px;"
      >
      <div class="filter-container" align="right" style="margin-top: 20px;margin-right:30px">
        <el-button type="primary"  @click="fabu(5,6)">发布通知</el-button>
        <el-button type="primary" class="el-icon-back" @click="back2()">返回</el-button>
        <!--  </el-form-item> -->
      </div>
      <el-form-item style="font-weight: bold;" label="通报内容" prop="tongbao">
          <quill-editor
            class="editor"
            style="height:340px;width:90%;margin-top:20px"
            ref="myQuillEditor"
            v-model="temp.tongbao"
          ></quill-editor>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import {
  add,
  update,
  list,
  deleteSpvduty,
  getDid,
  impFile,
  updateStatus,
  blist,
  btylist,
  updatestatusall,
  updatetong
} from "@/api/responsibility/spvduty";
import qs from "qs";
import { mapGetters } from "vuex";
export default {
  name: "userTable",
  computed: { ...mapGetters(["nickname", "userId"]) },
  components: {},
  data() {
    return {
      dis: "inline-block",
      dis2: "none",
      dis3:"none",
      dis4:"none",
      dis5:'none',
      tableKey: 0,
      checkShow:'none',
      tongShow:'none',
      list: [], // 后台返回，给数据表格展示的数据
      blist: [], // 后台返回，给数据表格展示的数据
      total: 0, // 总记录数
      btotal:0,
      listLoading: true, // 是否使用动画
      pageNum: 1, // 分页需要的当前页
      pageRow: 5, // 分页需要的每页显示多少
       bpageNum: 1, // 分页需要的当前页
      bpageRow: 5, // 分页需要的每页显示多少
      dutyTitle: "",
      dutyContent: "",
      backTitle: "",
        backType: "",
        backAccessoryName: "",
        backAccessory: "",
      temp: {
        // 添加、修改时绑定的表单数据
        did: undefined,
        dutyTitle: "",
        dutyType: "",
        bmid: "",
        dutyAccessoryName: "",
        dutyAccessory: "",
        fileList: [],
        dutyContent: "",
        sysStaff: {
          name: "",
          sid: 0
        },
        newTime: new Date(),
        sid: null,
        dnumId: "",
        status: 1,
        dstatus: "",
        options: [],
        value: [],
        tongbao:''
      },
      back: {
        // 添加、修改时绑定的表单数据
        sid: undefined,
        backTitle: "",
        backType: "",
        backAccessoryName: "",
        backAccessory: "",
        fileList: [],
        sysStaff: {
          name: "",
          sid: 0
        },
        sysMechanism:{
          mid:0,
          mechanismName:''
        },
        newTime: new Date(),
        dnumId: "",
        status: 1,
        dstatus: "",
        dutyContent:'',
        gettop:'',
        cid:0
      },
      title: "添加", // 对话框显示的提示 根据dialogStatus create
      dialogStatus: "", // 表示表单是添加还是修改的
      rules: {
        // 校验规则
        //dTitle:  [{ required: true, message: '标题必填', trigger: 'blur' }],
        dutyType: [
          {
            required: true,
            message: "责任监督类型不能为空",
            trigger: ["change", "blur"]
          }
        ],
        dutyTitle: [
          {
            required: true,
            message: "标题不能为空",
            trigger: ["blur", "change"]
          }
        ]
      },
      isShow: false,
      wew: {},
      multipleSelection: [],
      deleteid: [],
      fileList: [],
      options: [],
      value: [],
      formData: null,
      fileAgin: null
    };
  },
  // 创建实例时的钩子函数
  created() {
    this.getOption();
    this.getList();
    // 在创建时初始化获得部门信息
    //this.getGroupDept()
  },
  methods: {
    // 获得分好组的部门信息
    /* getGroupDept(){
        groupDept().then((response) => {
          this.deptList = response.data.deptList
        })
      }, */
    // 去后台取数据的
    getList() {
      if (!this.hasPerm("spvduty:list")) {
        return;
      }
      // 开始转圈圈
      this.listLoading = true;
      // debugger // 调试
      /* let data=qs.stringify({
          account: this.listQuery.account
        }) */
      list(this.pageNum, this.pageRow, this.dutyTitle).then(response => {
        this.list = response.records;
        this.total = response.total;
        console.debug(this.list);
        // 转圈圈结束
        this.listLoading = false;
      });
    },
    resetSou() {
      this.dutyTitle = "";
    },
    // 重置表单数据
    resetTemp() {
      this.temp = {
        mid: undefined,
        dutyTitle: "",
        dutyType: "",
        bmid: "",
        dutyAccessoryName: "",
        dutyAccessory: "",
        fileList: [],
        dutyContent: "",
        sysStaff: {
          name: "",
          sid: 0
        },
        newTime: new Date(),
        sid: null,
        dnumId: "",
        status: 1,
        dstatus: ""
      };
      this.value = [];
      this.formData = null;
      this.fileAgin = null;
      this.fileList=[]
    },
    // 显示添加的对话框
    handleCreate() {
      // 重置表单数据
      this.resetTemp();
      this.xianshi();
      this.temp.dnumId = this.mom(new Date()).format("YYMMDDHHmmss");
      this.temp.sysStaff.name = this.nickname;
      this.temp.sysStaff.sid = this.userId;
      this.temp.dstatus = "通知";
      // 点击确定时，是执行添加操作
      this.dialogStatus = "create";
      this.title = "添加用户";
      // 显示对话框
      this.dialogFormVisible = true;
      this.$nextTick(() => {
        // 表单清除验证
        this.$refs["dataForm"].clearValidate();
      });
    },
    // 添加对话框里，点击确定，执行添加操作
    createData() {
      // 表单校验
      this.$refs["dataForm"].validate(valid => {
        if (valid) {
          this.$refs["bbb"].getCheckedNodes().filter(getId => {
            this.temp.bmid += getId.value + ",";
          });
          this.temp.bmid = this.temp.bmid.substring(
            0,
            this.temp.bmid.length - 1
          );
          // 所有的校验都通过
          if (this.temp.dutyAccessoryName !== "") {
            impFile(this.formData).then(response => {
              this.temp.dutyAccessory = response.dFile;
              console.debug(this.temp);
              this.isShow = true;
              // 调用api里的sys里的user.js的ajax方法
              add(this.temp).then(response => {
                // 关闭对话框
                this.dialogFormVisible = false;
                // 刷新数据表格里的数据
                this.getList();
                // 显示一个通知
                this.$notify({
                  title: "成功",
                  message: "新增成功",
                  type: "success",
                  duration: 2000
                });
                this.isShow = false;
                this.yincang();
              });
            });
          } else {
            this.isShow = true;
            // 调用api里的sys里的user.js的ajax方法
            add(this.temp).then(response => {
              // 关闭对话框
              this.dialogFormVisible = false;
              // 刷新数据表格里的数据
              this.getList();
              // 显示一个通知
              this.$notify({
                title: "成功",
                message: "新增成功",
                type: "success",
                duration: 2000
              });
              this.isShow = false;
              this.yincang();
            });
          }
        }
      });
    },
    // 显示修改对话框
    handleUpdate(row) {
      this.fileAgin = row.dutyAccessoryName;
      let gId = row.bid.split(",").map(Number); //获取分割后的数组
      let big=[]
      gId.filter(value => {
        let oldv = value;
        let sz = [];
        this.options.filter(val => {
          if (val.value === oldv) {
            sz.push(val.value);
          }else{
            if(typeof val.children!=='undefined'){
            val.children.filter(va=>{
              if (va.value === oldv) {
                sz.push(val.value);
                sz.push(va.value);
              } else {
                if(typeof va.children!=='undefined'){
                va.children.filter(v => {
                  if (v.value === oldv) {
                    sz.push(val.value);
                    sz.push(va.value);
                    sz.push(v.value);
                  }
                });
                } 
              }
            })
          }
          }
          
        });
        big.push(sz)
      });
      this.value=big;
      this.fileList = [{ name: row.dutyAccessoryName, url: row.dutyAccessory }];
      this.temp = row;
      this.xianshi();
      if(this.temp.status===1){
          this.temp.dstatus='通知'
      }else if(this.temp.status===2){
          this.temp.dstatus='自查'
      }else if(this.temp.status===3){
          this.temp.dstatus='再检查'
      }else if(this.temp.status===4){
          this.temp.dstatus='整改通知'
      }else if(this.temp.status===5){
          this.temp.dstatus='提整改报告'
      }else if(this.temp.status===6){
          this.temp.dstatus='通报'
      }else{
          this.temp.dstatus='结束'
      }
      if(row.dutyAccessory===''){
        this.fileList=[];
      }
      // 将对话框里的确定点击时，改为执行修改操作
      this.dialogStatus = "update";
      // 修改标题
      this.title = "修改用户";
      this.$nextTick(() => {
        // 清除校验
        this.$refs["dataForm"].clearValidate();
      });
    },
    // 执行修改操作
    updateData() {
      this.temp.bmid = "";
      this.$refs["dataForm"].validate(valid => {
        // 表单校验通过
        if (valid) {
          this.$refs["bbb"].getCheckedNodes().filter(getId => {
            this.temp.bmid += getId.value + ",";
          });
          this.temp.bmid = this.temp.bmid.substring(
            0,
            this.temp.bmid.length - 1
          );
          if (this.fileList.length!==0&&this.fileAgin !== this.fileList[0].name&&this.temp.dutyAccessoryName!==''&&this.temp.dutyAccessoryName!==null) {
            impFile(this.formData).then(response => {
              this.temp.dutyAccessory = response.dFile;
              console.debug(this.temp);
              this.isShow = true;
              // 调用api里的sys里的user.js的ajax方法
              update(this.temp).then(response => {
                // 关闭对话框
                this.dialogFormVisible = false;
                // 刷新数据表格里的数据
                this.getList();
                // 显示一个通知
                this.$notify({
                  title: "成功",
                  message: "修改成功",
                  type: "success",
                  duration: 2000
                });
                this.isShow = false;
                this.yincang();
              });
            });
          } else if(this.fileList.length!==0&&this.fileAgin === this.fileList[0].name){
            this.temp.dutyAccessoryName = "1";
            update(this.temp).then(response => {
              // 关闭对话框
              this.dialogFormVisible = false;
              // 刷新数据表格里的数据
              this.getList();
              // 显示一个通知
              this.$notify({
                title: "成功",
                message: "修改成功",
                type: "success",
                duration: 2000
              });
              this.isShow = false;
              this.yincang();
            });
          }else{
              this.isShow = true;
              // 调用api里的sys里的user.js的ajax方法
              update(this.temp).then(response => {
                // 关闭对话框
                this.dialogFormVisible = false;
                // 刷新数据表格里的数据
                this.getList();
                // 显示一个通知
                this.$notify({
                  title: "成功",
                  message: "修改成功",
                  type: "success",
                  duration: 2000
                });
                this.isShow = false;
                this.yincang();
              });
          }
        }
      });
    },
    out() {
      this.yincang();
    },
    handleDelete() {
      // 先弹确认取消框
      let title = "";
      if (this.multipleSelection.length < 1) {
        this.$message({
          showClose: true,
          message: "请选择删除信息",
          type: "warning"
        });
      } else {
        if (this.multipleSelection.length == 1) {
          title = this.multipleSelection[0].dutyTitle;
          this.deleteid.push(this.multipleSelection[0].did);
        } else {
          title = "选中";
          this.multipleSelection.filter(row => {
            this.deleteid.push(row.did);
          });
        }
        this.$confirm("确认删除【" + title + "】的信息吗?", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        })
          .then(() => {
            // 调用ajax去后台删除
            console.debug(this.deleteid);
            deleteSpvduty(this.deleteid).then(response => {
              // 刷新数据表格
              this.pageNum = 1;
              this.getList();
              // ajax去后台删除
              this.$notify({
                title: "成功",
                message: "删除成功",
                type: "success",
                duration: 2000
              });
            });
          })
          .catch(() => {
            this.deleteid = [];
          });
      }
    },
    handleImgChange1(file, fileList, name) {
      const isLt2M = file.size / 1024/1024  < 10;
      if (!isLt2M) {
        console.debug(this.dutyAccessoryName);
        this.$message({
          showClose: true,
          message: "文件不能超过500k",
          type: "warning"
        });
        if (fileList.length == 2) {
          this.fileList = fileList.slice(0, 1);
        } else {
          this.fileList = fileList.slice(1);
        }
      } else {
        this.file = file.raw;
        if (fileList) {
          this.fileList = fileList.slice(-1);
        }
      }
      this.formData = new FormData();
      this.formData.append("file", this.file);
      this.temp.dutyAccessoryName = this.file.name;
    },
    handleSizeChange(size) {
      this.deleteid = [];
      this.multipleSelection = [];
      this.pageRow = size;
      this.getList();
    },
    handleCurrentChange(currentPage) {
      this.deleteid = [];
      this.multipleSelection = [];
      this.pageNum = currentPage;
      this.getList();
    },
    handleSizeChange1(size) {
      this.bpageRow = size;
      this.getbList(this.temp.did);
    },
    handleCurrentChange1(currentPage) {
      this.bpageNum = currentPage;
      this.getbList(this.temp.did);
    },
    handleSelectChangeLeft(rows) {
      let self = this;
      self.multipleSelection = rows;
      console.debug(self.multipleSelection);
    },
    indexMethod(val) {
      return ++val;
    },
    handleStatusUpdate(row){
      this.temp=row
      if(this.temp.status===1){
        this.temp.status+=1
      }else if(this.temp.status===6){
        this.temp.status=0
      }
      this.temp.bid=1+','+this.temp.bid
      updateStatus(this.temp).then(response => {
              // 刷新数据表格
              this.getList();
              // ajax去后台删除
              this.$notify({
                title: "成功",
                message: "发布成功",
                type: "success",
                duration: 2000
              });
            });
    },
    xianshi() {
      this.dis = "none";
      this.dis2 = "inline-block";
    },
    yincang() {
      this.dis = "inline-block";
      this.dis2 = "none";
      this.sid = null;
      this.resetTemp();
      this.fileList=[]
    },
    fileRemove(file, fileList) {
      this.file = {};
      this.temp.dutyAccessoryName = '';
       this.temp.dutyAccessory = '';
      this.fileList=[]
    },
    getOption() {
      getDid().then(response => {
        this.options = response;
        console.debug(this.options);
      });
    },
    handBackList(row){
      this.dis='none'
      this.dis2='none'
      this.dis3='inline-block'
      if(row.status===2){
        this.checkShow='inline-block'
      }else{
         this.checkShow='none'
      }
      if(row.status===4){
        this.tongShow='inline-block'
      }else{
        this.tongShow='none'
      }
      this.temp=row
      this.getbList(row.did)
    },
    getbList(did){
      this.listLoading = true;
      // debugger // 调试
      /* let data=qs.stringify({
          account: this.listQuery.account
        }) */
      blist(this.bpageNum, this.bpageRow,did).then(response => {
        this.blist = response.records;
        this.btotal = response.total;
        console.debug(this.list);
        // 转圈圈结束
        this.listLoading = false;
      });
    },
    goback(){
      this.dis='inline-block'
      this.dis2='none'
      this.dis3='none'
    },
    handleShow(row){
      this.fileAgin = row.backAccessoryName;
      this.back = row;
      this.dis='none'
      this.dis2='none'
      this.dis3='none'
      this.dis4='inline-block'
      if(this.back.status===1){
          this.back.dstatus='待提交'
      }else if(this.back.status===2){
          this.back.dstatus='已提交'
      }else if(this.back.status===3){
          this.back.dstatus='待检查'
      }else{
        this.back.dstatus='已结束'
      }
        this.fileList = [{ name: row.backAccessoryName, url: row.backAccessory }];
        if(row.backAccessoryName===null||row.backAccessoryName===''){
        this.fileList=[];
      }
    },
    back2(){
      this.dis='none'
      this.dis2='none'
      this.dis3='inline-block'
      this.dis4='none'
      this.dis5='none'
      this.temp.tongbao=''
    },
    agincheck(bstatus,dstatus){
      this.gincheck(bstatus,dstatus)
    },
    gincheck(bstatus,dstatus){
        let i=0;
        this.back.backType=''
        this.blist.filter(bl=>{
         this.back.backType+=bl.sid+','
          if(bl.status===2){
            i++;
          }
        })
        this.back.backType = this.back.backType.substring(
            0,
            this.back.backType.length - 1
          );
           this.back.bid=this.temp.did //父id
           this.back.status=bstatus //子部门状态
           this.back.sid=dstatus //父窗口状态
           console.debug(this.back)
           let title='再检查';
           if(bstatus===4&&dstatus===0){
             title='结束'
           }
        if(i!==this.blist.length){
          this.$confirm('还有子部门未反馈信息，确定要将项目进行'+title+'吗?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          // 调用ajax去后台删除
          updatestatusall(this.back).then((response) => {
            // 刷新数据表格
            this.getbList(this.temp.did)
            // ajax去后台删除
            this.$notify({
              title: '成功',
              message: '操作成功',
              type: 'success',
              duration: 2000
            })
            this.goback()
            this.getList();

          }) 
        })
        }
        },
        fabutongbao(){
          this.dis='none'
          this.dis1='none'
          this.dis2='none'
          this.dis3='none'
          this.dis4='none'
          this.dis5='inline-block'

        },
        fabu(bstatus,dstatus){
          console.debug(this.temp.tongbao)
          let i=0;
        this.back.backType=''
        this.blist.filter(bl=>{
         this.back.backType+=bl.sid+','
          if(bl.status===2){
            i++;
          }
        })
        this.back.backType = this.back.backType.substring(
            0,
            this.back.backType.length - 1
          );
           this.back.bid=this.temp.did //父id
           this.back.status=bstatus //子部门状态
           this.back.sid=dstatus //父窗口状态
           console.debug(this.back)
        if(i!==this.blist.length){
          this.$confirm('还有子部门未反馈信息，确定要进行项目通报吗?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          // 调用ajax去后台删除
          updatetong(this.temp).then((response)=>{
            updatestatusall(this.back).then((response) => {
            // 刷新数据表格
            this.getbList(this.temp.did)
            // ajax去后台删除
            this.$notify({
              title: '成功',
              message: '操作成功',
              type: 'success',
              duration: 2000
            })
            this.dis='inline-block'
      this.dis2='none'
      this.dis3='none'
      this.dis4='none'
      this.dis5='none'
      this.temp.tongbao=''
             this.getList();
          }) 
          })
          
        })
        }
        }
    }
}
</script>