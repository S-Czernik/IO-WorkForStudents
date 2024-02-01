package model.calendar;

import java.util.ArrayList;
import java.util.List;

public class Interval {

	int begin;
	int end;
	int day;

	Interval(int bg, int nd, int d) {
		begin = bg;
		end = nd;
		day = d;
	}

	Interval(Interval cpy) {
		begin = cpy.begin;
		end = cpy.end;
		day = cpy.day;
	}

	public boolean ok() {
		return day >= 0 && day < 7 && begin >= 0 && begin < end && end < 60 * 24;
	}

	public int getBeginHour() {
		return begin / 60;
	}

	public int getEndHour() {
		return end / 60;
	}

	public int getBeginMinute() {
		return begin - 60 * getBeginHour();
	}

	public int getEndMinute() {
		return end - 60 * getEndHour();
	}

	public int getLength() {
		return end - begin;
	}

	public boolean sum(Interval cmp) {
		if (day == cmp.day && begin <= cmp.end && end >= cmp.begin) {
			begin = Integer.min(begin, cmp.begin);
			end = Integer.max(end, cmp.end);
			return true;
		}
		return false;
	}

	public int sub(List<Interval> rems) {
		List<Interval> cur = new ArrayList<>();
		List<Interval> next = new ArrayList<>();

		cur.add(this);

		for (var i : rems) {
			for (var c : cur) {
				if (c.day == i.day) {
					if (i.end < c.begin || i.begin > c.end) {
						next.add(c);
					}
					else {
						if (i.end < c.end) {
							next.add(new Interval(i.end + 1, c.end, c.day));
						}
						if (i.begin > c.begin) {
							next.add(new Interval(c.begin, i.begin - 1, c.day));
						}
					}
				}
			}
			cur = next;
			next.clear();
		}

		int totalArea = 0;
		for (var i : cur) {
			totalArea += i.getLength();
		}
		return totalArea;
	}
}
