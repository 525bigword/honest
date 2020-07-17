package com.xr.run.service.risk.impl;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xr.run.dao.risk.PostRiskCombingMapper;
import com.xr.run.entity.PostRiskCombing2;
import com.xr.run.entity.Postriskcombing;
import com.xr.run.service.risk.PostRiskCombingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostRiskCombingServiceImpl implements PostRiskCombingService {
    @Autowired
    private PostRiskCombingMapper postRiskCombingMapper;
    @Override
    public IPage<Postriskcombing> list(Page page) {
        return postRiskCombingMapper.list(page);
    }
    @Override
    public IPage<Postriskcombing> getListBy(Integer infomationId, Integer deptId, String pgrade, Page page){
        return postRiskCombingMapper.getListBy(infomationId, deptId, pgrade,page);
    }

    @Override
    public String getpDeptNameById(Integer id) {
        return null;
    }

    @Override
    public String getpPostNameById(Integer id) {
        return null;
    }

    @Override
    public List<PostRiskCombing2> findByPcID(Integer pcid) {
        return postRiskCombingMapper.findByPcID(pcid);
    }

    // 添加
    @Override
    public void add(Postriskcombing postriskcombing) {
        postRiskCombingMapper.add(postriskcombing);
    }


    // 删除
    @Override
    public void deleteById(List<Integer> list) {
        postRiskCombingMapper.deleteById(list);
    }

    @Override
    public void updateRiskPointAssessmentByIdOne(Integer riskImfomation) {
        postRiskCombingMapper.updateRiskPointAssessmentByIdOne(riskImfomation);
    }

    @Override
    public void updateRiskPointAssessmentByIdTwo(Integer riskImfomation) {
        postRiskCombingMapper.updateRiskPointAssessmentByIdTwo(riskImfomation);
    }

    @Override
    public void updateRiskPointAssessmentByIdThree(Integer riskImfomation) {
        postRiskCombingMapper.updateRiskPointAssessmentByIdThree(riskImfomation);
    }


    @Override
    public void update(Postriskcombing postriskcombing) {
        postRiskCombingMapper.update(postriskcombing);
    }

    @Override
    public int findByGrade(Postriskcombing postriskcombing) {
        return postRiskCombingMapper.findByGrade(postriskcombing);
    }
}
