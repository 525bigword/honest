package com.xr.run.util;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class ResponseResult {
    private Integer code=100;
    private Map<String,Object> info=new HashMap<>();
    private String msg;
}
