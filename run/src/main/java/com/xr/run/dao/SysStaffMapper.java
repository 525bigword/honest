package com.xr.run.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xr.run.entity.SysStaff;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysStaffMapper extends BaseMapper<SysStaff> {
    @Select("select sid,name from sys_staff where pid=#{pid} and mid=#{mid}")
    List<SysStaff> findstatffByPid(@Param("pid") Integer pid,@Param("mid")Integer mid);
    @Select("select sid,name from sys_staff where mid=#{mid}")
    List<SysStaff> findstatffByMid(@Param("mid")Integer mid);
    /**
     * 恢复
     * @param id
     */
    @Update("update sys_staff set staus=1 where sid=#{id}")
    void hfSysStaffByid(@Param("id") Integer id);
    /**
     * 永久物理删除用户
     */
    @Delete("delete from sys_staff where sid=#{id} and staus=2")
    void yjDelSysStaffById(@Param("id") Integer id);
    /**
     * 根据用户名和密码查询对应的用户
     */
    @Select("SELECT u.sid,u.name, u.sex,u.age,u.educational_background,u.political_appearance,u.phone,u.mid,u" +
            ".username,u.password,u.pid,u.create_time,u.create_id,u.staus FROM " +
            " sys_staff u WHERE u.username = #{username}  AND u.staus = '1'")
    SysStaff getUser(@Param("username") String username, @Param("password") String password);
    @Select("select sid,name,sex,age,educational_background,political_appearance,phone,mid,username,password,pid,ppid" +
            "create_time,create_id,staus from sys_staff where name like CONCAT('%',#{name},'%') and staus=#{staus} and mid=#{mid} order by sid desc")
    @Results({
            @Result(id = true,property = "sid",column = "sid"),
            @Result(property = "name",column = "name"),
            @Result(property = "age",column = "age"),
            @Result(property = "mid",column = "mid"),
            @Result(property = "username",column = "username"),
            @Result(property = "password",column = "password"),
            @Result(property = "pid",column = "pid"),
            @Result(property = "mechanismname",column = "mid", one=@One(select = "com.xr.run.dao.SysMechanismMapper.findMechanismNameByMid",fetchType = FetchType.DEFAULT)),
            @Result(property = "postname",column = "pid", one=@One(select = "com.xr.run.dao.SysPostMapper.findSysPostPnameByPid",fetchType = FetchType.DEFAULT)),
            @Result(property = "sex",column = "sex"),
            @Result(property = "educationalBackground",column = "educational_background"),
            @Result(property = "politicalAppearance",column = "political_appearance"),
            @Result(property = "phone",column = "phone"),
            @Result(property = "ppid",column = "ppid"),
            @Result(property = "ppname",column = "ppid", one=@One(select = "com.xr.run.dao.SysPostPermissionMapper.findSysPpostToPnameByPid",fetchType = FetchType.DEFAULT)),

    })
    IPage<SysStaff> findSysStaffAll(Page page,@Param("name") String name, @Param("staus")Integer staus,@Param("mid")Integer mid);
    @Select("select sid,name,sex,age,educational_background,political_appearance,phone,mid,username,password,pid,ppid," +
            "create_time,create_id,staus from sys_staff where name like CONCAT('%',#{name},'%') and staus=#{staus} order by sid desc")
    @Results({
            @Result(id = true,property = "sid",column = "sid"),
            @Result(property = "name",column = "name"),
            @Result(property = "age",column = "age"),
            @Result(property = "mid",column = "mid"),
            @Result(property = "username",column = "username"),
            @Result(property = "password",column = "password"),
            @Result(property = "pid",column = "pid"),
            @Result(property = "mechanismname",column = "mid", one=@One(select = "com.xr.run.dao.SysMechanismMapper.findMechanismNameByMid",fetchType = FetchType.DEFAULT)),
            @Result(property = "postname",column = "pid", one=@One(select = "com.xr.run.dao.SysPostMapper.findSysPostPnameByPid",fetchType = FetchType.DEFAULT)),
            @Result(property = "sex",column = "sex"),
            @Result(property = "educationalBackground",column = "educational_background"),
            @Result(property = "politicalAppearance",column = "political_appearance"),
            @Result(property = "phone",column = "phone"),
            @Result(property = "ppid",column = "ppid"),
            @Result(property = "ppname",column = "ppid", one=@One(select = "com.xr.run.dao.SysPostPermissionMapper.findSysPpostToPnameByPid",fetchType = FetchType.DEFAULT)),
    })
    IPage<SysStaff> findSysStaffAllNoMid(Page page,@Param("name") String name, @Param("staus")Integer staus);

    /**
     * 按名字模糊查询员工id
     */
    @Select("select sid from sys_staff where name like CONCAT('%',#{name},'%')")
    List<Integer> findSysStaffByNameToId(@Param("name") String name);
    /**
     * 按id查询员工名字
     */
    @Select("select name from sys_staff where sid=#{sid}")
    String findSysStaffByIdToName(@Param("sid")Integer sid);

    /**
     * 查询所有员工
     * @return
     */
    @Select("select sid,name from sys_staff where staus='1'")
    List<SysStaff> findSysStaffAlla();
    /**
     * 根据部门查询员工数量
     */
    @Select("select count(sid) from sys_staff where staus='1' and mid=#{mid}")
    Integer findSysStaffByMidToCount(@Param("mid")Integer mid);

    /**
     * 查询单个用户
     * @param sid
     * @return
     */
    @Select("select sid,`name`,mid from sys_staff where sid=#{sid}")
    SysStaff findSysStaffById(int sid);

    @Select("select sid,`name`,mid from sys_staff where mid=#{mid}")
    List<SysStaff> findSysStaffByMid(int mid);
    /**
     * 查询全部
     * @return
     */
    @Select("select sid,`name` from sys_staff")
    List<SysStaff>  findSysStaff();

    /**
     * 根据岗位查询员工数量
     */
    @Select("select count(sid) from sys_staff where pid=#{pid} and staus <>1")
    Integer findSysStaffByPidToCount(@Param("pid") Integer pid);
    //新增
    @Insert("insert into sys_staff value(null,#{name},#{sex},#{age},#{educationalBackground},#{politicalAppearance},#{phone},#{mid},#{username},#{password},#{pid},#{createTime},#{createId},'1',#{ppid})")
    void addSysStaff(SysStaff sysStaff);
    //根据username查询
    @Select("select count(sid) from sys_staff where username=#{username}")
    Integer findSysStaffCount(@Param("username") String username);
    //根据username查询
    @Select("select sid from sys_staff where username=#{username}")
    Integer findSysStaffByUserName(@Param("username") String username);
    @Update("update sys_staff set name=#{s.name},sex=#{s.sex},age=#{s.age},educational_background=#{s.educationalBackground},political_appearance=#{s.politicalAppearance},phone=#{s.phone},mid=#{s.mid},password=#{s.password},pid=#{s.pid},staus=#{s.staus} where sid=#{s.sid}")
    void upSysStaff(@Param("s") SysStaff sysStaff);
    //根据sid查询员工
    @Select("select  sid,name,sex,age,educational_background,political_appearance,phone,mid,username,password,pid, create_time,create_id,staus from sys_staff where sid=#{sid}")
    SysStaff findSysStaffBySid(@Param("sid")Integer sid);
    //根据id删除(修改状态)
    @Update("update sys_staff set staus=2 where sid=#{sid}")
    void upSysStaffStausBySid(@Param("sid")Integer sid);
    //根据sid查询员工
    @Select("select sid,name,sex,age,educational_background,political_appearance,phone,mid,username,password,pid, create_time,create_id,staus from sys_staff where sid=#{sid}")
    @Results({
            @Result(column = "mid",property = "mechanismname",one = @One(select = "com.xr.run.dao.SysMechanismMapper.findSysMechanismByIdToMechanismName")),
            @Result(column = "pid",property = "postname",one = @One(select = "com.xr.run.dao.SysPostMapper.findSysPostPnameByPid")),
            @Result(column = "mid",property = "mid"),
            @Result(column = "pid",property = "pid")
    })
    SysStaff findSysStaffBySidDetail(@Param("sid")Integer sid);

}
