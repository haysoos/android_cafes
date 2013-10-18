package com.yahoo.cafes.adapters;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yahoo.cafes.R;
import com.yahoo.cafes.models.Comment;

public class CommentsArrayAdapter extends ArrayAdapter<Comment> {

	public CommentsArrayAdapter(Context context, List<Comment> comments) {
		super(context, R.layout.activity_rating, comments);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		RelativeLayout v = (RelativeLayout) vi.inflate(R.layout.comment_listview_item, null);
		Comment comment = this.getItem(position);
		TextView tvComment = (TextView) v.findViewById(R.id.tvComment);
		tvComment.setText(comment.getText());
		tvComment.setTextColor(Color.BLACK);
		
		TextView tvUsername = (TextView) v.findViewById(R.id.tvUsername);
		tvUsername.setText(comment.getUsername());
		
		TextView tvUserRating = (TextView) v.findViewById(R.id.tvUserRating);
		tvUserRating.setText("rating: " + comment.getUserRating());
		return v;
	}
}
