<template>
  <div class="app-container">
    <div :style="{'display':dis}">
      <div class="filter-container" align="center" style="margin-top: 20px;">
        <!-- v-waves -->
        <label>责任反馈类型</label>&nbsp;&nbsp;
        <el-input
          v-model="backType"
          placeholder="请输入责任反馈类型"
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
      <el-table
        :key="tableKey"
        v-loading="listLoading"
        @selection-change="handleSelectChangeLeft"
        :data="list"
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
          </template> -->
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
            <span>{{ scope.row.status===1?'待提交':(scope.row.status===2?'已提交':(scope.row.status===3?'待检查':(scope.row.status===5?'已通报':'已结束')))}}</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" width="225px">
          <template slot-scope="scope">
            <a
              style="color:#1890ff"
              @click="handleUpdate(scope.row)"
            >{{ scope.row.status===1||scope.row.status===3?'编辑':''}}&nbsp;&nbsp;</a>
            <a
              style="color:#1890ff"
              @click="handBack(scope.row)"
            >{{ scope.row.status===1||scope.row.status===3?'提交反馈':''}}&nbsp;&nbsp;</a>
           <a
              style="color:#1890ff"
              @click="tongbaoshow(scope.row)"
            >{{ scope.row.status===5?'查看通报':''}}&nbsp;&nbsp;</a>
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
            @click="updateData()"
          >更新</el-button>
          <el-button class="el-icon-back" plain @click="out()">返回</el-button>
        </el-form-item>
        <el-row>
          <el-col style="width:43%">
            <el-form-item style="font-weight: bold;" label="责任反馈标题" prop="backTitle">
              <el-input v-model="temp.backTitle" placeholder="请输入反馈信息标题" style="width:100%" />
            </el-form-item>
          </el-col>
          <el-col style="width:44%">
            <el-form-item style="font-weight: bold;" label="责任监督类型" prop="backType">
              <el-input v-model="temp.backType" disabled="disabled"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col style="width:43%">
            <el-form-item style="font-weight: bold;" label="所属部门" prop="sysMechanism">
              <el-input v-model="temp.sysMechanism.mechanismName" placeholder="请输入制度信息标题" disabled="disabled" style="width:100%" />
            </el-form-item>
          </el-col>
        </el-row>
         <el-form-item style="font-weight: bold;" label="通知内容" prop="dutyContent">
          <quill-editor disabled="disabled"
            class="editor"
            style="height:300px;width:85%;"
            ref="myQuillEditor"
            v-model="temp.gettop"
          ></quill-editor>
        </el-form-item>
        <el-form-item style="font-weight: bold;margin-top:125px" label="责任监督内容" prop="backContent">
          <quill-editor
            class="editor"
            style="height:400px;width:85%;"
            ref="myQuillEditor"
            v-model="temp.backContent"
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
                v-model="temp.backAccessoryName"
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
                <div slot="tip" class="el-upload__tip">只能上传单个txt/word/pdf文件，且不超过500k</div>
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
    <div :style="{'display':dis5}">
      <el-form
        ref="dataForm"
        :rules="rules"
        :model="temp"
        label-position="center"
        label-width="130px"
        style="width: 95%; margin-left:40px;"
      >
      <div class="filter-container" align="right" style="margin-top: 20px;margin-right:40px">
        <el-button type="primary" class="el-icon-back" @click="back2()">返回</el-button>
        <!--  </el-form-item> -->
      </div>
      <el-form-item style="font-weight: bold;" label="通报内容" prop="tongzhi">
          <quill-editor disabled="disabled"
            class="editor"
            style="height:340px;width:90%;margin-top:20px"
            ref="myQuillEditor"
            v-model="temp.tongzhi"
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
  impFile,
  updateStatus
} from "@/api/workorders/workorder";
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
      dis5:'none',
      tableKey: 0,
      list: [], // 后台返回，给数据表格展示的数据
      total: 0, // 总记录数
      listLoading: true, // 是否使用动画
      pageNum: 1, // 分页需要的当前页
      pageRow: 10, // 分页需要的每页显示多少
      backTitle: "",
      backContent: "",
      backType:'',
      temp: {
        // 添加、修改时绑定的表单数据
        sid: undefined,
        backTitle: "",
        backType: "",
        backAccessoryName: "",
        backAccessory: "",
        fileList: [],
        dutyContent: "",
        sysStaff: {
          name: "",
          sid: 0
        },
        sysMechanism:{
          mid:0,
          mechanismName:''
        },
        newTime: new Date(),
        sid: null,
        dnumId: "",
        status: 1,
        dstatus: "",
        dutyContent:'',
        gettop:'',
        tongzhi:''
      },
      title: "添加", // 对话框显示的提示 根据dialogStatus create
      dialogStatus: "", // 表示表单是添加还是修改的
      rules: {
        // 校验规则
        //dTitle:  [{ required: true, message: '标题必填', trigger: 'blur' }],
        backTitle: [
          {
            required: true,
            message: "标题不能为空",
            trigger: ["blur", "change"]
          }
        ]
      },
      isShow: false,
      multipleSelection: [],
      fileList: [],
      formData: null,
      fileAgin: null
    };
  },
  // 创建实例时的钩子函数
  created() {
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
      // 开始转圈圈
      this.listLoading = true;
      // debugger // 调试
      /* let data=qs.stringify({
          account: this.listQuery.account
        }) */
      list(this.pageNum, this.pageRow, this.backType).then(response => {
        this.list = response.records;
        this.total = response.total;
        console.debug(this.list);
        // 转圈圈结束
        this.listLoading = false;
      });
    },
    resetSou() {
      this.dutyType = "";
    },
    // 重置表单数据
    resetTemp() {
      this.temp = {
        sid: undefined,
        backTitle: "",
        backType: "",
        backAccessoryName: "",
        backAccessory: "",
        fileList: [],
        dutyContent: "",
        sysStaff: {
          name: "",
          sid: 0
        },
        sysMechanism:{
          mid:0,
          mechanismName:''
        },
        newTime: new Date(),
        sid: null,
        dnumId: "",
        status: 1,
        dstatus: "",
        dutyContent:''
      
      };
      this.value = [];
      this.formData = null;
      this.fileAgin = null;
      this.fileList=[]
    },
    // 显示修改对话框
    handleUpdate(row) {
      this.fileAgin = row.backAccessoryName;
      this.temp = row;
      this.xianshi();
      if(this.temp.status===1){
          this.temp.dstatus='待提交'
      }else if(this.temp.status===2){
          this.temp.dstatus='已提交'
      }else if(this.temp.status===3){
          this.temp.dstatus='待检查'
      }else if(this.temp.status===5){
        this.temp.dstatus='已通报'
      }else{
        this.temp.dstatus='已结束'
      }
        this.fileList = [{ name: row.backAccessoryName, url: row.backAccessory }];
        if(row.backAccessory===''||row.backAccessory===null){
        this.fileList=[]
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
      this.$refs["dataForm"].validate(valid => {
        // 表单校验通过
        if (valid) {
          if (this.fileList.length!==0&&this.fileAgin !== this.fileList[0].name&&this.temp.backAccessoryName!==''&&this.temp.backAccessoryName!==null) {
            impFile(this.formData).then(response => {
              this.temp.backAccessory = response.dFile;
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
          }else if(this.fileList.length===0&&this.fileAgin !==''){
            this.temp.backAccessoryName=''
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
          }else{
              this.temp.backAccessoryName = "1";
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
    handleImgChange1(file, fileList, name) {
      const isLt2M = file.size / 1024 < 500;
      if (!isLt2M) {
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
      this.temp.backAccessoryName = this.file.name;
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
      this.temp.backAccessoryName = '';
       this.temp.backAccessory = '';
      this.fileList=[]
    },
    handBack(row){
      this.temp=row;
      if(row.status===1||row.status==3){
        this.temp.status=2;
      }
      updateStatus(this.temp).then(response => {
              // 刷新数据表格
              this.getList();
              // ajax去后台删除
              this.$notify({
                title: "成功",
                message: "提交反馈成功",
                type: "success",
                duration: 2000
              });
            });
    },
    back2(){
      this.dis= "inline-block"
      this.dis2= "none"
      this.dis5='none'
    },
    tongbaoshow(row){
      console.debug(this.temp)
      this.temp=row
      this.dis= "none"
      this.dis2= "none"
      this.dis5='inline-block'
    }
  }
};
</script>