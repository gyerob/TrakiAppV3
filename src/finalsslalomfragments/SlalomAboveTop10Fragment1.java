package finalsslalomfragments;

import hu.gyerob.trakiapp.R;

import java.util.ArrayList;
import java.util.List;

import jsonParser.JSONParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import toplistview.ToplistView;
import toplistview.ToplistView.IListViewUpdate;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import data.SlalomTop;

public class SlalomAboveTop10Fragment1 extends Fragment implements IListViewUpdate {
	public static final String TITLE = "Lépcsõk+";

	private static String url_get_slalom_top = "http://gyerob.no-ip.biz/trakiweb/get_all_slalom_a_top.php";
	private static String url_update_slalom_top = "http://gyerob.no-ip.biz/trakiweb/update_slalom_a_top.php";
	private static String url_update_next_slalom_top = "http://gyerob.no-ip.biz/trakiweb/update_next_slalom_a_top.php";

	private static final String TAG_SUCCESS = "success";
	private static final String TAG_PRODUCTS = "racers";

	// Progress Dialog
	private ProgressDialog pDialog;

	private JSONParser jsonParser;

	private ArrayList<SlalomTop> slalomListr1;
	private ArrayList<SlalomTop> slalomListr2;
	private ArrayList<SlalomTop> slalomListr3;
	private ArrayList<SlalomTop> slalomListr4;
	private JSONArray racers = null;

	ToplistView tvRound11;
	ToplistView tvRound12;
	ToplistView tvRound13;
	ToplistView tvRound14;
	ToplistView tvRound15;
	ToplistView tvRound16;
	ToplistView tvRound17;
	ToplistView tvRound18;

	ToplistView tvRound21;
	ToplistView tvRound22;
	ToplistView tvRound23;
	ToplistView tvRound24;

	ToplistView tvRound31;
	ToplistView tvRound32;
	ToplistView tvRound33;
	ToplistView tvRound34;

	ToplistView tvRound41;
	ToplistView tvRound42;
	ToplistView tvRound43;
	ToplistView tvRound44;

	ArrayList<ToplistView> topracers;
	
	public static SlalomAboveTop10Fragment1 newInstance() {
		SlalomAboveTop10Fragment1 fragment = new SlalomAboveTop10Fragment1();
		
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		jsonParser = new JSONParser();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.finalsslalomscrollv2, container,
				false);

		tvRound11 = (ToplistView) v.findViewById(R.id.stoplistr11);
		tvRound12 = (ToplistView) v.findViewById(R.id.stoplistr12);
		tvRound13 = (ToplistView) v.findViewById(R.id.stoplistr13);
		tvRound14 = (ToplistView) v.findViewById(R.id.stoplistr14);
		tvRound15 = (ToplistView) v.findViewById(R.id.stoplistr15);
		tvRound16 = (ToplistView) v.findViewById(R.id.stoplistr16);
		tvRound17 = (ToplistView) v.findViewById(R.id.stoplistr17);
		tvRound18 = (ToplistView) v.findViewById(R.id.stoplistr18);

		tvRound21 = (ToplistView) v.findViewById(R.id.stoplistr21);
		tvRound22 = (ToplistView) v.findViewById(R.id.stoplistr22);
		tvRound23 = (ToplistView) v.findViewById(R.id.stoplistr23);
		tvRound24 = (ToplistView) v.findViewById(R.id.stoplistr24);

		tvRound31 = (ToplistView) v.findViewById(R.id.stoplistr31);
		tvRound32 = (ToplistView) v.findViewById(R.id.stoplistr32);
		tvRound33 = (ToplistView) v.findViewById(R.id.stoplistr33);
		tvRound34 = (ToplistView) v.findViewById(R.id.stoplistr34);

		tvRound41 = (ToplistView) v.findViewById(R.id.stoplistr41);
		tvRound42 = (ToplistView) v.findViewById(R.id.stoplistr42);
		tvRound43 = (ToplistView) v.findViewById(R.id.stoplistr43);
		tvRound44 = (ToplistView) v.findViewById(R.id.stoplistr44);

		tvRound11.setSlalomListener(this);
		tvRound12.setSlalomListener(this);
		tvRound13.setSlalomListener(this);
		tvRound14.setSlalomListener(this);
		tvRound15.setSlalomListener(this);
		tvRound16.setSlalomListener(this);
		tvRound17.setSlalomListener(this);
		tvRound18.setSlalomListener(this);

		tvRound21.setSlalomListener(this);
		tvRound22.setSlalomListener(this);
		tvRound23.setSlalomListener(this);
		tvRound24.setSlalomListener(this);

		tvRound31.setSlalomListener(this);
		tvRound32.setSlalomListener(this);
		tvRound33.setSlalomListener(this);
		tvRound34.setSlalomListener(this);

		tvRound41.setSlalomListener(this);
		tvRound42.setSlalomListener(this);
		tvRound43.setSlalomListener(this);
		tvRound44.setSlalomListener(this);

		tvRound11.setRound(1);
		tvRound12.setRound(1);
		tvRound13.setRound(1);
		tvRound14.setRound(1);
		tvRound15.setRound(1);
		tvRound16.setRound(1);
		tvRound17.setRound(1);
		tvRound18.setRound(1);

		tvRound21.setRound(2);
		tvRound22.setRound(2);
		tvRound23.setRound(2);
		tvRound24.setRound(2);

		tvRound31.setRound(3);
		tvRound32.setRound(3);
		tvRound33.setRound(3);
		tvRound34.setRound(3);

		tvRound41.setRound(4);
		tvRound42.setRound(4);
		tvRound43.setRound(4);
		tvRound44.setRound(4);

		slalomListr1 = new ArrayList<SlalomTop>();
		slalomListr2 = new ArrayList<SlalomTop>();
		slalomListr3 = new ArrayList<SlalomTop>();
		slalomListr4 = new ArrayList<SlalomTop>();
		new GetList().execute();

		return v;
	}

	@Override
	public void onRacerClick(ToplistView tv) {
		Log.d("katt elfogás", "sikeres");

		new UpdateList().execute(tv);
	}

	class UpdateList extends AsyncTask<ToplistView, String, ToplistView> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(
					SlalomAboveTop10Fragment1.this.getActivity());
			pDialog.setMessage("Versenyzõ eltárolása..");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected ToplistView doInBackground(ToplistView... param) {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("rajt", Integer.toString(param[0]
					.getNumber())));
			params.add(new BasicNameValuePair("nev", param[0].getName()));
			params.add(new BasicNameValuePair("nyert", Integer
					.toString(param[0].getWin())));
			params.add(new BasicNameValuePair("kor", Integer.toString(param[0]
					.getRound())));

			Log.d("elküldendõ:",
					param[0].getName() + " " + param[0].getNumber() + " "
							+ param[0].getWin() + " " + param[0].getRound());

			JSONObject json = jsonParser.makeHttpRequest(url_update_slalom_top,
					"POST", params);

			Log.d("Create Response", json.toString());

			if (param[0].getWin() == 2) {
				params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("rajt", Integer
						.toString(param[0].getNumber())));
				params.add(new BasicNameValuePair("nev", param[0].getName()));
				params.add(new BasicNameValuePair("kor", Integer
						.toString(param[0].getRound() + 1)));

				if (param[0].getRound() == 1) {
					if (param[0].getPid() == 8 || param[0].getPid() == 1) {
						params.add(new BasicNameValuePair("pid", Integer
								.toString(3)));
					} else if (param[0].getPid() == 7 || param[0].getPid() == 2) {
						params.add(new BasicNameValuePair("pid", Integer
								.toString(2)));
					} else if (param[0].getPid() == 6 || param[0].getPid() == 3) {
						params.add(new BasicNameValuePair("pid", Integer
								.toString(4)));
					} else if (param[0].getPid() == 5 || param[0].getPid() == 4) {
						params.add(new BasicNameValuePair("pid", Integer
								.toString(1)));
					}
					json = jsonParser.makeHttpRequest(
							url_update_next_slalom_top, "POST", params);
				} else if (param[0].getRound() == 2) {
					if (param[0].getPid() == 1 || param[0].getPid() == 2) {
						params.add(new BasicNameValuePair("pid", Integer
								.toString(1)));
					} else if (param[0].getPid() == 3 || param[0].getPid() == 4) {
						params.add(new BasicNameValuePair("pid", Integer
								.toString(4)));
					}
					json = jsonParser.makeHttpRequest(
							url_update_next_slalom_top, "POST", params);
				} else if (param[0].getRound() == 3) {
					if (param[0].getPid() == 1 || param[0].getPid() == 2) {
						params.add(new BasicNameValuePair("pid", Integer
								.toString(1)));
						json = jsonParser.makeHttpRequest(
								url_update_next_slalom_top, "POST", params);
						if (param[0].getPid() == 1) {
							params = new ArrayList<NameValuePair>();
							params.add(new BasicNameValuePair("rajt", Integer
									.toString(tvRound32.getNumber())));
							params.add(new BasicNameValuePair("nev", tvRound32
									.getName()));
							params.add(new BasicNameValuePair("kor", Integer
									.toString(tvRound32.getRound() + 1)));
							params.add(new BasicNameValuePair("pid", Integer
									.toString(3)));
						} else {
							params = new ArrayList<NameValuePair>();
							params.add(new BasicNameValuePair("rajt", Integer
									.toString(tvRound31.getNumber())));
							params.add(new BasicNameValuePair("nev", tvRound31
									.getName()));
							params.add(new BasicNameValuePair("kor", Integer
									.toString(tvRound31.getRound() + 1)));
							params.add(new BasicNameValuePair("pid", Integer
									.toString(3)));
						}
						json = jsonParser.makeHttpRequest(
								url_update_next_slalom_top, "POST", params);
					} else if (param[0].getPid() == 3 || param[0].getPid() == 4) {
						params.add(new BasicNameValuePair("pid", Integer
								.toString(2)));
						json = jsonParser.makeHttpRequest(
								url_update_next_slalom_top, "POST", params);
						if (param[0].getPid() == 3) {
							params = new ArrayList<NameValuePair>();
							params.add(new BasicNameValuePair("rajt", Integer
									.toString(tvRound34.getNumber())));
							params.add(new BasicNameValuePair("nev", tvRound34
									.getName()));
							params.add(new BasicNameValuePair("kor", Integer
									.toString(tvRound34.getRound() + 1)));
							params.add(new BasicNameValuePair("pid", Integer
									.toString(4)));
						} else {
							params = new ArrayList<NameValuePair>();
							params.add(new BasicNameValuePair("rajt", Integer
									.toString(tvRound33.getNumber())));
							params.add(new BasicNameValuePair("nev", tvRound33
									.getName()));
							params.add(new BasicNameValuePair("kor", Integer
									.toString(tvRound33.getRound() + 1)));
							params.add(new BasicNameValuePair("pid", Integer
									.toString(4)));
						}
						json = jsonParser.makeHttpRequest(
								url_update_next_slalom_top, "POST", params);
					}
				}

				Intent intent = new Intent("szfrissit");
				if(LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent)) {
					Log.d("broadcast", "elküldve");
				}
				
				Log.d("Create Response", json.toString());
			}

			return param[0];
		}

		protected void onPostExecute(ToplistView nev) {
			pDialog.dismiss();
			if (nev.getWin() == 2) {
				if (nev.getRound() == 1) {
					if (nev.getPid() == 8 || nev.getPid() == 1) {
						tvRound23.setNumber(nev.getNumber());
						tvRound23.setPid(nev.getPid());
						tvRound23.setName(nev.getName());
					} else if (nev.getPid() == 7 || nev.getPid() == 2) {
						tvRound22.setNumber(nev.getNumber());
						tvRound22.setPid(nev.getPid());
						tvRound22.setName(nev.getName());
					} else if (nev.getPid() == 6 || nev.getPid() == 3) {
						tvRound24.setNumber(nev.getNumber());
						tvRound24.setPid(nev.getPid());
						tvRound24.setName(nev.getName());
					} else if (nev.getPid() == 5 || nev.getPid() == 4) {
						tvRound21.setNumber(nev.getNumber());
						tvRound21.setPid(nev.getPid());
						tvRound21.setName(nev.getName());
					}
				} else if (nev.getRound() == 2) {
					if (nev.getPid() == 1 || nev.getPid() == 2) {
						tvRound31.setNumber(nev.getNumber());
						tvRound31.setPid(nev.getPid());
						tvRound31.setName(nev.getName());
					} else if (nev.getPid() == 3 || nev.getPid() == 4) {
						tvRound34.setNumber(nev.getNumber());
						tvRound34.setPid(nev.getPid());
						tvRound34.setName(nev.getName());
					}
				} else if (nev.getRound() == 3) {
					if (nev.getPid() == 1 || nev.getPid() == 2) {
						tvRound41.setNumber(nev.getNumber());
						tvRound41.setPid(nev.getPid());
						tvRound41.setName(nev.getName());
						if (nev.getPid() == 1) {
							tvRound43.setNumber(tvRound32.getNumber());
							tvRound43.setPid(tvRound32.getPid());
							tvRound43.setName(tvRound32.getName());
						} else {
							tvRound43.setNumber(tvRound31.getNumber());
							tvRound43.setPid(tvRound31.getPid());
							tvRound43.setName(tvRound31.getName());
						}
					} else if (nev.getPid() == 3 || nev.getPid() == 4) {
						tvRound42.setNumber(nev.getNumber());
						tvRound42.setPid(nev.getPid());
						tvRound42.setName(nev.getName());
						if (nev.getPid() == 4) {
							tvRound44.setNumber(tvRound33.getNumber());
							tvRound44.setPid(tvRound33.getPid());
							tvRound44.setName(tvRound33.getName());
						} else {
							tvRound44.setNumber(tvRound34.getNumber());
							tvRound44.setPid(tvRound34.getPid());
							tvRound44.setName(tvRound34.getName());
						}
					}
				}
			}
		}
	}

	class GetList extends AsyncTask<String, String, String> {

		boolean failed = false;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(
					SlalomAboveTop10Fragment1.this.getActivity());
			pDialog.setMessage("Versenyzõ frissítése..");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... param) {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			JSONObject json = jsonParser.makeHttpRequest(url_get_slalom_top,
					"GET", params);

			try {
				int success = json.getInt(TAG_SUCCESS);

				if (success == 1) {
					racers = json.getJSONArray(TAG_PRODUCTS);

					Log.d("racers hossza:", Integer.toString(racers.length()));
					for (int i = 0; i < racers.length(); i++) {
						JSONObject c = racers.getJSONObject(i);

						SlalomTop racer = new SlalomTop();

						racer.setNumber(Integer.parseInt(c.getString("rajt")));
						racer.setName(c.getString("nev"));
						racer.setWon(Integer.parseInt(c.getString("nyert")));
						racer.setPid(Integer.parseInt(c.getString("pid")));

						if (i < 8)
							slalomListr1.add(racer);
						else if (i < 12)
							slalomListr2.add(racer);
						else if (i < 16)
							slalomListr3.add(racer);
						else if (i < 20)
							slalomListr4.add(racer);
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
						SlalomAboveTop10Fragment1.this.getActivity(),
						"Sikertelen lekérés\n, ellenõrizd az internetkapcsolatot.",
						Toast.LENGTH_LONG).show();
			} else {
				tvRound11.setData(slalomListr1.get(4).getName(), slalomListr1
						.get(4).getNumber(), slalomListr1.get(4).getWon(),
						slalomListr1.get(4).getPid());
				Log.d("1round", tvRound11.getName() + " " + tvRound11.getPid());
				tvRound12.setData(slalomListr1.get(3).getName(), slalomListr1
						.get(3).getNumber(), slalomListr1.get(3).getWon(),
						slalomListr1.get(3).getPid());
				Log.d("1round", tvRound12.getName() + " " + tvRound12.getPid());
				tvRound13.setData(slalomListr1.get(1).getName(), slalomListr1
						.get(1).getNumber(), slalomListr1.get(1).getWon(),
						slalomListr1.get(1).getPid());
				Log.d("1round", tvRound13.getName() + " " + tvRound13.getPid());
				tvRound14.setData(slalomListr1.get(6).getName(), slalomListr1
						.get(6).getNumber(), slalomListr1.get(6).getWon(),
						slalomListr1.get(6).getPid());
				Log.d("1round", tvRound14.getName() + " " + tvRound14.getPid());
				tvRound15.setData(slalomListr1.get(7).getName(), slalomListr1
						.get(7).getNumber(), slalomListr1.get(7).getWon(),
						slalomListr1.get(7).getPid());
				Log.d("1round", tvRound15.getName() + " " + tvRound15.getPid());
				tvRound16.setData(slalomListr1.get(0).getName(), slalomListr1
						.get(0).getNumber(), slalomListr1.get(0).getWon(),
						slalomListr1.get(0).getPid());
				Log.d("1round", tvRound16.getName() + " " + tvRound16.getPid());
				tvRound17.setData(slalomListr1.get(2).getName(), slalomListr1
						.get(2).getNumber(), slalomListr1.get(2).getWon(),
						slalomListr1.get(2).getPid());
				Log.d("1round", tvRound17.getName() + " " + tvRound17.getPid());
				tvRound18.setData(slalomListr1.get(5).getName(), slalomListr1
						.get(5).getNumber(), slalomListr1.get(5).getWon(),
						slalomListr1.get(5).getPid());
				Log.d("1round", tvRound18.getName() + " " + tvRound18.getPid());

				tvRound21.setData(slalomListr2.get(0).getName(), slalomListr2
						.get(0).getNumber(), slalomListr2.get(0).getWon(),
						slalomListr2.get(0).getPid());
				tvRound22.setData(slalomListr2.get(1).getName(), slalomListr2
						.get(1).getNumber(), slalomListr2.get(1).getWon(),
						slalomListr2.get(1).getPid());
				tvRound23.setData(slalomListr2.get(2).getName(), slalomListr2
						.get(2).getNumber(), slalomListr2.get(2).getWon(),
						slalomListr2.get(2).getPid());
				tvRound24.setData(slalomListr2.get(3).getName(), slalomListr2
						.get(3).getNumber(), slalomListr2.get(3).getWon(),
						slalomListr2.get(3).getPid());

				tvRound31.setData(slalomListr3.get(0).getName(), slalomListr3
						.get(0).getNumber(), slalomListr3.get(0).getWon(),
						slalomListr3.get(0).getPid());
				tvRound32.setData(slalomListr3.get(1).getName(), slalomListr3
						.get(1).getNumber(), slalomListr3.get(1).getWon(),
						slalomListr3.get(1).getPid());
				tvRound33.setData(slalomListr3.get(2).getName(), slalomListr3
						.get(2).getNumber(), slalomListr3.get(2).getWon(),
						slalomListr3.get(2).getPid());
				tvRound34.setData(slalomListr3.get(3).getName(), slalomListr3
						.get(3).getNumber(), slalomListr3.get(3).getWon(),
						slalomListr3.get(3).getPid());

				tvRound41.setData(slalomListr4.get(0).getName(), slalomListr4
						.get(0).getNumber(), slalomListr4.get(0).getWon(),
						slalomListr4.get(0).getPid());
				tvRound42.setData(slalomListr4.get(1).getName(), slalomListr4
						.get(1).getNumber(), slalomListr4.get(1).getWon(),
						slalomListr4.get(1).getPid());
				tvRound43.setData(slalomListr4.get(2).getName(), slalomListr4
						.get(2).getNumber(), slalomListr4.get(2).getWon(),
						slalomListr4.get(2).getPid());
				tvRound44.setData(slalomListr4.get(3).getName(), slalomListr4
						.get(3).getNumber(), slalomListr4.get(3).getWon(),
						slalomListr4.get(3).getPid());
			}
		}
	}
}
