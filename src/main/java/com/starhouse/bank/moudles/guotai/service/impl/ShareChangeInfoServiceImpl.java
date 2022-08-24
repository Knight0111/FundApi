package com.starhouse.bank.moudles.guotai.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.starhouse.bank.moudles.guotai.entity.ShareChangeInfo;
import com.starhouse.bank.moudles.guotai.mapper.ShareChangeInfoMapper;
import com.starhouse.bank.moudles.guotai.service.ShareChangeInfoService;
import org.springframework.stereotype.Service;

@Service
public class ShareChangeInfoServiceImpl extends ServiceImpl<ShareChangeInfoMapper, ShareChangeInfo> implements ShareChangeInfoService{
}
