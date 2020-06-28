package com.xr.run.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xr.run.dao.SpvBackMapper;
import com.xr.run.entity.SpvBack;
import com.xr.run.service.SpvBackService;
import org.springframework.stereotype.Service;

@Service
public class SpvBackServiceImpl extends ServiceImpl<SpvBackMapper,SpvBack> implements SpvBackService {


    @Override
    public IPage<SpvBack> findSpvBack(Page page, int bid) {
        return baseMapper.findSpvBack(page,bid);
    }

    @Override
    public void updateSpvBackBySid(SpvBack spvBack) {
        baseMapper.updateSpvBackBySid(spvBack);
    }

    @Override
    public void updateSpvBackFileBySid(SpvBack spvBack) {
        baseMapper.updateSpvBackFileBySid(spvBack);
    }

    @Override
    public void insertSpvBack(SpvBack spvBack) {
        baseMapper.insertSpvBack(spvBack);
    }

    @Override
    public void deleteSpvBackBySid(int sid) {
        baseMapper.deleteSpvBackBySid(sid);
    }

    @Override
    public String findSpvBackByFile(int sid) {
        return baseMapper.findSpvBackByFile(sid);
    }

    @Override
    public void updateStatusBySid(SpvBack spvBack) {
        baseMapper.updateStatusBySid(spvBack);
    }

    @Override
    public IPage<SpvBack> findSpvBackByDid(Page page, int did) {
        return baseMapper.findSpvBackByDid(page,did);
    }
}
