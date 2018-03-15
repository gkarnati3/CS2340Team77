package edu.gatech.cs2340.homefullshelter.controller;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;

import edu.gatech.cs2340.homefullshelter.R;
import edu.gatech.cs2340.homefullshelter.interfaces.OnGetDataInterface;
import edu.gatech.cs2340.homefullshelter.model.Model;
import edu.gatech.cs2340.homefullshelter.model.User;
import edu.gatech.cs2340.homefullshelter.view.LogoutActivity;
import edu.gatech.cs2340.homefullshelter.view.MainActivity;

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

    public void login(final User user, final MainActivity mainActivity) {
        Log.e("login:uID", "" + user.getUID());
        Model.getInstance().login(user, new OnGetDataInterface() {
            @Override
            public void onDataRetrieved(DataSnapshot data) {
                User tmp = data.getValue(User.class);
                if (tmp != null) {
                    Model.getInstance().setCurrentUser(tmp);
                    Log.d("login:numBedsTmp", "" + tmp.getNumberOfBeds());
                    Log.d("loginDB:tmpuID", "hm: " + tmp.getUID());
                    Log.d("loginDB:usruID", "hm: " + user.getUID());



                } else {
                    Model.getInstance().setCurrentUser(user);
                    Log.d("login:numBedsUsr", "" + user.getNumberOfBeds());

                }
                onLoginSuccess(mainActivity);
            }

            @Override
            public void onFailed() {
                onLoginFail();
            }
        });
    }

    private void onLoginSuccess(MainActivity mainActivity) {
        //TODO Intent to main screen from here (not MainActivity)
        mainActivity.loginSuccess();
    }

    private void onLoginFail() {
        //TODO Intent to MainActivity from here, so they can restart login process
        Intent myIntent = new Intent(context, MainActivity.class);
        Toast.makeText(context, "Your username or password is incorrect", Toast.LENGTH_LONG).show();
        context.startActivity(myIntent);

    }
}
