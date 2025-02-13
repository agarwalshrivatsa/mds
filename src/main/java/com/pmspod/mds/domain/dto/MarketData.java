package com.pmspod.mds.domain.dto;

import lombok.Data;

@Data
public class MarketData {

    private String date;
    private String time;
    private String symbol;
    private float tradePrice;
    private int tradeSize;
    private float askPrice;
    private float bidPrice;
    private int askSize;
    private int bidSize;
    private String exchange;
    private String currency;


}
