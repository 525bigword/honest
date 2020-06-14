package com.xr.run.entity;

import lombok.Data;

import java.util.Date;

@Data
public class SpvBack {

  private int sid;
  private String backType;
  private String backTitle;
  private String backContent;
  private int bid;
  private String backAccessory;
  private Date newTime;
  private int eid;
  private int did;
  private int status;


}
