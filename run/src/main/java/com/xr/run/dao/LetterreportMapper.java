package com.xr.run.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xr.run.entity.EducationPolitics;
import com.xr.run.entity.Letterreport;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface LetterreportMapper extends BaseMapper<Letterreport> {

    @Select("SELECT count(lid) FROM ( SELECT lid,lPersonBeReported FROM daily_letterreport where " +
            " lStatus<>4 and lStatus<>-1) daily_letterreport WHERE lPersonBeReported = #{sid}")
    Integer findSpvBackByWstatusToCount(@Param("sid")Integer sid);

}
