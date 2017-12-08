package com.huione.im.api.common;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


public class SystemEncoder {

    public static final String ALGORITHM = "AES";
    public static final String RANDOM_ALGORITHM = "SHA1PRNG";


    //选择算法,根据密码生成密钥
    public static SecretKey genKey(String password) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
        SecureRandom random = SecureRandom.getInstance(RANDOM_ALGORITHM);
        random.setSeed(password.getBytes());//设置密钥
        keyGenerator.init(random);
        return keyGenerator.generateKey();
    }

    //以base64编码保存密钥
    public static void saveKey(Key key, String path) throws IOException {
        Path keyPath = Paths.get(path);
        Files.write(keyPath, Base64.getEncoder().encode(key.getEncoded()));
    }

    //读取密钥
    public static SecretKey readSecretKey(String path) throws Exception {
        Path keyPath = Paths.get(path);
        byte[] keyBytes = Files.readAllBytes(keyPath);
        return new SecretKeySpec(Base64.getDecoder().decode(keyBytes), ALGORITHM);
    }


    public static String encrypt(String content, SecretKey secretKey) throws Exception {
        //指定算法创建Cipher实例
        Cipher cipher = Cipher.getInstance(ALGORITHM);//算法是AES
        //选择模式，结合密钥初始化Cipher实例
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        //加密
        byte[] result = cipher.doFinal(content.getBytes());
        //使用Base64对密文进行转码
        String base64Result = Base64.getEncoder().encodeToString(result);
        return base64Result;
    }


    public static String decrpyt(String content, SecretKey secretKey) throws Exception {
        //获取实例
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        //初始化
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        //转码
        byte[] encodedBytes = Base64.getDecoder().decode(content.getBytes());
        //解密
        byte[] result = cipher.doFinal(encodedBytes);
        return new String(result);
    }
    
    public static String encrypt(String content) throws Exception {
    	SecretKey key = genKey(Constants.PASS_KEY);
    	String encryptContent = encrypt(content, key);
    	return encryptContent;
    }
    
    public static String decrpyt(String content) throws Exception {
    	SecretKey key = genKey(Constants.PASS_KEY);
    	String decrpytContent = decrpyt(content, key);
    	return decrpytContent;
    }

    public static void main(String[] args) {
        try {
            //确保目录存在
            File f = new File("/home/duoyi/encrypt/");
            f.mkdirs();

            String content = Constants.PASS_TOKEN;
            String password = Constants.PASS_KEY;

            SecretKey key = genKey(password);

//            saveKey(key, "/home/duoyi/encrypt/aes.key");

            String encryptBase64 = encrypt(content, key);

//            SecretKey readKey = readSecretKey("/home/duoyi/encrypt/aes.key");
            String result = decrpyt(encryptBase64, key);
            System.out.println("密文为:" + encrypt(content));
            System.out.println("密文为:" + URLEncoder.encode(encrypt(content)));
            System.out.println("密文为:" + new String(Base64.getDecoder().decode(encryptBase64.getBytes())));
            System.out.println("密文转码后为:" + encryptBase64);
            System.out.println("转码后解码为:" + result);
            
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

}