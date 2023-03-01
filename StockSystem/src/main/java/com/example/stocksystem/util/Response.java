package com.example.stocksystem.util;

import lombok.Data;

@Data
public class Response <T>{

    //操作成功
    public static final int OK = 200;

    //未登录
    public static final int WITHOUT_LOGIN = 501;

    //参数有误
    public static final int PARA_MISTAKE = 502;

    //服务器捕获异常
    public static final int SERVER_EXCEPTION = 503;

    //相应信息
    private String msg;

    //响应码
    private int code;

    //数据
    private T data;
}
