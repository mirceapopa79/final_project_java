package com.sda.javaremote18.spring_boot.utils;

import java.util.regex.Pattern;

public class Utils {
    public static boolean isValidString(String value) {
        if (value == null) {
            return false;
        }
        // I on => dupa trim() Ion
        if (value.trim().length() >= 3) {
            return true;
        }
        return false;
    }

    public static boolean isValidEmail(String email) {
        if (email == null) return false;
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();

        /**
         * email: name@domain.com
         * ^ means it has to start on a new line
         * [a-zA-Z0-9_+&*-] any character in that range
         * + means it must contain at least one character
         * * means it can contain 0 or more characters
         * \\. allows any character to pass once. Like having an underline in an email
         * ?: is a non-capturing group (it doesn't matter what it contains but it has to respect boundaries.
         * $ means it has to be the end of the line
         */
//Example: ^[a-zA-Z0-9_+&*-]+(?:\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\.)+[a-zA-Z]{2,7}$
//Explanation:
//  ^[a-zA-Z0-9_+&*-]+              - On a new line, at least 1 character in [a-zA-Z0-9_+&*-]
//  (?:\.[a-zA-Z0-9_+&*-]+)*@       - Followed by any character in that range until it meets @
//  (?:[a-zA-Z0-9-]+\.)+            - At least 1 character, followed by a dot "\."
//  [a-zA-Z]{2,7}$                  - A suffix of 2-7 chars at the end of the line
    }

    // n- reprezinta lungimea String-ului
    public static String getRandomString(int n) {

        System.out.println("random sting");
        // chose a Character random from this String
        String alphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < n; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int)(alphaNumericString.length()
                    * Math.random());
            System.out.println(index);

            // add Character one by one in end of sb
            sb.append(alphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }
}
