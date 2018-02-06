package com.example.android.precopia.booklisttest;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class BookListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>>, RecyclerAdapter.ItemClickListener {
	
	private static final String LOG_TAG = BookListActivity.class.getSimpleName();
	
	// Initialized in the onCreate method
	static boolean connectedToInternet;
	
	private RecyclerView recyclerView;
	private RecyclerAdapter recyclerAdapter;
	
	private ProgressBar progressBar;
	private ImageView imageViewError;
	private TextView textViewError;
	
	
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.book_list_activity);
		
		recyclerView = findViewById(R.id.recycler_view_layout);
		recyclerAdapter = new RecyclerAdapter(new ArrayList<Book>(), this);
		
		progressBar = findViewById(R.id.progress_bar);
		imageViewError = findViewById(R.id.image_view_error);
		textViewError = findViewById(R.id.text_view_error);
		
		setUpRecyclerView();
		
		connectedToInternet = NetworkUtil.haveConnection(this);
		
		queryServer();
	}
	
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		super.onPrepareOptionsMenu(menu);
		if (connectedToInternet) {
			MenuItem menuItem = menu.findItem(R.id.menu_item_refresh);
			menuItem.setVisible(false);
		}
		return true;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.book_activity_menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_item_refresh:
				retryConnection();
			default:
				return super.onOptionsItemSelected(item);
		}
	}
	
	private void retryConnection() {
		imageViewError.setVisibility(View.GONE);
		textViewError.setVisibility(View.GONE);
		progressBar.setVisibility(View.VISIBLE);
		queryServer();
	}
	
	
	private void setUpRecyclerView() {
		recyclerView.setHasFixedSize(true);
		recyclerView.setAdapter(recyclerAdapter);
		LinearLayoutManager linearLayoutManager = layoutManager();
		dividerDecoration(linearLayoutManager);
	}
	
	@NonNull
	private LinearLayoutManager layoutManager() {
		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
		recyclerView.setLayoutManager(linearLayoutManager);
		return linearLayoutManager;
	}
	
	private void dividerDecoration(LinearLayoutManager linearLayoutManager) {
		DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());
		recyclerView.addItemDecoration(dividerItemDecoration);
	}
	
	
	private void queryServer() {
		if (connectedToInternet) {
			getLoaderManager().initLoader(0, null, this);
		} else {
			displayNoConnectionError();
		}
	}
	
	private void displayNoConnectionError() {
		progressBar.setVisibility(View.GONE);
		imageViewError.setVisibility(View.VISIBLE);
		textViewError.setVisibility(View.VISIBLE);
		imageViewError.setImageResource(R.drawable.ic_signal_wifi_off_black_48dp);
		textViewError.setText(R.string.error_no_connection);
	}
	
	
	@Override
	public Loader<List<Book>> onCreateLoader(int id, Bundle args) {
		return new AsyncLoader(this, getUrl());
	}
	
	private String getUrl() {
		Intent intent = getIntent();
		String general = intent.getStringExtra(getString(R.string.general_edit_text));
		String title = intent.getStringExtra(getString(R.string.title_edit_text));
		String author = intent.getStringExtra(getString(R.string.author_edit_text));
		return QueryUrlConcatenation.concatUrl(general, title, author, getMaxResults());
	}
	
	private String getMaxResults() {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		return sharedPreferences.getString(
				getString(R.string.settings_max_results_key),
				getString(R.string.settings_default_value)
		);
	}
	
	@Override
	public void onLoadFinished(Loader<List<Book>> loader, List<Book> bookList) {
		progressBar.setVisibility(View.GONE);
		if (bookList == null || bookList.isEmpty()) {
			displayNoResultsError();
		} else {
			recyclerAdapter.swapData(bookList);
		}
	}
	
	private void displayNoResultsError() {
		imageViewError.setVisibility(View.VISIBLE);
		textViewError.setVisibility(View.VISIBLE);
		imageViewError.setImageResource(R.drawable.ic_error_outline_black_48dp);
		textViewError.setText(R.string.error_no_books_found);
	}
	
	@Override
	public void onLoaderReset(Loader<List<Book>> loader) {
		recyclerAdapter.swapData(new ArrayList<Book>());
	}
	
	
	/**
	 * Implements single method from RecyclerAdapter.ItemClickListener interface
	 */
	@Override
	public void onClick(String bookInfoUrl) {
		if (TextUtils.isEmpty(bookInfoUrl)) {
			Toast.makeText(this, R.string.error_no_book_info_url, Toast.LENGTH_SHORT).show();
		} else {
			openWebBrowser(bookInfoUrl);
		}
	}
	
	private void openWebBrowser(String bookInfoUrl) {
		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(bookInfoUrl));
		if (intent.resolveActivity(getPackageManager()) != null) {
			startActivity(intent);
		}
	}
}