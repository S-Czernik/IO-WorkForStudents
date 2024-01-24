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

	public int getBeginHour() {
		return begin / 60;
	}

	public int getEndHour() {
		return (end + 59) / 60;
	}

	public int getBeginMinute() {
		return begin - 60 * getBeginHour();
	}

	public int getEndMinute() {
		return end - 60 * getEndHour();
	}
}
