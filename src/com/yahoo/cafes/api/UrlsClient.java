package com.yahoo.cafes.api;

import org.json.JSONArray;
import org.json.JSONException;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.yahoo.cafes.models.Cafe;

public class UrlsClient extends AsyncHttpClient {

	private static UrlsClient instance;
	private static final String CAFES_BASE_URL="http://api.reviewr.mail.vip.gq1.yahoo.net/";
	
	public static UrlsClient getInstance() {
		return instance;
	}

	public void loadCafeFromApi() {
		
		this.get(urlBuilder("today.json"), new JsonHttpResponseHandler(){
			
			@Override
			public void onSuccess(JSONArray jsonArray){
				try {
					Cafe.getInstance().loadLocations(jsonArray);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
		
	}

	private String urlBuilder(String endpoint) {
		return CAFES_BASE_URL + endpoint;
	}

}
