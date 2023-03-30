package com.example.stocksystem;

import cn.hutool.json.JSONUtil;
import com.example.stocksystem.dao.StockDao;
import com.example.stocksystem.entity.StockChange;
import com.example.stocksystem.service.StockService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class StockSystemApplicationTests {

    @Autowired
    StockService service;

    @Autowired
    StockDao dao;

    @Resource
    RedisTemplate<String, String> template;

    //1360252800000   Fri Feb 08 00:00:00 CST 2013
    //1360512000000   Mon Feb 11 00:00:00 CST 2013
    //1360598400000   Tue Feb 12 00:00:00 CST 2013

    @Test
    void contextLoads() {
        List<StockChange> historyRecord = service.getHistoryRecord(2, 1);
        StockChange change = historyRecord.get(0);
        System.out.println(change.getDate());
        String s = JSONUtil.toJsonStr(change.getDate());
        System.out.println(s);
    }
}
