package com.yahoo.cafes;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;

import com.yahoo.cafes.fragments.UserRatingFragment;
import com.yahoo.cafes.models.Cafe;
import com.yahoo.cafes.models.MenuItem;

public class UserRatingActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_rating);
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			UserRatingFragment userRatingFragment = new UserRatingFragment();
			
			MenuItem menuItem = (MenuItem) bundle.get("menuItem");
			menuItem = Cafe.getInstance().getMenuItemById(menuItem.getMenuItemId());
			userRatingFragment.setMenuItem(menuItem);
			loadUserRatingFragment(userRatingFragment);
		} else {
			Log.d("DEBUG", "Menu Item is null");
		}
		
	}

	private void loadUserRatingFragment(UserRatingFragment userRatingFragment) {
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.frameLayout1, userRatingFragment);
		ft.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user_rating, menu);
		return true;
	}

}
