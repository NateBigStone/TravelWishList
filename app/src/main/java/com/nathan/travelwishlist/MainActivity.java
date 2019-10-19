package com.nathan.travelwishlist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class MainActivity extends AppCompatActivity implements WishListClickListener {

    private RecyclerView mWishListRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private Button mAddButton;
    private EditText mNewPlaceNameEditText;
    private EditText mNewReasonEditText;

    private List<Place> mPlaces;
    private PlaceViewModel mPlacesDatabase;
    private int mLength;

    private static String TAG = "MAIN_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPlacesDatabase = new PlaceViewModel(getApplication());
        Place example = new Place("Iowa", "To eat the Corn");
        mPlacesDatabase.insert(example);
        Log.d(TAG, "The database is: " + mPlacesDatabase);
//        mPlaces.add(new Place("Iowa", "To eat the Corn"));
//        mPlaces.add(new Place("Wisconsin", "To eat the Cheese"));
//        mPlaces.add(new Place("Alberta", "To Drink the Maple Syrup"));

        mPlacesDatabase.getAllRecords().observe(this, new Observer<List<Place>>() {

            @Override
            public void onChanged(List<Place> places) {
                mPlaces = places;
                //mLength = 5;
                mLength = places.size();
                Log.d(TAG, "Place records are: " + places);
                mAdapter.notifyItemInserted(mLength -1);
            }
        });

        mWishListRecyclerView = findViewById(R.id.wish_list);
        mAddButton = findViewById(R.id.add_place_button);
        mNewPlaceNameEditText = findViewById(R.id.new_place_name);
        mNewReasonEditText = findViewById(R.id.reason);

        mWishListRecyclerView.setHasFixedSize(false);

        mLayoutManager = new LinearLayoutManager(this);
        mWishListRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new WishListAdapter(mPlaces, mLength,this);
        mWishListRecyclerView.setAdapter(mAdapter);

        mAddButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String newPlace = mNewPlaceNameEditText.getText().toString();
                String newReason = mNewReasonEditText.getText().toString();
                if (newPlace.isEmpty() || newReason.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter a Place AND a Reason", Toast.LENGTH_LONG).show();
                    return;
                }
                Place newEntry = new Place(newPlace, newReason);
                mPlacesDatabase.insert(newEntry);
                mAdapter.notifyItemInserted(mPlaces.size() -1);
                mNewPlaceNameEditText.getText().clear();
                mNewReasonEditText.getText().clear();
            }
        });
    }

    @Override
    public void onListClick(int position) {
        System.out.println(position);
        Place place = mPlaces.get(position);
        System.out.println(place);
        Uri locationUri = Uri.parse("geo:0,0?q=" + Uri.encode(place.getName()));
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, locationUri );
        startActivity(mapIntent);
    }
    //TODO: write a remove
    @Override
    public void onListLongClick(int position) {
        final int itemPosition = position;
        AlertDialog confirmDeleteDialog = new AlertDialog.Builder(this)
                .setMessage(getString(R.string.delete_place_message, mPlaces.get(position).getName() ))
                .setTitle(getString(R.string.delete_dialog_title))
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mPlaces.remove(itemPosition);
                        mAdapter.notifyItemRemoved(itemPosition);
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .create();

        confirmDeleteDialog.show();
    }
}
