package edu.gatech.cs2340.homefullshelter.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;

import edu.gatech.cs2340.homefullshelter.R;
import edu.gatech.cs2340.homefullshelter.controller.DatabaseController;
import edu.gatech.cs2340.homefullshelter.interfaces.OnGetDataInterface;
import edu.gatech.cs2340.homefullshelter.model.Shelter;
import edu.gatech.cs2340.homefullshelter.model.User;
import edu.gatech.cs2340.homefullshelter.model.Model;

/**
 * Created by gkarnati3 on 3/11/18.
 */

public class VacancyView extends AppCompatActivity {
    TextView name;
    TextView shelterView;
    TextView capacity;
    TextView notIncrement;
    Button plus;
    Button minus;
    public int countBeds = 0;
    int initialCheckedOut = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.vacancy_view);

        final Shelter curr = getIntent().getExtras().getParcelable("Shelter");
        int checkedOut = Integer.parseInt(curr.getCheckedOut());
        int shelterCapacity = Integer.parseInt(curr.getCapacity());
        int capacityD = shelterCapacity - checkedOut;
        final int capacityDecrease = capacityD;
        final Model model = Model.getInstance();
        final User actualUser = model.getCurrentUser();
        Log.e("userNumBeds", "" + actualUser.getNumberOfBeds());

        name = findViewById(R.id.name);
        plus = findViewById(R.id.plus);
        minus = findViewById(R.id.minus);
        shelterView = findViewById(R.id.shelterName);
        capacity = findViewById(R.id.capacity);
        notIncrement = findViewById(R.id.notIncrement);
        //TODO RENAME SUBMIT BUTTON IDENTIFIER
        Button submit = findViewById(R.id.button2);
        Button goback = findViewById(R.id.goback);


        if(curr != null) {
            shelterView.setText(curr.getName());
            capacity.setText("Number of available beds: " + capacityD);
        }


        if (actualUser.getCurrentShelterID() == curr.getKey() || actualUser.getCurrentShelterID() == -1) {
            countBeds = actualUser.getNumberOfBeds();
            initialCheckedOut = actualUser.getNumberOfBeds();
            name.setText("" + countBeds);
            plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (Integer.parseInt(curr.getCheckedOut()) + countBeds <= Integer.parseInt(curr.getCapacity())) {
                        countBeds++;
                    }
                    name.setText("" + countBeds);
                    capacity.setText("" + (capacityDecrease + initialCheckedOut - countBeds));
                }
            });

            minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (countBeds > 0) {
                        if (actualUser.getNumberOfBeds() + countBeds >= 0) {
                            countBeds--;
                        }
                        name.setText("" + countBeds);
                        capacity.setText("" + (capacityDecrease + initialCheckedOut - countBeds));
                    }
                }
            });

            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //can they only click submit if they were allowed to update beds?
                    //if not need to check here if they updated beds
                    int initialNumberOfBeds = actualUser.getNumberOfBeds();
                    actualUser.setNumberOfBeds(countBeds);
                    actualUser.setCurrentShelterID(curr.getKey());
                    if (actualUser.getNumberOfBeds() == 0) {
                        actualUser.setCurrentShelterID(-1);
                    }

                    //Takes initial checked out - user initial check out + user final checked out = final checked out
                    String newCheckOut = "" + (Integer.parseInt(curr.getCheckedOut()) - initialNumberOfBeds + countBeds);
                    curr.setCheckedOut(newCheckOut);
                    model.updateCurrentUser(actualUser);
                    model.updateShelter(curr, new OnGetDataInterface() {
                        @Override
                        public void onDataRetrieved(DataSnapshot data) {
                            startActivity(new Intent(getApplicationContext(), ShelterListActivity.class));
                        }

                        @Override
                        public void onFailed() {
                            //TODO: NEED ERROR CODE TO LET THEM KNOW THEY COULD NOT CHECK OUT
                            startActivity(new Intent(getApplicationContext(), ShelterListActivity.class));
                        }
                    });
                }
            });
        } else {
            notIncrement.setText("You are not located in this shelter.");
        }

//        DatabaseController db = new DatabaseController();
//        String user = FirebaseAuth.getInstance().getCurrentUser().getUid();




        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ShelterListActivity.class));
            }
        });
    }
}