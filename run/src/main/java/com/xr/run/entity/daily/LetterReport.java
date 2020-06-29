package com.xr.run.entity.daily;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class LetterReport {
    private Integer lid;
    private String letterId;
    private String lTime;
    private String lComplainantName;
    private String lDeptId;
    private String lPostId;
    private String lPbrDeptId;
    private String lPbrPostId;
    private String lPersonBeReported;
    private String lContent;
    private String lSynopsis;
    private String lSupervisionComments;
    private String lSupervisionCommentsTime;
    private String lDisciplinaryComments;
    private String lDisciplinaryTime;
    private String lLeadersComments;
    private String lLeadersTime;
    private String lResult;
    private String lResultTime;
    private String lSupervisionResutl;
    private String lSupervisionResultTime;
    private String lCreateTime;
    private Integer lCreateId;
    private String lCreateName;
    private Integer lStatus;
}
