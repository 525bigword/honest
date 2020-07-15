package com.xr.run.service.educationPolitics.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xr.run.dao.educationPolitics.EducationPoliticsMapper;
import com.xr.run.entity.educationpolitics.EducationPolitics;
import com.xr.run.service.educationPolitics.EducationPoliticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class EducationPolitisServiceImpl implements EducationPoliticsService {
    @Autowired
    private EducationPoliticsMapper educationPoliticsMapper;
    @Override
    public List<EducationPolitics> findAllEducation() {
        return educationPoliticsMapper.findAllEducation();
    }

    @Override
    public List<EducationPolitics> findwhereEducation(EducationPolitics educationPolitics) {
        return educationPoliticsMapper.findwhereEducation(educationPolitics);
    }

    @Override
    public void updateEducation(EducationPolitics educationPolitics) {
educationPoliticsMapper.updateEducation(educationPolitics);
    }

    @Override
    public void delEcucation(Integer id) {
educationPoliticsMapper.delEcucation(id);
    }

    @Override
    public Integer addEcucation(EducationPolitics educationPolitics) {
        return educationPoliticsMapper.addEcucation(educationPolitics);
    }

    @Override
    public IPage<EducationPolitics> findwhereEducationIndex(Page page, String title) {
        return educationPoliticsMapper.findwhereEducationIndex(page,title);
    }

    @Override
    public IPage<EducationPolitics> findEducationIndexByType(Page page, String title, String type) {
        return educationPoliticsMapper.findEducationIndexByType(page,title,type);
    }

    @Override
    public EducationPolitics finsEduById(Integer id) {
        return educationPoliticsMapper.finsEduById(id);
    }
}
