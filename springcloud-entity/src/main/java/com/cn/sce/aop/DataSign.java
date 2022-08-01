package com.cn.sce.aop;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

public interface DataSign {
    byte[] sign(byte[] var1, byte[] var2) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException;

    boolean verify(byte[] var1, byte[] var2, byte[] var3) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException;
}