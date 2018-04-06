package edu.gatech.cs2340.homefullshelter;

import org.junit.Test;

import edu.gatech.cs2340.homefullshelter.model.Shelter;
import edu.gatech.cs2340.homefullshelter.model.User;

import static org.junit.Assert.*;

/**
 * Created by mattquan on 4/5/18.
 */
public class MATTQUANSTESTTest {
private Shelter[] allShelters;

    @Test
    public void testOnlyIDEquals() {
        //String uid, String email, String name, int acctType, int currentShelterID,
        //int numberOfBeds
        User u = new User("myID", "awef", "awef", 3, 2, 34);
        User p = new User("myID", "werwe", "234", 2, 3, 4);
        assertEquals(u, p);

    }
    @Test
    public void testNOTHINGISEQUAL() {
        //String uid, String email, String name, int acctType, int currentShelterID,
        //int numberOfBeds
        User u = new User("amyID", "awef", "awef", 3, 2, 34);
        User p = new User("myID", "werwe", "234", 2, 3, 4);
        assertNotEquals(u, p);

    }
    @Test
    public void testEqualsEquals() {
        //String uid, String email, String name, int acctType, int currentShelterID,
        //int numberOfBeds
        User u = new User("amyID", "werwe", "234", 2, 3, 4);
        User p = u;
        assertEquals(u, p);

    }

    @Test
    public void testEverythingButIDisequal() {
        //String uid, String email, String name, int acctType, int currentShelterID,
        //int numberOfBeds
        User u = new User("amyID", "werwe", "234", 2, 3, 4);
        Integer p = 3;
        assertNotEquals(u, p);

    }



}