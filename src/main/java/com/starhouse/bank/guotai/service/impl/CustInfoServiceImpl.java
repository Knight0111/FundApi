package com.starhouse.bank.guotai.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.starhouse.bank.guotai.entity.CustInfo;
import com.starhouse.bank.guotai.mapper.CustInfoMapper;
import com.starhouse.bank.guotai.service.CustInfoService;
import org.springframework.stereotype.Service;

@Service
public class CustInfoServiceImpl extends ServiceImpl<CustInfoMapper, CustInfo> implements CustInfoService {
}
