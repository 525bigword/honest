package com.xr.run.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xr.run.dao.SysPostPermissionMapper;
import com.xr.run.entity.SysPermission;
import com.xr.run.entity.SysPost;
import com.xr.run.entity.SysPostPermission;
import com.xr.run.entity.SysPpost;
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

    @Override
    public List<SysPpost> findSysPostPage(Integer pageNum, Integer pageRow, JSONObject jsonObject) {
        //String str=jsonObject.getString("mids");\
        //AND mid in CONCAT('(',#{json.mids},')')
        pageNum=pageNum-1;
        pageNum=pageNum*pageRow;
        int mids = 0;
        if(!StringUtils.isEmpty(jsonObject.getString("mids"))){
            mids=Integer.parseInt(jsonObject.getString("mids"));
        }
        String str="";
        if(mids!=0){
            str="and mid ="+mids;
        }
        System.out.println(str);
        List<SysPpost> sYsPostPage = baseMapper.findSysPpostPage(pageNum,pageRow,jsonObject.getString("message") ,jsonObject.getString("pname"),str);
        System.out.println(sYsPostPage);
        return sYsPostPage;
    }

    @Override
    public Integer findSysPostPage(JSONObject jsonObject) {
        int mids = 0;
        if(!StringUtils.isEmpty(jsonObject.getString("mids"))){
            mids=Integer.parseInt(jsonObject.getString("mids"));
        }
        String str="";
        if(mids!=0){
            str="and mid ="+mids;
        }
        Integer count = baseMapper.findSysPpostPagecount(jsonObject.getString("message"), jsonObject.getString("pname"),str);
        System.out.println(count);
        return count;
    }

    @Override
    public void add(SysPpost sysPpost) {
        baseMapper.addSysPpost(sysPpost);
    }

    @Override
    public void upSysPost(SysPpost sysPpost) {
        baseMapper.upSysPpost(sysPpost);
    }

    @Override
    public void delSysPost(String[] splice) {
        for (String s : splice) {
            baseMapper.delSysPpost(Integer.parseInt(s));
        }
    }

    @Override
    public List<SysPpost> findSysPpostByMid(Integer mid) {
        return baseMapper.findSysPpostByMid(mid);
    }

    public static void main(String[] args) {
        Random random = new Random();
        System.out.println(random.nextInt());
    }
}
