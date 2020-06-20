package com.xr.run.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xr.run.dao.SysPostPermissionMapper;
import com.xr.run.entity.SysPermission;
import com.xr.run.entity.SysPost;
import com.xr.run.entity.SysPostPermission;
import com.xr.run.service.SysPostPermissionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class SysPostPermissionServiceImpl extends ServiceImpl<SysPostPermissionMapper,SysPostPermission> implements SysPostPermissionService {

    @Override
    public List<SysPermission> findTreeSysPost() {
        List<SysPermission> sysPermissions = baseMapper.findSysPostByList();
        for (SysPermission sysPermission : sysPermissions) {
            List<SysPermission> sysPostUnList = baseMapper.findSysPostUnList(sysPermission.getId());
            sysPermission.setSysPermissions(sysPostUnList);
            sysPermission.setId(new Random().nextInt());
        }
        System.out.println(sysPermissions);
        return sysPermissions;
    }

    @Override
    public List<Integer> findSysPostPermissionByPostIdToPid(Integer postid) {
        List<Integer> sysPostPermissionByPostIdToPid = baseMapper.findSysPostPermissionByPostIdToPid(postid);
        return sysPostPermissionByPostIdToPid;
    }

    public static void main(String[] args) {
        Random random = new Random();
        System.out.println(random.nextInt());
    }
}
