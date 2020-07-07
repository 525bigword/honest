package com.xr.run.service.tam.impl;

import com.xr.run.dao.tam.SmokestyleMapper;
import com.xr.run.entity.tam.Smokestyle;
import com.xr.run.service.tam.SmokestyleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SmokestyleServiceImpl implements SmokestyleService {
    @Autowired
    private SmokestyleMapper smokestyleMapper;
    @Override
    public List<Smokestyle> findAllSmokestyle() {
        return smokestyleMapper.findAllSmokestyle();
    }

    @Override
    public void addSmokestyle(Smokestyle smokestyle) {
smokestyleMapper.addSmokestyle(smokestyle);
    }

    @Override
    public List<Smokestyle> findSmokestyle(String title) {
        return smokestyleMapper.findSmokestyle(title);
    }

    @Override
    public void delSmokestyle(Integer smoid) {
smokestyleMapper.delSmokestyle(smoid);
    }

    @Override
    public void gxSmokestyle(Smokestyle smokestyle) {
        smokestyleMapper.gxSmokestyle(smokestyle);
    }
}
