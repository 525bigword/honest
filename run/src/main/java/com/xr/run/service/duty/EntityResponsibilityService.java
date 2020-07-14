package com.xr.run.service.duty;

import com.xr.run.entity.duty.EntityResponsibility;

import java.util.List;

public interface EntityResponsibilityService {
    /*
     * 初始化页面*/
    public List<EntityResponsibility> list(Integer mid);
    /*根据标题模糊查询*/
    public List<EntityResponsibility> findbytitle(String title,Integer mid);
    /*新增数据*/
    public Integer addEntityResponsibility(EntityResponsibility entityResponsibility);
    /*更新工作计划*/
    public void updateEntityResponsibility(EntityResponsibility entityResponsibility);
    /*删除*/
    public void del(Integer id);
    /*提交审核*/
    public void subaudit(Integer id);
    /*审核通过*/
    public void passaudit(EntityResponsibility entityResponsibility);
}
