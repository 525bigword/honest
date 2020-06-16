package com.xr.run.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xr.run.entity.Creditrating;
import com.xr.run.entity.Datacollection;
import com.xr.run.entity.SysStaff;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

@Repository
public interface DatacollectionMapper extends BaseMapper<Datacollection> {
    @Select("SELECT did,dTitle,dFileName,dFile,dCreateTime,dCreateId,dStatus from datacollection")
    @Results({
            @Result(column = "dCreateId",property = "sysStaff",
            one = @One(select = "com.xr.run.dao.SysStaffMapper.findSysStaffById",fetchType = FetchType.DEFAULT))
    })
    IPage<Datacollection> findDataConllection(Page page);
}
