package com.xr.run.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xr.run.entity.SysPermission;
import com.xr.run.entity.SysPost;
import com.xr.run.entity.SysPostPermission;
import com.xr.run.entity.SysPpost;
import org.apache.ibatis.annotations.*;
import org.omg.CORBA.INTERNAL;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysPostPermissionMapper extends BaseMapper<SysPostPermission> {
    @Delete("delete from sys_post_permission where pid=#{pid}")
    void delSysPostPermissionByPid(@Param("pid") Integer pid);
    // TODO 查询所有权限中的列表权限
    @Select("select id,menu_name from sys_permission where required_permission=1")
    List<SysPermission> findSysPostByList();
    // TODO 按类型查询所有非列表权限
    @Select("select id,permission_name menu_name from sys_permission where menu_code= " +
            "(select menu_code from sys_permission where id=#{id} ) ")
    List<SysPermission> findSysPostUnList(@Param("id") Integer id);
    /**
     * 根据postid查询pid
     */
    @Select("select pid from sys_post_permission where postid=#{postid}")
    List<Integer> findSysPostPermissionByPostIdToPid(@Param("postid") Integer postid);
    /**
     * 按postid删除
     */
    @Delete("delete from sys_post_permission where postid=#{postid}")
    void delSysPostPermissionByPostId(@Param("postid")Integer postid);
    /**
     * 新增
     */
    @Insert("insert into sys_post_permission value(null,#{p.pid},#{postid},1,#{p.createTime},#{p.createId})")
    void addSysPostPermission(@Param("p")SysPost sysPost, @Param("postid")Integer postid);
    //根据postid查询
    @Select("select id,postid,pid from sys_post_permission where postid=#{postid} and stuas='1'")
    List<SysPostPermission> findSysPostPermissionByPostId(@Param("postid")Integer postId);
    @Select("select pid,pname,mid,message,create_time,create_id,staus from sys_ppost where EXISTS " +
            "(select mid from sys_mechanism where sys_ppost.mid=sys_mechanism.mid ${str} AND staus = '正常') and " +
            "pname like CONCAT('%',#{pame},'%') and staus='0' and message like CONCAT('%',#{message},'%') order by pid desc limit #{pageNum},#{pageRow} ")
    @Results({
            @Result(column = "create_id",property = "createname",one=@One(select = "com.xr.run.dao.SysStaffMapper.findSysStaffByIdToName")),
            @Result(column = "mid",property = "mname",one=@One(select = "com.xr.run.dao.SysMechanismMapper.findSysMechanismByIdToMechanismName")),
            @Result(column = "mid",property = "mid")
    })
    List<SysPpost> findSysPpostPage(@Param("pageNum") Integer pageNum, @Param("pageRow") Integer pageRow,@Param("message")String message,@Param("pame")String pame,@Param("str") String str);

    @Select("select count(pid) from sys_ppost where EXISTS " +
            "(select mid from sys_mechanism where sys_ppost.mid=sys_mechanism.mid ${str} AND staus = '正常') and " +
            "pname like CONCAT('%',#{pame},'%') and staus='0' and message like CONCAT('%',#{message},'%') order by pid desc ")
    Integer findSysPpostPagecount(@Param("message")String message,@Param("pame")String pame,@Param("str") String str);
    @Insert("insert into sys_ppost value(null,#{p.pname},#{p.mid},#{p.message},#{p.createTime},#{p.createId},0)")
    void addSysPpost(@Param("p") SysPpost sysPpost);
    @Update("update sys_ppost set pname=#{p.pname},mid=#{p.mid},message=#{p.message} where pid=#{p.pid}")
    void upSysPpost(@Param("p")SysPpost sysPpost);
    @Update("update sys_ppost set staus=1 where pid=#{pid}")
    void delSysPpost(@Param("pid")int pid);
    //根绝Mid查询
    @Select("select pid,pname,mid,message,create_time,create_id,staus from sys_ppost where mid=#{mid}")
    List<SysPpost> findSysPpostByMid(@Param("mid") Integer mid);
    //根据pid查询岗位名
    @Select("select pname from sys_ppost where pid=#{pid}")
    String findSysPpostToPnameByPid(@Param("pid") Integer pid);
}
