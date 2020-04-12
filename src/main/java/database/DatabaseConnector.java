package database;

import java.sql.*;
import algorithm.*;
import algorithm.Time;
import java.util.*;

/*
 * public class Section {

	public String sectionID;
	public ArrayList<TimeInterval> timing;
	
	int currentRegistered, maxRegistered;
	
	public String unit; //reference to unit
	public String type;
	public String instructor, location;


}
 */
public class DatabaseConnector {
	
	static Connection connection = null;
	
	//[PRIMARY FUNCTION: TO CONNECT TO DATABASE]
	public Connection connectToDatabase() {
		try {
			connection = DriverManager.getConnection("jdbc:mysql://us-cdbr-iron-east-01.cleardb.net:3306/heroku_15f10f75c7431e6?user=b5a203584b9d69&password=205de108&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC"); // URI - Uniform Resource Identifier
			return connection;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//[PRIMARY FUNCTION: SET SCHEDULE IN MYSQL DATABASE]
	public void setSchedule(Schedule schedule) {
		if (connection == null) { connectToDatabase(); }
		
	}
	
	@SuppressWarnings("deprecation")
	public Schedule retrieveSchedule(int scheduleID) {
		//[SET-UP]
		PreparedStatement ps = null;
		ResultSet rs = null;
		Schedule schedule = new Schedule();
		if (connection == null) { connectToDatabase(); }
		ArrayList<Section> sections = new ArrayList<Section>();
		
		try {
			
			ps = connection.prepareStatement("SELECT * FROM schedule_section_link WHERE scheduleID=?");
			ps.setInt(1, scheduleID);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				String secID = rs.getString("sectionID");
				PreparedStatement innerst = connection.prepareStatement("SELECT * FROM sections where sectionID = ?");
				ps.setString(1, secID);
				ResultSet innerrs = innerst.executeQuery();
				
				//[WILL ONLY EXECUTE ONE TIME]
				while (innerrs.next()) {
					Section section = new Section();
					
					//[SETTING DATA MEMBERS]
					section.sectionID = secID;
					section.currentRegistered = innerrs.getInt("current_registered");
					section.maxRegistered = innerrs.getInt("capacity");
					
					//[TEMP FIX]
					section.classname = null;
					//[TEMP FIX]
			
					section.type = innerrs.getString("type");
					section.instructor = innerrs.getString("instructor");
					section.location = innerrs.getString("location");
					
					//[CREATING TIME INTERVALS]
					ArrayList<TimeInterval> timings = new ArrayList<TimeInterval>();
					String alldays = innerrs.getString("days");
					
					java.sql.Time start_time = innerrs.getTime("startTime");
					java.sql.Time end_time = innerrs.getTime("endTime");
					
					for (int i = 0; i < alldays.length(); i++) {
						TimeInterval newTimeInterval = new TimeInterval();
						
						Time startTime = new Time();
						Time endTime = new Time();
						
						startTime.hour = start_time.getHours();
						startTime.min = start_time.getMinutes();
						endTime.hour = end_time.getHours();
						endTime.min = end_time.getMinutes();
						newTimeInterval.start = startTime;
						newTimeInterval.end = endTime;
						timings.add(newTimeInterval);
					}				
					section.timing = timings;
					sections.add(section);
				}
				
			}
		} catch (Exception e) { e.printStackTrace(); }
		schedule.sections = sections;
		schedule.scheduleID = scheduleID;
		return schedule;
	}
		
	public void deleteSchedule() {
		
	}
}
