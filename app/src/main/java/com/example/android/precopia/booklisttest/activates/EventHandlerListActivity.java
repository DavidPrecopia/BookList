package com.example.android.precopia.booklisttest.activates;

import android.content.Intent;

import com.example.android.precopia.booklisttest.book.Book;
import com.google.gson.Gson;

/**
 * Instead of RecyclerView.Adapter declaring an Interface that is
 * implemented by RecyclerView's enclosing Activity - I'm using data binding.
 *
 * In RecyclerView.ViewHolder I bind an instance of this class
 * to an individual list item layout
 */
public final class EventHandlerListActivity {
	
	private ListActivity listActivity;
	
	EventHandlerListActivity(ListActivity listActivity) {
		this.listActivity = listActivity;
	}
	
	public void onClick(Book book) {
		Intent intent = new Intent(listActivity, DetailActivity.class);
		intent.putExtra(DetailActivity.class.getSimpleName(), new Gson().toJson(book));
		listActivity.startActivity(intent);
	}
}