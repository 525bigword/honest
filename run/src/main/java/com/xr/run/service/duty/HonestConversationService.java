package com.xr.run.service.duty;

import com.xr.run.entity.SysPpost;
import com.xr.run.entity.SysStaff;
import com.xr.run.entity.SysMechanism;
import com.xr.run.entity.duty.HonestConversation;

import java.util.List;

public interface HonestConversationService {
    /*初始化廉政谈话页面*/
    public List<HonestConversation> list();
    /*根据谈话类型标题查询*/
    public List<HonestConversation> listBytitle(String title);
    /*新增谈话*/
    public void addHonestConversation(HonestConversation honestConversation);
    /*更新谈话*/
    public void updateHonestConversation(HonestConversation honestConversation);
    /*提交审核*/
    public void subauditHonestConversation(Integer id);
    /*审核通过*/
    public void passauditHonestConversation(Integer id, Integer status,String auditresult);
    /*删除谈话*/
    public void delHonestConversationt(Integer id);
    /*根据部门id查所有人*/
    public List<SysStaff> findallduty(Integer id);
    /*根据部门id查所有部门*/
    public List<SysPpost> findalldept(Integer id);
    /*根据职务查人*/
    public List<SysStaff> findallperson(Integer ppid);
    public List<SysMechanism> findallunit(Integer parent);
}
