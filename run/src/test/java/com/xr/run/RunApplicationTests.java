package com.xr.run;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xr.run.dao.SpvBackMapper;
import com.xr.run.dao.SpvDutyMapper;
import com.xr.run.entity.SpvBack;
import com.xr.run.entity.SysMechanism;
import com.xr.run.entity.SysPost;
import com.xr.run.service.SysPermissionService;
import com.xr.run.service.SysPostPermissionService;
import net.minidev.json.JSONArray;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class RunApplicationTests {
    @Autowired
    DataSource dataSource;//Druid数据源
    @Autowired
    private SpvBackMapper spvBackMapper;
    @Autowired
    private SysPostPermissionService sysPostPermissionService;
    @Test
    void contextLoads() {
        System.out.println(dataSource.getClass());
    }
    @Test
    public void test(){
        Page page=new Page(1,5);
        IPage<SpvBack> spvBack = spvBackMapper.findSpvBack(page, "");
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
