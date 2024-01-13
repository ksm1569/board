package com.smsoft.board.util;

import lombok.extern.log4j.Log4j2;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Log4j2
public class SHA256Util {
    public static final String ENCRYPTION_TYPE = "SHA-256";
    public static String encryptSHA256(String str) {
        String SHA = null;

        MessageDigest sh;

        try {
            sh = MessageDigest.getInstance(ENCRYPTION_TYPE);
            sh.update(str.getBytes(StandardCharsets.UTF_8));
            byte[] bytes = sh.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
            }
            SHA = sb.toString();
        } catch (Exception e) {
            log.error("Error occurred while encrypting the string: " + e.getMessage());
        }

        return SHA;
    }
}
