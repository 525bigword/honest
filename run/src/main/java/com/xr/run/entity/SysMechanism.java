package com.xr.run.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysMechanism {

  private int mid;
  private String mechanismName;
  private int sid;
  private int parent;
  private int branch;
  @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")//页面写入数据库时格式化
  @JSONField(format="yyyy-MM-dd HH:mm:ss")//数据库导出页面时json格式化
  private Date createTime;
  private int createId;
  private String staus;
  private int sort;
  //TODO 业务属性
  private String sname;
  private String parentName;
  private String branchName;
  private String createName;
  private List<SysMechanism> chilrenMechanism;
  private String lable;
  private Integer value;
  private String bmName;
}
