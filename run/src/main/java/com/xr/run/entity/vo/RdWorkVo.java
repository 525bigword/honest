/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: RdWorkVo
 * Author:   TSYH
 * Date:     2020-07-08 14:39
 * Description: 责任纪实
 * History:
 * <author>     <time>       <version>     <desc>
 * 作者姓名    修改时间     版本号       描述
 */
package com.xr.run.entity.vo;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.models.auth.In;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 〈一句话功能简述〉<br> 
 * 〈责任纪实〉
 *
 * @author TSYH
 * @create 2020-07-08
 * @since 1.0.0
 */
@Data
public class RdWorkVo implements Serializable {

    private Integer id;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")//页面写入数据库时格式化
    @JSONField(format="yyyy-MM-dd HH:mm:ss")//数据库导出页面时json格式化
    private Date createTime;


    private String title;
    private String content;

    //所属类型、0是工作计划\1是工作部署\2是廉政谈话\3是主体责任
    private Integer type;

    private Integer next;

    private Integer prev;

    private String name;




}