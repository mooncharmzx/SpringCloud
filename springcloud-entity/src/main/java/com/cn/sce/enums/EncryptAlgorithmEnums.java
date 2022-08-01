package com.cn.sce.enums;

public enum EncryptAlgorithmEnums {
    AES_128("AES", "AES/ECB/PKCS5Padding", 128),
    DES_128("DES", "DES/ECB/PKCS5Padding", 128),
    RSA_512("RSA", "RSA", 512),
    RSA_1024("RSA", "RSA", 1024),
    RSA_2048("RSA", "RSA", 2048);

    private final String encryptType;
    private final String algorithm;
    private final int size;

    private EncryptAlgorithmEnums(String encryptType, String algorithm, int size) {
        this.encryptType = encryptType;
        this.algorithm = algorithm;
        this.size = size;
    }

    public String getEncryptType() {
        return this.encryptType;
    }

    public int getSize() {
        return this.size;
    }

    public String getAlgorithm() {
        return this.algorithm;
    }
}
