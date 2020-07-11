package com.xr.run.entity.inspect;

import com.xr.run.entity.SysStaff;
import com.xr.run.entity.SysStaff;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class Report {
    private Integer id;
    private String reportType;
    private String report;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")//页面写入数据库时格式化
    private Date newTime;
    private List<SysStaff> eid=new ArrayList<>();
    private Integer status;
    private String typeconcrete;
    //功能需求
    private Integer sid;
    private String url;
    private String starttime;
    private String endtime;
}
