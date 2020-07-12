package com.xr.run.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xr.run.entity.Letterreport;
import com.xr.run.entity.Postriskcombing;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostriskcombingMapper extends BaseMapper<Postriskcombing> {
    @Select("select pid,pRiskId,pYear,pDeptId,pInfomationId,pProject,pRiskPointDescription,pProbableLValue,pCValue,pDValue,pGrade,pMeasures," +
            " pCreateTime,pCreateId,pCreateName,pStatus from postriskcombing where pStatus = 0 and  pRiskPointDescription " +
            " like CONCAT('%',#{pRiskPointDescription},'%') order by pid desc")
    @Results({
            @Result(column = "pCreateId", property = "pCreateId"),
            @Result(column = "pCreateId", property = "sysStaff",
                    one = @One(select = "com.xr.run.dao.SysStaffMapper.findSysStaffById", fetchType = FetchType.DEFAULT))
    })
    IPage<Postriskcombing> findPostriskCombingIndex(Page page,@Param("pRiskPointDescription") String pRiskPointDescription);


    /**
     * 查询列表
     *
     * @return
     */
    @Select("select pid,pRiskId,pYear,pDeptId,pInfomationId,pProject,pRiskPointDescription,pProbableLValue,pCValue,pDValue,pGrade,pMeasures,pCreateTime,pCreateId,pCreateName,pStatus from postriskcombing")
    List<Postriskcombing> getList();


    /**
     * 根据ID删除
     *
     * @param pid
     */
    @Delete("delete from postriskcombing where pid=#{pid}")
    void deleteById(Integer pid);


    /**
     * 更新信息
     *
     * @param postriskcombing
     */
    @Update("update postriskcombing set pRiskId=#{pRiskId},pInfomationId=#{pInfomationId},pDeptId=#{pDeptId},pProject=#{pProject},pRiskPointDescription=#{pRiskPointDescription},pProbableLValue=#{pProbableLValue},pCValue=#{pCValue},pMeasures=#{pMeasures} where pid=#{pid}")
    void updateByPid(Postriskcombing postriskcombing);


    /**
     * 新增
     *
     * @param postriskcombing
     */
    @Insert("insert into postriskcombing(pRiskId,pYear,pDeptId,pInfomationId,pProject,pRiskPointDescription,pProbableLValue,pCValue,pDValue,pGrade,pMeasures,pCreateTime,pCreateId,pCreateName,pStatus) values(#{pRiskId},#{pYear},#{pDeptId},#{pInfomationId},#{pProject},#{pRiskPointDescription},#{pProbableLValue},#{pCValue},#{pDValue},#{pGrade},#{pMeasures},#{pCreateTime},#{pCreateId},#{pCreateName},#{pStatus})")
    @Options(useGeneratedKeys = true,keyProperty = "pid")
    void addPostriskcombing(Postriskcombing postriskcombing);

    /**
     * 根据风险项目名称查询列表
     *
     * @param pProject
     * @return
     */
    @Select("select pid,pRiskId,pYear,pDeptId,pInfomationId,pProject,pRiskPointDescription,pProbableLValue,pCValue,pDValue,pGrade,pMeasures,pCreateTime,pCreateId,pCreateName,pStatus from postriskcombing where pProject like CONCAT('%',#{pProject},'%')")
    List<Postriskcombing> getListByPproject(String pProject);

    /**
     * @param pInfomationId
     * @param pDeptId
     * @return
     */
    List<Postriskcombing> getListBy(@Param("pInfomationId") Integer pInfomationId, @Param("pDeptId") Integer pDeptId, @Param("pGrade") String pGrade);

    /**
     * 根据部门ID和风险等级查询
     *
     * @param pDeptId
     * @param pGrade
     * @return
     */
    @Select("select * from postriskcombing where pDeptId=#{pDeptId} and pGrade=#{pGrade}")
    List<Postriskcombing> findByPDeptIdAndPGrade(@Param("pDeptId") Integer pDeptId, @Param("pGrade") String pGrade);
    @Select("select pid,pRiskId,pYear,pDeptId,pInfomationId,pProject,pRiskPointDescription,pProbableLValue,pCValue,pDValue,pGrade,pMeasures,pCreateTime,pCreateId,pCreateName,pStatus from postriskcombing where pid=#{pid}")
    @Results({
            @Result(column = "pCreateId",property = "pCreateName",one = @One(select = "com.xr.run.dao.SysStaffMapper.findSysStaffByIdToName",fetchType = FetchType.DEFAULT))
    })
    Postriskcombing findPostriskcombingByPid(@Param("pid")Integer pid);

}
