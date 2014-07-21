package hu.gyerob.trakiapp;

import map.TrakiMapRenderer;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.widget.Toast;

public class MapActivity extends ActionBarActivity {

	private GLSurfaceView felulet;
	private boolean rendererset;
	private TrakiMapRenderer mapRenderer;
	private float scale = 1f;
	private ScaleGestureDetector sgd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		felulet = new GLSurfaceView(this);

		// OpenGL ES 2 kompatibilitás ellenõrzés
		final ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		final ConfigurationInfo configurationInfo = activityManager
				.getDeviceConfigurationInfo();
		final boolean supportsEs2 = configurationInfo.reqGlEsVersion >= 0x20000
				|| (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1 && (Build.FINGERPRINT
						.startsWith("generic")
						|| Build.FINGERPRINT.startsWith("unknown")
						|| Build.MODEL.contains("google_sdk")
						|| Build.MODEL.contains("Emulator") || Build.MODEL
							.contains("Android SDK built for x86")));

		mapRenderer = new TrakiMapRenderer(this);

		if (supportsEs2) {
			// OpenGL ES 2 context
			felulet.setEGLContextClientVersion(2);

			felulet.setEGLConfigChooser(8, 8, 8, 8, 16, 0);

			// Renderer beállítás
			felulet.setRenderer(mapRenderer);
			rendererset = true;
		} else {
			Toast.makeText(this, "Az eszköz nem támogatja az OpenGL ES 2.0-t.",
					Toast.LENGTH_LONG).show();
			rendererset = false;
			return;
		}

		felulet.setFocusable(true);
		felulet.setFocusableInTouchMode(true);
		felulet.requestFocus();

		felulet.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (event.getAction() == KeyEvent.ACTION_DOWN)
					Log.d("nyomott gomb",
							"lenyomva: " + Integer.toString(keyCode));
				if (keyCode == KeyEvent.KEYCODE_W
						&& event.getAction() == KeyEvent.ACTION_DOWN) {
					felulet.queueEvent(new Runnable() {
						@Override
						public void run() {
							mapRenderer.keyHandle(0);
						}
					});
					return true;
				} else if (keyCode == KeyEvent.KEYCODE_S
						&& event.getAction() == KeyEvent.ACTION_DOWN) {
					felulet.queueEvent(new Runnable() {
						@Override
						public void run() {
							mapRenderer.keyHandle(1);
						}
					});
					return true;
				} else if (keyCode == KeyEvent.KEYCODE_A
						&& event.getAction() == KeyEvent.ACTION_DOWN) {
					felulet.queueEvent(new Runnable() {
						@Override
						public void run() {
							mapRenderer.keyHandle(2);
						}
					});
					return true;
				} else if (keyCode == KeyEvent.KEYCODE_D
						&& event.getAction() == KeyEvent.ACTION_DOWN) {
					felulet.queueEvent(new Runnable() {
						@Override
						public void run() {
							mapRenderer.keyHandle(3);
						}
					});
					return true;
				} else
					return false;
			}
		});

		sgd = new ScaleGestureDetector(this, new ScaleListener());

		felulet.setOnTouchListener(new OnTouchListener() {
			float previousX, previousY;
			float deltaX, deltaY;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				sgd.onTouchEvent(event);

				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					previousX = event.getX();
					previousY = event.getY();
				} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
					if (!sgd.isInProgress()) {
						deltaX = event.getX() - previousX;
						deltaY = event.getY() - previousY;
					}

					previousX = event.getX();
					previousY = event.getY();

					felulet.queueEvent(new Runnable() {
						@Override
						public void run() {
							mapRenderer.handledrag(deltaX, deltaY);
							mapRenderer.setZoom(scale);
						}
					});
				}

				return true;
			}

		});

		setContentView(felulet);
	}

	private class ScaleListener extends SimpleOnScaleGestureListener {

		@Override
		public boolean onScale(ScaleGestureDetector detector) {
			scale *= detector.getScaleFactor();
			scale = Math.max(0.1f, Math.min(scale, 5.0f));
			return true;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.mapmenu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.mapstart) {
			mapRenderer.start();
		} else if (item.getItemId() == R.id.mapa) {
			mapRenderer.settrakiview(true, false);
		} else if (item.getItemId() == R.id.mapb) {
			mapRenderer.settrakiview(false, true);
		} else if (item.getItemId() == R.id.mapfree) {
			mapRenderer.settrakiview(false, false);
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (rendererset)
			felulet.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (rendererset)
			felulet.onResume();
	}
}
