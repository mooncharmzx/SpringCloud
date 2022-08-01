package com.cn.sce.aop;

import com.cn.sce.enums.EncryptSchemaEnums;

public interface DataEncrypt {
    default String encrypt(String sourceStr, String secretKey, EncryptSchemaEnums encryptSchema) {
        return sourceStr;
    }

    default String encrypt(String sourceStr, String secretKey) {
        return this.encrypt(sourceStr, secretKey, EncryptSchemaEnums.PRIVATEENCRYPT);
    }

    default byte[] encrypt(byte[] sourceBytes, byte[] secretKey, EncryptSchemaEnums encryptSchema) {
        return sourceBytes;
    }

    default byte[] encrypt(byte[] sourceBytes, byte[] secretKey) {
        return this.encrypt(sourceBytes, secretKey, EncryptSchemaEnums.PRIVATEENCRYPT);
    }
}
