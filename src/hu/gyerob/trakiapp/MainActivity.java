package hu.gyerob.trakiapp;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff.Mode;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	private Button input;
	private Button output;
	private Button finals;
	private Button gallery;
	private Button map;
	
	private ActionBar actionbar;

	private OnClickListener startlistener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent i = null;
			switch (v.getId()) {
			case R.id.btnIn: {
				i = new Intent(getApplicationContext(), InputActivity.class);
				break;
			}
			case R.id.btnOut: {
				i = new Intent(getApplicationContext(), OutputActivity.class);
				break;
			}
			case R.id.btnFinals: {
				i = new Intent(getApplicationContext(), FinalsActivity.class);
				break;
			}
			case R.id.btnGallery: {
				i = new Intent(getApplicationContext(), GalleryActivity.class);
				break;
			}
			case R.id.btnMap: {
				i = new Intent(getApplicationContext(), MapActivity.class);
				break;
			}
			}
			startActivity(i);
		}
	};

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		if (android.os.Build.VERSION.SDK_INT >= 11) {
			actionbar = getActionBar();
			actionbar.setHomeButtonEnabled(true);
		}

		input = (Button) findViewById(R.id.btnIn);
		output = (Button) findViewById(R.id.btnOut);
		finals = (Button) findViewById(R.id.btnFinals);
		gallery = (Button) findViewById(R.id.btnGallery);
		map = (Button) findViewById(R.id.btnMap);

		input.getBackground().setColorFilter(0xFF00FF00, Mode.MULTIPLY);
		output.getBackground().setColorFilter(0xFF00FF00, Mode.MULTIPLY);
		finals.getBackground().setColorFilter(0xFF00FF00, Mode.MULTIPLY);
		gallery.getBackground().setColorFilter(0xFF00FF00, Mode.MULTIPLY);
		map.getBackground().setColorFilter(0xFF00FF00, Mode.MULTIPLY);

		input.setOnClickListener(startlistener);
		output.setOnClickListener(startlistener);
		finals.setOnClickListener(startlistener);
		gallery.setOnClickListener(startlistener);
		map.setOnClickListener(startlistener);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.mymenu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.itemPreferences) {
			Intent settingsActivity = new Intent(MainActivity.this,
					PreferencesActivity.class);
			startActivity(settingsActivity);
		}
		return super.onOptionsItemSelected(item);
	}
}
