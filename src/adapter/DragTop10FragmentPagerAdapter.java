package adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import finalsdragfragments.DragTop10Fragment1;
import finalsdragfragments.DragTop10Fragment2;

public class DragTop10FragmentPagerAdapter extends FragmentStatePagerAdapter {

	public DragTop10FragmentPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int pos) {
		switch (pos) {
		case 0:
			return DragTop10Fragment1.newInstance();
		case 1:
			return DragTop10Fragment2.newInstance();
		default:
			return null;
		}
	}

	@Override
	public int getItemPosition(Object object) {
		return POSITION_NONE;
	}

	@Override
	public CharSequence getPageTitle(int pos) {
		switch (pos) {
		case 0:
			return DragTop10Fragment1.TITLE;
		case 1:
			return DragTop10Fragment2.TITLE;
		default:
			return null;
		}
	}

	@Override
	public int getCount() {
		return 2;
	}

}
