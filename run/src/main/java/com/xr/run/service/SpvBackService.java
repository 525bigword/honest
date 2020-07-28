package com.xr.run.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xr.run.entity.EducationPolitics;
import com.xr.run.entity.SpvBack;
import lombok.Data;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;


public interface SpvBackService extends IService<SpvBack> {
    IPage<SpvBack> findSpvBack(Page page, int bid);

    void updateSpvBackBySid(SpvBack spvBack);

    void updateSpvBackFileBySid(SpvBack spvBack);

    void insertSpvBack(SpvBack spvBack);

    void deleteSpvBackBySid(int sid);

    public String findSpvBackByFile(int sid);


    void updateStatusBySid(SpvBack spvBack);
    IPage<SpvBack> findSpvBackByDid(Page page, int did);
    void updateStatusBySidT(SpvBack spvBack);



}
