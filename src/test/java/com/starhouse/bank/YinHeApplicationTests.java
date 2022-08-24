package com.starhouse.bank;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSONObject;
import com.starhouse.bank.moudles.yinhe.queryProductNetValue.pageInfo;
import com.starhouse.bank.moudles.yinhe.queryProductNetValue.entity.QueryProductNetValue;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import sun.security.jca.ProviderList;
import sun.security.jca.Providers;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author vdi.zhoul
 * @description: java api调用案例 只利用jdk的相关类实现
 * @date 2021/9/13 17:58
 */
@SpringBootTest
public class YinHeApplicationTests {
    //生成随机字符串的可选字符列表
    private static final String CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    //应用ID
    private static final String APP_ID = "P1000568";
    //应用密钥
    private static final String APP_KEY = "GvMokvINVIBBBl6N";
    //服务名
    private static final String SERVICE_NAME = "FMP";
    //签名字符串加密秘钥
    private static final String SECRET_KEY = "KtILNFHAWj10PVbK";
    //
    private static final String FILE_ENCODE = "UTF-8";
    //获取token接口地址
    private static final String TOKEN_URL = "https://fmp-tst.chinastock.com.cn:28980/login";
    //分红查询
    //private static final String SERVICE_URL = "https://fmp-tst.chinastock.com.cn:28980/fmp-shareRegistration/manager/queryDividendInfo";
    //投资者份额余额查询
    private static final String SERVICE_URL = "https://fmp-tst.chinastock.com.cn:28980/fmp-shareRegistration/manager/queryShareBalance";
    private static final String BASEURL = "https://fmp-tst.chinastock.com.cn:28980/";
    private static String accessToken = "";
    public static void main(String[] args) {

        //获取accessToken信息
        String accessToken = login();

        System.out.println("[accessToken]:" + accessToken);

    }

    /**
     * 调用登录接口获取 token
     *
     * @return
     */
    private static String login() {
        Map<String, String> headers = new HashMap<>(8);
        headers.put("Content-Type", "application/json;charset=UTF-8");
        headers.put("timestamp", String.valueOf(System.currentTimeMillis()));
        headers.put("Request-Sn", randomString(CHARS, 24));
        String body = "{\"appId\":\"" + APP_ID + "\",\"appKey\":\"" + APP_KEY + "\",\"serviceName\":\"" + SERVICE_NAME + "\"}";

        try {
            String result = sendPost(TOKEN_URL, headers, body);
            System.out.println(result);
            String regStr = "(\"accessToken\":\")([\\w.-]+)\"";
            Matcher matcher = Pattern.compile(regStr).matcher(result);
            while (matcher.find()) {
                accessToken = matcher.group(2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return accessToken;
    }

    public  String getRequestData(String url, JSONObject bodyJson){
        if (accessToken.length() == 0){
            login();
        }
        String signMsg = "";
        String path = url;
        String requestSn = randomString(CHARS, 24);
        String timestamp = String.valueOf(System.currentTimeMillis());

        //投资者份额余额查询
        String body = bodyJson.toJSONString();

        /**
         * 构成签名字符串 构成规则如下 （请求方法类型+换行+请求路径+换行+随机数+换行+时间戳+换行+请求体+换行）
         * 签名原始字符串 = HTTP Method + ‘\n’ + HTTP URI + ‘\n’ + requestSn + ‘\n’
         * timestamp + ‘\n’ + Request Body + ‘\n’
         */
        signMsg = "POST" + "\n" + path + "\n" + requestSn + "\n" + timestamp + "\n" + body + "\n";
        Map<String, String> headers = new HashMap<>(16);
        //生成一个签名sign
        headers.put("X-HMAC-SIGNATURE", hmacSha256(signMsg, SECRET_KEY));
        headers.put("Content-Type", "application/json;charset=UTF-8");
        headers.put("X-HMAC-ALGORITHM", "hmac-sha256");
        headers.put("X-HMAC-ACCESS-KEY", "user-key");
        headers.put("Access-Token", accessToken);
        headers.put("timestamp", timestamp);
        //headers.put("Host", "fmp-moneytransfer.tsa2.chinastock.com.cn");
        headers.put("Request-Sn", requestSn);
        headers.put("appId", APP_ID);
        String result = HttpRequest.post(BASEURL)
                .addHeaders(headers)
                .timeout(80000)//超时，毫秒
                .execute().body();
        return result;
    }

    public int getServiceTotal(String url,JSONObject bodyJson){
        String requestData = getRequestData(url, bodyJson);
        JSONObject jsonObject = JSONObject.parseObject(requestData);
        pageInfo pageInfo = jsonObject.getObject("pageInfo", pageInfo.class);
        int total = pageInfo.getTotal();
        return total;
    }
    public List<JSONObject> getData(String url,JSONObject bodyJson){
        int serviceTotal = getServiceTotal(url, bodyJson);
        List<JSONObject> list = new ArrayList<>();
        for (int i = 1; i <= serviceTotal; i++) {
            bodyJson.put("pageNo",i);
            String requestData = getRequestData(url, bodyJson);
            JSONObject resultJsonObject  = JSONObject.parseObject(requestData);
            List<JSONObject> data = resultJsonObject.getObject("data", List.class);
            list.addAll(data);
        }
        return list;


    }

    @Test
    public void test(){
        JSONObject bodyJson = new JSONObject();
        bodyJson.put("pageLength",10);
        bodyJson.put("pageNo",1);
        bodyJson.put("paging","1");
        List<JSONObject> list = getData("queryProductNetValue", bodyJson);
        System.out.println("获取到的数据条数:" + list.size());
        List<QueryProductNetValue> voList = new ArrayList<>();
        for (JSONObject object : list) {
            QueryProductNetValue queryProductNetValue = object.toJavaObject(QueryProductNetValue.class);
            queryProductNetValue.setCompany("银河证券");
            voList.add(queryProductNetValue);
        }
        System.out.println(voList.size());
    }



    /**
     * 发送post请求
     *
     * @param urlStr  url地址
     * @param headers 请求头
     * @param body    请求体
     * @return
     * @throws IOException
     */
    private static String sendPost(String urlStr, Map<String, String> headers, String body) throws IOException {
        StringBuffer stringBuffer;
        HttpURLConnection httpUrlConnection = null;
        try {
            //封装路径信息
            URL url = new URL(urlStr);
            //建立连接
            URLConnection urlConnection = url.openConnection();
            //连接对象类型转换
            httpUrlConnection = (HttpURLConnection) urlConnection;

            if (headers != null && !headers.isEmpty()) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    httpUrlConnection.addRequestProperty(entry.getKey(), entry.getValue());
                }
            }
            //设定POST请求方法
            httpUrlConnection.setRequestMethod("POST");
            //开启post请求的输出功能
            httpUrlConnection.setDoOutput(true);
            //设置连接超时时间
            httpUrlConnection.setConnectTimeout(5 * 1000);
            //设置读取超时时间
            httpUrlConnection.setReadTimeout(3 * 1000);
            //写入body数据
            if (null != body) {
                httpUrlConnection.getOutputStream().write(body.getBytes());
            }
            int responseCode = httpUrlConnection.getResponseCode();
            System.out.println("[responseCode]：" + responseCode);
            //获取字节输入流信息
            InputStream inputStream;
            if (responseCode == 200) {
                inputStream = httpUrlConnection.getInputStream();
            } else {
                inputStream = httpUrlConnection.getErrorStream();
            }
            //字节流转换成字符流
            InputStreamReader inReader = new InputStreamReader(inputStream, FILE_ENCODE);
            //把reader中的数据读取到缓冲的chars中
            char[] chars = new char[1 * 1024];
            int readCount;
            //用StringBuffer来接收char
            stringBuffer = new StringBuffer();
            while ((readCount = inReader.read(chars)) != -1) {
                stringBuffer.append(chars, 0, readCount);
            }
            return stringBuffer.toString();
        } catch (IOException e) {
            //异常时关闭连接
            if (null != httpUrlConnection) {
                httpUrlConnection.disconnect();
            }
            throw e;
        }
    }

    /**
     * 获得一个随机的字符串
     *
     * @param baseString 随机字符选取的样本
     * @param length     字符串的长度
     * @return 随机字符串
     */
    public static String randomString(String baseString, int length) {
        if (baseString == null || baseString.length() == 0) {
            return "";
        }
        final StringBuilder sb = new StringBuilder(length);

        if (length < 1) {
            length = 1;
        }
        int baseLength = baseString.length();
        while (sb.length() < length) {
            //利用ThreadLocalRandom提升多线程环境下的获取随机数的性能
            int number = ThreadLocalRandom.current().nextInt(baseLength);
            sb.append(baseString.charAt(number));
        }
        return sb.toString();
    }

    private static volatile Mac macInstance;

    /**
     * 按照 Hmac算法 生成一个SHA256加密字符串
     *
     * @param data 加密的字符串
     * @param key  加密的秘钥
     * @return 加密后的字符串
     * @throws Exception
     */
    public static String hmacSha256(String data, String key) {
        try {
            byte[] secKeyBytes = key.getBytes(FILE_ENCODE);
            //根据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称
            SecretKey secretKey = new SecretKeySpec(secKeyBytes, "HmacSHA256");
            //生成一个指定Mac算法的Mac对象
            macInstance = getMacInstance();
            //用给定密钥初始化Mac对象
            macInstance.init(secretKey);
            byte[] text = data.getBytes(FILE_ENCODE);
            //完成 Mac操作 并用base64进行编码
            return Base64.getEncoder().encodeToString(macInstance.doFinal(text));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * 获取一个单例的Mac（双端模式）
     *
     * @return
     */
    public static Mac getMacInstance() {
        if (macInstance == null) {
            synchronized (YinHeApplicationTests.class) {
                if (macInstance == null) {
                    try {
                        macInstance = Mac.getInstance("HmacSHA256");
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return macInstance;
    }


    public void testProvider() {
        ProviderList list = Providers.getProviderList();
        for (Provider provider : list.providers()) {
            for (Object o : provider.keySet()) {
                if (o.toString().toUpperCase().indexOf("HMACSHA256") > -1) {
                    System.out.println(o);
                    System.out.println("[" + provider.getInfo() + "]");
                }
            }
        }
    }
}
