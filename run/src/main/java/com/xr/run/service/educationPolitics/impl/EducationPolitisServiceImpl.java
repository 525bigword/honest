package com.xr.run.service.educationPolitics.impl;

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
}
