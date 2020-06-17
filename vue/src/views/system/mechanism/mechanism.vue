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
      <el-select
        v-model="listQuery.importance"
        placeholder="部门状态"
        clearable
        style="width: 150px"
        class="filter-item"
      >
        <el-option v-for="item in importanceOptions" :key="item" :label="item" :value="item" />
        <!-- <el-option label="全部" value="3"></el-option>
        <el-option label="必选" value="1"></el-option>
        <el-option label="非必选" value="2"></el-option>-->
      </el-select>
      <!-- <el-select
        v-model="listQuery.type"
        placeholder="Type"
        clearable
        class="filter-item"
        style="width: 130px"
      >
        <el-option
          v-for="item in calendarTypeOptions"
          :key="item.key"
          :label="item.display_name+'('+item.key+')'"
          :value="item.key"
        />
      </el-select>
      <el-select
        v-model="listQuery.sort"
        style="width: 140px"
        class="filter-item"
        @change="handleFilter"
      >
        <el-option
          v-for="item in sortOptions"
          :key="item.key"
          :label="item.label"
          :value="item.key"
        />
      </el-select>-->
      <el-button class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">搜索</el-button>
      <el-button
        class="filter-item"
        style="margin-left: 10px;"
        type="primary"
        icon="el-icon-edit"
        @click="handleCreate"
      >添加</el-button>
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

    <el-table :key="tableKey" v-loading="listLoading" :data="list" style="width: 100%;">
      <el-table-column
        label="序号"
        type="index"
        :index="indexMethod"
        align="center"
        :class-name="getSortClass('id')"
      >
        <!-- <template slot-scope="{row}">
          <span>{{ row.id }}</span>
        </template>-->
      </el-table-column>
      <el-table-column align="center" prop="mechanismName" label="部门名"></el-table-column>
      <!-- <el-table-column prop="menuCode" label="栏目码" width="150px" align="center">
        
        <template slot-scope="{row}">
          <span>{{ row.timestamp | parseTime('{y}-{m}-{d} {h}:{i}') }}</span>
        </template>
      </el-table-column>-->
      <el-table-column align="center" prop="sname" label="负责人"></el-table-column>
      <!-- <el-table-column prop="menuName" label="栏目名" min-width="150px">
        <template slot-scope="{row}">
          <span class="link-type" @click="handleUpdate(row)">{{ row.title }}</span>
          <el-tag>{{ row.type | typeFilter }}</el-tag>
        </template>
      </el-table-column>-->
      <el-table-column align="center" prop="parentName" label="父级部门"></el-table-column>
      <!-- <el-table-column prop="permissionCode" label="权限码" width="110px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.author }}</span>
        </template>
      </el-table-column>-->
      <el-table-column align="center" prop="branchName" label="分管领导"></el-table-column>
      <!-- <el-table-column prop="permissionName" label="权限名" width="110px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.author }}</span>
        </template>
      </el-table-column>-->
      <el-table-column align="center" prop="createTime" label="创建时间"></el-table-column>
      <el-table-column align="center" prop="createName" label="创建人"></el-table-column>
      <el-table-column align="center" prop="staus" label="状态"></el-table-column>
      <!-- <el-table-column prop="requiredPermission" label="是否必选" width="110px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.author }}</span>
        </template>
      </el-table-column>-->
      <!-- <el-table-column v-if="showReviewer" label="Reviewer" width="110px" align="center">
        <template slot-scope="{row}">
          <span style="color:red;">{{ row.reviewer }}</span>
        </template>
      </el-table-column>-->
      <!-- <el-table-column label="权限名" width="80px">
        <template slot-scope="{row}">
          <svg-icon
            v-for="n in + row.importance"
            :key="n"
            icon-class="star"
            class="meta-item__icon"
          />
        </template>
      </el-table-column>
      <el-table-column label="是否必须" align="center" width="95">
        <template slot-scope="{row}">
          <span
            v-if="row.pageviews"
            class="link-type"
            @click="handleFetchPv(row.pageviews)"
          >{{ row.pageviews }}</span>
          <span v-else>0</span>
        </template>
      </el-table-column>-->
      <!-- <el-table-column label="Status" class-name="status-col" width="100">
        <template slot-scope="{row}">
          <el-tag :type="row.status | statusFilter">{{ row.status }}</el-tag>
        </template>
      </el-table-column>-->
      <el-table-column
        v-if="hasPerm('permission:delete')"
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

          <el-button
            type="primary"
            v-if="hasPerm('mechanism:update')"
            size="mini"
            @click="handleUpdate(row)"
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
      @close="closefase"
      style="width:75%"
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
        <el-form-item label="部门名称" prop="menuName">
          <el-input v-model="temp.menuName" placeholder="栏目名" />
        </el-form-item>
        <el-form-item label="负责人">
          <el-select v-model="temp.region"  placeholder="负责人">
            <el-option
              v-for="(item,index) in staff"
              :label="item.name"
              :value="item.sid"
              :key="index"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="父级部门">
          <!-- //temp.parent -->
          <el-cascader
            :placeholder="placeholder"
            v-model="defaultvalue"
            :props="props"
            @change="Change"
            :show-all-levels="false"
            :options="bm"
          ></el-cascader>
        </el-form-item>
        <el-form-item label="分管领导">
          <el-select v-model="temp.ld" @change="ldchange" placeholder="分管领导">
            <el-option v-for="(item,index) in ld" :label="item.name" :value="item.sid" :key="index"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
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
  { key: "EU", display_name: "Eurozone" }
];
export default {
  data() {
    return {
      defaultvalue: ["1"],
      props: {
        value: "mid",
        label: "mechanismName",
        children: "chilrenMechanism",
        emitPath: false,
        expandTrigger: "click",
        checkStrictly: true
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
        sort: "+index"
      },
      ld: [],
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
        menuName: "",
        menuCode: "",
        region: "",
        parent: [],
        ld: "",
        status: "published"
      },
      dialogFormVisible: false,
      dialogStatus: "",
      textMap: {
        update: "修改",
        create: "新增"
      },
      staff: [],
      parentasd: [],
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
      bm: [],
      downloadLoading: false
    };
  },
  created() {
    this.getList();
    this.$nextTick(() => { // 以服务的方式调用的 Loading 需要异步关闭
    this.getAllStaff();
    this.getSysmechanismAll();  
    });
    
  },
  methods: {
    closefase() {
      console.log("123");
      this.placeholder = "";
      this.defaultvalue = [];
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
      //查询列表
      // if (!this.hasPerm('staff:list')) {
      //   return
      // }
      this.listLoading = false;
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
          staus: this.listQuery.importance
        }
      }).then(response => {
        console.log(response);
        this.total = response.count;
        this.list = [];
        response.data.filter((item, index) => {
          this.list.push(item);
        });
        console.log(this.list);
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
    indexMethod(val) {
      return val++;
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
    getAllStaff() {
      this.api({
        url: "SysStaff/get",
        method: "get"
      }).then(res => {
        console.log(res);
        res.filter(item => {
          this.staff.push(item);
          this.ld.push(item);
        });
      });
    },
    Change(val) {
      console.log(val);
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
    handleCreate() {
      this.resetTemp();
      this.dialogStatus = "create";
      this.dialogFormVisible = true;
      this.$nextTick(() => {
        this.$refs["dataForm"].clearValidate();
      });
    },
    createData() {
      console.log(store.getters.userId);
      console.log(this.temp);
      if (
        !store.getters.userId == null ||
        !this.temp.menuName == null ||
        !this.temp.region == null ||
        !this.temp.ld == null
      ) {
        this.$notify({
          title: "error",
          message: "请将信息填写完整",
          type: "error",
          duration: 2000
        });
      } else {
        this.api({
          url: "sysmechanism/add",
          method: "post",
          params: {
            mechanismName: this.temp.menuName,
            sid: this.temp.region,
            parent: this.defaultvalue,
            branch: this.temp.ld,
            createId: store.getters.userId
          }
        }).then(res => {
          this.getSysmechanismAll()
          this.getList()
          this.temp.parent = this.defaultvalue;
          console.log(res);
          this.$message({
            type: "success",
            title: "成功"
          });
          this.dialogFormVisible = false;
        });
      }
    },
    handleUpdate(row) {
      console.log("row", row);
      this.temp.id = row.mid;
      this.temp.menuName = row.mechanismName; // = Object.assign({}, row); // copy obj
      this.temp.region = row.sid;
      this.defaultvalue = [];
      this.defaultvalue.push(row.parent);
      console.log("this.defaultvalue", this.defaultvalue);
      this.placeholder = row.parentName;
      this.temp.ld = row.branch;
      console.log(this.temp);
      this.temp.timestamp = new Date(this.temp.timestamp);
      this.dialogStatus = "update";
      this.dialogFormVisible = true;
      this.$nextTick(() => {
        this.$refs["dataForm"].clearValidate();
      });
    },
    updateData() {
      console.log(this.temp, this.defaultvalue);
      this.api({
        url:'sysmechanism/update',
        method:'put',
        params:{
          mid:this.temp.id,
          mechanismName:this.temp.menuName,
          sid:this.temp.region,
          parent:this.defaultvalue[0],
        }
      }).then(res=>{
        this.getList();
        this.dialogFormVisible = false;
      })
    },
    handleDelete(row, index) {
      console.log("delete", row, index);
      if (row.staus == "正常") {
        this.api({
          url: "sysmechanism/del/" + row.mid,
          method: "delete"
        }).then(res => {
          console.log(res);
          if (res === 1) {
            this.total--;
            this.list.splice(index, 1);
            this.$notify({
              title: "成功",
              message: "",
              type: "success",
              duration: 2000
            });
          } else {
            this.$notify({
              title: "失败",
              message: "请先将与该部门相关联的数据删除",
              type: "error",
              duration: 2000
            });
          }
        });
      }else{
        this.api({
          url: "sysmechanism/update/" + row.mid,
          method: "put"
        }).then(res => {
          console.log(res);
          if (res === 1) {
            this.total++;
            this.list.splice(index, 1);
            this.$notify({
              title: "成功",
              message: "",
              type: "success",
              duration: 2000
            });
          } 
        });
      }
      this.getSysmechanismAll()
    },
    handleFetchPv(pv) {
      //   fetchPv(pv).then(response => {
      //     this.pvData = response.data.pvData;
      //     this.dialogPvVisible = true;
      //   });
    },
    handleDownload() {
      // var a = document.createElement('a');
      // a.download = file.name;
      // a.href = file.url;
      // a.dispatchEvent(event);
      this.api({
        url: "/ExcelDownload",
        method: "post"
      }).then(res => {
        console.log(res);
      });

      //   this.downloadLoading = true
      //   import('@/vendor/Export2Excel').then(excel => {
      //     const tHeader = ['timestamp', 'title', 'type', 'importance', 'status']
      //     const filterVal = ['timestamp', 'title', 'type', 'importance', 'status']
      //     const data = this.formatJson(filterVal)
      //     excel.export_json_to_excel({
      //       header: tHeader,
      //       data,
      //       filename: 'table-list'
      //     })
      //     this.downloadLoading = false
      //   })
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
    }
  }
};
</script>

<style>
</style>