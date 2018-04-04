package com.example.android.precopia.booklisttest.activates;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.android.precopia.booklisttest.book.Book;
import com.example.android.precopia.booklisttest.databinding.ListItemBinding;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.BookHolder> {
	
	private List<Book> bookList;
	
	private EventHandlerListActivity eventHandler;
	
	RecyclerAdapter(List<Book> bookList, ListActivity listActivity) {
		this.bookList = new ArrayList<>(bookList);
		this.eventHandler = new EventHandlerListActivity(listActivity);
	}
	
	
	@NonNull
	@Override
	public BookHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		return new BookHolder(
				ListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false)
		);
	}
	
	@Override
	public void onBindViewHolder(@NonNull BookHolder holder, int position) {
		holder.bind(bookList.get(position));
	}
	
	@Override
	public int getItemCount() {
		return bookList == null ? 0 : bookList.size();
	}
	
	
	public void swapData(List<Book> newList) {
		this.bookList.clear();
		this.bookList.addAll(newList);
		notifyDataSetChanged();
	}
	
	
	class BookHolder extends RecyclerView.ViewHolder {
		
		private ListItemBinding binding;
		
		private Book book;
		
		BookHolder(ListItemBinding binding) {
			super(binding.getRoot());
			this.binding = binding;
		}
		
		void bind(Book book) {
			this.book = book;
			binding.setBook(book);
			binding.setEventHandler(eventHandler);
			binding.executePendingBindings();
		}
	}
}