package edu.gatech.cs2340.homefullshelter.model;

/**
 * Created by Alexander Hammond on 2/8/18.
 */

public class User {
    private String uID;

    private String name;
    private String email;
    private String password;
    private int accountType;
    private int currentShelterID;
    private int numberOfBeds;

    //exists only for firebase, do not use in local code, should never create empty user
    public User() {
        this("", "", "", "", 0, -1, 0);
    }

    public User(String uID) {
        this(uID, "", "", "", 0, -1, 0);
    }

    /**
     * deprecated, do not use
     */
    public User(String name, String uID, String password, int acctType) {
        this(uID, "", name, password, acctType, -1, 0);
    }

    public User(String uID, String email, String name, String password, int acctType, int currentShelterID, int numberOfBeds) {
        this.uID = uID;
        this.email = email;
        this.name = name;
        this.password = password;
        this.accountType = acctType;
        this.currentShelterID = currentShelterID;
        this.numberOfBeds = numberOfBeds;
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
     * Gets the user's id
     * @return the user id
     */
    public String getUID() {
        return uID;
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

    /**
     * Gets the ID of the shelter the user is in
     * @return the shelter id
     */
    public int getCurrentShelterID() {
        return currentShelterID;
    }

    /**
     * Sets the current shelter id
     * @param currentShelterID the id of the shelter they are in
     */
    public void setCurrentShelterID(int currentShelterID) {
        this.currentShelterID = currentShelterID;
    }

    /**
     * Gets the number of beds the user has checked out
     * @return the the number of beds the user has check out
     */
    public int getNumberOfBeds() {
        return numberOfBeds;
    }

    /**
     * Sets the number of beds the user has check out
     * @param numberOfBeds the number of beds they have check out
     */
    public void setNumberOfBeds(int numberOfBeds) {
        this.numberOfBeds = numberOfBeds;
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
        return this.uID.equals(comp.uID);

    }

    //required for using hashmap to automate adding duplicates
    @Override
    public int hashCode() {
        return (int)email.charAt(0);
    }
}
