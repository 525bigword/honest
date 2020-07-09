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

import lombok.Data;

import java.io.Serializable;

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

    private Integer pid;
    private Integer proid;
    private String pProject;
    private String pRiskPointDescription;
    private String pCreateName;
    private String proCreateName;

    private String proInfomation;
    private String proName;





}