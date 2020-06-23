package com.xr.run.dao.provider;

import com.alibaba.druid.util.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class SYsLogProvider {
    public String findmhSysLog(@Param("begin") Date begin, @Param("end")Date end, @Param("ip")String ip, @Param("sid")Integer sid){
        SQL sql=new SQL();
        sql.SELECT("id,sid,url,method,ip,data,terminal,type,time");
        sql.FROM("sys_log");
        if(begin!=null&&end!=null){
            sql.WHERE("time between #{begin} and #{end}");
        }
        if(!StringUtils.isEmpty(ip)){
            sql.WHERE("ip =#{ip}");
        }
        if(null!=sid&&null!=sid){
            sql.WHERE("sid =#{sid}");
        }
        sql.ORDER_BY("id desc");
        return sql.toString();
    }
}
