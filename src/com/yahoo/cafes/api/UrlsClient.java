package com.yahoo.cafes.api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.yahoo.cafes.MenuActivity;
import com.yahoo.cafes.models.Cafe;
import com.yahoo.cafes.models.Comment;
import com.yahoo.cafes.models.Location;
import com.yahoo.cafes.models.MenuItem;
import com.yahoo.cafes.models.User;

public class UrlsClient extends AsyncHttpClient {

	private static UrlsClient instance = new UrlsClient();
	private static final String CAFES_BASE_URL="http://api.reviewr.mail.vip.gq1.yahoo.net/";
	
	public static UrlsClient getInstance() {
		return instance;
	}
	
	private UrlsClient(){
	}

	public void loadCafeFromApi(final ArrayAdapter<Location> adapter, final ProgressBar progressBar, final Context context) {
	
		RequestParams params = new RequestParams();
		//params.put("date", "9-10");
		String url = urlBuilder("today.json");
		//String url = urlBuilder("all_by_date.json");
		this.get(url, params, new JsonHttpResponseHandler(){
			
			@Override
			public void onSuccess(JSONArray jsonArray){
				try {
					Log.d("DEBUG", jsonArray.toString());
					Cafe.getInstance().loadLocations(jsonArray);
					notifyUserOfFavoriteItem(context);
					adapter.notifyDataSetChanged();
				} catch (JSONException e) {
					Log.d("DEBUG", e.getMessage());
				} finally {
					progressBar.setVisibility(View.INVISIBLE);
				}
			}
			
			@Override
			public void onFailure(Throwable e){
				Log.d("DEBUG", e.getMessage());
			}
		});
		
	}

	protected void notifyUserOfFavoriteItem(Context context) {
		if (User.getInstance().isLoaded()) {
			for (String favorite : User.getInstance().getFavorites()) {
				if (Cafe.getInstance().containsMenuItem(favorite)) {
				  notificationCreation(favorite, context);
				}
			}
		}		
	}

	private void notificationCreation(String menuItemTitle, Context context) {
		
		// prepare intent which is triggered if the
		// notification is selected

		Intent intent = new Intent(context, MenuActivity.class);
		intent.putExtra("location", Cafe.getInstance().getLocationForMenuItem(menuItemTitle));
		PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent, 0);

		// build notification
		// the addAction re-use the same intent to keep the example short
		Notification n  = new NotificationCompat.Builder(context)
		        .setContentTitle("Favorite food available today")
		        .setContentText(menuItemTitle + " is available today")
		        .setSmallIcon(com.yahoo.cafes.R.drawable.ic_launcher)
		        .setContentIntent(pIntent)
		        .setAutoCancel(true).build();
		    
		  
		NotificationManager notificationManager = 
		  (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

		notificationManager.notify(menuItemTitle.hashCode(), n); 
	}

	private String urlBuilder(String endpoint) {
		return CAFES_BASE_URL + endpoint;
	}

	public void loadCommentsForMenuItem(final MenuItem menuItem, final ArrayAdapter<Comment> adapter) {
		
		String url = urlBuilder("menu_items/" + menuItem.getMenuItemId() + "/comments.json");
		this.get(url, null, new JsonHttpResponseHandler(){
			
			@Override
			public void onSuccess(JSONArray jsonArray){
				try {
					Log.d("DEBUG", jsonArray.toString());
					menuItem.loadComments(jsonArray);
					adapter.notifyDataSetChanged();
				} catch (JSONException e) {
					Log.d("DEBUG", e.getMessage());
				}
			}
			
			@Override
			public void onFailure(Throwable e){
				Log.d("DEBUG", e.getMessage());
			}
		});
		
	}

	public void createUserCredentials(final User user, final SharedPreferences preferences, final ProgressBar progressBar) {
		
		RequestParams params = new RequestParams();
		params.put("username", user.getUsername());
		String url = urlBuilder("users.json");
		this.post(url, params, new JsonHttpResponseHandler(){
			
			@Override
			public void onSuccess(JSONObject json){
				try {
					Log.d("DEBUG", json.toString());
					user.loadFromJson(json);
					user.saveToPreferences(preferences);
					//adapter.notifyDataSetChanged();
					
				} catch (JSONException e) {
					Log.d("DEBUG", e.getMessage());
				} finally {
					progressBar.setVisibility(View.INVISIBLE);
				}
			}
			
			@Override
			public void onFailure(Throwable e){
				Log.d("DEBUG", e.getMessage());
			}
		});
		
	}

	public void postComment(final Comment comment) {
		
		comment.setUserId(User.getInstance().getUserId());
		comment.setUsername(User.getInstance().getUsername());
		RequestParams params = new RequestParams();
		params.put("user_id", Integer.toString(comment.getUserId()));
		params.put("rating", Integer.toString(comment.getUserRating()));
		params.put("comment", comment.getText());
		params.put("token", User.getInstance().getToken());
		
		String url = urlBuilder("menu_items/" + comment.getMenuItemId() + "/rate.json");
		this.post(url, params, new JsonHttpResponseHandler(){
			
			@Override
			public void onSuccess(JSONObject json){
				try {
					Log.d("DEBUG", json.toString());
					comment.setCommentId(json.getInt("id"));
					//adapter.notifyDataSetChanged();
				} catch (JSONException e) {
					Log.d("DEBUG", e.getMessage());
				}
			}
			
			@Override
			public void onFailure(Throwable e){
				Log.d("DEBUG", e.getMessage());
			}
		});
	}

}
