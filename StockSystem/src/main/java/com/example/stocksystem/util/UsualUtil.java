package com.example.stocksystem.util;

import com.example.stocksystem.entity.StockChange;
import com.example.stocksystem.vo.StockVo;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
import com.opencsv.bean.*;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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

    //手动解析csv文件,获取对应对象的List
    public static List<StockChange> getCsvData(MultipartFile file) throws IOException, ParseException {
        CSVReader csvReader = new CSVReaderBuilder(new BufferedReader(
                new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))).build();
        Iterator<String[]> iterator = csvReader.iterator();
        boolean isHead = true;//不读表头
        List<StockChange> list = new ArrayList<>();
        while (iterator.hasNext()){
            String[] next = iterator.next();
            if(!isHead){
                StockChange change = new StockChange();
                change.setStockId(Integer.parseInt(next[0]));
                SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
                Date date = format.parse(next[1]);
                change.setDate(date);
                change.setPriceHigh(Float.parseFloat(next[2]));
                change.setPriceLow(Float.parseFloat(next[3]));
                change.setPriceOpen(Float.parseFloat(next[4]));
                change.setPriceClose(Float.parseFloat(next[5]));
                change.setVolume(Integer.parseInt(next[6]));
                //校验股票数据是否合理
                if(ValidateUtil.validateStockChange(change)) list.add(change);
            }
            isHead = false;
        }
        return list;
    }

    //将获取到的list写入Csv文件
    public static void writeCsvData(List<StockVo> list) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        Writer writer = new FileWriter("data;data.csv");
        String[] column = {"stockId","date","stockName","stockType","priceHigh","priceLow",
                "priceOpen","priceClose","volume"};
        ColumnPositionMappingStrategy<StockVo> mappingStrategy =
                new ColumnPositionMappingStrategy<>();
        mappingStrategy.setType(StockVo.class);
        mappingStrategy.setColumnMapping(column);

        CSVWriter csvWriter = new CSVWriter(writer, CSVWriter.DEFAULT_SEPARATOR,
                CSVWriter.NO_QUOTE_CHARACTER, '\\', "\n");
        String[] header =  {"stockId","date","stockName","stockType","priceHigh","priceLow",
                "priceOpen","priceClose","volume"};
        csvWriter.writeNext(header);

        StatefulBeanToCsv<StockVo> beanToCsv = new StatefulBeanToCsvBuilder<StockVo>(writer)
                .withMappingStrategy(mappingStrategy)
                .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                .withSeparator(CSVWriter.DEFAULT_SEPARATOR).build();
        beanToCsv.write(list);
        csvWriter.close();
        writer.close();
    }

}
