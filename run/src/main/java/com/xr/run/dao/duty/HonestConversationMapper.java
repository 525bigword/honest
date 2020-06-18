package com.xr.run.dao.duty;

import com.xr.run.entity.duty.HonestConversation;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HonestConversationMapper {
    /*初始化廉政谈话页面*/
    public List<HonestConversation> list();
    /*根据谈话类型标题查询*/
    public List<HonestConversation> listBytitle(@Param(value = "type") String title);
    /*新增谈话*/
    public void addHonestConversation(HonestConversation honestConversation);
    /*更新谈话*/
    public void updateHonestConversation(HonestConversation honestConversation);
    /*提交审核*/
    public void subauditHonestConversation(Integer id);
    /*审核通过*/
    public void passauditHonestConversation(Integer id);
    /*删除谈话*/
    public void delHonestConversationt(Integer id);
}
