<template>
  <div style="width:80%">
    <el-form :inline="true" class="demo-form-inline">
      <div align="center" style="margin-top: 30px">
        <el-form-item label="父级部门">
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
        </el-form-item>
      </div>
    </el-form>
    <div style="align-content: center; width: 100%;height: 500px;background:#fff;" id="echartss"></div>
  </div>
</template>
<script>
  import {findByPDeptId, getOne, list, sync} from '@/api/risk/riskpointassessment'
  import {getAll, getAllMechanismByParent, getSysPostByMid} from '@/api/risk/postRiskCombing'
  import echarts from "echarts";
  import qs from 'qs'


  export default {
    //先要导入依赖的实例

    //挂载前初始化echarts实例
    mounted: function () {
      // let postData = qs.stringify({
      //   riskImfomation: 1
      // });
      this.search.pDeptId = 1;
      this.onSearch();

    }, watch: {
      //观察option的变化
      echarts1_option: {
        handler(newVal, oldVal) {
          if (this.myChart) {
            if (newVal) {
              this.myChart.setOption(newVal);
            } else {
              this.myChart.setOption(oldVal);
            }
          } else {
            this.init();
          }
        },
        deep: true //对象内部属性的监听，关键。
      }
    },
    created() {
      this.getSysmechanismAll();
    },
    methods: {
      //按条件查询
      onSearch() {
        let postData = qs.stringify({
          parent: this.search.pDeptId,
        });
        this.listLoading = true;

        getAllMechanismByParent(postData).then((response) => {
          this.num1 = 0;
          this.num2 = 0;
          this.num3 = 0;
          this.getPostByMid(response.list);
        })
        let that = this;
        setTimeout(
          that.test2
          , "1000");
      },
      test2() {

        let objData = [{
          value: this.num1,
          name: "一级风险"
        }, {value: this.num2, name: "二级风险"}
          , {value: this.num3, name: "三级风险"}];
        this.echarts1_option.series[0].data = objData;
        this.echarts1_option.tooltip.formatter="{b}数量:{c}"
      },
      getPostByMid(list) {
        for (let mechanism of list) {
          let postData = qs.stringify({
            riskImfomation: mechanism.mid
          });

          getOne(postData).then((response) => {
            this.num1 += response.riskpointassessment.numberOneRisks;
            this.num2 += response.riskpointassessment.numberTwoRisks;
            this.num3 += response.riskpointassessment.numberThreeRisks;

          });

          if (mechanism.chilrenMechanism.length > 0) {
            this.getPostByMid(mechanism.chilrenMechanism);
          }
        }
      },
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
      init() {
        // 基于准备好的dom，初始化echarts实例
        this.myChart = echarts.init(document.getElementById("echartss"));
        // 绘制图表，this.echarts1_option是数据
        this.myChart.setOption(this.echarts1_option);
      }
    },
    data() {
      return {
        myChart: null,
        search: {
          pDeptId: undefined
        },
        placeholder: "",
        bm: [],
        defaultvalue: [1],
        props: {
          value: "mid",
          label: "mechanismName",
          children: "chilrenMechanism",
          emitPath: false,
          expandTrigger: "click",
          checkStrictly: true
        },
        echarts1_option: {
          title: {
            text: "风险事件触发量",
            subtext: "",
            x: "center"
          },
          tooltip: {
            trigger: "item",
            formatter: "{a} <br/>{b} : {c} ({d}%)"
          },
          legend: {
            orient: "vertical",
            left: "right",
            data: ["一级风险", "二级风险", "三级风险"]
          },
          num1: 0,
          num2: 0,
          num3: 0,
          series: [
            {
              name: "访问来源",
              type: "pie",
              radius: "55%",
              center: ["50%", "45%"],
              data: []
            }
          ]
        }
      };
    }
  };
</script>
