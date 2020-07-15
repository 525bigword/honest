package com.xr.run.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper,SysLog> implements SysLogService {
    @Autowired
    private SysStaffMapper sysStaffMapper;

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
        Integer sysLogCountByLETime = baseMapper.findSysLogCountByLETime(now.toString());
        List<Integer> sysLogIdByLETime = baseMapper.findSysLogIdByLETime(now.toString(),sysLogCountByLETime/5);
        sysLogIdByLETime.forEach(item->{
            baseMapper.delSysLogById(item);
        });

    }
}
