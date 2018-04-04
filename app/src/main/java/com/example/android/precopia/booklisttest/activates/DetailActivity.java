package com.example.android.precopia.booklisttest.activates;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.android.precopia.booklisttest.R;
import com.example.android.precopia.booklisttest.book.Book;
import com.example.android.precopia.booklisttest.databinding.ActivityDetailBinding;

public class DetailActivity extends AppCompatActivity {
	
	private static final String LOG_TAG = DetailActivity.class.getSimpleName();
	
	private Book book;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActivityDetailBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
		
		binding.moreInfoButton.setOnClickListener(clickListener());
		
		book = getIntent().getParcelableExtra(DetailActivity.class.getSimpleName());
		binding.setBook(book);
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.detail_activty_menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.share_menu_item:
				shareBook();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
	
	
	private void shareBook() {
		ShareCompat.IntentBuilder
				.from(this)
				.setType("text/plain")
				.setText(getBookInformation())
				.startChooser();
	}
	
	private StringBuilder getBookInformation() {
		return new StringBuilder()
				.append(book.getTitle())
				.append(" by ")
				.append(book.getAuthor())
				.append("\n")
				.append(book.getBookInfoUrl());
	}
	
	
	private View.OnClickListener clickListener() {
		return new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				openWebBrowser();
			}
		};
	}
	
	private void openWebBrowser() {
		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(book.getBookInfoUrl()));
		if (intent.resolveActivity(getPackageManager()) != null) {
			startActivity(intent);
		}
	}
}
