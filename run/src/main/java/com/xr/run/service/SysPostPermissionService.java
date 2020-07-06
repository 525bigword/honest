package com.xr.run.service;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xr.run.entity.SysPermission;
import com.xr.run.entity.SysPost;
import com.xr.run.entity.SysPostPermission;
import com.xr.run.entity.SysPpost;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;


public interface SysPostPermissionService extends IService<SysPostPermission> {
    /**
     * 获取树形权限菜单
     */
    List<SysPermission> findTreeSysPost();
    /**
     * 根据岗位id查询对应的权限
     */
    List<Integer> findSysPostPermissionByPostIdToPid(Integer postid);
    /**
     *
     */
    List<SysPpost> findSysPostPage(Integer pageNum, Integer pageRow, JSONObject jsonObject);
    Integer findSysPostPage(JSONObject jsonObject);
    void add(SysPpost sysPpost);
    void upSysPost(SysPpost sysPpost);
    void delSysPost(String[] splice);
}
