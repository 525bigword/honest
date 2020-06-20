package com.xr.run.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysPermission {

  private int id;
  private String menuCode;
  private String menuName;
  private String permissionCode;
  private String permissionName;
  private int requiredPermission;
  //TODO 业务属性
  private List<SysPermission> sysPermissions;
}
