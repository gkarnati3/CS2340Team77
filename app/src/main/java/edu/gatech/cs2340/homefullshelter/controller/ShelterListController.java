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
        List<Shelter> shelters = model.getItems();
        List<Shelter> sheltersToShow = new ArrayList<>();
        if (male && female && fwn && child && ya && any && name.equals("")) {
            return shelters;
        }
        for (Shelter shelter : shelters) {
            String restrictions = shelter.getRestrictions();
            if (male) {
                if (!restrictions.contains("Women")) {
                    sheltersToShow.add(shelter);
                }
            } else if (female) {
                if (!restrictions.contains("Men")) {
                    sheltersToShow.add(shelter);
                }
            } else if (fwn) {
                if (restrictions.contains("Families w/ newborn")
                        || restrictions.contains("Anyone")) {
                    sheltersToShow.add(shelter);
                }
            } else if (child) {
                if (restrictions.contains("Children")) {
                    sheltersToShow.add(shelter);
                }
            } else if (ya) {
                if (restrictions.contains("Young adult")) {
                    sheltersToShow.add(shelter);
                }
            } else if (any) {
                if (restrictions.contains("Anyone")) {
                    sheltersToShow.add(shelter);
                }
            }
        }
        return sheltersToShow;
    }
}
