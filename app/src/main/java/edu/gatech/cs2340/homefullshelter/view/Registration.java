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
import android.widget.TextView;
import android.widget.Toast;

import edu.gatech.cs2340.homefullshelter.R;
import edu.gatech.cs2340.homefullshelter.controller.RegistrationController;
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
        final EditText usernameText = findViewById(R.id.editText_username1);
        final Spinner spinner = findViewById(R.id.spinner_registration_accountType);

        String[] actType = {"User", "Admin"};
        ArrayAdapter accountType = new ArrayAdapter(this, android.R.layout.simple_spinner_item, actType);
        accountType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(accountType);
        Button register = findViewById(R.id.button_register);
        TextView cancel = findViewById(R.id.textview_cancelRegistration);

        final Model model = Model.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = nameText.getText().toString();
                String pass = passText.getText().toString();
                String confirmPass = confirmPassText.getText().toString();
                String username = usernameText.getText().toString();
                int acctType = spinner.getSelectedItemPosition();

                RegistrationController rc = new RegistrationController(name, pass, confirmPass, username, acctType);
                Snackbar bar = rc.register(v, getApplicationContext());
                if (bar != null) {
                    bar.show();
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Registration.this, MainActivity.class);
                startActivity(myIntent);
                Toast.makeText(getApplicationContext(), "Registration cancelled", Toast.LENGTH_LONG).show();

            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
