package edu.gatech.cs2340.homefullshelter;

import com.google.firebase.database.DataSnapshot;

import org.junit.Test;
import static org.junit.Assert.*;

import edu.gatech.cs2340.homefullshelter.controller.DatabaseController;
import edu.gatech.cs2340.homefullshelter.interfaces.OnGetDataInterface;
import edu.gatech.cs2340.homefullshelter.model.Shelter;

/**
 * Created by abhinavtirath on 4/8/18.
 * Testing the addShelter method in DatabaseController
 */

public class ABHINAVTIRATHTEST {

    @Test
    public void testFalse() {
        Shelter shelter = new Shelter();
        DatabaseController dbc = new DatabaseController();

        assertTrue(dbc.addShelter(shelter, new OnGetDataInterface() {
            @Override
            public void onDataRetrieved(DataSnapshot data) {

            }

            @Override
            public void onFailed() {

            }
        }));
    }

    @Test
    public void testTrue() {
        Shelter shelter = new Shelter(10, "a", "a", "a", 2, 2, "a", "a", "a");
        DatabaseController dbc = new DatabaseController();
        assertFalse(dbc.addShelter(shelter, new OnGetDataInterface() {
            @Override
            public void onDataRetrieved(DataSnapshot data) {

            }

            @Override
            public void onFailed() {

            }
        }));
    }

}
