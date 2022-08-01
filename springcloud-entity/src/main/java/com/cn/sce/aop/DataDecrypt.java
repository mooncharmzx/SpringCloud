package com.cn.sce.aop;

import com.cn.sce.enums.EncryptSchemaEnums;

public interface DataDecrypt {
    default String decrypt(String encryptStr, String secretKey, EncryptSchemaEnums encryptSchema) {
        return encryptStr;
    }

    default String decrypt(String encryptStr, String secretKey) {
        return this.decrypt(encryptStr, secretKey, EncryptSchemaEnums.PRIVATEENCRYPT);
    }

    default byte[] decrypt(byte[] encryptBytes, byte[] secretKey, EncryptSchemaEnums encryptSchema) {
        return encryptBytes;
    }

    default byte[] decrypt(byte[] encryptBytes, byte[] secretKey) {
        return this.decrypt(encryptBytes, secretKey, EncryptSchemaEnums.PRIVATEENCRYPT);
    }
}
