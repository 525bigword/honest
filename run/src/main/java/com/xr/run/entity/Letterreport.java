package com.xr.run.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class Letterreport {

  private int lid;
  private int letterId;
  @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")//页面写入数据库时格式化
  @JSONField(format="yyyy-MM-dd HH:mm:ss")//数据库导出页面时json格式化
  private Date lTime;
  private String lComplainantName;
  private int lDeptId;
  private int lPostId;
  private String lPersonBeReported;
  private int lPbrDeptId;
  private int lPbrPostId;
  private String lContent;
  private String lSynopsis;
  private String lSupervisionComments;
  private String lSupervisionCommentsTime;
  private String lDisciplinaryComments;
  @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")//页面写入数据库时格式化
  @JSONField(format="yyyy-MM-dd HH:mm:ss")//数据库导出页面时json格式化
  private Date lDisciplinaryTime;
  private String lLeadersComments;
  private Date lLeadersTime;
  private String lResult;
  private Date lResultTime;
  private String lSupervisionResutl;
  @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")//页面写入数据库时格式化
  @JSONField(format="yyyy-MM-dd HH:mm:ss")//数据库导出页面时json格式化
  private Date lSupervisionResultTime;
  @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")//页面写入数据库时格式化
  @JSONField(format="yyyy-MM-dd HH:mm:ss")//数据库导出页面时json格式化
  private Date lCreateTime;
  private int lCreateId;
  private String lCreateName;
  private int lStatus;


}
