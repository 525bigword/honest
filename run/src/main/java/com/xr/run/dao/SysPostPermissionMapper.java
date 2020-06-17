package com.xr.run.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xr.run.entity.SysPostPermission;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SysPostPermissionMapper extends BaseMapper<SysPostPermission> {
    @Delete("delete from sys_post_permission where pid=#{pid}")
    void delSysPostPermissionByPid(@Param("pid") Integer pid);
}
