package com.xr.run.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.annotation.security.DenyAll;
import java.util.Date;

@Data
public class Creditrating {

  private int cid;
  private String reserved1;
  private String reserved2;
  @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")//页面写入数据库时格式化
  @JSONField(format="yyyy-MM-dd HH:mm:ss")//数据库导出页面时json格式化
  private Date cCreateTime;
  private int cCreateId;
  private String cCreateName;
  private int cStatus;
}
