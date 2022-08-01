package com.cn.sce.builder;

import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MessageDigestBuilder {
    private static final Logger log = LoggerFactory.getLogger(MessageDigestBuilder.class);
    public static final String FAMILY_HMAC = "HMAC";
    public static final String ERROR_BUILD = "构建摘要异常";
    private MessageDigestBuilder.DigestAlgorithm digestAlgorithm;
    private byte[] secretkey;

    public static MessageDigestBuilder getInstance() {
        return new MessageDigestBuilder();
    }

    private MessageDigestBuilder() {
    }

    public MessageDigestBuilder setDigestAlgorithm(MessageDigestBuilder.DigestAlgorithm digestAlgorithm) {
        this.digestAlgorithm = digestAlgorithm;
        return this;
    }

    public byte[] getSecretkey() {
        return this.secretkey;
    }

    public MessageDigestBuilder generateSecretkey() {
        if (this.digestAlgorithm != null && this.digestAlgorithm.getFamily().equals("HMAC")) {
            KeyGenerator keyGenerator = null;

            try {
                keyGenerator = KeyGenerator.getInstance(this.digestAlgorithm.getAlgorithm());
            } catch (NoSuchAlgorithmException var3) {
                log.error("实例化密钥生成器异常", var3);
            }

            if (keyGenerator != null) {
                SecretKey secretKey = keyGenerator.generateKey();
                this.secretkey = secretKey.getEncoded();
            }

            return this;
        } else {
            log.warn("nosupport");
            return this;
        }
    }

    public MessageDigestBuilder setSecretkey(byte[] secretkey) {
        this.secretkey = secretkey;
        return this;
    }

    public String build(byte[] bytes) {
        byte[] buff = null;
        if ("HMAC".equals(this.digestAlgorithm.getFamily())) {
            try {
                SecretKey restoreSecretKey = new SecretKeySpec(this.secretkey, this.digestAlgorithm.getAlgorithm());
                Mac mac = Mac.getInstance(restoreSecretKey.getAlgorithm());
                mac.init(restoreSecretKey);
                buff = mac.doFinal(bytes);
            } catch (InvalidKeyException | NoSuchAlgorithmException var6) {
                log.error("构建摘要异常", var6);
            }
        } else {
            try {
                MessageDigest md = MessageDigest.getInstance(this.digestAlgorithm.getAlgorithm());
                md.update(bytes);
                buff = md.digest();
            } catch (NoSuchAlgorithmException var5) {
                log.error("构建摘要异常", var5);
            }
        }

        return buff == null ? "" : Hex.encodeHexString(buff);
    }

    public String build(String content, String charset) {
        try {
            return this.build(content.getBytes(charset));
        } catch (UnsupportedEncodingException var4) {
            log.error("构建摘要异常", var4);
            return "";
        }
    }

    public String build(String content) {
        return this.build(content, "utf-8");
    }

    public static enum DigestAlgorithm {
        MD5("MD5", "MD"),
        SHA1("SHA-1", "SHA"),
        SHA256("SHA-256", "SHA"),
        SHA512("SHA-512", "SHA"),
        HMACMD5("HmacMD5", "HMAC"),
        HMACSHA1("HmacSHA1", "HMAC"),
        HMACSHA256("HmacSHA256", "HMAC"),
        HMACSHA512("HmacSHA512", "HMAC");

        private final String algorithm;
        private final String family;

        private DigestAlgorithm(String algorithm, String family) {
            this.algorithm = algorithm;
            this.family = family;
        }

        public String getAlgorithm() {
            return this.algorithm;
        }

        public String getFamily() {
            return this.family;
        }
    }
}

