package com.xr.run.dao.inspect;

import com.xr.run.entity.inspect.Report;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "DcpReportMapper")
public interface DcpReportMapper {
    /*查询*/
    public List<Report> list();
    /*按条件查询*
     */
    public List<Report> fintBywhere(Report report);
    /*新增*/
    public void addreport(Report report);
    /*刪除*/
    public void delReport(Integer id);
    /*更新*/
    public void gxpost(Report report);
}
