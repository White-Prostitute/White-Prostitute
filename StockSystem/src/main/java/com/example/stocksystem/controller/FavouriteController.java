package com.example.stocksystem.controller;


import com.example.stocksystem.service.FavouriteService;
import com.example.stocksystem.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/favourite")
public class FavouriteController {

    @Autowired
    FavouriteService service;

    @GetMapping()
    public Response<String> favourite(Integer user_id, Integer stock_id, Integer type){
        Response<String> response = new Response<>();
        if(type==1||type==0){
            service.favourite(user_id, stock_id, type);
            String msg = type == 1?"收藏成功":"取消收藏成功";
            response.setMsg(msg);
            response.setCode(Response.OK);
            response.setData("success");
            return response;
        }
        response.setCode(Response.PARA_MISTAKE);
        response.setMsg("type参数有误");
        response.setData("error");
        return response;
    }

    @GetMapping("/list")
    public Response<List<Integer>> list(Integer user_id){
        Response<List<Integer>> response = new Response<>();
        if(user_id == null){
            response.setCode(Response.PARA_MISTAKE);
            response.setMsg("user_id为空");
            response.setData(new ArrayList<>());
        }else{
            List<Integer> list = service.list(user_id);
            response.setCode(Response.OK);
            response.setMsg("获取收藏列表成功");
            response.setData(list);
        }
        return response;
    }

}
