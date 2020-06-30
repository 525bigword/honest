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
    public IPage<Datacollection> findDataConllection(Page page,String dTitle) {
        return baseMapper.findDataConllection(page,dTitle);
    }

    @Override
    public void updateDataConllectionByDid(Datacollection datacollection) {
        baseMapper.updateDataConllectionByDid(datacollection);
    }

    @Override
    public void insertDataConllection(Datacollection datacollection) {
        baseMapper.insertDataConllection(datacollection);
    }

    @Override
    public void deleteDataConllectionByDid(int did) {
        baseMapper.deleteDataConllectionByDid(did);
    }

    @Override
    public String findDatacollectionByFile(int did) {
        return baseMapper.findDatacollectionByFile(did);
    }

    @Override
    public void updateDataConllectionByFile(Datacollection datacollection) {
        baseMapper.updateDataConllectionByFile(datacollection);
    }

    @Override
    public void updateDataConllectionByVideo(Datacollection datacollection) {
        baseMapper.updateDataConllectionByVideo(datacollection);
    }

    @Override
    public void updateDataConllectionByVideoAndFile(Datacollection datacollection) {
        baseMapper.updateDataConllectionByVideoAndFile(datacollection);
    }

    @Override
    public String findDatacollectionBydVideo(int did) {
        return baseMapper.findDatacollectionBydVideo(did);
    }

}
