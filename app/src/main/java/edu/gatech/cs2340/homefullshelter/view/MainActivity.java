package edu.gatech.cs2340.homefullshelter.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.firebase.ui.auth.AuthUI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.gatech.cs2340.homefullshelter.R;
import edu.gatech.cs2340.homefullshelter.controller.LoginController;
import edu.gatech.cs2340.homefullshelter.model.Shelter;
import edu.gatech.cs2340.homefullshelter.model.Model;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MY_APP";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Model.getInstance().getItems().size() == 0) {
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
                Log.e("hello", "h");
                startActivityForResult(
                        AuthUI.getInstance()
                                .createSignInIntentBuilder()
                                .setAvailableProviders(providers)
                                .build(),
                        123);
                startActivityForResult(
                        AuthUI.getInstance()
                                .createSignInIntentBuilder()
                                .setAvailableProviders(providers)
                                .build(),
                        123);
            }

        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("he","he");
        Log.e("resquestCode", String.valueOf(requestCode));
        Log.e("resultCode", String.valueOf(resultCode));
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                String result=data.getStringExtra("result");
            }
        }
    }//onActivityResult
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
                for(int i = 0; i < parse.length; i++){
                    if (i % 2 == 0){
                    String[] tok = parse[i].split(",");
                    boolean isFirst = true;
                        for(String s  : tok) {
                            if(i == 0){
                                tokens.add(s);
                            }
                            else if (i != 0 && !isFirst) {
                                tokens.add(s);
                            }
                            isFirst = false;
                        }
                    }
                    else{
                        tokens.add(parse[i]);
                    }
                }
                int key = Integer.parseInt(tokens.get(0));
                double lo = Double.parseDouble(tokens.get(4));
                double la = Double.parseDouble(tokens.get(5));
                model.addItem(new Shelter(key, tokens.get(1), tokens.get(2), tokens.get(3), lo, la, tokens.get(6), tokens.get(7), tokens.get(8)));
            }
            br.close();
           //System.out.println("PRINT THE THINGY:"+model.getItems().size());
        } catch (IOException e) {
            Log.e(MainActivity.TAG, "error reading assets", e);
        }

    }

}
