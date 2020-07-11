package com.xr.run.service.daily.impl;

import com.xr.run.dao.daily.LetterReportMapper;
import com.xr.run.entity.daily.DeptUnit;
import com.xr.run.entity.daily.LetterReport;
import com.xr.run.service.daily.LetterReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LetterReporyServiceImpl implements LetterReportService {
    @Autowired
    private LetterReportMapper letterReportMapper;

    @Override
    public String xfbh() {
        return letterReportMapper.xfbh();
    }

    @Override
    public List<LetterReport> list(LetterReport letterReport) {
        return letterReportMapper.list(letterReport);
    }

    @Override
    public void addletter(LetterReport letterReport) {
        letterReportMapper.addletter(letterReport);
    }

    @Override
    public void deletter(Integer lid) {
        letterReportMapper.deletter(lid);
    }

    @Override
    public List<LetterReport> findbyName(LetterReport letterReport) {
        return letterReportMapper.findbyName(letterReport);
    }

    @Override
    public void shbc(LetterReport letterReport) {
        letterReportMapper.shbc(letterReport);
    }

    @Override
    public List<LetterReport> listLetterByid(Integer lid) {
        return letterReportMapper.listLetterByid(lid);
    }

    @Override
    public void tjshme(Integer lid) {
        letterReportMapper.tjshme(lid);
    }

    @Override
    public void turndept(Integer lid) {
        letterReportMapper.turndept(lid);
    }

    @Override
    public void kezhangbc(LetterReport letterReport) {
        letterReportMapper.kezhangbc(letterReport);
    }

    @Override
    public void deptbc(LetterReport letterReport) {
letterReportMapper.deptbc(letterReport);
    }

    @Override
    public void bjbcmethod(LetterReport letterReport) {
        letterReportMapper.bjbcmethod(letterReport);
    }

    @Override
    public void leaderbc(LetterReport letterReport) {
        letterReportMapper.leaderbc(letterReport);
    }

    @Override
    public void zzbc(LetterReport letterReport) {
letterReportMapper.zzbc(letterReport);
    }

    @Override
    public DeptUnit finddp(String nickname) {
        return letterReportMapper.finddp(nickname);
    }
}
