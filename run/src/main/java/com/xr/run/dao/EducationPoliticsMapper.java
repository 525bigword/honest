package com.xr.run.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xr.run.entity.Datacollection;
import com.xr.run.entity.EducationPolitics;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EducationPoliticsMapper extends BaseMapper<EducationPolitics> {

    @Select("select ep.*,ss.name as cname from education_politics ep inner join sys_staff ss on ep.create_id = ss.sid where ep.staus =0 " +
            " order by ep.id desc limit 0,9")
    List<EducationPolitics> findAllEducationsTopNine();


    @Select("select ep.*,ss.name as cname from education_politics ep inner join sys_staff ss on ep.create_id = ss.sid where ep.staus =0 " +
            "and type = #{type} order by ep.id desc limit 0,1")
    EducationPolitics findAllEducationsByType(@Param("type") String type);
}
