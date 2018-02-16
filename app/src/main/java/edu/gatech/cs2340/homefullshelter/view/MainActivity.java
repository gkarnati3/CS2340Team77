package edu.gatech.cs2340.homefullshelter.view;

import android.content.DialogInterface;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;

import edu.gatech.cs2340.homefullshelter.R;
import edu.gatech.cs2340.homefullshelter.model.Model;
import edu.gatech.cs2340.homefullshelter.model.User;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button registrationButton = (Button) findViewById(R.id.button_registration);
        Button loginButton = findViewById(R.id.button_login);
        final View view = findViewById(R.id.content);
        registrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, Registration.class);
                startActivity(myIntent);
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder;
                LayoutInflater mInflater = LayoutInflater.from(v.getContext());
                builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Login")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete

                                EditText usernameText = ((AlertDialog) dialog).findViewById(R.id.editText_userName);
                                EditText passwordText = ((AlertDialog) dialog).findViewById(R.id.editText_password);
                                Editable usernameEditable = usernameText.getText();
                                Editable passwordEditable = passwordText.getText();
                                String username = "";
                                String password = "";
                                if (usernameEditable != null) {
                                    username = usernameEditable.toString();
                                }
                                if (passwordEditable != null) {
                                    password = passwordEditable.toString();
                                }

                                Model model = Model.getInstance();
                                if (model.checkLogin(username, password)) {
                                    Intent myIntent = new Intent(getApplicationContext(), Logout.class);
                                    startActivity(myIntent);
                                } else {
                                    Snackbar snackbar = Snackbar
                                            .make(view, "Your username or password is incorrect.", Snackbar.LENGTH_LONG);

                                    snackbar.show();
                                }
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing

                            }
                        })
                        .setView(mInflater.inflate(R.layout.alertdialog_login, null))
                        .show();
            }
        });
    }
}
