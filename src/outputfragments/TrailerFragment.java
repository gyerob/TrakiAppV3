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

import adapter.TrailerAdapter;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import data.Trailer;

public class TrailerFragment extends ListFragment {
	public static final String TITLE = "P�tkocsis";

	private TrailerAdapter adapter;
	private ArrayList<Trailer> trailerList;

	private ProgressDialog pDialog;
	private LoadAllTrailer loadtrailers;

	private JSONParser jParser = new JSONParser();

	private static String url_all_trailer = "http://tv2014.ddns.net/trakiweb/get_all_trailer.php";

	private static final String TAG_SUCCESS = "success";
	private static final String TAG_PRODUCTS = "trailer";

	private JSONArray trailers = null;

	private static int MODE = 0;
	private static int GROUP = 0;

	public static TrailerFragment newInstance(int mode, int group) {
		TrailerFragment trailer = new TrailerFragment();

		Bundle args = new Bundle();
		args.putInt("mode", mode);
		args.putInt("group", group);
		trailer.setArguments(args);

		return trailer;
	}

	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);

		int mode = getArguments() != null ? getArguments().getInt("mode") : 0;
		if (mode == 1 || mode == 4 || mode == 5) {
			MODE = mode;
		}
		
		GROUP = getArguments() != null ? getArguments().getInt("group") : 0;

		trailerList = new ArrayList<Trailer>();

		loadtrailers = new LoadAllTrailer();
		loadtrailers.execute();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.output_trailer, container, false);

		return v;
	}

	private class LoadAllTrailer extends AsyncTask<String, String, String> {

		boolean failed = false;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(TrailerFragment.this.getActivity());
			pDialog.setMessage("Versenyz�k bet�lt�se, k�rlek v�rj...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		protected String doInBackground(String... args) {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			if (MODE == 4)
				params.add(new BasicNameValuePair("type", "veteran"));
			else if (MODE == 5)
				params.add(new BasicNameValuePair("type", "modern"));

			if (GROUP == 0) {
				params.add(new BasicNameValuePair("group", "0"));
			} else if (GROUP == 1) {
				params.add(new BasicNameValuePair("group", "1"));
			} else if (GROUP == 2) {
				params.add(new BasicNameValuePair("group", "2"));
			}

			JSONObject json = jParser.makeHttpRequest(url_all_trailer, "GET",
					params);

			try {
				int success = json.getInt(TAG_SUCCESS);

				if (success == 1) {
					trailers = json.getJSONArray(TAG_PRODUCTS);

					trailerList = new ArrayList<Trailer>();

					for (int i = 0; i < trailers.length(); i++) {
						JSONObject c = trailers.getJSONObject(i);

						Trailer trailer = new Trailer();

						trailer.setNumber(Integer.parseInt(c.getString("rajt")));
						trailer.setName(c.getString("nev"));
						trailer.setIdo(c.getString("ido"));
						trailer.setHiba((c.getInt("hiba")));
						trailer.setVido((c.getString("vido")));

						trailerList.add(trailer);
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
						TrailerFragment.this.getActivity(),
						"Sikertelen lek�r�s, ellen�rizd az internetkapcsolatot",
						Toast.LENGTH_LONG).show();
			} else {
				adapter = new TrailerAdapter(trailerList);
				setListAdapter(adapter);
			}
		}
	}
}
