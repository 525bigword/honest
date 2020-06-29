package com.xr.run.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xr.run.dao.RdWorkDeploymentMapper;
import com.xr.run.entity.RdWorkDeployment;
import com.xr.run.service.RdWorkDeploymentService;
import org.springframework.stereotype.Service;

@Service
public class RdWorkDeploymentServiceImpl extends ServiceImpl<RdWorkDeploymentMapper,RdWorkDeployment> implements RdWorkDeploymentService {

    @Override
    public IPage<RdWorkDeployment> findRdWorkDeploymentIndex(Page page, String title) {
        return baseMapper.findRdWorkDeploymentIndex(page,title);
    }
}
