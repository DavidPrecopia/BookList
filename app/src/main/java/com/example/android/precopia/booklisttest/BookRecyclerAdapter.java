package com.example.android.precopia.booklisttest;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

class BookRecyclerAdapter extends RecyclerView.Adapter<BookRecyclerAdapter.BookViewHolder> {
	
	interface ItemClickListener {
		void onClick(String bookInfoUrl);
	}
	
	private ItemClickListener itemClickListener;
	
	private List<Book> bookList;
	
	BookRecyclerAdapter(List<Book> bookList, ItemClickListener itemClickListener) {
		this.bookList = new ArrayList<>(bookList);
		this.itemClickListener = itemClickListener;
	}
	
	
	@Override
	public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
		return new BookViewHolder(view, parent);
	}
	
	@Override
	public void onBindViewHolder(BookViewHolder holder, int position) {
		holder.bindData(position);
	}
	
	@Override
	public int getItemCount() {
		return bookList == null ? 0 : bookList.size();
	}
	
	
	void swapData(List<Book> newList) {
		bookList.clear();
		this.bookList.addAll(newList);
		notifyDataSetChanged();
	}
	
	
	
	class BookViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
		
		private Book book;
		
		private ImageView thumbnail;
		private TextView title;
		private TextView author;
		
		private ViewGroup parent;
		
		/**
		 * Sets OnClickListener on itemView
		 * @param itemView The list item view
		 * @param parent Picasso needs context to set a thumbnail
		 */
		BookViewHolder(View itemView, ViewGroup parent) {
			super(itemView);
			itemView.setOnClickListener(this);
			thumbnail = itemView.findViewById(R.id.book_thumbnail_image);
			title = itemView.findViewById(R.id.title_text_view);
			author = itemView.findViewById(R.id.author_text_view);
			this.parent = parent;
		}
		
		
		void bindData(int position) {
			this.book = bookList.get(position);
			bindThumbnail(book.getThumbnailUrl());
			title.setText(book.getTitle());
			author.setText(book.getAuthor());
		}
		
		private void bindThumbnail(String url) {
			if (TextUtils.isEmpty(url)) {
				thumbnail.setImageResource(R.drawable.book_placeholder);
			} else {
				Picasso.with(parent.getContext())
						.load(url)
						.placeholder(R.drawable.book_placeholder)
						.error(R.drawable.book_placeholder)
						.into(thumbnail);
			}
		}
		
		
		@Override
		public void onClick(View v) {
			itemClickListener.onClick(this.book.getBookInfoUrl());
		}
	}
}