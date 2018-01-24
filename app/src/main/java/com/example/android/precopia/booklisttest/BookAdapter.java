package com.example.android.precopia.booklisttest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

class BookAdapter extends ArrayAdapter<Book> {
	
	private static final String LOG_TAG = BookAdapter.class.getSimpleName();
	
	BookAdapter(@NonNull Context context, @NonNull List<Book> bookList) {
		super(context, 0, bookList);
	}
	
	
	void swapData(List<Book> bookList) {
		if (bookList != null && !bookList.isEmpty()) {
			clear();
			addAll(bookList);
			notifyDataSetChanged();
		} else {
			Log.v(LOG_TAG, "swapData - passed List is null or empty");
		}
	}
	
	
	@NonNull
	@Override
	public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
		View listItemView = convertView;
		listItemView = inflateViewIfNull(parent, listItemView);
		
		Book book = getItem(position);
		setViews(listItemView, book);
		
		return listItemView;
	}
	
	
	private View inflateViewIfNull(@NonNull ViewGroup parent, View listItemView) {
		if (listItemView == null) {
			listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
		}
		return listItemView;
	}
	
	
	private void setViews(View listItemView, Book book) {
		setBookThumbnail(listItemView, book.getThumbnailUrl());
		setTitle(listItemView, book.getTitle());
		setAuthor(listItemView, book.getAuthor());
	}
	
	private void setBookThumbnail(View listItemView, String url) {
		ImageView imageView = listItemView.findViewById(R.id.book_thumbnail_image);
		if (TextUtils.isEmpty(url)) {
			imageView.setImageResource(R.drawable.book_placeholder);
		} else {
			Picasso.with(getContext())
					.load(url)
					.placeholder(R.drawable.book_placeholder)
					.error(R.drawable.book_placeholder)
					.into(imageView);
		}
	}
	
	private void setTitle(View listItemView, String title) {
		TextView view = listItemView.findViewById(R.id.title_text_view);
		view.setText(title);
	}
	
	private void setAuthor(View listItemView, String author) {
		TextView textView = listItemView.findViewById(R.id.author_text_view);
		textView.setText(author);
	}
}