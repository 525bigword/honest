package com.xr.run.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xr.run.dao.DBLogTableMapper;
import com.xr.run.dao.SysLogMapper;
import com.xr.run.dao.SysStaffMapper;
import com.xr.run.entity.SysLog;
import com.xr.run.entity.SysStaff;
import com.xr.run.service.SysLogService;
import com.xr.run.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper,SysLog> implements SysLogService {
    @Autowired
    private SysStaffMapper sysStaffMapper;
    @Autowired
    private DBLogTableMapper dbLogTableMapper;
    @Override
    public void addSysLog(SysLog sysLog) {
        baseMapper.addSysLog(sysLog);
    }

    @Override
    public IPage<SysLog> findSysLog(Page page, Date begin, Date end, String ip, String username) {
        Integer sid =null;
        if(!StringUtils.isEmpty(username)){
            sid= sysStaffMapper.findSysStaffByUserName(username);
        }
        IPage<SysLog> sysLog = baseMapper.findSysLog(page, begin, end, ip, sid);

        return sysLog;
    }

    @Override
    public void delSysLogByTimeSixMonth() {
        LocalDateTime now = LocalDateTime.now();
        now = now.minus(6, ChronoUnit.DAYS);

        System.out.println(now.toString());
        //查询6个月之前的信息
        List<SysLog> sysLogIdByLETime = baseMapper.findSysLogIdByLETime(now.toString());
        //创建备份日志表表名
        String format = DateTimeFormatter.ofPattern("yyyy_MM_dd").format(now);
        String tableName ="sys_log_"+format;
        //是否存在该表
        String hasTableName = dbLogTableMapper.findTableNameCount(tableName);
        //如果不存在
        if(StringUtils.isEmpty(hasTableName)){
            //创建该表
            dbLogTableMapper.createNewTable(tableName);
            //向该表插入需要备份的信息然后删除
            sysLogIdByLETime.forEach(item ->{
                dbLogTableMapper.insertByTableNameSysLog(tableName,item);
                baseMapper.delSysLogById(item.getId());
            });
        }
    }
}
