package hu.gyerob.trakiappdev;

import java.lang.reflect.Field;

import adapter.OutputFragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
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
import android.view.ViewConfiguration;

public class OutputActivity extends ActionBarActivity {

	private ViewPager pager;
	private OutputFragmentPagerAdapter outputadapter;
	private ActionBar actionbar;
	private Tab racerTab;
	private Tab trailerTab;
	private Tab slalomTab;
	private Tab dragTab;

	private int HIDE_STATE;
	private int MODE;
	private int GROUP;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_output);

		// setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

		pager = (ViewPager) findViewById(R.id.outputViewPager);

		outputadapter = new OutputFragmentPagerAdapter(
				getSupportFragmentManager());
		pager.setAdapter(outputadapter);

		actionbar = getSupportActionBar();
		actionbar.setDisplayHomeAsUpEnabled(true);

		racerTab = actionbar.newTab().setText("Versenyzõk")
				.setTabListener(MyTablistener);
		trailerTab = actionbar.newTab().setText("Pótkocsis")
				.setTabListener(MyTablistener);
		slalomTab = actionbar.newTab().setText("Szlalom")
				.setTabListener(MyTablistener);
		dragTab = actionbar.newTab().setText("Gyorsulás")
				.setTabListener(MyTablistener);

		actionbar.addTab(racerTab);
		actionbar.addTab(trailerTab);
		actionbar.addTab(slalomTab);
		actionbar.addTab(dragTab);

		actionbar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		try {
			ViewConfiguration config = ViewConfiguration.get(this);
			Field menuKeyField = ViewConfiguration.class
					.getDeclaredField("sHasPermanentMenuKey");
			if (menuKeyField != null) {
				menuKeyField.setAccessible(true);
				menuKeyField.setBoolean(config, false);
			}
		} catch (Exception ex) {
			// Ignore
		}

		pager.setOnPageChangeListener(MyPageListener);
	}

	private OnPageChangeListener MyPageListener = new OnPageChangeListener() {
		@Override
		public void onPageSelected(int pos) {
			actionbar.setSelectedNavigationItem(pos);

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
	};

	private TabListener MyTablistener = new TabListener() {

		@Override
		public void onTabUnselected(Tab tab, FragmentTransaction ft) {

		}

		@Override
		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			pager.setCurrentItem(tab.getPosition());
		}

		@Override
		public void onTabReselected(Tab tab, FragmentTransaction ft) {

		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.listmenu, menu);

		if (HIDE_STATE == 0) {
			menu.getItem(4).setVisible(false);
			menu.getItem(5).setVisible(false);
			menu.getItem(6).setVisible(false);
			menu.getItem(7).setVisible(false);
			menu.getItem(8).setVisible(false);
			menu.getItem(9).setVisible(false);
			menu.getItem(10).setVisible(false);
		} else if (HIDE_STATE == 1) {
			menu.getItem(5).setVisible(false);
			menu.getItem(6).setVisible(false);
			menu.getItem(9).setVisible(false);
			menu.getItem(10).setVisible(false);
		} else if (HIDE_STATE == 2) {
		} else if (HIDE_STATE == 3) {
			menu.getItem(4).setVisible(false);
			menu.getItem(5).setVisible(false);
			menu.getItem(6).setVisible(false);
			menu.getItem(7).setVisible(false);
			menu.getItem(8).setVisible(false);
			menu.getItem(9).setVisible(false);
			menu.getItem(10).setVisible(false);
		}

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Log.d("actionbarkatt", "frissít");
		if (item.getItemId() == R.id.listmenuupdate) {
			outputadapter.notifyDataSetChanged();
			return true;
		} else if (item.getItemId() == R.id.listmenuall) {
			MODE = 1;
			outputadapter.setMode(MODE, GROUP);
			outputadapter.notifyDataSetChanged();
			return true;
		} else if (item.getItemId() == R.id.listmenuabove150le) {
			MODE = 2;
			outputadapter.setMode(MODE, GROUP);
			outputadapter.notifyDataSetChanged();
			return true;
		} else if (item.getItemId() == R.id.listmenubelow150le) {
			MODE = 3;
			outputadapter.setMode(MODE, GROUP);
			outputadapter.notifyDataSetChanged();
			return true;
		} else if (item.getItemId() == R.id.listmenuveteran) {
			MODE = 4;
			outputadapter.setMode(MODE, GROUP);
			outputadapter.notifyDataSetChanged();
			return true;
		} else if (item.getItemId() == R.id.listmenumodern) {
			MODE = 5;
			outputadapter.setMode(MODE, GROUP);
			outputadapter.notifyDataSetChanged();
			return true;
		} else if (item.getItemId() == R.id.listmenuwomen) {
			MODE = 6;
			outputadapter.setMode(MODE, GROUP);
			outputadapter.notifyDataSetChanged();
			return true;
		} else if (item.getItemId() == R.id.listmenumen) {
			MODE = 7;
			outputadapter.setMode(MODE, GROUP);
			outputadapter.notifyDataSetChanged();
			return true;
		} else if (item.getItemId() == R.id.groupa) {
			item.setChecked(true);
			GROUP = 0;
			outputadapter.setMode(MODE, GROUP);
			outputadapter.notifyDataSetChanged();
			return true;
		} else if (item.getItemId() == R.id.groupb) {
			item.setChecked(true);
			GROUP = 1;
			outputadapter.setMode(MODE, GROUP);
			outputadapter.notifyDataSetChanged();
			return true;
		} else if (item.getItemId() == R.id.groupc) {
			item.setChecked(true);
			GROUP = 2;
			outputadapter.setMode(MODE, GROUP);
			outputadapter.notifyDataSetChanged();
			return true;
		}

		supportInvalidateOptionsMenu();

		return super.onOptionsItemSelected(item);
	}
}
