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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.gatech.cs2340.homefullshelter.R;
import edu.gatech.cs2340.homefullshelter.controller.DatabaseController;
import edu.gatech.cs2340.homefullshelter.controller.LoginController;
import edu.gatech.cs2340.homefullshelter.model.Shelter;
import edu.gatech.cs2340.homefullshelter.model.Model;
import edu.gatech.cs2340.homefullshelter.model.User;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MY_APP";
    private static final int RC_SIGN_IN = 124;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Model.getInstance().getShelters().size() == 0) {
            readSDFile();
        }
        setContentView(R.layout.activity_main);
        Button registrationButton = (Button) findViewById(R.id.button_registration);
        Button loginButton = findViewById(R.id.button_login);
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
                LoginController lc = new LoginController(v, getApplicationContext());
                lc.makeDialog().show();
            }
        });

        Button firebase = (Button) findViewById(R.id.button_fireThatBase);
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
                Log.e("successful login", "yay");
                FirebaseUser fbuser = FirebaseAuth.getInstance().getCurrentUser();
                LoginController lc = new LoginController();
                //TODO MATT, need to know if login or register
                /*
                if login call:
                    lc.login(fbuser)
                if register call:
                    User user = new User(fbuser.getUid(), fbuser.getEmail(), fbuser.getDisplayName());
                    lc.register(user);
                */
                //assumes register for now... will mean data about beds is overwritten
                User user = new User(fbuser.getUid(), fbuser.getEmail(), fbuser.getDisplayName());
                Intent myIntent = new Intent(MainActivity.this, LogoutActivity.class);
                lc.register(user, MainActivity.this);
                // ...
            } else {
                // Sign in failed, check response for error code
                // ...
            }
        }
    }//onActivityResult

    public void loginSuccess() {
        Intent myIntent = new Intent(MainActivity.this, LogoutActivity.class);
        startActivity(myIntent);
    }

    private void readSDFile() {
        Model model = Model.getInstance();

        try {
            //Open a stream on the raw file
            InputStream is = getResources().openRawResource(R.raw.homelessshelterdatabase);
            //From here we probably should call a model method and pass the InputStream
            //Wrap it in a BufferedReader so that we get the readLine() method
            BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));

            String line;
            br.readLine(); //get rid of header line
            while ((line = br.readLine()) != null) {
                Log.d(MainActivity.TAG, line);
                ArrayList<String> tokens = new ArrayList<String>();

                String[] parse = line.split("\"");
                for (int i = 0; i < parse.length; i++) {
                    if (i % 2 == 0) {
                        String[] tok = parse[i].split(",");
                        boolean isFirst = true;
                        for (String s : tok) {
                            if (i == 0) {
                                tokens.add(s);
                            } else if (i != 0 && !isFirst) {
                                tokens.add(s);
                            }
                            isFirst = false;
                        }
                    } else {
                        tokens.add(parse[i]);
                    }
                }
                int key = Integer.parseInt(tokens.get(0));
                double lo = Double.parseDouble(tokens.get(4));
                double la = Double.parseDouble(tokens.get(5));
                Shelter shelter = new Shelter(key, tokens.get(1), tokens.get(2), tokens.get(3), lo, la, tokens.get(6), tokens.get(7), tokens.get(8));
                model.addShelter(shelter);
            }
            br.close();
            //System.out.println("PRINT THE THINGY:"+model.getShelters().size());
        } catch (IOException e) {
            Log.e(MainActivity.TAG, "error reading assets", e);
        }
    }

}
