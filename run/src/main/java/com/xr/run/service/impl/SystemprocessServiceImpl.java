package com.xr.run.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xr.run.dao.SystemprocessMapper;
import com.xr.run.entity.Systemprocess;
import com.xr.run.service.SystemprocessService;
import org.springframework.stereotype.Service;

@Service
public class SystemprocessServiceImpl extends ServiceImpl<SystemprocessMapper,Systemprocess> implements SystemprocessService {


    @Override
    public IPage<Systemprocess> findSystemprocess(Page page, String sysTitle, String sysContent) {
        return baseMapper.findSystemprocess(page,sysTitle,sysContent);
    }

    @Override
    public void updateSystemprocessBySysId(Systemprocess systemprocess) {
        baseMapper.updateSystemprocessBySysId(systemprocess);
    }

    @Override
    public void insertSystemprocess(Systemprocess systemprocess) {
        baseMapper.insertSystemprocess(systemprocess);
    }

    @Override
    public void deleteSystemprocessBySysId(int sysid) {
        baseMapper.deleteSystemprocessBySysId(sysid);
    }
}
