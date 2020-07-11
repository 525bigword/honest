package com.xr.run.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xr.run.entity.Wind;

public interface WindService extends IService<Wind> {
    /**
     * 查询清风文苑 包含模糊查+份页
     * @param page
     * @param wTitle
     * @return
     */
    IPage<Wind> findWind(Page page, String wTitle);

    /**
     * 根据wid修改清风文苑
     * @param wind
     */
    void updateWindByWid(Wind wind);

    /**
     * 新增清风文苑
     * @param wind
     */
    void insertWind(Wind wind);

    /**
     * 根据wid删除清风文苑
     * @param wid
     */
    void deleteWindByWid(int wid);

    /**
     * 查找单个清风文苑
     * @param wid
     * @return
     */
    Wind findWindByWid(int wid);


}
