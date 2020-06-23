package com.xr.run.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class SysLog {

  private int id;
  private String title;
  private int sid;
  private String url;
  private String method;
  private String ip;
  private String data;
  private String terminal;
  private String type;
  @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")//页面写入数据库时格式化
  @JSONField(format="yyyy-MM-dd HH:mm:ss")//数据库导出页面时json格式化
  private Date time;
  //TODO 业务属性
  private String sname;

}
