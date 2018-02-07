package com.example.android.precopia.booklisttest.activates;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.android.precopia.booklisttest.R;

public class DetailActivity extends AppCompatActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		
		Book book = getIntent().getParcelableExtra(DetailActivity.class.getSimpleName());
		
		TextView textView = findViewById(R.id.text_view_detail);
		textView.setText(book.getTitle());
	}
}
