package com.xr.run.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xr.run.dao.RiskpointwarningMapper;
import com.xr.run.entity.Riskpointwarning;
import com.xr.run.service.RiskpointwarningService;
import org.springframework.stereotype.Service;

@Service
public  class RiskpointwarningServiceImpl extends ServiceImpl<RiskpointwarningMapper,Riskpointwarning> implements RiskpointwarningService {

    @Override
    public IPage<Riskpointwarning> findRiskpointwarning(Page page, String wTitle) {
        return baseMapper.findRiskpointwarning(page,wTitle);
    }

    @Override
    public void updateRiskpointwarningByWid(Riskpointwarning riskpointwarning) {
        baseMapper.updateRiskpointwarningByWid(riskpointwarning);
    }

    @Override
    public void insertRiskpointwarning(Riskpointwarning riskpointwarning) {
        baseMapper.insertRiskpointwarning(riskpointwarning);
    }

    @Override
    public void deleteRiskpointwarningByWid(int wid) {
        baseMapper.deleteRiskpointwarningByWid(wid);
    }
}
