package edu.gatech.cs2340.homefullshelter.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    private HashMap<String, User> users;

    /**
     * Default constructor for model
     * Initializes empty hashMap for the list of users
     */
    private Model() {
        users = new HashMap<>();
        items = new ArrayList<>();
    }

    private List<DataItem> items;

    public void addItem(DataItem item) {
        items.add(item);
    }

    public List<DataItem> getItems() {
        return items;
    }

    public DataItem findItemById(int key) {
        for (DataItem d : items) {
            if (d.getKey() == key) return d;
        }
        Log.d("MYAPP", "Warning - Failed to find id: " + key);
        return null;
    }

    /**
     * Adds a user to the model, prevents duplicates with hashMap storage
     *
     * Only use for adding a new user, this method will not update a user
     *
     * @param user a User object to save to the model
     * @return boolean representing whether the User was added
     */
    public boolean addUser(User user) {
        if (users.containsKey(user.getUsername())) {
            //username already exists, cannot add user with this username
            return false;
        } else {
            users.put(user.getUsername(), user);
            return true;
        }
    }

    /**
     * Checks to see if a username and password match the values for a stored user
     * @param username the user's username
     * @param password the user's password
     * @return true if login valid, otherwise false
     */
    public boolean checkLogin(String username, String password) {
        //default values passed in for empty textboxes from login
        //check is to prevent empty login (should be fine since an empty string
        //should not be valid username or password)
        if (username.equals("") || password.equals("")) {
            return false;
        }
        if (users.containsKey(username)) {
            User user = users.get(username);
            return user.checkPassword(password);
        }
        return false;
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


}
