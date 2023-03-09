package com.example.stocksystem.controller;

import com.example.stocksystem.entity.StockChange;
import com.example.stocksystem.service.StockService;
import com.example.stocksystem.util.Response;
import com.example.stocksystem.util.UsualUtil;
import com.example.stocksystem.vo.StockVo;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/stock")
@CrossOrigin
public class StockController {

    @Autowired
    StockService service;


    @GetMapping("/test")
    public void test(HttpServletResponse servletResponse) throws IOException {
        FileInputStream fis = new FileInputStream("data"+ File.pathSeparator+"data.csv");
        ServletOutputStream outputStream = servletResponse.getOutputStream();
        servletResponse.setHeader("Content-Disposition", "attachment;fileName=data.csv");
        byte[] bytes = new byte[1024];
        int len = 0;
        while((len = fis.read(bytes))!=-1){
            outputStream.write(bytes, 0, len);
        }
    }

    //获取股票数据(带条件)
    @RequestMapping()
    public Response<List<StockVo>> getAllStockInfo(/*@RequestBody Map<String, String> map*/Integer stock_id, String stock_name,
                                                               Integer pageIndex, Integer pageSize, String date) throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, IOException {
//        Integer pageIndex = map.get("pageIndex")!=null?Integer.parseInt(map.get("pageIndex")):null;
//        Integer pageSize = map.get("pageSize")!=null?Integer.parseInt(map.get("pageSize")):null;
//        Integer stock_id = map.get("stock_id")!=null?Integer.parseInt(map.get("stock_id")):null;
//        String date = map.get("date");
//        String stock_name = map.get("stock_name");

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
        UsualUtil.writeCsvData(list);
        response.setCode(Response.OK);
        response.setMsg("获取数据成功");
        response.setData(list);
        return response;
    }
    //获取历史数据
    @PostMapping("/history/{stock_id}/{size}")
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

    @GetMapping("/top")
    public Response<List<StockVo>> getTop(Integer size){
        Response<List<StockVo>> response = new Response<>();
        if(size==null||size <= 0){
            response.setCode(Response.PARA_MISTAKE);
            response.setMsg("参数错误");
            response.setData(new ArrayList<>());
        }else{
            List<StockVo> top = service.getTop(size);
            response.setCode(Response.OK);
            response.setMsg("获取成功");
            response.setData(top);
        }
        return response;
    }

    @GetMapping("/nameList")
    public Response<List<String>> getNameList(String name){
        System.out.println(name);
        Response<List<String>> response = new Response<>();
        if(name == null || name.length() == 0){
            response.setCode(Response.PARA_MISTAKE);
            response.setMsg("需要参数");
            response.setData(new ArrayList<>());
        }else{
            response.setCode(Response.OK);
            response.setMsg("获取数据成功");
            response.setData(service.getNameList(name));
        }
        return response;
    }
}
