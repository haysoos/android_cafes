package com.yahoo.cafes.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.yahoo.cafes.R;
import com.yahoo.cafes.models.Location;
import com.yahoo.cafes.models.Menu;

public class MenuWithHeadersAdapter extends BaseAdapter implements SectionIndexer {

	private final Context mContext;
	private String[] mCountries;
	private int[] mSectionIndices;
	private Character[] mSectionLetters;
	private LayoutInflater inflater;
	private Location location;

	public MenuWithHeadersAdapter(Context context, Location location) {
		this.location = location;
		mContext = context;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		int count = 0;

		for (Menu menu : location.getMenus()) {
			count += menu.getCountOfMenuItems();
		}

		return count;
	}

	@Override
	public Object getItem(int position) {
		for (Menu menu : location.getMenus()) {
			if (position > menu.getCountOfMenuItems()) {
				position -= menu.getCountOfMenuItems();
			} else {
				return menu.getMenuItems().get(position);
			}
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.test_list_item_layout, parent, false);
			holder.text = (TextView) convertView.findViewById(R.id.text);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.text.setText(mCountries[position]);

		return convertView;
	}

	@Override
	public int getPositionForSection(int section) {
		if (section >= location.getNumberOfMenus()) {
			section = location.getNumberOfMenus() - 1;
		} else if (section < 0) {
			section = 0;
		}
		return location.getMenus().indexOf(location.getMenus().get(section));
	}

	@Override
	public int getSectionForPosition(int position) {
		for (int i = 0; i < location.getNumberOfMenus(); i++) {
			if (position < location.getMenus().get(i).getCountOfMenuItems()) {
				return i - 1;
			}
		}
		return location.getNumberOfMenus() - 1;
	}

	@Override
	public Object[] getSections() {
		return location.getMenus().toArray();
	}

	public View getHeaderView(int position, View convertView, ViewGroup parent) {
		HeaderViewHolder holder;
		if (convertView == null) {
			holder = new HeaderViewHolder();
			convertView = inflater.inflate(R.layout.header, parent, false);
			holder.text = (TextView) convertView.findViewById(R.id.text1);
			convertView.setTag(holder);
		} else {
			holder = (HeaderViewHolder) convertView.getTag();
		}

		// set header text as first char in name
		char headerChar = mCountries[position].subSequence(0, 1).charAt(0);
		String headerText;
		if (headerChar % 2 == 0) {
			headerText = headerChar + "\n" + headerChar + "\n" + headerChar;
		} else {
			headerText = headerChar + "\n" + headerChar;
		}
		holder.text.setText(headerText);

		return convertView;
	}

	public long getHeaderId(int position) {
		return location.getMenus().get(position).getStation().subSequence(0, 1).charAt(0);
	}

	class HeaderViewHolder {
		TextView text;
	}

	class ViewHolder {
		TextView text;
	}

}
