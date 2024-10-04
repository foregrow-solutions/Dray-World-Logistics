package com.loonds.acl.util;

import org.apache.commons.lang3.RandomStringUtils;

import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.Random;
import java.util.UUID;

public final class RandomIdGenerator {
    private static final char[] ALLOWED_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();

    public static String generateId(int length) {
        SecureRandom ng = SecureRandomHolder.INSTANCE;
        byte[] secureBytes = new byte[length];
        ng.nextBytes(secureBytes);
        char[] chars = new char[secureBytes.length];
        for (int i = 0; i < secureBytes.length; i++) {
            chars[i] = ALLOWED_CHARS[((secureBytes[i] & 0xFF) % ALLOWED_CHARS.length)];
        }
        return new String(chars);
    }

    public static String generateUUID() {
        return generateRandomId(25);
    }

    public static String generateRandomId() {
        return UUID.randomUUID().toString();
    }

    public static String generateRandomId(int length) {
        return RandomIdGenerator.generateId(length);
    }

    public static String generateCompactUuid() {
        UUID uuid = UUID.randomUUID();
        ByteBuffer buf = ByteBuffer.allocate(16);
        buf.asLongBuffer()
                .put(uuid.getMostSignificantBits())
                .put(uuid.getLeastSignificantBits());
        return java.util.Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(buf.array());
    }

    public static String generateCompactUuid(int length) {
        return RandomStringUtils.random(length, 0, 0, true, true, null, SecureRandomHolder.INSTANCE);
    }

    public static Integer generateLoadNumber(){
        Random random = new Random();
        int number = 1000000 + random.nextInt(9000);
        return number;
    }

//    public static void main(String[] args) {
//        String password = RandomIdGenerator.generateRandomId(12);
//        System.out.println(password);
//        int o = RandomIdGenerator.generateOtp();
//        System.out.println(o);
//    }

    /**
     * This inner class is lazily loaded only after INSTANCE is called for the first time.
     */
    private static class SecureRandomHolder {
        private static final SecureRandom INSTANCE = new SecureRandom();
    }
}
