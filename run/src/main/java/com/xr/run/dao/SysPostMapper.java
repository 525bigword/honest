package com.xr.run.dao;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xr.run.entity.SysPost;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysPostMapper extends BaseMapper<SysPost> {
    @Select("select pname from sys_post where pid = #{pid}")
    String findSysPostPnameByPid(@Param("pid")Integer pid);
    /**
     * 根据mid查询数量
     */
    @Select("select count(pid) from sys_post where mid=#{mid} and staus='1'")
    Integer findSysPostByMidTocount(@Param("mid")Integer mid);
    /**
     * 分页
     */
    @Select("select pid,pname,mid,message,create_time,create_id,staus from sys_post where EXISTS " +
            "(select mid from sys_mechanism where sys_post.mid=sys_mechanism.mid ${str} AND staus = '正常') and " +
            "pname like CONCAT('%',#{pame},'%') and staus='1' and message like CONCAT('%',#{message},'%') limit #{pageNum},#{pageRow}")
    @Results({
            @Result(column = "create_id",property = "createname",one=@One(select = "com.xr.run.dao.SysStaffMapper.findSysStaffByIdToName")),
            @Result(column = "mid",property = "mname",one=@One(select = "com.xr.run.dao.SysMechanismMapper.findSysMechanismByIdToMechanismName")),
            @Result(column = "mid",property = "mid")
    })
    List<SysPost> findSYsPostPage(@Param("pageNum") Integer pageNum, @Param("pageRow") Integer pageRow,@Param("message")String message,@Param("pame")String pame,@Param("str") String str);

    /**
     * 分页数量
     */
    @Select("select count(pid) from sys_post where EXISTS " +
            "(select mid from sys_mechanism where sys_post.mid=sys_mechanism.mid ${str} AND staus = '正常') and " +
            "pname like CONCAT('%',#{pame},'%') and staus='1'  and message like CONCAT('%',#{message},'%')")
    Integer findSYsPostPagecount(@Param("message")String message,@Param("pame")String pame,@Param("str") String str);
    /**
     * 新增
     */
    @Insert("insert into sys_post value(null,#{p.pname},#{p.mid},#{p.message},#{p.createTime},#{p.createId},'1')")
    @Options(useGeneratedKeys = true,keyProperty = "pid",keyColumn = "pid")
    void addSysPost(@Param("p")SysPost sysPost);
    /**
     * 修改
     */
    @Update("update sys_post set pname=#{p.pname},mid=#{p.mid},message=#{p.message} where pid=#{p.pid}")
    void upSysPost(@Param("p")SysPost sysPost);
    /**
     * 删除
     */
    @Update("update sys_post set staus='2' where pid = #{pid}")
    void delSysPost(@Param("pid")Integer pid);
    /**
     * 获取全部SysPost
     */
    @Select("select pid,pname from sys_post where staus='1'")
    List<SysPost> getSysPost();
}
