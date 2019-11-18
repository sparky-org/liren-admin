package com.sparky.lirenadmin.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Utils {

    public static String md5(String password){
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            password = "<li"+password+"ren!";
            messageDigest.update(password.getBytes());
            String md5Pwd = new BigInteger(messageDigest.digest()).toString(16);
            if (password.equals(md5Pwd)){
                throw new RuntimeException("新旧密码不一致");
            }
            return md5Pwd;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("修改密码失败");
        }
    }

}
