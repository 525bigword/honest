package com.xr.run.dao.daily;

import com.xr.run.entity.daily.Supervisionfiling;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "SupervisionfilingMapper")
public interface SupervisionfilingMapper {
/*查询*/
    public List<Supervisionfiling> list();
    /*按事项摘要查询*/
    public List<Supervisionfiling> findbytitle(String sPaperItems);
    /*新增*/
    public void addsupervisionfiling(Supervisionfiling supervisionfiling);
    /*删除*/
    public void delesupervision(Integer sid);
    /*修改*/
    public void tjshme(Integer sid);
    /*更新*/
    public void updatesupervision(Supervisionfiling supervisionfiling);
    /*根据编号查询*/
    public List<Supervisionfiling> findbysFilingId(String sFilingId);
    /*审核意见*/
    public void tgsh(Supervisionfiling supervisionfiling);
    /*查询是否包含业务*/
    @Select("SELECT count(sid) FROM (SELECT sid,sStatus FROM daily_supervisionfiling where sStatus<>2 and sStatus<>-1) daily_supervisionfiling " +
            " WHERE sid = #{sid}")
    Integer findSupervisionfilingByWstatusToCount(@Param("sid")Integer sid);
}
