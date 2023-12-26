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

	public Kalyndarz(int... values) throws Exception {
		intervals = new ArrayList<>();

		if (values.length % 3 != 0) {
			throw new Exception("Invalid Kalyndarz constructor input");
		}

		for (int i = 0; i < values.length; i += 3) {
			var interval = new Interval(values[i], values[i + 1], values[i + 2]);
			if (interval.ok()) {
				intervals.add(interval);
			}
		}
	}

	List<Interval> intervals;

	float compare(final Kalyndarz cmp) {
		return 0.0f;
	}
}
