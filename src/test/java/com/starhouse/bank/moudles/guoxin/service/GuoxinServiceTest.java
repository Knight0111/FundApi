package com.starhouse.bank.moudles.guoxin.service;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GuoxinServiceTest {
    @Autowired
    private GuoxinService guoxinService;


    @Test
    public void netValueInfo(){
        guoxinService.netValueInfo("/netValueInfo/queryList","20220822","20220828");
    }
}
