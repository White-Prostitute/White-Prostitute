package com.example.stocksystem.controller;

import com.alibaba.excel.EasyExcel;
import com.example.stocksystem.dao.StockDao;
import com.example.stocksystem.entity.StockChange;
import com.example.stocksystem.service.StockService;
import com.example.stocksystem.service.impl.StockChangeServiceImpl;
import com.example.stocksystem.util.Response;
import com.example.stocksystem.util.StockChangeReadListener;
import com.example.stocksystem.util.UsualUtil;
import com.example.stocksystem.vo.StockVo;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/file")
@CrossOrigin
public class FileController {

    @Autowired
    StockChangeServiceImpl service;

    @Autowired
    StockService stockService;

    @Autowired
    StockDao dao;

    @Resource
    RedisTemplate<String, String> template;

    //接收base64格式的文件
    @PostMapping()
    public Response<String> acceptFile(String base64Str, String fileName){
        Response<String> response = new Response<>();
        try{
            UsualUtil.saveFile(base64Str, "file/"+fileName);
        }catch (Exception e){
            response.setCode(Response.SERVER_EXCEPTION);
            response.setMsg("服务器发生错误");
            response.setData("fail");
        }
        response.setCode(Response.OK);
        response.setMsg("上传文件成功");
        response.setData("success");
        return response;
    }


    //使用Multiple对象接收文件并将数据储存到数据库
    @PostMapping("/data")
    public Response<String> uploadData(@RequestBody MultipartFile file) {
        Response<String> response = new Response<>();
        try{
            EasyExcel.read(file.getInputStream(), StockChange.class, new StockChangeReadListener(service)).sheet().doRead();
        }catch (Exception e){
            e.printStackTrace();
            response.setCode(Response.SERVER_EXCEPTION);
            response.setMsg(("上传失败"));
            response.setData("fail");
            return response;
        }
        response.setCode(Response.OK);
        response.setMsg("上传数据成功");
        response.setMsg("成功");
        return response;
    }

    //接收csv文件并解析
    @PostMapping("/csv")
    public Response<String> uploadCsvData(@RequestBody MultipartFile file) {
        Response<String> response = new Response<>();
        try{
            List<StockChange> csvData = UsualUtil.getCsvData(file);
            System.out.println("数据量 : " + csvData.size());
            //service.updateBatchById(csvData);//更新实时表

            //更新数据将导致缓存失效,暂时先让所有缓存失效
            Set<String> keys = template.keys("*");
            if(keys != null)template.delete(keys);

            for (StockChange change : csvData) {
                dao.updateStockInfo(change);
            }
        }catch (Exception e){
            response.setCode(Response.SERVER_EXCEPTION);
            response.setMsg("发生了错误");
            response.setData("fail");
            return response;
        }
        response.setCode(Response.OK);
        response.setMsg("上传成功");
        response.setData("success");
        return response;
    }

    //接收股票id数组
    @PostMapping("/download")
    public Response<String> downLoadFile(@RequestBody List<String> list, HttpServletResponse servletResponse) throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, IOException {
        System.out.println("收到了数据");
        List<StockVo> stockVos = new ArrayList<>();
        for (String s : list) {
            StockVo vo = dao.getOneInfoById(Integer.parseInt(s));
            stockVos.add(vo);
        }
        UsualUtil.writeCsvData(stockVos);
//        FileInputStream fis = new FileInputStream("data"+ File.pathSeparator+"data.csv");
//        servletResponse.setHeader("Content-Disposition", "attachment;fileName=data.csv");
//        servletResponse.setHeader("content-type", "application/octet-stream");
//        ServletOutputStream outputStream = servletResponse.getOutputStream();
//        byte[] bytes = new byte[1024];
//        int len = 0;
//        while((len = fis.read(bytes))!=-1){
//            outputStream.write(bytes, 0, len);
//        }
        return null;
    }

}
