package com.qubic.grabsimulation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.qubic.grabsimulation.api.model.entity.TopUpType;

import java.util.List;

import com.qubic.grabsimulation.R;

/**
 * Created by dennyho on 5/1/17.
 */

public class TopUpAdapter extends ArrayAdapter<TopUpType> {
    private static class ViewHolder {
        private TextView itemView;
    }

    public TopUpAdapter(Context context, int textViewResourceId, List<TopUpType> items) {
        super(context, textViewResourceId, items);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (null == convertView) {
            convertView = LayoutInflater.from(this.getContext())
                    .inflate(R.layout.activity_listview, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.itemView = (TextView) convertView.findViewById(R.id.label);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        TopUpType item = getItem(position);
        viewHolder.itemView.setText(String.format("%s", item.getName()));


        return convertView;
    }
}
