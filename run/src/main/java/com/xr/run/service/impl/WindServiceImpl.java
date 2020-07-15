package com.xr.run.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xr.run.dao.WindMapper;
import com.xr.run.entity.Wind;
import com.xr.run.service.WindService;
import org.springframework.stereotype.Service;

@Service
public class WindServiceImpl extends ServiceImpl<WindMapper, Wind> implements WindService {
    @Override
    public IPage<Wind> findWind(Page page, String wTitle) {
        return baseMapper.findWind(page,wTitle);
    }

    @Override
    public void updateWindByWid(Wind wind) {
        baseMapper.updateWindByWid(wind);
    }

    @Override
    public void insertWind(Wind wind) {
        baseMapper.insertWind(wind);
    }

    @Override
    public void deleteWindByWid(int wid) {
        baseMapper.deleteWindByWid(wid);
    }

    @Override
    public Wind findWindByWid(int wid) {
        return baseMapper.findWindByWid(wid);
    }

    @Override
    public IPage<Wind> findWind1(Page page, String title) {
        return baseMapper.findWind1(page,title);
    }
}
