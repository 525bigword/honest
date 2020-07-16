package com.xr.run.entity.educationpolitics;

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
  private Date time;
  @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")//页面写入数据库时格式化
  private Date createTime;
  private Integer createId;
  private String createname;
  private String staus;
//业务属性
  private String starttime;
  private String endtime;
  private String cname;

  private Integer firstPage; //最上面那一页面
}
