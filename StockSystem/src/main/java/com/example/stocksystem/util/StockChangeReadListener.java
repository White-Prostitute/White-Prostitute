package com.example.stocksystem.util;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.example.stocksystem.entity.StockChange;
import com.example.stocksystem.service.StockChangeService;

import java.util.List;


public class StockChangeReadListener implements ReadListener<StockChange> {
    private static final int LIMIT_CACHE_SIZE = 100;
    List<StockChange> list = ListUtils.newArrayListWithCapacity(LIMIT_CACHE_SIZE);

    StockChangeService service;

    public StockChangeReadListener(StockChangeService service) {
        this.service = service;
    }

    @Override
    public void invoke(StockChange change, AnalysisContext analysisContext) {
        list.add(change);
        if(list.size() >= LIMIT_CACHE_SIZE){
            saveData();
            list = ListUtils.newArrayListWithCapacity(LIMIT_CACHE_SIZE);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        saveData();
    }

    public void saveData(){
        service.saveBatch(list);
    }
}
