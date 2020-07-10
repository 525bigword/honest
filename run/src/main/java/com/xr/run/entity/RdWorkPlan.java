package com.xr.run.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class RdWorkPlan {

  private int id;
  private Integer rdid;
  private String title;
  private String content;
  @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")//页面写入数据库时格式化
  @JSONField(format="yyyy-MM-dd HH:mm:ss")//数据库导出页面时json格式化
  private Date createTime;
  private int createId;
  private  SysStaff sysStaff;
  private String createName;
  private String staus;

}
