<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input
        v-model="listQuery.title"
        placeholder="部门名"
        style="width: 200px;"
        class="filter-item"
        @keyup.enter.native="handleFilter"
      />
      <el-input
        v-model="listQuery.fzr"
        placeholder="负责人"
        style="width: 200px;"
        class="filter-item"
        @keyup.enter.native="handleFilter"
      />
      <!-- <el-select
        v-model="listQuery.importance"
        placeholder="部门状态"
        clearable
        style="width: 150px"
        class="filter-item"
      >-->
      <!-- <el-option v-for="item in importanceOptions" :key="item" :label="item" :value="item" />
      </el-select>-->

      <el-button class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">搜索</el-button>
      <el-button class="filter-item" type="primary" icon="el-icon-search" @click="reset">重置</el-button>
      <el-button
        class="filter-item"
        style="margin-left: 10px;"
        type="primary"
        icon="el-icon-edit"
        @click="handleCreate"
      >添加</el-button>
      <el-button
        class="filter-item"
        style="margin-left: 10px;"
        type="danger"
        v-if="hasPerm('permission:delete')"
        icon="el-icon-edit"
        @click="handleDelete()"
      >删除</el-button>
    </div>

    <el-table
      :key="tableKey"
      @selection-change="selectChange"
      v-loading="listLoading"
      :data="list"
      style="width: 100%;"
    >
      <el-table-column prop="mid" type="selection" width="55"></el-table-column>
      <el-table-column
        label="序号"
        type="index"
        fit
        :index="indexMethod"
        highlight-current-row
        align="center"
        :class-name="getSortClass('id')"
      ></el-table-column>
      <el-table-column align="center" prop="mechanismName" label="部门名">
        <template slot-scope="scope">
          <el-tooltip
            v-if="hasPerm('mechanism:update')&&scope.row.staus=='正常'"
            content="点击查看详情或修改"
            placement="right"
            effect="dark"
          >
            <a
              @click="handleUpdate(scope.row)"
              target="_blank"
              class="buttonText"
              style="color: #1890ff"
            >{{scope.row.mechanismName}}</a>
          </el-tooltip>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="sname" label="负责人"></el-table-column>
      <el-table-column align="center" prop="parentName" label="父级部门"></el-table-column>
      <el-table-column align="center" prop="createTime" label="创建时间"></el-table-column>
      <el-table-column align="center" prop="createName" label="创建人"></el-table-column>
      <el-table-column align="center" prop="staus" label="状态"></el-table-column>
      <!-- <el-table-column
        v-if="hasPerm('permission:delete')&&row.staus!='正常'"
        label="操作"
        align="center"
        width="230"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="{row,$index}">
          <el-button
            v-if="hasPerm('mechanism:delete')"
            size="mini"
            :type="row.staus=='正常'?'success':'恢复'"
            @click="handleDelete(row,$index)"
          >
            <span>{{row.staus=='正常'?'删除':'恢复'}}</span>
          </el-button>
        </template>
      </el-table-column>-->
    </el-table>
    <el-pagination
      @current-change="handleCurrentChange"
      :current-page="listQuery.page"
      :page-size="listQuery.limit"
      :total="total"
    ></el-pagination>
    <!-- style="width:75%" -->
    <el-dialog
      @close="closefase"
      :title="textMap[dialogStatus]"
      width="40%"
      :visible.sync="dialogFormVisible"
    >
      <!-- style="width: 400px; margin-left:50px;" -->
      <el-form ref="dataForm" :rules="rules" :model="temp" label-position="right" label-width="30%">
        <el-form-item label="所属部门" prop="defaultvalue">
          <!-- //temp.parent -->
          <el-cascader
            style="width:60%;margin-left:10px"
            v-model="defaultvalue"
            placeholder="该部门所属的部门"
            :props="props"
            @change="Change"
            :show-all-levels="false"
            :options="bm"
          ></el-cascader>
        </el-form-item>
        <el-form-item label="是否为单位" prop="sort">
          <el-radio-group v-model="temp.sort">
            <el-radio :label="0">非单位</el-radio>
            <el-radio :label="1">单位</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="部门名称" prop="menuName">
          <el-input style="width:60%;margin-left:10px" v-model="temp.menuName" placeholder="部门名称" />
        </el-form-item>
        <el-form-item label="部门负责人部门" prop="defaultvalue">
          <!-- //temp.parent -->
          <el-cascader
            style="width:60%;margin-left:10px"
            placeholder="部门负责人部门"
            v-model="bmfzr"
            :props="props"
            @change="MChange"
            :show-all-levels="false"
            :options="bm"
          ></el-cascader>
        </el-form-item>
        <el-form-item label="负责人" prop="region">
          <el-select style="width:60%;margin-left:10px" @change="abc" v-model="temp.region" placeholder="负责人">
            <el-option
              v-for="(item,index) in staff"
              :label="item.name"
              :value="item.sid"
              :key="index"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer" align="center">
        <el-button type="primary" @click="dialogStatus==='create'?createData():updateData()">提交</el-button>
        <el-button @click="clicen">取消</el-button>
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
  { key: "EU", display_name: "Eurozone" },
];
export default {
  data() {
    return {
      deleteList: undefined,
      bmfzr: 1,
      defaultvalue: [1],
      props: {
        value: "mid",
        label: "mechanismName",
        children: "chilrenMechanism",
        emitPath: false,
        expandTrigger: "click",
        checkStrictly: true,
      },
      placeholder: "",
      tableKey: 0,
      list: [],
      total: 0,
      listQuery: {
        page: 1,
        limit: 10,
        importance: "正常",
        title: "",
        fzr: "",
        type: undefined,
        sort: "+index",
      },
      ld: [],
      importanceOptions: ["正常", "删除"],
      calendarTypeOptions,
      sortOptions: [
        { label: "ID Ascending", key: "+index" },
        { label: "ID Descending", key: "-index" },
      ],
      statusOptions: ["published", "draft", "deleted"],
      showReviewer: false,
      temp: {
        id: undefined,
        menuName: "",
        sort: undefined,
        menuCode: "",
        region: 0,
        parent: [],
        ld: "",
        status: "published",
      },
      listLoading: false,
      dialogFormVisible: false,
      dialogStatus: "",
      textMap: {
        update: "修改部门",
        create: "新增部门",
      },
      staff: [],
      parentasd: [],
      Excel: "",
      dialogPvVisible: true,
      pvData: [],
      rules: {
        sort: [
          {
            required: true,
            message: "请至少选择一项",
            trigger: ["change"],
          },
        ],
        menuName: [
          {
            required: true,
            message: "部门名称不能为空",
            trigger: ["blur"],
          },
        ],
        region: [
          {
            required: true,
            message: "请选择负责人",
            trigger: "blur",
          },
        ],
        /* defaultvalue:[{
          required:true,message:"请选择父级部门",trigger:'change'
        }], */
        ld: [
          {
            required: true,
            message: "请选择分管领导",
            trigger: "change",
          },
        ],
      },
      bm: [],
      downloadLoading: false,
    };
  },
  created() {
    this.temp.sort = 1;
    this.getList();
    this.$nextTick(() => {
      // 以服务的方式调用的 Loading 需要异步关闭
      //this.getAllStaff();
      this.getSysmechanismAll();
    });
  },
  methods: {
    abc(val){
      console.log("abc",val)
      this.$set(this.temp, this.temp.region, val)
      // this.temp.region=val
    },
    selectChange(val) {
      console.log(val);
      this.deleteList = val;
      console.log(this.deleteList);
    },
    reset() {
      this.listQuery = {
        page: 1,
        limit: 10,
        importance: "正常",
        title: "",
        fzr: "",
        type: undefined,
        sort: "+index",
      };
    },
    closefase() {
      
      console.log("123");
      // this.temp={}
      this.bmfzr=undefined
      this.temp.region=undefined
      this.placeholder = "";
      this.defaultvalue = 1;
      this.getSysmechanismAll()
      this.bmfzr=1
    },
    clicen() {
      this.dialogFormVisible = false;
      this.getSysmechanismAll();
      this.getList();
    },
    ldchange(val) {
      console.log(val);
    },
    getCheckedNodes(val) {
      console.log(val);
    },
    handleCurrentChange(val) {
      //改变页码
      this.listQuery.page = val;
      this.getList();
    },
    getList() {
      // 查询列表
      if (!this.hasPerm("mechanism:list")) {
        return;
      }
      this.listLoading = true;
      this.api({
        url:
          "sysmechanism/get/" +
          this.listQuery.page +
          "/" +
          this.listQuery.limit,
        method: "get",
        params: {
          mechanism: this.listQuery.title,
          principal: this.listQuery.fzr,
          staus: this.listQuery.importance,
        },
      }).then((response) => {
        console.log(response);
        this.total = response.count;
        this.list = [];
        response.data.filter((item, index) => {
          this.list.push(item);
        });
        console.log(this.list);
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
        type: "success",
      });
      row.status = status;
    },
    indexMethod(val) {
      return ++val;
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
        type: "",
      };
    },
    getAllStaff(mid) {
      this.api({
        url: "SysStaff/gets/10/" + mid,
        method: "get",
      }).then((res) => {
        console.log(res, "负责人列表");
        this.staff = [];
        res.filter((item) => {
          this.staff.push(item);
        });
      });
    },
    getAllld(mid) {
      this.api({
        url: "SysStaff/gets/7/" + mid,
        method: "get",
      }).then((res) => {
        console.log(res, "负责人列表");
        this.ld = [];
        res.filter((item) => {
          this.ld.push(item);
        });
      });
    },
    MChange(val) {
      console.log(val);
      this.temp.region = undefined;
      this.getAllStaff(val);
    },
    Change(val) {
      this.defaultvalue = [];
      this.defaultvalue.push(val);
      //  this.getAllStaff(val)
      //  this.getAllld(val)
    },
    getSysmechanismAll(val = 0) {
      this.api({
        url: "sysmechanism/get",
        method: "get",
      }).then((res) => {
        console.log("sysmechanism", res);
        // this.bm.concat(res)
        this.bm = [];
        res.filter((item) => {
          this.bm.push(item);
        });
        this.getAllStaff(res[0].mid);
        // this.getAllld(res[0].mid)
        console.log(this.bm);
      });
    },
    handleCreate() {
      this.resetTemp();
      this.dialogStatus = "create";
      this.dialogFormVisible = true;
      this.$nextTick(() => {
        this.$refs["dataForm"].clearValidate();
      });
    },
    createData() {
      this.$refs["dataForm"].validate((valid) => {
        if (valid) {
          this.api({
            url: "sysmechanism/add",
            method: "post",
            params: {
              mechanismName: this.temp.menuName,
              sid: this.temp.region,
              sort: this.temp.sort,
              parent: this.defaultvalue[0],
              branch: this.temp.ld,
              createId: store.getters.userId,
            },
          }).then((res) => {
            this.getSysmechanismAll();
            this.getList();
            this.temp.parent = this.defaultvalue;
            console.log(res);
            this.$notify({
              title: "成功",
              message: "新增成功",
              type: "success",
              duration: 2000,
            });
            this.dialogFormVisible = false;
          });
        }
      });
    },
    handleUpdate(row) {
      console.log("row", row);
      this.temp.id = row.mid;
      this.temp.menuName = row.mechanismName; // = Object.assign({}, row); // copy obj
      this.temp.region = row.sid;
      this.defaultvalue = [];
      this.defaultvalue = row.parent;
      console.log("this.defaultvalue", this.defaultvalue);
      // this.placeholder = row.parentName;
      this.temp.ld = row.branch;
      console.log(this.temp);
      // bmfzr
      this.api({
        url: "sysmechanism/getmname/" + this.temp.region,
        method: "get",
      }).then((res) => {
        console.log("部门Id:", res);
        this.bmfzr = res;
        this.getAllStaff(res);
      });
      this.temp.timestamp = new Date(this.temp.timestamp);
      this.dialogStatus = "update";
      this.dialogFormVisible = true;

      this.$nextTick(() => {
        this.$refs["dataForm"].clearValidate();
      });
    },
    updateData() {
      this.$refs["dataForm"].validate((valid) => {
        if (valid) {
          console.log("this.temp", this.temp);
          console.log(this.defaultvalue, "this.defaultvalue123");
          this.api({
            url: "sysmechanism/update",
            method: "put",
            params: {
              mid: this.temp.id,
              mechanismName: this.temp.menuName,
              sid: this.temp.region,
              parent: this.defaultvalue,
              branch: this.temp.ld,
            },
          }).then((res) => {
            this.getList();
            this.$notify({
              title: "成功",
              message: "修改成功",
              type: "success",
              duration: 2000,
            });
            this.dialogFormVisible = false;
          });
        }
      });
    },
    delete(mid) {
      this.api({
        url: "sysmechanism/del/" + mid,
        method: "delete",
      }).then((res) => {
        console.log(res);
        if (res === 1) {
          this.getList();
          this.$notify({
            title: "成功",
            message: "",
            type: "success",
            duration: 2000,
          });
        } else {
          this.$notify({
            title: "失败",
            message: "请先将与该部门相关联的数据删除",
            type: "error",
            duration: 2000,
          });
        }
      });
    },
    handleDelete() {
      console.log("deletelist", this.deleteList);
      this.$alert("是否确定删除", "提示", {
        showCancelButton: true,
        showConfirmButton: true,
        closeOnPressEscape: false,
        callback: (action) => {
          console.log(action, "this.$alert");
          if (action == "confirm") {
            this.deleteList.filter(item=>{
              console.log(item)
              this.delete(item.mid)
            })
          }
        },
      });
      this.getSysmechanismAll();
    },
    handleFetchPv(pv) {
      //   fetchPv(pv).then(response => {
      //     this.pvData = response.data.pvData;
      //     this.dialogPvVisible = true;
      //   });
    },
    handleDownload() {
      window.location.href = "http://localhost:8080/export/mechanism";
    },
    formatJson(filterVal) {
      return this.list.map((v) =>
        filterVal.map((j) => {
          if (j === "timestamp") {
            return parseTime(v[j]);
          } else {
            return v[j];
          }
        })
      );
    },
    getSortClass: function (key) {
      const sort = this.listQuery.sort;
      return sort === `+${key}` ? "ascending" : "descending";
    },
  },
};
</script>

<style>
</style>