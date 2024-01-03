package model;

public class CalendarHTMLBuilder {

	public static String get(Kalyndarz k) {
		String ret = "<!DOCTYPE html>\n"
				+ "<html>\n"
				+ "<head>\n"
				+ "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-2\">  \n"
				+ "<title>kek</title>  \n"
				+ "<link rel=\"stylesheet\" type=\"text/css\" href=\"css/kalendarz.css\">\n"
				+ "<script type=\"text/javascript\" language=\"JavaScript\" src=\"js/js.js\"></script>\n"
				+ "</head>\n"
				+ "<body>\n"
				+ "<div class=\"frame\" style=\"top: 0px; left: 0%; width: 8%; height: 20px;\">Day</div>\n"
				+ "<div class=\"frame\" style=\"top: 0px; left: 8%; width: 12%; height: 20px;\">Monday</div>\n"
				+ "<div class=\"frame\" style=\"top: 0px; left: 20%; width: 12%; height: 20px;\">Tuesday</div>\n"
				+ "<div class=\"frame\" style=\"top: 0px; left: 32%; width: 12%; height: 20px;\">Wednesday</div>\n"
				+ "<div class=\"frame\" style=\"top: 0px; left: 44%; width: 12%; height: 20px;\">Thursday</div>\n"
				+ "<div class=\"frame\" style=\"top: 0px; left: 56%; width: 12%; height: 20px;\">Friday</div>\n"
				+ "<div class=\"frame\" style=\"top: 0px; left: 68%; width: 12%; height: 20px;\">Saturday</div>\n"
				+ "<div class=\"frame\" style=\"top: 0px; left: 80%; width: 12%; height: 20px;\">Sunday</div>"
				+ "<div class=\"frame\" style=\"top: 0px; left: 92%; width: 7.9%; height: 20px;\">Day</div>\n";

		for (int hour = 0; hour <= 23; hour++) {
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
			int topPosition = hour * 45 + 22;

			ret += "<div class=\"frame\" style=\"top: " + topPosition + "px; left: 0%; width: 8%; height: 43px; line-height: 43px;\">" + hourText + "</div>\n";

			for (int day = 0; day < 7; day++) {
				int leftPosiiton = 8 + 12 * day;

				for (int quarter = 0; quarter < 4; quarter++) {
					int curTopPosition = topPosition + quarter * 11 + 1;
					ret += "<div class=\"block\" style=\"top: " + curTopPosition + "px; left: " + leftPosiiton + "%; width: 12%; height: 10px;\"></div>\n";
				}
			}

			ret += "<div class=\"frame\" style=\"top: " + topPosition + "px; left: 92%; width: 7.9%; height: 43px; line-height: 43px;\">" + hourText + "</div>\n";
		}

		for (var entry : k.intervals) {
			int hourBegin = entry.begin / 60;
			int hourEnd = (entry.end + 59) / 60;

			int topPosition = hourBegin * 45 + 23 + (entry.begin - 60 * hourBegin) * 44 / 60;
			int height = hourEnd * 45 + 13 + (entry.end - 60 * hourEnd) / 15 * 11 - topPosition;

			int leftPosition = 8 + 12 * entry.day;

			ret += "<div class=\"fill\" style=\"width: 11.58%; height: " + height + "px; top: " + topPosition + "px; left: " + leftPosition + "%; background-color: #696969; z-index: 1;\"></div>\n";
		}
		ret += "</body>\n"
				+ "</html>";
		return ret;
	}
}
