package com.starhouse.bank.moudles.guotai.fundNetVal.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.starhouse.bank.moudles.guotai.fundNetVal.dto.FundNetValDto;
import com.starhouse.bank.moudles.guotai.fundNetVal.entity.FundNetVal;
import com.starhouse.bank.moudles.guotai.fundNetVal.mapper.FundNetValMapper;
import com.starhouse.bank.moudles.guotai.fundNetVal.service.FundNetValService;
import com.starhouse.bank.moudles.guotai.utils.GetRequestData;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FundNetValServiceImpl extends ServiceImpl<FundNetValMapper, FundNetVal> implements FundNetValService {
    @Override
    public void add(FundNetValDto fundNetValDto) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("netDateBegin",fundNetValDto.getNetDateBegin());
        jsonObject.put("netDateEnd","20220601");
        jsonObject.put("reqNum",100);
        jsonObject.put("reqPageno",1);
        List<JSONObject> list = GetRequestData.getData(jsonObject, "queryService.fundnetval");
        System.out.println("获取到的数据条数:" + list.size());
        List<FundNetVal> voList = new ArrayList<>();
        for (JSONObject object : list) {
            FundNetVal fundNetVal = object.toJavaObject(FundNetVal.class);
            fundNetVal.setCompany("国泰君安");
            voList.add(fundNetVal);
        }
        saveBatch(voList);
    }
}
