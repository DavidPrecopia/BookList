package com.example.android.precopia.booklisttest.activates;

import android.content.Intent;

import com.example.android.precopia.booklisttest.book.Book;

public final class EventHandlerListActivity {
	
	private ListActivity listActivity;
	
	EventHandlerListActivity(ListActivity listActivity) {
		this.listActivity = listActivity;
	}
	
	public void onClick(Book book) {
		Intent intent = new Intent(listActivity, DetailActivity.class);
		intent.putExtra(DetailActivity.class.getSimpleName(), book);
		listActivity.startActivity(intent);
	}
}