package com.starhouse.bank.moudles.guotai.utils;

import cn.hutool.core.codec.Base64;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSONObject;
import com.starhouse.bank.encrypt.HmacUtil;
import lombok.SneakyThrows;

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

public class GetRequestData {
    static String  secretKey = "^h$_0j9kwf$y7,@w%;o+f[]-";
    private static final String Algorithm = "DESede";
    private static final String BaseUrl = "https://pbtest.gtja.com/fsdpl-api/api/";

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
    public static String getRequestData(JSONObject jsonObject, String url){
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

    public static int getTotal(JSONObject jsonObject,String url){
        String requestData = getRequestData(jsonObject, url);
        JSONObject resultJsonObject  = JSONObject.parseObject(requestData);
        JSONObject data = resultJsonObject.getObject("data",JSONObject.class);
        // System.out.println(data);
        Integer totalCount = data.getObject("totalCount", Integer.class);
        System.out.println("当前业务总页数为:"+ totalCount);
        return totalCount;
    }
    @SneakyThrows
    public static List<JSONObject> getData(JSONObject jsonObject, String url){
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

}
