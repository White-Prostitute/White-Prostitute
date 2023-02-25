package com.example.stocksystem.service;

import com.example.stocksystem.dao.StockDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockService {

    @Autowired
    StockDao dao;

}
