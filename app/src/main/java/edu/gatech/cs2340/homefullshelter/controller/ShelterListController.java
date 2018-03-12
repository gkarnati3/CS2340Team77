package edu.gatech.cs2340.homefullshelter.controller;

import java.util.ArrayList;
import java.util.List;

import edu.gatech.cs2340.homefullshelter.model.Model;
import edu.gatech.cs2340.homefullshelter.model.Shelter;


/**
 * Created by abhinavtirath on 3/4/18.
 */

public class ShelterListController {
    String name;
    boolean male;
    boolean female;
    boolean fwn;
    boolean child;
    boolean ya;
    boolean any;

    public ShelterListController() {
        this("", true, true, true, true, true, true);
    }

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

    public List<Shelter> getShelterData() {
        Model model = Model.getInstance();
        List<Shelter> shelters = model.getShelters();
        List<Shelter> sheltersToShow = new ArrayList<>();
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
