package com.xr.run;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.io.File;

@SpringBootTest
class RunApplicationTests {
    @Autowired
    DataSource dataSource;//Druid数据源

    @Test
    void contextLoads() {
        System.out.println(dataSource.getClass());
    }
    @Test
    public void test(){
        try{
        File file=new File("C://File_rec/tmp/"+"/2020/06/18/da124d91-954d-43e4-9750-44843fce99ba.docx");
        if(file.exists()){
            file.delete();
        }}catch (Exception e){
            e.printStackTrace();
        }
    }

}
