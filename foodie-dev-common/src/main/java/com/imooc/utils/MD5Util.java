package com.imooc.utils;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * Description:TODO
 * Create Time:2019/9/5 0005 下午 4:36
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class MD5Util {
    private static final String salt = "1a2b3c4d";

    public static String md5(String string) {
        return DigestUtils.md5Hex(string);
    }

    public static String inputPassFormPass(String inputPass) {
        String str = "" + salt.charAt(0) + salt.charAt(2) + inputPass + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    public static String formPassToDBPass(String formPass, String salt) {
        String str = "" + salt.charAt(0) + salt.charAt(2) + formPass + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    public static String inputPassToDBPass(String inputPass, String saltDB) {
        String formPass = inputPassFormPass(inputPass);
        String dbPass = formPassToDBPass(formPass, saltDB);
        return dbPass;
    }

    public static void main(String[] args) {

        System.out.println(md5("111111"));
        System.out.println(inputPassFormPass(("123456")));
        System.out.println(formPassToDBPass(inputPassFormPass(("123456")), "1a2b3c4d"));
        System.out.println(inputPassToDBPass("123456","1a2b3c4d"));
    }
}
