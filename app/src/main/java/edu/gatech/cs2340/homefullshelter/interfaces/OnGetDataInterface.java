package edu.gatech.cs2340.homefullshelter.interfaces;

import com.google.firebase.database.DataSnapshot;

/**
 * Created by AlexanderHammond on 3/12/18.
 */

public interface OnGetDataInterface {
    public void onDataRetrieved(DataSnapshot data);
    public void onFailed();
}
