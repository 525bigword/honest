package com.xr.run.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
public class Datacollection {

  private int did;
  private String dTitle;
  private String dFileName;
  private String dFile;
  @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")//页面写入数据库时格式化
  @JSONField(format="yyyy-MM-dd HH:mm:ss")//数据库导出页面时json格式化
  private Date dCreateTime;
  private SysStaff sysStaff;
  private Integer dCreateId;

  public int getdCreateId() {
    if (dCreateId!=null){
      return dCreateId;
    }
    return sysStaff.getSid();
  }

  private int dStatus;
}
