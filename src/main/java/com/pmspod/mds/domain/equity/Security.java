package com.pmspod.mds.domain.equity;

import com.pmspod.mds.domain.exchange.Exchange;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class Security {

    /**
     * The Security class is meant to define generic securities and is extended by Stock class.
     */

    private String symbol;
    private String name;
    private String exchange;
    private String assetClass;
    private float lastTradePrice;
    private float lastAskPrice;
    private float lastBidPrice;
    private String currency;


    public Security() {
    }

    public List<Object> generatePriceAndSize() {
        return null;
    }

}
