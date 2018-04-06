package edu.gatech.cs2340.homefullshelter.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashSet;
import java.util.Set;

import edu.gatech.cs2340.homefullshelter.R;
import edu.gatech.cs2340.homefullshelter.model.Model;
import edu.gatech.cs2340.homefullshelter.model.Shelter;

public class ShelterMapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;

    String name = "";
    boolean male;
    boolean female;
    boolean fwn;
    boolean child;
    boolean ya;
    boolean any;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Button filter = findViewById(R.id.button_maps_filter);
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder;
                LayoutInflater mInflater = LayoutInflater.from(v.getContext());
                builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Sort Shelters").setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        RadioButton maleButton = ((AlertDialog) dialog).findViewById(R.id.radiobutton_male);
                        male = maleButton.isChecked();
                        RadioButton femaleButton = ((AlertDialog) dialog).findViewById(R.id.radiobutton_female);
                        female = femaleButton.isChecked();
                        RadioButton fwnButton = ((AlertDialog) dialog).findViewById(R.id.radioButton_FWN);
                        fwn = fwnButton.isChecked();
                        RadioButton childButton = ((AlertDialog) dialog).findViewById(R.id.radiobutton_child);
                        child = childButton.isChecked();
                        RadioButton yaButton = ((AlertDialog) dialog).findViewById(R.id.radiobutton_YA);
                        ya = yaButton.isChecked();
                        RadioButton anyButton = ((AlertDialog) dialog).findViewById(R.id.radiobutton_AE);
                        any = anyButton.isChecked();

                        EditText shelter = ((AlertDialog) dialog).findViewById(R.id.editText_filterName);
                        name = shelter.getText().toString();

                        reloadMarkers();

                    }
                })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing

                            }
                        })
                        .setView(mInflater.inflate(R.layout.alertdialog_search, null)).show();


            }
        });
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add shelter markers.
     * @param googleMap an instance of Google Maps

     */

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        reloadMarkers();
    }

    /**
     * Clears the markers currently on the map and replaces them with the correct markers based
     * on the search
     */
    public void reloadMarkers() {

        Model model = Model.getInstance();
        Set<Shelter> shelters = model.getShelters();
        Set<Shelter> sheltersToShow = new HashSet<>();

        if (male && female && fwn && child && ya && any && name.equals("")) {
            sheltersToShow = shelters;
        }
        for (Shelter shelter : shelters) {
            boolean add = true;
            String restrictions = shelter.getRestrictions();
            if (male) {
                if (restrictions.contains("Women")) {
                    add = false;
                }
            }
            if (female) {
                if (restrictions.contains("Men")) {
                    add = false;
                }
            }
            if (!any) {
                if (fwn) {
                    if ((restrictions.contains("Children")
                            || restrictions.contains("Young adult"))
                            && !(restrictions.contains("Families w/ newborn")
                            || restrictions.contains("Anyone"))) {
                        add = false;
                    }
                }
                if (child) {
                    if ((restrictions.contains("Families w/ newborn")
                            || restrictions.contains("Young adult"))
                            && !(restrictions.contains("Children")
                            || restrictions.contains("Anyone"))) {
                        add = false;
                    }
                }
                if (ya) {
                    if ((restrictions.contains("Families w/ newborn")
                            || restrictions.contains("Children"))
                            && !(restrictions.contains("Young adult")
                            || restrictions.contains("Anyone"))) {
                        add = false;
                    }
                }
            }
            if (!name.equals("")) {
                if (!shelter.getName().contains(name)) {
                    add = false;
                }
            }
            if (add) {
                sheltersToShow.add(shelter);
            }
        }
        mMap.clear();
        for (Shelter shelter: sheltersToShow) {
            LatLng marker = new LatLng(shelter.getLatitude(), shelter.getLongitude());
            mMap.addMarker(new MarkerOptions().position(marker).title(shelter.getName()).snippet("Phone: " +shelter.getNumber()));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(marker));
        }

        mMap.setOnMarkerClickListener(this);
    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 11));
        return false;
    }
}
