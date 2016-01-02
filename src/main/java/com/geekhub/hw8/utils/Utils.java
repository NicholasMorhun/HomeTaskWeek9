package com.geekhub.hw8.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Contains util methods
 */
public class Utils {
    /**
     * Returns md5 hash from string
     * @param text text for hashing
     * @return md5 hash of {@code text}
     */
    public static String getMd5Hash(String text) {
        if (text == null) {
            return null;
        }

        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md.update(text.getBytes());

        byte byteData[] = md.digest();

        StringBuilder hexStringBuilder = new StringBuilder();
        for (int i = 0; i < byteData.length; i++) {
            String hex = Integer.toHexString(0xff & byteData[i]);
            if (hex.length() == 1) hexStringBuilder.append('0');
            hexStringBuilder.append(hex);
        }

        return hexStringBuilder.toString();
    }



}
