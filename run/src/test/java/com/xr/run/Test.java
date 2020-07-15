/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: Test
 * Author:   TSYH
 * Date:     2020-07-12 0:06
 * Description:
 * History:
 * <author>     <time>       <version>     <desc>
 * 作者姓名    修改时间     版本号       描述
 */
package com.xr.run;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xr.run.dao.SystemMessageMapper;
import com.xr.run.entity.SystemMessage;
import com.xr.run.service.SystemMessageService;
import com.xr.run.service.impl.HomePageSeviceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author TSYH
 * @create 2020-07-12
 * @since 1.0.0
 */
@SpringBootTest
public class Test {

    @Autowired
    private HomePageSeviceImpl homePageSevice;
    @Autowired
    private SystemMessageMapper systemMessageMapper;

    @org.junit.jupiter.api.Test
    public void htmlDestTest(){
        homePageSevice.a();
    }
    @org.junit.jupiter.api.Test
    public void test(){
        IPage<SystemMessage> systemMessage = systemMessageMapper.findSystemMessage(new Page(1, 5), "", "");
        System.out.println(systemMessage.getSize());
    }
}