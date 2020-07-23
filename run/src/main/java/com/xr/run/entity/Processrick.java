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
  private String proYear;
  /*
    private String proBranch;
  */
  private String proAccessory;
  private String proAccessoryName;
  private String proInfomation;
  private String proMeasures;
  private String proGrade;
  @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")//页面写入数据库时格式化
  @JSONField(format="yyyy-MM-dd HH:mm:ss")//数据库导出页面时json格式化
  private Date proCreateTime;
  private int proCreateId;
  private SysStaff sysStaff;
  private String proCreateName;
  private int proStatus;

  private String pname="所有部门"; //岗位
  private String mname ="所有岗位"; //部门

}
