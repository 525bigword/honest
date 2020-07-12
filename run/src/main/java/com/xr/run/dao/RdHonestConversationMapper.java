package com.xr.run.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xr.run.entity.RdHonestConversation;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RdHonestConversationMapper extends BaseMapper<RdHonestConversation> {
    //根据谈话对象和记录人查询创建状态和待审状态的数量
    @Select("select count(id) from rd_honest_conversation where pid = #{pid} or personid=#{pid}")
    Integer findRdHonestConversationCount(@Param("pid") Integer pid);

    List<RdHonestConversation> findRdHonestConversationAll(@Param("title") String title);

    @Select("select id,syllabus,content,create_time,create_id,staus from rd_honest_conversation where staus =2 and  syllabus like CONCAT('%',#{title},'%') order by id desc")
    IPage<RdHonestConversation> findRdHonestConversationIndex(Page page,@Param("title") String title);
}
