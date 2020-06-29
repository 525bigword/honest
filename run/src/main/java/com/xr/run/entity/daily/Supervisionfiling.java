package com.xr.run.entity.daily;

import lombok.Data;
import org.springframework.stereotype.Component;

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
    private String sCreateTime;
    private Integer sCreateId;
    private String sCreateName;
    private Integer sStatus;
    //功能需求
    private String url;
}
