/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: RiskController
 * Author:   TSYH
 * Date:     2020-07-14 20:19
 * Description:
 * History:
 * <author>     <time>       <version>     <desc>
 * 作者姓名    修改时间     版本号       描述
 */
package com.xr.run.controller.frontPage;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xr.run.dao.PostriskcombingMapper;
import com.xr.run.dao.ProcessrickMapper;
import com.xr.run.entity.Postriskcombing;
import com.xr.run.entity.Processrick;
import com.xr.run.service.PostriskcombingService;
import com.xr.run.service.ProcessrickService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author TSYH
 * @create 2020-07-14
 * @since 1.0.0
 */
@RestController
@RequestMapping("/qt/risk")
public class RiskController {

    //岗位风险
    @Autowired
    private PostriskcombingService postriskcombingService;

    //流程风险
    @Autowired
    private ProcessrickService processrickService;

    @RequestMapping("/getRiskAll")
    public String getRiskAll(String prProject,Integer pageNo,Integer pageSize){
        //岗位风险，type=1
        IPage<Postriskcombing> postriskCombingIndex = postriskcombingService.findPostriskCombingIndex(new Page(), prProject);
        //流程风险，type=0
        IPage<Processrick> processrickIndex = processrickService.findProcessrickIndex(new Page(), prProject);

        return null;
    }

















}