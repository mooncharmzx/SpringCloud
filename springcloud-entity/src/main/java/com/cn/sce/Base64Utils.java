package com.cn.sce;

import java.io.UnsupportedEncodingException;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Base64Utils {
    private static final Logger log = LoggerFactory.getLogger(Base64Utils.class);

    private static final char[] S_BASE64CHAR = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'};

    Base64Utils() {
    }

    public static String encodeFromString(String srcString) {
        return encodeFromString(srcString, "utf-8");
    }

    public static String encodeFromString(String srcString, String charsetName) {
        try {
            return encode(srcString.getBytes(charsetName), charsetName);
        } catch (UnsupportedEncodingException var3) {
            log.error(UnsupportedEncodingException.class.getSimpleName(), var3);
            return "";
        }
    }

    public static String encodeUTF8(byte[] srcBytes) {
        return encode(srcBytes, "utf-8");
    }

    public static String encode(byte[] srcBytes, String charsetName) {
        try {
            return new String(Base64.encodeBase64(srcBytes), charsetName);
        } catch (UnsupportedEncodingException var3) {
            log.error(UnsupportedEncodingException.class.getSimpleName(), var3);
            return "";
        }
    }

    public static String decodeToString(String base64String) {
        return decodeToString(base64String, "utf-8");
    }

    public static String decodeToString(String base64String, String charsetName) {
        try {
            return new String(decode(base64String, charsetName), charsetName);
        } catch (UnsupportedEncodingException var3) {
            log.error(UnsupportedEncodingException.class.getSimpleName(), var3);
            return "";
        }
    }

    public static byte[] decode(String base64String) {
        return decode(base64String, "utf-8");
    }

    public static byte[] decode(String base64String, String charsetName) {
        try {
            return Base64.decodeBase64(base64String.getBytes(charsetName));
        } catch (UnsupportedEncodingException var3) {
            log.error(UnsupportedEncodingException.class.getSimpleName(), var3);
            return new byte[0];
        }
    }

    public static String encode(byte[] data) {
        return encode(data, 0, data.length);
    }

    public static String encode(byte[] data, int off, int len) {
        if (len <= 0) {
            return "";
        } else {
            char[] out = new char[len / 3 * 4 + 4];
            int rindex = off;
            int windex = 0;

            int rest;
            int i;
            for(rest = len - off; rest >= 3; rest -= 3) {
                i = ((data[rindex] & 255) << 16) + ((data[rindex + 1] & 255) << 8) + (data[rindex + 2] & 255);
                out[windex++] = S_BASE64CHAR[i >> 18];
                out[windex++] = S_BASE64CHAR[i >> 12 & 63];
                out[windex++] = S_BASE64CHAR[i >> 6 & 63];
                out[windex++] = S_BASE64CHAR[i & 63];
                rindex += 3;
            }

            if (rest == 1) {
                i = data[rindex] & 255;
                out[windex++] = S_BASE64CHAR[i >> 2];
                out[windex++] = S_BASE64CHAR[i << 4 & 63];
                out[windex++] = '=';
                out[windex++] = '=';
            } else if (rest == 2) {
                i = ((data[rindex] & 255) << 8) + (data[rindex + 1] & 255);
                out[windex++] = S_BASE64CHAR[i >> 10];
                out[windex++] = S_BASE64CHAR[i >> 4 & 63];
                out[windex++] = S_BASE64CHAR[i << 2 & 63];
                out[windex++] = '=';
            }

            return new String(out, 0, windex);
        }
    }
}
