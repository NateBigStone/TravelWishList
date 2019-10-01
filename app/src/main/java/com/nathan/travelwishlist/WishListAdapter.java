package com.nathan.travelwishlist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WishListAdapter extends RecyclerView.Adapter<WishListAdapter.WishListViewHolder> {

    private List<Place> data;

    private WishListClickListener listener;

    public WishListAdapter(List<Place> data, WishListClickListener listener) {
        this.listener = listener;
        this.data = data;
    }

    static class WishListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        LinearLayout layout;
        TextView nameTextView;
        TextView dateCreatedTextView;
        WishListClickListener listener;

        WishListViewHolder(LinearLayout layout, WishListClickListener listener) {
            super(layout);
            this.listener = listener;
            this.layout = layout;
            nameTextView = layout.findViewById(R.id.placeNameTextView);
            dateCreatedTextView = layout.findViewById(R.id.dateCreatedTextView);

            layout.setOnClickListener(this);
            layout.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view){

            listener.onListClick(getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View view){
            listener.onListLongClick(getAdapterPosition());
            return true;
        }

    }

    @NonNull
    @Override
    public WishListAdapter.WishListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout layout = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.wish_list_element, parent, false);

        //Create a new viewHolder, to contain this TextView
        WishListViewHolder viewHolder = new WishListViewHolder(layout, listener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull WishListAdapter.WishListViewHolder holder, int position) {

        Place place = data.get(position);
        holder.nameTextView.setText(place.getName());
        holder.dateCreatedTextView.setText("Date Created: " + place.getDateCreated());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}
