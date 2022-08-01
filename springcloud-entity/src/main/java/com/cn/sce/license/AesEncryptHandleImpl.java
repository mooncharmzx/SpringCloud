package com.cn.sce.license;

import com.cn.sce.enums.EncryptSchemaEnums;
import com.cn.sce.exception.EncryptException;

import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class AesEncryptHandleImpl extends EncryptHandle {
    private final String keySeed;

    public AesEncryptHandleImpl(String keySeed, String algorithm, String encoding) {
        super("AES", algorithm, encoding);
        this.keySeed = keySeed;
    }

    public AesEncryptHandleImpl(String algorithm) {
        this((String) null, algorithm, "utf-8");
    }

    public AesEncryptHandleImpl() {
        this((String) null, "AES/ECB/PKCS5Padding", "utf-8");
    }

    protected Key buildKey(int mode, byte[] secretKey, EncryptSchemaEnums encryptSchema) {
        return new SecretKeySpec(secretKey, this.cryptoType);
    }

    public byte[] generateKey() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            if (this.keySeed != null && this.keySeed.length() > 0) {
                keyGenerator.init(128, new SecureRandom(this.keySeed.getBytes(this.encoding)));
            } else {
                keyGenerator.init(128);
            }

            return keyGenerator.generateKey().getEncoded();
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException var2) {
            throw new EncryptException(var2);
        }
    }
}
