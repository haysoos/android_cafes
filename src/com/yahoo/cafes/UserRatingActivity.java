package com.yahoo.cafes;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.yahoo.cafes.models.Comment;
import com.yahoo.cafes.models.MenuItem;

public class UserRatingActivity extends Activity {

	private Button btnSubmitUserRating;
	private MenuItem menuItem;
	private EditText etUserComment;
	private RatingBar rbUserRating;
	private ToggleButton tbFavorite;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_rating);
		
		loadUIElements();
		customizeViewWithMyComment();
		setActionListeners();
	}

	private void customizeViewWithMyComment() {
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			menuItem = (MenuItem) bundle.get("menuItem");
			if (menuItem.getMyComment() != null) {
				etUserComment.setText(menuItem.getMyComment().getText());
				
				if (menuItem.getMyComment().getUserRating() > 0) {
					rbUserRating.setRating((float) menuItem.getMyComment().getUserId());
				}
			}			
		} else {
			Log.d("DEBUG", "Menu Item is null");
		}
	}

	private void setActionListeners() {
		btnSubmitUserRating.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				clickedOnSubmitUserRatingButton();
			}
		});
	}

	private void loadUIElements() {
		etUserComment = (EditText) findViewById(R.id.etUserComment);
		rbUserRating = (RatingBar) findViewById(R.id.rbUserRating);
		tbFavorite = (ToggleButton) findViewById(R.id.tbFavorite);
		btnSubmitUserRating = (Button) findViewById(R.id.btnSubmitRating);
	}

	private void clickedOnSubmitUserRatingButton() {
		Toast.makeText(getBaseContext(), "Rating Submitted", Toast.LENGTH_SHORT).show();
		Comment comment = new Comment();
		comment.setMenuItemId(menuItem.getMenuItemId());
		comment.setText(etUserComment.getText().toString());
		comment.setUserRating((int) rbUserRating.getRating());
		//comment.setUserId(userId);
		//comment.setUsername(username);
		
		if (tbFavorite.isChecked()) {
			//TODO: Save this to local data
		}
		
		//TODO: POST data to server
				
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user_rating, menu);
		return true;
	}

}
