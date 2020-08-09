package com.amy.utils;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.spec.AlgorithmParameterSpec;

public class AESDecodeUtils {

    public static String decrypt(String encrypData,String iv,String sessionKey) throws Exception {
        byte[] encrypDataByte = Base64.decodeBase64(encrypData);
        byte[] ivDataByte = Base64.decodeBase64(iv);
        byte[] sessionKeyByte = Base64.decodeBase64(sessionKey);
        String decryptStr=decrypt(sessionKeyByte,ivDataByte,encrypDataByte);
        System.out.println(decryptStr);
        return decryptStr;
    }
 
    public static String decrypt(byte[] key, byte[] iv, byte[] encData) throws Exception {
        AlgorithmParameterSpec ivSpec = new IvParameterSpec(iv);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
        //解析解密后的字符串
        return new String(cipher.doFinal(encData),"UTF-8");
    }
}
