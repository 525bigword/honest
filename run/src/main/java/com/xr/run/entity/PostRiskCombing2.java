package com.xr.run.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;


@Component
@Data
public class PostRiskCombing2 implements Serializable {
    private Integer pcid;

    private String priskid;

    private String pyear;

    private Integer pdeptid;

    private Integer pinfomationid;

    private SysMechanism sysMechanism;

    private SysPost sysPost;

    private String pproject;

    private String priskpointdescription;

    private String pprobablelvalue;

    private String pcvalue;

    private String pdvalue;

    private String pgrade=null;

    private String pmeasures;

    @DateTimeFormat(pattern = "yyyy-MM-dd") // 前台传过来的日期格式转换
    @JsonFormat(pattern = "yyyy-MM-DD HH:mm:ss") // 后台的JAVA类型，转为JSON字符串
    private Date pcreatetime;

    private Integer pcreateid;

    private String pcreatename;

    private Integer pstatus;

    private static final long serialVersionUID = 1L;


}
