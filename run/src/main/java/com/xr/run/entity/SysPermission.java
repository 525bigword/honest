package com.xr.run.entity;

import lombok.Data;

@Data
public class SysPermission {

  private int id;
  private String menuCode;
  private String menuName;
  private String permissionCode;
  private String permissionName;
  private int requiredPermission;

}
