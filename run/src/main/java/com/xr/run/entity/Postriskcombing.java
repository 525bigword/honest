package com.xr.run.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class Postriskcombing {

  private int pid;
  private int pRiskId;
  private String pYear;
  private int pDeptId;
  private int pInfomationId;
  private String pProject;
  private String pRiskPointDescription;
  private String pProbableLValue;
  private String pCValue;
  private String pDValue;
  private String pGrade;
  private String pMeasures;
  @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")//页面写入数据库时格式化
  @JSONField(format="yyyy-MM-dd HH:mm:ss")//数据库导出页面时json格式化
  private Date pCreateTime;
  private int pCreateId;
  private String mname ="所有部门";  //部门
  private String pname ="所有岗位"; //岗位
  private  SysStaff sysStaff;
  private String pCreateName;
  private int pStatus;
  private String deptName;
  private String postname;



}
