package com.cn.sce.license;

import com.cn.sce.Base64Utils;
import com.cn.sce.aop.DataDecrypt;
import com.cn.sce.aop.DataEncrypt;
import com.cn.sce.aop.DataSign;
import com.cn.sce.enums.EncryptSchemaEnums;
import com.cn.sce.exception.EncryptException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

public abstract class EncryptHandle implements DataEncrypt, DataDecrypt, DataSign {
    protected final String cryptoType;
    protected final String algorithm;
    protected final String encoding;

    public EncryptHandle(String cryptoType, String algorithm, String encoding) {
        this.cryptoType = cryptoType;
        this.algorithm = algorithm;
        this.encoding = encoding;
    }

    protected abstract Key buildKey(int var1, byte[] var2, EncryptSchemaEnums var3);

    protected byte[] doFinal(byte[] bytes, int mode, byte[] secretKey, EncryptSchemaEnums encryptSchema) {
        try {
            Cipher cipher = Cipher.getInstance(this.algorithm);
            cipher.init(mode, this.buildKey(mode, secretKey, encryptSchema));
            return cipher.doFinal(bytes);
        } catch (BadPaddingException | NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | IllegalBlockSizeException var6) {
            throw new EncryptException(var6);
        }
    }

    public byte[] encrypt(byte[] sourceBytes, byte[] secretKey, EncryptSchemaEnums encryptSchema) {
        return this.doFinal(sourceBytes, 1, secretKey, encryptSchema);
    }

    public String encrypt(String sourceStr, String secretKey, EncryptSchemaEnums encryptSchema) {
        try {
            byte[] sourceBytes = sourceStr.getBytes(this.encoding);
            return Base64Utils.encode(this.encrypt(sourceBytes, secretKey.getBytes(this.encoding), encryptSchema), this.encoding);
        } catch (UnsupportedEncodingException var5) {
            throw new EncryptException(var5);
        }
    }

    public String decrypt(String encryptStr, String secretKey, EncryptSchemaEnums encryptSchema) {
        try {
            byte[] encryptBytes = Base64Utils.decode(encryptStr, this.encoding);
            return new String(this.decrypt(encryptBytes, secretKey.getBytes(this.encoding), encryptSchema), this.encoding);
        } catch (UnsupportedEncodingException var5) {
            throw new EncryptException(var5);
        }
    }

    public byte[] decrypt(byte[] encryptBytes, byte[] secretKey, EncryptSchemaEnums encryptSchema) {
        return this.doFinal(encryptBytes, 2, secretKey, encryptSchema);
    }

    public byte[] sign(byte[] bytes, byte[] privateKey) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        return new byte[0];
    }

    public boolean verify(byte[] bytes, byte[] publicKey, byte[] signed) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        return false;
    }
}
