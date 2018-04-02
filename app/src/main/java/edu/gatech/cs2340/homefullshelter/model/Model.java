package edu.gatech.cs2340.homefullshelter.model;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;

import java.util.HashSet;
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


    public void login(final User user, final OnGetDataInterface listener) {
        final DatabaseController dc = new DatabaseController();
        dc.getUser(user.getUID(), new OnGetDataInterface() {
            @Override
            public void onDataRetrieved(DataSnapshot data) {
                if (data.exists()) {
                    listener.onDataRetrieved(data);
                } else {
                    dc.addUser(user, new OnGetDataInterface() {
                        @Override
                        public void onDataRetrieved(DataSnapshot data) {
                            listener.onDataRetrieved(data);
                        }

                        @Override
                        public void onFailed() {
                            listener.onFailed();
                        }
                    });
                }
            }

            @Override
            public void onFailed() {
                listener.onFailed();
            }
        });
    }


    /**
     * Updates current user (who is logged in)
     * @param user the new current user
     */
    public void updateCurrentUser(final User user) {
        //TODO change for asynchronous safety
        DatabaseController db = new DatabaseController();
        db.updateUser(user, new OnGetDataInterface() {
            @Override
            public void onDataRetrieved(DataSnapshot data) {
                User tmp = data.getValue(User.class);
                if (tmp != null) {
                    Model.getInstance().setCurrentUser(tmp);
                    Log.e("updateUserTmp:numBeds", "" + tmp.getNumberOfBeds());
                } else {
                    Model.getInstance().setCurrentUser(user);
                    Log.e("updateUserUsr:numBeds", "" + user.getNumberOfBeds());

                }
                Model.getInstance().setCurrentUser(tmp);
            }

            @Override
            public void onFailed() {

            }
        });
    }

    /**
     * Updates a shelter in the model and database
     * @param shelter the updated shelter (identifies by key)
     * @param listener an interface to handle the response from the database
     */
    public void updateShelter(Shelter shelter, OnGetDataInterface listener) {
        shelters.remove(shelter);
        shelters.add(shelter);
        DatabaseController db = new DatabaseController();
        db.updateShelter(shelter, listener);
    }

    /**
     * Shelter to add to the model and database
     * @param shelter to add
     */
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

    /**
     * Retrieves a set of all the shelters
     * @return a set of all the shelters stored in the model, and refreshes the list in the model
     * to match the database
     */
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
