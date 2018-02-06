package com.example.android.precopia.booklisttest.activites;

public class Book {
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
}