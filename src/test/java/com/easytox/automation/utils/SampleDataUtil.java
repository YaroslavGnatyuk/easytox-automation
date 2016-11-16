package com.easytox.automation.utils;

import java.sql.Timestamp;
import java.util.*;

public class SampleDataUtil {

    static Random random = new Random();

    static List<String> firstNames = Arrays.asList("Matthew", "Joseph", "Samuel", "Luke", "Gavin", "Carson", "Dominic", "Henry", "Anthony", "David", "Kevin",
                                      "Andrew", "Austin", "Angel", "Aaron", "Sebastian", "Jason", "Hudson", "Parker", "Cooper");
    static List<String> lastNames = Arrays.asList("Smith", "Johnson", "Williams", "Brown", "Davis", "Millor", "Wilson", "Moore", "Taylor",
                                     "Anderson", "Thomas", "Jackson", "White", "Harris", "Martin", "Thompson", "Clark", "Adams", "Baker", "Roberts");


    public static String getFirstName() {

        return firstNames.get(random.nextInt(firstNames.size()));
    }

    public static String getLastName() {

        return lastNames.get(random.nextInt(lastNames.size()));
    }

    public static String getEmail() {
        return "auto${System.currentTimeMillis()}@gmail.com";
    }

    public static String getOrganization() {
        String[] arr = UUID.randomUUID().toString().split("-");
        return "organisation${arr[0]}";
    }

    public static String getUserName() {
        String[] arr = UUID.randomUUID().toString().split("-");
        return "user${arr[0]}";

    }

//    static long getRandomId(int[] ids) {
//        long value = ids.get(random.nextInt(ids.size()));
//        return value;
//    }

    static int getRandomNumber(int max) {
        return random.nextInt(max);
    }

    static Date getRandomDate(Date from, Date to) {
        long offset = from.getTime();
        long end = to.getTime();
        long diff = end - offset + 1;
        Timestamp randomTimeStamp = new Timestamp(offset + (long) (Math.random() * diff));
        long milliseconds = randomTimeStamp.getTime() + (randomTimeStamp.getNanos() / 1000000);
        return new Date(milliseconds);
    }

}

