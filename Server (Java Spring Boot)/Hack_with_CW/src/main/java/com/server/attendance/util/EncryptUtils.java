package com.server.attendance.util;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

@Slf4j
public class EncryptUtils {

    private static final Charset UTF8 = StandardCharsets.UTF_8;
    private static final String PADDING = "AES/ECB/PKCS5PADDING";
    private static final String SHA_1 = "SHA-1";
    private static final String AES = "AES";
    private static SecretKeySpec secretKey;
    private static final String PRIVATE_KEY = "justASecretKey";

    public static void setKey() {
        MessageDigest sha = null;
        try {
            byte[] key = Base64.getDecoder().decode(PRIVATE_KEY);
            sha = MessageDigest.getInstance(SHA_1);
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, AES);
        } catch (NoSuchAlgorithmException e) {
            log.error("Error while encrypting: {}", e.getMessage(), e);
        }
    }

    public static String encrypt(String strToEncrypt) {
        try {
            setKey();
            Cipher cipher = Cipher.getInstance(PADDING);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes(UTF8)));
        } catch (Exception e) {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }

    /*public static String decrypt(String strToDecrypt) {
        try {
            setKey();
            Cipher cipher = Cipher.getInstance(PADDING);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        } catch (Exception e) {
            log.error("Error while decrypting: {}", e.getMessage(), e);
        }
        return null;
    }*/
}
