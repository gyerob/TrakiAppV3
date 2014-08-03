package finalsslalomfragments;

import hu.gyerob.trakiappdev.R;

import java.util.ArrayList;
import java.util.List;

import jsonParser.JSONParser;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import adapter.SlalomTop10Adapter;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import data.SlalomTop;

public class SlalomAboveTop10Fragment2 extends ListFragment {
	public static final String TITLE = "Helyezések+";

	private SlalomTop10Adapter adapter;
	private ArrayList<SlalomTop> racerList;

	public ProgressDialog pDialog;

	private JSONParser jParser = new JSONParser();

	private static String url_all_slalom_top_results = "http://tv2014.ddns.net/trakiweb/get_all_slalom_a_top_results.php";

	private static final String TAG_SUCCESS = "success";
	private static final String TAG_PRODUCTS = "slalom";

	private JSONArray racers = null;

	class updatebroadcast extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			
		}
	}

	public static SlalomAboveTop10Fragment2 newInstance() {
		SlalomAboveTop10Fragment2 fragment = new SlalomAboveTop10Fragment2();

		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		LocalBroadcastManager.getInstance(getActivity()).registerReceiver(
				new updatebroadcast(), new IntentFilter("szfrissit"));

		new LoadAllRacer().execute();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.finalsslalomlist, container, false);

		return v;
	}

	class LoadAllRacer extends AsyncTask<String, String, String> {

		boolean failed = false;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(
					SlalomAboveTop10Fragment2.this.getActivity());
			pDialog.setMessage("Versenyzõk betöltése, kérlek várj...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		protected String doInBackground(String... args) {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			JSONObject json = jParser.makeHttpRequest(
					url_all_slalom_top_results, "GET", params);

			try {
				int success = json.getInt(TAG_SUCCESS);

				if (success == 1) {
					racers = json.getJSONArray(TAG_PRODUCTS);

					racerList = new ArrayList<SlalomTop>();

					for (int i = 0; i < racers.length(); i++) {
						JSONObject c = racers.getJSONObject(i);

						SlalomTop racer = new SlalomTop();

						racer.setNumber(Integer.parseInt(c.getString("rajt")));
						racer.setName(c.getString("nev"));
						racer.setPid(Integer.parseInt(c.getString("pid")));

						racerList.add(racer);
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (Exception e) {
				failed = true;
				e.printStackTrace();
			}

			return null;
		}

		protected void onPostExecute(String file_url) {

			pDialog.dismiss();

			if (failed) {
				Toast.makeText(
						SlalomAboveTop10Fragment2.this.getActivity(),
						"Sikertelen lekérés, ellenõrizd az internetkapcsolatot",
						Toast.LENGTH_LONG).show();
			} else {
				adapter = new SlalomTop10Adapter(racerList);
				setListAdapter(adapter);
			}
		}
	}
}
