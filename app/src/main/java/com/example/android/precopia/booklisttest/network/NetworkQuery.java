package com.example.android.precopia.booklisttest.network;

import com.example.android.precopia.booklisttest.book.Book;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

final class NetworkQuery {
	
	private NetworkQuery() {
	}
	
	static List<Book> fetchBookInformation(String url) {
		List<Book> bookList = new ArrayList<>();
		try {
			bookList = parseJson(
					new OkHttpClient().newCall(getRequest(url)).execute()
			);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return bookList;
	}
	
	private static Request getRequest(String url) {
		return new Request.Builder().url(url).build();
	}
	
	private static List<Book> parseJson(Response response) throws IOException {
		return ParseJson.parseJsonResponse(
				response.body().string()
		);
	}
}