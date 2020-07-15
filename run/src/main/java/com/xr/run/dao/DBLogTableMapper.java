package com.xr.run.dao;

import com.xr.run.entity.SysLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface DBLogTableMapper {
    //查询是否存在当前表名
    @Select("show TABLES like #{tableName}")
    String findTableNameCount(@Param("tableName")String tableName);
    //根据表名创建日志表
    @Update("CREATE TABLE ${tableName} (" +
            "  `id` int(11) NOT NULL AUTO_INCREMENT," +
            "  `sid` int(50) DEFAULT NULL," +
            "  `url` varchar(100) DEFAULT NULL," +
            "  `method` varchar(20) DEFAULT NULL," +
            "  `ip` varchar(20) DEFAULT NULL," +
            "  `data` varchar(5000) DEFAULT NULL," +
            "  `terminal` varchar(10) DEFAULT NULL," +
            "  `type` varchar(30) DEFAULT NULL," +
            "  `time` datetime DEFAULT NULL," +
            "  PRIMARY KEY (`id`)" +
            ")ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;")
    void createNewTable(@Param("tableName")String tableName);
    //根绝表名插入信息
    @Insert("insert into ${tableName} value(null,#{log.sid},#{log.url},#{log.method},#{log.ip},#{log.data},#{log.terminal},#{log.type},#{log.time})")
    void insertByTableNameSysLog(@Param("tableName")String tableName, @Param("log")SysLog sysLog);
}
