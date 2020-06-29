package com.xr.run.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xr.run.dao.RdEntityResponsibilityMapper;
import com.xr.run.entity.RdEntityResponsibility;
import com.xr.run.service.RdEntityResponsibilityService;
import org.springframework.stereotype.Service;

@Service
public class RdEntityResponsibilityServiceImpl extends ServiceImpl<RdEntityResponsibilityMapper,
        RdEntityResponsibility> implements RdEntityResponsibilityService {

    @Override
    public IPage<RdEntityResponsibility> findRdEntityResponsibilityIndex(Page page, String title) {
        return baseMapper.findRdEntityResponsibilityIndex(page,title);
    }
}
