package adapter;

import outputfragments.DragFragment;
import outputfragments.RacerFragment;
import outputfragments.SlalomFragment;
import outputfragments.TrailerFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class OutputFragmentPagerAdapter extends FragmentStatePagerAdapter {
	private int MODE;

	public OutputFragmentPagerAdapter(FragmentManager fm) {
		super(fm);
	}
	
	public void setMode(int mode) {
		MODE = mode;
	}

	@Override
	public Fragment getItem(int pos) {
		switch (pos) {
		case 0:
			return RacerFragment.newInstance(MODE);
		case 1:
			return TrailerFragment.newInstance(MODE);
		case 2:
			return SlalomFragment.newInstance(MODE);
		case 3:
			return DragFragment.newInstance(MODE);
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
			return RacerFragment.TITLE;
		case 1:
			return TrailerFragment.TITLE;
		case 2:
			return SlalomFragment.TITLE;
		case 3:
			return DragFragment.TITLE;
		default:
			return null;
		}
	}

	@Override
	public int getCount() {
		return 4;
	}

}
