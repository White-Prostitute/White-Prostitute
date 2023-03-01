package com.example.stocksystem.util;

import lombok.Data;

@Data
public class Response <T>{

    //操作成功
    public static final int OK = 200;

    //未登录
    public static final int WITHOUT_LOGIN = 501;

    //相应信息
    private String msg;

    //响应码
    private int code;

    //数据
    private T data;
}
