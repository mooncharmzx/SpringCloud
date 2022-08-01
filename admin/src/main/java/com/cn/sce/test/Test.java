package com.cn.sce.test;

import com.cn.sce.enmu.AppHttpCodeEnum;

public class Test {
    public static void main(String[] args) {
//
//        try {
//            String aa = "a";
//            Integer.parseInt(aa);
//        }catch (NumberFormatException numberFormatException){
//            System.out.println("2");
//        }catch (RuntimeException runtimeException){
//            System.out.println(3);
//        }catch (Exception e){
//            System.out.println("1");
//        }
//
        System.out.println(AppHttpCodeEnum.NEED_LOGIN.getAhCode());
    }
}
