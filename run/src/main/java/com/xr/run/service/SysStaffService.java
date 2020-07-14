package com.xr.run.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xr.run.entity.EducationPolitics;
import com.xr.run.entity.SysStaff;
import lombok.Data;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;
import java.util.List;


public interface SysStaffService extends IService<SysStaff> {
    //根绝角色获取对应的用户
    List<SysStaff> findstatffByPid(Integer pid);
    void hfSysStaff(Integer id);
    void yjDelSysStaffById(Integer id);
    IPage<SysStaff> findSysStaffAll(Page<SysStaff> page,String name, Integer staus,Integer mid);
    List<SysStaff> findSysStaffAll();
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
    /**
     *  按照岗位查询员工数量
     */
    Integer findSysStaffToCount(String[] pids);
    List<SysStaff> findSysStaff();
    //新增
    Integer addSysStaff(SysStaff sysStaff);
    //修改
    void upSysStaff(SysStaff sysStaff);
    //批量删除
    Integer delSysStaff(String arr);
    //根据ID查询所有该用户的信息
    SysStaff findSysStaffBySid(Integer sid);

    /**
     * 根据部门查用户
     * @param mid
     * @return
     */
    List<SysStaff> findSysStaffByMid(int mid);
    String findSysStaffByIdToName(@Param("sid")Integer sid);
}
