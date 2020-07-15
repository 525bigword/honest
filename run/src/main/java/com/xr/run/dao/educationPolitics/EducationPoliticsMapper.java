package com.xr.run.dao.educationPolitics;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xr.run.entity.educationpolitics.EducationPolitics;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository(value = "EducationPoliticsMapper")
public interface EducationPoliticsMapper {
    /*查询廉政教育*/
    public List<EducationPolitics> findAllEducation();

    /*多条件查询廉政教育*/
    public List<EducationPolitics> findwhereEducation(EducationPolitics educationPolitics);

    /*更新廉政教育*/
    public void updateEducation(EducationPolitics educationPolitics);

    /*删除廉政教育*/
    public void delEcucation(Integer id);

    /*新增廉政教育信息*/
    public Integer addEcucation(EducationPolitics educationPolitics);

    @Select("select id, " +
            "title, " +
            "source, " +
            "type, " +
            "content, " +
            "time, " +
            "create_time, " +
            "create_id, " +
            "staus, " +
            "createname from education_politics where staus=0 and title like CONCAT('%',#{title},'%') order by create_time desc")
    IPage<EducationPolitics> findwhereEducationIndex(Page page, @Param("title") String title);

    @Select("select id, " +
            "title, " +
            "source, " +
            "type, " +
            "content, " +
            "time, " +
            "create_time, " +
            "create_id, " +
            "staus, " +
            "createname  from education_politics where staus=0 and type = #{type} and title like CONCAT('%',#{title},'%') order by create_time desc")
    IPage<EducationPolitics> findEducationIndexByType(Page page, @Param("title") String title, @Param("type") String type);

    @Select("select ep.id, " +
            "ep.title, " +
            "ep.source, " +
            "ep.type, " +
            "ep.content, " +
            "ep.time, " +
            "ep.create_time, " +
            "ep.create_id, " +
            "ep.staus, " +
            "ep.createname,ss.name as cname  from education_politics ep inner join sys_staff ss on ep.create_id=ss.sid where ep.id = #{id} order by ep.create_time desc")
    EducationPolitics finsEduById(@Param("id") Integer id);
}
