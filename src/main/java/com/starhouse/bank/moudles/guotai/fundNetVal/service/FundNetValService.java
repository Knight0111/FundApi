package com.starhouse.bank.moudles.guotai.fundNetVal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.starhouse.bank.moudles.guotai.fundNetVal.dto.FundNetValDto;
import com.starhouse.bank.moudles.guotai.fundNetVal.entity.FundNetVal;

public interface FundNetValService extends IService<FundNetVal> {
    void add(FundNetValDto fundNetValDto);
}
