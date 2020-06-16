package com.xr.run.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xr.run.dao.SysMechanismMapper;
import com.xr.run.dao.SysStaffMapper;
import com.xr.run.entity.SysMechanism;
import com.xr.run.service.SysMechanismService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysMechanismServiceImpl extends ServiceImpl<SysMechanismMapper,SysMechanism> implements SysMechanismService {
    @Autowired
    private SysStaffMapper sysStaffMapper;
    @Override
    public Map<String,Object> findSysMechanismPage(Integer pageNum,  Integer pageRow, String mechanism, String principal, String staus) {
        List<Integer> sysStaffByNameToId = sysStaffMapper.findSysStaffByNameToId(principal);
        String ids="(";
        for (Integer sid : sysStaffByNameToId) {
            ids+=sid+",";
        }
        ids=ids.substring(0,ids.length()-1);
        ids+=")";
        pageNum=pageNum-1;
        pageNum=pageNum*pageRow;
        List<SysMechanism> sysMechanismPage = baseMapper.findSysMechanismPage(pageNum, pageRow, mechanism, ids, staus);
        Integer sysMechanismPageCount = baseMapper.findSysMechanismPageCount(mechanism, ids, staus);
        Map<String,Object> map=new HashMap<>();
        map.put("data",sysMechanismPage);
        map.put("count",sysMechanismPageCount);
        return map;
    }

    @Override
    public List<SysMechanism> findSysMechanismAll() {
        List<SysMechanism> sysMechanismByParentAll = baseMapper.findSysMechanismByParentAll(0);
        List<SysMechanism> sysMechanismAllchrlen = findSysMechanismAllchrlen(sysMechanismByParentAll);
        return sysMechanismByParentAll;
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
    public static void main(String[] args) {
        List<Integer> list=new ArrayList<>();
        list.add(1);
        list.add(12);
        list.add(2);
        String id="(";
        for (Integer i : list) {
            id+=i+",";
        }
        id=id.substring(0,id.length()-1);
        id+=")";
        System.out.println(id);
    }
}
