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

import com.yahoo.cafes.adapters.MenuItemArrayAdapter;
import com.yahoo.cafes.models.MenuItem;

public class MenuItemsActivity extends Activity {

	private com.yahoo.cafes.models.Menu menu;
	private ListView lvMenus;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu_items);
		
		lvMenus = (ListView) this.findViewById(R.id.lvMenuItems);
		Bundle bundle = getIntent().getExtras();
		if (bundle != null){
			menu = (com.yahoo.cafes.models.Menu) bundle.get("menu");
			setTitle(menu.getStation());
			Log.d("DEBUG", menu.toString());
			
			ArrayAdapter<MenuItem> menuAdapter = 
					new MenuItemArrayAdapter(getApplicationContext(), menu.getMenuItems());
			lvMenus.setAdapter(menuAdapter);
			
			lvMenus.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					clickedOnMenuItem(position);
				}
			});
		}
	}
	
	private void clickedOnMenuItem(int position) {
		MenuItem menuItem = (MenuItem) lvMenus.getItemAtPosition(position);
		Intent intent = new Intent(getBaseContext(), RatingActivity.class);
		intent.putExtra("menuItem", menuItem);
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_items, menu);
		return true;
	}

}
