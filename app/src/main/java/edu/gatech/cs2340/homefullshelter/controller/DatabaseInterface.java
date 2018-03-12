package edu.gatech.cs2340.homefullshelter.controller;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import edu.gatech.cs2340.homefullshelter.model.Model;
import edu.gatech.cs2340.homefullshelter.model.Shelter;
import edu.gatech.cs2340.homefullshelter.model.User;

/**
 * Created by AlexanderHammond on 3/11/18.
 */

public class DatabaseInterface {
    private DatabaseReference mDatabase;

    public DatabaseInterface() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    //may not be used depending on how whether firebase automates this, which it should
    public void register(User user) {
        String key = mDatabase.child("users").push().getKey();
        DatabaseReference newUser = mDatabase.child("users").child(key);
        newUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Model model = Model.getInstance();
                model.setUserID(dataSnapshot.getKey());
                //TODO call some method in login controller to say they logged in successfully
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //TODO call some method in login controller that shows pop up saying failed to register
            }
        });
    }

    public void addShelter(Shelter shelter) {
        DatabaseReference newShelter;
        if (shelter.getKey() != -1) {
            newShelter = mDatabase.child("shelters").child(Integer.toString(shelter.getKey()));
        } else {
            newShelter = mDatabase.child("shelters").child(Integer.toString(shelter.getKey()));
        }
        newShelter.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //TODO code to show shelter was added
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //TODO code to show error in adding shelter
            }
        });
        newShelter.setValue(shelter);
    }

    /**
     * Gets data for a shelter specified with the given key from the database
     * @param key
     * @return the shelter object created from the information stored on the database
     */
    public Shelter getShelter(int key) {
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
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //TODO show some error screen that data could not be loaded from server
                Log.d("Database:getShelter", databaseError.getMessage());
            }
        });
        return shelter;
    }

    /**
     * Gets data for a shelter specified with the given key from the database
     * @param uID
     * @return the shelter object created from the information stored on the database
     */
    public User getUser(String uID) {
        final User user = new User();
        DatabaseReference shelterRef = mDatabase.child("users").child(uID);
        shelterRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User tmp = dataSnapshot.getValue(User.class);
                if (tmp != null) {
                    user.setAccountType(tmp.getAccountType());
                    user.setName(tmp.getName());
                    user.setPassword(tmp.getPassword());
                    user.setUsername(tmp.getUsername());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //TODO show some error screen that data could not be loaded from server
                Log.d("Database:getUser", databaseError.getMessage());
            }
        });
        return user;
    }

    /**
     * Gets data for all the shelters from the database
     * @return a list of all shelters
     */
    public List<Shelter> getShelters() {
        final List<Shelter> shelters = new ArrayList<>();
        DatabaseReference shelterRef = mDatabase.child("shelters");
        shelterRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Shelter tmp = dataSnapshot.getValue(Shelter.class);
                shelters.add(tmp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //TODO show some error screen that data could not be loaded from server
                Log.d("Database:getShelters", databaseError.getMessage());
            }
        });
        return shelters;
    }

    /**
     * Right now just replaces the existing shelter object, shelter object will need to store its list of occupied users
     * this does not support concurrent use
     * @param shelter the updated shelter class to send to the database
     */
    public void updateShelter(Shelter shelter) {
        DatabaseReference newShelter;
        newShelter = mDatabase.child("shelters").child(Integer.toString(shelter.getKey()));
        newShelter.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //TODO code to show shelter was updated
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //TODO code to show error in updating shelter
            }
        });
        newShelter.setValue(shelter);
    }
}
