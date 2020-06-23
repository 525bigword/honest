package com.xr.run.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xr.run.entity.SysLog;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface SysLogMapper extends BaseMapper<SysLog> {

    @Insert("insert into sys_log value(null,#{log.sid},#{log.url},#{log.method},#{log.ip},#{log.data},#{log.terminal},#{log.type},#{log.time})")
    void addSysLog(@Param("log") SysLog sysLog);
//    @Select({"<script> " +
//            "select id,sid,url,method,ip,data,terminal,type,time from sys_log " +
//            "where  1=1 " +
//            "<if test='begin!=null && end!=null'> and date between #{begin} and #{end}</if> " +
//            "<if test='sid!=null||sid!=0 '> and sid = #{sid} </if> " +
//            "<if test='ip!=null||ip!='' '> and ip = #{sid} </if> " +
//            "</script>"})
    @SelectProvider(type = com.xr.run.dao.provider.SYsLogProvider.class,method = "findmhSysLog")
    @Results({
            @Result(column = "sid",property = "sid"),
            @Result(column = "sid",property = "sname",one = @One(select = "com.xr.run.dao.SysStaffMapper.findSysStaffByIdToName"))
    })
    IPage<SysLog> findSysLog(Page page, @Param("begin") Date begin, @Param("end")Date end, @Param("ip")String ip, @Param("sid")Integer sid);
}
