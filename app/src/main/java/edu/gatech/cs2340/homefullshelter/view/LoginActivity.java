package edu.gatech.cs2340.homefullshelter.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

import edu.gatech.cs2340.homefullshelter.R;
import edu.gatech.cs2340.homefullshelter.controller.LoginController;
import edu.gatech.cs2340.homefullshelter.model.Model;
import edu.gatech.cs2340.homefullshelter.model.User;

public class LoginActivity extends AppCompatActivity {
    public static final String TAG = "MY_APP";
    private static final int RC_SIGN_IN = 124;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Model.getInstance().getShelters();

        /*if (Model.getInstance().getShelters().size() == 0) {
            //also uploads the csv data to database
            readSDFile();
        }*/
        setContentView(R.layout.activity_login);


        Button firebase = findViewById(R.id.button_fireThatBase);
        firebase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<AuthUI.IdpConfig> providers = Arrays.asList(
                        new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build());

                // Create and launch sign-in intent
                startActivityForResult(
                        AuthUI.getInstance()
                                .createSignInIntentBuilder()
                                .setAvailableProviders(providers)
                                .build(),
                        RC_SIGN_IN);
            }

        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                Log.d("successful login", "yay");
                FirebaseUser fbuser = FirebaseAuth.getInstance().getCurrentUser();
                LoginController lc = new LoginController(LoginActivity.this);
                Log.d("email", fbuser.getEmail());
                Log.d("name", fbuser.getDisplayName());
                Log.d("Uid", fbuser.getUid());

                lc.login(new User(fbuser.getUid(), fbuser.getEmail(), fbuser.getDisplayName()),
                        LoginActivity.this);
                Log.d("LoginActivity:Login", "called login");

                // ...
            } else {
                Log.e("Login failed", "" + response.getErrorCode());
                // Sign in failed, check response for error code
                // ...
            }
        }
    }//onActivityResult

    /**
     * this is the method that is called when the login was successful.
     * it is called by the login controller, as the login controller will alter the view to switch
     * activities
     * the method simply switches activities from the login activity to the activity that contains
     * all the buttons
     */
    public void loginSuccess() {
        Intent myIntent = new Intent(LoginActivity.this, ButtonActivity.class);
        startActivity(myIntent);
    }



}
