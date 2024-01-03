package model;

import java.util.ArrayList;
import java.util.List;

public class Kalyndarz {

	class Interval {

		int begin;
		int end;
		int day;

		Interval(int bg, int nd, int d) {
			begin = bg;
			end = nd;
			day = d;
		}

		private boolean ok() {
			return day >= 0 && day < 7 && begin >= 0 && begin < end && end < 60 * 24;
		}
	}

	List<Interval> intervals;

	public Kalyndarz() {
		intervals = new ArrayList<>();
	}

	public void loadCSV(String csv) {
		var values = csv.split(",");

		for (int i = 0; i + 2 < values.length; i += 3) {
			var bg = Integer.valueOf(values[i]);
			var nd = Integer.valueOf(values[i + 1]);
			var d = Integer.valueOf(values[i + 2]);
			var interval = new Interval(bg, nd, d);
			if (interval.ok()) {
				intervals.add(interval);
			}
		}
	}

	public String getCSV() {
		String ret = "";
		for (var i : intervals) {
			ret += i.begin + "," + i.end + "," + i.day + ",";
		}
		return ret.substring(0, ret.length() - 1);
	}

	float compare(final Kalyndarz cmp) {
		return 0.0f;
	}
}
