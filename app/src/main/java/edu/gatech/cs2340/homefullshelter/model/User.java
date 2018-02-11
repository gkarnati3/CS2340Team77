package edu.gatech.cs2340.homefullshelter.model;

/**
 * Created by AlexanderHammond on 2/8/18.
 */

public class User {
    private static final String username = "user";
    private static final String password = "pass";

    public static boolean checkUsername(String userName) {
        return username.equals(userName);
    }

    public static boolean checkPassword(String passWord) {
        return password.equals(passWord);
    }
}
