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
