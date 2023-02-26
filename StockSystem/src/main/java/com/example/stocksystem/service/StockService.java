package com.example.stocksystem.service;

import com.example.stocksystem.dao.StockDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class StockService {

    @Autowired
    StockDao dao;

    public List<Map<String, Object>> getStockInfo(int stock_id, String stock_name){
        return dao.getStockInfoByCondition(stock_id, stock_name);
    }

}
