package com.example.android.precopia.booklisttest.helpers;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.example.android.precopia.booklisttest.activates.Book;

import java.util.ArrayList;
import java.util.List;

public class AsyncLoader extends AsyncTaskLoader<List<Book>> {
	
	private static final String LOG_TAG = AsyncLoader.class.getSimpleName();
	
	private String url;
	
	private List<Book> cacheOfResults = new ArrayList<>();
	
	public AsyncLoader(Context context, String url) {
		super(context);
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
		return NetworkUtil.fetchBookInformation(this.url);
	}
	
	@Override
	public void deliverResult(List<Book> data) {
		cacheOfResults = data;
		super.deliverResult(data);
	}
}
