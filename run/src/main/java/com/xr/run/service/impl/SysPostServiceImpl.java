package com.xr.run.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xr.run.dao.SysPostMapper;
import com.xr.run.dao.SysPostPermissionMapper;
import com.xr.run.entity.SysPost;
import com.xr.run.service.SysPostService;
import com.xr.run.util.DateUtil;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SysPostServiceImpl extends ServiceImpl<SysPostMapper,SysPost> implements SysPostService {
    @Autowired
    private SysPostPermissionMapper sysPostPermissionMapper;

    @Override
    public List<SysPost> findSysPostPage(Integer pageNum,  Integer pageRow, JSONObject jsonObject) {
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
        List<SysPost> sYsPostPage = baseMapper.findSYsPostPage(pageNum,pageRow,jsonObject.getString("message") ,jsonObject.getString("pname"),str);
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
        Integer count = baseMapper.findSYsPostPagecount(jsonObject.getString("message"), jsonObject.getString("pname"),str);
        System.out.println(count);
        return count;
    }

    @Override
    public void addSysPost(SysPost sysPost) throws Exception {
        Date date = DateUtil.getDate();
        sysPost.setCreateTime(date);
        System.out.println(sysPost);
        baseMapper.addSysPost(sysPost);
        String[] split = sysPost.getArr().split(",");
        for (String s : split) {
            sysPostPermissionMapper.addSysPostPermission(sysPost,Integer.parseInt(s));
        }
    }

    @Override
    public void upSysPost(SysPost sysPost) {
        baseMapper.upSysPost(sysPost);
        System.out.println(sysPost);
        int pid = sysPost.getPid();
        sysPostPermissionMapper.delSysPostPermissionByPostId(pid);
        String[] split = sysPost.getArr().split(",");
//        Date date = DateUtil.getDate();
        for (String s : split) {
            sysPostPermissionMapper.addSysPostPermission(sysPost,Integer.parseInt(s));
        }
    }

    @Override
    public void delSysPost(String[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
            baseMapper.delSysPost(Integer.parseInt(arr[i]));
        }
    }

    @Override
    public List<SysPost> getSysPost() {
        return baseMapper.getSysPost();
    }

    @Override
    public List<SysPost> getAllSysPost() {
        return baseMapper.getSysPostAll();
    }
}
