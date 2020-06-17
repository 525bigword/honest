package com.xr.run.service;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xr.run.entity.Creditrating;
import com.xr.run.entity.Datacollection;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public interface DatacollectionService extends IService<Datacollection> {
    /**
     * 查询所有资料锦集+分页+模糊查询
     * @param page
     * @param dTitle
     * @return
     */
    IPage<Datacollection> findDataConllection(Page page, String dTitle);

    /**
     * 根据did修改资料锦集
     * @param datacollection
     */
    void updateDataConllectionByDid(Datacollection datacollection);

    /**
     * 添加资料锦集
     * @param datacollection
     */
    void insertDataConllection(Datacollection datacollection);

    /**
     * 根据did删除资料锦集
     * @param did
     */
    void deleteDataConllectionByDid(int did);
}
