package com.xr.run.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.xr.run.entity.duty.Post;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Data
public class SysStaff {
//"select sid,name,sex,age,educational_background,political_appearance,phone,mid,username,password,pid," +
//        "create_time,create_id,staus"
  private int sid;
  private String name;
  private String sex;
  private int age;
  private String educationalBackground;
  private String politicalAppearance;
  private String phone;
  private int mid;
  private String username;
  private String password;
  private String pid;
  private Integer ppid;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
  private Date createTime;
  private int createId;
  private String staus;
  //TODO 业务属性
  private String mechanismname;
  private String postname;
  private String ppname;
  private List<SysMechanism> mechanisms=new ArrayList<>();
  private List<Post> posts=new ArrayList<>();
}
