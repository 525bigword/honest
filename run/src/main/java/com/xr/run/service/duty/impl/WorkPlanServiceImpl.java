package com.xr.run.service.duty.impl;

import com.xr.run.dao.duty.WorkPlanMapper;
import com.xr.run.entity.duty.WorkPlan;
import com.xr.run.service.duty.WorkPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkPlanServiceImpl implements WorkPlanService {
    @Autowired
    private WorkPlanMapper workPlanMapper;
    @Override
    public List<WorkPlan> list(Integer mid) {
        return workPlanMapper.list(mid);
    }

    @Override
    public List<WorkPlan> findbytitle(String title,Integer mid) {
        return workPlanMapper.findbytitle(title,mid);
    }

    @Override
    public Integer addWorkPlan(WorkPlan workPlan) {
        workPlanMapper.addWorkPlan(workPlan);
        return workPlan.getId();
    }

    @Override
    public void updateWorkPlan(WorkPlan workPlan) {
        workPlanMapper.updateWorkPlan(workPlan);
    }

    @Override
    public void del(Integer id) {
        workPlanMapper.del(id);
    }

    @Override
    public void subaudit(Integer id) {
        workPlanMapper.subaudit(id);
    }

    @Override
    public void passaudit(WorkPlan workPlan) {
        workPlanMapper.passaudit(workPlan);

    }
}
