package edu.gatech.cs2340.homefullshelter.model;

/**
 * Created by AlexanderHammond on 2/8/18.
 */

public class User {
    private final String username = "user";
    private final String password = "pass";

    public boolean checkUsername(String username) {
        if (this.username.equals(username)) {
            return true;
        }
        return false;
    }

    public boolean checkPassword(String password) {
        if (this.password.equals(password)) {
            return true;
        }
        return false;
    }
}
