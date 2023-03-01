package com.example.stocksystem.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.stocksystem.service.StockService;
import com.example.stocksystem.util.Response;
import com.example.stocksystem.vo.StockVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/stock")
public class StockController {

    @Autowired
    StockService service;

    //获取股票数据(带条件)
    @PostMapping()
    public Response<List<StockVo>> getAllStockInfo(Integer stock_id, @DefaultValue("") String stock_name,
                                                               Integer pageIndex, Integer pageSize){
        Response<List<StockVo>> response = new Response<>();
        if(stock_id != null&&stock_id < 0){
            response.setCode(Response.PARA_MISTAKE);
            response.setMsg("stock_id应该大于0");
            response.setData(new ArrayList<>());
            return response;
        }
        List<StockVo> list = service.getStockInfo(stock_id, stock_name, pageIndex, pageSize).getRecords();
        if(list == null){
            list = new ArrayList<>();
        }
        response.setCode(Response.OK);
        response.setMsg("获取数据成功");
        response.setData(list);
        return response;
    }

    @GetMapping("")
    public Response<List<StockVo>> getStockInfo(Integer pageIndex, Integer pageSize){
        Response<List<StockVo>> response = new Response<>();
        if(pageIndex == null || pageSize == null){
            response.setCode(Response.PARA_MISTAKE);
            response.setMsg("参数有误");
            response.setData(new ArrayList<>());
            return response;
        }
        try{
            IPage<StockVo> info = service.getStockInfo(null, "", pageIndex, pageSize);
            response.setCode(Response.OK);
            response.setMsg("获取数据成功");
            response.setData(info.getRecords());
            return response;
        }catch (Exception e){
               response.setCode(Response.SERVER_EXCEPTION);
               response.setMsg("分页参数异常");
               response.setData(new ArrayList<>());
               return response;
        }
    }
}
