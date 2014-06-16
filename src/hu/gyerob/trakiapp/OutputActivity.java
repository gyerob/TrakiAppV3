package hu.gyerob.trakiapp;

import adapter.OutputFragmentPagerAdapter;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBar.TabListener;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class OutputActivity extends ActionBarActivity implements ActionBar.TabListener {

	private ViewPager pager;
	private PagerTabStrip strip;
	private OutputFragmentPagerAdapter outputadapter;
	private ActionBar actionbar;
	
	private int HIDE_STATE;
	private int MODE;
	
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
		
		actionbar = getSupportActionBar();

		actionbar.addTab(actionbar.newTab().setText("Elsõ").setTabListener(this));
		actionbar.addTab(actionbar.newTab().setText("Második").setTabListener(this));
		
		
		actionbar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		

		
		pager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int pos) {
				if (pos == 0)
					HIDE_STATE = 0;
				else if (pos == 1)
					HIDE_STATE = 1;
				else if (pos == 2)
					HIDE_STATE = 2;
				else if (pos == 3)
					HIDE_STATE = 3;
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
			menu.getItem(4).setVisible(false);
			menu.getItem(5).setVisible(false);
			menu.getItem(6).setVisible(false);
			menu.getItem(7).setVisible(false);
		} else if (HIDE_STATE == 1) {
			menu.getItem(2).setVisible(false);
			menu.getItem(3).setVisible(false);
			menu.getItem(6).setVisible(false);
			menu.getItem(7).setVisible(false);
			if (MODE == 4) 
				menu.getItem(4).setVisible(false);
			else
				menu.getItem(5).setVisible(false);
		} else if (HIDE_STATE == 2)	{
			//150le+ látszik
			if (MODE == 2) 
				menu.getItem(2).setVisible(false);
			else	//150le- látszik
				menu.getItem(3).setVisible(false);
			
			//modern látszik
			if (MODE == 4) 
				menu.getItem(4).setVisible(false);
			else	//veterán látszik
				menu.getItem(5).setVisible(false);
			
			//férfi látszik
			if (MODE == 7) 
				menu.getItem(7).setVisible(false);
			else	//nõi látszik
				menu.getItem(6).setVisible(false);
		} else if (HIDE_STATE == 3)	{
			menu.getItem(1).setVisible(false);
			menu.getItem(2).setVisible(false);
			menu.getItem(3).setVisible(false);
			menu.getItem(4).setVisible(false);
			menu.getItem(5).setVisible(false);
			menu.getItem(6).setVisible(false);
			menu.getItem(7).setVisible(false);
		}

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Log.d("actionbarkatt", "frissít");
		if (item.getItemId() == R.id.listmenuupdate) {
			outputadapter.notifyDataSetChanged();
		} else if (item.getItemId() == R.id.listmenuall) {
			MODE = 1;
			outputadapter.setMode(MODE);
			outputadapter.notifyDataSetChanged();
		} else if (item.getItemId() == R.id.listmenuabove150le) {
			MODE = 2;
			outputadapter.setMode(MODE);
			outputadapter.notifyDataSetChanged();
		} else if (item.getItemId() == R.id.listmenubelow150le) {
			MODE = 3;
			outputadapter.setMode(MODE);
			outputadapter.notifyDataSetChanged();
		} else if (item.getItemId() == R.id.listmenuveteran) {
			MODE = 4;
			outputadapter.setMode(MODE);
			outputadapter.notifyDataSetChanged();
		} else if (item.getItemId() == R.id.listmenumodern) {
			MODE = 5;
			outputadapter.setMode(MODE);
			outputadapter.notifyDataSetChanged();
		} else if (item.getItemId() == R.id.listmenuwomen) {
			MODE = 6;
			outputadapter.setMode(MODE);
			outputadapter.notifyDataSetChanged();
		} else if (item.getItemId() == R.id.listmenumen) {
			MODE = 7;
			outputadapter.setMode(MODE);
			outputadapter.notifyDataSetChanged();
		}
		
		supportInvalidateOptionsMenu();
		
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
		
	}

	@Override
	public void onTabSelected(Tab arg0, FragmentTransaction arg1) {
		
	}

	@Override
	public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
		
	}
}
