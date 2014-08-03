package gallery;

import hu.gyerob.trakiappdev.R;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings.ZoomDensity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

@SuppressLint("SetJavaScriptEnabled")
public class WebFragment extends Fragment {

	private WebView webView;

	@SuppressWarnings("deprecation")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.activity_gallery_web, container,
				false);

		webView = (WebView) v.findViewById(R.id.webView1);
		webView.setWebViewClient(new WebViewClient());
		webView.getSettings().setJavaScriptEnabled(true);
		// webView.setInitialScale(1);
		webView.getSettings().setDefaultZoom(ZoomDensity.FAR);
		webView.getSettings().setBuiltInZoomControls(true);
		// webView.getSettings().setUseWideViewPort(true);
		webView.setBackgroundColor(0xFF9CBB87);
		webView.loadUrl("httP://tv2014.ddns.net/trakiweb/pics");

		return v;
	}

	public void update() {
		webView.reload();
	}

	public WebView getWebView() {
		return webView;
	}
}
