package com.xr.run.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xr.run.entity.DcpReport;


public interface DcpReportService extends IService<DcpReport> {
    IPage<DcpReport> findDcpReportIndex(Page page, String report);

    DcpReport findDcpReportById(Integer id);
}
