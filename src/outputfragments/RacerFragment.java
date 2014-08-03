package outputfragments;

import hu.gyerob.trakiappdev.R;

import java.util.ArrayList;
import java.util.List;

import jsonParser.JSONParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import adapter.RacerAdapter;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import data.Racer;

public class RacerFragment extends ListFragment {
	public static final String TITLE = "Versenyzõk";

	private RacerAdapter adapter;
	private ArrayList<Racer> racerList;

	private ProgressDialog pDialog;
	private LoadAllRacer loadracers;

	private JSONParser jsonParser = new JSONParser();

	private static String url_all_racer = "http://tv2014.ddns.net/trakiweb/get_all_racer.php";

	private static final String TAG_SUCCESS = "success";
	private static final String TAG_PRODUCTS = "racers";

	private JSONArray racers = null;

	private static int GROUP = 0;

	public static RacerFragment newInstance(int mode, int group) {
		RacerFragment racer = new RacerFragment();

		Bundle args = new Bundle();
		args.putInt("group", group);
		racer.setArguments(args);
		
		return racer;
	}

	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);

		GROUP = getArguments() != null ? getArguments().getInt("group") : 0;
		
		racerList = new ArrayList<Racer>();

		loadracers = new LoadAllRacer();
		loadracers.execute();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.output_racer, container, false);
		return v;
	}

	class LoadAllRacer extends AsyncTask<String, String, String> {

		boolean failed = false;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(RacerFragment.this.getActivity());
			pDialog.setMessage("Versenyzõk betöltése, kérlek várj...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		protected String doInBackground(String... args) {
			List<NameValuePair> params = new ArrayList<NameValuePair>();

			if (GROUP == 0) {
				params.add(new BasicNameValuePair("group", "0"));
			} else if (GROUP == 1) {
				params.add(new BasicNameValuePair("group", "1"));
			} else if (GROUP == 2) {
				params.add(new BasicNameValuePair("group", "2"));
			}
			
			JSONObject json = jsonParser.makeHttpRequest(url_all_racer, "GET", params);
			
			if (json != null) {
				try {
					int success = json.getInt(TAG_SUCCESS);

					if (success == 1) {
						racers = json.getJSONArray(TAG_PRODUCTS);

						for (int i = 0; i < racers.length(); i++) {
							JSONObject c = racers.getJSONObject(i);

							Racer racer = new Racer();

							racer.setNumber(Integer.parseInt(c
									.getString("rajt")));
							racer.setName(c.getString("nev"));
							racer.setTown(c.getString("varos"));
							racer.setTrailer((Boolean.parseBoolean(c
									.getString("potkocsi"))));
							racer.setSlalom(Boolean.parseBoolean(c
									.getString("szlalom")));
							racer.setDrag(Boolean.parseBoolean(c
									.getString("gyorsulas")));
							racer.setPoint(c.getString("pont"));

							racerList.add(racer);
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				} catch (Exception e) {
					failed = true;
					e.printStackTrace();
				}
			} else {
				failed = true;
			}
			return null;
		}

		protected void onPostExecute(String file_url) {

			pDialog.dismiss();

			if (failed) {
				Toast.makeText(
						RacerFragment.this.getActivity(),
						"Sikertelen lekérés, ellenõrizd az internetkapcsolatot",
						Toast.LENGTH_LONG).show();
			} else {
				adapter = new RacerAdapter(racerList);
				setListAdapter(adapter);
			}
		}
	}
}
