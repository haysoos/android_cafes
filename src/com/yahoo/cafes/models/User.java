package com.yahoo.cafes.models;

import java.util.HashSet;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import com.yahoo.cafes.models.exceptions.UserNotInitialzied;

public class User {

	private static final String FAVORITES = "favorites";
	private static final int DEFAULT_USER_ID = -1;
	private static final String EMPTY_STRING = "";
	private static final String ID = "id";
	private static final String USER_ID = "userId";
	private static final String USERNAME = "username";
	private static final String TOKEN = "token";
	private static User instance = new User();
	private String username;
	private int userId;
	private String token;
	private Set<String> favorites;
	
	private User() {
		
	}
	
	public static User getInstance() {
		return instance;
	}

	public void loadFromJson(JSONObject json) throws JSONException {
		userId = json.getInt(ID);
		username = json.getString(USERNAME);
		token = json.getString(TOKEN);
	}

	public String getToken() {
		return token;
	}

	public int getUserId() {
		return userId;
	}

	public String getUsername() {
		return username;
	}

	public void saveToPreferences(SharedPreferences preferences) {
		Editor editor = preferences.edit();
		editor.putInt(USER_ID, userId);
		editor.putString(USERNAME, username);
		editor.putString(TOKEN, token);
		editor.commit();
	}
	
	public void loadFromPreferences(SharedPreferences preferences) throws UserNotInitialzied {
		if (preferences.contains(USER_ID) 
				&& preferences.contains(USERNAME)
				&& preferences.contains(TOKEN)) {
			userId = preferences.getInt(USER_ID, DEFAULT_USER_ID);
			username = preferences.getString(USERNAME, EMPTY_STRING);
			token = preferences.getString(TOKEN, EMPTY_STRING);
			favorites = preferences.getStringSet(FAVORITES, new HashSet<String>());
		} else {
			throw new UserNotInitialzied();
		}
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void addMenuItemToFavorites(String menuTitle, SharedPreferences preferences) {
		favorites.add(menuTitle);
		Editor editor = preferences.edit();
		editor.putStringSet(FAVORITES, favorites);
		editor.commit();
		Log.d("STUFF", "Supposedly added set to favorites" + preferences.getStringSet(FAVORITES, null));
	}

	public boolean isLoaded() {
		return userId > 0;
	}

	public Set<String> getFavorites() {
		return favorites;
	}

}
