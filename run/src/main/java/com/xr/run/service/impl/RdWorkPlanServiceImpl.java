package com.xr.run.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xr.run.dao.RdWorkPlanMapper;
import com.xr.run.entity.RdWorkPlan;
import com.xr.run.service.RdWorkPlanService;
import org.springframework.stereotype.Service;

@Service
public class RdWorkPlanServiceImpl extends ServiceImpl<RdWorkPlanMapper,RdWorkPlan> implements RdWorkPlanService {

    @Override
    public IPage<RdWorkPlan> findRdWorkPlanIndex(Page page, String title,Integer staus) {
        return baseMapper.findRdWorkPlanIndex(page,title,staus);
    }
}
