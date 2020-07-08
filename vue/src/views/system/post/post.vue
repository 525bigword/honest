<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input
        v-model="listQuery.name"
        placeholder="角色名称"
        style="width: 200px;"
        class="filter-item"
        @keyup.enter.native="handleFilter"
      />
      <!-- <el-form-item label="部门"> -->
      <!-- //temp.parent -->
      <!-- <el-cascader
        placeholder="部门"
        :props="props"
        @change="Change"
        :show-all-levels="false"
        :options="bm"
      ></el-cascader>
      <!-- </el-form-item> 
      <el-input
        v-model="listQuery.message"
        placeholder="部门描述"
        style="width: 200px;"
        class="filter-item"
        @keyup.enter.native="handleFilter"
      /> -->
      <el-button class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">搜索</el-button>
      <el-button
        v-if="hasPerm('post:add')"
        class="filter-item"
        style="margin-left: 10px;"
        type="primary"
        icon="el-icon-edit"
        @click="handleCreate"
      >添加</el-button>
      <el-button
        v-if="hasPerm('post:delete')"
        class="filter-item"
        style="margin-left: 10px;"
        type="danger"
        icon="el-icon-edit"
        @click="handleDelete"
      >删除</el-button>
      <el-button
        :loading="downloadLoading"
        class="filter-item"
        type="primary"
        icon="el-icon-download"
        @click="handleDownload"
      >导出</el-button>
      <!-- <el-checkbox
        v-model="showReviewer"
        class="filter-item"
        style="margin-left:15px;"
        @change="tableKey=tableKey+1"
      >reviewer</el-checkbox>-->
    </div>

    <el-table
      @selection-change="toggleRowExpansion"
      :key="tableKey"
      v-loading="listLoading"
      :data="list"
      style="width: 100%;"
    >
      <el-table-column prop="pid" type="selection" width="55"></el-table-column>
      <el-table-column
        label="序号"
        type="index"
        :index="indexMethod"
        prop="index"
        align="center"
        :class-name="getSortClass('id')"
      ></el-table-column>
      <el-table-column align="center" prop="pname" label="岗位名称"></el-table-column>

      <el-table-column align="center" prop="message" label="岗位描述"></el-table-column>

      <el-table-column align="center" prop="createname" label="创建人"></el-table-column>

      <el-table-column align="center" prop="createTime" label="创建时间"></el-table-column>

      <el-table-column align="center" prop="staus" label="状态"></el-table-column>
      <el-table-column
        v-if="hasPerm('post:delete')"
        label="操作"
        align="center"
        width="230"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="{row,$index}">
          <el-button
            v-if="hasPerm('post:update')"
            size="mini"
            type="primary"
            @click="tree(row,$index)"
          >修改</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      @current-change="handleCurrentChange"
      :current-page="listQuery.page"
      :page-size="listQuery.limit"
      :total="total"
    ></el-pagination>

    <el-dialog
      @close="dialogClose"
      :title="textMap[dialogStatus]"
      :visible.sync="dialogFormVisible"
    >
      <el-form
        ref="dataForm"
        :rules="rules"
        :model="temp"
        label-position="left"
        label-width="70px"
        style="width: 400px; margin-left:50px;"
      >
        <el-form-item label="岗位名称" class="link-type" prop="pname">
          <el-input v-model="temp.pname" placeholder="岗位名称" />
        </el-form-item>
        <el-form-item label="所属部门">
          <el-cascader
            :placeholder="placeholder"
            v-model="temp.defaultvalue"
            :props="props"
            @change="Change"
            :show-all-levels="false"
            :options="bm"
          ></el-cascader>
        </el-form-item>
        <el-form-item label="岗位描述" prop="message">
          <el-input v-model="temp.message" placeholder="栏目码" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="dialogStatus==='create'?createData():updateData()">提交</el-button>
        <el-button @click="dialogFormVisible = false">取消</el-button>
      </div>
    </el-dialog>
    <!-- 树形层 -->
    <el-dialog
      @closed="treeClose"
      :title="dialogStatus==='create'?'添加':'修改'"
      :fullscreen="true"
      :visible.sync="treeDisable"
    >
      <div align="left">
        <el-row>
          <el-col :span="10">
            <el-form
              ref="dataForm"
              :rules="rules"
              :model="temp"
              label-position="left"
              label-width="70px"
              style="width: 400px; margin-left:50px;"
            >
              <el-form-item label="角色名" class="link-type" prop="pname">
                <el-input v-model="temp.pname" placeholder="角色名" />
              </el-form-item>
              <!-- <el-form-item label="所属部门">
               
                <el-cascader
                  style="width:100%"
                  :placeholder="placeholder"
                  v-model="temp.defaultvalue"
                  :props="props"
                  @change="Change"
                  :show-all-levels="false"
                  :options="bm"
                ></el-cascader>
              </el-form-item>-->
              <el-form-item label="角色描述" prop="message">
                <el-input v-model="temp.message" placeholder="角色描述" />
              </el-form-item>

              <!-- <div slot="footer" class="dialog-footer"> -->
              <el-button
                type="primary"
                @click="dialogStatus==='create'?createData():updateData()"
              >提交</el-button>
              <el-button @click="treeDisable = false">取消</el-button>
              <!-- </div> -->
            </el-form>
          </el-col>
          <el-col :span="10">
            <el-tree
              width="50%"
              :data="treedata"
              show-checkbox
              node-key="id"
              ref="tree"
              @check-change="treecheck"
              v-model="default_checked"
              :default-checked-keys="default_checked"
              :props="treeProps"
            ></el-tree>
          </el-col>
        </el-row>
      </div>
    </el-dialog>
  </div>
</template> 

<script>
import store from "../../../store";
const calendarTypeOptions = [
  { key: "CN", display_name: "China" },
  { key: "US", display_name: "USA" },
  { key: "JP", display_name: "Japan" },
  { key: "EU", display_name: "Eurozone" }
];
export default {
  data() {
    return {
      treeProps: {
        label: "menuName",
        children: "sysPermissions"
      },
      default_checked: [],
      treedata: [],
      treeDisable: false,
      props: {
        value: "mid",
        label: "mechanismName",
        children: "chilrenMechanism",
        emitPath: false,
        expandTrigger: "click",
        checkStrictly: true,
        collapseTags: true,
        clearable: true
      },
      deletelist: [],
      placeholder: "所属部门",
      bm: [],
      radio: "",
      tableKey: 0,
      list: [],
      total: 0,
      listQuery: {
        page: 1,
        limit: 10,
        importance: "正常",
        bm: [],
        name: "",
        message: "",
        type: undefined,
        sort: "+index"
      },
      importanceOptions: ["正常", "删除"],
      calendarTypeOptions,
      sortOptions: [
        { label: "ID Ascending", key: "+index" },
        { label: "ID Descending", key: "-index" }
      ],
      statusOptions: ["published", "draft", "deleted"],
      showReviewer: false,
      temp: {
        id: undefined,
        message: "",
        pname: "",
        defaultvalue: []
      },
      dialogFormVisible: false,
      dialogStatus: "",
      textMap: {
        update: "修改",
        create: "新增"
      },
      listLoading: true,
      Excel: "",
      dialogPvVisible: false,
      pvData: [],
      rules: {
        type: [
          { required: true, message: "type is required", trigger: "change" }
        ],
        timestamp: [
          {
            type: "date",
            required: true,
            message: "timestamp is required",
            trigger: "change"
          }
        ],
        title: [
          { required: true, message: "title is required", trigger: "blur" }
        ]
      },
      downloadLoading: false
    };
  },
  created() {
    this.getList();
    this.getSysmechanismAll();
    this.getTree();
  },
  methods: {
    submit() {},
    treeClose() {
      console.log(this.default_checked);
      this.default_checked = [];

      // setCheckedKeys
      this.placeholder = "所属部门";
      this.default_checked.filter(value => {
        this.$refs.tree.setChecked(value, false, true); //利用这个方法就可以获取到父节点
      });
      console.log(this.default_checked);
      // this.default_checked
    },
    treecheck(leafOnly, includeHalfChecked) {
      let res = this.$refs.tree.getCheckedNodes(true); //利用这个方法就可以获取到父节点
      console.log(res);
      this.default_checked = [];
      res.filter(i => {
        this.default_checked.push(i.id);
      });
    },
    tree(row) {
      this.dialogStatus = "update";
      console.log(row);
      this.placeholder = row.mname;
      console.log(row);
      this.temp.defaultvalue = [];
      this.temp.defaultvalue.push(row.mid);
      console.log("defaultvalue", this.temp.defaultvalue);
      this.temp = Object.assign({}, row); // copy obj
      this.temp.timestamp = new Date(this.temp.timestamp);

      this.api({
        url: "SysPostPermission/get/" + row.pid,
        method: "get"
      }).then(res => {
        console.log(res);
        this.default_checked = [];
        res.filter(item => {
          this.default_checked.push(item);
        });
        this.$nextTick(() => {
          this.$refs["dataForm"].clearValidate();
        });
        console.log(this.default_checked);

        this.treeDisable = true;
      });
    },
    getTree() {
      this.api({
        url: "SysPostPermission/get",
        method: "get"
      }).then(res => {
        console.log("getTree", res);
        this.treedata = [];
        res.filter(item => {
          this.treedata.push(item);
        });
        console.log(this.treedata);
      });
    },
    toggleRowExpansion(val) {
      console.log("val", val);
      this.deletelist = [];
      val.filter(item => {
        this.deletelist.push(item.pid);
      });
      console.log(this.deletelist);
    },
    dialogClose() {
      console.log("a");
    },
    getSysmechanismAll() {
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
        console.log(this.bm);
      });
    },
    handleCurrentChange(val) {
      //改变页码
      this.listQuery.page = val;
      this.getList();
    },
    getList() {
      //查询列表
      // if (!this.hasPerm('staff:list')) {
      //   return
      // }
      console.log(this.listQuery.bm);
      console.log("this.temp.defaultvalue", this.temp.defaultvalue);
      // let mids = this.listQuery.bm.join(",");
      // console.log(mids);
      this.listLoading = true;
      this.api({
        url: "syspost/get/" + this.listQuery.page + "/" + this.listQuery.limit,
        method: "post",
        data: {
          pname: this.listQuery.name,
          message: this.listQuery.message,
          mids:
            this.temp.defaultvalue.length === 0
              ? this.temp.defaultvalue[0]
              : this.temp.defaultvalue
        }
      }).then(response => {
        console.log("getlist", response);
        this.list = [];
        this.total = response.count;
        response.data.filter(item => {
          let i = {};
          i.pid = item.pid;
          i.pname = item.pname;
          i.mid = item.mid;
          i.mname = item.mname;
          i.message = item.message;
          i.createname = item.createname;
          i.createTime = item.createTime;
          i.staus = item.staus == 1 ? "正常" : "删除";
          this.list.push(i);
        });
        this.listLoading = false;
      });
      //   fetchList(this.listQuery).then(response => {
      //     this.list = response.data.items;
      //     this.total = response.data.total;

      //     // Just to simulate the time of the request
      //     setTimeout(() => {
      //       this.listLoading = false;
      //     }, 1.5 * 1000);
      //   });
    },
    handleFilter() {
      this.listQuery.page = 1;
      this.getList();
    },
    handleModifyStatus(row, status) {
      this.$message({
        message: "操作Success",
        type: "success"
      });
      row.status = status;
    },
    Change(val) {
      console.log(val);
      this.temp.defaultvalue = val;
    },
    sortChange(data) {
      console.log(data);
      const { prop, order } = data;
      if (prop === "index") {
        this.sortByID(order);
      }
    },
    sortByID(order) {
      if (order === "ascending") {
        this.listQuery.sort = "+index";
      } else {
        this.listQuery.sort = "-index";
      }
      this.handleFilter();
    },
    resetTemp() {
      this.temp = {
        id: undefined,
        importance: 1,
        remark: "",
        timestamp: new Date(),
        title: "",
        status: "published",
        type: ""
      };
    },
    handleCreate() {
      this.resetTemp();
      this.dialogStatus = "create";
      this.treeDisable = true;
      this.$nextTick(() => {
        this.$refs["dataForm"].clearValidate();
      });
    },
    createData() {
      this.temp.id = store.getters.userId;
      console.log(this.temp);
      console.log(this.default_checked);
      if (!this.temp.pname ) {
        this.$message({
          type: "error",
          message: "请将信息填写完整"
        });
      } else {
        let arr = this.default_checked.join(",");
        this.api({
          url: "syspost/add",
          method: "post",
          data: {
            pname: this.temp.pname,
            mid: this.temp.defaultvalue,
            message: this.temp.message,
            createId: this.temp.id,
            arr: arr
          }
        }).then(respone => {
          console.log(respone);
          if (respone === 0) {
            this.$message({
              type: "error",
              message: "添加失败"
            });
          } else {
            this.$message({
              type: "success",
              message: "添加成功"
            });
            this.temp.defaultvalue = [];
            this.getList();
            this.treeDisable = false;
          }
        });
      }
    },
    handleUpdate(row) {
      this.placeholder = row.mname;
      console.log(row);
      this.temp.defaultvalue = [];
      this.temp.defaultvalue.push(row.mid);
      console.log("defaultvalue", this.temp.defaultvalue);
      this.temp = Object.assign({}, row); // copy obj
      this.temp.timestamp = new Date(this.temp.timestamp);
      this.dialogStatus = "update";
      this.dialogFormVisible = true;
      this.$nextTick(() => {
        this.$refs["dataForm"].clearValidate();
      });
    },
    updateData() {
      console.log("temp", this.temp);
      console.log("this.default_checked", this.default_checked);
      this.$alert("是否确定修改", "提示", {
        showCancelButton: true,
        showConfirmButton: true,
        closeOnPressEscape: false,
        callback: action => {
          if (action === "confirm") {
            if (!this.temp.pname || !this.temp.pid || !this.temp.mid) {
              this.$message({
                type: "error",
                message: "请将信息填写完整"
              });
            } else {
              let arr = this.default_checked.join(",");
              console.log(arr);
              this.api({
                url: "syspost/update",
                method: "post",
                data: {
                  pid: this.temp.pid,
                  mid: this.temp.mid,
                  pname: this.temp.pname,
                  message: this.temp.message,
                  createId: store.getters.userId,
                  createTime: this.temp.createTime,
                  arr: arr
                }
              }).then(res => {
                this.temp.defaultvalue = [];
                this.getList();
                this.treeDisable = false;
              });
            }
          }
        }
      });
    },
    handleDelete(row, index) {
      console.log(row, index);
      console.log(this.deletelist);
      if (this.deletelist.length < 1) {
        this.$message({
          type: "error",
          message: "请勾选选择需要删除的行"
        });
      } else {
        console.log(this.deletelist);
        this.$alert("是否确定删除", "提示", {
          showCancelButton: true,
          showConfirmButton: true,
          closeOnPressEscape: false,
          callback: action => {
            let arr = this.deletelist.join(",");
            console.log(arr);
            this.api({
              url: "syspost/del",
              method: "post",
              params: {
                arr: arr
              }
            }).then(res => {
              console.log(res);
              if (res === 0) {
                this.$message({
                  type: "error",
                  message: "请保证该岗位没有员工"
                });
                return;
              }
              this.getList();
              // this.getList();
            });
          }
        });
      }
    },
    handleFetchPv(pv) {
      //   fetchPv(pv).then(response => {
      //     this.pvData = response.data.pvData;
      //     this.dialogPvVisible = true;
      //   });
    },
    handleDownload() {
      window.location.href = "http://localhost:8080/export/post";
    },
    formatJson(filterVal) {
      return this.list.map(v =>
        filterVal.map(j => {
          if (j === "timestamp") {
            return parseTime(v[j]);
          } else {
            return v[j];
          }
        })
      );
    },
    getSortClass: function(key) {
      const sort = this.listQuery.sort;
      return sort === `+${key}` ? "ascending" : "descending";
    },
    indexMethod(val) {
      return ++val;
    }
  }
};
</script>

<style>
</style>