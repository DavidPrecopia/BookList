package com.example.android.precopia.booklisttest.activates;

import android.os.Parcel;
import android.os.Parcelable;

public class Book implements Parcelable {
	private String title;
	private String author;
	private String thumbnailUrl;
	private String bookInfoUrl;
	
	public Book(String title, String author, String thumbnailUrl, String bookInfoUrl) {
		this.title = title;
		this.author = author;
		this.thumbnailUrl = thumbnailUrl;
		this.bookInfoUrl = bookInfoUrl;
	}
	
	
	public String getTitle() {
		return title;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public String getThumbnailUrl() {
		return thumbnailUrl;
	}
	
	public String getBookInfoUrl() {
		return bookInfoUrl;
	}
	
	
	/**
	 * Comments from Stack Overflow
	 * https://stackoverflow.com/questions/2139134/how-to-send-an-object-from-one-android-activity-to-another-using-intents
	 */
	// example constructor that takes a Parcel and gives you an object populated with it's values
	private Book(Parcel in) {
		title = in.readString();
		author = in.readString();
		thumbnailUrl = in.readString();
		bookInfoUrl = in.readString();
	}
	
	// this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
	public static final Creator<Book> CREATOR = new Creator<Book>() {
		@Override
		public Book createFromParcel(Parcel in) {
			return new Book(in);
		}
		
		@Override
		public Book[] newArray(int size) {
			return new Book[size];
		}
	};
	
	/**
	 * 99.9% of the time you can just ignore this
	 * (Android Doc) Describe the kinds of special objects contained in this Parcelable instance's marshaled representation.
	 */
	@Override
	public int describeContents() {
		return 0;
	}
	
	/**
	 * write your object's data to the passed-in Parcel
	 * (Android Doc) Flatten this object in to a Parcel.
	 */
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(title);
		dest.writeString(author);
		dest.writeString(thumbnailUrl);
		dest.writeString(bookInfoUrl);
	}
}