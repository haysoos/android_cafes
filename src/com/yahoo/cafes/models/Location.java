package com.yahoo.cafes.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Location implements Serializable {

	private static final long serialVersionUID = 2923800663883574948L;
	private static final String MENU = "menu";
	private int locationId;
	private String menuDate;
	private String name;
	private String mealtime;
	private String createdAt;
	private String updatedAt;
	private List<Menu> menus;
	
	public Location() {
		menus = new ArrayList<Menu>();
	}
	public Location(JSONObject json) throws JSONException {
		this();
		
		locationId = json.getInt("id");
		menuDate = json.getString("menu_date");
		name = json.getString("name");
		mealtime = json.getString("mealtime");
		createdAt = json.getString("created_at");
		updatedAt = json.getString("updated_at");
	}
	public int getLocationId() {
		return locationId;
	}
	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}
	public String getMenuDate() {
		return menuDate;
	}
	public void setMenuDate(String menuDate) {
		this.menuDate = menuDate;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMealtime() {
		return mealtime;
	}
	public void setMealtime(String mealtime) {
		this.mealtime = mealtime;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	public String getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}
	public int getNumberOfMenus() {
		return menus.size();
	}
	public void loadMenus(JSONArray jsonArray) throws JSONException {
		for(int i=0; i<jsonArray.length(); i++){
			Menu menu = new Menu(jsonArray.getJSONObject(i).getJSONObject(MENU));
			menu.loadMenuItems(jsonArray.getJSONObject(i).getJSONArray("menu_items"));
			menus.add(menu);
			
		}
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((createdAt == null) ? 0 : createdAt.hashCode());
		result = prime * result + locationId;
		result = prime * result
				+ ((mealtime == null) ? 0 : mealtime.hashCode());
		result = prime * result
				+ ((menuDate == null) ? 0 : menuDate.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((updatedAt == null) ? 0 : updatedAt.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Location other = (Location) obj;
		if (createdAt == null) {
			if (other.createdAt != null)
				return false;
		} else if (!createdAt.equals(other.createdAt))
			return false;
		if (locationId != other.locationId)
			return false;
		if (mealtime == null) {
			if (other.mealtime != null)
				return false;
		} else if (!mealtime.equals(other.mealtime))
			return false;
		if (menuDate == null) {
			if (other.menuDate != null)
				return false;
		} else if (!menuDate.equals(other.menuDate))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (updatedAt == null) {
			if (other.updatedAt != null)
				return false;
		} else if (!updatedAt.equals(other.updatedAt))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Location [locationId=" + locationId + ", menuDate=" + menuDate
				+ ", name=" + name + ", mealtime=" + mealtime + ", createdAt="
				+ createdAt + ", updatedAt=" + updatedAt + "]";
	}
	public List<Menu> getMenus() {
		return menus;
	}
	
}
