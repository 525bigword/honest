package com.xr.run.dao.tam;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xr.run.entity.tam.Smokestyle;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SmokestyleMapper {
    /*查询潭烟风貌*/
    public List<Smokestyle> findAllSmokestyle();

    /*新增*/
    public void addSmokestyle(Smokestyle smokestyle);

    /*根据标题查*/
    public List<Smokestyle> findSmokestyle(Smokestyle smotitle);

    /*删除*/
    public void delSmokestyle(Integer smoid);

    /*更新*/
    public void gxSmokestyle(Smokestyle smokestyle);

    @Select("select tms.smoid,tms.smotitle,tms.smoproperty,tms.smoimage,tms.smosource,tms.smoauthor,tms.smocreatetime," +
            "tms.smodescrion,tms.smocontent,tms.smocreateid,tms.smocreatename," +
            " tms.surl,ss.sid,ss.name from tam_smoke_style tms,sys_staff ss where tms.smocreateid=ss.sid " +
            " and tms.sstatus!=-1 and tms.smotitle like CONCAT('%',#{title},'%') order by tms.smoid desc")
    public IPage<Smokestyle> findAllSmokestyle1(Page page, @Param("title") String title);


    @Select("select tms.smoid,tms.smotitle,tms.smoproperty,tms.smoimage,tms.smosource,tms.smoauthor,tms.smocreatetime," +
            "tms.smodescrion,tms.smocontent,tms.smocreateid,tms.smocreatename," +
            " tms.surl,ss.sid,ss.name from tam_smoke_style tms,sys_staff ss where tms.smocreateid=ss.sid " +
            " and tms.sstatus!=-1  order by tms.smoid desc")
    List<Smokestyle> findAllSmokestyle2();
}
