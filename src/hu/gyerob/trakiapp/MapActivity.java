package hu.gyerob.trakiapp;

import map.TrakiMapRenderer;
import map.TrakiSurfaceView;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MapActivity extends Activity {

	private TrakiSurfaceView felulet;
	private Button cambutton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_map);
		
		felulet = (TrakiSurfaceView) findViewById(R.id.trakiSurfaceView1);
		cambutton = (Button) findViewById(R.id.cambutton);

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

		if (supportsEs2) {
			// OpenGL ES 2 context
			felulet.setEGLContextClientVersion(2);

			// Renderer beállítás
			felulet.setRenderer(new TrakiMapRenderer(this));
		} else
			return;

		cambutton.setOnClickListener(forgat);
	}

	private OnClickListener forgat = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			felulet.cam();
		}
	};
	
	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}
}
