package com.cn.sce.license;

import com.cn.sce.Base64Utils;
import com.cn.sce.enums.EncryptAlgorithmEnums;
import com.cn.sce.enums.EncryptSchemaEnums;
import com.cn.sce.exception.EncryptException;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

public class RsaEncryptHandleImpl extends EncryptHandle {
    public static final String SIGN_ALGORITHMS_SHA1 = "SHA1WithRSA";
    private static final Map<Integer, RsaChunkSize> rsaChunkSizes = new HashMap();
    private final int keySize;

    public static KeyPair generatorKeyPair(String keyAlgorithm, int keySize) throws NoSuchAlgorithmException {
        KeyPairGenerator kpGenerator = KeyPairGenerator.getInstance(keyAlgorithm);
        kpGenerator.initialize(keySize);
        KeyPair keyPair = kpGenerator.generateKeyPair();
        return keyPair;
    }

    public static KeyPair generatorKeyPair(EncryptAlgorithmEnums encryptAlgorithm) throws NoSuchAlgorithmException {
        return generatorKeyPair(encryptAlgorithm.getAlgorithm(), encryptAlgorithm.getSize());
    }

    public static PublicKey loadPublicKey(byte[] publicKeyBytes) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
            return keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException var3) {
            throw new EncryptException("无此算法");
        } catch (InvalidKeySpecException var4) {
            throw new EncryptException("公钥非法");
        } catch (NullPointerException var5) {
            throw new EncryptException("公钥数据为空");
        }
    }

    public static PrivateKey loadPrivateKey(byte[] privateKeyBytes) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            return keyFactory.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException var3) {
            throw new EncryptException("无此算法");
        } catch (InvalidKeySpecException var4) {
            throw new EncryptException("私钥非法");
        } catch (NullPointerException var5) {
            throw new EncryptException("私钥数据为空");
        }
    }

    public RsaEncryptHandleImpl(String algorithm, String encoding, int keySize) {
        super("RSA", algorithm, encoding);
        this.keySize = keySize;
    }

    public RsaEncryptHandleImpl(String algorithm) {
        this(algorithm, "utf-8", 512);
    }

    public RsaEncryptHandleImpl() {
        this("RSA");
    }

    protected Key buildKey(int mode, byte[] secretKey, EncryptSchemaEnums encryptSchema) {
        Key key = null;
        switch(mode) {
            case 1:
                if (encryptSchema == EncryptSchemaEnums.PRIVATEENCRYPT) {
                    key = loadPrivateKey(secretKey);
                } else {
                    key = loadPublicKey(secretKey);
                }
                break;
            case 2:
                if (encryptSchema == EncryptSchemaEnums.PRIVATEENCRYPT) {
                    key = loadPublicKey(secretKey);
                } else {
                    key = loadPrivateKey(secretKey);
                }
        }

        return (Key)key;
    }

    public String encrypt(String sourceStr, String secretKey, EncryptSchemaEnums encryptSchema) {
        byte[] sourceBytes = new byte[0];

        try {
            sourceBytes = sourceStr.getBytes(this.encoding);
            return Base64Utils.encode(this.encrypt(sourceBytes, Base64Utils.decode(secretKey), encryptSchema), this.encoding);
        } catch (UnsupportedEncodingException var6) {
            throw new EncryptException(var6);
        }
    }

    public String decrypt(String encryptStr, String secretKey, EncryptSchemaEnums encryptSchema) {
        try {
            byte[] encryptBytes = Base64Utils.decode(encryptStr, this.encoding);
            return new String(this.decrypt(encryptBytes, Base64Utils.decode(secretKey), encryptSchema), this.encoding);
        } catch (UnsupportedEncodingException var5) {
            throw new EncryptException(var5);
        }
    }

    protected byte[] doFinal(byte[] bytes, int mode, byte[] secretKey, EncryptSchemaEnums encryptSchema) {
        int maxLength = ((RsaChunkSize)rsaChunkSizes.get(this.keySize)).calculateChunkSize(mode);
        int byteLength = bytes.length;
        Cipher cipher = null;

        try {
            cipher = Cipher.getInstance(this.algorithm);
        } catch (Exception var29) {
            throw new EncryptException(var29);
        }

        Key key = this.buildKey(mode, secretKey, encryptSchema);

        try {
            cipher.init(mode, key);
        } catch (InvalidKeyException var28) {
            throw new EncryptException(var28);
        }

        if (byteLength <= maxLength) {
            try {
                return cipher.doFinal(bytes);
            } catch (Exception var27) {
                throw new EncryptException(var27);
            }
        } else {
            int offSet = 0;
            int i = 0;

            try {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                Throwable var13 = null;

                byte[] var14;
                try {
                    while(byteLength - offSet > 0) {
                        byte[] cache;
                        if (byteLength - offSet > maxLength) {
                            cache = cipher.doFinal(bytes, offSet, maxLength);
                        } else {
                            cache = cipher.doFinal(bytes, offSet, byteLength - offSet);
                        }

                        out.write(cache, 0, cache.length);
                        ++i;
                        offSet = i * maxLength;
                    }

                    var14 = out.toByteArray();
                } catch (Throwable var30) {
                    var13 = var30;
                    throw var30;
                } finally {
                    if (out != null) {
                        if (var13 != null) {
                            try {
                                out.close();
                            } catch (Throwable var26) {
                                var13.addSuppressed(var26);
                            }
                        } else {
                            out.close();
                        }
                    }

                }

                return var14;
            } catch (Exception var32) {
                throw new EncryptException(var32);
            }
        }
    }

    public byte[] sign(byte[] bytes, byte[] privateKey) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        PrivateKey pKey = loadPrivateKey(privateKey);
        Signature signature = Signature.getInstance("SHA1WithRSA");
        signature.initSign(pKey);
        signature.update(bytes);
        return signature.sign();
    }

    public boolean verify(byte[] bytes, byte[] publicKey, byte[] signed) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        PublicKey pkey = loadPublicKey(publicKey);
        Signature signature = Signature.getInstance("SHA1WithRSA");
        signature.initVerify(pkey);
        signature.update(bytes);
        return signature.verify(signed);
    }

    static {
        rsaChunkSizes.put(512, new RsaChunkSize(53, 64));
        rsaChunkSizes.put(1024, new RsaChunkSize(117, 128));
        rsaChunkSizes.put(2048, new RsaChunkSize(245, 256));
    }
}
