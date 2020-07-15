/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: CultureVo
 * Author:   TSYH
 * Date:     2020-07-13 11:32
 * Description: 廉政文化
 * History:
 * <author>     <time>       <version>     <desc>
 * 作者姓名    修改时间     版本号       描述
 */
package com.xr.run.entity.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 〈一句话功能简述〉<br> 
 * 〈廉政文化〉
 *
 * @author TSYH
 * @create 2020-07-13
 * @since 1.0.0
 */
@Data
public class CultureVo {

    private Integer id;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")//页面写入数据库时格式化
    @JSONField(format="yyyy-MM-dd HH:mm:ss")//数据库导出页面时json格式化
    private Date createTime;


    private String title;
    private String content;

    //所属类型、0是资料集锦\1是清风文苑
    private Integer type;


    private String cname;
    private String url;

}