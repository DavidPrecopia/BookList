package com.example.android.precopia.booklisttest.network;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.android.precopia.booklisttest.book.Book;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

final class ParseJson implements JsonDeserializer<Book> {
	
	private static final String VOLUME_INFO = "volumeInfo";
	
	private static final String TITLE = "title";
	private static final String AUTHOR = "authors";
	
	private static final String IMAGE_LINKS = "imageLinks";
	private static final String THUMBNAIL_LINK = "thumbnail";
	
	private static final String DESCRIPTION = "description";
	
	private static final String INFO_LINK = "infoLink";
	
	
	private static final String NO_TITLE = "No title listed";
	private static final String NO_AUTHOR = "No authors listed";
	private static final String LOG_TAG = ParseJson.class.getSimpleName();
	
	
	@Override
	public Book deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		Book book = null;
		try {
			book = extractBookInfo(
					json.getAsJsonObject().getAsJsonObject(VOLUME_INFO)
			);
		} catch (JsonParseException e) {
			e.printStackTrace();
		}
		Log.d(LOG_TAG, book.getThumbnailUrl() + " - " + book.getDescription());
		return book;
	}
	
	
	private static Book extractBookInfo(JsonObject bookInfo) {
		return new Book(
				getTitle(bookInfo),
				getAuthors(bookInfo),
				getThumbnailUrl(bookInfo),
				getDescription(bookInfo),
				getBookInfoUrl(bookInfo)
		);
	}
	
	
	private static String getTitle(JsonObject bookInfo) {
		return bookInfo.has(TITLE) ? removeQuotationMarks(bookInfo.get(TITLE).toString()) : NO_TITLE;
	}
	
	
	@NonNull
	private static String getAuthors(JsonObject bookInfo) {
		return bookInfo.has(AUTHOR) ? getAuthors(bookInfo.get(AUTHOR).getAsJsonArray()) : NO_AUTHOR;
	}
	
	private static String getAuthors(JsonArray authorsArray) {
		if (authorsArray.size() == 0) {
			return NO_AUTHOR;
		}
		return authorsArray.get(0).getAsString();
	}
	
	
	private static String getThumbnailUrl(JsonObject bookInfo) {
		return bookInfo.has(IMAGE_LINKS) ? removeQuotationMarks(bookInfo.getAsJsonObject(IMAGE_LINKS).get(THUMBNAIL_LINK).toString()) : "";
	}
	
	
	private static String getDescription(JsonObject bookInfo) {
		return bookInfo.has(DESCRIPTION) ? bookInfo.get(DESCRIPTION).toString() : "";
	}
	
	
	private static String getBookInfoUrl(JsonObject bookInfo) {
		return bookInfo.has(INFO_LINK) ? removeQuotationMarks(bookInfo.get(INFO_LINK).toString()) : "";
	}
	
	
	private static String removeQuotationMarks(String text) {
		return text.substring(1, text.length() - 1);
	}
}