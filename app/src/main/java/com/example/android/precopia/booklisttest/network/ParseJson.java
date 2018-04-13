package com.example.android.precopia.booklisttest.network;

import android.support.annotation.NonNull;

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
	private static final String MULTIPLE_AUTHORS_SEPARATOR = ", ";
	
	
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
		return bookInfo.has(TITLE) ? bookInfo.get(TITLE).getAsString() : NO_TITLE;
	}
	
	
	@NonNull
	private static String getAuthors(JsonObject bookInfo) {
		return bookInfo.has(AUTHOR) ? getAuthors(bookInfo.get(AUTHOR).getAsJsonArray()) : NO_AUTHOR;
	}
	
	private static String getAuthors(JsonArray authorsArray) {
		switch (authorsArray.size()) {
			case 0:
				return NO_AUTHOR;
			case 1:
				return authorsArray.get(0).getAsString();
			default:
				return multipleAuthors(authorsArray);
		}
	}
	
	private static String multipleAuthors(JsonArray authorsArray) {
		StringBuilder builder = new StringBuilder();
		for (int x = 0; x < authorsArray.size(); x++) {
			if (x > 0) {
				builder.append(MULTIPLE_AUTHORS_SEPARATOR);
			}
			builder.append(authorsArray.get(x).getAsString());
		}
		return builder.toString();
	}
	
	
	private static String getThumbnailUrl(JsonObject bookInfo) {
		return bookInfo.has(IMAGE_LINKS) ? bookInfo.getAsJsonObject(IMAGE_LINKS).get(THUMBNAIL_LINK).getAsString() : "";
	}
	
	
	private static String getDescription(JsonObject bookInfo) {
		return bookInfo.has(DESCRIPTION) ? bookInfo.get(DESCRIPTION).getAsString() : "";
	}
	
	
	private static String getBookInfoUrl(JsonObject bookInfo) {
		return bookInfo.has(INFO_LINK) ? bookInfo.get(INFO_LINK).getAsString() : "";
	}
}