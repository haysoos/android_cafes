package com.yahoo.cafes.adapters;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yahoo.cafes.R;
import com.yahoo.cafes.models.Location;

public class LocationArrayAdapter extends ArrayAdapter<Location>{

	public LocationArrayAdapter(Context context, List<Location> locations) {
		super(context, R.layout.activity_main, locations);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout v = (LinearLayout) vi.inflate(R.layout.listview_item, null);
		Location location = this.getItem(position);
		TextView tvItemTitle = (TextView) v.findViewById(R.id.tvItem);
		//TextView tvItemDesc = (TextView) v.findViewById(R.id.tvItemDescription);
		tvItemTitle.setText(location.getName() + " " + location.getMealtime());
		//tvItemDesc.setText(location.getDescription());
		tvItemTitle.setTextColor(Color.WHITE);
		return v;
	}
}
