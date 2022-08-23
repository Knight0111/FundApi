package com.starhouse.bank.guotai.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.starhouse.bank.guotai.entity.BankBal;
import com.starhouse.bank.guotai.mapper.BankBalMapper;
import com.starhouse.bank.guotai.service.BankBalService;
import org.springframework.stereotype.Service;

@Service
public class BankBalServiceImpl extends ServiceImpl<BankBalMapper, BankBal> implements BankBalService {
}
