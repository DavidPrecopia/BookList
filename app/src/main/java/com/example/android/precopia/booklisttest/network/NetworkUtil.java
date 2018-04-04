package com.example.android.precopia.booklisttest.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.android.precopia.booklisttest.book.Book;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
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
	
	
	static List<Book> fetchBookInformation(String requestUrl) {
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
}