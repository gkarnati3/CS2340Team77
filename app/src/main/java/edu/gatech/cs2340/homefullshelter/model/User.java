package edu.gatech.cs2340.homefullshelter.model;

/**
 * Created by Alexander Hammond on 2/8/18.
 */

public class User {
    private String uid;

    private String name;
    private String email;
    private int accountType;
    private int currentShelterID;
    private int numberOfBeds;


    /**
     * this is a constructor for firebase. it is ncessary only for firebase.
     * do not use in local code.
     */
    public User() {
        this("");
    }

    /**
     * Create a user with just the uid, use when creating an user just for a database call
     * @param uid
     */
    private User(String uid) {
        this(uid, "", "");
    }

    /**
     * Create a user with uid, email, and name, use when creating a user after firebase login
     * Ensures we have as much information as possible stored in model in case database call fails
     * @param uid
     * @param email
     * @param name
     */
    public User(String uid, String email, String name) {
        this(uid, email, name, 0, -1, 0);
    }

    /**
     * Create a user with all user data, used when data retrieved from database
     * @param uid
     * @param email
     * @param name
     * @param acctType
     * @param currentShelterID
     * @param numberOfBeds
     */
    private User(String uid, String email, String name, int acctType, int currentShelterID,
                 int numberOfBeds) {
        this.uid = uid;
        this.email = email;
        this.name = name;
        this.accountType = acctType;
        this.currentShelterID = currentShelterID;
        this.numberOfBeds = numberOfBeds;
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
        return uid;
    }

    /**
     * Gets the user's email
     * @return the user email
     */
    public String getEmail() {
        return email;
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

    //required for using hash data structures to automate adding duplicates
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof User)) {
            return false;
        }

        User comp = (User) other;
        return this.uid.equals(comp.uid);

    }

    @Override
    public String toString() {
        return "User{" +
                "uID='" + uid + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", accountType=" + accountType +
                ", currentShelterID=" + currentShelterID +
                ", numberOfBeds=" + numberOfBeds +
                '}';
    }

    //required for using hash data structures to automate adding duplicates
    @Override
    public int hashCode() {
        return (int)uid.charAt(0);
    }
}
