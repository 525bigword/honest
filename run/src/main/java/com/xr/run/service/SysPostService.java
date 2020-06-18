package com.xr.run.service;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xr.run.entity.EducationPolitics;
import com.xr.run.entity.SysPost;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;
import java.util.List;


public interface SysPostService extends IService<SysPost> {
    List<SysPost> findSysPostPage(Integer pageNum, Integer pageRow, JSONObject jsonObject);
    Integer findSysPostPage(JSONObject jsonObject);
    void addSysPost(SysPost sysPost) throws Exception;
    void upSysPost(SysPost sysPost);
    //TODO 批量删除岗位
    void delSysPost(String[] arr);
}
