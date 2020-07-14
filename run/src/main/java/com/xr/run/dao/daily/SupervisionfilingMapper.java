package com.xr.run.dao.daily;

import com.xr.run.entity.daily.Supervisionfiling;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "SupervisionfilingMapper")
public interface SupervisionfilingMapper {
/*查询*/
    public List<Supervisionfiling> list(@Param(value = "sStatus") Integer sStatus);
    /*按事项摘要查询*/
    public List<Supervisionfiling> findbytitle(@Param(value = "sPaperItems")String sPaperItems,@Param(value = "sStatus") Integer sStatus);
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
    /*验收*/
    public void signleader(Integer sid);
}
