package com.xr.run.entity.tam;

import com.xr.run.entity.SysStaff;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class Smokestyle {
    private Integer smoid;
    private String smotitle;
    private String smoproperty;
    private String smoimage;
    private String smosource;
    private String smoauthor;
    private String smocreatetime;
    private String smodescrion;
    private String smocontent;
private String surl;
    private String smocreatename;
    private String sstatus;
    //业务属性
    private List<SysStaff> smocreateid=new ArrayList<>();
    private Integer smocreateids;
}
