package com.pmspod.mds.generator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pmspod.mds.config.SecuritiesConfig;
import com.pmspod.mds.domain.dto.MarketData;
import com.pmspod.mds.domain.equity.Security;
//import com.pmspod.mds.domain.equity.Stock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Slf4j
@Component
public class PriceGenerator implements ApplicationRunner {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    public SecuritiesConfig securitiesConfig;

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Map<String, Security> securitiesMap = securitiesConfig.getSecuritiesMap();
        while (true) {
            for (Map.Entry<String, Security> entry : securitiesMap.entrySet()) {
                MarketData data = generateMessage(entry.getValue());
                String topic = generateTopic(entry.getValue());
                String jsonData = mapper.writeValueAsString(data);

                // log.info("topic: {}, data: {}", topic, jsonData);
                messagingTemplate.convertAndSend(topic, jsonData);

            }
            Thread.sleep(50);
        }
    }

    public String generateTopic(Security security){
        String symbol = security.getSymbol();
        String exchange = security.getExchange();

        return "/topic/v1";
    }

    public MarketData generateMessage(Security security){
        List<Object> priceAndSize = this.generatePriceAndSize(security);

        float tradePrice = (float) priceAndSize.get(0);
        int tradeSize = (int) priceAndSize.get(1);
        float askPrice = (float) priceAndSize.get(2);
        int askSize = (int) priceAndSize.get(3);
        float bidPrice = (float) priceAndSize.get(4);
        int bidSize = (int) priceAndSize.get(5);
        OffsetDateTime currentDateTime = OffsetDateTime.now();

        MarketData marketData = new MarketData();
        marketData.setDate(currentDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        marketData.setTime(currentDateTime.format(DateTimeFormatter.ISO_OFFSET_TIME));
        marketData.setSymbol(security.getSymbol());
        marketData.setTradePrice(tradePrice);
        marketData.setTradeSize(tradeSize);
        marketData.setAskPrice(askPrice);
        marketData.setAskSize(askSize);
        marketData.setBidPrice(bidPrice);
        marketData.setBidSize(bidSize);
        marketData.setExchange(security.getExchange());
        marketData.setCurrency(security.getCurrency());

        return marketData;
    }

    private List<Object> generatePriceAndSize(Security security) {
        // Generate a random number and use it as a threshold
        // The threshold can be positive or negative
        Random rand = new Random();
        float threshold = (float) (rand.nextInt(21) - 10)/800;

        float lastTradePrice = security.getLastTradePrice();
        float tradePrice = lastTradePrice+(lastTradePrice*threshold);
        security.setLastTradePrice(tradePrice);

        float askPrice = tradePrice+(tradePrice*Math.abs(threshold));
        float bidPrice = tradePrice-(tradePrice*Math.abs(threshold));
        security.setLastAskPrice(askPrice);
        security.setLastBidPrice(bidPrice);

        Random randSize = new Random();
        int tradeSize = randSize.nextInt(51)*10;
        int askSize = randSize.nextInt(81)*10;
        int bidSize = randSize.nextInt(81)*10;

        return Arrays.asList(tradePrice, tradeSize, askPrice, askSize, bidPrice, bidSize);
    }
}
