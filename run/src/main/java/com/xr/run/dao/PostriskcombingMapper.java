package com.xr.run.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xr.run.entity.Letterreport;
import com.xr.run.entity.Postriskcombing;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

@Repository
public interface PostriskcombingMapper extends BaseMapper<Postriskcombing> {
    @Select("select pid,pRiskId,pYear,pDeptId,pInfomationId,pProject,pRiskPointDescription,pProbableLValue,pCValue,pDValue,pGrade,pMeasures,pCreateTime,pCreateId,pCreateName,pStatus from postriskcombing where pStatus=0 and  pRiskPointDescription like CONCAT('%',#{pRiskPointDescription},'%') order by pid desc")
    @Results({
            @Result(column = "pCreateId",property = "pCreateId"),
            @Result(column = "pCreateId",property = "sysStaff",
                    one = @One(select = "com.xr.run.dao.SysStaffMapper.findSysStaffById",fetchType = FetchType.DEFAULT))
    })
    IPage<Postriskcombing> findPostriskCombingIndex(Page page, String pRiskPointDescription);
}
