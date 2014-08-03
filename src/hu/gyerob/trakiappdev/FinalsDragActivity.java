package hu.gyerob.trakiappdev;

import adapter.DragTop10FragmentPagerAdapter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class FinalsDragActivity extends ActionBarActivity {

	private ViewPager pager;
	private DragTop10FragmentPagerAdapter adapter;
	private PagerTabStrip strip;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.finals_drag);

		pager = (ViewPager) findViewById(R.id.dragViewPager);
		strip = (PagerTabStrip) findViewById(R.id.dragPagerTabStrip);

		adapter = new DragTop10FragmentPagerAdapter(getSupportFragmentManager());
		pager.setAdapter(adapter);

		strip.setTextColor(Color.BLACK);
		strip.setTabIndicatorColor(Color.BLACK);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.finalsdragmenu, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.finalsrefresh) {
			adapter.notifyDataSetChanged();
		}

		return super.onOptionsItemSelected(item);
	}
}
