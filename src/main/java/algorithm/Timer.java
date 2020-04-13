package algorithm;

public class Timer {
	public int day;
	public int hour;
	public int min;
	
	public Timer(int da, int hou, int mi) {
		day = da;
		hour = hou;
		min = mi;
	}
	
	int convertMin() {
		return min + 60*hour;
	}
	
	@Override
	public String toString() {
		if(hour == -1 && min == -1 && day == 7) {
			return "TBA";
		}
		else {
			return day + ", " + hour + ", " + min;
		}
	}
	
	public String getDay() {
		String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday", "TBA"};
		return days[day];
	}
	
	public void print() {
		System.out.println(day + " " + hour + " " + min);
	}
}
