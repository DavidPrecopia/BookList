package com.example.android.precopia.booklisttest;

class Book {
	private String title;
	private String author;
	private String thumbnailUrl;
	private String bookInfoUrl;
	
	Book(String title, String author, String thumbnailUrl, String bookInfoUrl) {
		this.title = title;
		this.author = author;
		this.thumbnailUrl = thumbnailUrl;
		this.bookInfoUrl = bookInfoUrl;
	}
	
	String getTitle() {
		return title;
	}
	
	String getAuthor() {
		return author;
	}
	
	String getThumbnailUrl() {
		return thumbnailUrl;
	}
	
	String getBookInfoUrl() {
		return bookInfoUrl;
	}
}