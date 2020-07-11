package com.xr.run.service.educationPolitics;

import com.xr.run.entity.educationpolitics.EducationPolitics;

import java.util.List;

public interface EducationPoliticsService {
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
}
