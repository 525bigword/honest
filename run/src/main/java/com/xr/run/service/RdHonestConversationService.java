package com.xr.run.service;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xr.run.entity.EducationPolitics;
import com.xr.run.entity.RdHonestConversation;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;


public interface RdHonestConversationService extends IService<RdHonestConversation> {

    RdHonestConversation findRdHonestConversationById(Integer parseInt);

    IPage<RdHonestConversation> findRdHonestConversationIndex(Page page, String title);

    List<RdHonestConversation> findRdHonestConversationAll(String title);
}
