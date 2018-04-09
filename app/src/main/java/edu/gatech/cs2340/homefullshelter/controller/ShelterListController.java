package edu.gatech.cs2340.homefullshelter.controller;

import java.util.HashSet;
import java.util.Set;

import edu.gatech.cs2340.homefullshelter.model.Model;
import edu.gatech.cs2340.homefullshelter.model.Shelter;


/**
 * Created by abhinavtirath on 3/4/18.
 */

public class ShelterListController {
    private final String name;
    private final boolean male;
    private final boolean female;
    private final boolean fwn;
    private final boolean child;
    private final boolean ya;
    private final boolean any;

    /**
     * no args constructor that initializes a default ShelterListController
     */
    public ShelterListController() {
        this("", true, true, true, true, true, true);
    }

    /**
     * args constructor that initializes a ShelterListController
     * @param name String of name of shelter
     * @param male boolean if shelter can hold males
     * @param female boolean if shelter can hold females
     * @param fwn boolean if shelter can hold families with newborns
     * @param child boolean if shelter can hold children
     * @param ya boolean if shelter can hold young adult
     * @param any boolean if shelter can hold anyone
     */
    public ShelterListController(String name, boolean male, boolean female, boolean fwn,
                                 boolean child, boolean ya, boolean any) {
        this.name = name;
        this.male = male;
        this.female = female;
        this.fwn = fwn;
        this.child = child;
        this.ya = ya;
        this.any = any;
    }

    /**
     *
     * @return all the shelter data
     */
    public Set<Shelter> getShelterData() {
        Model model = Model.getInstance();
        Set<Shelter> shelters = model.getShelters();
        Set<Shelter> sheltersToShow = new HashSet<>();
        if (male && female && fwn && child && ya && any && name.equals("")) {
            return shelters;
        }
        for (Shelter shelter : shelters) {
            boolean add = true;
            String restrictions = shelter.getRestrictions();
            if (male) {
                if (restrictions.contains("Women")) {
                    add = false;
                }
            }
            if (female) {
                if (restrictions.contains("Men")) {
                    add = false;
                }
            }
            if (!any) {
                if (fwn) {
                    if ((restrictions.contains("Children")
                            || restrictions.contains("Young adult"))
                            && !(restrictions.contains("Families w/ newborn")
                            || restrictions.contains("Anyone"))) {
                        add = false;
                    }
                }
                if (child) {
                    if ((restrictions.contains("Families w/ newborn")
                            || restrictions.contains("Young adult"))
                            && !(restrictions.contains("Children")
                            || restrictions.contains("Anyone"))) {
                        add = false;
                    }
                }
                if (ya) {
                    if ((restrictions.contains("Families w/ newborn")
                            || restrictions.contains("Children"))
                            && !(restrictions.contains("Young adult")
                            || restrictions.contains("Anyone"))) {
                        add = false;
                    }
                }
            }
            if (!name.equals("")) {
                if (!shelter.getName().contains(name)) {
                    add = false;
                }
            }
            if (add) {
                sheltersToShow.add(shelter);
            }
        }
        return sheltersToShow;
    }
}
