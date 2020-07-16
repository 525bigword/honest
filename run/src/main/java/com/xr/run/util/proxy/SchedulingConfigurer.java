package com.xr.run.util.proxy;

import com.xr.run.dao.SysLogMapper;
import com.xr.run.entity.SysLog;
import com.xr.run.service.SysLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.Calendar;

//@Configuration     //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
@Slf4j
public class SchedulingConfigurer {
    @Autowired
    private SysLogService sysLogService;
    //3.添加定时任务

    /**
     * 没个月最后一天凌晨1一点开始每个小时执行一次
     */
    @Scheduled(cron = "1 1 1/1 L * ? ")//* * 1/5 L * ? 1 1 1/1 L * ?
    //@Scheduled(cron = "*/20 * * * * *")  //每个月31号执行
    //或直接指定时间间隔，例如：5秒
    //@Scheduled(fixedRate=5000)
    private void configureTasks() {
        sysLogService.delSysLogByTimeSixMonth();
        log.info("当前北京时间："+ LocalDateTime.now()+"已经完成对10天前日志的删除");
    }
}
