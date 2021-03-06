package hu.gyerob.trakiappdev;

import java.util.ArrayList;
import java.util.List;

import jsonParser.JSONParser;

import org.apache.http.NameValuePair;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends ActionBarActivity {

	private Button input;
	private Button output;
	private Button finals;
	private Button gallery;
	private Button map;

	private ProgressDialog pDialog;

	private JSONParser jsonParser = new JSONParser();

	private static String url_pontoz = "http://tv2014.ddns.net/trakiweb/pontoz.php";

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		input = (Button) findViewById(R.id.btnIn);
		output = (Button) findViewById(R.id.btnOut);
		finals = (Button) findViewById(R.id.btnFinals);
		gallery = (Button) findViewById(R.id.btnGallery);
		map = (Button) findViewById(R.id.btnMap);

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
		} else if (item.getItemId() == R.id.itemPontoz) {
			new Pontoz().execute();
		}
		return super.onOptionsItemSelected(item);
	}

	class Pontoz extends AsyncTask<Void, Void, Void> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(MainActivity.this);
			pDialog.setMessage("Pontoz�s..");
			pDialog.setCancelable(true);
			pDialog.show();
		}

		protected Void doInBackground(Void... args) {
			List<NameValuePair> params = new ArrayList<NameValuePair>();

			@SuppressWarnings("unused")
			JSONObject json = jsonParser.makeHttpRequest(url_pontoz, "POST",
					params);

			return null;
		}

		protected void onPostExecute(Void file_url) {
			pDialog.dismiss();
		}
	}
}
