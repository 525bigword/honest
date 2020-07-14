package com.xr.run.dao.duty;

import com.xr.run.entity.duty.WorkPlan;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkPlanMapper {
    /*
     * 初始化页面*/
    public List<WorkPlan> list(Integer mid);
    /*根据标题模糊查询*/
    public List<WorkPlan> findbytitle(@Param(value = "title")String title,@Param(value = "mid") Integer mid);
    /*新增数据*/
    public Integer addWorkPlan(WorkPlan workPlan);
    /*更新工作计划*/
    public void updateWorkPlan(WorkPlan workPlan);
    /*删除*/
    public void del(Integer id);
    /*提交审核*/
    public void subaudit(Integer id);
    /*审核通过与否*/
    public void passaudit(WorkPlan workPlan);
}
