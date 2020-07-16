package com.xr.run.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xr.run.dao.RdHonestConversationMapper;
import com.xr.run.entity.RdHonestConversation;
import com.xr.run.service.RdHonestConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RdHonestConversationServiceImpl extends ServiceImpl<RdHonestConversationMapper,RdHonestConversation> implements RdHonestConversationService {

    @Autowired
    private RdHonestConversationMapper rdHonestConversationMapper;
    @Override
    public RdHonestConversation findRdHonestConversationById(Integer id) {
        return rdHonestConversationMapper.findRdHonestConversationById(id);
    }

    @Override
    public IPage<RdHonestConversation> findRdHonestConversationIndex(Page page, String title) {
        return rdHonestConversationMapper.findRdHonestConversationIndex(page,title);
    }

    @Override
    public List<RdHonestConversation> findRdHonestConversationAll(String title) {
        return rdHonestConversationMapper.findRdHonestConversationAll(title);
    }
}
