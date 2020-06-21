package com.xr.run.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xr.run.dao.SpvDutyMapper;
import com.xr.run.entity.SpvDuty;
import com.xr.run.entity.SysMechanism;
import com.xr.run.service.SpvDutyService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpvDutySericeImpl extends ServiceImpl<SpvDutyMapper,SpvDuty> implements SpvDutyService {

    @Override
    public IPage<SpvDuty> findSpvDuty(Page page, String dutyTitle) {
        return baseMapper.findSpvDuty(page,dutyTitle);
    }

    @Override
    public void updateSpvDutyByDid(SpvDuty spvDuty) {
        baseMapper.updateSpvDutyByDid(spvDuty);
    }

    @Override
    public void updateSpvDutyFileByDid(SpvDuty spvDuty) {
        baseMapper.updateSpvDutyFileByDid(spvDuty);
    }

    @Override
    public void insertSpvDuty(SpvDuty spvDuty) {
        baseMapper.insertSpvDuty(spvDuty);
    }

    @Override
    public void deleteSpvDutyByDid(int did) {
        baseMapper.deleteSpvDutyByDid(did);
    }

    @Override
    public String findSpvDutyByFile(int did) {
        return baseMapper.findSpvDutyByFile(did);
    }

    @Override
    public List<SysMechanism> findDid() {
        return baseMapper.findDid(0);
    }
}
