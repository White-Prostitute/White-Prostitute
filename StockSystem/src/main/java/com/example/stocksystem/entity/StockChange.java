package com.example.stocksystem.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import nonapi.io.github.classgraph.json.Id;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.Date;

@Data
public class StockChange implements Serializable {

    @TableId
    int stockId;
    Date date;
    @Min(value = 0)
    float priceOpen;
    @Min(value = 0)
    float priceClose;
    @Min(value = 0)
    float priceHigh;
    @Min(value = 0)
    float priceLow;
    @Min(value = 0)
    int volume;

}
