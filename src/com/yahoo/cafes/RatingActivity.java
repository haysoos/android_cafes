package com.yahoo.cafes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.yahoo.cafes.adapters.CommentsArrayAdapter;
import com.yahoo.cafes.api.UrlsClient;
import com.yahoo.cafes.models.Comment;
import com.yahoo.cafes.models.MenuItem;

public class RatingActivity extends Activity {

	private MenuItem menuItem;
	private TextView tvDescription;
	private TextView tvMenuItemName;
	private ImageView ivRating;
	private TextView tvReviewers;
	private TextView tvRatingText;
	private ArrayAdapter<Comment> adapter;
	private ListView lvComments;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rating);
		
		Bundle bundle = getIntent().getExtras();
		
		if (bundle != null) {
			menuItem = (MenuItem) bundle.get("menuItem");
			setTitle(menuItem.getTitle());
			
			adapter = new CommentsArrayAdapter(getApplicationContext(), menuItem.getComments());
			loadComments(adapter);
			lvComments = (ListView) findViewById(R.id.lvComments);
			lvComments.setAdapter(adapter);
			
			tvMenuItemName = (TextView) findViewById(R.id.tvMenuItemName);
			tvMenuItemName.setText(menuItem.getTitle());
			
			tvDescription = (TextView) findViewById(R.id.tvDescription);
			if (menuItem.getDescription() != null) {
				tvDescription.setText(menuItem.getDescription());
			} else {
				tvDescription.setText("no description provided");
			}
			
			tvReviewers = (TextView) findViewById(R.id.tvReviewers);
			if (menuItem.getNumberOfReviewers() == 1){
				tvReviewers.setText(menuItem.getNumberOfReviewers() + " reviewer");
			} else {
				tvReviewers.setText(menuItem.getNumberOfReviewers() + " reviewers");
			}
			
			tvRatingText = (TextView) findViewById(R.id.tvRatingText);
			tvRatingText.setText(menuItem.getStringFormattedRating() + "/5");
			
			
			ivRating = (ImageView) findViewById(R.id.ivRatingImage);
			ivRating.setImageResource(this.getApplicationContext().getResources().getIdentifier(menuItem.getRatingImageName(), "drawable", this.getApplicationContext().getPackageName()));
			
			ivRating.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					clickedOnRatingImage();
				}
			});
		}
	}

	private void loadComments(ArrayAdapter<Comment> adapter) {
		UrlsClient.getInstance().loadCommentsForMenuItem(menuItem, adapter);
	}

	private void clickedOnRatingImage() {
		Toast.makeText(getApplicationContext(), "Clicked on Rating Image", Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(getBaseContext(), UserRatingActivity.class);
		intent.putExtra("menuItem", menuItem);
		
		startActivity(intent);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.rating, menu);
		return true;
	}

}
