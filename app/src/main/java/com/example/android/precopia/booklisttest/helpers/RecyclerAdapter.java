package com.example.android.precopia.booklisttest.helpers;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.precopia.booklisttest.R;
import com.example.android.precopia.booklisttest.activites.Book;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.BookHolder> {
	
	public interface ItemClickListener {
		void onClick(String bookInfoUrl);
	}
	
	private ItemClickListener itemClickListener;
	
	private List<Book> bookList;
	
	public RecyclerAdapter(List<Book> bookList, ItemClickListener itemClickListener) {
		this.bookList = new ArrayList<>(bookList);
		this.itemClickListener = itemClickListener;
	}
	
	
	@Override
	public BookHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
		return new BookHolder(view, parent);
	}
	
	@Override
	public void onBindViewHolder(BookHolder holder, int position) {
		holder.bindData(position);
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
		
		private Book book;
		
		private ImageView thumbnailImageView;
		private TextView titleTextView;
		private TextView authorTextView;
		
		private ViewGroup parent;
		
		/**
		 * @param itemView The list item view
		 * @param parent Picasso needs context to set a thumbnailImageView
		 */
		BookHolder(View itemView, ViewGroup parent) {
			super(itemView);
			itemView.setOnClickListener(this);
			thumbnailImageView = itemView.findViewById(R.id.book_thumbnail_image);
			titleTextView = itemView.findViewById(R.id.title_text_view);
			authorTextView = itemView.findViewById(R.id.author_text_view);
			this.parent = parent;
		}
		
		
		void bindData(int position) {
			this.book = bookList.get(position);
			bindThumbnail(book.getThumbnailUrl());
			bindTitle();
			bindAuthor();
		}
		
		private void bindTitle() {
			String titleFromBook = book.getTitle();
			String titleString = TextUtils.isEmpty(titleFromBook) ? "No title listed" : titleFromBook;
			titleTextView.setText(titleString);
		}
		
		private void bindAuthor() {
			String authorFromBook = book.getAuthor();
			String authorString = TextUtils.isEmpty(authorFromBook) ? "No author listed" : authorFromBook;
			authorTextView.setText(authorString);
		}
		
		private void bindThumbnail(String url) {
			if (TextUtils.isEmpty(url)) {
				thumbnailImageView.setImageResource(R.drawable.ic_book_black_24dp);
			} else {
				Picasso.with(parent.getContext())
						.load(url)
						.placeholder(R.drawable.ic_book_black_24dp)
						.error(R.drawable.ic_book_black_24dp)
						.into(thumbnailImageView);
			}
		}
		
		
		@Override
		public void onClick(View v) {
			itemClickListener.onClick(this.book.getBookInfoUrl());
		}
	}
}