package com.xr.run.dao.risk.sqlConver;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Component;

@Component
public class PostRiskCombingSQL {
    public String getListBy(@Param("pInfomationId") Integer pInfomationId, @Param("pDeptId") Integer pDeptId, @Param("pGrade") String pGrade, Page page){
        StringBuffer sql = new StringBuffer();
//        SELECT pc.pid,pRiskId,pYear,pDeptId,pInfomationId,pProject,pRiskPointDescription,pProbableLValue,
//                pCValue,pDValue,pGrade,pMeasures,pCreateTime,pCreateId,pCreateName,m.mechanism_name,p.pname
//        from postriskcombing pc LEFT JOIN sys_mechanism m on pc.pDeptId=m.mid LEFT JOIN  sys_post p on pc.pInfomationId=p.pid
//        where pStatus=1
        sql.append("select pc.pid,pRiskId,pYear,pDeptId,pInfomationId," +
                "pProject,pRiskPointDescription,pProbableLValue,pCValue,pDValue" +
                ",pGrade,pMeasures,pCreateTime,pCreateId,pCreateName,m.mechanism_name,p.pname from postriskcombing pc LEFT JOIN sys_mechanism m on pc.pDeptId=m.mid LEFT JOIN  sys_post p on pc.pInfomationId=p.pid" +
                " where pStatus=1");

        if(null!=pDeptId){
            sql.append(" and pDeptId="+pDeptId);
        }
        if(0!=pInfomationId){
            sql.append(" and pInfomationId="+pInfomationId);
        }
        if(!StringUtils.isEmpty(pGrade)){
            sql.append(" and pGrade='"+pGrade+"'");
        }
        sql.append(" order by pc.pid desc");
        return sql.toString();
    }
}
