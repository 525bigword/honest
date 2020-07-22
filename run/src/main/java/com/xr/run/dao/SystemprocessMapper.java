package com.xr.run.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xr.run.entity.Systemprocess;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemprocessMapper extends BaseMapper<Systemprocess> {
    @Select("select sysid,sysTitle,sysContent,sysCreateTime,sysCreateId,sysStatus from systemprocess where sysStatus=0 and sysTitle like CONCAT('%',#{sysTitle},'%') and sysContent like CONCAT('%',#{sysContent},'%') order by sysid desc")
    @Results({
            @Result(column = "sysCreateId",property = "sysStaff",
                    one = @One(select = "com.xr.run.dao.SysStaffMapper.findSysStaffById",fetchType = FetchType.DEFAULT))
    })
    IPage<Systemprocess> findSystemprocess(Page page, String sysTitle, String sysContent);

    @Update("update systemprocess set sysTitle=#{sysTitle},sysContent=#{sysContent} where sysid=#{sysid}")
    void updateSystemprocessBySysId(Systemprocess systemprocess);

    @Insert("insert into systemprocess(sysid,sysTitle,sysContent,sysCreateTime,sysCreateId,sysStatus) VALUES(null,#{sysTitle},#{sysContent},date_add(NOW(),INTERVAL -8 hour),#{sysCreateId},0)")
    void insertSystemprocess(Systemprocess systemprocess);

    @Delete("update systemprocess set sysStatus=1 where sysid=#{sysid}")
    void deleteSystemprocessBySysId(int sysid);

}
