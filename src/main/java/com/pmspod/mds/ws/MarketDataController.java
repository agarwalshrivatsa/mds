package com.pmspod.mds.ws;

import com.pmspod.mds.domain.dto.MarketData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class MarketDataController {

    @MessageMapping("/topic/v1")
    @SendTo("/topic/v1")
    public MarketData handleMarketData(MarketData marketData) {
        log.info("sending message: {} ", marketData);
        return marketData;
    }
}
