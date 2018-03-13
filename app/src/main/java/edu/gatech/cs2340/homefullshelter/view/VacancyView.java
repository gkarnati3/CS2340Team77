package edu.gatech.cs2340.homefullshelter.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import edu.gatech.cs2340.homefullshelter.R;
import edu.gatech.cs2340.homefullshelter.controller.DatabaseController;
import edu.gatech.cs2340.homefullshelter.model.Shelter;

/**
 * Created by gkarnati3 on 3/11/18.
 */

public class VacancyView extends AppCompatActivity {
    TextView name;
    TextView shelterView;
    TextView capacity;
    Button plus;
    public int countBeds = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.vacancy_view);

        Shelter curr = getIntent().getExtras().getParcelable("Shelter");
        String capacityD = curr.getCapacity();
        final int capacityDecrease = Integer.parseInt(capacityD);

        name = findViewById(R.id.name);
        plus = findViewById(R.id.plus);
        shelterView = findViewById(R.id.shelterName);
        capacity = findViewById(R.id.capacity);

        if(curr != null) {
            shelterView.setText(curr.getName());
            capacity.setText("Number of available beds: " + capacityD);
        }


        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countBeds++;
                name.setText("" + countBeds);
                capacity.setText("" + (capacityDecrease - countBeds));
            }
        });

//        DatabaseController db = new DatabaseController();
//        String user = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Button submit = findViewById(R.id.button2);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ShelterListActivity.class));
                //setNumberOfBeds(countBeds);
            }
        });
    }
}