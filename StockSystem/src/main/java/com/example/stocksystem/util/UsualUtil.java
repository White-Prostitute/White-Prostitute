package com.example.stocksystem.util;

import com.example.stocksystem.entity.StockChange;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.stylesheets.LinkStyle;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

public class UsualUtil {

    //将传输来的base64字符串解码，并存档到文件中
    public static void saveFile(String base64Str, String path) throws IOException {
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] bytes = decoder.decode(base64Str.getBytes(StandardCharsets.UTF_8));
        FileOutputStream fos = new FileOutputStream(path);
        fos.write(bytes, 0, bytes.length);
        fos.close();
    }

    //解析csv文件，获取对应对象的List
    public static <T> List<T> getCsvData(MultipartFile file, Class<T> tClass) {
        InputStreamReader in = null;
        try{
            in = new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8);
        }catch (IOException e){
            e.printStackTrace();
        }
        HeaderColumnNameMappingStrategy<T> mappingStrategy = new HeaderColumnNameMappingStrategy<>();
        mappingStrategy.setType(tClass);
        CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(in).withSeparator(',').withIgnoreQuotations(true)
                .withMappingStrategy(mappingStrategy).build();
        return csvToBean.parse();
    }

}
