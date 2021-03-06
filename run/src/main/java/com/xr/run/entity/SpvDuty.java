package com.xr.run.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class SpvDuty {

  private int did;
  private String dnumId;
  private String dutyType;
  private String dutyTitle;
  private String dutyContent;
  private String bid;
  private String dutyAccessory;
  private String dutyAccessoryName;
  @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")//页面写入数据库时格式化
  @JSONField(format="yyyy-MM-dd HH:mm:ss")//数据库导出页面时json格式化
  private Date newTime;
  private Integer dCreateId;
  private SysStaff sysStaff;
  public int getdCreateId() {
    if (dCreateId!=null){
      return dCreateId;
    }
    return sysStaff.getSid();
  }
  private int status;
  private String tongbao;

}
