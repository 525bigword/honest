package com.xr.run.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xr.run.dao.SysMechanismMapper;
import com.xr.run.dao.SysPostMapper;
import com.xr.run.dao.SysStaffMapper;
import com.xr.run.entity.SysMechanism;
import com.xr.run.service.SysMechanismService;
import com.xr.run.service.SysPostService;
import com.xr.run.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class SysMechanismServiceImpl extends ServiceImpl<SysMechanismMapper,SysMechanism> implements SysMechanismService {
    @Autowired
    private SysStaffMapper sysStaffMapper;
    @Autowired
    private SysPostMapper sysPostMapper;

    public Map<String, Object> findSysMechanismPage(Integer pageNum, Integer pageRow, String mechanism, String principal, String staus) {
//        List<Integer> sysStaffByNameToId = sysStaffMapper.findSysStaffByNameToId(principal);
//        String ids="(";
//        for (Integer sid : sysStaffByNameToId) {
//            ids+=sid+",";
//        }
//        ids=ids.substring(0,ids.length()-1);
//        ids+=")";
        pageNum=pageNum-1;
        pageNum=pageNum*pageRow;
        List<SysMechanism> sysMechanismPage = baseMapper.findSysMechanismPage(principal,pageNum, pageRow, mechanism, staus);
        Integer sysMechanismPageCount = baseMapper.findSysMechanismPageCount(principal,mechanism, staus);
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("data",sysMechanismPage);
        map.put("count",sysMechanismPageCount);
        return map;
    }


    public List<SysMechanism> findSysMechanismAll() {
        List<SysMechanism> sysMechanismByParentAll = baseMapper.findSysMechanismByParentAll(0);
        List<SysMechanism> sysMechanismAllchrlen = findSysMechanismAllchrlen(sysMechanismByParentAll);
        return sysMechanismByParentAll;
    }

    @Override
    public void addSysMechanism(SysMechanism sysMechanism) {
        Date date = DateUtil.getDate();
        sysMechanism.setCreateTime(date);
        baseMapper.addSysMechanism(sysMechanism);
    }

    @Override
    public Integer delSysMechanism(Integer mid) {
        List<SysMechanism> sysMechanismByParentAll = baseMapper.findSysMechanismByParentAll(mid);
        List<SysMechanism> sysMechanismAllchrlen = findSysMechanismAllchrlen(sysMechanismByParentAll);
        if(sysMechanismAllchrlen.size()>0){
            return 1;
        }
        if(sysPostMapper.findSysPostByMidTocount(mid)>0){
            return 1;
        }
        if(sysStaffMapper.findSysStaffByMidToCount(mid)>0){
            return 1;
        }
        baseMapper.delSysMechanism(mid);
        return 0;
    }

    @Override
    public void unDelSysMechanism(Integer mid) {
        baseMapper.UnDelSysMechanism(mid);
    }

    @Override
    public void upSysmechanism(SysMechanism sysMechanism) {
        baseMapper.upSysMechanismByMid(sysMechanism);
    }

    @Override
    public List<SysMechanism> findAllSysMechanism() {
        return baseMapper.findSysMechanism();
    }

    @Override
    public List<SysMechanism> findSysMechanisms() {
        return baseMapper.findSysMechanisms();
    }


    private List<SysMechanism> findSysMechanismAllchrlen(List<SysMechanism> sysMechanismList){
        List<SysMechanism> sysMechanisms=new ArrayList<>();
            for (SysMechanism sysMechanism : sysMechanismList) {
            List<SysMechanism> sysMechanismByParentAll = baseMapper.findSysMechanismByParentAll(sysMechanism.getMid());
            findSysMechanismAllchrlen(sysMechanismByParentAll);
            sysMechanism.setChilrenMechanism(sysMechanismByParentAll);
            sysMechanisms.add(sysMechanism);
        }
        return sysMechanisms;
    }
//    public static void main(String[] args) {
//        long time = new Date().getTime();
//        System.out.println(time);
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
//        String date = df.format(time);// new Date()为获取当前系统时间，也可使用当前时间戳
//        System.out.println(date);
//    }
}
