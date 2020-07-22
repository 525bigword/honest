package com.xr.run.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xr.run.entity.SystemMessage;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SystemMessageMapper extends BaseMapper<SystemMessage> {
    @Select("select sm.aid,sm.articletitle,sm.attribute,sm.picture,sm.picturename,sm.articlesource,sm.`describe`,sm.content,sm.createId,sm.createTime,ss.name as cname from system_message sm " +
            "              inner join sys_staff ss on sm.createId=ss.sid  where sm.status=0 and sm.articletitle like CONCAT('%',#{articletitle},'%') and sm.`describe` like CONCAT('%',#{describe},'%') order by sm.aid desc")
    @Results({
            @Result(column = "createId",property = "sysStaff",
                    one = @One(select = "com.xr.run.dao.SysStaffMapper.findSysStaffById",fetchType = FetchType.DEFAULT))
    })
    IPage<SystemMessage> findSystemMessage(Page page,@Param("articletitle") String articletitle,@Param("describe") String describe);

    @Update("update system_message set articletitle=#{articletitle},attribute=#{attribute},picture=#{picture},picturename=#{picturename},articlesource=#{articlesource},`describe`=#{describe},content=#{content} where aid=#{aid}")
    void updateSystemMessageByAid(SystemMessage systemMessage);

    @Update("update system_message set articletitle=#{articletitle},attribute=#{attribute},articlesource=#{articlesource},`describe`=#{describe},content=#{content} where aid=#{aid}")
    void updateSystemMessageByAidNoFile(SystemMessage systemMessage);

    @Insert("insert into system_message(aid,articletitle,attribute,picture,picturename,articlesource,`describe`,content,createId,createTime,`status`) values(NULL,#{articletitle},#{attribute},#{picture},#{picturename},#{articlesource},#{describe},#{content},#{createId},date_add(NOW(),INTERVAL -8 hour),0)")
    void insertSystemMessage(SystemMessage riskpointwarning);

    @Update("update system_message set status=1 where aid=#{aid}")
    void deleteSystemMessageByAid(int aid);

    @Select("select picture from system_message where aid=#{aid}")
    String findSystemMessageByFile(int aid);

    @Select("select aid,articletitle,attribute,picture,picturename,articlesource,`describe`,content,createId,createTime from system_message where status=0 and articletitle like CONCAT('%',#{articletitle},'%') and `describe` like CONCAT('%',#{describe},'%') order by aid desc")
    List<SystemMessage> findSystemMessageStatic(String articletitle, String describe);
}
