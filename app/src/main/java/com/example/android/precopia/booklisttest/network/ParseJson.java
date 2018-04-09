package com.example.android.precopia.booklisttest.network;

import android.support.annotation.NonNull;

import com.example.android.precopia.booklisttest.book.Book;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

final class ParseJson {
	
	private ParseJson() {
	}
	
	static List<Book> parseJsonResponse(String jsonResponse) {
		List<Book> bookList = new ArrayList<>();
		try {
			JSONArray jsonArrayOfBooks = new JSONObject(jsonResponse).optJSONArray("items");
			if (jsonArrayOfBooks == null) {
				return bookList;
			}
			for (int x = 0; x < jsonArrayOfBooks.length(); x++) {
				JSONObject bookInfo = jsonArrayOfBooks.getJSONObject(x).getJSONObject("volumeInfo");
				bookList.add(extractBookInfo(bookInfo));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return bookList;
	}
	
	
	private static Book extractBookInfo(JSONObject bookInfo) {
		String title = "", authors = "", thumbnailUrl = "", description = "", bookInfoUrl = "";
		try {
			title = getTitle(bookInfo);
			authors = getAuthors(bookInfo);
			thumbnailUrl = getThumbnailUrl(bookInfo);
			description = getDescription(bookInfo);
			bookInfoUrl = getBookInfoUrl(bookInfo);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return new Book(title, authors, thumbnailUrl, description, bookInfoUrl);
	}
	
	private static String getTitle(JSONObject bookInfo) throws JSONException {
		return bookInfo.isNull("title") ? "No title listed" : bookInfo.getString("title");
	}
	
	@NonNull
	private static String getAuthors(JSONObject bookInfo) throws JSONException {
		return bookInfo.isNull("authors") ? "No authors listed" : getAuthors(bookInfo.getJSONArray("authors"));
	}
	
	private static String getAuthors(JSONArray jsonAuthorsArray) {
		StringBuilder authorsString = new StringBuilder();
		try {
			for (int j = 0; j < jsonAuthorsArray.length(); j++) {
				// Separate authors if multiple
				if (j > 0) {
					authorsString.append("; ");
				}
				authorsString.append(jsonAuthorsArray.get(0).toString());
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return authorsString.toString();
	}
	
	private static String getThumbnailUrl(JSONObject bookInfo) throws JSONException {
		return bookInfo.isNull("imageLinks") ? "" : bookInfo.optJSONObject("imageLinks").getString("thumbnail");
	}
	
	private static String getDescription(JSONObject bookInfo) throws JSONException {
		return bookInfo.isNull("description") ? "" : bookInfo.getString("description");
	}
	
	private static String getBookInfoUrl(JSONObject bookInfo) {
		return bookInfo.isNull("infoLink") ? "" : bookInfo.optString("infoLink");
	}
}