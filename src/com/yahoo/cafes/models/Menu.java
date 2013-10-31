package com.yahoo.cafes.models;

import com.yahoo.cafes.R;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Menu implements Serializable {
	
	private static final long serialVersionUID = 7606341235699903458L;
	private int menuId;
	private int locationId;
	private String station;
	private String createdAt;
	private String updatedAt;
	private List<MenuItem> menuItems;
	
	public Menu(JSONObject json) throws JSONException {
		this();
		
		menuId = json.getInt("id");
		locationId = json.getInt("location_id");
		station = json.getString("station");
		createdAt = json.getString("created_at");
		updatedAt = json.getString("updated_at");
	}
	
	public Menu() {
		menuItems = new ArrayList<MenuItem>();
	}
	
	public int getMenuId() {
		return menuId;
	}
	
	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}
	
	public int getLocationId() {
		return locationId;
	}
	
	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}
	
	public String getStation() {
		return station;
	}
	
	public void setStation(String station) {
		this.station = station;
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
	
	public int getCountOfMenuItems() {
		return menuItems.size();
	}
	
	public void loadMenuItems(JSONArray jsonArray) throws JSONException {
		for(int i=0; i<jsonArray.length(); i++){
			menuItems.add(new MenuItem(jsonArray.getJSONObject(i)));
		}
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((createdAt == null) ? 0 : createdAt.hashCode());
		result = prime * result + locationId;
		result = prime * result + menuId;
		result = prime * result
				+ ((menuItems == null) ? 0 : menuItems.hashCode());
		result = prime * result + ((station == null) ? 0 : station.hashCode());
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
		Menu other = (Menu) obj;
		if (createdAt == null) {
			if (other.createdAt != null)
				return false;
		} else if (!createdAt.equals(other.createdAt))
			return false;
		if (locationId != other.locationId)
			return false;
		if (menuId != other.menuId)
			return false;
		if (menuItems == null) {
			if (other.menuItems != null)
				return false;
		} else if (!menuItems.equals(other.menuItems))
			return false;
		if (station == null) {
			if (other.station != null)
				return false;
		} else if (!station.equals(other.station))
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
		return "Menu [menuId=" + menuId + ", locationId=" + locationId
				+ ", station=" + station + ", createdAt=" + createdAt
				+ ", updatedAt=" + updatedAt +"]";
	}

	public List<MenuItem> getMenuItems() {
		return menuItems;
	}
}
