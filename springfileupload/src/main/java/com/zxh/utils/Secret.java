package com.zxh.utils;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import javax.crypto.*;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Secret {

    static  final String SECRETTYPE="AES";
    static  final String  charsetName = "UTF-8";
    public static SecretKey secretKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator =KeyGenerator.getInstance(SECRETTYPE);
        SecureRandom secureRandom =  new SecureRandom();
        keyGenerator.init(secureRandom);
        return  keyGenerator.generateKey();
    }
    public static MultipartFile encrypt(MultipartFile file,SecretKey secretKey) throws Exception {
        byte[] bytes = aes(file.getBytes(),Cipher.ENCRYPT_MODE,secretKey);
        InputStream inputStream = new ByteArrayInputStream(bytes);
        MultipartFile file1 = new MockMultipartFile(file.getName(), inputStream);
        return file1;

    }

    public static MultipartFile decrypt(MultipartFile file,SecretKey secretKey) throws Exception {

        byte[] bytes = aes(file.getBytes(),Cipher.DECRYPT_MODE,secretKey);

        InputStream inputStream = new ByteArrayInputStream(bytes);

        MultipartFile file1 = new MockMultipartFile("name", inputStream);
        return file1;
    }

    public static byte[] aes(byte[] contenttype,int mode,SecretKey secretKey) throws Exception {
        Cipher cipher =  Cipher.getInstance(SECRETTYPE);//声明加密方式
        cipher.init(mode,secretKey);//初始化(说明时加密还是解密，并且给与密匙)
        byte[] result =cipher.doFinal(contenttype);
        return  result;
    }





}
