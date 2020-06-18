package com.xr.run.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xr.run.dao.SysPostMapper;
import com.xr.run.entity.SysPost;
import com.xr.run.service.SysPostService;
import org.apache.poi.util.StringUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysPostServiceImpl extends ServiceImpl<SysPostMapper,SysPost> implements SysPostService {

    @Override
    public List<SysPost> findSysPostPage(Integer pageNum,  Integer pageRow, JSONObject jsonObject) {
        //String str=jsonObject.getString("mids");\
        //AND mid in CONCAT('(',#{json.mids},')')
        String str=null;
        if(!StringUtils.isEmpty(jsonObject.getString("mids"))) {
            str = "AND mid in (" + jsonObject.getString("mids") + ")";
        }
        System.out.println(str);
        pageNum=pageNum-1;
        pageNum=pageNum*pageRow;
        List<SysPost> sYsPostPage = baseMapper.findSYsPostPage(pageNum,pageRow,jsonObject.getString("message") ,jsonObject.getString("pname"),str);
        System.out.println(sYsPostPage);
        return sYsPostPage;
    }

    @Override
    public Integer findSysPostPage(JSONObject jsonObject) {
        String str=null;
        if(!StringUtils.isEmpty(jsonObject.getString("mids"))) {
            str = "AND mid in (" + jsonObject.getString("mids") + ")";
        }
        System.out.println(str);
        Integer count = baseMapper.findSYsPostPagecount(jsonObject.getString("message"), jsonObject.getString("pname"), str);
        System.out.println(count);
        return count;
    }
}
