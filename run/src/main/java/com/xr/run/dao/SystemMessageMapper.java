package com.xr.run.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xr.run.entity.SystemMessage;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemMessageMapper extends BaseMapper<SystemMessage> {
    @Select("select aid,articletitle,attribute,picture,picturename,articlesource,`describe`,content,createId,createTime from system_message where status=0 and articletitle like CONCAT('%',#{articletitle},'%') and `describe` like CONCAT('%',#{describe},'%') order by aid desc")
    @Results({
            @Result(column = "createId",property = "sysStaff",
                    one = @One(select = "com.xr.run.dao.SysStaffMapper.findSysStaffById",fetchType = FetchType.DEFAULT))
    })
    IPage<SystemMessage> findSystemMessage(Page page, String articletitle, String describe);

    @Update("update system_message set articletitle=#{articletitle},attribute=#{attribute},picture=#{picture},picturename=#{picturename},articlesource=#{articlesource},`describe`=#{describe},content=#{content} where aid=#{aid}")
    void updateSystemMessageByAid(SystemMessage systemMessage);

    @Update("update system_message set articletitle=#{articletitle},attribute=#{attribute},articlesource=#{articlesource},`describe`=#{describe},content=#{content} where aid=#{aid}")
    void updateSystemMessageByAidNoFile(SystemMessage systemMessage);

    @Insert("insert into system_message(aid,articletitle,attribute,picture,picturename,articlesource,`describe`,content,createId,createTime,`status`) values(NULL,#{articletitle},#{attribute},#{picture},#{picturename},#{articlesource},#{describe},#{content},#{createId},NOW(),0)")
    void insertSystemMessage(SystemMessage riskpointwarning);

    @Update("update system_message set status=1 where aid=#{aid}")
    void deleteSystemMessageByAid(int aid);

    @Select("select picture from system_message where aid=#{aid}")
    String findSystemMessageByFile(int aid);
}
