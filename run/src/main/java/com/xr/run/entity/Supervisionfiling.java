package com.xr.run.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class Supervisionfiling {

  private int sid;
  private int sFilingId;
  private String sPaperItems;
  private String sAccessory;
  private String sEnforcementMode;
  private String sCost;
  private String sUndertaker;
  private int sUndertakerDeptId;
  private String sAuditOpinion;
  @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")//页面写入数据库时格式化
  @JSONField(format="yyyy-MM-dd HH:mm:ss")//数据库导出页面时json格式化
  private Date sCreateTime;
  private int sCreateId;
  private String sCreateName;
  private int sStatus;

}
