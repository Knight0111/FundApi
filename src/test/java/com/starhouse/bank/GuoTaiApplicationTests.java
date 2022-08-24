package com.starhouse.bank;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.lang.Console;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSONObject;
import com.starhouse.bank.moudles.guotai.entity.*;
import com.starhouse.bank.moudles.guotai.fundNetVal.entity.FundNetVal;
import com.starhouse.bank.moudles.guotai.fundNetVal.service.FundNetValService;
import com.starhouse.bank.moudles.guotai.service.*;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@SpringBootTest
class GuoTaiApplicationTests {
    static String  secretKey = "^h$_0j9kwf$y7,@w%;o+f[]-";
    private static final String Algorithm = "DESede";
    private static final String BaseUrl = "https://pbtest.gtja.com/fsdpl-api/api/";

    @Autowired(required = false)
    private FundNetValService fundNetValService;
    @Autowired(required = false)
    private ShareChangeInfoService shareChangeInfoService;
    @Autowired(required = false)
    private ShareHolderInfoService shareHolderInfoService;
    @Autowired(required = false)
    private ConfirmInfoService confirmInfoService;
    @Autowired(required = false)
    private CustInfoService custInfoService;
    @Autowired(required = false)
    private BonusInfoService bonusInfoService;
    @Autowired(required = false)
    private FundProfitService fundProfitService;

    public static String encryptMode(byte[] keybyte, byte[] src) {
        try {
            // 生成密钥
            SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);

            // 加密
            Cipher c1 = Cipher.getInstance(Algorithm);
            c1.init(Cipher.ENCRYPT_MODE, deskey);
            byte[] bytes = c1.doFinal(src);
            String encode = Base64.encode(bytes);
            return encode;
        } catch (java.security.NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (javax.crypto.NoSuchPaddingException e2) {
            e2.printStackTrace();
        } catch (java.lang.Exception e3) {
            e3.printStackTrace();
        }
        return null;
    }

    @SneakyThrows
    public String getData(JSONObject jsonObject,String url){
        String bizParamJson = jsonObject.toJSONString();
        String appKey = "ngmaSPGtvrDy1JW9EEM8";
        String managerId = "00000001";
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMddHHmmssSSS");
        String timeStamp = sdf.format(date);
        System.out.println(timeStamp);
        String bizParam = URLEncoder.encode(bizParamJson,"UTF-8");
        System.out.println(bizParam);
        String params ="appkey=" + appKey +"&bizParam=" + bizParam +"&managerId=" + managerId +"&timeStamp=" + timeStamp;
        System.out.println(params);
        String appsigned = HmacUtil.encrypt(params, "xGqqc0OZhS2bk7fnYHrSaTH5rzu5pU&", HmacUtil.HMAC_SHA512).replace("+","-").replace("=","").replaceAll("/","_");
        System.out.println( appsigned);
        byte[] key = "^h$_0j9kwf$y7,@w%;o+f[]-".getBytes(StandardCharsets.UTF_8);
        String bizParamFinal = encryptMode(key,params.getBytes(StandardCharsets.UTF_8));
        System.out.println(bizParamFinal);

        HashMap map = new HashMap();
        map.put("appKey",appKey);
        map.put("timeStamp",timeStamp);
        map.put("managerId",managerId);
        map.put("appsigned", appsigned);
        map.put("bizParam",bizParamFinal);

        String result = HttpRequest.post(BaseUrl + url + "?appkey=" + appKey)
                .header(Header.CONTENT_TYPE, "application/x-www-form-urlencoded")//头信息，多个头信息多次调用此方法即可
                .form(map)
                //表单内容
                .timeout(80000)//超时，毫秒
                .execute().body();
        Console.log(result);
        return result;

    }
    @SneakyThrows
    public String getRequestData(JSONObject jsonObject,String url){
        String bizParamJson = jsonObject.toJSONString();
        String appKey = "ngmaSPGtvrDy1JW9EEM8";
        String managerId = "00000001";
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMddHHmmssSSS");
        String timeStamp = sdf.format(date);
        // System.out.println(timeStamp);
        String bizParam = URLEncoder.encode(bizParamJson,"UTF-8");
        String params ="appkey=" + appKey +"&bizParam=" + bizParam +"&managerId=" + managerId +"&timeStamp=" + timeStamp;
        // System.out.println(params);
        String appsigned = HmacUtil.encrypt(params, "xGqqc0OZhS2bk7fnYHrSaTH5rzu5pU&", HmacUtil.HMAC_SHA512).replace("+","-").replace("=","").replaceAll("/","_");
        // System.out.println( appsigned);
        byte[] key = "^h$_0j9kwf$y7,@w%;o+f[]-".getBytes(StandardCharsets.UTF_8);
        String bizParamFinal = encryptMode(key,params.getBytes(StandardCharsets.UTF_8));
        // System.out.println(bizParamFinal);

        HashMap map = new HashMap();
        map.put("appKey",appKey);
        map.put("timeStamp",timeStamp);
        map.put("managerId",managerId);
        map.put("appsigned", appsigned);
        map.put("bizParam",bizParamFinal);

        String result = HttpRequest.post(BaseUrl + url + "?appkey=" + appKey)
                .header(Header.CONTENT_TYPE, "application/x-www-form-urlencoded")//头信息，多个头信息多次调用此方法即可
                .form(map)
                //表单内容
                .timeout(80000)//超时，毫秒
                .execute().body();
        return result;
    }

    public int getTotal(JSONObject jsonObject,String url){
        String requestData = getRequestData(jsonObject, url);
        JSONObject resultJsonObject  = JSONObject.parseObject(requestData);
        JSONObject data = resultJsonObject.getObject("data",JSONObject.class);
        // System.out.println(data);
        Integer totalCount = data.getObject("totalCount", Integer.class);
        System.out.println("当前业务总页数为:"+ totalCount);
        return totalCount;
    }
    @SneakyThrows
    public List<JSONObject> getData2(JSONObject jsonObject,String url){
        int total = getTotal(jsonObject, url);
        Integer reqNum = jsonObject.getObject("reqNum", Integer.class);
        BigDecimal countB = new BigDecimal(total).divide(new BigDecimal(reqNum));
        double v = countB.doubleValue();
        int count = (int) Math.ceil(v);
        // System.out.println(count);
        List<JSONObject> voList = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            System.out.println("开始获取第" + i + "页数据");
            jsonObject.put("reqPageno",i);
            String requestData = getRequestData(jsonObject, url);
            JSONObject resultJsonObject  = JSONObject.parseObject(requestData);
            JSONObject data = resultJsonObject.getObject("data",JSONObject.class);
            List<JSONObject> result = data.getObject("result", List.class);
            System.out.println(result.size());
            voList.addAll(result);
        }

        return voList;
    }

    @SneakyThrows
    @Test
    void FundNetValData2() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("netDateBegin","20220501");
        jsonObject.put("netDateEnd","20220601");
        jsonObject.put("reqNum",100);
        jsonObject.put("reqPageno",1);
        List<JSONObject> list = getData2(jsonObject, "queryService.fundnetval");
        System.out.println("获取到的数据条数:" + list.size());
        List<FundNetVal> voList = new ArrayList<>();
        for (JSONObject object : list) {
            FundNetVal fundNetVal = object.toJavaObject(FundNetVal.class);
            fundNetVal.setCompany("国泰君安");
            voList.add(fundNetVal);
        }
        System.out.println(voList.size());
        fundNetValService.saveBatch(voList);
    }

    @SneakyThrows
    @Test
    void FundNetValData() {
        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("fundCode","SSD575");
       jsonObject.put("netDateBegin","20220501");
        jsonObject.put("netDateEnd","20220601");
        jsonObject.put("reqNum",100);
        jsonObject.put("reqPageno",1);
        String result = getData(jsonObject, "queryService.fundnetval");
        System.out.println(result);

        JSONObject jsonObject1  = JSONObject.parseObject(result);
        JSONObject data = jsonObject1.getObject("data",JSONObject.class);
        List<JSONObject> list = (List<JSONObject>) data.get("result");
        System.out.println(list.size());
        List<FundNetVal> voList = new ArrayList<>();
        for (JSONObject object : list) {
            FundNetVal fundNetVal = object.toJavaObject(FundNetVal.class);
            fundNetVal.setCompany("国泰君安");
            voList.add(fundNetVal);
        }
        for (FundNetVal fundNetVal : voList) {
            System.out.println(fundNetVal);
        }
//        fundNetValService.saveBatch(voList);
    }


    @SneakyThrows
    @Test
    void ShareChangeInfoData() {
        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("fundCode","CS0003");
        jsonObject.put("beginDate","20220101");
        jsonObject.put("endDate","20220220");
        jsonObject.put("reqNum",20);
        jsonObject.put("reqPageno",1);
        String result = getData(jsonObject, "queryService.shareChangeInfo");
        System.out.println(result);

        JSONObject jsonObject1  = JSONObject.parseObject(result);
        JSONObject data = jsonObject1.getObject("data",JSONObject.class);
        List<JSONObject> list = (List<JSONObject>) data.get("result");
        System.out.println(list.size());
        List<ShareChangeInfo> voList = new ArrayList<>();
        for (JSONObject object : list) {
            voList.add(object.toJavaObject(ShareChangeInfo.class));
        }
        for (ShareChangeInfo shareChangeInfo : voList) {
            System.out.println(shareChangeInfo);
        }
        shareChangeInfoService.saveBatch(voList);
    }

    @SneakyThrows
    @Test
    void ShareHolderInfoData() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("fundCode","CS0005");
//        jsonObject.put("beginDate","20220501");
//        jsonObject.put("endDate","20220601");
        jsonObject.put("reqNum",100);
        jsonObject.put("reqPageno",1);
        String result = getData(jsonObject, "queryService.shareholderinfo");
        System.out.println(result);

        JSONObject jsonObject1  = JSONObject.parseObject(result);
        JSONObject data = jsonObject1.getObject("data",JSONObject.class);
        List<JSONObject> list = (List<JSONObject>) data.get("result");
        System.out.println(list.size());
        List<ShareHolderInfo> voList = new ArrayList<>();
        for (JSONObject object : list) {
            voList.add(object.toJavaObject(ShareHolderInfo.class));
        }
        for (ShareHolderInfo shareHolderInfo : voList) {
            System.out.println(shareHolderInfo);
        }
        shareHolderInfoService.saveBatch(voList);
    }

    @SneakyThrows
    @Test
    void ConfirmInfoData() {
        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("fundCode","CS0003");
        jsonObject.put("beginDate","20220501");
        jsonObject.put("endDate","20220601");
        jsonObject.put("reqNum",100);
        jsonObject.put("reqPageno",1);
        String result = getData(jsonObject, "queryService.confirminfo");
        System.out.println(result);

        JSONObject jsonObject1  = JSONObject.parseObject(result);
        JSONObject data = jsonObject1.getObject("data",JSONObject.class);
        List<JSONObject> list = (List<JSONObject>) data.get("result");
        System.out.println(list.size());
        List<ConfirmInfo> voList = new ArrayList<>();
        for (JSONObject object : list) {
            voList.add(object.toJavaObject(ConfirmInfo.class));
        }
        for (ConfirmInfo confirmInfo : voList) {
            System.out.println(confirmInfo);
        }
        confirmInfoService.saveBatch(voList);
    }

    @SneakyThrows
    @Test
    void CustInfoData() {
        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("fundCode","CS0003");
//        jsonObject.put("beginDate","20220101");
//        jsonObject.put("endDate","20220220");
        jsonObject.put("reqNum",20);
        jsonObject.put("reqPageno",1);
        String result = getData(jsonObject, "queryService.custinfo");
        System.out.println(result);

        JSONObject jsonObject1  = JSONObject.parseObject(result);
        JSONObject data = jsonObject1.getObject("data",JSONObject.class);
        List<JSONObject> list = (List<JSONObject>) data.get("result");
        System.out.println(list.size());
        List<CustInfo> voList = new ArrayList<>();
        for (JSONObject object : list) {
            CustInfo custInfo = object.toJavaObject(CustInfo.class);
            custInfo.setCompany("国泰君安");
            voList.add(custInfo);
        }
        for (CustInfo custInfo : voList) {
            System.out.println(custInfo);
        }
        custInfoService.saveBatch(voList);
    }

    @SneakyThrows
    @Test
    void BonusInfoData() {
        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("custName","8686");
//        jsonObject.put("fundCode","CS0003");
//        jsonObject.put("beginDate","20220101");
//        jsonObject.put("endDate","20220220");
        jsonObject.put("reqNum",100);
        jsonObject.put("reqPageno",1);
        String result = getData(jsonObject, "queryService.queryBonusInfo");
        System.out.println(result);

        JSONObject jsonObject1  = JSONObject.parseObject(result);
        JSONObject data = jsonObject1.getObject("data",JSONObject.class);
        List<JSONObject> list = (List<JSONObject>) data.get("result");
        System.out.println(list.size());
        List<BonusInfo> voList = new ArrayList<>();
        for (JSONObject object : list) {
            voList.add(object.toJavaObject(BonusInfo.class));
        }
        for (BonusInfo bonusInfo : voList) {
            System.out.println(bonusInfo);
        }
        bonusInfoService.saveBatch(voList);
    }

    @SneakyThrows
    @Test
    void FundProfitData() {
        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("fundCode","CS0003");
//        jsonObject.put("beginDate","20220101");
//        jsonObject.put("endDate","20220220");
        jsonObject.put("reqNum",100);
        jsonObject.put("reqPageno",1);
        String result = getData(jsonObject, "queryService.fundprofit");
        System.out.println(result);

        JSONObject jsonObject1  = JSONObject.parseObject(result);
        JSONObject data = jsonObject1.getObject("data",JSONObject.class);
        List<JSONObject> list = (List<JSONObject>) data.get("result");
        System.out.println(list.size());
        List<FundProfit> voList = new ArrayList<>();
        for (JSONObject object : list) {
            voList.add(object.toJavaObject(FundProfit.class));
        }
        for (FundProfit fundProfit : voList) {
            System.out.println(fundProfit);
        }
        fundProfitService.saveBatch(voList);
    }

}
