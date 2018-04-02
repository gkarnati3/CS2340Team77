package edu.gatech.cs2340.homefullshelter.controller;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;

import edu.gatech.cs2340.homefullshelter.interfaces.OnGetDataInterface;
import edu.gatech.cs2340.homefullshelter.model.Model;
import edu.gatech.cs2340.homefullshelter.model.User;
import edu.gatech.cs2340.homefullshelter.view.LoginActivity;

/**
 * Created by mattquan on 2/8/18.
 */

public class LoginController {
    private Context context;

    /**
     * Creates a login controller object
     * @param context the view that created the controller (for an intent on failure to login)
     */
    public LoginController(Context context) {
        this.context = context;
    }

    /**
     * Logs a user into the application
     * @param user the user to login
     * @param loginActivity the LoginActivity view that creates the controller
     */
    public void login(final User user, final LoginActivity loginActivity) {
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
                onLoginSuccess(loginActivity);
            }

            @Override
            public void onFailed() {
                onLoginFail();
            }
        });
    }

    /**
     * Method to run on successful login
     * @param loginActivity
     */
    private void onLoginSuccess(LoginActivity loginActivity) {
        loginActivity.loginSuccess();
    }

    /**
     * Method to run on login failure
     */
    private void onLoginFail() {
        Intent myIntent = new Intent(context, LoginActivity.class);
        Toast.makeText(context, "Your username or password is incorrect", Toast.LENGTH_LONG).show();
        context.startActivity(myIntent);

    }
}
