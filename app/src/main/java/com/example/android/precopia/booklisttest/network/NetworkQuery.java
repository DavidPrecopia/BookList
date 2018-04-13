package com.example.android.precopia.booklisttest.network;

import com.example.android.precopia.booklisttest.book.Book;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

final class NetworkQuery {
	
	private static final Gson GSON;
	static {
		GSON = new GsonBuilder().
				registerTypeAdapter(Book.class, new ParseJson()).
				create();
	}
	
	private static final String BOOKS_ARRAY = "items";
	
	
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
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		}
		return bookList;
	}
	
	private static Request getRequest(String url) {
		return new Request.Builder().url(url).build();
	}
	
	
	private static List<Book> parseJson(Response response) throws JsonSyntaxException, IOException {
		return GSON.fromJson(
				getBooksArray(response.body().string()),
				new TypeToken<List<Book>>() {}.getType()
		);
	}
	
	private static String getBooksArray(String jsonString) {
		JSONArray jsonArray = new JSONArray();
		try {
			jsonArray = new JSONObject(jsonString).getJSONArray(BOOKS_ARRAY);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonArray.toString();
	}
}