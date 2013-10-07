package com.yahoo.cafes;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.yahoo.cafes.api.UrlsClient;
import com.yahoo.cafes.models.Cafe;
import com.yahoo.cafes.models.Location;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ListView lvLocations = (ListView) findViewById(R.id.lvLocations);
		final ArrayAdapter<Location> adapter = new LocationArrayAdapter(this.getApplicationContext(), Cafe.getInstance().getLocations());
		lvLocations.setAdapter(adapter);
		UrlsClient.getInstance().loadCafeFromApi(adapter);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
