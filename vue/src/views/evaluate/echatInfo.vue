<template>
  <div id="chartLineBox" style="margin-left:-5%;width: 100%;height: 70vh;"></div>
</template>

<script>
import echarts from "echarts";
export default {
  data() {
    return {
      series: [],
      data: [],
      titleText:""
    };
  },
  methods: {
    echat() {
      this.chartLine = echarts.init(document.getElementById("chartLineBox"));
if(this.$route.params.type===0){
          this.titleText='一'
      }else if(this.$route.params.type===1){
          this.titleText='二'
      }else{
           this.titleText='三'
      }
      // 指定图表的配置项和数据
      var option = {
        title: {
          text: this.titleText+"级风险柱状统计图",
          left: "center",
        //   top: "center",
          textStyle: {
            color: "#FA6F53"
          }
        },
        tooltip: {
          //设置tip提示
          trigger: "axis"
        },
        grid: {
          left: "3%",
          right: "3%",
          bottom: "3%",
          containLabel: true
        },
        xAxis: {
          //设置x轴
          type: "category",
          boundaryGap: true, //坐标轴两边不留白
          data: this.data,
          name: "部\n门", //X轴 name
          nameTextStyle: {
            //坐标轴名称的文字样式
            color: "#FA6F53",
            fontSize: 16,
            padding: [0, 0, 0, 0]
          },
          axisLine: {
            //坐标轴轴线相关设置。
            lineStyle: {
              color: "#FA6F53"
            }
          }
        },
        yAxis: {
          name: "数量",
          nameTextStyle: {
            color: "#FA6F53",
            fontSize: 16,
            padding: [0, 0, 10, 0]
          },
          axisLine: {
            lineStyle: {
              color: "#FA6F53"
            }
          },
          type: "value"
        },
        series: [
          {
            name: "学生成绩",
            data: this.series,
            barWidth: "10%",
            barCateGoryGap: 20,

            type: "bar",
            lineStyle: {
              normal: {
                color: this.getColor
              }
            }
          }
        ]
      };

      // 使用刚指定的配置项和数据显示图表。
      this.chartLine.setOption(option);
    },
    getColor() {
      /*随机产生十六进制的颜色值*/
      let i = 0;
      let str = "#";
      let random = 0;
      let aryNum = [
        "0",
        "1",
        "2",
        "3",
        "4",
        "5",
        "6",
        "7",
        "8",
        "9",
        "A",
        "B",
        "C",
        "D",
        "E",
        "F"
      ];

      for (i = 0; i < 6; i++) {
        random = parseInt(Math.random() * 16);
        console.log(random);
        str += aryNum[random];
      }
      return str;
    }
  },
  create() {
      
  },
  mounted() {
    this.api({
      url: "riskPointAssessment/echatsInfo",
      method: "get",
      params: {
        mids: this.$route.params.mids.join(","),
        type: this.$route.params.type
      }
    }).then(res => {
      console.log("res", res);
      this.data = [];
      //   this.data[0] = "";
      this.series = [];
      //   this.series.push(0);
      res.filter((item, index) => {
        this.data.push(item.name);
        this.series.push(item.value);
      });

      console.log("this.data", this.data);
      console.log("this.series", this.series);
      this.echat();
    });
  }
};
</script>

<style>
</style>