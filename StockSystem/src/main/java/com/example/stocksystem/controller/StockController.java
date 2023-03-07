package com.example.stocksystem.controller;

import com.example.stocksystem.entity.StockChange;
import com.example.stocksystem.service.StockService;
import com.example.stocksystem.util.Response;
import com.example.stocksystem.vo.StockVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/stock")
@CrossOrigin
public class StockController {

    @Autowired
    StockService service;

    //获取股票数据(带条件)
    @PostMapping()
    public Response<List<StockVo>> getAllStockInfo(Integer stock_id, String stock_name,
                                                               Integer pageIndex, Integer pageSize, String date){
        System.out.println("index:"+pageIndex + "; size:" + pageSize + "id : " + stock_id);
        Response<List<StockVo>> response = new Response<>();
        if(stock_id != null&&stock_id < 0){
            response.setCode(Response.PARA_MISTAKE);
            response.setMsg("stock_id应该大于0");
            response.setData(new ArrayList<>());
            return response;
        }
        System.out.println("日期是 : " + date);
        List<StockVo> list = service.getStockInfo(stock_id, stock_name, pageIndex, pageSize, date).getRecords();
        if(list == null){
            list = new ArrayList<>();
        }
        response.setCode(Response.OK);
        response.setMsg("获取数据成功");
        response.setData(list);
        return response;
    }
    //获取历史数据
    @GetMapping("/history/{stock_id}/{size}")
    public Response<List<StockChange>> getHistoryRecord(@PathVariable Integer stock_id, @PathVariable Integer size){
        Response<List<StockChange>> response = new Response<>();
        response.setMsg("成功");
        response.setCode(Response.OK);
        response.setData(service.getHistoryRecord(stock_id, size));
        return response;
    }

    @GetMapping("/high")
    public Response<List<Float>> getHighList(Integer stock_id){
        Response<List<Float>> response = new Response<>();
        if(stock_id == null){
            response.setCode(Response.PARA_MISTAKE);
            response.setMsg("stock_id为空");
            response.setData(new ArrayList<>());
        }else{
            List<Float> list = service.getHighList(stock_id);
            response.setCode(Response.OK);
            response.setMsg("获取数据成功");
            response.setData(list);
        }
        return response;
    }

    @GetMapping("/one/{stock_id}")
    public Response<List<StockVo>> getOneInfo(@PathVariable Integer stock_id){
        Response<List<StockVo>> response = new Response<>();
        if(stock_id == null){
            response.setCode(Response.PARA_MISTAKE);
            response.setMsg("需要stock_id参数");
            response.setData(null);
        }else{
            List<StockVo> info = service.getOneStockInfo(stock_id);
            response.setCode(Response.OK);
            response.setMsg("成功获取数据");
            response.setData(info);
        }
        return response;
    }

    @PostMapping("/add")
    public Response<String> addStockInfo(@RequestBody StockChange change){
        Response<String> response = new Response<>();
        Date date = new Date();
        change.setDate(date);
        System.out.println(change);
        boolean flag = service.addStockInfo(change);
        if(flag){
            response.setCode(Response.OK);
            response.setMsg("插入股票记录成功");
            response.setData("success");
        }else{
            response.setCode(Response.SERVER_EXCEPTION);
            response.setMsg("插入操作失败");
            response.setData("fail");
        }
        return response;
    }

    /**
     * 返回股票数量
     */
    @GetMapping("/num")
    public Response<Integer> getStockNum(){
        Response<Integer> response = new Response<>();
        response.setCode(Response.OK);
        response.setMsg("获取股票数量成功");
        response.setData(service.getStockNum());
        return response;
    }
}
