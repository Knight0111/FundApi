package com.starhouse.bank.moudles.guotai.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.starhouse.bank.moudles.guotai.entity.BonusInfo;
import com.starhouse.bank.moudles.guotai.mapper.BonusInfoMapper;
import com.starhouse.bank.moudles.guotai.service.BonusInfoService;
import org.springframework.stereotype.Service;

@Service
public class BonusInfoServiceImpl extends ServiceImpl<BonusInfoMapper, BonusInfo> implements BonusInfoService {
}
