package com.example.android.precopia.booklisttest.activates;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.precopia.booklisttest.databinding.ListItemBinding;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.BookHolder> {
	
	public interface ItemClickListener {
		void onClick(Book book);
	}
	
	private ItemClickListener itemClickListener;
	
	private List<Book> bookList;
	
	RecyclerAdapter(List<Book> bookList, ItemClickListener itemClickListener) {
		this.bookList = new ArrayList<>(bookList);
		this.itemClickListener = itemClickListener;
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
		bookList.clear();
		this.bookList.addAll(newList);
		notifyDataSetChanged();
	}
	
	
	class BookHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
		
		private ListItemBinding binding;
		
		private Book book;
		
		BookHolder(ListItemBinding binding) {
			super(binding.getRoot());
			this.binding = binding;
			itemView.setOnClickListener(this);
		}
		
		void bind(Book book) {
			this.book = book;
			binding.setBook(book);
			binding.executePendingBindings();
		}
		
		@Override
		public void onClick(View v) {
			itemClickListener.onClick(this.book);
		}
	}
}