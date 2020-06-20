package com.xr.run.service;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xr.run.entity.EducationPolitics;
import com.xr.run.entity.Systemprocess;
import lombok.Data;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


public interface SystemprocessService extends IService<Systemprocess> {
    /**
     * 查询所有制度流程+分页+条件查询
     * @param page
     * @param sysTitle
     * @param sysContent
     * @return
     */
    IPage<Systemprocess> findSystemprocess(Page page, String sysTitle, String sysContent);

    /**
     * 修改制度流程
     * @param systemprocess
     */
    void updateSystemprocessBySysId(Systemprocess systemprocess);

    /**
     * 新增制度流程
     * @param systemprocess
     */
    void insertSystemprocess(Systemprocess systemprocess);

    /**
     * 软删除制度流程 0正常 1删除
     * @param sysid
     */
    void deleteSystemprocessBySysId(int sysid);

}
