package com.example.stocksystem.controller;


import com.example.stocksystem.entity.User;
import com.example.stocksystem.service.FavouriteService;
import com.example.stocksystem.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/favourite")
@CrossOrigin
public class FavouriteController {

    @Autowired
    FavouriteService service;

    @PostMapping()
    public Response<String> favourite(HttpServletRequest request, @RequestBody Map<String, Object> map){
        Response<String> response = new Response<>();
        Integer user_id = ((User)request.getSession().getAttribute("user")).getUserId();
        System.out.println("通过session获取到的id : " + user_id);
        Integer stock_id = (Integer) map.get("stock_id");
        int type = (Boolean)map.get("type")?1:0;
        if(stock_id != null){
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
