package com.example.android.precopia.booklisttest;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class BookActivity extends AppCompatActivity {
	
	private static final String LOG_TAG = BookActivity.class.getSimpleName();
	
	// Initialized in the onCreate method
	// Also used by ListFragment to check for connectivity prior to querying the server
	static boolean connectedToInternet;
	
	
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Need prior to setContentView in order initialize connectedToInternet prior to fragment inflation
		connectedToInternet = haveConnection();
		setContentView(R.layout.book_activity);
	}
	
	private boolean haveConnection() {
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivityManager == null) {
			return false;
		}
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		return networkInfo != null && networkInfo.isConnectedOrConnecting();
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
		getMenuInflater().inflate(R.menu.book_list_menu, menu);
		return true;
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_item_refresh:
				// TODO how to refresh?
				Toast.makeText(this, "TODO", Toast.LENGTH_SHORT).show();
//				retryConnection();
			default:
				return super.onOptionsItemSelected(item);
		}
	}
}