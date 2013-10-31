package com.yahoo.cafes.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import com.yahoo.cafes.R;

public class MenuItem implements Serializable {
	
	private static final long serialVersionUID = -5246885874438766745L;
	private int menuItemId;
	private int menuId;
	private String title;
	private String description;
	private boolean vegitarian;
	private boolean vegan;
	private boolean farmToFork;
	private boolean organic;
	private boolean glutenFree;
	private boolean seafoodWatch;
	private String createdAt;
	private String updatedAt;
	private int numberOfReviewers;
	private double rating;
	private Comment myComment;
	private List<Comment> comments;

	public MenuItem(JSONObject json) throws JSONException {
		this();
		
		menuItemId = json.getInt("id");
		menuId = json.getInt("menu_id");
		title = json.getString("title");
		description = json.getString("description");
		vegitarian = json.getBoolean("vegitarian");
		vegan = json.getBoolean("vegan");
		farmToFork = json.getBoolean("farm_to_fork");
		organic = json.getBoolean("organic");
		glutenFree = json.getBoolean("gluten_free");
		seafoodWatch = json.getBoolean("seafood_watch");
		createdAt = json.getString("created_at");
		updatedAt = json.getString("updated_at");
		numberOfReviewers = json.getInt("reviewers");
		rating = json.getDouble("rating");
		
	}

	public MenuItem() {
		comments = new ArrayList<Comment>();
	}

	public String getRatingImageName() {
		return "round_rating" + getStringFormattedRating().replace(".", "") + "_150";
	}

	public String getRatingImageNameInWhite() {
		return "round_rating" + getStringFormattedRating().replace(".", "") + "_150_white";
	}
	
	@SuppressLint("DefaultLocale")
	public String getStringFormattedRating() {	
		double roundedRating = Math.round(2.0 * rating) / 2.0;
		
		if((roundedRating - Math.floor(roundedRating)) > 0){
			return String.format("%.1f", roundedRating);
		}
		
		return String.format("%.0f", roundedRating);
	}
	
	public int getNumberOfComments() {
		return comments.size();
	}

	public void loadComments(JSONArray json) throws JSONException {
		for(int i=0; i<json.length(); i++){
			Comment comment = new Comment(json.getJSONObject(i));
			if (comment.getUserId() == User.getInstance().getUserId()) {
				setMyComment(comment);
			}
			addComment(comment);
		}
	}

	public void addComment(Comment comment) {
		comments.add(comment);
	}

	public int getMenuItemId() {
		return menuItemId;
	}

	public void setMenuItemId(int menuItemId) {
		this.menuItemId = menuItemId;
	}

	public int getMenuId() {
		return menuId;
	}

	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isVegitarian() {
		return vegitarian;
	}

	public void setVegitarian(boolean vegitarian) {
		this.vegitarian = vegitarian;
	}

	public boolean isVegan() {
		return vegan;
	}

	public void setVegan(boolean vegan) {
		this.vegan = vegan;
	}

	public boolean isFarmToFork() {
		return farmToFork;
	}

	public void setFarmToFork(boolean farmToFork) {
		this.farmToFork = farmToFork;
	}

	public boolean isOrganic() {
		return organic;
	}

	public void setOrganic(boolean organic) {
		this.organic = organic;
	}

	public boolean isGlutenFree() {
		return glutenFree;
	}

	public void setGlutenFree(boolean glutenFree) {
		this.glutenFree = glutenFree;
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

	public int getNumberOfReviewers() {
		return numberOfReviewers;
	}

	public void setNumberOfReviewers(int numberOfReviewers) {
		this.numberOfReviewers = numberOfReviewers;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public boolean isSeafoodWatch() {
		return seafoodWatch;
	}

	public void setSeafoodWatch(boolean seafoodWatch) {
		this.seafoodWatch = seafoodWatch;
	}

	public void setMyComment(Comment comment) {
		myComment = comment;
	}

	public Comment getMyComment() {
		return myComment;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((comments == null) ? 0 : comments.hashCode());
		result = prime * result
				+ ((createdAt == null) ? 0 : createdAt.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + (farmToFork ? 1231 : 1237);
		result = prime * result + (glutenFree ? 1231 : 1237);
		result = prime * result + menuId;
		result = prime * result + menuItemId;
		result = prime * result
				+ ((myComment == null) ? 0 : myComment.hashCode());
		result = prime * result + numberOfReviewers;
		result = prime * result + (organic ? 1231 : 1237);
		long temp;
		temp = Double.doubleToLongBits(rating);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + (seafoodWatch ? 1231 : 1237);
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result
				+ ((updatedAt == null) ? 0 : updatedAt.hashCode());
		result = prime * result + (vegan ? 1231 : 1237);
		result = prime * result + (vegitarian ? 1231 : 1237);
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
		MenuItem other = (MenuItem) obj;
		if (comments == null) {
			if (other.comments != null)
				return false;
		} else if (!comments.equals(other.comments))
			return false;
		if (createdAt == null) {
			if (other.createdAt != null)
				return false;
		} else if (!createdAt.equals(other.createdAt))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (farmToFork != other.farmToFork)
			return false;
		if (glutenFree != other.glutenFree)
			return false;
		if (menuId != other.menuId)
			return false;
		if (menuItemId != other.menuItemId)
			return false;
		if (myComment == null) {
			if (other.myComment != null)
				return false;
		} else if (!myComment.equals(other.myComment))
			return false;
		if (numberOfReviewers != other.numberOfReviewers)
			return false;
		if (organic != other.organic)
			return false;
		if (Double.doubleToLongBits(rating) != Double
				.doubleToLongBits(other.rating))
			return false;
		if (seafoodWatch != other.seafoodWatch)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (updatedAt == null) {
			if (other.updatedAt != null)
				return false;
		} else if (!updatedAt.equals(other.updatedAt))
			return false;
		if (vegan != other.vegan)
			return false;
		if (vegitarian != other.vegitarian)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MenuItem [menuItemId=" + menuItemId + ", menuId=" + menuId
				+ ", description=" + description+ ", title=" + title
				+ ", vegitarian=" + vegitarian + ", vegan=" + vegan
				+ ", farmToFork=" + farmToFork + ", seafoodWatch=" + seafoodWatch 
				+ ", organic=" + organic + ", glutenFree=" + glutenFree 
			    + ", rating=" + rating + ", numberOfReviewers=" + numberOfReviewers
				+ ", comments=" + comments
				+ ", createdAt=" + createdAt + ", updatedAt="
				+ updatedAt + ", myComment=" + myComment + "]";
	}

	public List<Comment> getComments() {
		return comments;
	}

}
