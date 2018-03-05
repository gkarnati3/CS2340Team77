package edu.gatech.cs2340.homefullshelter.controller;

import java.util.List;

import edu.gatech.cs2340.homefullshelter.model.Model;
import edu.gatech.cs2340.homefullshelter.model.Shelter;


/**
 * Created by abhinavtirath on 3/4/18.
 */

public class ShelterListController {

    public List<Shelter> getShelterData() {
        Model model = Model.getInstance();
        return model.getInstance().getItems();
    }
}
