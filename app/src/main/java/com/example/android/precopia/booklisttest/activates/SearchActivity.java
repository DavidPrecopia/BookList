package com.example.android.precopia.booklisttest.activates;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.precopia.booklisttest.R;
import com.example.android.precopia.booklisttest.databinding.ActivitySearchBinding;

public class SearchActivity extends AppCompatActivity {
	
	private EditText generalEditText;
	private EditText titleEditText;
	private EditText authorEditText;
	
	private ActivitySearchBinding binding;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = DataBindingUtil.setContentView(this, R.layout.activity_search);
		
		generalEditText = binding.generalEditText;
		titleEditText = binding.titleEditText;
		authorEditText = binding.authorEditText;
		
		searchButtonListener();
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.search_activity_menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.settings_menu_item:
				startActivity(new Intent(this, SettingsActivity.class));
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
	
	
	private void searchButtonListener() {
		binding.searchButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (allTextFieldsAreEmpty()) {
					toastEmptyFields();
				} else {
					intentSwitchActivity();
				}
			}
		});
	}
	
	private void toastEmptyFields() {
		Toast.makeText(this, R.string.error_empty_fields, Toast.LENGTH_SHORT).show();
	}
	
	
	private void intentSwitchActivity() {
		Intent intent = new Intent(SearchActivity.this, ListActivity.class);
		intent.putExtra(getString(R.string.general_edit_text), generalEditText.getText().toString());
		intent.putExtra(getString(R.string.title_edit_text), titleEditText.getText().toString());
		intent.putExtra(getString(R.string.author_edit_text), authorEditText.getText().toString());
		startActivity(intent);
	}
	
	private boolean allTextFieldsAreEmpty() {
		return TextUtils.isEmpty(generalEditText.getText().toString())
				&& TextUtils.isEmpty(titleEditText.getText().toString())
				&& TextUtils.isEmpty(authorEditText.getText().toString());
	}
}