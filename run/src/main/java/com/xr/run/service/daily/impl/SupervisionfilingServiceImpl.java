package com.xr.run.service.daily.impl;

import com.xr.run.dao.daily.SupervisionfilingMapper;
import com.xr.run.entity.daily.Supervisionfiling;
import com.xr.run.service.daily.SupervisionfilingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "SupervisionfilingService")
public class SupervisionfilingServiceImpl implements SupervisionfilingService {
    @Autowired
    private SupervisionfilingMapper supervisionfilingMapper;
    @Override
    public List<Supervisionfiling> list(Integer sStatus) {
        return supervisionfilingMapper.list(sStatus);
    }

    @Override
    public List<Supervisionfiling> findbytitle(String sPaperItems,Integer sStatus) {
        return supervisionfilingMapper.findbytitle(sPaperItems,sStatus);
    }

    @Override
    public void addsupervisionfiling(Supervisionfiling supervisionfiling) {
        supervisionfilingMapper.addsupervisionfiling(supervisionfiling);
    }

    @Override
    public void delesupervision(Integer sid) {
        supervisionfilingMapper.delesupervision(sid);
    }

    @Override
    public void tjshme(Integer sid) {
        supervisionfilingMapper.tjshme(sid);
    }

    @Override
    public void updatesupervision(Supervisionfiling supervisionfiling) {
        supervisionfilingMapper.updatesupervision(supervisionfiling);
    }

    @Override
    public List<Supervisionfiling> findbysFilingId(String sFilingId, Integer sStatus) {
        return supervisionfilingMapper.findbysFilingId(sFilingId,sStatus);
    }

    @Override
    public void tgsh(Supervisionfiling supervisionfiling) {
        supervisionfilingMapper.tgsh(supervisionfiling);
    }

    @Override
    public void signleader(Integer sid) {
        supervisionfilingMapper.signleader(sid);
    }
}
