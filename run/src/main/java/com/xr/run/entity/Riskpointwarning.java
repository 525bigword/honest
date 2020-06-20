package com.xr.run.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class Riskpointwarning {
  private int wid;
  private String numId;
  private String wTitle;
  private String wContent;
  @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")//页面写入数据库时格式化
  @JSONField(format="yyyy-MM-dd HH:mm:ss")//数据库导出页面时json格式化
  private Date wCreateTime;
  private SysStaff sysStaff;
  private Integer wCreateId;
  public int getwCreateId() {
    if (wCreateId!=null){
      return wCreateId;
    }
    return sysStaff.getSid();
  }
  private int wStatus;

}
