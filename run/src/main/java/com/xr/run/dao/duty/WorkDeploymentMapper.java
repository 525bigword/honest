package com.xr.run.dao.duty;

import com.xr.run.entity.duty.WorkDeployment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface WorkDeploymentMapper {
    /*初始化工作部署页面*/
    //@Select(" select id,title,content,create_time,staus from rd_work_deployment")
    public List<WorkDeployment> list();
    /*根据标题查询*/
    public List<WorkDeployment> listBytitle(String title);
    /*新增工作部署*/
    public void addDeployment(WorkDeployment workDeployment);
    /*更新工作部署*/
    public void updateDeployment(WorkDeployment workDeployment);
    /*提交审核*/
    public void subauditDeployment(Integer id);
    /*审核通过*/
    public void passauditDeployment(Integer id);
    /*删除工作部署*/
    public void delDeployment(Integer id);
}
