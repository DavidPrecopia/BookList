package com.example.android.precopia.booklisttest.network;

import android.text.TextUtils;
import android.util.Log;

public final class QueryUrlConcatenation {
	
	private static final String LOG_TAG = QueryUrlConcatenation.class.getSimpleName();
	
	private static final StringBuilder BASE_URL = new StringBuilder(
			"https://www.googleapis.com/books/v1/volumes?q="
	);
	private static final String IN_TITLE = "+intitle:";
	private static final String IN_AUTHOR = "+inauthor:";
	private static final String MAX_RESULTS = "&maxResults=";
	
	private QueryUrlConcatenation() {
	}
	
	public static String concatUrl(String generalQuery, String title, String author, String maxResults) {
		StringBuilder url = new StringBuilder(BASE_URL);
		
		url.append(appendGeneral(generalQuery));
		url.append(appendTitle(title));
		url.append(appendAuthor(author));
		url.append(appendMaxResults(maxResults));
		
		Log.d(LOG_TAG, url.toString());
		return url.toString();
	}
	
	private static String appendGeneral(String generalQuery) {
		return TextUtils.isEmpty(generalQuery) ? "" : generalQuery;
	}
	
	private static String appendTitle(String title) {
		return TextUtils.isEmpty(title) ? "" : (IN_TITLE + title);
	}
	
	private static String appendAuthor(String author) {
		return TextUtils.isEmpty(author) ? "" : (IN_AUTHOR + author);
	}
	
	private static String appendMaxResults(String maxResults) {
		return Integer.valueOf(maxResults) > 40 ? "40" : (MAX_RESULTS + maxResults);
	}
}