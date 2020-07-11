package com.xr.run.service.duty.impl;

import com.xr.run.dao.duty.HonestConversationMapper;
import com.xr.run.entity.SysPpost;
import com.xr.run.entity.SysStaff;
import com.xr.run.entity.SysMechanism;
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
    public void passauditHonestConversation(Integer id,Integer status,String auditresult) {
honestConversationMapper.passauditHonestConversation(id,status,auditresult);
    }

    @Override
    public void delHonestConversationt(Integer id) {
honestConversationMapper.delHonestConversationt(id);
    }

    @Override
    public List<SysStaff> findallduty(Integer id) {
        return honestConversationMapper.findallduty(id);
    }

    @Override
    public List<SysPpost> findalldept(Integer id) {
        return honestConversationMapper.findalldept(id);
    }

    @Override
    public List<SysStaff> findallperson(Integer ppid) {
        return honestConversationMapper.findallperson(ppid);
    }

    @Override
    public List<SysMechanism> findallunit(Integer parent) {
        return honestConversationMapper.findallunit(parent);
    }
}
