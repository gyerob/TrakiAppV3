package adapter;

import hu.gyerob.trakiappdev.R;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import data.Racer;

public class RacerAdapter extends BaseAdapter {
	ArrayList<Racer> racers;

	public RacerAdapter(final ArrayList<Racer> Racers) {
		super();
		this.racers = Racers;
	}

	@Override
	public int getCount() {
		return racers.size();
	}

	@Override
	public Racer getItem(int position) {
		return racers.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	static public class ViewHolder {
		TextView name;
		TextView number;
		TextView town;
		TextView point;
		CheckBox trailer;
		CheckBox slalom;
		CheckBox drag;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Racer racer = getItem(position);
		ViewHolder holder = null;
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) parent.getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.row_racer, null);
			holder = new ViewHolder();

			holder.name = (TextView) convertView
					.findViewById(R.id.row_racerName);
			holder.number = (TextView) convertView
					.findViewById(R.id.row_racerNumber);
			holder.town = (TextView) convertView
					.findViewById(R.id.row_racerTown);
			holder.point = (TextView) convertView
					.findViewById(R.id.row_racerPoints);
			holder.trailer = (CheckBox) convertView
					.findViewById(R.id.row_racerTrailer);
			holder.slalom = (CheckBox) convertView
					.findViewById(R.id.row_racerSlalom);
			holder.drag = (CheckBox) convertView
					.findViewById(R.id.row_racerDrag);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.name.setTextColor(Color.BLACK);
		holder.number.setTextColor(Color.BLACK);
		holder.town.setTextColor(Color.BLACK);
		holder.point.setTextColor(Color.BLACK);

		holder.name.setText(racer.getName());
		holder.number.setText(Integer.toString(racer.getNumber()));
		holder.town.setText(racer.getTown());
		holder.point.setText(racer.getPoint());
		holder.trailer.setChecked(racer.getTrailer());
		holder.slalom.setChecked(racer.getSlalom());
		holder.drag.setChecked(racer.getDrag());

		return convertView;
	}
}
