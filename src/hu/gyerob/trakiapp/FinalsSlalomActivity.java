package hu.gyerob.trakiapp;

import adapter.SlalomTop10FragmentPagerAdapter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class FinalsSlalomActivity extends ActionBarActivity {

	private ViewPager pager;
	private SlalomTop10FragmentPagerAdapter adapter;
	private PagerTabStrip strip;

	private int MODE;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.finals_slalom);

		pager = (ViewPager) findViewById(R.id.slalomViewPager);
		strip = (PagerTabStrip) findViewById(R.id.slalomPagerTabStrip);

		adapter = new SlalomTop10FragmentPagerAdapter(
				getSupportFragmentManager());
		pager.setAdapter(adapter);

		strip.setTextColor(Color.BLACK);
		strip.setTabIndicatorColor(Color.BLACK);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.finalsslalommenu, menu);

		if (MODE == 0) {
			menu.getItem(1).setVisible(false);
		} else if (MODE == 1) {
			menu.getItem(2).setVisible(false);
		}

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.finalsrefresh) {
			adapter.notifyDataSetChanged();
		} else if (item.getItemId() == R.id.finalsabove) {
			MODE = 0;
			adapter.setMode(MODE);
			adapter.notifyDataSetChanged();
		} else if (item.getItemId() == R.id.finalsbelow) {
			MODE = 1;
			adapter.setMode(MODE);
			adapter.notifyDataSetChanged();
		}

		supportInvalidateOptionsMenu();

		return super.onOptionsItemSelected(item);
	}
}
