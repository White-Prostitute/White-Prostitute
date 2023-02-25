package com.example.stocksystem.controller;

import com.example.stocksystem.service.StockService;
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
    public List<Map<String, String>> getAllStockInfo(int stock_id, String stock_name){
        List<Map<String, String>> list = service.getStockInfo(stock_id, stock_name);
        if(list == null){
            list = new ArrayList<>();
        }
        return list;
    }


}
