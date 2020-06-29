package com.xr.run.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xr.run.dao.DcpReportMapper;
import com.xr.run.entity.DcpReport;
import com.xr.run.service.DcpReportService;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service("DcpReportServiceImpl")
public class DcpReportServiceImpl  extends ServiceImpl<DcpReportMapper, DcpReport> implements DcpReportService {
    @Override
    public IPage<DcpReport> findDcpReportIndex(Page page, String report) {
        return baseMapper.findDcpReportIndex(page,report);
    }
}
