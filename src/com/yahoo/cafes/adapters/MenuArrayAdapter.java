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

public class MenuArrayAdapter extends ArrayAdapter<Menu> {

	public MenuArrayAdapter(Context context, List<Menu> objects) {
		super(context, R.layout.activity_menu, objects);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout v = (LinearLayout) vi.inflate(R.layout.menu_listview_item, null);
		Menu menu = this.getItem(position);
		TextView tvMenuName = (TextView) v.findViewById(R.id.tvMenuName);
		tvMenuName.setText(menu.getStation());
		TextView tvItemCount = (TextView) v.findViewById(R.id.tvItemNumber);
		tvItemCount.setText(menu.getCountOfMenuItems() + " items");
		return v;
	}

}
