package com.xr.run.dao.duty;

import com.xr.run.entity.SysPpost;
import com.xr.run.entity.SysStaff;
import com.xr.run.entity.SysMechanism;
import com.xr.run.entity.duty.HonestConversation;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HonestConversationMapper {
    /*初始化廉政谈话页面*/
    public List<HonestConversation> list(Integer mid);
    /*根据谈话类型标题查询*/
    public List<HonestConversation> listBytitle(@Param(value = "type") String title,@Param(value = "mid") Integer mid);
    /*新增谈话*/
    public void addHonestConversation(HonestConversation honestConversation);
    /*更新谈话*/
    public void updateHonestConversation(HonestConversation honestConversation);
    /*提交审核*/
    public void subauditHonestConversation(Integer id);
    /*审核通过*/
    public void passauditHonestConversation(@Param(value = "id") Integer id, @Param(value = "staus") Integer status,@Param(value = "auditresult") String auditresult);
    /*删除谈话*/
    public void delHonestConversationt(Integer id);
    /*根据单位查用户*/
   public List<SysStaff> findallduty(Integer id);
    /*根据部门id查所有部门*/
    public List<SysPpost> findalldept(Integer mid);
    /*根据职务查人*/
    public List<SysStaff> findallperson(Integer ppid);
    public List<SysMechanism> findallunit(@Param(value = "parent") Integer parent);
}
