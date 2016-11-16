package com.easytox.automation.utils;

import java.util.Random;

class StringUtils {
    public static String generateRandom() {
        String aToZ = "ABCDEFGHIJKLMNOPQRSTUVWXYZ123456789";
        Random rand = new Random();
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            int randIndex = rand.nextInt(aToZ.length());
            res.append(aToZ.charAt(randIndex));
        }
        return res.toString();
    }
}

