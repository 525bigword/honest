package com.xr.run.entity.daily;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
public class LetterReport {
    private Integer lid;
    private String letterId;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")//页面写入数据库时格式化
    private Date lTime;
    private String lComplainantName;
    private String lDeptId;
    private String lPostId;
    private String lPbrDeptId;
    private String lPbrPostId;
    private String lPersonBeReported;
    private String lContent;
    private String lSynopsis;
    private String lSupervisionComments;
    private String puni;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")//页面写入数据库时格式化
    private Date lSupervisionCommentsTime;
    private String lDisciplinaryComments;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")//页面写入数据库时格式化
    private Date lDisciplinaryTime;
    private String lLeadersComments;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")//页面写入数据库时格式化
    private Date lLeadersTime;
    private String lResult;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")//页面写入数据库时格式化
    private Date lResultTime;
    private String lSupervisionResutl;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")//页面写入数据库时格式化
    private Date lSupervisionResultTime;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")//页面写入数据库时格式化
    private Date lCreateTime;
    private Integer lCreateId;
    private String lCreateName;
    private Integer lStatus;
}
