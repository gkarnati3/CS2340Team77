package edu.gatech.cs2340.homefullshelter.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import edu.gatech.cs2340.homefullshelter.R;

/**
 * Created by gkarnati3 on 3/11/18.
 */

public class VacancyView extends AppCompatActivity {

    public int increment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vacancy_view);

        Button submit = findViewById(R.id.button2);
        //TODO: add logout code here
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ShelterListActivity.class));
            }
        });

        Button add = findViewById(R.id.plus);
        add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
//                if (increment == 0) {
//
//                }
            }
        });
    }
}
