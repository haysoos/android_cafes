package com.yahoo.cafes.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yahoo.cafes.R;
import com.yahoo.cafes.models.Menu;
import com.yahoo.cafes.models.MenuItem;

public class MenuItemArrayAdapter extends ArrayAdapter<MenuItem> {

	public MenuItemArrayAdapter(Context context, List<MenuItem> objects) {
		super(context, R.layout.activity_menu, objects);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout v = (LinearLayout) vi.inflate(R.layout.listview_item, null);
		MenuItem menuItem = this.getItem(position);
		TextView tvItemTitle = (TextView) v.findViewById(R.id.tvLocation);
		tvItemTitle.setText(menuItem.getTitle() + " " + menuItem.getStringFormattedRating());
		return v;
	}

}
