package com.xr.run.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xr.run.entity.SystemMessage;

public interface SystemMessageService extends IService<SystemMessage> {
    /**
     * 查询所有的通知公告 + 模糊查新 +分页
     * @param page
     * @param articletitle
     * @return
     */
    IPage<SystemMessage> findSystemMessage(Page page, String articletitle, String describe);

    /**
     * 修改通知公告
     * @param systemMessage
     */
    void updateSystemMessageByAid(SystemMessage systemMessage);

    /**
     * 修改通知公告不改文件
     * @param systemMessage
     */
    void updateSystemMessageByAidNoFile(SystemMessage systemMessage);

    /**
     * 新增通知公告
     * @param systemMessage
     */
    void insertSystemMessage(SystemMessage systemMessage);

    /**
     * 删除通知公告
     * @param aid
     */
    void deleteSystemMessageByAid(int aid);

    /**
     * 查找单个文件路径
     * @param aid
     * @return
     */
    String findSystemMessageByFile(int aid);


    SystemMessage findSystemMessageByAid(int aid);
}
