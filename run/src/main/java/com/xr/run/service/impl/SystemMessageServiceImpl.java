package com.xr.run.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xr.run.dao.SystemMessageMapper;
import com.xr.run.entity.SystemMessage;
import com.xr.run.service.SystemMessageService;
import org.springframework.stereotype.Service;

@Service
public class SystemMessageServiceImpl extends ServiceImpl<SystemMessageMapper, SystemMessage> implements SystemMessageService {
    @Override
    public IPage<SystemMessage> findSystemMessage(Page page, String articletitle, String describe) {
        return baseMapper.findSystemMessage(page,articletitle,describe);
    }

    @Override
    public void updateSystemMessageByAid(SystemMessage systemMessage) {
        baseMapper.updateSystemMessageByAid(systemMessage);
    }

    @Override
    public void updateSystemMessageByAidNoFile(SystemMessage systemMessage) {
        baseMapper.updateSystemMessageByAidNoFile(systemMessage);
    }

    @Override
    public void insertSystemMessage(SystemMessage systemMessage) {
        baseMapper.insertSystemMessage(systemMessage);
    }

    @Override
    public void deleteSystemMessageByAid(int aid) {
        baseMapper.deleteSystemMessageByAid(aid);
    }

    @Override
    public String findSystemMessageByFile(int aid) {
        return baseMapper.findSystemMessageByFile(aid);
    }

    @Override
    public SystemMessage findSystemMessageByAid(int aid) {
        return baseMapper.findSystemMessageByAid(aid);
    }
}
