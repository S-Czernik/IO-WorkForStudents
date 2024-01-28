package model.calendar;

public class CalendarHTMLBuilder {

	public static String get(Kalyndarz k) {

		int minTime = 1339, maxTime = 0;
		for (Interval i : k.intervals) {
			if (minTime > i.begin) {
				minTime = i.begin;
			}
			if (maxTime < i.end) {
				maxTime = i.end;
			}
		}

		String ret = "<div id=\"calendar\" style=\"position: relative;\">";

		int mexTimeDelta = maxTime - minTime;

		float d;
		if (mexTimeDelta > 1080) {
			d = 0.5f;
		}
		else if (mexTimeDelta > 540) {
			d = 1.0f;
		}
		else if (mexTimeDelta > 360) {
			d = 2.0f;
		}
		else if (mexTimeDelta > 180) {
			d = 3.0f;
		}
		else {
			d = 6.0f;
		}
		
		int timeStep = (int) (60 / d);

		int minStep = Integer.max(minTime - (minTime % timeStep) - timeStep, 0);
		int maxStep = Integer.min(maxTime - (minTime % timeStep) + 2 * timeStep, 1440);
		
		int timeCount = (maxStep - minStep) / timeStep;

		float timeHeight = 99.9f / (timeCount + 1);

		ret += "<div class=\"frame\" style=\"top: 0%; left: 0%; width: 10%; height: " + timeHeight + "%;\">Day</div>\n"
				+ "<div class=\"frame\" style=\"top: 0%; left: 10%; width: 11%; height: " + timeHeight + "%;\">Monday</div>\n"
				+ "<div class=\"frame\" style=\"top: 0%; left: 21%; width: 11%; height: " + timeHeight + "%;\">Tuesday</div>\n"
				+ "<div class=\"frame\" style=\"top: 0%; left: 32%; width: 11%; height: " + timeHeight + "%;\">Wednesday</div>\n"
				+ "<div class=\"frame\" style=\"top: 0%; left: 43%; width: 11%; height: " + timeHeight + "%;\">Thursday</div>\n"
				+ "<div class=\"frame\" style=\"top: 0%; left: 54%; width: 11%; height: " + timeHeight + "%;\">Friday</div>\n"
				+ "<div class=\"frame\" style=\"top: 0%; left: 65%; width: 11%; height: " + timeHeight + "%;\">Saturday</div>\n"
				+ "<div class=\"frame\" style=\"top: 0%; left: 76%; width: 11%; height: " + timeHeight + "%;\">Sunday</div>\n"
				+ "<div class=\"frame\" style=\"top: 0%; left: 87%; width: 10%; height: " + timeHeight + "%;\">Day</div>\n";

		float verticalPos = timeHeight;
		for (int time = minStep; time < maxStep; time += timeStep) {
			String hourText = formatTime(time, time + timeStep);
			ret += "<div class=\"frame\" style=\"top: " + verticalPos + "%; left: 0%; width: 10%; height: " + timeHeight + "%;\">" + hourText + "</div>\n";

			for (int day = 0; day < 7; day++) {
				float horizontalPos = 10 + 11 * day;
				ret += "<div class=\"block\" style=\"top: " + verticalPos + "%; left: " + horizontalPos + "%; width: 11%; height: " + timeHeight + "%;\"></div>\n";
			}

			ret += "<div class=\"frame\" style=\"top: " + verticalPos + "%; left: 87%; width: 10%; height: " + timeHeight + "%;\">" + hourText + "</div>\n";

			verticalPos += timeHeight;
		}

		for (var entry : k.intervals) {
			verticalPos = timeHeight + (entry.getBeginHour() * 60.0f + entry.getBeginMinute() - minStep) / timeStep * timeHeight;
			float height = (float) (entry.getLength()) / (float) (timeStep) * timeHeight;

			float horizontalPos = 10 + 11 * entry.day;

			ret += "<div class=\"fill\" style=\"width: 11%; height: " + height + "%; top: " + verticalPos + "%; left: " + horizontalPos + "%;\"></div>\n";
		}
		ret += "</div>";
		return ret;
	}

	static String formatTime(int timeBegin, int timeEnd) {
		String hourBegin = format2(timeBegin / 60);
		String hourEnd = format2(timeEnd / 60);
		String minuteBegin = format2(timeBegin % 60);
		String minuteEnd = format2(timeEnd % 60);

		return hourBegin + ":" + minuteBegin + "-" + hourEnd + ":" + minuteEnd;
	}

	static String format2(int val) {
		if (val < 10) {
			return "0" + val;
		}
		else {
			return "" + val;
		}
	}
}
