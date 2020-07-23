package com.xr.run.dao.risk;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xr.run.dao.risk.sqlConver.PostRiskCombingSQL;
import com.xr.run.entity.PostRiskCombing2;
import com.xr.run.entity.Postriskcombing;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRiskCombingMapper {


    /**
     * 查询所有岗位流程梳理信息列表
     * @param page
     * @return
     */
    @SelectProvider(type = PostRiskCombingSQL.class,method = "list")
    @Results({
            @Result(column = "pDeptId",property = "deptName",one = @One(select = "getpDeptNameById",fetchType = FetchType.DEFAULT)),
            @Result(column = "pDeptId",property = "pDeptId"),
            @Result(column = "pInfomationId",property = "postname",one = @One(select = "getpPostNameById",fetchType = FetchType.DEFAULT)),
            @Result(column = "pInfomationId",property = "pInfomationId"),
            @Result(column = "pRiskPointDescription",property = "pRiskPointDescription"),
            @Result(column = "pProbableLValue",property = "pProbableLValue"),
    })
    public IPage<Postriskcombing> list(Page page);
    // 条件查询
    /**
     * @param pInfomationId
     * @param pDeptId
     * @return
     */
    @SelectProvider(type = PostRiskCombingSQL.class,method = "getListBy")
    @Results({
            @Result(column = "pDeptId",property = "deptName",one = @One(select = "getpDeptNameById",fetchType = FetchType.DEFAULT)),
            @Result(column = "pDeptId",property = "pDeptId"),
            @Result(column = "pInfomationId",property = "postname",one = @One(select = "getpPostNameById",fetchType = FetchType.DEFAULT)),
            @Result(column = "pInfomationId",property = "pInfomationId"),
            @Result(column = "pRiskPointDescription",property = "pRiskPointDescription"),
            @Result(column = "pProbableLValue",property = "pProbableLValue"),

    })
    IPage<Postriskcombing> getListBy(@Param("pInfomationId") Integer pInfomationId, @Param("pDeptId") Integer pDeptId, @Param("pGrade") String pGrade, Page page);
    @Select("select mechanism_name from sys_mechanism where mid=#{id}")
    String getpDeptNameById(@Param("id")Integer id);
    @Select("select pname from sys_ppost where pid=#{id}")
    String getpPostNameById(@Param("id")Integer id);


    /**
     * 根据id 查询
     * @param pcid
     * @return
     */
    public List<PostRiskCombing2> findByPcID(Integer pcid);

    /**
     * 添加用户
     * @param postriskcombing
     * @return
     */
    @Insert(" insert into postriskcombing value(0,null,#{pYear},#{pDeptId},#{pInfomationId}," +
            "#{pProject},#{pRiskPointDescription},#{pProbableLValue},#{pCValue},#{pDValue}," +
            "#{pGrade},#{pMeasures},now(),#{pCreateId},#{pCreateName},1);")
    public void add(Postriskcombing postriskcombing);
    /**
     * 根据id 删除 修改状态为失效
     * @param list
     * @return
     */
    @Update({"<script>update postriskcombing set pStatus=0 where pid in "
            +"<foreach collection='list' item='pcid' index='i' open='(' separator= ',' close = ')'>"
            +"#{pcid}"
            +"</foreach>"
            +"</script>"
    })
    public void deleteById(List<Integer> list);

    @Update({"update riskpointassessment set numberOneRisks=numberOneRisks-1 where riskImfomation=#{riskImfomation}"})
    public void updateRiskPointAssessmentByIdOne(Integer riskImfomation);
    @Update({"update riskpointassessment set numberTwoRisks=numberTwoRisks-1 where riskImfomation=#{riskImfomation}"})
    public void updateRiskPointAssessmentByIdTwo(Integer riskImfomation);
    @Update({"update riskpointassessment set numberThreeRisks=numberThreeRisks-1 where riskImfomation=#{riskImfomation}"})
    public void updateRiskPointAssessmentByIdThree(Integer riskImfomation);
    /**
     * 根据id 修改信息
     * @param postriskcombing
     * @return
     */
    @Update("update postriskcombing set pYear=#{pYear},pDeptId=#{pDeptId},pInfomationId=#{pInfomationId}," +
            "pProject=#{pProject},pRiskPointDescription=#{pRiskPointDescription},pProbableLValue=#{pProbableLValue}," +
            "pCValue=#{pCValue},pDValue=#{pDValue},pGrade=#{pGrade},pMeasures=#{pMeasures} where pid=${pid}")
    public void update(Postriskcombing postriskcombing);

    // 根据风险等级统计部门数据行数
    @Select("select count(pid) from postriskcombing  where  pDeptId=#{pDeptId} and pGrade=#{pGrade} and pStatus=1")
    public int findByGrade(Postriskcombing postriskcombing);

}
