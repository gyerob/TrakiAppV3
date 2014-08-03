package gallery;

import hu.gyerob.trakiappdev.R;

import java.util.ArrayList;
import java.util.List;

import jsonParser.JSONParser;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import adapter.GridViewAdapter;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;

public class GridFragment extends Fragment {

	private static String url_get_all_image = "http://tv2014.ddns.net/trakiweb/get_all_image.php";
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_PRODUCTS = "images";

	private JSONParser jsonParser = new JSONParser();;
	private GridViewAdapter gridAdapter;
	private ArrayList<String> picturenames;
	private JSONArray images = null;
	private GridView gridView;
	private ImageView iv;

	private Matrix matrix = new Matrix();
	private float scale = 1f;
	private float bitmaph, bitmapw;
	private float width, height;
	private ScaleGestureDetector sgd;

	private float lastX;
	private float lastY;

	private float posX;
	private float posY;
	private int pointerID;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.activity_gallery_grid, container,
				false);

		picturenames = new ArrayList<String>();

		gridView = (GridView) v.findViewById(R.id.gridview);
		gridAdapter = new GridViewAdapter(getActivity(), R.layout.row_grid,
				picturenames);
		gridView.setAdapter(gridAdapter);

		iv = (ImageView) v.findViewById(R.id.galleryiv);
		iv.setImageDrawable(getResources().getDrawable(R.drawable.icon));

		sgd = new ScaleGestureDetector(getActivity(), new ScaleListener());

		width = (float) iv.getWidth();
		height = (float) iv.getHeight();

		iv.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				sgd.onTouchEvent(event);
				final int action = event.getAction();
				if (action == MotionEvent.ACTION_DOWN) {
					lastX = event.getX();
					lastY = event.getY();
					pointerID = event.getPointerId(0);
				} else if (action == MotionEvent.ACTION_MOVE) {
					final int pointerIndex = event.findPointerIndex(pointerID);
					final float x = event.getX(pointerIndex);
					final float y = event.getY(pointerIndex);

					if (!sgd.isInProgress()) {
						final float deltaX = x - lastX;
						final float deltaY = y - lastY;

						posX += deltaX;
						posY += deltaY;
						Log.d("képpos",
								Float.toString(posX) + " "
										+ Float.toString(posY));
					}

					lastX = x;
					lastY = y;
				} else if (action == MotionEvent.ACTION_UP) {
					pointerID = -1;
				} else if (action == MotionEvent.ACTION_CANCEL) {
					pointerID = -1;
				} else if (action == MotionEvent.ACTION_POINTER_UP) {
					final int pointerIndex = (event.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
					final int pointerId = event.getPointerId(pointerIndex);
					if (pointerId == pointerID) {
						final int newpointerIndex = pointerIndex == 0 ? 1 : 0;
						lastX = event.getX(newpointerIndex);
						lastY = event.getY(newpointerIndex);
						pointerID = event.getPointerId(newpointerIndex);
					}
				}

				bitmaph = iv.getDrawable().getIntrinsicHeight() * scale;
				bitmapw = iv.getDrawable().getIntrinsicWidth() * scale;

				width = (float) iv.getWidth();
				height = (float) iv.getHeight();

				Log.d("bitmapméret",
						Float.toString(bitmaph) + " " + Float.toString(bitmapw));
				Log.d("képméret",
						Float.toString(width) + " " + Float.toString(height));

				if (bitmaph <= height) {
					Log.d("alacsonyabb", "alacsonyabb");
					if (posY <= 0f)
						posY = 0f;
					else if (posY >= (height - bitmaph)) {
						posY = (height - bitmaph);
					}
				} else {
					
				}

				if (bitmapw <= width) {
					if (posX <= 0f)
						posX = 0f;
					else if (posX >= (width - bitmapw)) {
						posX = (width - bitmapw);
					}
				} else {
					
				}

				matrix.reset();
				matrix.postScale(scale, scale);
				matrix.postTranslate(posX, posY);
				iv.setImageMatrix(matrix);

				return true;
			}
		});

		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				Bitmap bm = ((GridViewAdapter) gridView.getAdapter())
						.getBitmap(position);
				if (bm != null)
					iv.setImageBitmap(bm);
			}
		});

		new GetImages().execute();

		return v;
	}

	public ImageView getIV() {
		return iv;
	}

	private class ScaleListener extends SimpleOnScaleGestureListener {

		@Override
		public boolean onScale(ScaleGestureDetector detector) {
			scale *= detector.getScaleFactor();
			scale = Math.max(0.1f, Math.min(scale, 5.0f));

			return true;
		}
	}

	public class GetImages extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... param) {
			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			// getting JSON string from URL
			JSONObject json = jsonParser.makeHttpRequest(url_get_all_image,
					"GET", params);

			picturenames = new ArrayList<String>();

			try {
				// Checking for SUCCESS TAG
				int success = json.getInt(TAG_SUCCESS);

				if (success == 1) {
					// products found
					// Getting Array of Products
					images = json.getJSONArray(TAG_PRODUCTS);

					// looping through All Products
					for (int i = 0; i < images.length(); i++) {
						JSONObject c = images.getJSONObject(i);

						// Storing each json item in variable
						String nev = c.getString("nev");

						picturenames.add(nev);
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);

			gridAdapter.clear();
			gridAdapter.addAll(picturenames);
			gridAdapter.notifyDataSetChanged();
		}
	}

}
