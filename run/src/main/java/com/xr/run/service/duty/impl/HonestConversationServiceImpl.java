package com.xr.run.service.duty.impl;

import com.xr.run.dao.duty.HonestConversationMapper;
import com.xr.run.entity.duty.HonestConversation;
import com.xr.run.service.duty.HonestConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HonestConversationServiceImpl implements HonestConversationService {
    @Autowired
    private HonestConversationMapper honestConversationMapper;

    @Override
    public List<HonestConversation> list() {
        return honestConversationMapper.list();
    }

    @Override
    public List<HonestConversation> listBytitle(String title) {
        return honestConversationMapper.listBytitle(title);
    }

    @Override
    public void addHonestConversation(HonestConversation honestConversation) {
honestConversationMapper.addHonestConversation(honestConversation);
    }

    @Override
    public void updateHonestConversation(HonestConversation honestConversation) {
honestConversationMapper.updateHonestConversation(honestConversation);
    }

    @Override
    public void subauditHonestConversation(Integer id) {
honestConversationMapper.subauditHonestConversation(id);
    }

    @Override
    public void passauditHonestConversation(Integer id) {
honestConversationMapper.passauditHonestConversation(id);
    }

    @Override
    public void delHonestConversationt(Integer id) {
honestConversationMapper.delHonestConversationt(id);
    }
}
