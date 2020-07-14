package com.xr.run.service.duty;

import com.xr.run.entity.duty.WorkDeployment;

import java.util.List;

public interface WorkDeploymentService {
    /*初始化工作部署页面*/
    public List<WorkDeployment> list(Integer mid);
    /*根据标题查询*/
    public List<WorkDeployment> listBytitle(String title,Integer mid);
    /*新增工作部署*/
    public Integer addDeployment(WorkDeployment workDeployment);
    /*更新工作部署*/
    public void updateDeployment(WorkDeployment workDeployment);
    /*提交审核*/
    public void subauditDeployment(Integer id);
    /*审核通过*/
    public void passauditDeployment(WorkDeployment workDeployment);
    /*删除工作部署*/
    public void delDeployment(Integer id);
}
