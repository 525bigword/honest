package com.xr.run.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class SystemMessage {
    private int aid;
    private String articletitle;
    private String attribute;
    private String picture;
    private String picturename;
    private String articlesource;
    private String describe;
    private String content;
    private int status;
    private Integer createId;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")//页面写入数据库时格式化
    @JSONField(format="yyyy-MM-dd HH:mm:ss")//数据库导出页面时json格式化
    private Date createTime;
    private SysStaff sysStaff;
    public int getcreateId() {
        if (createId!=null){
            return createId;
        }
        return sysStaff.getSid();
    }
}
