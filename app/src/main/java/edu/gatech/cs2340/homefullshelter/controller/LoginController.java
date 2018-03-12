package edu.gatech.cs2340.homefullshelter.controller;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;

import edu.gatech.cs2340.homefullshelter.R;
import edu.gatech.cs2340.homefullshelter.model.Model;
import edu.gatech.cs2340.homefullshelter.view.LogoutActivity;
/**
 * Created by mattquan on 2/8/18.
 */

public class LoginController {
    private View view;
    private Context context;
    public LoginController(View v, final Context c) {
        view = v;
        context = c;
    }
    public LoginController() {

    }
    public AlertDialog.Builder makeDialog() {
        AlertDialog.Builder builder;
        LayoutInflater mInflater = LayoutInflater.from(view.getContext());
        builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle("Login")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete

                        EditText usernameText = ((AlertDialog) dialog).findViewById(R.id.editText_userName);
                        EditText passwordText = ((AlertDialog) dialog).findViewById(R.id.editText_password);
                        String username = usernameText.getText().toString();
                        String password = passwordText.getText().toString();

                        Model model = Model.getInstance();

                        if (model.checkLogin(username, password)) {
                            Intent myIntent = new Intent(context, LogoutActivity.class);
                            context.startActivity(myIntent);
                        } else {
                            Toast.makeText(context, "Your username or password is incorrect", Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing

                    }
                })
                .setView(mInflater.inflate(R.layout.alertdialog_login, null));

        return builder;
    }

    public void login(FirebaseUser user) {
        if (Model.getInstance().checkLogin(user.getUid())) {
            Log.e("login successful", "whee");
        } else {
            Log.e("ah","ha");
            //create a new user
        }
    }
}
