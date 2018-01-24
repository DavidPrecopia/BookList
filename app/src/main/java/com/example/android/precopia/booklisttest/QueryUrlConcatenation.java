package com.example.android.precopia.booklisttest;

import android.text.TextUtils;
import android.util.Log;

final class QueryUrlConcatenation {
	
	private static final String LOG_TAG = QueryUrlConcatenation.class.getSimpleName();
	
	private static final StringBuilder BASE_URL = new StringBuilder(
			"https://www.googleapis.com/books/v1/volumes?q="
	);
	private static final String IN_TITLE = "+intitle:";
	private static final String IN_AUTHOR = "+inauthor:";
	private static final String MAX_RESULTS = "&maxResults=20";
	
	private QueryUrlConcatenation() {
	}
	
	static String concatUrl(String general, String title, String author) {
		StringBuilder url = new StringBuilder(BASE_URL);
		if (! TextUtils.isEmpty(general)) {
			url.append(general);
		}
		if (! TextUtils.isEmpty(title)) {
			url.append(IN_TITLE).append(title);
		}
		if (! TextUtils.isEmpty(author)) {
			url.append(IN_AUTHOR).append(author);
		}
		url.append(MAX_RESULTS);
		
		Log.v(LOG_TAG, url.toString());
		return url.toString();
	}
}