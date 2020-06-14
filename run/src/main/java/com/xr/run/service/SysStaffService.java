package com.xr.run.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xr.run.entity.EducationPolitics;
import com.xr.run.entity.SysStaff;
import lombok.Data;

import java.util.Date;
import java.util.List;


public interface SysStaffService extends IService<SysStaff> {

    IPage<SysStaff> findSysStaffAll(Page<SysStaff> page);

    /**
     * 登录
     */
    JSONObject authLogin(JSONObject jsonObject);
    /**
     * 根据用户名和密码查询对应的用户
     *
     * @param username 用户名
     * @param password 密码
     */
    SysStaff getUser(String username, String password);
    /**
     * 查询当前登录用户的权限等信息
     */
    JSONObject getInfo();

    /**
     * 退出登录
     */
    JSONObject logout();
}
