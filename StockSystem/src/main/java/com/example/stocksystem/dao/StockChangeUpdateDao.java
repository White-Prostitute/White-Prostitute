package com.example.stocksystem.dao;


import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.stocksystem.entity.StockChange;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@TableName("stock_change_update")
public interface StockChangeUpdateDao extends BaseMapper<StockChange> {
}
