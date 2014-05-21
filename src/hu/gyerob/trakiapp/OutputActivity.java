package hu.gyerob.trakiapp;

import adapter.OutputFragmentPagerAdapter;
import android.app.ActionBar;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class OutputActivity extends FragmentActivity {

	private ViewPager pager;
	private PagerTabStrip strip;
	private OutputFragmentPagerAdapter outputadapter;
	private ActionBar actionbar;
	
	private int HIDE_STATE;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_output);
		
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		
		pager = (ViewPager) findViewById(R.id.outputViewPager);
		strip = (PagerTabStrip) findViewById(R.id.outputPagerTabStrip);
		
		outputadapter = new OutputFragmentPagerAdapter(
				getSupportFragmentManager());
		pager.setAdapter(outputadapter);
				
		strip.setTextColor(Color.BLACK);
		strip.setTabIndicatorColor(Color.BLACK);
		
		pager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int pos) {
				if (pos == 0) HIDE_STATE = 0;
				else if (pos == 1) HIDE_STATE = 1;
				else if (pos == 2) HIDE_STATE = 2;
				else if (pos == 3) HIDE_STATE = 3;
				supportInvalidateOptionsMenu();
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}
			
			@Override
			public void onPageScrollStateChanged(int state) {
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.listmenu, menu);
		
		if (HIDE_STATE == 0) {
			menu.getItem(1).setVisible(false);
			menu.getItem(2).setVisible(false);
			menu.getItem(3).setVisible(false);
		} else if (HIDE_STATE == 1) {
			menu.getItem(1).setVisible(false);
			menu.getItem(3).setVisible(false);
		} else if (HIDE_STATE == 2)	{
		} else if (HIDE_STATE == 3)	{
			menu.getItem(1).setVisible(false);
			menu.getItem(2).setVisible(false);
			menu.getItem(3).setVisible(false);
		}
		
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.listmenuupdate) {
			outputadapter.notifyDataSetChanged();
		} else if (item.getItemId() == R.id.listmenuveteran) {
			
		} else if (item.getItemId() == R.id.listmenu150le) {
			
		} else if (item.getItemId() == R.id.listmenuwomen) {
			
		}
		return super.onOptionsItemSelected(item);
	}
}
