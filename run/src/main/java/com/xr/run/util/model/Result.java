package com.xr.run.util.model;

import lombok.Data;

@Data
public class Result {
    private Integer code;
    private String message;
    private Object data;
    public  Result(){}
    private Result(Integer code,String message,Object data){}
    public Result success(Object data){
        return new Result(200,"",data);
    }
    public Result error(String message){
        return new Result(201,message,"");
    }
    public Result error(String message,Object data){
        return new Result(201,message,data);
    }
    public Result loign(){
        return new Result(211,"未登录","");
    }
}
