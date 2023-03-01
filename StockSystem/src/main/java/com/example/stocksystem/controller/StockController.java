package com.example.stocksystem.controller;

import com.example.stocksystem.service.StockService;
import com.example.stocksystem.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/stock")
public class StockController {

    @Autowired
    StockService service;

    @GetMapping()
    public Response<List<Map<String, Object>>> getAllStockInfo(String stock_id, String stock_name){
        if(stock_id == null||stock_id.length()==0){
            stock_id = "-1";
        }
        List<Map<String, Object>> list = service.getStockInfo(Integer.parseInt(stock_id), stock_name);
        if(list == null){
            list = new ArrayList<>();
        }
        Response<List<Map<String, Object>>> response = new Response<>();
        response.setCode(Response.OK);
        response.setMsg("获取数据成功");
        response.setData(list);
        return response;
    }


}
