package com.xr.run.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xr.run.dao.ProcessrickMapper;
import com.xr.run.entity.Processrick;
import com.xr.run.service.ProcessrickService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProcessrickServiceImpl extends ServiceImpl<ProcessrickMapper,Processrick> implements ProcessrickService {

    @Override
    public IPage<Processrick> findProcessrickIndex(Page page, String proName) {
        return baseMapper.findProcessrickIndex(page,proName);
    }

    @Autowired
    private ProcessrickMapper processrickMapper;

    @Override
    public List<Processrick> getList() {
        return processrickMapper.getList();
    }

    @Override
    public void addProcessrick(Processrick processrick) {
        processrickMapper.addProcessrick(processrick);
    }

    @Override
    public void deleteByProid(Integer proid) {
        processrickMapper.deleteByProid(proid);
    }

    @Override
    public void updateByProid(Processrick processrick) {
        processrickMapper.updateByProcessrick(processrick);
    }

    @Override
    public List<Processrick> getListBy(String proName, String proYear) {
        return processrickMapper.getListBy(proName, proYear);
    }

    @Override
    public Processrick findProcessrickById(Integer id) {
        return processrickMapper.findProcessrickById(id);
    }
}
