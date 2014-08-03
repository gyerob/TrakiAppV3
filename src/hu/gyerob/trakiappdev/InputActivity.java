package hu.gyerob.trakiappdev;

import adapter.InputFragmentPagerAdapter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;

public class InputActivity extends ActionBarActivity {

	private ViewPager pager;
	private InputFragmentPagerAdapter inputadapter;
	private PagerTabStrip strip;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_input);
				
		pager = (ViewPager) findViewById(R.id.inputViewPager);
		strip = (PagerTabStrip) findViewById(R.id.inputPagerTabStrip);
		
		inputadapter = new InputFragmentPagerAdapter(
				getSupportFragmentManager());
		pager.setAdapter(inputadapter);

		strip.setTextColor(Color.BLACK);
		strip.setTabIndicatorColor(Color.BLACK);
	}
}
