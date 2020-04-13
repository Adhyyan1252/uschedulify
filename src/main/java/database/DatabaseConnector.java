package database;

import java.sql.*;
import algorithm.*;
import algorithm.Timer;
import java.util.*;

public class DatabaseConnector {
	
	static Connection connection = null;
	
	//[PRIMARY FUNCTION: TO CONNECT TO DATABASE]
	public static Connection connectToDatabase() {
		try {
			connection = DriverManager.getConnection("jdbc:mysql://us-cdbr-iron-east-01.cleardb.net:3306/heroku_15f10f75c7431e6?user=b5a203584b9d69&password=205de108&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC"); // URI - Uniform Resource Identifier
			return connection;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void pushSection(Section section) {
		if (connection == null) { connectToDatabase(); }

		PreparedStatement toinsert = null;
		try {
			toinsert = connection.prepareStatement("REPLACE INTO sections (sectionID, majorName, className,  type, startTime, endTime, days, current_registered, capacity, instructor, location) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			toinsert.setString(1, section.sectionID);
			toinsert.setString(2, section.majorname);
			toinsert.setString(3, section.classname);
			toinsert.setString(4, section.type);
			//[WARNING: TIME DATA ENTRY]
			toinsert.setString(5, section.timing.get(0).start.toString());
			toinsert.setString(6, section.timing.get(0).end.toString());
			
			String alldays = "";
			for (int j = 0; j < section.timing.size(); j++) {
				alldays += section.timing.get(j).start.day;
			}
			toinsert.setString(7, alldays);
			toinsert.setInt(8, section.currentRegistered);
			toinsert.setInt(9, section.maxRegistered);
			toinsert.setString(10, section.instructor);
			toinsert.setString(11, section.location);	
			toinsert.executeUpdate();
		} catch (Exception e) { e.printStackTrace(); }	
	}
	
	//[PRIMARY FUNCTION: SET SCHEDULE IN MYSQL DATABASE]
	public static void setSchedule(Schedule schedule) {
		//[SETUP & ENSURING CONNECTION EXISTS]
		if (connection == null) { connectToDatabase(); }
		
		try {
			//[UPDATING SECTIONS]
			for (int i = 0; i < schedule.sections.size(); i++) {
				pushSection(schedule.sections.get(i));
			}
			
			//[ADD TO SCHEDULE]
			PreparedStatement ps = null;
			ps = connection.prepareStatement("INSERT INTO schedules (userID, dateCreated, schedule_name) VALUES (?, NULL, ?)",  Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, 11);
			ps.setString(2, "ScheduleName");
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			int scheduleID;
			if(rs.next()) { scheduleID = rs.getInt(1); } 
			else { throw new Exception("NO SCHEDULE ID RETURNED"); }
			System.out.println(scheduleID);
			
			//[ADDING TO LINKER TABLE]
			for (int i = 0; i < schedule.sections.size(); i++) {
				PreparedStatement preps = connection.prepareStatement("INSERT INTO schedule_section_link (scheduleID, sectionID) VALUES (?,?)");
				preps.setInt(1, scheduleID);
				preps.setString(2, schedule.sections.get(i).sectionID);
				preps.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace(); 
		}
	}
	
	//[PRIMARY FUNCTION: RETURN NEW JAVA SCHEDULE OBJECT]
	public static Schedule retrieveSchedule(int scheduleID, int userID) {
		//[SET-UP]
		PreparedStatement ps = null;
		ResultSet rs = null;
		Schedule schedule = new Schedule();
		ArrayList<Section> sections = new ArrayList<Section>();
		
		//[MAKE SURE NO UNNECCESARY COMMITS]
		if (connection == null) { connectToDatabase(); }
		
		try {	
			ps = connection.prepareStatement("SELECT * FROM schedule_section_link WHERE scheduleID=?");
			ps.setInt(1, scheduleID);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				String secID = rs.getString("sectionID");
				PreparedStatement innerst = connection.prepareStatement("SELECT * FROM sections where sectionID = ?");
				innerst.setString(1, secID);
				ResultSet innerrs = innerst.executeQuery();
				
				//[WILL ONLY EXECUTE ONE TIME]
				while (innerrs.next()) {
					Section section = new Section();
					
					//[SETTING THE DATA MEMBERS OF THE OBJECT TO BE RETURNED]
					section.sectionID = secID;
					section.currentRegistered = innerrs.getInt("current_registered");
					section.maxRegistered = innerrs.getInt("capacity");
					section.classname = innerrs.getString("className");
					section.majorname = innerrs.getString("majorName");
					section.type = innerrs.getString("type");
					section.instructor = innerrs.getString("instructor");
					section.location = innerrs.getString("location");
					
					//[TIME MANIPULATION: UPDATE NEEDED]
					ArrayList<TimeInterval> timings = new ArrayList<TimeInterval>();
					String alldays = innerrs.getString("days");
					
					java.sql.Time start_time = innerrs.getTime("startTime");
					java.sql.Time end_time = innerrs.getTime("endTime");
					
					for (int i = 0; i < alldays.length(); i++) {
						TimeInterval newTimeInterval = new TimeInterval();
					
						Timer startTime = new Timer();
						Timer endTime = new Timer();
	
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
		schedule.userID = userID;
		return schedule;
	}
		
}
