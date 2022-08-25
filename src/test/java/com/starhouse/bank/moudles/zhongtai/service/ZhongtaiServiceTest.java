package com.starhouse.bank.moudles.zhongtai.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ZhongtaiServiceTest {
    @Autowired
    private ZhongtaiService zhongtaiService;

    @Test
    void getToken() {
        String token = zhongtaiService.getToken();
        System.out.println(token);
    }

    @Test
    void queryTaNetValue() {
        zhongtaiService.queryTaNetValue("2022-08-01", "2022-08-25","量客稳健多策略一号私募证券投资基金", 100);
    }

    @Test
    void queryTaInvestor(){
        zhongtaiService.queryTaInvestor("2022-08-01", "2022-08-25","量客稳健多策略一号私募证券投资基金", 100);
    }
}