package com.xr.run.service;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xr.run.entity.Creditrating;
import com.xr.run.entity.Datacollection;
import lombok.Data;
import org.apache.ibatis.annotations.Update;
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

    /**
     * 查找资料锦集的原路径
     * @param did
     * @return
     */
    public String findDatacollectionByFile(int did);

    /**
     * 是否要删除文件
     * @param datacollection
     */
    void updateDataConllectionByFile(Datacollection datacollection);

    /**
     * 修改资料锦集的视频
     * @param datacollection
     */
    void updateDataConllectionByVideo(Datacollection datacollection);

    /**
     * 修改锦集视频和文档
     * @param datacollection
     */
    void updateDataConllectionByVideoAndFile(Datacollection datacollection);

    /**
     * 查询视频地址
     * @param did
     * @return
     */
    String findDatacollectionBydVideo(int did);

    /**
     * 根据id查询
     * @param did
     * @return
     */
    Datacollection findDatacollectionById(int did);

//    /**
//     * 用于前台资料锦鸡
//     * @param page
//     * @param title
//     * @return
//     */
//    IPage<Datacollection> qtFindDatacollectionPage(Page page,String title);
}
