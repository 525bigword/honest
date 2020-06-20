package com.xr.run;

import com.xr.run.entity.SysPost;
import com.xr.run.service.SysPermissionService;
import com.xr.run.service.SysPostPermissionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.io.File;
import java.util.List;

@SpringBootTest
class RunApplicationTests {
    @Autowired
    DataSource dataSource;//Druid数据源
    @Autowired
    private SysPostPermissionService sysPostPermissionService;
    @Test
    void contextLoads() {
        System.out.println(dataSource.getClass());
    }
    @Test
    public void test(){
//        List<SysPost> treeSysPost = sysPostPermissionService.findTreeSysPost();
    }

}
