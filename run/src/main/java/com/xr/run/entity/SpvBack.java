package com.xr.run.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
public class SpvBack {
  private int sid;
  private String backType;
  private String backTitle;
  private String backContent;
  private Integer bid;
  private SysMechanism sysMechanism;
  public int getbid() {
    if (bid!=null){
      return bid;
    }
    return sysMechanism.getMid();
  }
  private String backAccessory;
  private String backAccessoryName;
  @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")//页面写入数据库时格式化
  @JSONField(format="yyyy-MM-dd HH:mm:ss")//数据库导出页面时json格式化
  private Date newTime;
  private Integer bCreateId;
  private SysStaff sysStaff;
  private List<SpvBack> spvBacks;
  public int getbCreateId() {
    if (bCreateId!=null){
      return bCreateId;
    }
    return sysStaff.getSid();
  }
  private String gettop;
  private String tongzhi;
  private int did;
  private int status;


}
