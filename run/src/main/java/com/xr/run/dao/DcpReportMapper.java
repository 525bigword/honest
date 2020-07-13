package com.xr.run.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xr.run.entity.Creditrating;
import com.xr.run.entity.DcpReport;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

@Repository
public interface DcpReportMapper extends BaseMapper<DcpReport> {
    @Select("select dr.id,dr.ReportType,dr.report,dr.NewTime,dr.eid,dr.`status`,dr.url,ss.name as cname from dcp_report dr inner join sys_staff ss on dr.eid = ss.sid  where `status`<>-1  and  report like CONCAT('%',#{report},'%') order by id desc")
    @Results({
            @Result(column = "eid",property = "eid"),
            @Result(column = "eid",property = "sysStaff",
                    one = @One(select = "com.xr.run.dao.SysStaffMapper.findSysStaffById",fetchType = FetchType.DEFAULT))
    })
    IPage<DcpReport> findDcpReportIndex(Page page,@Param("report") String report);
}
