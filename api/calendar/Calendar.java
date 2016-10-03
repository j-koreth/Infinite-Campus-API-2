/* Copyright (c) 2014-2016, Nicolas Nytko, Liam Fruzyna
 All rights reserved.
 ...

 Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

 Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer
 in the documentation and/or other materials provided with the distribution.

 THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS “AS IS” AND ANY EXPRESS OR IMPLIED WARRANTIES,
 INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING
 IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

 The views and conclusions contained in the software and documentation are those of the authors
 and should not be interpreted as representing official policies, either expressed or implied, of the FreeBSD Project.
*/

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
