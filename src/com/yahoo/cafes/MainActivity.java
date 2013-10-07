package com.yahoo.cafes;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
		
		Button btnLoad = (Button)findViewById(R.id.btnLoad);
		btnLoad.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				UrlsClient.getInstance().loadCafeFromApi(adapter);
				Log.d("DEBUG", Cafe.getInstance().getLocations().toString());
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
