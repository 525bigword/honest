package com.xr.run.service.duty.impl;

import com.xr.run.dao.duty.EntityResponsibilityMapper;
import com.xr.run.entity.duty.EntityResponsibility;
import com.xr.run.service.duty.EntityResponsibilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EntityResponsibilityServiceImpl implements EntityResponsibilityService {
    @Autowired
    private EntityResponsibilityMapper entityResponsibilityMapper;
    @Override
    public List<EntityResponsibility> list() {
        return entityResponsibilityMapper.list();
    }

    @Override
    public List<EntityResponsibility> findbytitle(String title) {
        return entityResponsibilityMapper.findbytitle(title);
    }

    @Override
    public Integer addEntityResponsibility(EntityResponsibility entityResponsibility) {
entityResponsibilityMapper.addEntityResponsibility(entityResponsibility);
return entityResponsibility.getId();
    }

    @Override
    public void updateEntityResponsibility(EntityResponsibility entityResponsibility) {
entityResponsibilityMapper.updateEntityResponsibility(entityResponsibility);
    }

    @Override
    public void del(Integer id) {
entityResponsibilityMapper.del(id);
    }

    @Override
    public void subaudit(Integer id) {
entityResponsibilityMapper.subaudit(id);
    }

    @Override
    public void passaudit(EntityResponsibility entityResponsibility) {
entityResponsibilityMapper.passaudit(entityResponsibility);
    }
}
