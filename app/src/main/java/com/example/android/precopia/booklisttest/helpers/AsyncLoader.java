package com.example.android.precopia.booklisttest.helpers;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.example.android.precopia.booklisttest.activites.Book;

import java.util.List;

public class AsyncLoader extends AsyncTaskLoader<List<Book>> {
	
	private String url;
	
	public AsyncLoader(Context context, String url) {
		super(context);
		this.url = url;
	}
	
	@Override
	protected void onStartLoading() {
		forceLoad();
	}
	
	@Override
	public List<Book> loadInBackground() {
		return NetworkUtil.fetchBookInformation(this.url);
	}
}
