/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: RiskVo
 * Author:   TSYH
 * Date:     2020-07-09 0:36
 * Description:
 * History:
 * <author>     <time>       <version>     <desc>
 * 作者姓名    修改时间     版本号       描述
 */
package com.xr.run.entity.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author TSYH
 * @create 2020-07-09
 * @since 1.0.0
 */
@Data
public class RiskVo implements Serializable {

    private Integer id;
    private String project; //名称
    private String description; //描述

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")//页面写入数据库时格式化
    @JSONField(format="yyyy-MM-dd HH:mm:ss")//数据库导出页面时json格式化
    private Date createTime; //时间
    private String cname;//姓名








}