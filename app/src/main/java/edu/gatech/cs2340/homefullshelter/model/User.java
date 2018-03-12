package edu.gatech.cs2340.homefullshelter.model;

/**
 * Created by Alexander Hammond on 2/8/18.
 */

public class User {
    private String name;
    private String username;
    private String password;
    private int accountType;

    //exists only for firebase, do not use in local code, should never create empty user
    public User() {
        this("", "", "", 0);
    }

    public User(String name, String username, String password, int acctType) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.accountType = acctType;
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }


    //Below are all the getters/setters, equals and hashcode methods

    /**
     * Gets the user's name
     * @return the name of the user
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the user's name
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the user's username
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the user's username
     * @param username the new username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the user's password
     * @return the password
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Sets the user's password
     * @param password the new password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the account type
     * @return the account type
     */
    public int getAccountType() {
        return accountType;
    }

    /**
     * Sets the account type
     * @param accountType the new account type
     */
    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }

    //required for using hashmap to automate adding duplicates
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof User)) {
            return false;
        }

        User comp = (User) other;
        return this.username.equals(comp.name);

    }

    //required for using hashmap to automate adding duplicates
    @Override
    public int hashCode() {
        return (int)username.charAt(0);
    }
}
