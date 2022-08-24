package com.starhouse.bank.encrypt;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class HmacUtil {

    public static final String HMAC_SHA1 = "HmacSHA1";
    public static final String HMAC_MD5 = "HmacMD5";
    public static final String HMAC_SHA256 = "HmacSHA256";
    public static final String HMAC_SHA512 = "HmacSHA512";


    public static String encrypt(String input, String key, String algorithm) {
        String cipher = "";
        try {
            byte[] data = key.getBytes(StandardCharsets.UTF_8);
            //根据给定的字节数组构造一个密钥，第二个参数指定一个密钥的算法名称，生成HmacSHA1专属密钥
            SecretKey secretKey = new SecretKeySpec(data, algorithm);

            //生成一个指定Mac算法的Mac对象
            Mac mac = Mac.getInstance(algorithm);
            //用给定密钥初始化Mac对象
            mac.init(secretKey);
            byte[] text = input.getBytes(StandardCharsets.UTF_8);
            byte[] encryptByte = mac.doFinal(text);
            cipher = Base64.getEncoder().encodeToString(encryptByte);
            // cipher = Base64.encode(encryptByte);
            // cipher = bytesToHexStr(encryptByte);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
        }
        return cipher;
    }


    public static String bytesToHexStr(byte[] bytes) {
        StringBuilder hexStr = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(b & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            hexStr.append(hex);
        }
        return hexStr.toString();
    }
}
