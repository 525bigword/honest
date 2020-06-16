package com.xr.run.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xr.run.dao.DatacollectionMapper;
import com.xr.run.entity.Datacollection;
import com.xr.run.entity.SysStaff;
import com.xr.run.service.DatacollectionService;
import org.springframework.stereotype.Service;

@Service
public class DatacollectionServiceImpl extends ServiceImpl<DatacollectionMapper, Datacollection> implements DatacollectionService {

    @Override
    public IPage<Datacollection> findDataConllection(Page page) {
        return baseMapper.findDataConllection(page);
    }
}
