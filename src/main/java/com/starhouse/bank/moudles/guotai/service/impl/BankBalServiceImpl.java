package com.starhouse.bank.moudles.guotai.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.starhouse.bank.moudles.guotai.entity.BankBal;
import com.starhouse.bank.moudles.guotai.mapper.BankBalMapper;
import com.starhouse.bank.moudles.guotai.service.BankBalService;
import org.springframework.stereotype.Service;

@Service
public class BankBalServiceImpl extends ServiceImpl<BankBalMapper, BankBal> implements BankBalService {
}
