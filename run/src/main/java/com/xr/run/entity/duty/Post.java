package com.xr.run.entity.duty;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class Post {
    private Integer pid;
    private String pname;
    private Integer mid;
    private String message;
    private String createtime;
    private Integer createid;
    private Integer staus;
}
