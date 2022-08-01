package com.cn.sce;

import com.cn.sce.aop.ExposeMethodAble;

import static com.sun.corba.se.spi.logging.CORBALogDomains.UTIL;

public class BaseCoderUtil {

    /**
     * 16进制字符串
     */
    private static final String HEXSTRING = "0123456789abcdef";

    /**
     * 将byte数组转换为表示16进制值的字符串
     * @param arrB byte数组
     * @return 16进制的字符串
     */

    @ExposeMethodAble(component = UTIL,method = "")
    public static String byteArr2HexStr(byte[] arrB) {
        int iLen = arrB.length;
        // 每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍
        StringBuilder sb = new StringBuilder(iLen * 2);

        // 将字节数组中每个字节拆解成2位16进制整数
        for (int i = 0; i < iLen; i++) {
            sb.append(HEXSTRING.charAt((arrB[i] & 0xf0) >> 4));
            sb.append(HEXSTRING.charAt((arrB[i] & 0x0f) >> 0));
        }
        return sb.toString();
    }

    /**
     * 将表示16进制值的字符串转换为byte数组
     * @param strIn 需要转换的字符串
     * @return 转换后的byte数组
     */

    @ExposeMethodAble(component = UTIL,method = "")
    public static byte[] hexStr2ByteArr(String strIn) {
        byte[] arrB = strIn.getBytes();
        int iLen = arrB.length;

        // 两个字符表示一个字节，所以字节数组长度是字符串长度除以2
        byte[] arrOut = new byte[iLen / 2];
        for (int i = 0; i < iLen; i = i + 2) {
            String strTmp = new String(arrB, i, 2);
            arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
        }
        return arrOut;
    }

}
