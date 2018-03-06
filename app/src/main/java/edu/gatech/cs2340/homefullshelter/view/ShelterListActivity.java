package edu.gatech.cs2340.homefullshelter.view;

/**
 * Created by gkarnati3 on 2/25/18.
 */

import android.content.DialogInterface;
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
 */
public class ShelterListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */

    ShelterListController sla = new ShelterListController();
    final SimpleItemRecyclerViewAdapter recylerviewAdapter =
            new SimpleItemRecyclerViewAdapter(sla.getShelterData());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        final Button sortButton = findViewById(R.id.button_sort);
        sortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("finderrrr");
                AlertDialog.Builder builder;
                LayoutInflater mInflater = LayoutInflater.from(v.getContext());
                builder = new AlertDialog.Builder(v.getContext());
                    builder.setTitle("Sort Shelters").setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            RadioButton maleButton =  (RadioButton) ((AlertDialog) dialog).findViewById(R.id.radiobutton_male);
                            boolean male = maleButton.isChecked();
                            RadioButton femaleButton = (RadioButton) ((AlertDialog) dialog).findViewById(R.id.radiobutton_female);
                            boolean female = femaleButton.isChecked();
                            RadioButton fwnButton = (RadioButton) ((AlertDialog) dialog).findViewById(R.id.radioButton_FWN);
                            boolean fwn = fwnButton.isChecked();
                            RadioButton childButton = (RadioButton) ((AlertDialog) dialog).findViewById(R.id.radiobutton_child);
                            boolean child = childButton.isChecked();
                            RadioButton yaButton = (RadioButton) ((AlertDialog) dialog).findViewById(R.id.radiobutton_YA);
                            boolean ya = yaButton.isChecked();
                            RadioButton anyButton = (RadioButton) ((AlertDialog) dialog).findViewById(R.id.radiobutton_AE);
                            boolean any = anyButton.isChecked();

                            EditText shelter = (EditText) ((AlertDialog) dialog).findViewById(R.id.editText_filterName);
                            String shelterName = shelter.getText().toString();

                            ShelterListController newSla = new ShelterListController(shelterName,
                                    male, female, fwn, child, ya, any);
                            recylerviewAdapter.setmValues(newSla.getShelterData());



                            System.out.println("dinder and stuff");

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


        View recyclerView = findViewById(R.id.dataitem_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(recylerviewAdapter);
    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private List<Shelter> mValues;

        public SimpleItemRecyclerViewAdapter(List<Shelter> items) {
            mValues = items;
        }

        public void setmValues(List<Shelter> newValues) {
            mValues = newValues;
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
            holder.mContentView.setText(mValues.get(position).getShelter());
            LinearLayout bottomSheet = (LinearLayout) findViewById(R.id.linear_layout_bottom_sheet_theme_details);
            final TextView peekText = (TextView) findViewById(R.id.textview_peek_shelterdetails);
            final TextView contentText = (TextView) findViewById(R.id.textview_content_shelterdetails);
            final BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    peekText.setText("" + mValues.get(position).getShelter());
                    contentText.setText("\nCapacity: " + mValues.get(position).getCapacity() +
                            "\nRestricitons: " + mValues.get(position).getRestrictions() +
                            "\nLongitude: " + mValues.get(position).getLongitude() +
                            "\nLattitude: " + mValues.get(position).getLatitude() +
                            "\nAddress: " + mValues.get(position).getAddress() +
                            "\nPhone Number: " + mValues.get(position).getNumber());
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mIdView;
            public final TextView mContentView;
            public Shelter mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mIdView = (TextView) view.findViewById(R.id.id);
                mContentView = (TextView) view.findViewById(R.id.content);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mContentView.getText() + "'";
            }
        }
    }
}