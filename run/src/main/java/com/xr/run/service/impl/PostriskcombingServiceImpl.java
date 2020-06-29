package com.xr.run.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xr.run.dao.PostriskcombingMapper;
import com.xr.run.entity.Postriskcombing;
import com.xr.run.service.PostriskcombingService;
import org.springframework.stereotype.Service;

@Service
public class PostriskcombingServiceImpl  extends ServiceImpl<PostriskcombingMapper,Postriskcombing> implements PostriskcombingService {

    @Override
    public IPage<Postriskcombing> findPostriskCombingIndex(Page page, String pRiskPointDescription) {
        return baseMapper.findPostriskCombingIndex(page,pRiskPointDescription);
    }
}
