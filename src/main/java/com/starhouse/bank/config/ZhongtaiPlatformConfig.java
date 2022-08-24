package com.starhouse.bank.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = {"classpath:application.yml"}, factory = YmlPropertySourceFactory.class)
@ConfigurationProperties(prefix = "platform.zhongtai")
@Data
public class ZhongtaiPlatformConfig {
    private String baseUrl;
    private String appId;
    private String appKey;
    private String secretKey;
}
