package edu.gatech.cs2340.homefullshelter;

import org.junit.Test;
import edu.gatech.cs2340.homefullshelter.model.User;
import edu.gatech.cs2340.homefullshelter.model.Shelter;
import static org.junit.Assert.*;

/**
 * Created by AlexanderHammond on 4/8/18.
 */

public class ALEXANDERHAMMONDTEST {

    @Test
    public void testValidEquals() {
        assertTrue((new Shelter()).equals(new Shelter()));
        assertTrue((new Shelter(4, "a", "a", "a", 4.0, 4.0, "a", "a", "a", "a"))
                .equals(new Shelter(4, "a", "a", "a", 4.0, 4.0, "a", "a", "a", "a")));
        assertTrue((new Shelter(45, "a", "a", "a", 4.0, 4.0, "a", "a", "a", "a"))
                .equals(new Shelter(45, "b", "b", "b", 4.2, 4.2, "b", "b", "b", "b")));

    }

    @Test
    public void testInvalidEquals() {
        assertFalse((new Shelter()).equals(new Shelter(4, "a", "a", "a", 4.0, 4.0, "a", "a", "a", "a")));
        assertFalse((new Shelter(4, "a", "a", "a", 4.0, 4.0, "a", "a", "a", "a"))
                .equals(new Shelter(5, "a", "a", "a", 4.0, 4.0, "a", "a", "a", "a")));
    }

    @Test
    public void testInvalidEqualsWithOtherObject() {
        assertFalse((new Shelter()).equals(new User()));
        assertFalse((new Shelter(4, "a", "a", "a", 4.0, 4.0, "a", "a", "a", "a")).equals(new User()));
    }

    @Test
    public void testWithNull() {
        assertFalse((new Shelter()).equals(null));
    }

}
