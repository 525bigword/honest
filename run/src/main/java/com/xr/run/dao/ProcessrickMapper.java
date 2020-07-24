package com.xr.run.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xr.run.entity.Postriskcombing;
import com.xr.run.entity.Processrick;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProcessrickMapper extends BaseMapper<Processrick> {
       @Select("select proid,processID,proName,proYear,proInfomation,proAccessory,proCreateTime,proCreateId,proCreateName,proStatus from processrick " +
            " where  proName like CONCAT('%',#{proName},'%') order by proid desc")
    @Results({
            @Result(column = "proCreateId",property = "proCreateId"),
            @Result(column = "proCreateId",property = "sysStaff",
                    one = @One(select = "com.xr.run.dao.SysStaffMapper.findSysStaffById",fetchType = FetchType.DEFAULT))
    })
    IPage<Processrick> findProcessrickIndex(Page page,@Param("proName") String proName);

    /**
     * 查询列表
     *
     * @return
     */
    @Select("select  proid,proName,proYear,proAccessory,proAccessoryName,proInfomation," +
            "        proMeasures,proGrade,proCreateTime,proCreateId,proCreateName,proStatus,proAccessoryName" +
            "        from processrick where proStatus=1 ORDER BY proid desc")
    List<Processrick> getList();

    /**
     * 新增
     *
     * @param processrick
     */
/*
    @Insert("insert into processrick(processID,proName,proYear,proInfomation,proAccessory,proCreateTime,proCreateId,proCreateName,proStatus,proAccessoryName) values(#{processId},#{proName},#{proYear},#{proInfomation},#{proAccessory},#{proCreateTime},#{proCreateId},#{proCreateName},#{proStatus},#{proAccessoryName})")
*/
    @Insert("insert into processrick" +
            " values(0,null,#{p.proName},#{p.proYear},#{p.proAccessory}," +
            "#{p.proAccessoryName},#{p.proInfomation},#{p.proMeasures},#{p.proGrade}," +
            "now(),#{p.proCreateId},#{p.proCreateName},1)")
    void addProcessrick(@Param("p") Processrick processrick);

    /**
     * 根据ID删除
     *
     * @param proid
     */
    @Delete("update processrick set proStatus=0 where proid=#{proid}")
    void deleteByProid(Integer proid);

    /**
     * 更新
     *
     * @param processrick
     */
    @Update("update processrick set proName=#{proName},proYear=#{proYear},proAccessory=#{proAccessory}," +
            "proAccessoryName=#{proAccessoryName},proInfomation=#{proInfomation},proMeasures=#{proMeasures},proGrade=#{proGrade}" +
            " where proid=#{proid}")
    void updateByProcessrick(Processrick processrick);

    /**
     * 根据条件筛选查询
     *
     * @param proName
     * @param proYear
     * @return
     */
    List<Processrick> getListBy(@Param("proName") String proName, @Param("proYear") String proYear,@Param("proGrade") String proGrade);

    @Select("select p.proGrade,p.proid,p.processID,p.proName,p.proMeasures,p.proYear,p.proInfomation,p.proAccessory,p.proCreateTime,p.proCreateId,p.proCreateName,p.proStatus,m.pname,sm.mechanism_name as mname from processrick p " +
            "  left join sys_post m on p.processID = m.pid left join sys_mechanism sm on sm.mid = m.mid  " +
            " where p.proid=#{id} order by p.proid desc ")
    Processrick findProcessrickById(@Param("id") Integer id);

    @Select("select p.proGrade,p.proid,p.processID,p.proName,p.proMeasures,p.proYear,p.proInfomation,p.proAccessory,p.proCreateTime,p.proCreateId,p.proCreateName,p.proStatus,m.pname,sm.mechanism_name as mname from processrick p " +
            " left join sys_post m on p.processID = m.pid left join sys_mechanism sm on sm.mid = m.mid " +
            " where p.proStatus =1 and  p.proName like CONCAT('%',#{proName},'%') order by p.proid desc")
    @Results({
            @Result(column = "proCreateId",property = "proCreateId"),
            @Result(column = "proCreateId",property = "sysStaff",
                    one = @One(select = "com.xr.run.dao.SysStaffMapper.findSysStaffById",fetchType = FetchType.DEFAULT))
    })
    IPage<Processrick> findProcessrickIndex1(Page page,@Param("proName") String proName);
}
