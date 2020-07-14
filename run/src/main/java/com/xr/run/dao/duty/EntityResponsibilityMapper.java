package com.xr.run.dao.duty;

import com.xr.run.entity.duty.EntityResponsibility;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntityResponsibilityMapper {
    /*
     * 初始化页面*/
    public List<EntityResponsibility> list(Integer mid);
    /*根据标题模糊查询*/
    public List<EntityResponsibility> findbytitle(@Param(value = "title") String title,@Param(value = "mid") Integer mid);
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
