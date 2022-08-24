package com.starhouse.bank.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.starhouse.bank.moudles.guotai.fundNetVal.dto.FundNetValDto;
import com.starhouse.bank.moudles.guotai.fundNetVal.entity.FundNetVal;
import com.starhouse.bank.moudles.guotai.fundNetVal.service.FundNetValService;
import com.starhouse.bank.result.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class ApiController {

    @Autowired
    private FundNetValService fundNetValService;

    /**
     * xxx功能 存储数据
     * @return
     */
    @PostMapping("/fundNetValAdd")
    public ApiResult fundNetValAdd(@RequestBody FundNetValDto fundNetValDto){
        fundNetValService.add(fundNetValDto);
        return ApiResult.ok();
    }
    @GetMapping("/fundNetValList")
    public ApiResult fundNetValList(@RequestParam long page,@RequestParam long size){
        Page<FundNetVal> pageModel = new Page<FundNetVal>(page,size);
        Page<FundNetVal> pageData = fundNetValService.page(pageModel);
        return ApiResult.ok(pageData.getRecords());
    }


}

