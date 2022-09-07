package com.starhouse.bank.moudles.guoxin.service;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.starhouse.bank.config.GuoxinPlatformConfig;
import com.starhouse.bank.moudles.guoxin.entity.NetValueInfo;
import com.starhouse.bank.moudles.zhongxin.queryFundNetVal.entity.QueryFundNetVal;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
public class GuoxinService {

    @Autowired
    private NetValueInfoService netValInfoService;

    @Autowired
    private  GuoxinPlatformConfig guoxinPlatformConfig;

    public String getSign(String msg){
        String str = guoxinPlatformConfig.getSecKey() + msg;
        System.out.println(SecureUtil.md5(str));


        return SecureUtil.md5(str).toUpperCase();
    }

    @SneakyThrows
    private List<JSONObject> getList(String api , HashMap<String,Object> map){
        List<JSONObject> voList = new ArrayList<>();
        String result = HttpRequest.get(guoxinPlatformConfig.getBaseUrl() + api)
                .form(map)
                .timeout(80000)
                .execute().body();
        JSONObject jsonObject  = JSONObject.parseObject(result);
        JSONArray jsonArray = jsonObject.getJSONArray("returnObject");
        for (Object object : jsonArray) {
            voList.add((JSONObject) object);
        }
        return voList;

    }


    public void netValueInfo(String api, String start_dt,String end_dt){
        String param = "appId" + guoxinPlatformConfig.getAppID()  + "endDate" + end_dt +
                "managerCode" + guoxinPlatformConfig.getManagerId() + "startDate" + start_dt;
        String sign = getSign(param);

        HashMap<String,Object> map = new HashMap();
        map.put("managerCode",guoxinPlatformConfig.getManagerId());
        map.put("startDate",start_dt);
        map.put("endDate",end_dt);
        map.put("appId",guoxinPlatformConfig.getAppID());
        map.put("sign",sign);

        List<JSONObject> list = getList("/netValueInfo/queryList", map);
        List<NetValueInfo> voList = new ArrayList<>();
        for (JSONObject object : list) {
            NetValueInfo netValueInfo = object.toJavaObject(NetValueInfo.class);
            netValueInfo.setCompany("国信托管");
            voList.add(netValueInfo);
        }
        for (NetValueInfo netValueInfo : voList){
            System.out.println(netValueInfo);
        }

//        netValInfoService.saveBatch(voList);
    }



    public void investorInfo(String api){
        String param = "appId"+guoxinPlatformConfig.getAppID() + "managerCode" + guoxinPlatformConfig.getManagerId();
        String sign = getSign(param);

        HashMap<String,Object> map = new HashMap();
        map.put("managerCode",guoxinPlatformConfig.getManagerId());
        map.put("appId",guoxinPlatformConfig.getAppID());
        map.put("sign",sign);

        String result = HttpRequest.get(guoxinPlatformConfig.getBaseUrl() + api)
                .form(map)
                .timeout(80000)
                .execute().body();
        JSONObject jsonObject = JSON.parseObject(result);
        JSONArray data = jsonObject.getJSONArray("returnObject");
        List<JSONObject> voList = new ArrayList<>();
        for (Object object : data) {
            voList.add((JSONObject) object);
        }
        System.out.println(voList);
    }

}
