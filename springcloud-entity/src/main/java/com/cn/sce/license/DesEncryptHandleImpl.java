package com.cn.sce.license;

import com.cn.sce.enums.EncryptSchemaEnums;
import com.cn.sce.exception.EncryptException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class DesEncryptHandleImpl extends EncryptHandle {
    public DesEncryptHandleImpl(String algorithm, String encoding) {
        super("DES", algorithm, encoding);
    }

    public DesEncryptHandleImpl(String algorithm) {
        this(algorithm, "utf-8");
    }

    public DesEncryptHandleImpl() {
        this("DES/ECB/PKCS5Padding", "utf-8");
    }

    protected Key buildKey(int mode, byte[] secretKey, EncryptSchemaEnums encryptSchema) {
        DESKeySpec des = null;

        try {
            des = new DESKeySpec(secretKey);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(this.cryptoType);
            return keyFactory.generateSecret(des);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | InvalidKeyException var6) {
            throw new EncryptException(var6);
        }
    }
}
