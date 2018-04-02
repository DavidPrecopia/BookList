package com.example.android.precopia.booklisttest.activates;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.android.precopia.booklisttest.R;
import com.example.android.precopia.booklisttest.helpers.GlideApp;

public class DetailActivity extends AppCompatActivity {
	
	private static final String LOG_TAG = DetailActivity.class.getSimpleName();
	
	private Book book;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		
		book = getIntent().getParcelableExtra(DetailActivity.class.getSimpleName());
		
		bindData();
		
		clickListener();
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
		String mimeType = "text/plain";
		StringBuilder information = getBookInformation();
		ShareCompat.IntentBuilder.from(this).setType(mimeType).setText(information).startChooser();
	}
	
	private StringBuilder getBookInformation() {
		return new StringBuilder()
				.append(book.getTitle())
				.append(" by ")
				.append(book.getAuthor())
				.append("\n")
				.append(book.getBookInfoUrl());
	}
	
	
	private void bindData() {
		bindImage();
		bindTitle();
		bindAuthor();
		bindDescription();
	}
	
	private void bindImage() {
		ImageView thumbnail = findViewById(R.id.book_thumbnail_image);
		String url = book.getThumbnailUrl();
		GlideApp.with(this)
				.load(url)
				.placeholder(R.drawable.ic_book_24dp)
				.transition(DrawableTransitionOptions.withCrossFade())
				.into(thumbnail);
	}
	
	private void bindTitle() {
		TextView title = findViewById(R.id.title_text_view);
		title.setText(book.getTitle());
	}
	
	private void bindAuthor() {
		TextView author = findViewById(R.id.author_text_view);
		author.setText(book.getAuthor());
	}
	
	private void bindDescription() {
		String bookDescription = book.getDescription();
		if (TextUtils.isEmpty(bookDescription)) {
			showErrorTextView();
		} else {
			TextView description = findViewById(R.id.description_text_view);
			description.setText(book.getDescription());
		}
	}
	
	private void showErrorTextView() {
		TextView noDescription = findViewById(R.id.no_description_error);
		noDescription.setVisibility(View.VISIBLE);
	}
	
	
	private void clickListener() {
		Button button = findViewById(R.id.more_info_button);
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				openWebBrowser();
			}
		});
	}
	
	private void openWebBrowser() {
		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(book.getBookInfoUrl()));
		if (intent.resolveActivity(getPackageManager()) != null) {
			startActivity(intent);
		}
	}
}
