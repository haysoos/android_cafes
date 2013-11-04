package com.yahoo.cafes.fragments;

import java.util.Set;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.Toast;

import com.yahoo.cafes.R;
import com.yahoo.cafes.api.UrlsClient;
import com.yahoo.cafes.models.Comment;
import com.yahoo.cafes.models.MenuItem;
import com.yahoo.cafes.models.User;
import com.yahoo.cafes.models.exceptions.UserNotInitialzied;

public class UserRatingFragment extends Fragment {

	private Button btnSubmitUserRating;
	private EditText etUserComment;
	private RatingBar rbUserRating;
	private Switch tbFavorite;
	private MenuItem menuItem;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Defines the xml file for the fragment
		View view = inflater.inflate(R.layout.fragment_user_rating, container, false);
		// Setup handles to view objects here

		loadUIElements(view);
		customizeViewWithMyComment();
		checkUserPreferncesForFavorites();
		setActionListeners();

		return view;
	}

	private void checkUserPreferncesForFavorites() {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());

		if (preferences.contains("favorites")) {
			Set<String> favorites = preferences.getStringSet("favorites", null);
			tbFavorite.setChecked(favorites.contains(menuItem.getTitle().toLowerCase()));
		}
	}

	private void customizeViewWithMyComment() {

		if (menuItem.getMyComment() != null) {
			etUserComment.setText(menuItem.getMyComment().getText());

			if (menuItem.getMyComment().getUserRating() > 0) {
				rbUserRating.setRating((float) menuItem.getMyComment().getUserId());
			}
		}
	}

	private void setActionListeners() {
		btnSubmitUserRating.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				clickedOnSubmitUserRatingButton();
			}
		});

		tbFavorite.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
				if (tbFavorite.isChecked()) {
					User.getInstance().addMenuItemToFavorites(menuItem.getTitle().toLowerCase(), preferences);
				} else {
					User.getInstance().removeMenuItemFromFavorites(menuItem.getTitle().toLowerCase(), preferences);
				}
			}
		});
	}

	private void loadUIElements(View view) {
		etUserComment = (EditText) view.findViewById(R.id.etUserComment);
		rbUserRating = (RatingBar) view.findViewById(R.id.rbUserRating);
		tbFavorite = (Switch) view.findViewById(R.id.tbFavorite);
		btnSubmitUserRating = (Button) view.findViewById(R.id.btnSubmitRating);
	}

	@SuppressLint("InlinedApi")
	private void clickedOnSubmitUserRatingButton() {

		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());

		Comment comment = new Comment();
		comment.setMenuItemId(menuItem.getMenuItemId());
		comment.setText(etUserComment.getText().toString());
		comment.setUserRating((int) rbUserRating.getRating());
		menuItem.setMyComment(comment);



		try {
			User.getInstance().loadFromPreferences(preferences);
		} catch (UserNotInitialzied e) {
			loadRegistrationFragment(preferences, comment);
			return;
		}

		UrlsClient.getInstance().postComment(comment);
		Toast.makeText(getActivity().getBaseContext(), "Rating Submitted", Toast.LENGTH_SHORT).show();
		getActivity().finish();
	}

	private void loadRegistrationFragment(SharedPreferences preferences, Comment comment) {
		FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
		RegistrationFragment fragment = new RegistrationFragment();
		fragment.setComment(comment);
		fragment.setPreferences(preferences);
		ft.replace(R.id.frameLayout1, fragment);
		ft.commit();
	}

	public void setMenuItem(MenuItem menuItem) {
		this.menuItem = menuItem;
	}
}
