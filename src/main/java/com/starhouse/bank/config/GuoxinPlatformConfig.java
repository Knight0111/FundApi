package com.starhouse.bank.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = {"classpath:application.yml"}, factory = YmlPropertySourceFactory.class)
@ConfigurationProperties(prefix = "platform.guoxin")
@Data
public class GuoxinPlatformConfig {
    private String appID;
    private String secKey;
    private String managerId;
    private String baseUrl;
}
