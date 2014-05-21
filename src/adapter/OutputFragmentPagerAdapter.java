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
	}
	
	public void refresh() {
		
	}

	@Override
	public Fragment getItem(int pos) {
		switch(pos) {
		case 0:
			return racer = new RacerFragment();
		case 1:
			return trailer = new TrailerFragment();
		case 2:
			return slalom = new SlalomFragment();
		case 3:
			return drag = new DragFragment();
		default:
			return null;
		}
	}
	
	@Override
	public int getItemPosition(Object object) {
		//return super.getItemPosition(object);
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
