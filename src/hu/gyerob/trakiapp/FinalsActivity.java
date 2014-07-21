package hu.gyerob.trakiapp;

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
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class FinalsActivity extends ActionBarActivity {

	private Button drag;
	private Button slalom;
	private Button slalomlock;
	private Button draglock;

	private static String url_lock_slalom = "http://tv2014.ddns.net/trakiweb/create_slalom_top.php";
	//private static String url_lock_slalom_a = "http://gyerob.no-ip.biz/trakiweb/create_slalom_a_top.php";
	//private static String url_lock_slalom_b = "http://gyerob.no-ip.biz/trakiweb/create_slalom_b_top.php";
	private static String url_lock_drag = "http://tv2014.ddns.net/trakiweb/create_drag_top.php";

	private ProgressDialog pDialog;
	private JSONParser jsonParser;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_finals);

		drag = (Button) findViewById(R.id.finalsDragbtn);
		slalom = (Button) findViewById(R.id.finalsSlalombtn);
		slalomlock = (Button) findViewById(R.id.btnlockslalom);
		draglock = (Button) findViewById(R.id.btnlockdrag);

		slalom.setOnClickListener(click);
		slalomlock.setOnClickListener(click);
		drag.setOnClickListener(click);
		draglock.setOnClickListener(click);
		
		jsonParser = new JSONParser();
	}

	OnClickListener click = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent i = null;

			switch (v.getId()) {
			case R.id.finalsDragbtn: {
				i = new Intent(getApplicationContext(),
						FinalsDragActivity.class);
				startActivity(i);
				break;
			}
			case R.id.finalsSlalombtn: {
				i = new Intent(getApplicationContext(),
						FinalsSlalomActivity.class);
				startActivity(i);
				break;
			}
			case R.id.btnlockslalom: {
				new LockSlalom().execute();
				break;
			}
			case R.id.btnlockdrag: {
				new LockDrag().execute();
				break;
			}
			}
			
		}
	};

	class LockSlalom extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(FinalsActivity.this);
			pDialog.setMessage("Versenyszám lezárása");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... param) {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
/*
			JSONObject json = jsonParser.makeHttpRequest(url_lock_slalom_a,
					"POST", params);
			Log.d("Create Response", json.toString());
			
			json = jsonParser.makeHttpRequest(url_lock_slalom_b,
					"POST", params);

			Log.d("Create Response", json.toString());*/
			
			JSONObject json = jsonParser.makeHttpRequest(url_lock_slalom,
					"POST", params);
			Log.d("Create Response", json.toString());
			return null;
		}

		protected void onPostExecute(String file_url) {
			pDialog.dismiss();
		}
	}

	class LockDrag extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(FinalsActivity.this);
			pDialog.setMessage("Versenyszám lezárása");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... param) {
			List<NameValuePair> params = new ArrayList<NameValuePair>();

			JSONObject json = jsonParser.makeHttpRequest(url_lock_drag,
					"POST", params);

			Log.d("Create Response", json.toString());
			return null;
		}

		protected void onPostExecute(String file_url) {
			pDialog.dismiss();
		}
	}
}
