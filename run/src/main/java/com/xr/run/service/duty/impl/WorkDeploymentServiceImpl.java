package com.xr.run.service.duty.impl;

import com.xr.run.dao.duty.WorkDeploymentMapper;
import com.xr.run.entity.duty.WorkDeployment;
import com.xr.run.service.duty.WorkDeploymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkDeploymentServiceImpl implements WorkDeploymentService {
    @Autowired
    private WorkDeploymentMapper workDeploymentMapper;

    @Override
    public List<WorkDeployment> list() {
        return workDeploymentMapper.list();
    }

    @Override
    public List<WorkDeployment> listBytitle(String title) {
        return workDeploymentMapper.listBytitle(title);
    }

    @Override
    public Integer addDeployment(WorkDeployment workDeployment) {
workDeploymentMapper.addDeployment(workDeployment);
return workDeployment.getId();
    }

    @Override
    public void updateDeployment(WorkDeployment workDeployment) {
workDeploymentMapper.updateDeployment(workDeployment);
    }

    @Override
    public void subauditDeployment(Integer id) {
workDeploymentMapper.subauditDeployment(id);
    }

    @Override
    public void passauditDeployment(WorkDeployment workDeployment) {
workDeploymentMapper.passauditDeployment(workDeployment);
    }

    @Override
    public void delDeployment(Integer id) {
workDeploymentMapper.delDeployment(id);
    }
}
