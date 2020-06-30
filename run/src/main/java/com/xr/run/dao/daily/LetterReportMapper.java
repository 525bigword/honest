package com.xr.run.dao.daily;

import com.xr.run.entity.daily.LetterReport;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LetterReportMapper {
    /*信访编号*/
    @Select("select replace(uuid(),'-','') from dual")
    public String xfbh();
    /*查询*/
    public List<LetterReport> list(LetterReport letterReport);
    /*新增*/
    public void addletter(LetterReport letterReport);
/*删除*/
    public void deletter(Integer lid);
    /*按条件查询*/
    public List<LetterReport> findbyName(LetterReport letterReport);
    /*纪检监察员编写内容摘要提交审核*/
    public void shbc(LetterReport letterReport);
    /*按id查询*/
    public List<LetterReport> listLetterByid(Integer lid);
    /*用户提交审核*/
    public void tjshme(Integer lid);
    /*转办部门*/
    public void turndept(Integer lid);
    /*监察科自办结果*/
    public void kezhangbc(LetterReport letterReport);
    /*转办部门处理结果*/
    public void deptbc(LetterReport letterReport);
    /*用户更新*/
    public void bjbcmethod(LetterReport letterReport);
}
