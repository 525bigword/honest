package com.xr.run.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xr.run.entity.SysMechanism;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysMechanismMapper extends BaseMapper<SysMechanism> {
    @Select("select mechanism_name from sys_mechanism where mid=#{mid}")
    String findMechanismNameByMid(@Param("mid") Integer mid);

    @Select("select mid,mechanism_name,sid,parent,branch,create_time,create_id,staus from sys_mechanism where EXISTS " +
            "(SELECT sid FROM sys_staff WHERE `NAME` LIKE CONCAT('%',#{principal},'%') AND sys_mechanism.sid = sys_staff.sid AND staus = '1')" +
            " and mechanism_name like CONCAT('%',#{mechanism},'%') and staus=#{staus} order by mid desc limit #{index},#{pagenum} ")
    @Results({
            @Result(property = "sname",column = "sid",one = @One(select = "com.xr.run.dao.SysStaffMapper.findSysStaffByIdToName")),
            @Result(property = "parentName",column = "parent",one = @One(select = "com.xr.run.dao.SysMechanismMapper.findSysMechanismByIdToMechanismName")),
            @Result(property = "parent",column = "parent"),
            @Result(property = "sid",column = "sid"),
            @Result(property = "branch",column = "branch"),
            @Result(property = "branchName",column = "branch",one = @One(select = "com.xr.run.dao.SysStaffMapper.findSysStaffByIdToName")),
            @Result(property = "createName",column = "create_id",one = @One(select = "com.xr.run.dao.SysStaffMapper.findSysStaffByIdToName")),
    })
    List<SysMechanism> findSysMechanismPage(@Param("principal") String principal,@Param("index") Integer index, @Param("pagenum") Integer pagenum, @Param("mechanism") String mechanism, @Param("staus") String staus);
    @Select("select count(mid) from sys_mechanism where EXISTS " +
            "(SELECT sid FROM sys_staff WHERE `NAME` LIKE CONCAT('%',#{principal},'%') AND sys_mechanism.sid = sys_staff.sid AND staus = '1')" +
            " and mechanism_name like CONCAT('%',#{mechanism},'%') and staus=#{staus}")
    Integer findSysMechanismPageCount(@Param("principal") String principal,@Param("mechanism") String mechanism, @Param("staus") String staus);

    /**
     * 根据Id查询机构名称
     */
    @Select("select mechanism_name from sys_mechanism where mid=#{mid}")
    String findSysMechanismByIdToMechanismName(@Param("mid")Integer mid);
    /**
     * 按parent查
     */
    @Select("select mid,mechanism_name,sid,parent,branch,create_time,create_id,staus from sys_mechanism where parent=#{parent} and staus='正常'")
    @Results({
            @Result(column = "mechanismName",property = "lable"),
            @Result(column = "mid",property = "value"),
            @Result(column = "mid",property = "mid"),
            @Result(column = "sid",property = "sid"),
            @Result(column = "branch",property = "branch")
    })
    List<SysMechanism> findSysMechanismByParentAll(@Param("parent")Integer parent);
    /**
     * 新增SysMechanism
     */
    @Insert("insert into sys_mechanism value(null,#{m.mechanismName},#{m.sid},#{m.parent},#{m.branch},#{m.createTime},#{m.createId},'正常')")
    void addSysMechanism(@Param("m") SysMechanism sysMechanism);
    /**
     * 查询该项是否有子项
     */
    @Select("select count(mid) from sys_mechanism where parent=#{mid}")
    Integer findSysMechanismByParentTocount(@Param("mid")Integer mid);
    /**
     * 删除该部门(修改部门状态)
     */
    @Update("update sys_mechanism set staus='删除' where mid=#{mid}")
    void delSysMechanism(@Param("mid")Integer mid);
    /**
     * 恢复该部门(修改部门状态)
     */
    @Update("update sys_mechanism set staus='正常' where mid=#{mid}")
    void UnDelSysMechanism(@Param("mid")Integer mid);
    /**
     * 修改部门信息
     */
    @Update("update sys_mechanism set mechanism_name=#{m.mechanismName},sid=#{m.sid},parent=#{m.parent},branch=#{m.branch} where mid=#{m.mid}")
    void upSysMechanismByMid(@Param("m") SysMechanism sysMechanism);

    @Select("select mid,mechanism_name from sys_mechanism where mid=#{mid}")
    SysMechanism findSysMechanismMid(int mid);
    //全部信息
    @Select("select mid,mechanism_name,sid,parent,branch,create_time,create_id,staus from sys_mechanism")
    @Results({
            @Result(property = "sname",column = "sid",one = @One(select = "com.xr.run.dao.SysStaffMapper.findSysStaffByIdToName")),
            @Result(property = "parentName",column = "parent",one = @One(select = "com.xr.run.dao.SysMechanismMapper.findSysMechanismByIdToMechanismName")),
            @Result(property = "parent",column = "parent"),
            @Result(property = "sid",column = "sid"),
            @Result(property = "branch",column = "branch"),
            @Result(property = "branchName",column = "branch",one = @One(select = "com.xr.run.dao.SysStaffMapper.findSysStaffByIdToName")),
            @Result(property = "createName",column = "create_id",one = @One(select = "com.xr.run.dao.SysStaffMapper.findSysStaffByIdToName")),
    })
    List<SysMechanism> findSysMechanism();


    @Select("select mid,mechanism_name from sys_mechanism")
    @Results({
            @Result(id = true,column = "mid",property ="mid" ),
            @Result(column = "mechanism_name",property = "mechanismName")
    })
    public List<SysMechanism> findSysMechanisms();

    @Select("select *  from sys_mechanism where mid=#{mid}")
    SysMechanism findByMid(@Param("mid") int mid);

    /**
     * 查询所有状态正常的部门
     * @return
     */
    @Select("select *  from sys_mechanism where staus='正常'")
    List<SysMechanism> getAll();

}
