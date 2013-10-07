package com.yahoo.cafes.models;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Cafe {

	private static Cafe instance = new Cafe();
	private static List<Location> locations;

	private Cafe(){
		locations = new ArrayList<Location>();
	}

	public static Cafe getInstance() {
		return instance;
	}

	public int getNumberOfLocations() {
		return locations.size();
	}

	public void loadLocations(JSONArray jsonArray) throws JSONException {
		for(int i=0; i<jsonArray.length(); i++){
			JSONArray namesArray = jsonArray.getJSONObject(i).names();
			JSONObject nextLevelJson = jsonArray.getJSONObject(i).getJSONObject(namesArray.getString(0));
			for(Mealtime mealtime : Mealtime.values()){
				if(!nextLevelJson.isNull(mealtime.toString())){
					JSONObject mealLevelJson = nextLevelJson.getJSONObject(mealtime.toString());
					Location location = new Location(mealLevelJson.getJSONObject("location"));
					location.loadMenus(mealLevelJson.getJSONArray("menus"));
					locations.add(location);
				}
			}
			
		}
	}

	public List<Location> getLocations() {
		return locations;
	}
	
	

}
