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
    @Select("select sid,BackType,BackTitle,BackContent,bid,BackAccessory,NewTime,bCreateId,did,`status`,backAccessoryName from spv_back  where backType like CONCAT('%',#{backType},'%') order by sid desc")
    @Results({
            @Result(column = "bid",property = "sysMechanism",
            one = @One(select = "com.xr.run.dao.SysMechanismMapper.findSysMechanismMid",fetchType = FetchType.DEFAULT)),
            @Result(column = "bCreateId",property = "sysStaff",
                    one = @One(select = "com.xr.run.dao.SysStaffMapper.findSysStaffById",fetchType = FetchType.DEFAULT)),
            @Result(column = "did",property = "gettop",
            one = @One(select = "com.xr.run.dao.SpvDutyMapper.getSpvDuty",fetchType = FetchType.DEFAULT)),
            @Result(column = "did",property = "tongzhi",
            one = @One(select = "com.xr.run.dao.SpvDutyMapper.getTongzhi",fetchType = FetchType.DEFAULT))
    })
    IPage<SpvBack> findSpvBack(Page page, String backType);

    @Update("update spv_back set BackTitle=#{backTitle},BackContent=#{backContent} where sid=#{sid}")
    void updateSpvBackBySid(SpvBack spvBack);

    @Update("update spv_back set BackTitle=#{backTitle},BackContent=#{backContent},BackAccessory=#{backAccessory},backAccessoryName=#{backAccessoryName} where sid=#{sid}")
    void updateSpvBackFileBySid(SpvBack spvBack);

    @Insert("insert into spv_back(sid,BackType,bid,NewTime,bCreateId,did,`status`) VALUES (NULL,#{backType},#{bid},NOW(),#{bCreateId},#{did},#{status})")
    void insertSpvBack(SpvBack spvBack);

    @Delete("delete from  spv_back where sid=#{sid}")
    void deleteSpvBackBySid(int sid);

    @Select("select BackAccessory from spv_back where sid=#{sid}")
    public String findSpvBackByFile(int sid);


    @Update("update spv_back set `status`=#{status} where sid=#{sid}")
    void updateStatusBySid(SpvBack spvBack);

    @Select("select sid,BackType,BackTitle,BackContent,bid,BackAccessory,NewTime,bCreateId,did,`status`,backAccessoryName from spv_back  where did=#{did} order by sid desc")
    @Results({
            @Result(column = "bid",property = "sysMechanism",
                    one = @One(select = "com.xr.run.dao.SysMechanismMapper.findSysMechanismMid",fetchType = FetchType.DEFAULT)),
            @Result(column = "bCreateId",property = "sysStaff",
                    one = @One(select = "com.xr.run.dao.SysStaffMapper.findSysStaffById",fetchType = FetchType.DEFAULT)),
            @Result(column = "did",property = "gettop",
                    one = @One(select = "com.xr.run.dao.SpvDutyMapper.getSpvDuty",fetchType = FetchType.DEFAULT))
    })
    IPage<SpvBack> findSpvBackByDid(Page page, int did);

}
