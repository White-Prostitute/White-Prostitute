package com.example.stocksystem.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.stocksystem.dao.StockDao;
import com.example.stocksystem.entity.StockChange;
import com.example.stocksystem.vo.StockVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class StockService {

    @Autowired
    StockDao dao;

    public IPage<StockVo> getStockInfo(Integer stock_id, String stock_name,
                                       Integer pageIndex, Integer pageSize, String date){
        IPage<StockVo> page;
        if(pageIndex != null && pageSize != null){
            page = new Page<>(pageIndex, pageSize);
        }else{
            page = new Page<>();
        }
        QueryWrapper<StockVo> wrapper = new QueryWrapper<>();
        if(stock_id!=null)wrapper.eq("stock.stock_id", stock_id);
        if(stock_name!=null)wrapper.like("stock_name", stock_name);
        if(date!=null)wrapper.eq("date", date);
        return dao.findStockInfo(page, wrapper);
    }

    public List<Float> getHighList(Integer stock_id){
        return dao.getHighList(stock_id);
    }

    public List<StockVo> getOneStockInfo(Integer stock_id){
        QueryWrapper<StockVo> wrapper = new QueryWrapper<>();
        wrapper.eq("stock.stock_id", stock_id).orderByAsc("date").last("limit 1");
        return dao.getOneStockInfo(wrapper);
    }

    // 通过 stock_id 在 update 表中查找数据
    public StockChange getUpdateRecord(Integer stockId){
        return dao.getUpdateRecord(stockId);
    }

    public boolean addStockInfo(StockChange change){
        try{
            dao.addOneStockInfo(change);

            // 获取 change 的 stock_id
            int newId = change.getStockId();
            // 调用函数查找对应的最新记录
            StockChange update = getUpdateRecord(newId);

            // 定义 Calendar 对象: rawCalendar 指的是添加的股票变化的时间， updCalendar 指的是原来的股票变化的最新时间
            Calendar rawCalendar = Calendar.getInstance();
            Calendar updCalendar = Calendar.getInstance();
            // 这里获取一个系统时间为之后的合理性检验做准备
            Calendar realCalendar = Calendar.getInstance();
            // 提取 change 和 update 的 date 字段
            Date rawDate = change.getDate();
            Date updDate = update.getDate();
            // 将 Date 类型转换为 Calendar 类型
            rawCalendar.setTime(rawDate);
            updCalendar.setTime(updDate);

            // 判断时间早晚
            // 首先获取年份、月份和日期
            int rawYear = rawCalendar.get(Calendar.YEAR);
            int updYear = updCalendar.get(Calendar.YEAR);
            int realYear = realCalendar.get(Calendar.YEAR);
            int rawMonth = rawCalendar.get(Calendar.MONTH) + 1;
            int updMonth = updCalendar.get(Calendar.MONTH) + 1;
            int realMonth = realCalendar.get(Calendar.MONTH) + 1;
            int rawDay = rawCalendar.get(Calendar.DATE);
            int updDay = updCalendar.get(Calendar.DATE);
            int realDay = realCalendar.get(Calendar.DATE);
            // 判断年份
            if (rawYear > updYear) {
                if (rawYear < realYear) {
                    // 数据最新且符合常理性判断，更新 update 表
                    dao.updateStockInfo(change);
                } else if (rawYear == realYear) {
                    // 每一级时间数据相同就进行下一级的比较
                    if (rawMonth < realMonth) {
                        dao.updateStockInfo(change);
                    } else if (rawMonth == realMonth) {
                        if (rawDay <= realDay) {
                            dao.updateStockInfo(change);
                        } else if (rawDay > realDay) {
                            // 不符合常理性判断，该分支可删除
                            System.out.println("添加的数据日期超出限制！");
                        }
                    } else if (rawMonth > realMonth) {
                        // 不符合常理性判断，该分支可删除
                        System.out.println("添加的数据月份超出限制！");
                    }
                } else if (rawYear > realYear) {
                    // 不符合常理性判断，该分支可删除
                    System.out.println("添加的数据年份超出限制！");
                }
            } else if (rawYear == updYear) {
                // 每一级时间数据相同就进行下一级的比较
                if (rawMonth > updMonth){
                    if (rawMonth < realMonth) {
                        dao.updateStockInfo(change);
                    } else if (rawMonth == realMonth) {
                        if (rawDay <= realDay) {
                            dao.updateStockInfo(change);
                        } else if (rawDay > realDay) {
                            // 不符合常理性判断，该分支可删除
                            System.out.println("添加的数据日期超出限制！");
                        }
                    } else if (rawMonth > realMonth) {
                        // 不符合常理性判断，该分支可删除
                        System.out.println("添加的数据月份超出限制！");
                    }
                } else if (rawMonth == updMonth) {
                    if (rawDay == realDay) {
                        dao.updateStockInfo(change);
                    } else if (rawDay > realDay) {
                        // 不符合常理性判断，该分支可删除
                        System.out.println("添加的数据月份超出限制！");
                    } else if (rawDay < realDay) {
                        // 该分支可被删除
                        System.out.println("添加的不是最新数据，不用更新 update 数据库！");
                    }
                } else if (rawMonth < updMonth) {
                    // 该分支可被删除
                    System.out.println("添加的不是最新数据，不用更新 update 数据库！");
                }
            } else if (rawYear < updYear) {
                // 该分支可被删除
                System.out.println("添加的不是最新数据，不用更新 update 数据库！");
            }

            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public int getStockNum(){
        return dao.selectCount(null);
    }

    public List<StockChange> getHistoryRecord(Integer stock_id, Integer pageSize){
        IPage<StockChange> page = new Page<>(1, pageSize);
        return dao.getHistoryRecord(page, stock_id).getRecords();
    }

}
