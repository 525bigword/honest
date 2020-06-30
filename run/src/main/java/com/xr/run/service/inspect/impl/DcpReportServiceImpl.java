package com.xr.run.service.inspect.impl;

import com.xr.run.dao.inspect.DcpReportMapper;
import com.xr.run.entity.inspect.Report;
import com.xr.run.service.inspect.DcpReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "DcpReportService")
public class DcpReportServiceImpl implements DcpReportService  {
    @Autowired
    private DcpReportMapper dcpReportMapper;
    @Override
    public List<Report> list() {
        return dcpReportMapper.list();
    }

    @Override
    public List<Report> fintBywhere(Report report) {
        return dcpReportMapper.fintBywhere(report);
    }

    @Override
    public void addreport(Report report) {
        dcpReportMapper.addreport(report);
    }

    @Override
    public void delReport(Integer id) {
        dcpReportMapper.delReport(id);
    }

    @Override
    public void gxpost(Report report) {
        dcpReportMapper.gxpost(report);
    }
}
