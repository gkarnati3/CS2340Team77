package edu.gatech.cs2340.homefullshelter.controller;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

import edu.gatech.cs2340.homefullshelter.model.Model;
import edu.gatech.cs2340.homefullshelter.model.User;
import edu.gatech.cs2340.homefullshelter.view.MainActivity;
import edu.gatech.cs2340.homefullshelter.view.Registration;

/**
 * Created by mattquan on 2/23/18.
 */

public class RegistrationController {
    private String name;
    private String pass;
    private String confirmPass;
    private String username;
    private int acctType;
    private Model model = Model.getInstance();
    public RegistrationController (String name, String pass, String confirmPass, String username, int acctType) {
        this.name = name;
        this.pass = pass;
        this.confirmPass = confirmPass;
        this.username = username;
        this.acctType = acctType;
    }

    public Snackbar register(View v, Context c) {
        Snackbar returnThis = null;
        if (name.equals("") || pass.equals("") || confirmPass.equals("") || username.equals("")) {
            returnThis = Snackbar
                    .make(v, "Please fill out all fields",
                            Snackbar.LENGTH_LONG);

        } else {
            if (pass.equals(confirmPass)) {
                User newUser = new User(name, username, acctType);
                if (model.addUser(newUser)) {
                    Intent myIntent = new Intent(c, MainActivity.class);
                    c.startActivity(myIntent);
                    Toast.makeText(c, "Registration Successful!", Toast.LENGTH_LONG).show();
                } else {
                    returnThis = Snackbar
                            .make(v, "Username already taken.", Snackbar.LENGTH_LONG);
                }

            } else {
                returnThis = Snackbar
                        .make(v, "Your passwords do not match.", Snackbar.LENGTH_LONG);

            }
        }
        return returnThis;
    }
}
