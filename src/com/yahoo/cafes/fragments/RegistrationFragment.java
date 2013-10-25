package com.yahoo.cafes.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.yahoo.cafes.R;
import com.yahoo.cafes.api.UrlsClient;
import com.yahoo.cafes.models.Comment;
import com.yahoo.cafes.models.User;

public class RegistrationFragment extends Fragment{

	private Button submitButton;
	private EditText etUserEmail;
	private ProgressBar progressBar;
	private SharedPreferences preferences;
	private Comment comment;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Defines the xml file for the fragment
		View view = inflater.inflate(R.layout.fragment_registration, container, false);
		// Setup handles to view objects here

		progressBar = (ProgressBar) view.findViewById(R.id.progressBar1);
		progressBar.setVisibility(View.INVISIBLE);
		etUserEmail = (EditText) view.findViewById(R.id.editText1);
		submitButton = (Button) view.findViewById(R.id.button1);
		submitButton.setText("Submit");
		submitButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				requestUserToken();
			}
		});
		return view;
	}

	protected void requestUserToken() {
		progressBar.setVisibility(View.VISIBLE);
		User.getInstance().setUsername(etUserEmail.getText().toString());
		UrlsClient.getInstance().createUserCredentials(User.getInstance(), preferences, progressBar);
		UrlsClient.getInstance().postComment(comment);
		getActivity().finish();
	}

	public void setPreferences(SharedPreferences preferences) {
		this.preferences = preferences;
	}
	
	public void setComment(Comment comment) {
		this.comment = comment;
	}

}
