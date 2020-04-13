package algorithm;

public class TimeInterval {
	public Timer start, end;
	
	public TimeInterval(Timer st, Timer en) {
		start = st;
		end = en;
	}
	
	public TimeInterval(java.sql.Time sta, java.sql.Time en) {
		// TODO Auto-generated constructor stub
	}
	
	public TimeInterval() {}

	public static boolean isIntersecting(TimeInterval a, TimeInterval b) {
		if(a.start.day != b.start.day) { //on different days
			return false;
		}
		
		
		int astart = a.start.convertMin();
		int aend = a.end.convertMin();
		int bstart = b.start.convertMin();
		int bend = b.end.convertMin();
		
		if(bstart <= astart && astart <= bstart) {
			return true;
		}else if(astart <= bstart && bstart <= aend) {
			return true;
		}else {
			return false;
		}
		
	}
	
	public int getDay() {
		return start.day;
	}
	
	public String toString() {
		return start.toString() + " :: " + end.toString();
	}
}