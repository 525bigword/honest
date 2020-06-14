package com.xr.run.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class Riskpointassessment {

  private int riskid;
  private String riskImfomation;
  private String numberOneRisks;
  private String numberTwoRisks;
  private String numberThreeRisks;
  @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")//页面写入数据库时格式化
  @JSONField(format="yyyy-MM-dd HH:mm:ss")//数据库导出页面时json格式化
  private Date riskCreateTime;
  private int riskCreateId;
  private String riskCreateName;
  private int riskStatus;

}
