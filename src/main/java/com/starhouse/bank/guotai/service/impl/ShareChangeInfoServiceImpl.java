package com.starhouse.bank.guotai.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.starhouse.bank.guotai.entity.ShareChangeInfo;
import com.starhouse.bank.guotai.mapper.ShareChangeInfoMapper;
import com.starhouse.bank.guotai.service.ShareChangeInfoService;
import org.springframework.stereotype.Service;

@Service
public class ShareChangeInfoServiceImpl extends ServiceImpl<ShareChangeInfoMapper, ShareChangeInfo> implements ShareChangeInfoService{
}
