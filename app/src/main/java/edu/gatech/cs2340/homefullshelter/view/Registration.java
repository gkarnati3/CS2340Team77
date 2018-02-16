package edu.gatech.cs2340.homefullshelter.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import edu.gatech.cs2340.homefullshelter.R;
import edu.gatech.cs2340.homefullshelter.model.Model;
import edu.gatech.cs2340.homefullshelter.model.User;

/*
 * Model singleton that acts as backing store for all of the data for the app
 *
 * To get the reference to the model call "Model model = Model.getInstance();"
 * to add a user: "model.addUser(User user);"
 * to check login info: "model.checkLogin(String username, String password);"
 */
public class Registration extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final EditText nameText = findViewById(R.id.editText_registration_name);
        final EditText passText = findViewById(R.id.editText_password);
        final EditText confirmPassText = findViewById(R.id.editText_password_confirm);
        final EditText username1Text = findViewById(R.id.editText_username1);
        final Spinner spinner = findViewById(R.id.spinner_registration_accountType);
        String[] actType = {"User", "Admin"};
        ArrayAdapter accountType = new ArrayAdapter(this, android.R.layout.simple_spinner_item, actType);
        accountType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(accountType);
        Button register = findViewById(R.id.button_register);
        Button cancel = findViewById(R.id.button_cancel);
        final Model model = Model.getInstance();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Editable usernameEditable = nameText.getText();
                Editable passwordEditable = passText.getText();
                Editable confirmPassEditable = confirmPassText.getText();
                Editable username1Editable = username1Text.getText();

                String name = "";
                String pass = "";
                String confirmPass = "";
                String username1 = "";

                if (usernameEditable == null || passwordEditable == null
                        || confirmPassEditable == null || username1Editable == null) {
                    Snackbar snackbar4 = Snackbar
                            .make(v, "Unkown error",
                                    Snackbar.LENGTH_LONG);

                    snackbar4.show();
                } else {
                    name = usernameEditable.toString();
                    pass = passwordEditable.toString();
                    confirmPass = confirmPassEditable.toString();
                    username1 = username1Editable.toString();
                }
                if (name.equals("") || pass.equals("") || confirmPass.equals("") || username1.equals("")) {
                    Snackbar snackbar5 = Snackbar
                            .make(v, "Please fill out all fields",
                                    Snackbar.LENGTH_LONG);

                    snackbar5.show();
                } else {

                    int acctType = spinner.getSelectedItemPosition();
                    if (pass.equals(confirmPass)) {
                        User newUser = new User(name, username1, pass, acctType);
                        if (model.addUser(newUser)) {
                            Intent myIntent = new Intent(Registration.this, MainActivity.class);
                            startActivity(myIntent);
                            Snackbar snackbar1 = Snackbar
                                    .make(v, "You have successfully registered.", Snackbar.LENGTH_LONG);

                            snackbar1.show();
                        } else {
                            Snackbar snackbar3 = Snackbar
                                    .make(v, "Username already taken.", Snackbar.LENGTH_LONG);

                            snackbar3.show();
                        }

                    } else {
                        Snackbar snackbar = Snackbar
                                .make(v, "Your passwords do not match.", Snackbar.LENGTH_LONG);

                        snackbar.show();
                    }
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Registration.this, MainActivity.class);
                startActivity(myIntent);
                Snackbar snackbar2 = Snackbar
                        .make(v, "Registration cancelled.", Snackbar.LENGTH_LONG);

                snackbar2.show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
