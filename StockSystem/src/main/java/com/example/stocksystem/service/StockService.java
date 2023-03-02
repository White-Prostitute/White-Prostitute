package com.example.stocksystem.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.stocksystem.dao.StockDao;
import com.example.stocksystem.vo.StockVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockService {

    @Autowired
    StockDao dao;

    public IPage<StockVo> getStockInfo(Integer stock_id, String stock_name, Integer pageIndex, Integer pageSize){
        IPage<StockVo> page;
        if(pageIndex != null && pageSize != null){
            page = new Page<>(pageIndex, pageSize);
        }else{
            page = new Page<>();
        }
        QueryWrapper<StockVo> wrapper = new QueryWrapper<>();
        if(stock_id != null)wrapper.eq("stock.stock_id", stock_id);
        wrapper.like("stock_name", stock_name);
        return dao.findStockInfo(page, wrapper);
    }

    public List<Float> getHighList(Integer stock_id){
        return dao.getHighList(stock_id);
    }

}
