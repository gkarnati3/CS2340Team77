package edu.gatech.cs2340.homefullshelter;

import org.junit.Test;

import java.util.Set;

import edu.gatech.cs2340.homefullshelter.model.Model;
import edu.gatech.cs2340.homefullshelter.model.Shelter;

import static org.junit.Assert.*;

/**
 * Created by nitarakad on 4/8/18.
 */
public class NITYATARAKADTESTTest {
    Model model = Model.getInstance();

    @Test
    public void testGetShelters {
        Set<Shelter> shelters = model.getShelters();
        assertEquals(shelters, shelters);
    }

}