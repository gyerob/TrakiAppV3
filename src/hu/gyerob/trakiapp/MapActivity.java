package hu.gyerob.trakiapp;

import map.TrakiMapRenderer2;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Toast;

public class MapActivity extends Activity {

	private GLSurfaceView felulet;

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

		final TrakiMapRenderer2 mapRenderer = new TrakiMapRenderer2(this);

		if (supportsEs2) {
			// OpenGL ES 2 context
			felulet.setEGLContextClientVersion(2);

			// Renderer beállítás
			felulet.setRenderer(mapRenderer);
		} else {
			Toast.makeText(this, "Az eszköz nem támogatja az OpenGL ES 2.0-t.",
					Toast.LENGTH_LONG).show();
			return;
		}

		felulet.setOnTouchListener(new OnTouchListener() {
			float previousX, previousY;

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event != null) {
					if (event.getAction() == MotionEvent.ACTION_DOWN) {
						previousX = event.getX();
						previousY = event.getY();
					} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
						final float deltaX = event.getX() - previousX;
						final float deltaY = event.getY() - previousY;

						previousX = event.getX();
						previousY = event.getY();

						felulet.queueEvent(new Runnable() {
							@Override
							public void run() {
								mapRenderer.handledrag(deltaX, deltaY);
							}
						});
					}

					return true;
				} else {
					return false;
				}
			}
		});

		setContentView(felulet);
	}

	@Override
	protected void onPause() {
		super.onPause();
		felulet.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		felulet.onResume();
	}
}
