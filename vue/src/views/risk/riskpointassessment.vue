<template>
  <div>
    <el-form :inline="true" class="demo-form-inline">
      <div align="center" style="margin-top: 30px">
        <el-form-item label="部门">
          <el-cascader
            :placeholder="placeholder"
            v-model="defaultvalue"
            :props="props"
            @change="Change"
            :show-all-levels="false"
            :options="bm"
          ></el-cascader>
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

      <!--<div>-->
      <!--<el-form-item>-->
      <!--<el-button type="primary" class="el-icon-plus" @click="syncList">同步</el-button>-->
      <!--</el-form-item>-->
      <!--</div>-->
      <el-table
        :data="tableData.slice((currentPage-1)*pageSize,currentPage*pageSize)"
        border
        style="width: 100%" ref="multipleTable">
        <el-table-column type="selection" width="50px"></el-table-column>
        <el-table-column  label="序号" type="index" align="center"  width="60px"></el-table-column>
        <el-table-column
          align="center"
          prop="riskImfomation"
          label="部门名称"
          :formatter="deptFormat">
        </el-table-column>
        <el-table-column
          align="center"
          width="160px"
          prop="numberOneRisks"
          label="一级风险数">
        </el-table-column>
        <el-table-column
          align="center"
          width="160px"
          prop="numberTwoRisks"
          label="二级风险数">
        </el-table-column>
        <el-table-column
          align="center"
          width="160px"
          prop="numberThreeRisks"
          label="三级风险数">
        </el-table-column>
        <el-table-column
          align="center"
          width="230px"
          prop="riskCreateTime"
          label="创建时间"
          :formatter="dateFormat">
        </el-table-column>
        <!--<el-table-column-->
        <!--label="操作">-->
        <!--<template slot-scope="scope">-->
        <!--<el-button-->
        <!--type="primary"-->
        <!--@click="handleEdit(scope.$index, scope.row)">编辑-->
        <!--</el-button>-->
        <!--</template>-->
        <!--</el-table-column>-->
      </el-table>
      <div style="margin-top: 30px" class="block" align="center">
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
    </el-form>
  </div>
</template>

<script>
  import {list, sync, findByPDeptId, getOne} from '@/api/risk/riskpointassessment'
  import {getAll, getAllMechanismByParent, getSysPostByMid} from '@/api/risk/postRiskCombing'
  import {myGetList} from "@/api/evaluate/riskEvaluate"
  import {mapGetters} from 'vuex'
  import moment from 'moment'
  import qs from 'qs'

  export default {
    computed: {
      ...mapGetters([
        'userId',
        'nickname'
      ])
    },
    created() {
      this.initList();
      //this.getAllPost();
      this.getAllMechanism();
      this.getSysmechanismAll();

      //this.MyInitList();

    },
    methods: {
      /*MyInitList() {
        myGetList().then(response => {
          this.eChartList = response.list
          this.sumOne=response.sumOne
          this.sumTwo=response.sumTwo
          this.sumThree=response.sumThree
          console.log(sumOne+"  "+sumTwo+"  "+sumThree)
        })
      },*/
      Change(val) {
        this.search.pDeptId = val;
      },
      getSysmechanismAll(val = 0) {
        this.api({
          url: "sysmechanism/get",
          method: "get"
        }).then(res => {
          this.bm = [];
          res.filter(item => {
            this.bm.push(item);
          });
        });
      },
      //按条件查询
      onSearch() {
        let postData = qs.stringify({
          parent: this.search.pDeptId,
        });
        this.listLoading = true;
        getAllMechanismByParent(postData).then((response) => {
          this.tableData = [];
          this.total = 0;
          this.getPostByMid(response.list);
          this.currentPage = 1;
          this.listLoading = false
        })

      },
      getPostByMid(list) {
        for (let mechanism of list) {
          let postData = qs.stringify({
            riskImfomation: mechanism.mid
          });

          getOne(postData).then((response) => {
            this.tableData.push(response.riskpointassessment);
            this.total += 1;
          });

          if (mechanism.chilrenMechanism.length > 0) {
            this.getPostByMid(mechanism.chilrenMechanism);
          }
        }
      },
      syncList() {
        sync().then(response => {
          this.initList();
        })
      },
      getAllMechanism() {
        getAll().then(response => {
          this.mechanismList = response.list
        })
      },
      stateFormat(row, column) {
        if (row.pstatus === 1) {
          return '正常'
        } else {
          return '禁用'
        }
      },
      deptFormat(row, column) {
        for (var i = 0; i < this.mechanismList.length; i++) {
          if (row.riskImfomation == this.mechanismList[i].mid) {
            //pname=this.mechanismList[i].mechanismName;
            return this.mechanismList[i].mechanismName;
          }else if(row.riskImfomation===0){
            return "公共风险"
          }
        }

      },
      dateFormat(row, column) {
        var date = row[column.property];
        return moment(date).format("YYYY-MM-DD")
      },
      onrest() {
        this.search.pDeptId = undefined;

      },
      initList() {
        list().then(response => {
          this.tableData = response.list
          this.total = response.list.length
        })
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
      }
    },
    data() {
      return {
        eChartList:[],// 图表需要的数据集合
        sumOne:0,
        sumTwo:0,
        sumThree:0,


        placeholder: "",
        bm: [],
        defaultvalue: ["1"],
        props: {
          value: "mid",
          label: "mechanismName",
          children: "chilrenMechanism",
          emitPath: false,
          expandTrigger: "click",
          checkStrictly: true
        },
        timer: '',
        iconFormVisible: false,
        rickInfo: {},
        userInfo: {},
        dialogTitle: '增加',
        rowIndex: null,
        search: {
          pDeptId: undefined
        },
        currentPage4: 1,
        pageSize: 10,
        total: 0,
        postList: [],
        mechanismList: [{}],
        currentPage: 1,
        isShowSelect: false,
        tableData: [{
          riskid: undefined,
          riskImfomation: undefined,
          numberOneRisks: undefined,
          numberTwoRisks: undefined,
          numberThreeRisks: undefined,
          riskCreateTime: undefined,
          riskCreateId: undefined,
          riskCreateName: undefined,
          riskStatus: undefined
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
