package com.example.android.precopia.booklisttest.activates;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.precopia.booklisttest.R;
import com.example.android.precopia.booklisttest.book.Book;
import com.example.android.precopia.booklisttest.databinding.ActivityListBinding;
import com.example.android.precopia.booklisttest.network.AsyncLoader;
import com.example.android.precopia.booklisttest.network.NetworkUtil;
import com.example.android.precopia.booklisttest.network.QueryUrlConcatenation;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>>, SwipeRefreshLayout.OnRefreshListener {
	
	private static final int LOADER_ID = 0;
	
	private String queryUrl;
	
	private RecyclerView recyclerView;
	private RecyclerAdapter recyclerAdapter;
	
	private SwipeRefreshLayout swipeRefresh;
	private Menu menu;
	
	private ProgressBar progressBar;
	private ImageView imageViewError;
	private TextView textViewError;
	
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActivityListBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_list);
		
		queryUrl = getUrl();
		
		recyclerView = binding.recyclerView;
		recyclerAdapter = new RecyclerAdapter(new ArrayList<Book>(), this);
		setUpRecyclerView();
		
		swipeRefresh = binding.swipeRefresh;
		swipeRefresh.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
		swipeRefresh.setOnRefreshListener(this);
		
		progressBar = binding.progressBar;
		imageViewError = binding.listErrorImageView;
		textViewError = binding.listErrorTextView;
		
		attemptNetworkQuery();
	}
	
	
	private String getUrl() {
		Intent intent = getIntent();
		return QueryUrlConcatenation.concatUrl(
				intent.getStringExtra(getString(R.string.general_edit_text)),
				intent.getStringExtra(getString(R.string.title_edit_text)),
				intent.getStringExtra(getString(R.string.author_edit_text)),
				getMaxResults()
		);
	}
	
	private String getMaxResults() {
		return PreferenceManager.getDefaultSharedPreferences(this)
				.getString(
						getString(R.string.settings_max_results_key),
						getString(R.string.settings_default_value)
				);
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
	
	
	/**
	 * Only show the menu item if their is a network connection
	 * <p>
	 * This is called post onCreateOptionsMenu(Menu menu)
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		super.onPrepareOptionsMenu(menu);
		if (NetworkUtil.haveConnection(getApplicationContext())) {
			MenuItem menuItem = menu.findItem(R.id.refresh_menu_item);
			menuItem.setVisible(false);
		}
		return true;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		this.menu = menu;
		getMenuInflater().inflate(R.menu.list_activity_menu, menu);
		return true;
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.refresh_menu_item:
				swipeRefresh.setRefreshing(true);
				retryConnection();
				swipeRefresh.setRefreshing(false);
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	public void onRefresh() {
		retryConnection();
		swipeRefresh.setRefreshing(false);
	}
	
	private void retryConnection() {
		imageViewError.setVisibility(View.GONE);
		textViewError.setVisibility(View.GONE);
		attemptNetworkQuery();
	}
	
	
	private void attemptNetworkQuery() {
		if (NetworkUtil.haveConnection(getApplicationContext())) {
			getSupportLoaderManager().initLoader(LOADER_ID, null, this);
			if (menu != null) {
				// This removes the menu item when the user refreshes
				// and has a network connection
				menu.removeItem(R.id.refresh_menu_item);
			}
		} else {
			displayNoConnectionError();
		}
	}
	
	
	@NonNull
	@Override
	public android.support.v4.content.Loader<List<Book>> onCreateLoader(int id, Bundle args) {
		return new AsyncLoader(this, queryUrl);
	}
	
	@Override
	public void onLoadFinished(@NonNull android.support.v4.content.Loader<List<Book>> loader, List<Book> bookList) {
		progressBar.setVisibility(View.GONE);
		if (bookList == null || bookList.isEmpty()) {
			displayNoResultsError();
		} else {
			recyclerAdapter.swapData(bookList);
		}
		
	}
	
	@Override
	public void onLoaderReset(@NonNull android.support.v4.content.Loader<List<Book>> loader) {
		recyclerAdapter.swapData(new ArrayList<Book>());
	}
	
	
	private void displayNoConnectionError() {
		progressBar.setVisibility(View.GONE);
		
		textViewError.setVisibility(View.VISIBLE);
		textViewError.setText(R.string.error_no_connection);
		
		imageViewError.setVisibility(View.VISIBLE);
		imageViewError.setImageResource(R.drawable.ic_signal_wifi_off_48dp);
	}
	
	private void displayNoResultsError() {
		textViewError.setVisibility(View.VISIBLE);
		textViewError.setText(R.string.error_no_books_found);
		
		imageViewError.setVisibility(View.VISIBLE);
		imageViewError.setImageResource(R.drawable.ic_error_outline_48dp);
	}
}