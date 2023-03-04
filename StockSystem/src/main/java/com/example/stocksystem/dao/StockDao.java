package com.example.stocksystem.dao;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.stocksystem.entity.Stock;
import com.example.stocksystem.entity.StockChange;
import com.example.stocksystem.vo.StockVo;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface StockDao extends BaseMapper<StockChange> {

    @MapKey("list")
    List<Map<String, Object>> getStockInfoByCondition(@Param("stock_id") int stock_id,
                                                      @Param("stock_name") String stock_name,
                                                      @Param("page_index") int pageIndex,
                                                      @Param("page_size") int pageSize);


    @Select("select * from stock join stock_change on stock.stock_id = stock_change.stock_id "+
        "${ew.customSqlSegment}"
    )
    IPage<StockVo> findStockInfo(IPage<StockVo> page, @Param("ew") Wrapper wrapper);

    @Select("select price_high from stock_change where stock_id = #{stock_id}")
    List<Float> getHighList(int stock_id);

    @Select("select * from stock join stock_change on stock.stock_id = stock_change.stock_id " +
    "${ew.customSqlSegment}")
    List<StockVo> getOneStockInfo(@Param("ew") Wrapper<StockVo> wrapper);

    @Insert("insert into stock_change values(#{stockId}, #{date}, #{priceHigh}, #{priceLow}, #{priceOpen}, " +
            "#{priceClose}, #{volume})")
    int addOneStockInfo(StockChange change);
}
