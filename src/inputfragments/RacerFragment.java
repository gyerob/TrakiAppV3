package inputfragments;

import hu.gyerob.trakiapp.R;

import java.util.ArrayList;
import java.util.List;

import jsonParser.JSONParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

public class RacerFragment extends Fragment {

	public static final String TITLE = "Versenyzõk";

	private static String url_create_racer = "http://tv2014.ddns.net/trakiweb/create_racer.php";
	private static String url_delete_racer = "http://tv2014.ddns.net/trakiweb/delete_racer.php";
	private static String url_update_racer = "http://tv2014.ddns.net/trakiweb/update_racer.php";

	private ProgressDialog pDialog;

	private JSONParser jsonParser = new JSONParser();

	private Button save;
	private Button delete;
	private Button update;
	private EditText name;
	private EditText number;
	private EditText town;
	private CheckBox trailer;
	private CheckBox slalom;
	private CheckBox drag;
	private Spinner group;

	private int selectedpos = 0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.input_racer, container, false);

		save = (Button) v.findViewById(R.id.inputracerbtn);
		update = (Button) v.findViewById(R.id.inputracerbtnupdate);
		delete = (Button) v.findViewById(R.id.inputracerbtndelete);

		name = (EditText) v.findViewById(R.id.inputracerEditName);
		number = (EditText) v.findViewById(R.id.inputracerEditNumber);
		town = (EditText) v.findViewById(R.id.inputracerEditTown);
		trailer = (CheckBox) v.findViewById(R.id.inputracerTrailer);
		slalom = (CheckBox) v.findViewById(R.id.inputracerSlalom);
		drag = (CheckBox) v.findViewById(R.id.inputracerDrag);
		group = (Spinner) v.findViewById(R.id.myspinner);

		group.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				selectedpos = position;
				Log.d("selected", Integer.toString(selectedpos));
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});

		save.setOnClickListener(savemethod);
		update.setOnClickListener(updatemethod);
		delete.setOnClickListener(deletemethod);
		return v;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	OnClickListener savemethod = new OnClickListener() {

		@Override
		public void onClick(View v) {

			new CreateNewRacer().execute();
		}
	};

	OnClickListener updatemethod = new OnClickListener() {

		@Override
		public void onClick(View v) {

			new UpdateRacer().execute();
		}
	};

	OnClickListener deletemethod = new OnClickListener() {

		@Override
		public void onClick(View v) {

			new DeleteRacer().execute();
		}
	};

	class CreateNewRacer extends AsyncTask<String, String, String> {
		private boolean fail = false;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(RacerFragment.this.getActivity());
			pDialog.setMessage("Versenyzõ eltárolása..");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		protected String doInBackground(String... args) {
			String nev = name.getText().toString();
			String rajt = number.getText().toString();
			String varos = town.getText().toString();
			String csoport = Integer.toString(selectedpos);
			String potk, szlalom, gyors;

			if (trailer.isChecked())
				potk = "true";
			else
				potk = "false";
			if (slalom.isChecked())
				szlalom = "true";
			else
				szlalom = "false";
			if (drag.isChecked())
				gyors = "true";
			else
				gyors = "false";

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("name", nev));
			params.add(new BasicNameValuePair("number", rajt));
			params.add(new BasicNameValuePair("town", varos));
			params.add(new BasicNameValuePair("trailer", potk));
			params.add(new BasicNameValuePair("slalom", szlalom));
			params.add(new BasicNameValuePair("drag", gyors));
			params.add(new BasicNameValuePair("group", csoport));

			JSONObject json = jsonParser.makeHttpRequest(url_create_racer,
					"POST", params);

			if (json == null)
				fail = true;

			Log.d("Create Response", json.toString());

			return null;
		}

		protected void onPostExecute(String file_url) {
			pDialog.dismiss();

			if (!fail) {
				name.setText("");
				number.setText("");
				town.setText("");
				trailer.setChecked(false);
				slalom.setChecked(false);
				drag.setChecked(false);
				group.setSelection(0);
			}
		}
	}

	class DeleteRacer extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(RacerFragment.this.getActivity());
			pDialog.setMessage("Versenyzõ eltávolítása..");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		protected String doInBackground(String... args) {
			String rajt = number.getText().toString();

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("rajt", rajt));

			JSONObject json = jsonParser.makeHttpRequest(url_delete_racer,
					"POST", params);

			Log.d("Create Response", json.toString());

			return null;
		}

		protected void onPostExecute(String file_url) {
			pDialog.dismiss();

			name.setText("");
			number.setText("");
			town.setText("");
			trailer.setChecked(false);
			slalom.setChecked(false);
			drag.setChecked(false);
		}
	}

	class UpdateRacer extends AsyncTask<String, String, String> {
		private boolean fail = false;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(RacerFragment.this.getActivity());
			pDialog.setMessage("Versenyzõ frissítése..");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@SuppressWarnings("unused")
		protected String doInBackground(String... args) {
			Log.d("frissítés", "kezdõdik");
			String nev = name.getText().toString();
			String rajt = number.getText().toString();
			String varos = town.getText().toString();
			String csoport = Integer.toString(selectedpos);
			String potk, szlalom, gyors;

			if (trailer.isChecked())
				potk = "true";
			else
				potk = "false";
			if (slalom.isChecked())
				szlalom = "true";
			else
				szlalom = "false";
			if (drag.isChecked())
				gyors = "true";
			else
				gyors = "false";
			Log.d("potk:szl:gy", potk + " " + szlalom + " " + gyors);

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("name", nev));
			params.add(new BasicNameValuePair("number", rajt));
			params.add(new BasicNameValuePair("town", varos));
			params.add(new BasicNameValuePair("trailer", potk));
			params.add(new BasicNameValuePair("slalom", szlalom));
			params.add(new BasicNameValuePair("drag", gyors));
			params.add(new BasicNameValuePair("group", csoport));

			JSONObject json = jsonParser.makeHttpRequest(url_update_racer,
					"POST", params);

			Log.d("Create Response", json.toString());

			if (json == null)
				fail = true;

			return null;
		}

		protected void onPostExecute(String file_url) {
			pDialog.dismiss();

			if (!fail) {
				name.setText("");
				number.setText("");
				town.setText("");
				trailer.setChecked(false);
				slalom.setChecked(false);
				drag.setChecked(false);
				group.setSelection(0);
			}
		}
	}
}
