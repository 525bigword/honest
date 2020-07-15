package com.xr.run.dao.duty;

import com.xr.run.entity.duty.WorkDeployment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface WorkDeploymentMapper {
    /*初始化工作部署页面*/
    //@Select(" select id,title,content,create_time,staus from rd_work_deployment")
    public List<WorkDeployment> list(@Param(value = "mid") Integer mid);
    /*根据标题查询*/
    public List<WorkDeployment> listBytitle(@Param(value = "title") String title,@Param(value = "mid") Integer mid);
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
