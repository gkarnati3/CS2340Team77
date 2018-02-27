package edu.gatech.cs2340.homefullshelter.view;

/**
 * Created by gkarnati3 on 2/25/18.
 */

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import edu.gatech.cs2340.homefullshelter.model.DataItem;
import edu.gatech.cs2340.homefullshelter.model.Model;
import edu.gatech.cs2340.homefullshelter.R;

/**
 * A fragment representing a single DataItem detail screen.
 * This fragment is either contained in a {@link DataItemListActivity}
 * in two-pane mode (on tablets) or a {@link DataItemDetailActivity}
 * on handsets.
 */
public class DataItemDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private DataItem mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public DataItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            int item_id = getArguments().getInt(ARG_ITEM_ID);
            Log.d("MYAPP", "Start details for: " + item_id);
            mItem = Model.getInstance().findItemById(item_id);

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.getShelter());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dataitem_detail, container, false);
        Log.d("MYAPP", "Getting ready to set data");
        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            Log.d("MYAPP", "Getting ready to set key");
            ((TextView) rootView.findViewById(R.id.key)).setText("" + mItem.getKey());
            Log.d("MYAPP", "Getting ready to set shelter");
            ((TextView) rootView.findViewById(R.id.shelter)).setText(mItem.getShelter());
            ((TextView) rootView.findViewById(R.id.capacity)).setText(mItem.getCapacity());
            ((TextView) rootView.findViewById(R.id.restrictions)).setText(mItem.getRestrictions());
            //TODO ((TextView) rootView.findViewById(R.id.longitude)).setText((int) mItem.getLongitude());
            //TODO((TextView) rootView.findViewById(R.id.latitude)).setText((int) mItem.getLatitude());
            ((TextView) rootView.findViewById(R.id.address)).setText(mItem.getAddress());
            ((TextView) rootView.findViewById(R.id.notes)).setText(mItem.getNotes());
            ((TextView) rootView.findViewById(R.id.numbers)).setText(mItem.getNumber());
        }

        return rootView;
    }
}
