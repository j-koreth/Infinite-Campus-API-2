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

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import nu.xom.Element;

public class ScheduleStructure
{
	public String id;
	private String name;
	private String label;
	private String grade;
	private boolean active;
	private String primary;
	private boolean is_default;
	private Date startDate;

	public ScheduleStructure(JSONObject jsonSchedule) throws JSONException
	{
		name = jsonSchedule.getString("name");

		active = jsonSchedule.getBoolean("active");
		grade = jsonSchedule.getString("grade");
		id = jsonSchedule.getString("id");
		is_default = jsonSchedule.getBoolean("default");
		label = jsonSchedule.getString("label");
		primary = jsonSchedule.getString("primary");
		try
		{
			startDate = new SimpleDateFormat("MM/dd/yy", Locale.ENGLISH).parse(jsonSchedule.getString("startDate"));
		} catch (ParseException e)
		{
			e.printStackTrace();
		}
	}

	public ScheduleStructure(Element scheduleElement)
	{
		id = scheduleElement.getAttributeValue("structureID");
		name = scheduleElement.getAttributeValue("structureName");
		label = scheduleElement.getAttributeValue("label");
		grade = scheduleElement.getAttributeValue("grade");
		active = scheduleElement.getAttributeValue("active").equalsIgnoreCase("true");
		primary = scheduleElement.getAttributeValue("primary");
        if(scheduleElement.getAttributeValue("default") != null)
        {
            is_default = scheduleElement.getAttributeValue("default").equalsIgnoreCase("true");
        }
        else
        {
            is_default = false;
        }

		try
		{
			startDate = new SimpleDateFormat("MM/dd/yy", Locale.ENGLISH).parse(scheduleElement.getAttributeValue("startDate"));
		}
		catch(Exception e)
		{
			startDate = new Date();
		}
	}

	public ScheduleStructure(String name)
	{
		this.name = name;
	}
	
	public String getInfoString()
	{
		return "Information for Schedule \'" + name  + "\' titled \'" + label + "\':\nGrade: " + grade + "\nID: " + id + "\nIs Active? " + active + "\nPrimary: " + primary + "\nIs Default? " + is_default + "\nEnding Date: " + startDate.toString();
	}

	public String getId()
	{
		return id;
	}
	public String getName()
	{
		return name;
	}
	public String getLabel()
	{
		return label;
	}
	public String getGrade()
	{
		return grade;
	}
	public String getPrimary()
	{
		return primary;
	}
	public Date getStartDate()
	{
		return startDate;
	}
	public boolean isActive()
	{
		return active;
	}
	public boolean isDefault()
	{
		return is_default;
	}
}
