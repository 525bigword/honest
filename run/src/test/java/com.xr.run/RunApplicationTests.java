package com.xr.run;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xr.run.controller.WindController;
import com.xr.run.dao.DBLogTableMapper;
import com.xr.run.dao.SpvBackMapper;
import com.xr.run.dao.SysLogMapper;
import com.xr.run.entity.SpvBack;
import com.xr.run.entity.SysLog;
import com.xr.run.entity.SysMechanism;
import com.xr.run.service.HomePageSevice;
import com.xr.run.service.SysPostPermissionService;
import com.xr.run.service.impl.HomePageSeviceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import java.io.File;

import javax.sql.DataSource;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

@SpringBootTest
class RunApplicationTests {
    private static final int wdFormatPDF = 17; // PDF 格式
    @Autowired
    DataSource dataSource;//Druid数据源
    @Autowired
    private SpvBackMapper spvBackMapper;
    @Autowired
    private SysPostPermissionService sysPostPermissionService;
    @Autowired
    private HomePageSeviceImpl homePageSevice;
    @Autowired
    private WindController windController;
    @Autowired
    private DBLogTableMapper dbLogTableMapper;
//    @Value("${server.context-path}")
//    private String path="";
    @Test
    public void testDBLogTableMapper(){
        //查询是否有对应表
        String tableNameCount = dbLogTableMapper.findTableNameCount("sys_log_2020");
        System.out.println(tableNameCount);

        if(StringUtils.isEmpty(tableNameCount)){
            dbLogTableMapper.createNewTable("sys_log_2020");
            SysLog sysLog=new SysLog();
            sysLog.setId(1);
            sysLog.setTime(new Date());
            sysLog.setType("Chrome");
            sysLog.setTerminal("pc");
            sysLog.setIp("127.0.0.1");
            sysLog.setUrl("/syslog/get/1");
            sysLog.setSid(1);
            sysLog.setData("[1]");
            sysLog.setMethod("get");
            dbLogTableMapper.insertByTableNameSysLog("sys_log_2020",sysLog);
        }
        //根绝表名创建日志表
    }
    @Test
    public void test(){
        Page page=new Page(1,5);
        IPage<SpvBack> spvBack = spvBackMapper.findSpvBack(page, 10);
        System.out.println(spvBack.getRecords());
    }
    public List<Map> getMap(List<SysMechanism> list){
        List<Map> mapList=new ArrayList<>();
        for (SysMechanism mechain:list) {
            Map<String,Object> map=new HashMap<>();
            map.put("value",mechain.getMid());
            map.put("lable",mechain.getMechanismName());
            List<Map> map1 = getMap(mechain.getChilrenMechanism());
            map.put("children",map1);
            mapList.add(map);
        }
        return mapList;
    }



}
