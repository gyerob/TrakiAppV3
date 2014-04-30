package hu.gyerob.trakiapp;

import adapter.InputFragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

public class InputActivity extends FragmentActivity {

	private ViewPager pager;
	private InputFragmentPagerAdapter inputadapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_input);
				
		pager = (ViewPager) findViewById(R.id.inputViewPager);
		inputadapter = new InputFragmentPagerAdapter(
				getSupportFragmentManager());
		pager.setAdapter(inputadapter);
	}
}
