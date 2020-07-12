package com.xr.run.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xr.run.dao.PostriskcombingMapper;
import com.xr.run.entity.Postriskcombing;
import com.xr.run.service.PostriskcombingService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PostriskcombingServiceImpl  extends ServiceImpl<PostriskcombingMapper,Postriskcombing> implements PostriskcombingService {

    @Override
    public IPage<Postriskcombing> findPostriskCombingIndex(Page page, String pRiskPointDescription) {
        return baseMapper.findPostriskCombingIndex(page,pRiskPointDescription);
    }

    @Resource
    private PostriskcombingMapper postriskcombingMapper;

    @Override
    public List<Postriskcombing> getList() {
        return postriskcombingMapper.getList();
    }

    @Override
    public void deleteById(Integer pid) {
        postriskcombingMapper.deleteById(pid);
    }

    @Override
    public void updateByPid(Postriskcombing postriskcombing) {
        postriskcombingMapper.updateByPid(postriskcombing);
    }

    @Override
    public void addPostriskcombing(Postriskcombing postriskcombing) {
        postriskcombingMapper.addPostriskcombing(postriskcombing);
    }

    @Override
    public List<Postriskcombing> getListByPproject(String pProject) {
        return postriskcombingMapper.getListByPproject(pProject);
    }

    @Override
    public List<Postriskcombing> getListBy(Integer pInfomationId, Integer pDeptId, String pGrade) {
        return postriskcombingMapper.getListBy(pInfomationId, pDeptId, pGrade);
    }

    @Override
    public List<Postriskcombing> findByPDeptIdAndPGrade(Integer pDeptId, String pGrade) {
        return postriskcombingMapper.findByPDeptIdAndPGrade(pDeptId, pGrade);
    }

    @Override
    public Postriskcombing findByPid(Integer pid) {
        return baseMapper.findPostriskcombingByPid(pid);
    }
}
