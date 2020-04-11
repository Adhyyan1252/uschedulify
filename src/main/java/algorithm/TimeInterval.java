package algorithm;

public class TimeInterval {
	public Time start, end;
	
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
