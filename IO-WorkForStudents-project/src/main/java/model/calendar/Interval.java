package model.calendar;

public class Interval {

	int begin;
	int end;
	int day;

	Interval(int bg, int nd, int d) {
		begin = bg;
		end = nd;
		day = d;
	}

	public boolean ok() {
		return day >= 0 && day < 7 && begin >= 0 && begin < end && end < 60 * 24;
	}
}
