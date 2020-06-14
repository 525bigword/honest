package com.xr.run.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class Processrick {

  private int proid;
  private int processId;
  private String proName;
  @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")//页面写入数据库时格式化
  @JSONField(format="yyyy-MM-dd HH:mm:ss")//数据库导出页面时json格式化
  private Date proYear;
  private String proInfomation;
  private String proAccessory;
  private Date proCreateTime;
  private int proCreateId;
  private String proCreateName;
  private int proStatus;

}
