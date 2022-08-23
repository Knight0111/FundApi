package com.starhouse.bank.zhongtai.queryTaInvestor.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.starhouse.bank.zhongtai.queryTaInvestor.entity.QueryTaInvestor;
import com.starhouse.bank.zhongtai.queryTaInvestor.mapper.QueryTaInvestorMapper;
import com.starhouse.bank.zhongtai.queryTaInvestor.service.QueryTaInvestorService;
import com.starhouse.bank.zhongtai.queryTaNetValue.service.QueryTaNetValueService;
import org.springframework.stereotype.Service;

@Service
public class QueryTaInvestorServiceImpl extends ServiceImpl<QueryTaInvestorMapper, QueryTaInvestor> implements QueryTaInvestorService {
}
