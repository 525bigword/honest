<template>
  <div style="width:100%">
    <el-form :inline="true" class="demo-form-inline">
      <div align="center" style="margin-top: 30px">
        <el-form-item label="部门">
          <el-cascader
            :placeholder="placeholder"
            v-model="search.pDeptId"
            :props="props"
            :show-all-levels="false"
            :options="bm"
          ></el-cascader>
        </el-form-item>
        <!-- <el-form-item label="岗位">
           <el-select style="width: 80%;" v-model="search.pInfomationId" placeholder="岗位">
             <el-option
               v-for="(post) in postList2"
               :key="post.pid"
               :label="post.pname"
               :value="post.pid"
             ></el-option>
           </el-select>
         </el-form-item>-->
        <el-form-item>
          <el-button type="primary" @click="onSearch()" class="el-icon-search">查询</el-button>
        </el-form-item>
        <el-button
          type="primary"
          class="el-icon-refresh" plain @click="resetTemp">重置
        </el-button>
      </div>
    </el-form>
    <hr>
    <div style="background-color:darkolivegreen;align-content: center; width: 100%;height: 700px;background:#fff;" id="echartss"></div>
  </div>
</template>
<script>
  import {
    findBy,
    findByPDeptId,
    getOne,
    list,
    sync
  } from "@/api/risk/riskpointassessment";
  import {
    getAll,
    getAllMechanismByParent,
    getSysPostByMid
  } from "@/api/risk/postRiskCombing";
  import echarts from "echarts";
  import qs from "qs";
  // function eConsole(param) {

  // 　　//alert(option.series[0].data.length);
  // 　　//alert(option.series[0].data[i]);
  // 　　//param.dataIndex 获取当前点击索引，
  // 　　//alert(param.dataIndex);
  // 　　clickFunc(param.dataIndex);//执行点击效果
  // }

  // myChart.on("click", eConsole);

  export default {
    //先要导入依赖的实例

    //挂载前初始化echarts实例
    mounted: function() {
      // let postData = qs.stringify({
      //   riskImfomation: 1
      // });
      //this.search.pDeptId = 1;
      this.onSearch();
    },
    watch: {
      //观察option的变化
      echarts1_option: {
        handler(newVal, oldVal) {
          if (this.myChart) {
            this.myChart.on("click", this.eConsole);
            if (newVal) {
              this.myChart.setOption(newVal);
            } else {
              this.myChart.setOption(oldVal);
            }
          } else {
            this.init();
            if (this.search.pDeptId != undefined) {
              this.myChart.on("click", this.eConsole);
            }
          }
        },
        deep: true //对象内部属性的监听，关键。
      }
    },
    created() {
      this.getSysmechanismAll();
    },
    methods: {

      eConsole(param) {
        /*console.log("param:"+param.name);
        console.log("this.mids:"+this.search.pDeptId);*/
        this.$router.push({path: '/risk/postRiskCombing', query: {pDeptId: this.search.pDeptId, pGrade: param.name}
        });
      },
      //按条件查询
      onSearch() {
        let postData = qs.stringify({
          riskImfomation: this.search.pDeptId,
        });
        this.listLoading = true;
        findBy(postData).then(response => {
          this.num1 = response.num1
          this.num2 = response.num2
          this.num3 = response.num3
          console.log(this.num1+ "    " +this.num2+"  "+this.num3)
          /* if(this.search.pDeptId!=undefined){
             for(var i=0;i<this.bm.length;i++){
               if(this.search.pDeptId===this.bm[i].mid){
                 this.echarts1_option.title.text=this.bm[i].mechanismName+"风险等级占比图"
               }
             }
           }else{
             this.echarts1_option.title.text="风险等级总占比图"
           }*/
        })
        /*getAllMechanismByParent(postData).then(response => {
          this.num1 = 1;
          this.num2 = 1;
          this.num3 = 1;
          console.log("response.list", response.list);
          // response.list.filter(item=>{
          //   this.bm.push(item)
          // })
          this.getPostByMid(response.list);
        });*/
        let that = this;
        setTimeout(that.test2, 1000);
      },
      // 重置表单数据
      resetTemp() {
        this.search.pDeptId=undefined
      },
      test2() {
        console.log("test2:"+this.num1+ "    " +this.num2+"  "+this.num3)

        let objData = [
          {value: this.num1, name: "一级风险"},
          { value: this.num2, name: "二级风险" },
          { value: this.num3, name: "三级风险" }
        ];
        this.echarts1_option.series[0].data = objData;
        if(this.search.pDeptId===undefined) {
          this.echarts1_option.tooltip.formatter = "{b}数量:{c}";
        }else {
          this.echarts1_option.tooltip.formatter = "{b}数量:{c}<br/>点击查看风险详情";
        }

      },
      // 获得部门
      getSysmechanismAll() {
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
        postList2: [],
        num1:0,
        num2:0,
        num3:0,
        mids: [],
        myChart: null,
        search: {
          pDeptId: undefined,
        },
        placeholder: "",
        bm: [],
        defaultvalue: [0],
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
            text: "风险等级总占比图",
            subtext: "",
            x: "center",
            textStyle: { //图例文字的样式
              fontSize: 40
            },
          },
          tooltip: {
            trigger: "item",
            formatter: "{a} <br/>{b} : {c} ({d}%)"
          },
          color:["#C93326", "#FE9727","#29815E",],
          legend: {
            orient: "horizontal",
            right: 'center',  //图例距离左的距离
            y: '85%',  //图例上下居中
            data: ["一级风险", "二级风险", "三级风险"],
            textStyle: { //图例文字的样式
              fontSize: 20
            },
          },
          /*  num1: 2,
            num2: 0,
            num3: 0,*/
          series: [
            {
              name: "访问来源",
              type: "pie",
              radius: "55%",
              center: ["center", "45%"],
              data: [],
              label: {
                normal: {
                  show: true,
                  formatter: "{b}: ({d}%)",
                  textStyle: { //图例文字的样式
                    fontSize: 20
                  }
                },

              }
            }
          ]
        }
      };
    }
  };
</script>
