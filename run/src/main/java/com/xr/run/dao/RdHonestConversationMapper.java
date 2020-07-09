package com.xr.run.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xr.run.entity.RdEntityResponsibility;
import com.xr.run.entity.RdHonestConversation;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RdHonestConversationMapper extends BaseMapper<RdHonestConversation> {
    //根据谈话对象和记录人查询创建状态和待审状态的数量
    @Select("select count(id) from rd_honest_conversation where pid = #{pid} or personid=#{pid}")
    Integer findRdHonestConversationCount(@Param("pid") Integer pid);

    @Select("select * from rd_honest_conversation order by create_time desc")
    List<RdHonestConversation> findRdHonestConversationAll();
}
