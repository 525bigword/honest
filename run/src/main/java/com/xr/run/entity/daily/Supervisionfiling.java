package com.xr.run.entity.daily;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Data
public class Supervisionfiling {
    private Integer sid;
    private String sFilingId;
    private String sPaperItems;
    private String sAccessory;
    private String sEnforcementMode;
    private String sCost;
    private String sUndertaker;
    private String sUndertakerDeptId;
    private String sAuditOpinion;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")//页面写入数据库时格式化
    private Date sCreateTime;
    private Integer sCreateId;
    private String sCreateName;
    private Integer sStatus;
    private String auditorrole;
    private String punit;
    //功能需求
    private String url;

}
