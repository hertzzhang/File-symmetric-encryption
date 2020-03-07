package test;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.nio.charset.Charset;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class TestFile {

    static  final  String SECRETTYPE="AES";
    static  final  String charsetname="UTF-8";
    static  Charset  charest=Charset.forName(charsetname);//字节流传输格式
    //生成key
    public static SecretKey secretKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator  = KeyGenerator.getInstance(SECRETTYPE);//声明加密方式
        SecureRandom secureRandom =  new SecureRandom();//随机函数
        keyGenerator.init(secureRandom);//随机初始化密匙
        SecretKey secretKey =  keyGenerator.generateKey();//生成key
        return  secretKey;
    }

    //加密字符串
    public  static  byte[]  encrypt(String content,SecretKey secretKey) throws Exception {
        return  aes(content.getBytes(charest),Cipher.ENCRYPT_MODE,secretKey);

    }
    //解密字符串
    public static String decrypt(byte[] coontentByte,SecretKey secretKey) throws Exception {

         byte[] result =   aes(coontentByte,Cipher.DECRYPT_MODE,secretKey);
         return  new String(result,charsetname);
    }

    /*
    * contentbyte:需要传入的字节数组-----加密或者解密
    * mode：模式，需要加密还是解密
    * secretKey:密匙
    * */
    private static byte[] aes(byte[] contentByte,int mode,SecretKey secretKey) throws Exception {
        Cipher cipher =  Cipher.getInstance(SECRETTYPE);//声明加密方式
        cipher.init(mode,secretKey);//初始化(说明时加密还是解密，并且给与密匙)
        byte[] result =cipher.doFinal(contentByte);
        return  result;
    }

    public static void main(String[] args) throws Exception {
        String content ="你好，水水水水aaa";
        SecretKey secretKey=secretKey();
        System.out.println("密匙："+secretKey);
        byte[] result1 =  encrypt(content,secretKey);
        System.out.println("加密后的字符串是："+new String(result1,charsetname));
        String result2 = decrypt(result1,secretKey);
        System.out.println("解密后的字符串是："+result2);

    }
}
