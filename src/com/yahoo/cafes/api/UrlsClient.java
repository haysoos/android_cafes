package com.yahoo.cafes.api;

import org.json.JSONArray;
import org.json.JSONException;

import android.util.Log;
import android.widget.ArrayAdapter;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.yahoo.cafes.models.Cafe;
import com.yahoo.cafes.models.Comment;
import com.yahoo.cafes.models.Location;
import com.yahoo.cafes.models.MenuItem;

public class UrlsClient extends AsyncHttpClient {

	private static UrlsClient instance = new UrlsClient();
	private static final String CAFES_BASE_URL="http://api.reviewr.mail.vip.gq1.yahoo.net/";
	
	public static UrlsClient getInstance() {
		return instance;
	}
	
	private UrlsClient(){
	}

	public void loadCafeFromApi(final ArrayAdapter<Location> adapter) {
	
		RequestParams params = new RequestParams();
		params.put("date", "9-10");
		//String url = urlBuilder("today.json");
		String url = urlBuilder("all_by_date.json");
		this.get(url, params, new JsonHttpResponseHandler(){
			
			@Override
			public void onSuccess(JSONArray jsonArray){
				try {
					Log.d("DEBUG", jsonArray.toString());
					Cafe.getInstance().loadLocations(jsonArray);
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
					Log.d("DEBUG", menuItem.getComments().get(0).toString());
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

}
