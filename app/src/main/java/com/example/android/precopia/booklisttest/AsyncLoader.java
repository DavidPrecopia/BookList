package com.example.android.precopia.booklisttest;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

class AsyncLoader extends AsyncTaskLoader<List<Book>> {
	
	private String url;
	
	AsyncLoader(Context context, String url) {
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
