package com.xr.run.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class Systemprocess {

  private int sysid;
  private String sysTitle;
  private String sysContent;
  @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")//页面写入数据库时格式化
  @JSONField(format="yyyy-MM-dd HH:mm:ss")//数据库导出页面时json格式化
  private Date sysCreateTime;
  private Integer sysCreateId;
  private SysStaff sysStaff;
  public int getsysCreateId() {
    if (sysCreateId!=null){
      return sysCreateId;
    }
    return sysStaff.getSid();
  }
  private int sysStatus;


}
