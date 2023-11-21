package com.example.pipayshopapi.util;

import java.util.Random;

public class InviteCodeGenerator {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    /**
     * 随机生成8为数的邀请码
     * @return 邀请码
     */
    public static String generateInviteCode() {
        StringBuilder inviteCode = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 8; i++) {
            int index = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(index);
            inviteCode.append(randomChar);
        }

        return inviteCode.toString();
    }

}
