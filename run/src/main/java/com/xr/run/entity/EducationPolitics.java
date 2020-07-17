package com.xr.run.entity;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.models.auth.In;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class EducationPolitics {

  private int id;
  private String title;
  private String source;
  private String type;
  private String content;
  @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")//页面写入数据库时格式化
  @JSONField(format="yyyy-MM-dd HH:mm:ss")//数据库导出页面时json格式化
  private Date time;
  @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")//页面写入数据库时格式化
  @JSONField(format="yyyy-MM-dd HH:mm:ss")//数据库导出页面时json格式化
  private Date createTime;
  private Integer createId;
  private String staus;
  private String cname;
  private Integer next;
  private String surl;

}
