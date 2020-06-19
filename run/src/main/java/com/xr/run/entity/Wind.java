package com.xr.run.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class Wind {
    private int wid;
    private String wTitle;
    private String wContent;
    private Integer wContributor;
    private SysStaff wnew;
    public int getwContributor() {
        if (wContributor!=null){
            return wContributor;
        }
        return wnew.getSid();
    }
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")//页面写入数据库时格式化
    @JSONField(format="yyyy-MM-dd HH:mm:ss")//数据库导出页面时json格式化
    private Date wCreateTime;
    private SysStaff sysStaff;
    private Integer wCreateId;
    public int getwCreateId() {
        if (wCreateId!=null){
            return wCreateId;
        }
        return sysStaff.getSid();
    }
    private int wStatus;
}
