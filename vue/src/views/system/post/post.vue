<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input
        v-model="listQuery.name"
        placeholder="岗位名称"
        style="width: 200px;"
        class="filter-item"
        @keyup.enter.native="handleFilter"
      />
      <!-- <el-form-item label="部门"> -->
          <!-- //temp.parent -->
          <el-cascader
            placeholder="部门"
            
            :props="props"
            @change="Change"
            :show-all-levels="false"
            :options="bm"
          ></el-cascader>
        <!-- </el-form-item> -->
      <el-input
        v-model="listQuery.message"
        placeholder="部门描述"
        style="width: 200px;"
        class="filter-item"
        @keyup.enter.native="handleFilter"
      />
      <!-- <el-select
        v-model="listQuery.importance"
        placeholder="岗位描述"
        clearable
        style="width: 150px"
        class="filter-item"
      >
        <el-option v-for="item in importanceOptions" :key="item" :label="item" :value="item" />
         <el-option label="全部" value="3"></el-option>
        <el-option label="必选" value="1"></el-option>
        <el-option label="非必选" value="2"></el-option>
      </el-select> -->
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
      <el-table-column prop="pid" type="selection" width="55"></el-table-column>
      <el-table-column label="序号" type="index" :index="indexMethod" prop="index" align="center" :class-name="getSortClass('id')">
        <!-- <template slot-scope="{row}">
          <span>{{ row.id }}</span>
        </template>-->
      </el-table-column>
      <el-table-column align="center" prop="pname" label="岗位名称"></el-table-column>
      <!-- <el-table-column prop="menuCode" label="栏目码" width="150px" align="center">
        
         <template slot-scope="{row}">
          <span>{{ row.timestamp | parseTime('{y}-{m}-{d} {h}:{i}') }}</span>
        </template>
      </el-table-column>-->
      <el-table-column align="center" prop="mname" label="部门名称"></el-table-column>
      <!-- <el-table-column prop="menuName" label="栏目名" min-width="150px">
        <template slot-scope="{row}">
          <span class="link-type" @click="handleUpdate(row)">{{ row.title }}</span>
          <el-tag>{{ row.type | typeFilter }}</el-tag>
        </template>
      </el-table-column>-->
      <el-table-column align="center" prop="message" label="岗位描述"></el-table-column>
      <!-- <el-table-column prop="permissionCode" label="权限码" width="110px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.author }}</span>
        </template>
      </el-table-column>-->
      <el-table-column align="center" prop="createname" label="创建人"></el-table-column>
      <!-- <el-table-column prop="permissionName" label="权限名" width="110px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.author }}</span>
        </template>
      </el-table-column>-->
      <el-table-column align="center" prop="createTime" label="创建时间"></el-table-column>

      <el-table-column align="center" prop="staus" label="状态"></el-table-column>
      <el-table-column
        v-if="hasPerm('post:delete')"
        label="Actions"
        align="center"
        width="230"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="{row,$index}">
          <el-button
            v-if="hasPerm('post:delete')"
            size="mini"
            type="danger"
            @click="handleDelete(row,$index)"
          >删除</el-button>
        </template>
        <template slot-scope="{row,$index}">
          <el-button
            v-if="hasPerm('post:update')"
            size="mini"
            type="primary"
            @click="handleUpdate(row,$index)"
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

    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form
        ref="dataForm"
        :rules="rules"
        :model="temp"
        label-position="left"
        label-width="70px"
        style="width: 400px; margin-left:50px;"
      >
        <el-form-item label="岗位名称" prop="pname">
          <el-input v-model="temp.menuName" placeholder="栏目名" />
        </el-form-item>
        <el-form-item label="栏目码" prop="menuCode">
          <el-input v-model="temp.menuCode" placeholder="栏目码" />
        </el-form-item>
        <el-form-item label="权限类型" prop="code">
          <el-radio v-model="radio" label="add">新增</el-radio>
          <el-radio v-model="radio" label="update">修改</el-radio>
          <el-radio v-model="radio" label="delete">删除</el-radio>
          <el-radio v-model="radio" label="list">查询</el-radio>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="dialogStatus==='create'?createData():updateData()">提交</el-button>
        <el-button @click="dialogFormVisible = false">取消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
const calendarTypeOptions = [
  { key: "CN", display_name: "China" },
  { key: "US", display_name: "USA" },
  { key: "JP", display_name: "Japan" },
  { key: "EU", display_name: "Eurozone" }
];
export default {
  data() {
    return {
      props: {
        value: "mid",
        label: "mechanismName",
        children: "chilrenMechanism",
        emitPath: false,
        expandTrigger: "click",
        checkStrictly: true,
        multiple:true,
        collapseTags: true,
        clearable:true
      },
      bm:[],
      radio: "",
      tableKey: 0,
      list: [],
      total: 0,
      listQuery: {
        page: 1,
        limit: 10,
        importance: "正常",
        bm:[],
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
        menuName: "",
        menuCode: "",
        code: "",
        status: "published"
      },
      dialogFormVisible: false,
      dialogStatus: "",
      textMap: {
        update: "修改",
        create: "新增"
      },
      listLoading:true,
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
    this.getSysmechanismAll()
  },
  methods: {
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
      
       let mids=this.listQuery.bm.join(',')
       console.log(mids)
      this.listLoading = true;
      this.api({
        url:
          "syspost/get/" +
          this.listQuery.page +
          "/" +
          this.listQuery.limit,
        method: "post",
        data: {
          pname: this.listQuery.name,
          message: this.listQuery.message,
          mids: mids
        }
      }).then(response => {
        console.log("getlist",response);
        this.total=response.count
        response.data.filter(item=>{
          let i={}
          i.pid=item.pid
          i.pname=item.pname
          i.mname=item.mname
          i.message=item.message
          i.createname=item.createname
          i.createTime=item.createTime
          i.staus=item.staus==1?'正常':'删除'
          this.list.push(i)
        })
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
    Change(val){
      console.log(val)
      this.listQuery.bm=val
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
      this.dialogFormVisible = true;
      this.$nextTick(() => {
        this.$refs["dataForm"].clearValidate();
      });
    },
    createData() {
      if (!this.temp.menuName || !this.temp.menuCode || !this.radio) {
        this.$message({
          type: "error",
          message: "请将信息填写完整"
        });
      } else {
        let name = "";
        if (this.radio == "add") {
          name = "新增";
        } else if (this.radio == "delete") {
          name = "删除";
        } else if (this.radio == "update") {
          name = "修改";
        } else {
          name = "列表";
        }
        let permissionCode = this.temp.menuCode + ":" + this.radio;
        this.api({
          url: "syspermission/add",
          method: "post",
          data: {
            menuCode: this.temp.menuCode,
            menuName: this.temp.menuName,
            permissionCode: permissionCode,
            permissionName: name,
            requiredPermissionis: this.radio == "list" ? 1 : 2
          }
        }).then(respone => {
          console.log(respone);
          if (respone === 2) {
            this.$message({
              type: "error",
              message: "已存在该权限"
            });
          } else {
            this.$message({
              type: "success",
              message: "添加成功"
            });
          }
        });
      }
      // this.$refs["dataForm"].validate(valid => {
      //   if (valid) {
      //     this.temp.id = parseInt(Math.random() * 100) + 1024; // mock a id
      //     this.temp.author = "vue-element-admin";
      //     //   createArticle(this.temp).then(() => {
      //     //     this.list.unshift(this.temp);
      //     //     this.dialogFormVisible = false;
      //     //     this.$notify({
      //     //       title: "Success",
      //     //       message: "Created Successfully",
      //     //       type: "success",
      //     //       duration: 2000
      //     //     });
      //     //   });
      //   }
      // });
    },
    handleUpdate(row) {
      this.temp = Object.assign({}, row); // copy obj
      this.temp.timestamp = new Date(this.temp.timestamp);
      this.dialogStatus = "update";
      this.dialogFormVisible = true;
      this.$nextTick(() => {
        this.$refs["dataForm"].clearValidate();
      });
    },
    updateData() {
      this.$refs["dataForm"].validate(valid => {
        if (valid) {
          const tempData = Object.assign({}, this.temp);
          tempData.timestamp = +new Date(tempData.timestamp); // change Thu Nov 30 2017 16:41:05 GMT+0800 (CST) to 1512031311464
          //   updateArticle(tempData).then(() => {
          //     const index = this.list.findIndex(v => v.id === this.temp.id);
          //     this.list.splice(index, 1, this.temp);
          //     this.dialogFormVisible = false;
          //     this.$notify({
          //       title: "Success",
          //       message: "Update Successfully",
          //       type: "success",
          //       duration: 2000
          //     });
          //   });
        }
      });
    },
    handleDelete(row, index) {
      console.log(row, index);
      this.api({
        url: "syspermission/del",
        method: "delete",
        data: row
      }).then(res => {
        // console.log(res);
        if (res === 1) {
          this.$notify({
            title: "Success",
            message: "Delete Successfully",
            type: "success",
            duration: 2000
          });
          this.list.splice(index, 1);
        } else {
          this.$notify({
            title: "error",
            message: "请最后删除列表(list)项",
            type: "error",
            duration: 2000
          });
        }
      });
      // this.$notify({
      //   title: "Success",
      //   message: "Delete Successfully",
      //   type: "success",
      //   duration: 2000
      // });
      // this.list.splice(index, 1);
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
    },
    indexMethod(val){
      return ++val
    }
  }
};
</script>

<style>
</style>