package com.cn.sce.license;

import com.cn.sce.DateTime;
import com.cn.sce.entity.LicenseInfo;
import com.cn.sce.exception.LicenseResoleException;
import com.cn.sce.factory.EncoderFactory;
import com.cn.sce.factory.JsonMapperFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

public class ComponentLicense {
    private static final String ERROR_RELOVER = "证书解析异常";
    private static final String ERROR_SIGN_VALIDATE = "签名验证异常";
    private static final String ERROR_READ = "读取授权证书文件异常";
    private static final Logger logger = LoggerFactory.getLogger(ComponentLicense.class);
    private byte[] publickey;
    private LicenseInfo licenseInfo;
    private boolean validated;
    private String error;
    private String licensePath;

    public ComponentLicense() {
    }

    public byte[] getPublickey() {
        return this.publickey;
    }

    public LicenseInfo getLicenseInfo() {
        return this.licenseInfo;
    }

    public boolean isValidated() {
        return this.validated;
    }

    public String getError() {
        return this.error;
    }

    public String getLicensePath() {
        return this.licensePath;
    }

    public void setLicensePath(String licensePath) {
        this.licensePath = licensePath;
    }

    public void resolve(String licensePath) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1048576);
        Throwable var4;
        if (!StringUtils.isEmpty(this.licensePath)) {
            logger.debug(">> 加载外部证书:{}", this.licensePath);

            try {
                FileInputStream fileInputStream = new FileInputStream(this.licensePath);
                var4 = null;

                try {
                    FileChannel fc = fileInputStream.getChannel();
                    Throwable var6 = null;

                    try {
                        fc.read(byteBuffer);
                    } catch (Throwable var56) {
                        var6 = var56;
                        throw var56;
                    } finally {
                        if (fc != null) {
                            if (var6 != null) {
                                try {
                                    fc.close();
                                } catch (Throwable var55) {
                                    var6.addSuppressed(var55);
                                }
                            } else {
                                fc.close();
                            }
                        }

                    }
                } catch (Throwable var58) {
                    var4 = var58;
                    throw var58;
                } finally {
                    if (fileInputStream != null) {
                        if (var4 != null) {
                            try {
                                fileInputStream.close();
                            } catch (Throwable var54) {
                                var4.addSuppressed(var54);
                            }
                        } else {
                            fileInputStream.close();
                        }
                    }

                }
            } catch (IOException var60) {
                this.validated = false;
                this.error = "读取授权证书文件异常";
            }
        } else {
            logger.debug(">> 加载内部证书:{}", licensePath);

            try {
                InputStream inputStream = ComponentLicense.class.getClassLoader().getResourceAsStream(licensePath);
                var4 = null;

                try {
                    if (inputStream != null) {
                        byte[] bytes = new byte[1024];
                        boolean var66 = false;

                        int lenght;
                        while((lenght = inputStream.read(bytes)) > 0) {
                            byteBuffer.put(bytes, 0, lenght);
                        }
                    } else {
                        this.validated = false;
                        this.error = "读取授权证书文件异常";
                    }
                } catch (Throwable var61) {
                    var4 = var61;
                    throw var61;
                } finally {
                    if (inputStream != null) {
                        if (var4 != null) {
                            try {
                                inputStream.close();
                            } catch (Throwable var53) {
                                var4.addSuppressed(var53);
                            }
                        } else {
                            inputStream.close();
                        }
                    }

                }
            } catch (IOException var63) {
                this.validated = false;
                this.error = "读取授权证书文件异常";
            }
        }

        if (byteBuffer.position() == 0) {
            this.validated = false;
            this.error = "授权证书文件不存在";
        } else {
            byteBuffer.flip();
            this.readPublicKey(byteBuffer);
            this.readLicenseInfo(byteBuffer);
            if (this.isExpire()) {
                this.validated = false;
                this.error = "证书已过期";
            } else {
                this.validated = true;
                logger.debug("证书时间有效:{}", this.licenseInfo.getExpireTime());
            }
        }

    }

    public boolean isExpire() {
        return this.getLicenseInfo() == null || DateTime.now().substract(new DateTime(this.getLicenseInfo().getExpireTime())).getTicks() > 0L;
    }

    public ValidateResult validate(String component, String method) {
        if (!this.isValidated()) {
            return ValidateResult.builder().error(this.error).result(false).build();
        } else if (this.isExpire()) {
            return ValidateResult.builder().error("证书已过期").result(false).build();
        } else if (StringUtils.isEmpty(component)) {
            return ValidateResult.builder().error("组件不能为空，位置:" + method).result(false).build();
        } else {
            return this.getLicenseInfo().hasComponent(component) ? ValidateResult.builder().result(true).build() : ValidateResult.builder().error("组件：" + component + "->没有授权，位置:" + method).result(false).build();
        }
    }

    private void readPublicKey(ByteBuffer byteBuffer) {
        int keyLenght = byteBuffer.getInt();
        if (keyLenght < 1) {
            this.validated = false;
            this.error = "证书解析异常";
            throw new LicenseResoleException("证书解析异常");
        } else {
            byte[] keyBytes = new byte[keyLenght];
            if (byteBuffer.remaining() >= keyLenght) {
                byteBuffer.get(keyBytes);
                this.publickey = keyBytes;
            } else {
                this.validated = false;
                this.error = "证书解析异常";
                throw new LicenseResoleException("证书解析异常");
            }
        }
    }

    private void readLicenseInfo(ByteBuffer byteBuffer) {
        int licenseLenght = byteBuffer.getInt();
        if (licenseLenght < 1) {
            this.validated = false;
            this.error = "证书解析异常";
            throw new LicenseResoleException("证书解析异常");
        } else {
            byte[] licenseBytes = new byte[licenseLenght];
            if (byteBuffer.remaining() >= licenseLenght) {
                byteBuffer.get(licenseBytes);
                EncryptHandle rsaEncryptHandle = EncoderFactory.getInstance("RSA");

                byte[] decodeValue;
                try {
                    decodeValue = rsaEncryptHandle.decrypt(licenseBytes, this.publickey);
                    String licenseStr = new String(decodeValue, "utf-8");
                    this.licenseInfo = (LicenseInfo) JsonMapperFactory.getInstance().parse(licenseStr, LicenseInfo.class);
                } catch (UnsupportedEncodingException var10) {
                    this.validated = false;
                    this.error = "证书解析异常";
                    throw new LicenseResoleException("证书解析异常", var10);
                }

                int signLenght = byteBuffer.getInt();
                byte[] signBytes = new byte[signLenght];
                byteBuffer.get(signBytes);

                try {
                    if (!rsaEncryptHandle.verify(decodeValue, this.publickey, signBytes)) {
                        this.validated = false;
                        this.error = "证书解析异常";
                        throw new LicenseResoleException("签名验证异常");
                    }

                    logger.debug("签名验证通过");
                } catch (SignatureException | InvalidKeyException | NoSuchAlgorithmException var9) {
                    this.handleException(var9);
                }

            } else {
                this.validated = false;
                this.error = "证书解析异常";
                throw new LicenseResoleException("证书解析异常");
            }
        }
    }

    private void handleException(Exception e) {
        this.validated = false;
        this.error = "签名验证异常";
        throw new LicenseResoleException("签名验证异常", e);
    }
}
