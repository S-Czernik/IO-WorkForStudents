package model.calendar;

import java.util.ArrayList;
import java.util.List;

public class Calendar {

	List<Interval> intervals;

	public Calendar() {
		intervals = new ArrayList<>();
	}

	public void loadCSV(String csv) {
		var values = csv.split(",");

		for (int i = 0; i + 2 < values.length; i += 3) {
			var bg = Integer.valueOf(values[i]);
			var nd = Integer.valueOf(values[i + 1]);
			var d = CalendarInterface.getIdxFromDay(values[i + 2]);
			var interval = new Interval(bg, nd, d);
			if (interval.ok()) {
				boolean doesntIntersect = true;
				for (var j : intervals) {
					if (j.sum(interval)) {
						doesntIntersect = false;
						break;
					}
				}
				if (doesntIntersect) {
					intervals.add(interval);
				}
			}
		}
	}

	public String getCSV() {
		String ret = "";
		for (var i : intervals) {
			ret += i.begin + "," + i.end + "," + CalendarInterface.getDayFromIdx(i.day) + ",";
		}
		if (ret.isEmpty()) {
			return "";
		}
		else {
			return ret.substring(0, ret.length() - 1);
		}
	}

	float compare(Calendar cmp) {
		int totalArea = 0;
		for (var i : intervals) {
			totalArea += i.getLength();
		}

		int subArea = 0;
		for (var i : intervals) {
			subArea += i.sub(cmp.intervals);
		}

		return 1.0f - ((float) (totalArea) / (float) (subArea));
	}
}
