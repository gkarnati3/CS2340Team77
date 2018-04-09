package edu.gatech.cs2340.homefullshelter.view;



import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;


import java.util.List;

import edu.gatech.cs2340.homefullshelter.R;
import edu.gatech.cs2340.homefullshelter.controller.ShelterListController;
import edu.gatech.cs2340.homefullshelter.model.Shelter;

/**
 * An activity representing a list of Data Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 *
 * Created by gkarnati3 on 2/25/18.
 */

public class ShelterListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */

    ShelterListController sla = new ShelterListController();
    final SimpleItemRecyclerViewAdapter recyclerViewAdapter =
            new SimpleItemRecyclerViewAdapter(sla.getShelterData());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        final Button sortButton = findViewById(R.id.button_sort);
        sortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("finder");
                AlertDialog.Builder builder;
                LayoutInflater mInflater = LayoutInflater.from(v.getContext());
                builder = new AlertDialog.Builder(v.getContext());
                    builder.setTitle("Sort Shelters").setPositiveButton(android.R.string.yes,
                            new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            RadioButton maleButton = ((AlertDialog) dialog)
                                    .findViewById(R.id.radiobutton_male);
                            boolean male = false;
                            if (maleButton != null) {
                                male = maleButton.isChecked();
                            }
                            RadioButton femaleButton = ((AlertDialog) dialog)
                                    .findViewById(R.id.radiobutton_female);
                            boolean female = false;
                            if (femaleButton != null) {
                                female = femaleButton.isChecked();
                            }
                            RadioButton fwnButton = ((AlertDialog) dialog)
                                    .findViewById(R.id.radioButton_FWN);
                            boolean fwn = false;
                            if (fwnButton != null) {
                                fwn = fwnButton.isChecked();
                            }
                            RadioButton childButton = ((AlertDialog) dialog)
                                    .findViewById(R.id.radiobutton_child);
                            boolean child = false;
                            if (childButton != null) {
                                child = childButton.isChecked();
                            }
                            RadioButton yaButton = ((AlertDialog) dialog)
                                    .findViewById(R.id.radiobutton_YA);
                            boolean ya = false;
                            if (yaButton != null) {
                                ya = yaButton.isChecked();
                            }
                            RadioButton anyButton = ((AlertDialog) dialog)
                                    .findViewById(R.id.radiobutton_AE);
                            boolean any = false;
                            if (anyButton != null) {
                                any = anyButton.isChecked();
                            }

                            EditText shelter = ((AlertDialog) dialog)
                                    .findViewById(R.id.editText_filterName);
                            String shelterName = "";
                            if (shelter != null) {
                                shelterName = shelter.getText().toString();
                            }

                            ShelterListController newSla = new ShelterListController(shelterName,
                                    male, female, fwn, child, ya, any);
                            recyclerViewAdapter.setmValues(newSla.getShelterData());
                        }
                    })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // do nothing
                                }
                            })
                            .setView(mInflater.inflate(R.layout.alertdialog_search,
                                    null)).show();

            }
        });


        View recyclerView = findViewById(R.id.dataitem_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private List<Shelter> mValues;

        private SimpleItemRecyclerViewAdapter(Collection<Shelter> items) {
            List<Shelter> arr = new ArrayList<>();
            arr.addAll(items);
            mValues = arr;
        }

        private void setmValues(Collection<Shelter> newValues) {
            List<Shelter> arr = new ArrayList<>();
            arr.addAll(newValues);
            mValues = arr;
            notifyDataSetChanged();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.dataitem_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            holder.mItem = mValues.get(position);
            holder.mIdView.setText("" + mValues.get(position).getKey());
            holder.mContentView.setText(mValues.get(position).getName());
            LinearLayout bottomSheet = findViewById(R.id.linear_layout_bottom_sheet_theme_details);
            final TextView peekText = findViewById(R.id.textview_peek_shelterdetails);
            final TextView contentText = findViewById(R.id.textview_content_shelterdetails);
            final BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    peekText.setText("" + mValues.get(position).getName());
                    contentText.setText("\nCapacity: " + mValues.get(position).getCapacity() +
                            "\nRestricitons: " + mValues.get(position).getRestrictions() +
                            "\nLongitude: " + mValues.get(position).getLongitude() +
                            "\nLattitude: " + mValues.get(position).getLatitude() +
                            "\nAddress: " + mValues.get(position).getAddress() +
                            "\nPhone Number: " + mValues.get(position).getNumber());
                    Button bedsButton = findViewById(R.id.button_beds);
                    bedsButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent vacancyIntent = new Intent(ShelterListActivity.this, VacancyView.class);
                            vacancyIntent.putExtra("Shelter", mValues.get(position));
                            startActivity(vacancyIntent);
                            finish();
                        }
                    });
                }

            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private final View mView;
            private final TextView mIdView;
            private final TextView mContentView;
            private Shelter mItem;

            private ViewHolder(View view) {
                super(view);
                mView = view;
                mIdView = view.findViewById(R.id.id);
                mContentView = view.findViewById(R.id.content);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mContentView.getText() + "'";
            }
        }
    }
}