package com.bey2ollak.rafaeladel.bey2ollaktask.home.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bey2ollak.rafaeladel.bey2ollaktask.R;
import com.bey2ollak.rafaeladel.bey2ollaktask.base.models.Place;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rafael Adel on 9/29/2017.
 */

public class PlacesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_DATA = 0;
    private static final int VIEW_TYPE_SEPARATOR = 1;
    private List<Place> places = new ArrayList<>();

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if(viewType == VIEW_TYPE_SEPARATOR) {
            View view = layoutInflater.inflate(R.layout.separator, parent,false);
            return new SeparatorViewHolder(view);
        } else {
            View view = layoutInflater.inflate(R.layout.place_item, parent, false);
            return new DataViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(places.get(position) != null) {
            ((DataViewHolder) holder).populate(places.get(position));
        } else {

        }
    }

    @Override
    public int getItemViewType(int position) {
        if(places.get(position) == null) {
            return VIEW_TYPE_SEPARATOR;
        } else {
            return VIEW_TYPE_DATA;
        }
    }

    @Override
    public int getItemCount() {
        return places.size();
    }

    public void setPlaces(List<Place> places) {
        this.places = places;
    }

    class DataViewHolder extends RecyclerView.ViewHolder {
        private TextView placeName;
        private TextView placeAddress;

        public DataViewHolder(View itemView) {
            super(itemView);
            placeName = itemView.findViewById(R.id.place_name);
            placeAddress = itemView.findViewById(R.id.place_address);
        }

        public void  populate(Place place) {
            placeName.setText(place.getName());
            placeAddress.setText(place.getAddress());
        }
    }

    class SeparatorViewHolder extends RecyclerView.ViewHolder {

        public SeparatorViewHolder(View itemView) {
            super(itemView);
        }
    }
}
