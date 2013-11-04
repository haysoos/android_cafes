package com.yahoo.cafes;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.tonicartos.widget.stickygridheaders.StickyGridHeadersGridView;
import com.yahoo.cafes.adapters.MenuWithHeadersAdapter;
import com.yahoo.cafes.models.Location;

public class MenuWithItemsActivity extends Activity {

	private StickyGridHeadersGridView listView;
	private Location location;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu_with_items);
		listView = (StickyGridHeadersGridView) findViewById(R.id.asset_grid);
		
		Bundle bundle = getIntent().getExtras();
		if (bundle != null){
			location = (Location) bundle.get("location");
			setTitle(location.getName());
			Log.d("DEBUG", location.toString());
			//Set background based on selected location
			if(locationNameContains("url's")) {
				setBackgroundImage(R.drawable.bldg_urls_clear_tall);
			} else if (locationNameContains("bldg e")) {
				setBackgroundImage(R.drawable.bldg_e_clear_tall);
			} else if (locationNameContains("bldg f")) {
				setBackgroundImage(R.drawable.bldg_f_clear_tall);
			} else if (locationNameContains("bldg g")){
				setBackgroundImage(R.drawable.bldg_g_clear_tall);
			} else {
				setBackgroundImage(R.drawable.peppers_clear);
			}
			MenuWithHeadersAdapter adapter = new MenuWithHeadersAdapter(this.getApplicationContext(), location);
			listView.setAdapter(adapter);	
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_with_items, menu);
		return true;
	}
	
	private void setBackgroundImage(int resourceId) {
		getWindow().getDecorView().setBackgroundResource(resourceId);
	}

	private boolean locationNameContains(String substring) {
		return location.getName().toLowerCase().contains(substring);
	}

}
