package edu.gatech.cs2340.homefullshelter.controller;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import edu.gatech.cs2340.homefullshelter.interfaces.OnGetDataInterface;
import edu.gatech.cs2340.homefullshelter.model.Shelter;
import edu.gatech.cs2340.homefullshelter.model.User;

/**
 * Created by AlexanderHammond on 3/11/18.
 */

public class DatabaseController {
    private DatabaseReference mDatabase;

    public DatabaseController() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    /**
     * Used for adding a user to the firebase database after authentication
     * Also sets the new user as the current user in the model if they are added to the database successfully
     * @param user the user to add
     * @param listener an interface with the correct response
     */
    public void addUser(User user, final OnGetDataInterface listener) {
        changeUser(user, listener);
    }

    /**
     * Updates
     * @param user the user to change
     * @param listener an interface with methods to run based on response
     */
    private void changeUser(User user, final OnGetDataInterface listener) {
        DatabaseReference newUser = mDatabase.child("users").child(user.getUID());
        newUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listener.onDataRetrieved(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onFailed();
            }
        });
        newUser.setValue(user);
    }

    /**
     * Adds a shelter to the database
     * @param shelter to add
     * @param listener interface with methods to handle the response from database
     */
    public void addShelter(Shelter shelter, final OnGetDataInterface listener) {
        DatabaseReference newShelter;
        if (shelter.getKey() != -1) {
            newShelter = mDatabase.child("shelters").child(Integer.toString(shelter.getKey()));
        } else {
            newShelter = mDatabase.child("shelters").child(Integer.toString(shelter.getKey()));
        }
        newShelter.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listener.onDataRetrieved(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onFailed();
            }
        });
        newShelter.setValue(shelter);
    }

    /**
     * Gets data for a shelter specified with the given key from the database
     * @param key the database key for the desired shelter
     * @param listener an interface to handle the response from the database
     */
    public void getShelter(int key, final OnGetDataInterface listener) {
        final Shelter shelter = new Shelter();
        DatabaseReference shelterRef = mDatabase.child("shelters").child(Integer.toString(key));
        shelterRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Shelter tmp = dataSnapshot.getValue(Shelter.class);
                if (tmp != null) {
                    shelter.setAddress(tmp.getAddress());
                    shelter.setCapacity(tmp.getCapacity());
                    shelter.setKey(tmp.getKey());
                    shelter.setLatitude(tmp.getLatitude());
                    shelter.setLongitude(tmp.getLongitude());
                    shelter.setName(tmp.getName());
                    shelter.setNotes(tmp.getNotes());
                    shelter.setNumber(tmp.getNumber());
                    shelter.setRestrictions(tmp.getRestrictions());

                }
                listener.onDataRetrieved(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onFailed();
                Log.d("Database:getShelter", databaseError.getMessage());
            }
        });
    }

    /**
     * Gets data for a user specified with the given key from the database
     * @param uID the userID of the user whose information should be retrieved
     * @param listener an interface to handle the response from the database
     */
    public void getUser(final String uID, final OnGetDataInterface listener) {
        Log.e("getUser:uID", "" + uID);
        DatabaseReference userRef = mDatabase.child("users").child(uID);
        ValueEventListener valueListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listener.onDataRetrieved(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onFailed();
                Log.d("Database:getUser", databaseError.getMessage());
            }
        };
        userRef.addListenerForSingleValueEvent(valueListener);
    }

    /**
     * Gets data for all the shelters from the database
     * @param listener an interface to handle the response from the database
     */
    public void getShelters(final OnGetDataInterface listener) {
        DatabaseReference shelterRef = mDatabase.child("shelters");
        shelterRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //the entire list of shelters will be in this snapshot
                listener.onDataRetrieved(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onFailed();
                Log.d("Database:getShelters", databaseError.getMessage());
            }
        });
    }

    /**
     * Right now just replaces the existing user object
     * this does not support concurrent use, and changes to user are not automatically
     * reflected in any shelter object they affect
     * @param user the updated shelter class to send to the database
     * @param listener an interface to handle the response from the database
     */
    public void updateUser(User user, final OnGetDataInterface listener) {
        changeUser(user, listener);
    }

    /**
     * Right now just replaces the existing shelter object,
     * shelter object will need to store its list of occupied users,
     * changes to this do not affect any corresponding user data, that must be changed separately
     * this does not support concurrent use
     * @param shelter the updated shelter class to send to the database
     * @param listener an interface to handle the response from the database
     */
    public void updateShelter(Shelter shelter, final OnGetDataInterface listener) {
        DatabaseReference newShelter;
        newShelter = mDatabase.child("shelters").child(Integer.toString(shelter.getKey()));
        newShelter.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listener.onDataRetrieved(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onFailed();
            }
        });
        newShelter.setValue(shelter);
    }
}
