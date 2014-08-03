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
	private int GROUP;

	public OutputFragmentPagerAdapter(FragmentManager fm) {
		super(fm);
	}
	
	public void setMode(int mode, int group) {
		MODE = mode;
		GROUP = group;
	}

	@Override
	public Fragment getItem(int pos) {
		switch (pos) {
		case 0:
			return RacerFragment.newInstance(MODE, GROUP);
		case 1:
			return TrailerFragment.newInstance(MODE, GROUP);
		case 2:
			return SlalomFragment.newInstance(MODE, GROUP);
		case 3:
			return DragFragment.newInstance(MODE, GROUP);
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
