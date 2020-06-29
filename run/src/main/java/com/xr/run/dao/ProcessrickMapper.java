package com.xr.run.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xr.run.entity.Postriskcombing;
import com.xr.run.entity.Processrick;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProcessrickMapper extends BaseMapper<Processrick> {
    @Select("select proid,processID,proName,proYear,proInfomation,proAccessory,proCreateTime,proCreateId,proCreateName,proStatus from processrick  where  proName like CONCAT('%',#{proName},'%') order by proid desc")
    @Results({
            @Result(column = "proCreateId",property = "proCreateId"),
            @Result(column = "proCreateId",property = "sysStaff",
                    one = @One(select = "com.xr.run.dao.SysStaffMapper.findSysStaffById",fetchType = FetchType.DEFAULT))
    })
    List<Processrick> findProcessrickIndex(String proName);
}
