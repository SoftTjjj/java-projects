package com.example.cachebackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    private Boolean success;
    private String errMsg;
    private Object data;
    private Long count;

    public static Result ok(){
        return new Result(true,null,null,null);
    }

    public static Result ok(Object data){
        return new Result(true,null,data,null);
    }

    public static Result ok(List<?> data,Long total){
        return new Result(true,null,data,total);
    }

    public static Result fail(String errMsg){
        return new Result(false,errMsg,null,null);
    }
}
