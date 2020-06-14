package com.xr.run.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class Riskdisposition {

  private int id;
  private String dispositionTitile;
  private String dispositionContent;
  @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")//页面写入数据库时格式化
  @JSONField(format="yyyy-MM-dd HH:mm:ss")//数据库导出页面时json格式化
  private Date createDate;
  private int departmentId;
  private int positionId;
  private int riskLevelId;
  private String disposeResult;
  private int loginId;
  private String createName;
  private int state;


}
