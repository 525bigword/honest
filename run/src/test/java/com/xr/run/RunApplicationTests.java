package com.xr.run;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;

@SpringBootTest
class RunApplicationTests {
    @Autowired
    DataSource dataSource;//Druid数据源

    @Test
    void contextLoads() {
        System.out.println(dataSource.getClass());
    }

}
