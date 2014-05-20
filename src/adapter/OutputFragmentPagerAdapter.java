package adapter;

import outputfragments.DragFragment;
import outputfragments.RacerFragment;
import outputfragments.SlalomFragment;
import outputfragments.TrailerFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class OutputFragmentPagerAdapter extends FragmentStatePagerAdapter {

	RacerFragment racer;
	TrailerFragment trailer;
	SlalomFragment slalom;
	DragFragment drag;
	
	FragmentManager fm;
	
	public OutputFragmentPagerAdapter(FragmentManager fm) {
		super(fm);
		this.fm = fm;
		racer = new RacerFragment();
		trailer = new TrailerFragment();
		slalom = new SlalomFragment();
		drag = new DragFragment();		
	}
	
	public void refresh() {
		
	}

	@Override
	public Fragment getItem(int pos) {
		switch(pos) {
		case 0:
			return racer;
		case 1:
			return trailer;
		case 2:
			return slalom;
		case 3:
			return drag;
		default:
			return null;
		}
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
