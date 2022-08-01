package com.cn.sce.factory;

import com.cn.sce.license.AesEncryptHandleImpl;
import com.cn.sce.license.DesEncryptHandleImpl;
import com.cn.sce.license.EncryptHandle;
import com.cn.sce.license.RsaEncryptHandleImpl;

import java.util.concurrent.ConcurrentHashMap;

public final class EncoderFactory {
    private static ConcurrentHashMap<String, EncryptHandle> handles = new ConcurrentHashMap();

    private EncoderFactory() {
    }

    public static EncryptHandle getInstance(String algorithm) {
        if (!handles.containsKey(algorithm)) {
            if ("RSA".equals(algorithm)) {
                handles.put(algorithm, new RsaEncryptHandleImpl());
            } else if ("AES".equals(algorithm)) {
                handles.put(algorithm, new AesEncryptHandleImpl());
            } else if ("DES".equals(algorithm)) {
                handles.put(algorithm, new DesEncryptHandleImpl());
            }
        }

        return (EncryptHandle)handles.get(algorithm);
    }
}
