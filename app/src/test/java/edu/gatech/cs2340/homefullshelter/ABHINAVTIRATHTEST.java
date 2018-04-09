package edu.gatech.cs2340.homefullshelter;

import com.google.firebase.database.DataSnapshot;

import org.junit.Test;
import static org.junit.Assert.*;

import edu.gatech.cs2340.homefullshelter.controller.DatabaseController;
import edu.gatech.cs2340.homefullshelter.interfaces.OnGetDataInterface;
import edu.gatech.cs2340.homefullshelter.model.Shelter;

/**
 * Created by abhinavtirath on 4/8/18.
 * Testing the setCapacity method in Shelter
 */

public class ABHINAVTIRATHTEST {

    @Test(expected = NumberFormatException.class)
    public void testThrowNumberFormatException() {
        Shelter shelter = new Shelter();
        shelter.setCapacity("hello");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testThrowIllegalArgumentException() {
        Shelter shelter = new Shelter();
        shelter.setCapacity("-1");
    }

    @Test
    public void testNameChangedCorrectlyTrue() {
        Shelter shelter = new Shelter();
        shelter.setCapacity("32");
        System.out.println(shelter.getCapacity());
        assertTrue(shelter.getCapacity().equals("32"));
    }

    @Test
    public void testNameChangedCorrectlyFalse() {
        Shelter shelter = new Shelter();
        shelter.setCapacity("32");
        assertFalse(shelter.getCapacity().equals("27"));
    }

}
