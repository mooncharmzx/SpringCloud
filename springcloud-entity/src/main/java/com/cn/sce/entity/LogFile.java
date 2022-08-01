package com.cn.sce.entity;

import com.cn.sce.Base64Utils;
import com.cn.sce.builder.MessageDigestBuilder;
import com.cn.sce.license.LicenseManager;
import com.google.gson.Gson;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class LogFile {
    private static final Logger logger = LoggerFactory.getLogger(LogFile.class);
    private static String FILE_ID = getByUUId();

    private LogFile() {
    }

    public static void writeFile(MessageEntity me) throws IOException {
        BufferedWriter writer = null;

        try {
            File dir = new File(me.getPath());
            if (!dir.exists()) {
                dir.mkdirs();
            }

            String content = me.getConent();
            Gson gson = new Gson();
            LogContentInfo logContentInfo = (LogContentInfo)gson.fromJson(content, LogContentInfo.class);
            String filename = logContentInfo.getComponentName() + "-" + DateFormatUtils.format(new Date(), "yyyyMMdd");
            File logFile = new File(dir, filename + ".log");
            if (!logFile.exists()) {
                logFile.createNewFile();
                writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(logFile, true), "UTF-8"));
            } else {
                writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(logFile, true), "UTF-8"));
            }

            logger.info("写入的文件为：" + logFile.getAbsolutePath());
            logger.info("将要向文件中写入的日志的内容是：" + content);
            String sign = signatureMessage(content);
            logger.info("签名的密钥是：" + LicenseManager.getPubliceKey());
            logger.info("得到的签名是：" + sign);
            String result = "{\"content\":" + content + ",\"sign\":\"" + sign + "\"}\n";
            logger.info("写入文件中的日志为：" + result);
            writer.write(result);
            writer.flush();
            if (logFile.length() / 1024L > 102400L) {
                int file_no = 1;
                String[] var11 = logFile.getParentFile().list();
                int var12 = var11.length;

                for(int var13 = 0; var13 < var12; ++var13) {
                    String itemName = var11[var13];
                    if (itemName.indexOf(filename + "_") >= 0) {
                        int file_cur_no = Integer.parseInt(itemName.substring((filename + "_").length()));
                        if (file_cur_no >= file_no) {
                            file_no = file_cur_no + 1;
                        }
                    }
                }

                logFile.renameTo(new File(me.getPath(), filename + ".log" + "_" + file_no));
                (new File(me.getPath(), filename + ".log")).createNewFile();
            }
        } catch (IOException var19) {
            logger.error("write log file error !", var19);
        } finally {
            if (writer != null) {
                writer.close();
            }

        }

    }

    private static String signatureMessage(String content) {
        try {
            MessageDigestBuilder md = MessageDigestBuilder.getInstance().setDigestAlgorithm(MessageDigestBuilder.DigestAlgorithm.HMACSHA256);
            String secret = Base64Utils.encode(LicenseManager.getPubliceKey());
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secret_key);
            String hash = Base64.encodeBase64String(sha256_HMAC.doFinal(content.getBytes()));
            logger.info("secret:{},\n signature:{}", secret, hash);
            return hash;
        } catch (Exception var6) {
            logger.error("content encrypt error!", var6);
            return content;
        }
    }

    private static String getByUUId() {
        int first = (new Random(10L)).nextInt(8) + 1;
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        if (hashCodeV < 0) {
            hashCodeV = -hashCodeV;
        }

        return first + String.format("%015d", hashCodeV);
    }
}
