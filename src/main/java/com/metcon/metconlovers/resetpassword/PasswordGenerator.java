package com.metcon.metconlovers.resetpassword;

import java.security.SecureRandom;

public class PasswordGenerator {

    /**
     * different dictionaries used
     */

    private static final SecureRandom random = new SecureRandom();

    /**
     * Method will generate random string based on the parameters
     *
     * @param len the length of the random string
     * @param dic the dictionary used to generate the password
     * @return the random password
     */
    public static String generatePassword(int len, String dic) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < len; i++) {
            int index = random.nextInt(dic.length());
            result.append(dic.charAt(index));
        }
        return result.toString();
    }
}