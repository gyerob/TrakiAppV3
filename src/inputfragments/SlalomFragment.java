package inputfragments;

import hu.gyerob.trakiappdev.R;

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
import android.widget.Button;
import android.widget.EditText;

public class SlalomFragment extends Fragment {
	public static final String TITLE = "Szlalom";

	// url to create new product
	private static String url_update_slalom = "http://tv2014.ddns.net/trakiweb/update_slalom.php";

	// Progress Dialog
	private ProgressDialog pDialog;

	JSONParser jsonParser = new JSONParser();

	private Button save;
	private EditText number;
	private EditText hiba;
	private EditText p;
	private EditText mp;
	private EditText ms;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.input_slalom, container, false);

		save = (Button) v.findViewById(R.id.inputslalombtn);

		number = (EditText) v.findViewById(R.id.inputslalomEditNumber);
		p = (EditText) v.findViewById(R.id.inputslalomEditP);
		mp = (EditText) v.findViewById(R.id.inputslalomEditMP);
		ms = (EditText) v.findViewById(R.id.inputslalomEditMS);
		hiba = (EditText) v.findViewById(R.id.inputslalomEditError);

		save.setOnClickListener(click);

		return v;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	OnClickListener click = new OnClickListener() {

		@Override
		public void onClick(View v) {

			new UpdateSlalom().execute();
		}
	};

	class UpdateSlalom extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(SlalomFragment.this.getActivity());
			pDialog.setMessage("Versenyzõ eltárolása..");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		/**
		 * Creating product
		 * */
		protected String doInBackground(String... args) {
			String rajt = number.getText().toString();
			String time = "harr";
			String fault = hiba.getText().toString();
			String finaltime = "harr";

			int perc, mperc, h;
			String m, s, ezred;
			perc = Integer.parseInt(p.getText().toString());
			mperc = Integer.parseInt(mp.getText().toString());
			ezred = ms.getText().toString();
			h = Integer.parseInt(hiba.getText().toString());

			if(ezred.length() == 0) ezred = "000";
			else if (ezred.length() == 1) ezred = ezred + "00";
			else if (ezred.length() == 2) ezred = ezred + "0";
			
			if(perc<10) m = "0" + perc;
			else m = Integer.toString(perc);
			if(mperc<10) s = "0" + mperc;
			else s = Integer.toString(mperc);
			
			time = m + ":" + s +":" + ezred;
			
			int ujmp = ((h*5) + mperc);
			while(ujmp>59){
				ujmp -= 60;
				perc++;
			}
			
			if(perc<10) m = "0" + perc;
			else m = Integer.toString(perc);
			if(ujmp<10) s = "0" + ujmp;
			else s = Integer.toString(ujmp);
			
			finaltime = m + ":" + s + ":" + ezred;
			Log.d("ido", time);

			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("rajt", rajt));
			params.add(new BasicNameValuePair("ido", time));
			params.add(new BasicNameValuePair("hiba", fault));
			params.add(new BasicNameValuePair("vido", finaltime));

			// getting JSON Object
			// Note that create product url accepts POST method
			JSONObject json = jsonParser.makeHttpRequest(url_update_slalom,
					"POST", params);

			// check log cat fro response
			Log.d("Create Response", json.toString());

			return null;
		}

		protected void onPostExecute(String file_url) {
			// dismiss the dialog once done
			pDialog.dismiss();
		}
	}
}
