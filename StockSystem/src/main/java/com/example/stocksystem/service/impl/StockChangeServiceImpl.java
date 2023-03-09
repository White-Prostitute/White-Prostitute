package com.example.stocksystem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.stocksystem.dao.StockChangeUpdateDao;
import com.example.stocksystem.entity.StockChange;
import com.example.stocksystem.service.StockChangeService;
import org.springframework.stereotype.Service;

@Service
public class StockChangeServiceImpl extends ServiceImpl<StockChangeUpdateDao, StockChange> implements StockChangeService {
}
