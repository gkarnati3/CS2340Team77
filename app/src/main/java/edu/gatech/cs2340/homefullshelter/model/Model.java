package edu.gatech.cs2340.homefullshelter.model;

import android.util.Log;
import android.view.Display;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.gatech.cs2340.homefullshelter.controller.DatabaseController;
import edu.gatech.cs2340.homefullshelter.interfaces.OnGetDataInterface;

/**
 * Created by mattquan on 2/8/18.
 */

/*
 * Model singleton that acts as backing store for all of the data for the app
 *
 * To get the reference to the model call "Model model = Model.getInstance();"
 * to add a user: "model.addUser(User user);"
 * to check login info: "model.login(String username, String password);"
 */

public class Model {
    //the one and only instantiation of the class (making it a singleton)
    private static Model appModel = new Model();
    private Set<Shelter> shelters;
    private User currentUser;

    /**
     * Default constructor for model
     * Initializes empty hashMap for the list of users
     */
    private Model() {
        users = new HashMap<>();
        shelters = new HashSet<>();
        currentUser = null;
    }

    /**
     * @return the current user
     */
    public User getCurrentUser() {
        return currentUser;
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


    public void login(final String uID, OnGetDataInterface listener) {
        DatabaseController dc = new DatabaseController();
        dc.getUser(uID, listener);
    }

    public void register(User user, OnGetDataInterface listener) {
        //THIS IS FOR THE DATABASE
        setCurrentUser(user); //this line is redundant, but stays in place in case database error
        DatabaseController db = new DatabaseController();
        db.addUser(user, listener);
    }


    //probably do not need this w/ login and register above
    public void updateCurrentUser(final User user) {
        //TODO change for asynchronous safety
        currentUser = user;
        DatabaseController db = new DatabaseController();
        db.updateUser(user, new OnGetDataInterface() {
            @Override
            public void onDataRetrieved(DataSnapshot data) {
                User tmp = data.getValue(User.class);
                if (tmp != null) {
                    Model.getInstance().setCurrentUser(tmp);
                } else {
                    Model.getInstance().setCurrentUser(user);
                }
                Model.getInstance().setCurrentUser(tmp);
            }

            @Override
            public void onFailed() {

            }
        });
    }

    public void updateShelter(Shelter shelter, OnGetDataInterface listener) {
        int counter = 0;
        shelters.remove(shelter);
        shelters.add(shelter);
        DatabaseController db = new DatabaseController();
        db.updateShelter(shelter, listener);
    }

    public void addShelter(Shelter shelter) {
        shelters.add(shelter);
        new DatabaseController().addShelter(shelter, new OnGetDataInterface() {
            @Override
            public void onDataRetrieved(DataSnapshot data) {
                //nothing needed
            }

            @Override
            public void onFailed() {
                //nothing needed
            }
        });
    }

    public Set<Shelter> getShelters() {
        //should be changed to update the list of shelters from the database
        DatabaseController db = new DatabaseController();
        db.getShelters(new OnGetDataInterface() {
            @Override
            public void onDataRetrieved(DataSnapshot data) {
                for (DataSnapshot dataSnapshot: data.getChildren()) {
                    shelters.add(dataSnapshot.getValue(Shelter.class));
                    Log.d("LoadedShelter", dataSnapshot.getValue(Shelter.class).toString());
                }
            }

            @Override
            public void onFailed() {

            }
        });
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
     * DEPRECATED DO NOT USE
     * Adds a user to the model, prevents duplicates with hashMap storage
     *
     * Only use for adding a new user, this method will not update a user
     *
     * @param user a User object to save to the model
     * @return boolean representing whether the User was added
     */
    public boolean addUser(User user) {
        if (users.containsKey(user.getUID())) {
            //username already exists, cannot add user with this username
            return false;
        } else {
            users.put(user.getUID(), user);
            return true;
        }
    }



    /**
     * **DEPRECATED DO NOT USE**
     * Checks to see if a username and password match the values for a stored user
     * @param uID the user's username
     * @param password the user's password
     * @return true if login valid, otherwise false
     */
    public boolean checkLogin(String uID, String password) {
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
}
