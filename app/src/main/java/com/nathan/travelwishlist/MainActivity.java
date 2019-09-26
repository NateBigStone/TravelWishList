package com.nathan.travelwishlist;

import androidx.appcompat.app.AppCompatActivity;
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

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements WishListClickListener {

    private RecyclerView mWishListRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private Button mAddButton;
    private EditText mNewPlaceNameEditText;

    private List<String> mPlaces;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPlaces = new ArrayList<>();

        mWishListRecyclerView = findViewById(R.id.wish_list);
        mAddButton = findViewById(R.id.add_place_button);
        mNewPlaceNameEditText = findViewById(R.id.new_place_name);

        mWishListRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mWishListRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new WishListAdapter(mPlaces, this);
        mWishListRecyclerView.setAdapter(mAdapter);

        mAddButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String newPlace = mNewPlaceNameEditText.getText().toString();
                if (newPlace.isEmpty()) {
                    return;
                }
                mPlaces.add(newPlace);
                mAdapter.notifyItemInserted(mPlaces.size() -1);
                mNewPlaceNameEditText.getText().clear();
            }
        });
    }

    @Override
    public void onListClick(int position) {
        System.out.println(position);
        String place = mPlaces.get(position);
        System.out.println(place);
        Uri locationUri = Uri.parse("geo:0,0?q=" + Uri.encode(place));
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, locationUri );
        startActivity(mapIntent);
    }

    @Override
    public void onListLongClick(int position) {
        final int itemPosition = position;
        AlertDialog confirmDeleteDialog = new AlertDialog.Builder(this)
                .setMessage(getString(R.string.delete_place_message, mPlaces.get(position) ))
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
