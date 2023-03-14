package com.example.stocksystem;

import com.example.stocksystem.dao.StockDao;
import com.example.stocksystem.service.StockService;
import com.example.stocksystem.vo.StockVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class StockSystemApplicationTests {

    @Autowired
    StockService service;

    @Autowired
    StockDao dao;

    @Test
    void contextLoads() {
    }

}
