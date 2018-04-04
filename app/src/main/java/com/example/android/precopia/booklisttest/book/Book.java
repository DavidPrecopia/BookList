package com.example.android.precopia.booklisttest.book;

import android.databinding.BindingAdapter;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.android.precopia.booklisttest.R;
import com.example.android.precopia.booklisttest.activates.GlideApp;

public class Book implements Parcelable {
	
	private static final String LOG_TAG = Book.class.getSimpleName();
	
	private String title;
	private String author;
	private String thumbnailUrl;
	private String description;
	private String bookInfoUrl;
	
	public Book(String title, String author, String thumbnailUrl, String description, String bookInfoUrl) {
		this.title = title;
		this.author = author;
		this.thumbnailUrl = thumbnailUrl;
		this.description = description;
		this.bookInfoUrl = bookInfoUrl;
	}
	
	
	public String getThumbnailUrl() {
		return thumbnailUrl;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getBookInfoUrl() {
		return bookInfoUrl;
	}
	
	
	@BindingAdapter({"srcCompat"})
	public static void bindThumbnail(ImageView view, String url) {
		GlideApp.with(view.getContext())
				.load(url)
				.placeholder(R.drawable.ic_book_24dp)
				.transition(DrawableTransitionOptions.withCrossFade())
				.into(view);
	}
	
	
	public boolean hasDescription() {
		return !TextUtils.isEmpty(this.description);
	}
	
	
	
	/**
	 * Comments from Stack Overflow
	 * https://stackoverflow.com/questions/2139134/how-to-send-an-object-from-one-android-activity-to-another-using-intents
	 */
	// Example constructor that takes a Parcel and gives you an object populated with it's values
	private Book(Parcel in) {
		title = in.readString();
		author = in.readString();
		thumbnailUrl = in.readString();
		description = in.readString();
		bookInfoUrl = in.readString();
	}
	
	// This is used to regenerate your object.
	// All Parcelables must have a CREATOR that implements these two methods
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
	 * (Stack Overflow) 99.9% of the time you can just ignore this
	 * (Android Doc) Describe the kinds of special objects contained in this Parcelable instance's marshaled representation.
	 */
	@Override
	public int describeContents() {
		return 0;
	}
	
	/**
	 * (Stack Overflow) Write your object's data to the passed-in Parcel
	 * (Android Doc) Flatten this object in to a Parcel.
	 */
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(title);
		dest.writeString(author);
		dest.writeString(thumbnailUrl);
		dest.writeString(description);
		dest.writeString(bookInfoUrl);
	}
}