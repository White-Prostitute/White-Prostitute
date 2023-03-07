package com.example.stocksystem.controller;


import com.alibaba.excel.EasyExcel;
import com.example.stocksystem.entity.StockChange;
import com.example.stocksystem.service.impl.StockChangeServiceImpl;
import com.example.stocksystem.util.Response;
import com.example.stocksystem.util.StockChangeReadListener;
import com.example.stocksystem.util.UsualUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/file")
public class FileController {

    @Autowired
    StockChangeServiceImpl service;

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
    public Response<String> uploadCsvData(@RequestBody MultipartFile file){
        Response<String> response = new Response<>();
        try{
            List<StockChange> csvData = UsualUtil.getCsvData(file, StockChange.class);
            System.out.println(csvData);
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

}
