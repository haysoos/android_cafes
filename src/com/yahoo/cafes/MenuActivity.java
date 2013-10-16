package com.yahoo.cafes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.yahoo.cafes.adapters.MenuArrayAdapter;
import com.yahoo.cafes.models.Location;

public class MenuActivity extends Activity {

	private Location location;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		
		
		
		final ListView lvMenus = (ListView) this.findViewById(R.id.lvMenu);
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
			
			
			ArrayAdapter<com.yahoo.cafes.models.Menu> menuAdapter = 
					new MenuArrayAdapter(this.getApplicationContext(), location.getMenus());
			lvMenus.setAdapter(menuAdapter);
			
			lvMenus.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					
					com.yahoo.cafes.models.Menu menu = (com.yahoo.cafes.models.Menu) lvMenus.getItemAtPosition(position);
					Intent intent = new Intent(MenuActivity.this.getApplicationContext(), MenuItemsActivity.class);
					intent.putExtra("menu", menu);
					startActivity(intent);
				}
			});
			
		} else {
			Log.d("DEBUG", "Extras bundle is null");
		}
	}

	private void setBackgroundImage(int resourceId) {
		getWindow().getDecorView().setBackgroundResource(resourceId);
	}

	private boolean locationNameContains(String substring) {
		return location.getName().toLowerCase().contains(substring);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

}
