package com.starhouse.bank.moudles.zhongtai.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ZhongtaiServiceTest {
    @Autowired
    private ZhongTaiService zhongtaiService;

    @Test
    void getToken() {
        String token = zhongtaiService.getToken();
        System.out.println(token);
    }


    @Test
    void queryTaNetValue() { zhongtaiService.queryTaNetValue("量客鼎安稳健一号私募证券投资基金", 100); }

    @Test
    void queryTaInvestor(){
        zhongtaiService.queryTaInvestor("量客稳健多策略一号私募证券投资基金", 100);
    }


    @Test
    void queryTaConfirm(){
        zhongtaiService.queryTaDataConfirm("量客稳健多策略一号私募证券投资基金",100);
    }


    @Test
    void queryInvestorShare(){
        zhongtaiService.queryInvestorShare("量客稳健多策略一号私募证券投资基金",100);
    }


    @Test
    void queryTaFundDividends(){
        zhongtaiService.queryTaFundDividends(100);
    }

    @Test
    void queryTaProfit(){
        zhongtaiService.queryTaProfit("量客稳健多策略一号私募证券投资基金",100);
    }
}