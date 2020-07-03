<template>
  <div>
    <el-form :inline="true" class="demo-form-inline">
      <div align="center" style="margin-top: 30px">
        <el-form-item label="名称">
          <el-input
            v-model="search.proName"
            class="search_name"
            placeholder="请输入风险流程名称">
          </el-input>
        </el-form-item>
        <el-form-item label="风险流程时间">
          <el-date-picker v-model="search.proYear" placeholder="请选择风险流程时间" type="date"
                          style="width: 300px"></el-date-picker>
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
          prop="proid"
          label="序号">
        </el-table-column>
        <el-table-column
          prop="processId"
          label="风险流程编号">
        </el-table-column>
        <el-table-column
          prop="proName"
          label="风险流程名称">
          <template slot-scope="scope">
            <a @click="handleEdit(scope.$index, scope.row)"
               target="_blank"
               class="buttonText" style="color: #1890ff">{{scope.row.proName}}</a>
          </template>
        </el-table-column>
        <el-table-column
          prop="proYear"
          label="风险流程时间"
          :formatter="dateFormat">
        </el-table-column>
        <el-table-column
          prop="proAccessoryName"
          label="附件名称">
        </el-table-column>
        <el-table-column
          prop="proCreateName"
          label="创建人">
        </el-table-column>
        <el-table-column
          prop="proCreateTime"
          label="创建时间"
          :formatter="dateFormat">
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
      <el-dialog :title="dialogTitle" width="55%" :visible.sync="iconFormVisible">
        <el-form :inline="true" :model="rickInfo" label-width="120px" width="240px">
          <el-input v-model="rickInfo.proid" placeholder="序号" type="hidden"></el-input>
          <el-form-item label="风险流程名称">
            <el-input style="width: 400px" v-model="rickInfo.proName" placeholder="请输入风险流程名称" width="220px"></el-input>
          </el-form-item>
          <el-form-item label="附件相关" prop="proAccessoryName">
            <el-upload
              style="width:100%"
              class="upload-demo"
              v-model="rickInfo.proAccessoryName"
              ref="upload"
              action="https://localhost:8080/imp/importDuty"
              :on-remove="fileRemove"
              :on-change="handleImgChange1"
              accept=".zip,.rar,.doc,.xls,.xlsx,.ppt,.pptx,.docx,.txt,.pdf"
              :file-list="fileList"
              :limit="2"
              :auto-upload="false"
            >
              <el-button slot="trigger" class="el-icon-upload" size="small" type="primary">选取文件</el-button>
              <div slot="tip" class="el-upload__tip">建议上传5M以下的文件，文件类型支持格式:zip,rar,doc,xls,xlsx,ppt,pptx,docx,txt,pdf
              </div>
            </el-upload>
          </el-form-item>
          <el-form-item label="风险流程时间">
            <el-date-picker v-model="rickInfo.proYear" placeholder="风险流程年份" type="date"
                            style="width: 400px"></el-date-picker>
          </el-form-item>
          <!--<el-form-item label="风险流程编号">-->
          <!--<el-input style="width: 300px" v-model="rickInfo.processId" placeholder="请输入风险流程编号"></el-input>-->
          <!--</el-form-item>-->
          <el-form-item style="margin-bottom:70px" label="风险流程信息">
            <quill-editor
              class="editor"
              ref="myQuillEditor"
              style="height:200px"
              v-model="rickInfo.proInfomation"
            ></quill-editor>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="deselect()">返回</el-button>
          <el-button type="primary" @click="submitUser()">保存</el-button>
        </div>
      </el-dialog>
    </el-form>
  </div>
</template>

<script>
  import {add, del, findBy, impFile, list, update} from '@/api/risk/processRick'
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
      /* this.getAllPost();
       this.getAllMechanism()*/
    },
    methods: {
      fileRemove(file, fileList) {
        this.file = {};
        this.rickInfo.proAccessoryName = '';
        this.rickInfo.proAccessory = '';
        this.fileList = []
      },
      handleImgChange1(file, fileList, name) {
        const isLt2M = file.size / 1024 < 5000;
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
        this.rickInfo.proAccessoryName = this.file.name;
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
        return moment(date).format("YYYY-MM-DD")
      },
      onrest() {
        this.search = {
          proName: undefined,
          proYear: undefined
        }
      },
      initList() {
        list().then(response => {
          this.tableData = response.list
          this.total = response.list.length
        })

      },
      // 修改
      handleEdit(index, row) {
        this.fileList=[];
        this.dialogTitle = '修改';
        this.rickInfo = row;
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
        if (this.search.proYear != undefined) {
          var dateee = new Date(this.search.proYear).toJSON();
          this.search.proYear = new Date(+new Date(dateee) + 8 * 3600 * 1000).toISOString().split("T")[0];
        }

        let postData = qs.stringify({
          proName: this.search.proName,
          proYear: this.search.proYear
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
            message: '请至少选择一行进行删除',
            type: 'success',
            duration: 2000
          })
        }
        else {
          var bool = confirm("是否确认删除？");
          if (!bool) {
            return;
          }
          let postData = qs.stringify({
            test: JSON.stringify(data)
          });
          console.log("删除数据" + postData);
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
      handleSizeChange(size) {
        this.pageSize = size;
      },
      handleCurrentChange(currentPage) {
        this.currentPage = currentPage;
      },
      //新增
      add() {
        this.fileList=[];
        this.dialogTitle = '增加';
        this.rickInfo = {};
        this.iconFormVisible = true;
      },
      //弹窗取消
      deselect() {
        this.iconFormVisible = false
        clearInterval(this.timer)
      },
      // 弹窗确定
      submitUser() {

        if (this.dialogTitle === '增加') {
          impFile(this.formData).then(response => {
            this.rickInfo.proAccessory = response.dFile;
            var dateee = new Date(this.rickInfo.proYear).toJSON();
            var date = new Date(+new Date(dateee) + 8 * 3600 * 1000).toISOString().replace(/T/g, " ").replace(/\.[\d]{3}Z/, "");
            this.rickInfo.proYear = date;

            let postData = qs.stringify({
              proid: this.rickInfo.proid,
              proName: this.rickInfo.proName,
              proYear: this.rickInfo.proYear,
              proInfomation: this.rickInfo.proInfomation,
              proAccessory: this.rickInfo.proAccessory,
              proCreateTime: this.rickInfo.proCreateTime,
              proCreateId: this.rickInfo.proCreateId,
              proCreateName: this.rickInfo.proCreateName,
              proStatus: this.rickInfo.proStatus,
              proAccessoryName: this.rickInfo.proAccessoryName
            });

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
          })

        }
        if (this.dialogTitle === '修改') {
          impFile(this.formData).then(response => {
            this.rickInfo.proAccessory = response.dFile;
            var dateee = new Date(this.rickInfo.proYear).toJSON();
            var date = new Date(+new Date(dateee) + 8 * 3600 * 1000).toISOString().replace(/T/g, " ").replace(/\.[\d]{3}Z/, "");
            this.rickInfo.proYear = date;

            let postData = qs.stringify({
              proid: this.rickInfo.proid,
              processId: this.rickInfo.processId,
              proName: this.rickInfo.proName,
              proYear: this.rickInfo.proYear,
              proInfomation: this.rickInfo.proInfomation,
              proAccessory: this.rickInfo.proAccessory,
              proCreateId: this.rickInfo.proCreateId,
              proCreateName: this.rickInfo.proCreateName,
              proStatus: this.rickInfo.proStatus,
              proAccessoryName: this.rickInfo.proAccessoryName
            });

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
          })
        }
      }
    },
    data() {
      return {
        timer: '',
        iconFormVisible: false,
        rickInfo: {},
        userInfo: {},
        dialogTitle: '增加',
        rowIndex: null,
        search: {
          proName: undefined,
          proYear: undefined
        },
        currentPage4: 1,
        pageSize: 5,
        total: 0,
        postList: [],
        mechanismList: [{}],
        currentPage: 1,
        isShowSelect: false,
        tableData: [{
          proid: undefined,
          processId: undefined,
          proName: undefined,
          proYear: undefined,
          proInfomation: undefined,
          proAccessory: undefined,
          proCreateTime: undefined,
          proCreateId: undefined,
          proCreateName: undefined,
          proStatus: undefined,
          proAccessoryName: undefined
        }],
        listQuery: {
          page: 1,
          limit: 5
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
          sysMechanism: {
            mid: 0,
            mechanismName: ''
          },
          newTime: new Date(),
          dnumId: "",
          status: 1,
          dstatus: "",
          dutyContent: '',
          gettop: '',
          cid: 0
        },
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
          tongbao: ''
        },
        fileList: []
      }
    }
  }
</script>
