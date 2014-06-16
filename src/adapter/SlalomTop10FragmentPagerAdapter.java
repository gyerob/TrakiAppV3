package adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import finalsslalomfragments.SlalomAboveTop10Fragment1;
import finalsslalomfragments.SlalomAboveTop10Fragment2;
import finalsslalomfragments.SlalomBelowTop10Fragment1;
import finalsslalomfragments.SlalomBelowTop10Fragment2;

public class SlalomTop10FragmentPagerAdapter extends FragmentPagerAdapter {
	private int MODE = 0;

	public SlalomTop10FragmentPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	public void setMode(int mode) {
		MODE = mode;
	}

	@Override
	public Fragment getItem(int pos) {
		switch (pos) {
		case 0:
			if (MODE == 0)
				return SlalomAboveTop10Fragment1.newInstance();
			else
				return SlalomBelowTop10Fragment1.newInstance();
		case 1:
			if (MODE == 0)
				return SlalomAboveTop10Fragment2.newInstance();
			else
				return SlalomBelowTop10Fragment2.newInstance();
		default:
			return null;
		}
	}

	@Override
	public CharSequence getPageTitle(int pos) {
		switch (pos) {
		case 0:
			if (MODE == 0)
				return SlalomAboveTop10Fragment1.TITLE;
			else
				return SlalomBelowTop10Fragment1.TITLE;
		case 1:
			if (MODE == 1)
				return SlalomAboveTop10Fragment2.TITLE;
			else
				return SlalomBelowTop10Fragment2.TITLE;
		default:
			return null;
		}
	}

	@Override
	public int getItemPosition(Object object) {
		return POSITION_NONE;
	}

	@Override
	public int getCount() {
		return 2;
	}

}
