package com.example.android.precopia.booklisttest.helpers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.android.precopia.booklisttest.activites.Book;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class NetworkUtil {
	
	private static final String LOG_TAG = NetworkUtil.class.getSimpleName();
	
	private NetworkUtil() {
	}
	
	
	public static boolean haveConnection(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivityManager == null) {
			return false;
		}
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		return networkInfo != null && networkInfo.isConnectedOrConnecting();
	}
	
	
	public static List<Book> fetchBookInformation(String requestUrl) {
		String jsonResponse = "";
		try {
			jsonResponse = httpRequest(createUrl(requestUrl));
		} catch (IOException e) {
			// Should catch the exception from the disconnect() method - if I'm not mistaken
			Log.e(LOG_TAG, "Error closing InputStream", e);
		}
		return ParseJson.parseJsonResponse(jsonResponse);
	}
	
	private static URL createUrl(String requestUrl) {
		URL url = null;
		try {
			url = new URL(requestUrl);
		} catch (MalformedURLException e) {
			Log.e(LOG_TAG, "Error instantiating URL object", e);
		}
		return url;
	}
	
	/*
	Not sure how to break up
	 */
	private static String httpRequest(URL url) throws IOException {
		String jsonResponse = "";
		
		if (url == null) {
			return jsonResponse;
		}
		
		HttpsURLConnection httpsURLConnection = null;
		InputStream inputStream = null;
		
		try {
			httpsURLConnection = (HttpsURLConnection) url.openConnection();
			httpsURLConnection.setRequestMethod("GET");
			httpsURLConnection.setConnectTimeout(15000);
			httpsURLConnection.setReadTimeout(10000);
			httpsURLConnection.connect();
			if (httpsURLConnection.getResponseCode() == 200) {
				inputStream = httpsURLConnection.getInputStream();
				jsonResponse = readFromStream(inputStream);
			} else {
				Log.e(LOG_TAG, "Connection error - HTTP response code: " + httpsURLConnection.getResponseCode());
			}
		} catch (IOException e) {
			Log.e(LOG_TAG, "Error establishing HTTPS connection (httpRequest)", e);
		} finally {
			disconnect(httpsURLConnection, inputStream);
		}
		return jsonResponse;
	}
	
	
	private static String readFromStream(InputStream inputStream) throws IOException {
		StringBuilder dataFromInputStream = new StringBuilder();
		if (inputStream != null) {
			readInputStream(dataFromInputStream, getBufferedReader(inputStream));
		}
		return dataFromInputStream.toString();
	}
	
	@NonNull
	private static BufferedReader getBufferedReader(InputStream inputStream) {
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
		return new BufferedReader(inputStreamReader);
	}
	
	private static void readInputStream(StringBuilder dataFromInputStream, BufferedReader bufferedReader) throws IOException {
		String line = bufferedReader.readLine();
		while (line != null) {
			dataFromInputStream.append(line);
			line = bufferedReader.readLine();
		}
	}
	
	
	private static void disconnect(HttpsURLConnection httpsURLConnection, InputStream inputStream) throws IOException {
		if (httpsURLConnection != null) {
			httpsURLConnection.disconnect();
		}
		if (inputStream != null) {
			inputStream.close();
		}
	}
	
	
	private static class ParseJson {
		private static List<Book> parseJsonResponse(String jsonResponse) {
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
				Log.e(LOG_TAG, "parseJsonResponse method", e);
			}
			return bookList;
		}
		
		
		private static Book extractBookInfo(JSONObject bookInfo) {
			String title = "", authors = "", thumbnailUrl = "", bookInfoUrl = "";
			try {
				title = getTitle(bookInfo);
				authors = getAuthors(bookInfo);
				thumbnailUrl = getThumbnailUrl(bookInfo);
				bookInfoUrl = getBookInfoUrl(bookInfo);
			} catch (JSONException e) {
				Log.e(LOG_TAG, "extractBookInfo method", e);
			}
			return new Book(title, authors, thumbnailUrl, bookInfoUrl);
		}
		
		private static String getTitle(JSONObject bookInfo) throws JSONException {
			return bookInfo.isNull("title") ? "" : bookInfo.getString("title");
		}
		
		@NonNull
		private static String getAuthors(JSONObject bookInfo) throws JSONException {
			return bookInfo.isNull("authors") ? "" : getAuthors(bookInfo.getJSONArray("authors"));
		}
		
		private static String getAuthors(JSONArray jsonAuthorsArray) throws JSONException {
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
				Log.e(LOG_TAG, "getAuthors", e);
			}
			return authorsString.toString();
		}
		
		private static String getThumbnailUrl(JSONObject bookInfo) throws JSONException {
			return bookInfo.isNull("imageLinks") ? "" : bookInfo.optJSONObject("imageLinks").getString("thumbnail");
		}
		
		private static String getBookInfoUrl(JSONObject bookInfo) {
			return bookInfo.isNull("infoLink") ? "" : bookInfo.optString("infoLink");
		}
	}
}