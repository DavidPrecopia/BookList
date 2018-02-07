package com.example.android.precopia.booklisttest.activates;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.precopia.booklisttest.R;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {
	
	private Book book;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		
		book = getIntent().getParcelableExtra(DetailActivity.class.getSimpleName());
		
		bindImage();
		bindTitle();
		bindAuthor();
	
		// TODO bindDescription
		
		clickListener();
	}
	
	
	private void bindImage() {
		ImageView thumbnail = findViewById(R.id.book_thumbnail_image);
		String url = book.getThumbnailUrl();
		if (TextUtils.isEmpty(url)) {
			thumbnail.setImageResource(R.drawable.ic_book_black_24dp);
		} else {
			Picasso.with(this)
					.load(url)
					.placeholder(R.drawable.ic_book_black_24dp)
					.error(R.drawable.ic_book_black_24dp)
					.into(thumbnail);
		}
	}
	
	private void bindTitle() {
		TextView title = findViewById(R.id.text_view_title);
		title.setText(book.getTitle());
	}
	
	private void bindAuthor() {
		TextView author = findViewById(R.id.author_text_view);
		author.setText(book.getAuthor());
	}
	
	
	private void clickListener() {
		Button button = findViewById(R.id.button_more_info);
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
