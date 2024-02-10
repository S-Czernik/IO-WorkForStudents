package servlets.helper;

public class Helper {

	public static int getIntValueOf(String v) {
		return (v != null ? Integer.parseInt(v) : -1);
	}
}
