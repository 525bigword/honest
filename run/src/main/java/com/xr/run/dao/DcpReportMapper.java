package com.xr.run.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xr.run.entity.Creditrating;
import com.xr.run.entity.DcpReport;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

@Repository
public interface DcpReportMapper extends BaseMapper<DcpReport> {
    @Select("select id,ReportType,report,NewTime,eid,`status`,url from dcp_report where `status`<>-1  and  report like CONCAT('%',#{report},'%') order by pid desc")
    @Results({
            @Result(column = "eid",property = "eid"),
            @Result(column = "eid",property = "sysStaff",
                    one = @One(select = "com.xr.run.dao.SysStaffMapper.findSysStaffById",fetchType = FetchType.DEFAULT))
    })
    IPage<DcpReport> findDcpReportIndex(Page page, String report);
}
