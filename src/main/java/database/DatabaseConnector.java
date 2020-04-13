package database;

import java.sql.*;
import algorithm.*;
import algorithm.Timer;
import java.util.*;

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
		//[SETUP & ENSURING CONNECTION EXISTS]
		if (connection == null) { connectToDatabase(); }
		
		try {
			//[UPDATING SECTIONS]
			for (int i = 0; i < schedule.sections.size(); i++) {
				PreparedStatement prepst = connection.prepareStatement("SELECT * FROM sections where sectionID = ?");
				prepst.setString(1, schedule.sections.get(i).sectionID);
				ResultSet rs = prepst.executeQuery();
				if (!(rs.next())) {
					PreparedStatement toinsert = null;
					toinsert = connection.prepareStatement("INSERT INTO sections (sectionID, classID, type, startTime, endTime, days, current_registered, capacity, instructor, location) VALUES (?, NULL, ?, ?, ?, ?, ?, ?, ?, ?)");
					toinsert.setString(1, schedule.sections.get(i).sectionID);
					toinsert.setString(2, schedule.sections.get(i).type);
					toinsert.setString(3, schedule.sections.get(i).timing.get(0).start.toString());
					toinsert.setString(4, schedule.sections.get(i).timing.get(0).end.toString());

					String alldays = "";
					for (int j = 0; j < schedule.sections.get(i).timing.size(); j++) {
						alldays += schedule.sections.get(i).timing.get(j).start.day;
					}
					toinsert.setString(5, alldays);
					toinsert.setInt(6, schedule.sections.get(i).currentRegistered);
					toinsert.setInt(7, schedule.sections.get(i).maxRegistered);
					toinsert.setString(8, schedule.sections.get(i).instructor);
					toinsert.setString(9, schedule.sections.get(i).location);	
					toinsert.executeUpdate();
				} 
			}
			
			//[END UPDATING SECTIONS]
			PreparedStatement ps = null;
			ps = connection.prepareStatement("INSERT INTO schedules (scheduleID, userID, dateCreated, schedule_name) VALUES (?,?, NULL, ?)");
			ps.setInt(1, schedule.scheduleID);
			ps.setInt(2, schedule.userID);
			
			//[TEMP FIX]
			ps.setString(3, "");
			//[END TEMP FIX]
			ps.executeUpdate();
			//[NOW ADDING TO LINKER]
			for (int i = 0; i < schedule.sections.size(); i++) {
				
				PreparedStatement preps = connection.prepareStatement("INSERT INTO schedule_section_link (scheduleID, sectionID) VALUES (?,?)");
				ps.setInt(1, schedule.scheduleID);
				ps.setString(2, schedule.sections.get(i).sectionID);
				preps.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace(); 
		}
	}
	
	//[PRIMARY FUNCTION: RETURN NEW JAVA SCHEDULE OBJECT]
	@SuppressWarnings("deprecation")
	public Schedule retrieveSchedule(int scheduleID, int userID) {
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
				ps.setString(1, secID);
				ResultSet innerrs = innerst.executeQuery();
				
				//[WILL ONLY EXECUTE ONE TIME]
				while (innerrs.next()) {
					Section section = new Section();
					
					//[SETTING THE DATA MEMBERS OF THE OBJECT TO BE RETURNED]
					section.sectionID = secID;
					section.currentRegistered = innerrs.getInt("current_registered");
					section.maxRegistered = innerrs.getInt("capacity");
					
					//[TEMPORARY FIX]
					section.classname = null;
					//[END TEMPORARY FIX]
			
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
		schedule.scheduleID = scheduleID;
		schedule.userID = userID;
		return schedule;
	}
		
	public void deleteSchedule(int scheduleID) {
		
	}

}
