package com.example.android.precopia.booklisttest.network;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.example.android.precopia.booklisttest.book.Book;

import java.util.ArrayList;
import java.util.List;

public final class AsyncLoader extends AsyncTaskLoader<List<Book>> {
	
	private String url;
	
	private List<Book> cacheOfResults;
	
	public AsyncLoader(Context context, String url) {
		super(context);
		cacheOfResults = new ArrayList<>();
		this.url = url;
	}
	
	@Override
	protected void onStartLoading() {
		if (cacheOfResults.isEmpty()) {
			forceLoad();
		} else {
			deliverResult(cacheOfResults);
		}
	}
	
	@Override
	public List<Book> loadInBackground() {
		return NetworkQuery.fetchBookInformation(url);
	}
	
	@Override
	public void deliverResult(List<Book> data) {
		cacheOfResults = data;
		super.deliverResult(data);
	}
}
