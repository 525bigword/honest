<template>
  <div>
    <el-form :inline="true" class="demo-form-inline">
      <div align="center" style="margin-top: 30px">
        <el-form-item>

          <el-select v-model="search" class="search_name" placeholder="请选择谈话类型">
            <el-option label="例行廉政谈话" value="例行廉政谈话"></el-option>
            <el-option label="任前廉政谈话" value="任前廉政谈话"></el-option>
            <el-option label="提醒谈话" value="提醒谈话"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button
            type="primary"
            @click="onSearch()"
            class="el-icon-search" >查询
          </el-button>
          <el-button
            type="primary"
            class="el-icon-refresh" plain @click="onrest" >重置
          </el-button>
        </el-form-item></div><br/>
      <div><el-form-item>
        <el-button type="primary" class="el-icon-plus" @click="add">新增</el-button>
        <el-button type="primary" class="el-icon-delete" @click="del">删除</el-button></el-form-item></div>
      <el-table
        :data="tableData.slice((currentPage-1)*pageSize,currentPage*pageSize)"
        border
        style="width: 100%" ref="multipleTable">
        <el-table-column type="selection" width="55px"></el-table-column>
        <el-table-column
          prop="id"
          label="编号"
          width="180">
        </el-table-column>
        <el-table-column
          prop="type"
          label="谈话类型"
          width="180">
        </el-table-column>
        <el-table-column
          prop="time"
          label="谈话时间"
          width="180">
        </el-table-column>
        <el-table-column
          prop="site"
          label="谈话地点">
        </el-table-column>
        <el-table-column
          prop="staus"
          label="状态">
          <template slot-scope="scope">
            <span
                target="_blank"
                class="buttonText">{{scope.row.status=='0'?'创建':'待审'}}</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" fixed="right" align="center">
          <template slot-scope="scope">
            <el-button type="primary" size="small" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="block" align="center">
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="currentPage4"
          :page-sizes="[10, 20, 30, 40,50]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
        </el-pagination>
      </div>

      <!--弹窗-->
      <el-dialog :title="dialogTitle" width="50%" :visible.sync="iconFormVisible">
        <el-form :inline="true" :model="userInfo" class="demo-form-inline" label-width="120px">
          <el-input v-model="userInfo.id" placeholder="编号" type="hidden"></el-input>
          <el-form-item label="谈话对象单位">

            <el-input v-model="userInfo.deptname" placeholder="谈话对象单位"></el-input>
          </el-form-item>
          <el-form-item label="谈话对象姓名">
            <el-input v-model="userInfo.name" placeholder="谈话对象姓名"></el-input>
          </el-form-item>
          <el-form-item label="记录人">
            <el-input v-model="userInfo.persenname" placeholder="记录人"></el-input>
          </el-form-item>
          <el-form-item label="谈话时间">
            <el-date-picker v-model="userInfo.time" placeholder="创建时间" type="datetime"  style="width: 400px" ></el-date-picker>
          </el-form-item>
          <el-form-item label="谈话对象政治面貌">
            <el-input v-model="userInfo.userAddress" placeholder="谈话对象政治面貌"></el-input>
          </el-form-item>
          <el-form-item label="谈话对象职务">
            <el-input v-model="userInfo.duty" placeholder="谈话对象职务"></el-input>
          </el-form-item>
          <el-form-item label="谈话类型">
            <el-input v-model="userInfo.usertype" placeholder="谈话类型"></el-input>
          </el-form-item>
          <el-form-item label="谈话地点">
            <el-input v-model="userInfo.userAddress" placeholder="谈话地点"></el-input>
          </el-form-item><el-form-item label="谈话提纲">
          <el-input v-model="userInfo.outline" placeholder="谈话提纲"></el-input>
        </el-form-item>
          <el-form-item label="创建人">
            <el-input v-model="userInfo.createname" placeholder="创建人" disabled="disabled"></el-input>
          </el-form-item>
          <el-form-item label="创建时间">
            <el-date-picker v-model="userInfo.createtime" placeholder="创建时间" type="datetime"  style="width: 400px" disabled="disabled" ></el-date-picker>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="iconFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="submitUser()" >确 定</el-button>
        </div>
      </el-dialog>
    </el-form>
  </div>
</template>

<script>
  import { mapGetters } from 'vuex'
  import { list,findbytitle,del } from '@/api/duty/talk'
  import qs from 'qs'
  export default {
    computed: {
      ...mapGetters([
        'nickname',
        'userId'
      ])
    },
    created() {
      this.initList()
    },
    methods:{    onrest(){
        this.search=''
      },
      //按标题查询
      onSearch() {
        let postData = qs.stringify({
          title:this.search
        });
        this.listLoading = true
        findbytitle(postData).then((response) =>{
          this.currentPage = 1
          this.tableData = response.list
          console.debug(this.tableData)
          this.total=response.list.length
          this.listLoading=false
        })
        },
      //删除
      del(){ var data = this.$refs.multipleTable.selection;
        console.debug("11"+data)
        if(JSON.stringify(data)=='[]'){
          this.$notify({
            title: '温馨提示',
            message: '请选择一行进行删除',
            type: 'success',
            duration: 2000
          })
        }
        else {
          let postData = qs.stringify({
            test:JSON.stringify(data)
          });

          console.debug('选中行数据'+JSON.stringify(data))
          del(postData).then((response) =>{
            this.initList();
            this.$notify({
              title: '成功',
              message: response.message,
              type: 'success',
              duration: 2000
            })
          })
        }},
      //初始化页面
      initList() {
        list(this.listQuery).then(response =>{
          console.debug(response)
          this.tableData = response.list
          this.total = response.list.length
        })
      }, add() {
        this.dialogTitle = '增加';
        this.userInfo={};
        this.userInfo.createname =this.nickname
        console.debug('id='+this.userId)
        /* 动态赋值实时设置当前时间*/
        this.$set(this.userInfo,'createtime',new Date())
        //this.timer= setInterval(this.time, 1000 );

        this.iconFormVisible = true;
      },
      handleSizeChange(size) {
        this.pageSize = size;
        console.log(this.pageSize)
      },
      handleCurrentChange(currentPage) {
        this.currentPage = currentPage;
        console.log(this.currentPage)  //点击第几页
      }
    },
    data() {
      return {
        iconFormVisible: false,
        userInfo: {},
        dialogTitle: '增加',
        rowIndex: null,
        search:'',
        currentPage4: 1,
        pageSize:10,
        total:0,
        currentPage:1,

        tableData: [/*{
          id:'1',
          type: '任前廉政谈话',
          time: '2019/2/2 19:09',
          site: '怀化市鹤城区迎风路 1518 号',
          staus:'已审核'
        }, {
          id:'2',
          type: '任前廉政谈话',
          time: '2019/2/2 19:09',
          site: '怀化市鹤城区迎风路 1518 号',
          staus:'已审核'
        }, {
          id:'3',
          type: '例行廉政谈话',
          time: '2019/2/2 19:09',
          site: '怀化市鹤城区迎风路 1518 号',
          staus:'已审核'
        }, {
          id:'4',
          type: '任前廉政谈话',
          time: '2019/2/2 19:09',
          site: '怀化市鹤城区德贤路 123 号',
          staus:'未审核'
        }*/]
      }
    }
  }
</script>

