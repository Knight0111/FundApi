package com.starhouse.bank.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = {"classpath:application.yml"}, factory = YmlPropertySourceFactory.class)
@ConfigurationProperties(prefix = "platform.zhaoshang")
public class ZhaoshangPlatformConfig {
}
