package calendar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import nu.xom.Element;

public class Calendar
{
	private String name;
	private String schoolID;
	public String calendarID;
	private String endYear;
	
	public List<ScheduleStructure> schedules = new ArrayList<ScheduleStructure>();
	
	public Calendar(JSONObject jsonCalendar) throws JSONException
	{
		name = jsonCalendar.getString("name");

		calendarID = jsonCalendar.getString("id");
		endYear = jsonCalendar.getString("endYear");
		schoolID = jsonCalendar.getString("schoolId");

		JSONArray jsonSchedules = jsonCalendar.getJSONArray("schedules");
		schedules = new ArrayList<>();
		for(int j = 0; j < jsonSchedules.length(); j++)
		{
			schedules.add(new ScheduleStructure(jsonSchedules.getJSONObject(j)));
		}
	}

	public Calendar(Element calendar)
	{
		name = calendar.getAttributeValue("calendarName");
		schoolID = calendar.getAttributeValue("schoolID");
		calendarID = calendar.getAttributeValue("calendarID");
		endYear = calendar.getAttributeValue("endYear");
        System.out.println("Calendar info string: " + getInfoString());
		for(int i = 0; i < calendar.getChildElements().size(); i++)
			schedules.add(new ScheduleStructure(calendar.getChildElements().get(i)));
	}

	public Calendar(String name)
	{
		this.name = name;
	}
	
	public String getInfoString()
	{
		String returnString = "Information for Calendar \'" + name + "\':\nSchool ID: " + schoolID + "\nCalendar ID: " + calendarID + "\nEnding Year: " + endYear + "\n\n===Schedules===";
		
		for(ScheduleStructure s : schedules)
			returnString += "\n" + s.getInfoString();
		
		return returnString;
	}

	public String getName()
	{
		return name;
	}
	public String getSchoolID()
	{
		return schoolID;
	}
	public String getCalendarID()
	{
		return calendarID;
	}
	public String getEndYear()
	{
		return endYear;
	}
}
