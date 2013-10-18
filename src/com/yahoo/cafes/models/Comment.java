package com.yahoo.cafes.models;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

public class Comment implements Serializable {

	private static final long serialVersionUID = 3754671063263598893L;
	private int commentId;
	private int userId;
	private int menuItemId;
	private String text;
	private String createdAt;
	private String updatedAt;
	private String username;
	private int userRating;
	
	public Comment(JSONObject json) throws JSONException {
		
		commentId = json.getInt("id");
		userId = json.getInt("user_id");
		menuItemId = json.getInt("menu_item_id");
		text = json.getString("text");
		createdAt = json.getString("created_at");
		updatedAt = json.getString("updated_at");
		username = json.getString("username");
		
		if (!json.isNull("user_rating")) {
			userRating = json.getInt("user_rating");
		}
		
	}
	
	public Comment() {
	}

	public int getCommentId() {
		return commentId;
	}
	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getMenuItemId() {
		return menuItemId;
	}
	public void setMenuItemId(int menuItemId) {
		this.menuItemId = menuItemId;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
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
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getUserRating() {
		return userRating;
	}
	public void setUserRating(int userRating) {
		this.userRating = userRating;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + commentId;
		result = prime * result
				+ ((createdAt == null) ? 0 : createdAt.hashCode());
		result = prime * result + menuItemId;
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		result = prime * result
				+ ((updatedAt == null) ? 0 : updatedAt.hashCode());
		result = prime * result + userId;
		result = prime * result + userRating;
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
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
		Comment other = (Comment) obj;
		if (commentId != other.commentId)
			return false;
		if (createdAt == null) {
			if (other.createdAt != null)
				return false;
		} else if (!createdAt.equals(other.createdAt))
			return false;
		if (menuItemId != other.menuItemId)
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		if (updatedAt == null) {
			if (other.updatedAt != null)
				return false;
		} else if (!updatedAt.equals(other.updatedAt))
			return false;
		if (userId != other.userId)
			return false;
		if (userRating != other.userRating)
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Comment [commentId=" + commentId + ", userId=" + userId
				+ ", menuItemId=" + menuItemId + ", text=" + text
				+ ", createdAt=" + createdAt + ", updatedAt=" + updatedAt
				+ ", username=" + username + ", userRating=" + userRating + "]";
	}

}
