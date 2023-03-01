package com.example.stocksystem.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.stocksystem.vo.StockVo;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface StockDao extends BaseMapper {

    @MapKey("list")
    List<Map<String, Object>> getStockInfoByCondition(@Param("stock_id") int stock_id,
                                                      @Param("stock_name") String stock_name,
                                                      @Param("page_index") int pageIndex,
                                                      @Param("page_size") int pageSize);


    @Select("select * from stock join stock_change on stock.stock_id = stock_change.stock_id "+
        "${ew.customSqlSegment}"
    )
    IPage<StockVo> findStockInfo(IPage<StockVo> page, @Param("ew") Wrapper wrapper);
}
