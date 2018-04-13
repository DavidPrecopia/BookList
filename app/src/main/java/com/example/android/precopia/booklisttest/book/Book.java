package com.example.android.precopia.booklisttest.book;

import android.databinding.BindingAdapter;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.android.precopia.booklisttest.R;

public class Book {
	
	private String title;
	private String author;
	private String thumbnailUrl;
	private String description;
	private String bookInfoUrl;
	
	public Book(String title, String author, String thumbnailUrl, String description, String bookInfoUrl) {
		this.title = title;
		this.author = author;
		this.thumbnailUrl = thumbnailUrl;
		this.description = description;
		this.bookInfoUrl = bookInfoUrl;
	}
	
	
	public String getThumbnailUrl() {
		return thumbnailUrl;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getBookInfoUrl() {
		return bookInfoUrl;
	}
	
	
	@BindingAdapter({"srcCompat"})
	public static void bindThumbnail(ImageView view, String url) {
		GlideApp.with(view.getContext())
				.load(url)
				.placeholder(R.drawable.ic_book_24dp)
				.transition(DrawableTransitionOptions.withCrossFade())
				.into(view);
	}
	
	
	public boolean hasDescription() {
		return ! TextUtils.isEmpty(this.description);
	}
}