package edu.gatech.cs2340.homefullshelter;

import org.junit.Test;

import edu.gatech.cs2340.homefullshelter.model.Shelter;

import static org.junit.Assert.*;

/**
 * Created by nitarakad on 4/8/18.
 */
public class NITYATARAKADTESTTest {

    @Test
    public void testIfSetNameIsNotNull() {
        Shelter shelter = new Shelter(0, "My Sister's House" ,"264" ,"Women/Children",
                -84.410142,33.780174,"921 Howell Mill Road, Atlanta, Georgia 30318",
                "Temporary, Emergency, Residential Recovery","(404) 367-2465");
        shelter.setName(null);
        assertNotNull(shelter.getName());
        assertEquals(shelter.getName(), "My Sister's House");
        shelter.setName("My Aunt's House");
        assertNotNull(shelter.getName());
        assertEquals(shelter.getName(), "My Aunt's House");
    }


}