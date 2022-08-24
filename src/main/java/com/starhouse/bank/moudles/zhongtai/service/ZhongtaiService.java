package com.starhouse.bank.moudles.zhongtai.service;

import cn.hutool.core.lang.Console;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSONObject;
import com.starhouse.bank.config.ZhongtaiPlatformConfig;
import com.starhouse.bank.moudles.zhongtai.entity.PageData;
import com.starhouse.bank.moudles.zhongtai.entity.QueryTaInvestor;
import com.starhouse.bank.moudles.zhongtai.entity.QueryTaNetValue;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Slf4j
@Service
public class ZhongtaiService {
    @Autowired
    private QueryTaInvestorService queryTaInvestorService;
    @Autowired
    private QueryTaNetValueService queryTaNetValueService;

    @Autowired
    private ZhongtaiPlatformConfig zhongtaiPlatformConfig;

    private String token;

    public String getToken() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("appId", zhongtaiPlatformConfig.getAppId());
        map.put("appKey", zhongtaiPlatformConfig.getAppKey());
        String result = HttpRequest.post(zhongtaiPlatformConfig.getBaseUrl() + "/custodyMgrService/auth/api/getToken")
                .form(map)
                .timeout(80000)
                .execute().body();
        Console.log(result);
        JSONObject jsonObject = JSONObject.parseObject(result);
        token = jsonObject.getObject("data", String.class);
        System.out.println(token);
        return token;
    }

//    public String getSign(String token){
//        if (!Strings.isEmpty(token)){
//            getToken();
//        }
//        String date = String.valueOf(new Date().getTime());
//        String nonce = UUID.randomUUID().toString();
//        String str = token + date + nonce + zhongtaiPlatformConfig.getSecretKey();
//        String sign = SecureUtil.md5(str);
//        return sign;
//    }

    public String doRequest(String api, HashMap<String, Object> map) {
        if (Strings.isEmpty(token)) {
            getToken();
        }

        String date = String.valueOf(new Date().getTime());
        String nonce = UUID.randomUUID().toString();
        String str = token + date + nonce + zhongtaiPlatformConfig.getSecretKey();
        String sign = SecureUtil.md5(str);

        String result = HttpRequest.get(zhongtaiPlatformConfig.getBaseUrl() + api)
                .header("AUTHORITY-TOKEN", token)
                .header("AUTHORITY-DATE", date)
                .header("AUTHORITY-NONCE", nonce)
                .header("AUTHORITY-SIGN", sign)
                .form(map)
                .timeout(80000)
                .execute().body().replace("\\", "").replace("}\"", "}").replace("\"{", "{").trim();
        return result;
    }

    private List<JSONObject> getList(String api, HashMap<String, Object> map) {
        System.out.println("获取第1页");
        PageData firstPage = getPage(api, map, 1);
        System.out.println("获取到该页" + firstPage.getList().size() + "条数据");
        int rows = (int)map.get("rows");
        int pageCount = (int) Math.ceil((double)firstPage.getTotal() / rows);

        List<JSONObject> jsonObjectList = new ArrayList<>();
        jsonObjectList.addAll(firstPage.getList());

        for (int i = 2; i <= pageCount; i++) {
            System.out.println("获取第" + i + "页");
            map.put("currentpage", i);
            PageData pageData = getPage(api, map, i);
            System.out.println("获取到该页" + pageData.getList().size() + "条数据");
            jsonObjectList.addAll(pageData.getList());
        }
        return jsonObjectList;
    }

    private PageData getPage(String api, HashMap<String, Object> map, Integer currentPage) {
        if (map.isEmpty()) {
            return null;
        }
        map.put("currentpage", currentPage);
        String result = doRequest(api, map);
        JSONObject jsonObject = JSONObject.parseObject(result);
        JSONObject data = jsonObject.getObject("data", JSONObject.class);
        List<JSONObject> list = (List<JSONObject>) data.get("records");
        Integer total = data.getObject("total", Integer.class);
        return new PageData(total, list);
    }

    public void queryTaNetValue(String sdate, String edate, Integer rows) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("sdate", sdate);
        map.put("edate", edate);
//        map.put("currentpage", 1);
        map.put("rows", rows);
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
    }

//    public void queryTaInvestor(){
//        HashMap<String,Object> map = new HashMap<>();
//        map.put("sdate","2021-08-10");
//        map.put("edate","2021-11-13");
//        map.put("currentpage",1);
//        map.put("rows",100);
//        List<JSONObject> list = getList("/custodyMgrService/openApi/queryTaInvestor", map);
//        System.out.println(list.size());
//        List<QueryTaInvestor> voList = new ArrayList<>();
//        for (JSONObject object : list) {
//            QueryTaInvestor queryTaInvestor = object.toJavaObject(QueryTaInvestor.class);
//            queryTaInvestor.setCompany("中泰证券");
//            voList.add(queryTaInvestor);
//        }
//        for (QueryTaInvestor queryTaInvestor : voList) {
//            System.out.println(queryTaInvestor);
//        }
//        queryTaInvestorService.saveBatch(voList);
//    }
}
