<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input
        v-model="listQuery.title"
        placeholder="栏目名"
        style="width: 200px;"
        class="filter-item"
        @keyup.enter.native="handleFilter"
      />
      <el-select
        v-model="listQuery.importance"
        placeholder="是否必须"
        clearable
        style="width: 150px"
        class="filter-item"
      >
        <el-option v-for="item in importanceOptions" :key="item" :label="item" :value="item" />
      </el-select>

      <el-button class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">搜索</el-button>
      <el-button class="filter-item" type="primary" icon="el-icon-search" @click="reset">重置</el-button>
      <el-button
        class="filter-item"
        style="margin-left: 10px;"
        type="primary"
        icon="el-icon-edit"
        @click="handleCreate"
      >添加</el-button>
      <!-- <el-button
        :loading="downloadLoading"
        class="filter-item"
        type="primary"
        icon="el-icon-download"
        @click="handleDownload"
      >导出</el-button>-->
      <!-- <el-checkbox
        v-model="showReviewer"
        class="filter-item"
        style="margin-left:15px;"
        @change="tableKey=tableKey+1"
      >reviewer</el-checkbox>-->
    </div>

    <el-table :key="tableKey" v-loading="listLoading" :data="list" style="width: 100%;">
      <el-table-column label="序号" prop="index" align="center" :class-name="getSortClass('id')">
        <!-- <template slot-scope="{row}">
          <span>{{ row.id }}</span>
        </template>-->
      </el-table-column>
      <el-table-column align="center" prop="menuCode" label="栏目码"></el-table-column>
      <!-- <el-table-column prop="menuCode" label="栏目码" width="150px" align="center">
        
         <template slot-scope="{row}">
          <span>{{ row.timestamp | parseTime('{y}-{m}-{d} {h}:{i}') }}</span>
        </template>
      </el-table-column>-->
      <el-table-column align="center" prop="menuName" label="栏目名">
        <template slot-scope="scope">
          <el-tooltip v-if="hasPerm('permission:update')" content="点击查看详情或修改" placement="right" effect="dark">
            <a 
              @click="handleUpdate(scope.row,)"
              target="_blank"
              class="buttonText"
              style="color: #1890ff"
            >{{scope.row.menuName}}</a>
          </el-tooltip>
        </template>
      </el-table-column>
      <!-- <el-table-column prop="menuName" label="栏目名" min-width="150px">
        <template slot-scope="{row}">
          <span class="link-type" @click="handleUpdate(row)">{{ row.title }}</span>
          <el-tag>{{ row.type | typeFilter }}</el-tag>
        </template>
      </el-table-column>-->
      <el-table-column align="center" prop="permissionCode" label="权限码"></el-table-column>
      <!-- <el-table-column prop="permissionCode" label="权限码" width="110px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.author }}</span>
        </template>
      </el-table-column>-->
      <el-table-column align="center" prop="permissionName" label="权限名"></el-table-column>
      <!-- <el-table-column prop="permissionName" label="权限名" width="110px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.author }}</span>
        </template>
      </el-table-column>-->
      <el-table-column align="center" prop="requiredPermissionis" label="是否必选"></el-table-column>

      <el-table-column
        v-if="hasPerm('permission:delete')"
        label="操作"
        align="center"
        width="230"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="{row,$index}">
          <el-button
            v-if="hasPerm('permission:delete')"
            size="mini"
            type="danger"
            @click="handleDelete(row,$index)"
          >删除</el-button>
          <!-- <el-button
            v-if="hasPerm('permission:update')"
            size="mini"
            type="primary"
            @click="handleUpdate(row,$index)"
          >修改</el-button> -->
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      @current-change="handleCurrentChange"
      :current-page="listQuery.page"
      :page-size="listQuery.limit"
      :total="total"
    ></el-pagination>

    <el-dialog @close="close" :title="dialogStatus==='create'?'新增权限':'修改权限'" :visible.sync="dialogFormVisible">
      <el-form
        ref="temp"
        :rules="rules"
        :model="temp"
        label-position="right"
        label-width="80px"
        style="width: 400px; margin-left:50px;"
      >
        <el-form-item label="栏目名" prop="menuName">
          <el-input v-model="temp.menuName" placeholder="栏目名" />
        </el-form-item>
        <el-form-item label="栏目码" prop="menuCode">
          <el-input v-model="temp.menuCode" placeholder="栏目码" />
        </el-form-item>
        <el-form-item label="权限名" prop="permissionName">
          <el-input v-model="temp.permissionName" placeholder="权限名"></el-input>
        </el-form-item>
        <el-form-item label="权限码" prop="permissionCode">
          <el-input v-model="temp.permissionCode" placeholder="权限码"></el-input>
        </el-form-item>
        <el-form-item label="是否必选" prop="permissionCode">
          <el-radio-group v-model="temp.requiredPermission">
            <el-radio  :label="2" checked>非必选</el-radio>
            <el-radio  :label="1">必选</el-radio>
          </el-radio-group>
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
    var validateNoChinese= (rule, value, callback) => {
        if (/^[\u4e00-\u9fa5]+$/.test(value)) {
          callback(new Error('暂不支持中文'));
        } else {
          callback();
        }
      };
    return {
      radio: "",
      tableKey: 0,
      list: [],
      total: 0,
      listQuery: {
        page: 1,
        limit: 10,
        importance: "全部",
        title: "",
        type: undefined,
        sort: "+index"
      },
      importanceOptions: ["全部", "必选", "非必选"],
      calendarTypeOptions,
      sortOptions: [
        { label: "ID Ascending", key: "+index" },
        { label: "ID Descending", key: "-index" }
      ],
      statusOptions: ["published", "draft", "deleted"],
      showReviewer: false,
      temp: {
        id: undefined,
        requiredPermission:1,
        menuCode: "",
        menuName: "",
        permissionCode: "",
        permissionName: "",
        status: "published"
      },
      rules: {
        menuCode: [
          { required: true, message: "请输入栏目码", trigger: "blur" },
          {validator: validateNoChinese, trigger: 'blur'}
        ],
        menuName: [{ required: true, message: "请输入名", trigger: "blur" },
        
          ],
        permissionCode: [
          { required: true, message: "请输入权限码", trigger: "blur" },
          {validator: validateNoChinese, trigger: 'blur'}
        ],
        permissionName: [
          { required: true, message: "请输入权限名", trigger: "blur" }
        ]
      },
      dialogFormVisible: false,
      dialogStatus: "",
      textMap: {
        update: "修改权限",
        create: "增加权限"
      },

      listLoading: true,
      Excel: "",
      dialogPvVisible: false,
      pvData: [],
      downloadLoading: false
    };
  },
  computed: {},
  created() {
    console.log('重置')
    this.getList();
  },
  methods: {
    reset(){
      this.listQuery={
        page: 1,
        limit: 10,
        importance: "全部",
        title: "",
        type: undefined,
        sort: "+index"
      }
    },
    swchange(val) {
      console.log(val);
      this.temp.requiredPermission = val ? 1 : 2;
    },
    close(){
      this.getList();
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
      this.listLoading = true;
      this.api({
        url:
          "syspermission/get/" +
          this.listQuery.page +
          "/" +
          this.listQuery.limit,
        method: "get",
        params: {
          name: this.listQuery.title,
          re: this.listQuery.importance
        }
      }).then(response => {
        console.log(response);
        this.listQuery.limit = response.size;
        this.total = response.total;
        this.list = [];
        response.records.filter((item, index) => {
          let i = {};
          i.index = index + 1;
          i.id = item.id;
          i.menuCode = item.menuCode;
          i.menuName = item.menuName;
          i.permissionCode = item.permissionCode;
          i.permissionName = item.permissionName;
          i.requiredPermission = item.requiredPermission;
          i.requiredPermissionis = item.requiredPermission === 1 ? "是" : "否";

          this.list.push(i);
        });
        this.listLoading = false;
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
      // this.$nextTick(() => {
      //   this.$refs["dataForm"].clearValidate();
      // });
    },
    createData() {
      this.$refs["temp"].validate(valid => {
        console.log(valid,"valid")
        if (valid) {
          this.api({
            url: "syspermission/add",
            method: "post",
            data: this.temp,
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
              this.dialogFormVisible = false;
            }
          });
        } else {
          return false;
        }
      });
    },
    handleUpdate(row){
      console.log(row)
      this.dialogStatus='update'
      this.temp=row
      this.temp.permissionCode=this.temp.permissionCode.substring(this.temp.menuCode.length+1)
      this.dialogFormVisible = true
    },
    updateData() {
      this.$alert("是否确定修改", "提示", {
        showCancelButton: true,
        showConfirmButton: true,
        closeOnPressEscape: false,
        callback: action => {
          console.log(this.temp);
          if (action === "confirm") {
            this.api({
              url: "syspermission/update",
              method: "put",
              params: {
                id: this.temp.id,
                menuCode: this.temp.menuCode,
                menuName: this.temp.menuName,
                permissionCode: this.temp.permissionCode,
                permissionName: this.temp.permissionName,
                requiredPermission: this.temp.requiredPermission
              }
            }).then(res => {
              console.log(res)
              if(res===1){
                this.$message({
                  type: "success",
                  message: "修改成功"
                });
                this.dialogFormVisible = false;
              }else if(res===0){
                this.$message({
                  type: "success",
                  message: "栏目码不能重复"
                });
              }
            });
          }
        }
      });
    },
    handleDelete(row, index) {
      console.log(row, index);
      this.$alert("将会同时删除相关角色下的此权限", "是否确定删除？", {
        showCancelButton: true,
        showConfirmButton: true,
        closeOnPressEscape: false,
        callback: action => {
          if (action === "confirm") {
            this.api({
              url: "syspermission/del",
              method: "delete",
              data: row
            }).then(res => {
              // console.log(res);
              if (res === 1) {
                this.$notify({
                  title: "成功",
                  message: "删除成功",
                  type: "success",
                  duration: 2000
                });
                this.total = this.total - 1;
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
          }
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
    }
  }
};
</script>

<style>
</style>