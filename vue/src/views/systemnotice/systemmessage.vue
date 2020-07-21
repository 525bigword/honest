<template>
  <div >
    <div class="app-container" :style="{'display':dis}" style="width:100%">
      <div class="filter-container" style="margin-top: -30px;margin-left:60px">
        <!-- v-waves -->
        <label>标题</label>&nbsp;&nbsp;
        <el-input
          v-model="articletitle"
          placeholder="请输入通知公告标题"
          style="width: 200px;"
          class="filter-item"
        />&nbsp;&nbsp;&nbsp;&nbsp;
        <label style="margin-left:60px">描述</label>&nbsp;&nbsp;
        <el-input
          v-model="describe"
          placeholder="请输入通知公告描述"
          style="width: 200px;"
          class="filter-item"
        />
        <!--  <el-form-item> -->
        &nbsp;&nbsp;&nbsp;&nbsp;
        <el-button
          style="margin-left:60px"
          type="primary"
          @click="getList"
          class="el-icon-search"
        >查询</el-button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <el-button type="primary" class="el-icon-refresh" @click="resetSou">重置</el-button>
        <!--  </el-form-item> -->
      </div>
      <div style="margin-top:20px">
        <el-button type="primary" class="el-icon-plus" @click="handleCreate">增加通知</el-button>
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
          width="90px"
          type="index"
          :index="indexMethod"
        >
          <!-- <template slot-scope="scope">
          <span>{{ scope.row.did }}</span>
          </template>-->
        </el-table-column>

        <el-table-column label="标题" prop="articletitle" align="center" width="240px">
          <template slot-scope="scope">
            <a style="color:#1890ff" @click="handleUpdate(scope.row)">{{ scope.row.articletitle }}</a>
          </template>
        </el-table-column>
        <el-table-column label="描述" prop="describe" align="center" width="250px">
          <template slot-scope="scope">
            <span>{{ scope.row.describe | getContent }}</span>
          </template>
        </el-table-column>
        <el-table-column label="创建人" prop="sysStaff" align="center" width="180px">
          <template slot-scope="scope">
            <span>{{ scope.row.sysStaff.name }}</span>
          </template>
        </el-table-column>
        <el-table-column label="创建时间" sortable prop="createTime" align="center">
          <template slot-scope="scope">
            <span>{{ scope.row.createTime | comverTime('YYYY-MM-DD') }}</span>
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
    <div :style="{'display':dis2}" style="background-color: lightgray;width:100%;margin-top:-9px">
      <el-main>
      <el-form
        ref="dataForm"
        :rules="rules"
        :model="temp"
        label-position="center"
        label-width="130px"
        style="width: 100%;">
        <!--        数据校验要求prop值和temp.属性名一致-->
        <div style="background-color: white;width: 100%;height: 65px;position:fixed; top:50px; left:-1px;z-index:2 ;">
        <el-form-item  style="width:100%;height:30px;margin-left: -60px;margin-top:12px" align="right">
          <el-button
            type="success"
            :disabled="isShow"
            class="el-icon-top"
            @click="dialogStatus==='update'?updateData():createData()"
          >保存</el-button>
          <el-button class="el-icon-back" plain @click="out()">返回</el-button>
        </el-form-item></div>
        <div style="background-color: white;margin-top: 25px;z-index:3;">
          <div style="height:20px"></div>
        <el-row>
          <el-col style="width:45%;margin-top:10px">
            <el-form-item style="font-weight: bold;" label="文章标题" prop="articletitle">
              <el-input v-model="temp.articletitle" placeholder="请输入文章标题" />
            </el-form-item>
          </el-col>
          <el-col style="width:45%;margin-top:10px">
            <el-form-item style="font-weight: bold;" label="文章来源" prop="articlesource">
              <el-input v-model="temp.articlesource" placeholder="请输入文章来源" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row style="margin-top:10px">
          <!-- <el-col style="width:45%;">
            <el-form-item style="font-weight: bold;" label="文章属性" prop="attribute">
                <el-checkbox-group v-model="checkList"
                @change="getValue">
              <el-checkbox :key="1" label="1">头条</el-checkbox>
              <el-checkbox :key="2" label="2">推荐</el-checkbox>
              <el-checkbox :key="3" label="3">图片</el-checkbox>
              <el-checkbox :key="4" label="4">幻灯</el-checkbox>
                </el-checkbox-group>
            </el-form-item>
          </el-col> -->
          <el-col style="width:45%">
            <el-form-item style="font-weight: bold;" label="文章缩略图" prop="picturename">
              <el-upload
                style="width:100%"
                class="upload-demo"
                v-model="temp.picturename"
                ref="upload"
                action="https://localhost:8080/imp/importDuty"
                :on-remove="fileRemove"
                :on-change="handleImgChange1"
                accept=".jpg,.png"
                list-type="picture"
                :file-list="fileList"
                :limit="2"
                :auto-upload="false"
              >
                <el-button slot="trigger" class="el-icon-upload" size="small" type="primary">选取文件</el-button>
                <div slot="tip" class="el-upload__tip">只能上传单个png/jpg文件，且不超过5M</div>
              </el-upload>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item style="font-weight: bold;" label="描述" prop="describe">
          <textarea style="width:88%" rows="8" cols="107" v-model="temp.describe"></textarea>
        </el-form-item>
        <el-form-item style="font-weight: bold;" label="内容" prop="content">
          <quill-editor
            class="editor"
            style="height:400px;width:88%;"
            ref="myQuillEditor"
            v-model="temp.content"
          ></quill-editor>
        </el-form-item>
        <el-form-item style="font-weight: bold;margin-top:120px;" label="文章作者" prop="sysStaff">
          <el-input v-model="temp.sysStaff.name" disabled="disabled" style="width:50%" />
        </el-form-item>
        <el-form-item style="font-weight: bold;" label="创建时间" prop="createTime">
          <el-date-picker
            disabled="disabled"
            style="width: 50%"
            type="date"
            v-model="temp.createTime"
            :format="'yyyy-MM-dd HH:mm:ss'"
          ></el-date-picker>
        </el-form-item>
        <el-form-item style="margin-top:10px"></el-form-item>
        </div>
      </el-form>
      </el-main>
    </div>
  </div>
</template>

<script>
import {
  add,
  update,
  list,
  deletesystemmessage,
    imageFile
} from "@/api/systemnotice/systemmessage";
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
      tableKey: 0,
      list: [], // 后台返回，给数据表格展示的数据
      total: 0, // 总记录数
      listLoading: true, // 是否使用动画
      pageNum: 1, // 分页需要的当前页
      pageRow: 10, // 分页需要的每页显示多少
      articletitle: "",
      describe: "",
      temp: {
        // 添加、修改时绑定的表单数据
        aid: undefined,
        articletitle: "",
        attribute: "",
        articlesource: "",
        describe: "",
        content: "",
        sysStaff: {
          name: "",
          sid: 0
        },
        picture: "",
        picturename: "",
        createTime: new Date()
      },
      checkList:[],
      i: 0,
      title: "添加", // 对话框显示的提示 根据dialogStatus create
      dialogStatus: "", // 表示表单是添加还是修改的
      rules: {
        // 校验规则
        //dTitle:  [{ required: true, message: '标题必填', trigger: 'blur' }],
        //dFileName: [{ required: true, message: '请上传文件', trigger: 'change'}]
        articletitle: [
          {
            required: true,
            message: "标题不能为空",
            trigger: ["blur", "change"]
          }
        ],
        /* articlesource: [
          {
            required: true,
            message: "文章来源不能为空",
            trigger: ["blur", "change"]
          }
        ], */
        describe: [
          {
            required: true,
            message: "文章描述不能为空",
            trigger: ["change"]
          }
        ]
      },
      fileList: [],
      file:{},
      isShow: false,
      wew: {},
      multipleSelection: [],
      deleteid: [],
      formData:null,
      aginp:''
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
      if (!this.hasPerm("systemnotice:list")) {
        return;
      }
      // 开始转圈圈
      this.listLoading = true;
      // debugger // 调试
      /* let data=qs.stringify({
          account: this.listQuery.account
        }) */
      list(this.pageNum, this.pageRow, this.articletitle, this.describe).then(
        response => {
          this.list = response.records;
          this.total = response.total;
          console.debug(this.list);
          // 转圈圈结束
          this.listLoading = false;
        }
      );
    },
    resetSou() {
      this.articletitle = "";
      this.describe = "";
    },
    // 重置表单数据
    resetTemp() {
      this.temp = {
          // 添加、修改时绑定的表单数据
          aid: undefined,
          articletitle: "",
          attribute: "",
          aticlesource: "",
          describe: "",
          content: "",
          sysStaff: {
            name: "",
            sid: 0
          },
          picture: "",
          picturename: "",
          createTime: new Date()
      };
      this.formData=null
      this.checkList=[]
      this.fileList=[]
      this.file={}
      this.aginp=''
    },
    // 显示添加的对话框
    handleCreate() {
      // 重置表单数据
      this.resetTemp();
      this.xianshi();
      this.temp.sysStaff.name=this.nickname
        this.temp.sysStaff.sid=this.userId
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
      if (!this.hasPerm('systemnotice:add')) {
          return
        }
        this.temp.attribute=''
        this.checkList.filter(val=>{
            this.temp.attribute+=val+','
        })
        this.temp.attribute = this.temp.attribute.substring(
            0,
            this.temp.attribute.length - 1
          );
        // 表单校验
        this.$refs['dataForm'].validate((valid) => {
          // 所有的校验都通过
              if (valid) {
                  if(this.fileList.length!==0){
                      imageFile(this.formData).then((response)=>{
          this.temp.picture=response.dFile
            this.isShow=true
            let posdata=qs.stringify({
              articletitle: this.temp.articletitle,
            attribute: this.temp.attribute,
            picture: this.temp.picture,
            picturename: this.temp.picturename,
            content: this.temp.content,
            articlesource: this.temp.articlesource,
            describe: this.temp.describe,
            createId: this.temp.sysStaff.sid
          })
            // 调用api里的sys里的user.js的ajax方法
            add(posdata).then((response) => {

              // 关闭对话框
              this.dialogFormVisible = false
              // 刷新数据表格里的数据
              this.getList()
              // 显示一个通知
              this.$notify({
                title: '成功',
                message: '新增成功',
                type: 'success',
                duration: 2000
              })
              this.isShow=false
              this.yincang()
              this.resetTemp()
          })
          })
                  }else{
                    let posdata=qs.stringify({
              articletitle: this.temp.articletitle,
            attribute: this.temp.attribute,
            picture: this.temp.picture,
            picturename: this.temp.picturename,
            content: this.temp.content,
            articlesource: this.temp.articlesource,
            describe: this.temp.describe,
            createId: this.temp.sysStaff.sid
          })
                      add(posdata).then((response) => {

              // 关闭对话框
              this.dialogFormVisible = false
              // 刷新数据表格里的数据
              this.getList()
              // 显示一个通知
              this.$notify({
                title: '成功',
                message: '新增成功',
                type: 'success',
                duration: 2000
              })
              this.isShow=false
              this.yincang()
              this.resetTemp()
          })
                  }

          }

        })
    },
    // 显示修改对话框
    handleUpdate(row) {
        this.aginp=row.picturename;
      this.temp = row;
      this.xianshi();
      this.checkList=this.temp.attribute.split(",")
      this.temp.createTime = row.createTime;
      if(row.picture!==''&&row.picture!==null){
        this.fileList = [{ name: row.picturename, url:this.virtualimgIp+row.picture }];
      }else{
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
        if (!this.hasPerm("systemnotice:update")) {
        return;
      }
        this.$refs["dataForm"].validate(valid => {
        // 表单校验通过
        if (valid) {
             this.temp.attribute=''
        this.checkList.filter(val=>{
            this.temp.attribute+=val+','
        })
        this.temp.attribute = this.temp.attribute.substring(
            0,
            this.temp.attribute.length - 1
          );
            if(this.fileList.length!==0&&this.aginp !== this.fileList[0].name&&this.temp.picturename!==''&&this.temp.picturename!==null){
                imageFile(this.formData).then(response => {
              this.temp.picture = response.dFile;
              console.debug(this.temp);
              this.isShow = true;
              // 调用api里的sys里的user.js的ajax方法
              let posdata=qs.stringify({
              aid: this.temp.aid,
            articletitle: this.temp.articletitle,
            attribute: this.temp.attribute,
            picture: this.temp.picture,
            picturename: this.temp.picturename,
            content: this.temp.content,
            articlesource: this.temp.articlesource,
            describe: this.temp.describe
          })
              update(posdata).then(response => {
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
            }else if(this.fileList.length===0&&this.aginp !==''&&this.aginp !==null){
                this.temp.picturename=''
                this.temp.picture=''
                let posdata=qs.stringify({
              aid: this.temp.aid,
            articletitle: this.temp.articletitle,
            attribute: this.temp.attribute,
            picture: this.temp.picture,
            picturename: this.temp.picturename,
            content: this.temp.content,
            articlesource: this.temp.articlesource,
            describe: this.temp.describe
          })
                update(posdata).then(response => {
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
                this.temp.picturename='1'
                let posdata=qs.stringify({
              aid: this.temp.aid,
            articletitle: this.temp.articletitle,
            attribute: this.temp.attribute,
            picture: this.temp.picture,
            picturename: this.temp.picturename,
            content: this.temp.content,
            articlesource: this.temp.articlesource,
            describe: this.temp.describe
          })
                update(posdata).then(response => {
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
            })
            }
        }
        })

    },
    out() {
      this.yincang();
    },
    handleDelete() {
        if (!this.hasPerm("systemnotice:delete")) {
        return;
      }
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
          title = this.multipleSelection[0].articletitle;
          this.deleteid.push(this.multipleSelection[0].aid);
        } else {
          title = "选中";
          this.multipleSelection.filter(row => {
            this.deleteid.push(row.aid);
          });
        }
        this.$confirm("确认删除【" + title + "】的信息吗?", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          // 调用ajax去后台删除
          console.debug(this.deleteid);
          deletesystemmessage(this.deleteid).then(response => {
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
          this.deleteid = [];
        });
      }
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
    xianshi() {
      this.dis = "none";
      this.dis2 = "inline-block";
    },
    yincang() {
      this.dis = "inline-block";
      this.dis2 = "none";
      this.sid = null;
      this.getList();
    },
    handleImgChange1(file, fileList, name) {
      const isLt2M = file.size / 1024/1024  < 5;
      if (!isLt2M) {
        this.$message({
          showClose: true,
          message: "文件不能超过5M",
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
      this.temp.picturename = this.file.name;
    },
    fileRemove(file, fileList) {
        console.debug("asdf")
      this.file = {};
      this.temp.picturename = '';
       this.temp.picture = '';
      this.fileList=[]
    },
    getValue(val){
        console.debug(val)
    }
  },
  filters: {
    getContent(val) {
      /* val.replace(/<\/?[^>]*>/g, ""); */
      if(val.length>20){
          return val.replace(/<\/?[^>]*>/g, "").slice(0, 20) + "......";
      }else{
          return val.replace(/<\/?[^>]*>/g, "");
      }

    }
  }
};
</script>
