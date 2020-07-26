package com.xr.run.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xr.run.entity.SpvBack;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpvBackMapper extends BaseMapper<SpvBack> {
    @Select("select sid,BackType,BackTitle,BackContent,bid,BackAccessory,NewTime,bCreateId,did,`status`,backAccessoryName,bPdf from spv_back  where bid=#{bid} and `status`<>-1 order by sid desc")
    @Results({
            @Result(column = "bid",property = "sysMechanism",
            one = @One(select = "com.xr.run.dao.SysMechanismMapper.findSysMechanismMid",fetchType = FetchType.DEFAULT)),
            @Result(column = "bCreateId",property = "sysStaff",
                    one = @One(select = "com.xr.run.dao.SysStaffMapper.findSysStaffById",fetchType = FetchType.DEFAULT)),
            @Result(column = "did",property = "gettop",
            one = @One(select = "com.xr.run.dao.SpvDutyMapper.getSpvDuty",fetchType = FetchType.DEFAULT)),
            @Result(column = "did",property = "gfile",
                    one = @One(select = "com.xr.run.dao.SpvDutyMapper.findSpvDutyByFile",fetchType = FetchType.DEFAULT)),
            @Result(column = "did",property = "tongzhi",
            one = @One(select = "com.xr.run.dao.SpvDutyMapper.getTongzhi",fetchType = FetchType.DEFAULT))
    })
    IPage<SpvBack> findSpvBack(Page page, int bid);

    @Update("update spv_back set BackTitle=#{backTitle},BackContent=#{backContent} where sid=#{sid}")
    void updateSpvBackBySid(SpvBack spvBack);

    @Update("update spv_back set BackTitle=#{backTitle},BackContent=#{backContent},BackAccessory=#{backAccessory},backAccessoryName=#{backAccessoryName},bPdf=#{bPdf} where sid=#{sid}")
    void updateSpvBackFileBySid(SpvBack spvBack);

    @Insert("insert into spv_back(sid,BackType,bid,NewTime,bCreateId,did,`status`) VALUES (NULL,#{backType},#{bid},date_add(NOW(),INTERVAL -8 hour),#{bCreateId},#{did},#{status})")
    void insertSpvBack(SpvBack spvBack);

    @Update("update spv_back set `status`=-1  where sid=#{sid}")
    void deleteSpvBackBySid(int sid);

    @Select("select BackAccessory from spv_back where sid=#{sid}")
    public String findSpvBackByFile(int sid);


    @Update("update spv_back set `status`=#{status} where sid=#{sid}")
    void updateStatusBySid(SpvBack spvBack);

    @Select("select sid,BackType,BackTitle,BackContent,bid,BackAccessory,NewTime,bCreateId,did,`status`,backAccessoryName,bPdf from spv_back  where did=#{did} order by sid desc")
    @Results({
            @Result(column = "bid",property = "sysMechanism",
                    one = @One(select = "com.xr.run.dao.SysMechanismMapper.findSysMechanismMid",fetchType = FetchType.DEFAULT)),
            @Result(column = "bCreateId",property = "sysStaff",
                    one = @One(select = "com.xr.run.dao.SysStaffMapper.findSysStaffById",fetchType = FetchType.DEFAULT)),
            @Result(column = "did",property = "gettop",
                    one = @One(select = "com.xr.run.dao.SpvDutyMapper.getSpvDuty",fetchType = FetchType.DEFAULT)),
            @Result(column = "did",property = "gfile",
                    one = @One(select = "com.xr.run.dao.SpvDutyMapper.findSpvDutyByFile",fetchType = FetchType.DEFAULT)),
            @Result(column = "did",property = "tongzhi",
                    one = @One(select = "com.xr.run.dao.SpvDutyMapper.getTongzhi",fetchType = FetchType.DEFAULT))
    })
    IPage<SpvBack> findSpvBackByDid(Page page, int did);
    @Select("SELECT count(sid) FROM (SELECT sid,bCreateId,`status` FROM spv_back WHERE `status` <> 4 and `status` <>-1) spv_back WHERE bCreateId = #{sid}")
    Integer findspvBackByWstatusToCount(@Param("sid")Integer sid);
}
