package com.cn.sce.license;

import java.io.Serializable;

public class RsaChunkSize implements Serializable {
    private int encryptSize;
    private int decryptSize;

    public RsaChunkSize(int encryptSize, int decryptSize) {
        this.encryptSize = encryptSize;
        this.decryptSize = decryptSize;
    }

    public int getEncryptSize() {
        return this.encryptSize;
    }

    public void setEncryptSize(int encryptSize) {
        this.encryptSize = encryptSize;
    }

    public int getDecryptSize() {
        return this.decryptSize;
    }

    public void setDecryptSize(int decryptSize) {
        this.decryptSize = decryptSize;
    }

    public int calculateChunkSize(int mode) {
        int chunkSize;
        switch(mode) {
            case 1:
            default:
                chunkSize = this.encryptSize;
                break;
            case 2:
                chunkSize = this.decryptSize;
        }

        return chunkSize;
    }
}
