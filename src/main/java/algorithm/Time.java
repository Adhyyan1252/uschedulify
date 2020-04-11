package algorithm;

public class Time {
	public int day;
	public int hour;
	public int min;
	
	int convertMin() {
		return min + 60*hour;
	}
	
	@Override
	public String toString() {
		return day + ", " + hour + ", " + min;
	}
	
	public String getDay() {
		String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
		return days[day];
	}
}
