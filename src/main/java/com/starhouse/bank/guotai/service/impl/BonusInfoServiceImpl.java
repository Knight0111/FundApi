package com.starhouse.bank.guotai.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.starhouse.bank.guotai.entity.BonusInfo;
import com.starhouse.bank.guotai.mapper.BonusInfoMapper;
import com.starhouse.bank.guotai.service.BonusInfoService;
import org.springframework.stereotype.Service;

@Service
public class BonusInfoServiceImpl extends ServiceImpl<BonusInfoMapper, BonusInfo> implements BonusInfoService {
}
