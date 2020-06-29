package com.xr.run.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xr.run.dao.ProcessrickMapper;
import com.xr.run.entity.Processrick;
import com.xr.run.service.ProcessrickService;
import org.springframework.stereotype.Service;

@Service
public class ProcessrickServiceImpl extends ServiceImpl<ProcessrickMapper,Processrick> implements ProcessrickService {

    @Override
    public IPage<Processrick> findProcessrickIndex(Page page, String proName) {
        return baseMapper.findProcessrickIndex(page,proName);
    }
}
