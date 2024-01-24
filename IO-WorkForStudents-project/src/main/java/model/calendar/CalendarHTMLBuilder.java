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

		String ret = "<!DOCTYPE html>\n"
				+ "<html>\n"
				+ "<head>\n"
				+ "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-2\">  \n"
				+ "<title>kek</title>  \n"
				+ "<link rel=\"stylesheet\" type=\"text/css\" href=\"css/kalendarz.css\">\n"
				+ "<script type=\"text/javascript\" language=\"JavaScript\" src=\"js/js.js\"></script>\n"
				+ "</head>\n"
				+ "<body>\n"
				+ "<div id=\"calendar\" style=\"position: relative;\">";

		int minHour = minTime / 60;
		int maxHour = (maxTime + 59) / 60;
		int hourCount = maxHour - minHour + 1;
		float hourHeight = 99.9f / (hourCount + 1);

		ret += "<div class=\"frame\" style=\"top: 0%; left: 0%; width: 10%; height: " + hourHeight + "%;\">Day</div>\n"
				+ "<div class=\"frame\" style=\"top: 0%; left: 10%; width: 11%; height: " + hourHeight + "%;\">Monday</div>\n"
				+ "<div class=\"frame\" style=\"top: 0%; left: 21%; width: 11%; height: " + hourHeight + "%;\">Tuesday</div>\n"
				+ "<div class=\"frame\" style=\"top: 0%; left: 32%; width: 11%; height: " + hourHeight + "%;\">Wednesday</div>\n"
				+ "<div class=\"frame\" style=\"top: 0%; left: 43%; width: 11%; height: " + hourHeight + "%;\">Thursday</div>\n"
				+ "<div class=\"frame\" style=\"top: 0%; left: 54%; width: 11%; height: " + hourHeight + "%;\">Friday</div>\n"
				+ "<div class=\"frame\" style=\"top: 0%; left: 65%; width: 11%; height: " + hourHeight + "%;\">Saturday</div>\n"
				+ "<div class=\"frame\" style=\"top: 0%; left: 76%; width: 11%; height: " + hourHeight + "%;\">Sunday</div>\n"
				+ "<div class=\"frame\" style=\"top: 0%; left: 87%; width: 10%; height: " + hourHeight + "%;\">Day</div>\n";

		float verticalPos = hourHeight;
		for (int hour = minHour; hour <= maxHour; hour++) {
			// <editor-fold desc="Sidebar hour display text">
			String hourText = "";
			if (hour < 10) {
				hourText += "0" + hour;
			}
			else {
				hourText += hour;
			}
			hourText += ":00-";
			if (hour < 9) {
				hourText += "0" + (hour + 1);
			}
			else {
				hourText += (hour + 1);
			}
			hourText += ":00";
			// </editor-fold>

			ret += "<div class=\"frame\" style=\"top: " + verticalPos + "%; left: 0%; width: 10%; height: " + hourHeight + "%;\">" + hourText + "</div>\n";

			for (int day = 0; day < 7; day++) {
				float horizontalPos = 10 + 11 * day;
				ret += "<div class=\"block\" style=\"top: " + verticalPos + "%; left: " + horizontalPos + "%; width: 11%; height: " + hourHeight + "%;\"></div>\n";
			}

			ret += "<div class=\"frame\" style=\"top: " + verticalPos + "%; left: 87%; width: 10%; height: " + hourHeight + "%;\">" + hourText + "</div>\n";

			verticalPos += hourHeight;
		}

		for (var entry : k.intervals) {
			verticalPos = ((entry.getBeginHour() - minHour) + entry.getBeginMinute() / 60.0f + 1.0f) * hourHeight;
			float height = ((entry.getEndHour() - minHour) + entry.getEndMinute() / 60.0f + 1.0f) * hourHeight - verticalPos;

			float horizontalPos = 10 + 11 * entry.day;

			ret += "<div class=\"fill\" style=\"width: 11%; height: " + height + "%; top: " + verticalPos + "%; left: " + horizontalPos + "%;\"></div>\n";
		}
		ret += "</div>"
				+ "</body>\n"
				+ "</html>";
		return ret;
	}
}
