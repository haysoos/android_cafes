package com.yahoo.cafes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.yahoo.cafes.adapters.LocationArrayAdapter;
import com.yahoo.cafes.api.UrlsClient;
import com.yahoo.cafes.models.Cafe;
import com.yahoo.cafes.models.Location;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ListView lvLocations = (ListView) findViewById(R.id.lvLocations);
		final ArrayAdapter<Location> adapter = 
				new LocationArrayAdapter(this.getApplicationContext(), Cafe.getInstance().getLocations());
		lvLocations.setAdapter(adapter);
		UrlsClient.getInstance().loadCafeFromApi(adapter);
		
		lvLocations.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Location location = (Location) parent.getItemAtPosition(position);
				
				Intent intent = new Intent(MainActivity.this.getApplicationContext(), MenuActivity.class);
				intent.putExtra("location", location);
				startActivity(intent);
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
