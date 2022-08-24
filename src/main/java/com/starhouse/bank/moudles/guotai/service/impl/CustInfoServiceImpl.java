package com.starhouse.bank.moudles.guotai.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.starhouse.bank.moudles.guotai.entity.CustInfo;
import com.starhouse.bank.moudles.guotai.mapper.CustInfoMapper;
import com.starhouse.bank.moudles.guotai.service.CustInfoService;
import org.springframework.stereotype.Service;

@Service
public class CustInfoServiceImpl extends ServiceImpl<CustInfoMapper, CustInfo> implements CustInfoService {
}
