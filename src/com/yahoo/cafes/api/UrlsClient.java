package com.yahoo.cafes.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.yahoo.cafes.MainActivity;
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

		RequestParams params = loadParameters();
		String url = getUrl();
		this.get(url, params, new JsonHttpResponseHandler(){

			@Override
			public void onSuccess(JSONArray jsonArray){
				try {
					Log.d("DEBUG", jsonArray.toString());
					Cafe.getInstance().loadLocations(jsonArray);
					//notifyUserOfFavoriteItem(context);
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

	private String getUrl() {
				String url = urlBuilder("today.json");
//		String url = urlBuilder("all_by_date.json");
		return url;
	}

	private RequestParams loadParameters() {
		RequestParams params = new RequestParams();
//		params.put("date", "9-10");
		return params;
	}

	public void notifyUserOfFavoriteItem(Context context) {
		for (String favorite : User.getInstance().getFavorites()) {
			if (Cafe.getBackgroundInstance().containsMenuItem(favorite)) {
				notificationCreation(favorite, context);
			}
		}
	}

	private void notificationCreation(String menuItemTitle, Context context) {

		// prepare intent which is triggered if the
		// notification is selected

		Intent intent = new Intent(context, MainActivity.class);
		intent.putExtra("location", Cafe.getBackgroundInstance().getLocationForMenuItem(menuItemTitle).getLocationId());
		PendingIntent pIntent = PendingIntent.getActivity(context, menuItemTitle.hashCode(), intent, 0);

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

	public void backgroundNotification(Context applicationContext) {
		JSONArray json = connect(getUrl(), loadParameters());//"http://api.reviewr.mail.vip.gq1.yahoo.net/all_by_date.json?date=9-10");
		try {
			Cafe.getBackgroundInstance().loadLocations(json);
			//Log.d("DEBUG", json.toString());
			if (User.getInstance().getFavorites() == null) {
				Log.d("DEBUG", "favorites are not loaded");
				User.getInstance().cloneFavorites(PreferenceManager.getDefaultSharedPreferences(applicationContext));
			}
			Log.d("DEBUG", Arrays.toString(User.getInstance().getFavorites().toArray()));
			UrlsClient.getInstance().notifyUserOfFavoriteItem(applicationContext);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private static JSONArray connect(String url, RequestParams requestParams) {

		HttpClient httpclient = new DefaultHttpClient();

		// Prepare a request object
//		HttpGet httpget = new HttpGet(url + "?date=9-10");
		HttpGet httpget = new HttpGet(url);

		HttpParams params = new BasicHttpParams();
		params.setParameter("date", "9-10");
		//httpget.setParams(params);

		// Execute the request
		HttpResponse response;

		InputStream instream = null;
		try {
			response = httpclient.execute(httpget);
			// Examine the response status
			Log.d("DEBUG",response.getStatusLine().toString());

			// Get hold of the response entity
			HttpEntity entity = response.getEntity();
			// If the response does not enclose an entity, there is no need
			// to worry about connection release

			if (entity != null) {

				// A Simple JSON Response Read
				instream = entity.getContent();
				String result= convertStreamToString(instream);
				//Log.d("DEBUG",result);

				// A Simple JSONObject Creation
				JSONArray json=new JSONArray(result);
				return json;
			}

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		} finally {
			// Closing the input stream will trigger connection release
			try {
				instream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	private static String convertStreamToString(InputStream is) {
		/*
		 * To convert the InputStream to String we use the BufferedReader.readLine()
		 * method. We iterate until the BufferedReader return null which means
		 * there's no more data to read. Each line will appended to a StringBuilder
		 * and returned as String.
		 */
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

}
