package com.cn.sce;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.util.Base64Utils;
import sun.misc.BASE64Decoder;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import static com.cn.sce.BaseCoderUtil.byteArr2HexStr;

public class RSAUtil {

    public static String privateKey="MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJmFdr7ZgrLB9UWawQuKavtvei/WUQLrSKx8mb0sXkmaDG4gUuFDvHqNDcFTyYhiYmqPFUyZ6JkMt9y7p/GQ6Cl+TY+AChnpL0E7YKZPYxASmtqQ8WKU1ko4nwUChGCaeJ4QAME8PK+Ct+LJLuFPk5E8zhmiM8n151m9mV2Wm5tDAgMBAAECgYEAgOWXArx7SP0iovNBHCB2nG9HpryJviqwFOp05mlHLQLjZt9K1wnZSQf/QY/IyPLZ8N/7oRokOcR85x7D7kWdNlLih0o37MQ2fCi7qcnGYN5fnV9WzBlJexZUbcTuV8nCa8DWbweO/0gAqitacXrvBPpeRtZHgKO0x88sx2CTyHkCQQDa9skyfWpjDvMNQiwQJYPjo9x+QHEuE26oUiV60sifsZjy0qraF3TTSrvZxJ1MWEBv2zqAZds3L6Y+A6n1NndXAkEAs3z9Js/6fOdmWAdq633B5/6QKzoxOkuRTp6Y6Lz+UZJ7etxJ/Pk6L/y8Dzrv8QKtZpgUFoxyAymFPn6ZjBmj9QJAcHh46i1PeyhPogzOU1DhyQj/Ff6F5DPpEB55z/f7Q0PXG0sopTONN4bYrMf4QCHIn2wNRayMCHG6Q3pgT2s1qwJAY47w3JaAWsf//NQgen9HD2gkj9W3eJ5x+yn9pxst9hFKbNvELo4PuI53wfrvHMBfbojsm5VLqHVfc0rF4PBYAQJAKNuImaPP1oGMO4jdj6IX0390MeagNpRbkP/HCyW18rHDENHQQ9oiB2QLn/oM1qPwFQ+OD15GUSVe6i1gdh7kBw==\n" +
            "lpmEhwlWq2FFVfzG8Ox0cD1ELvOK-Vh99gTmcV3TZHBNXnX0o6cidZkzXXU80IyXQC61VmRMjeg2U__84-pPx1VuGhV6derBxoJFEwxDwok8sdpKpC-yWvORQa5Y4Y5Iax_WuV8OA4GPGHiLiVYTjFWqWh4TvZ-kgyIHBljYlT0";
    public static String publicKey="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCZhXa+2YKywfVFmsELimr7b3ov1lEC60isfJm9LF5JmgxuIFLhQ7x6jQ3BU8mIYmJqjxVMmeiZDLfcu6fxkOgpfk2PgAoZ6S9BO2CmT2MQEprakPFilNZKOJ8FAoRgmnieEADBPDyvgrfiyS7hT5ORPM4ZojPJ9edZvZldlpubQwIDAQAB";
    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;

    /**
     * 加密算法RSA
     */
    public static final String KEY_ALGORITHM = "RSA";

    /**
     * 加密方式，标准jdk的
     */
    public static final String TRANSFORMATION = "RSA/ECB/PKCS1Padding";

    public static void main(String[] args) throws Exception {

        String abc="123456";

        String encrypt = encrypt(abc, publicKey);

        System.out.println(encrypt);


        System.out.println("-----");


        String decrypt = decrypt(encrypt, privateKey);


        System.out.println(decrypt);

    }

    public static void genKeyPair() throws NoSuchAlgorithmException {
        // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        // 初始化密钥对生成器，密钥大小为96-1024位
        keyPairGen.initialize(1024,new SecureRandom());
        // 生成一个密钥对，保存在keyPair中
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();   // 得到私钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();  // 得到公钥
        String publicKeyString = new String(Base64.encodeBase64(publicKey.getEncoded()));
        // 得到私钥字符串
        String privateKeyString = new String(Base64.encodeBase64((privateKey.getEncoded())));
        // 将公钥和私钥保存到Map

        System.out.println(publicKeyString);

        System.out.println("-----");

        System.out.println(privateKeyString);
    }

    /**
     * RSA公钥加密
     *
     * @param str
     *            加密字符串
     * @param publicKey
     *            公钥
     * @return 密文
     * @throws Exception
     *             加密过程中的异常信息
     */
    public static String encrypt( String str, String publicKey ) throws Exception{
        //base64编码的公钥
        byte[] decoded = Base64.decodeBase64(publicKey);
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
        //RSA加密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        String outStr = Base64.encodeBase64String(cipher.doFinal(str.getBytes("UTF-8")));
        return outStr;
    }

    /**
     * RSA私钥解密
     *
     * @param str
     *            加密字符串
     * @param privateKey
     *            私钥
     * @return 铭文
     * @throws Exception
     *             解密过程中的异常信息
     */
    public static String decrypt(String str, String privateKey) throws Exception{
        //64位解码加密后的字符串
        byte[] inputByte = Base64.decodeBase64(str.getBytes("UTF-8"));
        //base64编码的私钥
        byte[] decoded = Base64.decodeBase64(privateKey);
        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
        //RSA解密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, priKey);
        String outStr = new String(cipher.doFinal(inputByte));
        return outStr;
    }

    /**
     * 私钥解密（不推荐使用，请使用decryptByPrivateKey方法）
     * @param ciphertext 密文
     * @param privatekey 私钥
     * @return 解密内容
     * @throws Exception
     */
    public static byte[] decryptPri(byte[] ciphertext, byte[] privatekey) throws Exception {

        return decryptByPrivateKey(ciphertext, com.cn.sce.Base64Utils.encode(privatekey));
    }

    public static byte[] decryptByPrivateKey(byte[] encryptedData, String privateKey)
            throws GeneralSecurityException,IOException {
        byte[] keyBytes = Base64Utils.decode(privateKey.getBytes());
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateK);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }

    public static String encode(String data, String charset,String public_key){
        byte[] datas;
        try {
            datas = data.getBytes(charset);
            byte[] res = encryptByPublicKey(datas,public_key);
            return byteArr2HexStr(res);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 加密<br>
     * 用公钥加密
     *
     * @param data 待加密字节
     * @return byte[]
     */
    private static byte[] encryptByPublicKey(byte[] data,String public_key){
        //对数据加密
        Cipher cipher;
        try {
            PublicKey publicKey = getPublicKey(public_key);
            cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] encryptedData;
            int inputLen = data.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段加密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                    cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(data, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_ENCRYPT_BLOCK;
            }
            encryptedData = out.toByteArray();
            out.close();
            return encryptedData;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * String转公钥PublicKey
     *
     * @param key
     * @return
     * @throws
     */
    public static PublicKey getPublicKey(String key) throws Exception {
//        byte[] data = decode(key.getBytes());
//        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(Base64Utils.decodeFromString(
//                "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCADv22t2c1G90oEJn7NoeO/cax785jjlpZ9cZl2nkbv0cAWr1iISFXsqPwHO8VEVchf5QHuUMXjCLR9ev4vUmBMz5y34UVsbBmmYfp2niHMx4ivNhjBd7DZ7ollZ9BVi+LXQyd8ugoiSl+OrbiLj/PGaAJKxrB7+hA6tdadyZG8wIDAQAB"
//        ));
//        KeyFactory keyFactory = KeyFactory.getInstance(CODE);
//        PublicKey publicKe = keyFactory.generatePublic(x509KeySpec);
//        return publicKe;

        byte[] keyBytes;
        keyBytes = (new BASE64Decoder()).decodeBuffer(key);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }
}
