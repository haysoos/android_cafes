package com.yahoo.cafes;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

import com.yahoo.cafes.models.MenuItem;

public class RatingActivity extends Activity {

	private MenuItem menuItem;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rating);
		
		Bundle bundle = getIntent().getExtras();
		
		if (bundle != null) {
			menuItem = (MenuItem) bundle.get("menuItem");
			setTitle(menuItem.getTitle());
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.rating, menu);
		return true;
	}

}
