package com.xr.run.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xr.run.entity.SysMechanism;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface SysMechanismMapper extends BaseMapper<SysMechanism> {
    @Select("select mechanism_name from sys_mechanism where mid=#{mid}")
    String findMechanismNameByMid(@Param("mid") Integer mid);
}
