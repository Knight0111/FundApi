package com.starhouse.bank;

import cn.hutool.core.lang.Console;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSONObject;
import com.starhouse.bank.moudles.zhongtai.entity.QueryTaInvestor;
import com.starhouse.bank.moudles.zhongtai.service.QueryTaInvestorService;
import com.starhouse.bank.moudles.zhongtai.entity.QueryTaNetValue;
import com.starhouse.bank.moudles.zhongtai.service.QueryTaNetValueService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.*;

@SpringBootTest
class ZhongTaiApplicationTests  {

    // tgwb.95538.cn
    private final String BaseUrl = "https://tgwb.95538.cn";
    private final String appId = "d46c7c8104bc498f9e71bb8e9038ec5e";
    private final String appKey = "1c91721427b34407994c84e2a1e51be9";
    private final String secKey = "b99b45d5c9ac18780542640019364b5929bc144ffe8e070426b3172ff742188f";
    private String token ="";
    private String date = "";
    private String nonce = "";
    private String sign = "";


    @Autowired
    private QueryTaNetValueService queryTaNetValueService;

    @Autowired
    private QueryTaInvestorService queryTaInvestorService;

    @Test
    public void getToken(){
        HashMap<String,Object> map = new HashMap<>();
        map.put("appId",appId);
        map.put("appKey",appKey);
        String result = HttpRequest.post(BaseUrl + "/custodyMgrService/auth/api/getToken")
                .form(map)
                .timeout(80000)
                .execute().body();
        Console.log(result);
        JSONObject jsonObject  = JSONObject.parseObject(result);
        token = jsonObject.getObject("data",String.class);
        System.out.println(token);
    }

    public void getSign(){
        if (token == null || token.length() == 0){
            getToken();
        }
        date = String.valueOf(new Date().getTime());
        nonce = UUID.randomUUID().toString();
        String str = token + date + nonce + secKey;
        sign = SecureUtil.md5(str);
    }


    private List<JSONObject> getList(String api,HashMap<String,Object> map){
        getSign();
        int finalTotal = getTotal(api, map);
        // 获取当前页数
        int currentPage = (int) map.get("rows");
        BigDecimal count = new BigDecimal(finalTotal).divide(new BigDecimal(currentPage));
        double v = count.doubleValue();
        int count2 = (int) Math.ceil(v);
        List<JSONObject> jsonObjectList = new ArrayList<>();
        for (int i = 1; i <= count2; i++) {
            System.out.println("获取第" + i + "页");
            getSign();
            map.put("currentpage",i);
            String result = HttpRequest.get(BaseUrl + api)
                    .header("AUTHORITY-TOKEN",token)
                    .header("AUTHORITY-DATE",date)
                    .header("AUTHORITY-NONCE",nonce)
                    .header("AUTHORITY-SIGN",sign)
                    .form(map)
                    .timeout(80000)
                    .execute().body().replace("\\","").replace("}\"","}").replace("\"{","{").trim();
            JSONObject jsonObject  = JSONObject.parseObject(result);
            JSONObject data = jsonObject.getObject("data",JSONObject.class);
            List<JSONObject> list = (List<JSONObject>) data.get("records");
            System.out.println("获取到该页" + list.size() + "条数据");
            jsonObjectList.addAll(list);
        }
        return jsonObjectList;
    }


    private int getTotal(String api,HashMap<String,Object> map){
        getSign();
        String result = HttpRequest.get(BaseUrl + api)
                .header("AUTHORITY-TOKEN",token)
                .header("AUTHORITY-DATE",date)
                .header("AUTHORITY-NONCE",nonce)
                .header("AUTHORITY-SIGN",sign)
                .form(map)
                .timeout(80000)
                .execute().body().replace("\\","").replace("}\"","}").replace("\"{","{").trim();
        JSONObject jsonObject  = JSONObject.parseObject(result);
        JSONObject data = jsonObject.getObject("data",JSONObject.class);
        List<JSONObject> list = (List<JSONObject>) data.get("records");
        Integer total = data.getObject("total", Integer.class);
        return total;
    }

    @Test
    public void queryTaNetValue(){
        HashMap<String,Object> map = new HashMap<>();
        map.put("sdate","2021-08-10");
        map.put("edate","2021-11-13");
        map.put("currentpage",1);
        map.put("rows",100);
        List<JSONObject> list = getList("/custodyMgrService/openApi/queryTaNetValue", map);
        System.out.println(list.size());
        List<QueryTaNetValue> voList = new ArrayList<>();
        for (JSONObject object : list) {
            QueryTaNetValue queryTaNetValue = object.toJavaObject(QueryTaNetValue.class);
            queryTaNetValue.setCompany("中泰证券");
            voList.add(queryTaNetValue);
        }
        for (QueryTaNetValue queryTaNetValue : voList) {
            System.out.println(queryTaNetValue);
        }
//        queryTaNetValueService.saveBatch(voList);
    }


    @Test
    public void queryTaInvestor(){
        HashMap<String,Object> map = new HashMap<>();
        map.put("sdate","2021-08-10");
        map.put("edate","2021-11-13");
        map.put("currentpage",1);
        map.put("rows",100);
        List<JSONObject> list = getList("/custodyMgrService/openApi/queryTaInvestor", map);
        System.out.println(list.size());
        List<QueryTaInvestor> voList = new ArrayList<>();
        for (JSONObject object : list) {
            QueryTaInvestor queryTaInvestor = object.toJavaObject(QueryTaInvestor.class);
            queryTaInvestor.setCompany("中泰证券");
            voList.add(queryTaInvestor);
        }
        for (QueryTaInvestor queryTaInvestor : voList) {
            System.out.println(queryTaInvestor);
        }
        queryTaInvestorService.saveBatch(voList);
    }


}
