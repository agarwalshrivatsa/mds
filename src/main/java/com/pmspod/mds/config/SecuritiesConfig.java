package com.pmspod.mds.config;

import com.pmspod.mds.domain.equity.Security;
//import com.pmspod.mds.domain.equity.Stock;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.YamlMapFactoryBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;


import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Configuration
@PropertySource(value = "classpath:securities.yml")
@ConfigurationProperties
public class SecuritiesConfig {

    private Map<String, Security> securitiesMap;


}
