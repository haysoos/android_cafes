package com.yahoo.cafes.adapters;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yahoo.cafes.R;
import com.yahoo.cafes.models.MenuItem;

public class MenuItemArrayAdapter extends ArrayAdapter<MenuItem> {

	public MenuItemArrayAdapter(Context context, List<MenuItem> objects) {
		super(context, R.layout.activity_menu, objects);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		RelativeLayout v = (RelativeLayout) vi.inflate(R.layout.menu_item_listview_item, null);
		MenuItem menuItem = this.getItem(position);
		TextView tvMenuItemName = (TextView) v.findViewById(R.id.tvMenuItemName);
		tvMenuItemName.setText(menuItem.getTitle());
		tvMenuItemName.setTextColor(Color.BLACK);
		
		ImageView ivRating = (ImageView) v.findViewById(R.id.ivRating);
		ivRating.setImageResource(R.drawable.round_rating15_150);
		
		return v;
	}

}
