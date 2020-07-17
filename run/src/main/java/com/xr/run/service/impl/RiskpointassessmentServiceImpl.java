package com.xr.run.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xr.run.dao.RiskpointassessmentMapper;
import com.xr.run.entity.Postriskcombing;
import com.xr.run.entity.Riskpointassessment;
import com.xr.run.entity.SysMechanism;
import com.xr.run.entity.SysStaff;
import com.xr.run.service.PostriskcombingService;
import com.xr.run.service.RiskpointassessmentService;
import com.xr.run.service.SysMechanismService;
import com.xr.run.util.constants.Constants;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RiskpointassessmentServiceImpl extends ServiceImpl<RiskpointassessmentMapper,Riskpointassessment> implements RiskpointassessmentService {

    @Autowired
    private RiskpointassessmentMapper riskpointassessmentMapper;

    @Autowired
    private PostriskcombingService postriskcombingService;

    @Autowired
    private SysMechanismService sysMechanismService;


    @Override
    public void add(Riskpointassessment riskPointAssessment) {
        riskpointassessmentMapper.add(riskPointAssessment);
    }

    @Override
    public void update(Riskpointassessment riskPointAssessment) {
        riskpointassessmentMapper.update(riskPointAssessment);
    }

    @Override
    public List<SysMechanism> notUpdatedDept(Riskpointassessment riskpointassessment) {
        return riskpointassessmentMapper.notUpdatedDept(riskpointassessment);
    }

    @Override
    public Riskpointassessment findBy(Integer riskImfomation) {
        return riskpointassessmentMapper.findBy(riskImfomation);
    }


    @Override
    public void syncRiskpointassessment() {
        List<SysMechanism> sysMechanismList = sysMechanismService.getAll();
        //从session获取用户信息
        Session session = SecurityUtils.getSubject().getSession();
        SysStaff userInfo = (SysStaff) session.getAttribute(Constants.SESSION_USER_INFO);

        for (SysMechanism sysMechanism : sysMechanismList) {
            List<Postriskcombing> list1 = postriskcombingService.findByPDeptIdAndPGrade(sysMechanism.getMid(), "一级风险");
            Integer one = list1.size();
            List<Postriskcombing> list2 = postriskcombingService.findByPDeptIdAndPGrade(sysMechanism.getMid(), "二级风险");
            Integer two = list2.size();
            List<Postriskcombing> list3 = postriskcombingService.findByPDeptIdAndPGrade(sysMechanism.getMid(), "三级风险");
            Integer three = list3.size();
            Riskpointassessment riskpointassessment = new Riskpointassessment();
            riskpointassessment.setRiskImfomation(sysMechanism.getMid());
            riskpointassessment.setNumberOneRisks(one);
            riskpointassessment.setNumberTwoRisks(two);
            riskpointassessment.setNumberThreeRisks(three);
            riskpointassessment.setRiskCreateTime(DateUtil.date());
            riskpointassessment.setRiskCreateId(userInfo.getSid());
            riskpointassessment.setRiskCreateName(userInfo.getName());
            riskpointassessment.setRiskStatus(1);
            addRiskpointassessment(riskpointassessment);
        }
    }

    @Override
    public void addRiskpointassessment(Riskpointassessment riskpointassessment) {
        riskpointassessmentMapper.addRiskpointassessment(riskpointassessment);
    }

    @Override
    public List<Riskpointassessment> getList() {
        return riskpointassessmentMapper.getList();
    }

    @Override
    public Riskpointassessment getOneByRiskImfomation(Integer riskImfomation) {
        return riskpointassessmentMapper.getOneByRiskImfomation(riskImfomation);
    }

    @Override
    public void updateByRiskImfomation(Integer riskImfomation, String pGrade, Integer num) {
        if (pGrade.equals("一级风险")) {
            riskpointassessmentMapper.updateByRiskImfomation1(riskImfomation, num);
        } else if (pGrade.equals("二级风险")) {
            riskpointassessmentMapper.updateByRiskImfomation2(riskImfomation, num);
        } else {
            riskpointassessmentMapper.updateByRiskImfomation3(riskImfomation, num);
        }

    }
}
