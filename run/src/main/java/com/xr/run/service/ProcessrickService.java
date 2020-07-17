package com.xr.run.service;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xr.run.entity.EducationPolitics;
import com.xr.run.entity.Processrick;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;


public interface ProcessrickService extends IService<Processrick> {
    IPage<Processrick> findProcessrickIndex(Page page, String proName);

    /**
     * 查询列表
     *
     * @return
     */
    List<Processrick> getList();

    /**
     * 新增
     *
     * @param processrick
     */
    void addProcessrick(Processrick processrick);


    /**
     * 根据ID删除
     *
     * @param proid
     */
    void deleteByProid(Integer proid);

    /**
     * 更新
     *
     * @param processrick
     */
    void updateByProid(Processrick processrick);

    /**
     * 根据条件筛选查询
     *
     * @param proName
     * @param proYear
     * @return
     */
    List<Processrick> getListBy(String proName, String proYear,String proGrade);
    Processrick findProcessrickById(Integer id);
}
