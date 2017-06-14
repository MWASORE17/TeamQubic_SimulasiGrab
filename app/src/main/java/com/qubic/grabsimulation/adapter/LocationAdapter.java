package com.qubic.grabsimulation.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qubic.grabsimulation.R;
import com.qubic.grabsimulation.api.model.entity.Locations;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ferr on 08/05/17.
 */

public class LocationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Locations> mLocationList;
    private Context mContext;
    public LocationAdapter(Context context, List<Locations> lists) {
        mContext = context;
        this.mLocationList = lists;
    }

    @Override
    public int getItemCount() {
        return mLocationList.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.location_list, parent, false);
        viewHolder = new LocationViewHolder(itemView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        Locations location = mLocationList.get(position);
        LocationViewHolder locationViewHolder = (LocationViewHolder) holder;

        Double dist = location.getDistance();
        locationViewHolder.name.setText(location.getName());
        locationViewHolder.address.setText(location.getAddress());
        locationViewHolder.distance.setText(String.format(mContext.getString(R.string.distance_value), dist));
    }

    private class LocationViewHolder extends RecyclerView.ViewHolder {
        private TextView name, address, distance;
        public boolean favorite;
        public LocationViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.location_name);
            address = (TextView) view.findViewById(R.id.location_address);
            distance = (TextView) view.findViewById(R.id.distance);
        }
    }

    public void setFilter(List<Locations> lists) {
        mLocationList = new ArrayList<>();
        mLocationList.addAll(lists);
        notifyDataSetChanged();
    }

}
