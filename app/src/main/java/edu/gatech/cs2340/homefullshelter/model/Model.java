package edu.gatech.cs2340.homefullshelter.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.gatech.cs2340.homefullshelter.controller.DatabaseController;

/**
 * Created by mattquan on 2/8/18.
 */

/*
 * Model singleton that acts as backing store for all of the data for the app
 *
 * To get the reference to the model call "Model model = Model.getInstance();"
 * to add a user: "model.addUser(User user);"
 * to check login info: "model.checkLogin(String username, String password);"
 */

public class Model {
    //the one and only instantiation of the class (making it a singleton)
    private static Model appModel = new Model();
    private List<Shelter> shelters;
    private User currentUser;

    /**
     * Default constructor for model
     * Initializes empty hashMap for the list of users
     */
    private Model() {
        users = new HashMap<>();
        shelters = new ArrayList<>();
        currentUser = null;
    }

    /**
     * Only changes the model
     * Only use immediately after login, DO NOT USE OTHERWISE
     *
     * @param user the new current user
     */
    public void setCurrentUser(User user) {
        currentUser = user;
    }

    public void updateCurrentUser(User user) {
        //TODO change for asynchronous safety
        currentUser = user;
        DatabaseController db = new DatabaseController();
        db.updateUser(user);
    }

    public void updateShelter(Shelter shelter) {
        //TODO change for asynchronous safety
        int counter = 0;
        shelters.remove(shelter);
        shelters.add(shelter);
        DatabaseController db = new DatabaseController();
        db.updateShelter(shelter);
    }

        //eventually replace this with addShelter
    public void addShelter(Shelter shelter) {
        shelters.add(shelter);
    }

    public List<Shelter> getShelters() {
        //should be changed to update the list of shelters from the database
        return shelters;
    }

    public Shelter findShelterById(int key) {
        for (Shelter d : shelters) {
            if (d.getKey() == key) return d;
        }
        Log.d("MYAPP", "Warning - Failed to find id: " + key);
        return null;
    }


    //method used to access the singleton

    /**
     * Provides access to the model
     * @return a reference to the model
     */
    public static Model getInstance() {
        if (appModel == null) {
            appModel = new Model();
        }
        return appModel;
    }



    //Everything below here is deprecated user handling. Remove eventually

    private HashMap<String, User> users;

    /**
     * Adds a user to the model, prevents duplicates with hashMap storage
     *
     * Only use for adding a new user, this method will not update a user
     *
     * @param user a User object to save to the model
     * @return boolean representing whether the User was added
     */
    public boolean addUser(User user) {
        //TODO change to support database checking
        if (users.containsKey(user.getUID())) {
            //username already exists, cannot add user with this username
            return false;
        } else {
            users.put(user.getUID(), user);
            return true;
        }
    }



    /**
     * Checks to see if a username and password match the values for a stored user
     * @param uID the user's username
     * @param password the user's password
     * @return true if login valid, otherwise false
     */
    public boolean checkLogin(String uID, String password) {
        //TODO change to support database checking
        //default values passed in for empty textboxes from login
        //check is to prevent empty login (should be fine since an empty string
        //should not be valid username or password)
        if (uID.equals("") || password.equals("")) {
            return false;
        }
        if (users.containsKey(uID)) {
            User user = users.get(uID);
            return user.checkPassword(password);
        }

        return false;
    }
    public void checkLogin(String uID) {
        //THIS IS FOR THE DATABASE
        DatabaseController dc = new DatabaseController();
        User user = dc.getUser(uID);
        if (user.getName().isEmpty()) {
            //TODO ALEX THIS METHOD MIGHT NEED CHANGING
            dc.addUserAndSetAsCurrent(new User(uID));
        } else {
            //TODO Also this
            setCurrentUser(user);
        }

    }

}
