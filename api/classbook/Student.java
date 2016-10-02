/*	Copyright 2016 Joel Koreth
	This file is part of Infinite Campus API 2.0.

	Infinite Campus API 2.0 is free software: you can redistribute it and/or modify
	it under the terms of the GNU Affero General Public License as published by
	the Free Software Foundation, either version 3 of the License, or
	(at your option) any later version.

	Infinite Campus API 2.0 is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
	GNU Affero General Public License for more details.

	You should have received a copy of the GNU General Affero Public License
	along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
*/

package classbook;

import calendar.Calendar;
import district.DistrictInfo;
import nu.xom.Element;

import java.util.ArrayList;

public class Student
{
	public String studentNumber;
	public boolean hasSecurityRole = false;
	public String personID;
	public String lastName;
	public String firstName;
	public String middleName;
	public String isGuardian;
	
	public ArrayList<Calendar> calendars = new ArrayList<Calendar>();
	public GradingDetailSummary gradeDetailSummary;
	public ArrayList<Classbook> classbooks = new ArrayList<Classbook>();
	
	private DistrictInfo distInfo;
	
	public Student(Element userElement)
	{
		this(userElement, null);
	}
	
	public Student(Element userElement, DistrictInfo info)
	{
		distInfo = info;
		
		studentNumber = userElement.getAttributeValue("studentNumber");
		personID = userElement.getAttributeValue("personID");
		lastName = userElement.getAttributeValue("lastName");
		firstName = userElement.getAttributeValue("firstName");
		middleName = userElement.getAttributeValue("middleName");
		isGuardian = userElement.getAttributeValue("isGuardian");
		for(int i = 0; i < userElement.getChildElements("Calendar").size(); i++)
			calendars.add(new Calendar(userElement.getChildElements("Calendar").get(i)));
		gradeDetailSummary = new GradingDetailSummary(userElement.getFirstChildElement("GradingDetailSummary"));
		for(int i = 0; i < userElement.getChildElements("Classbook").size(); i++) {
            try {
                classbooks.add(new Classbook(userElement.getChildElements("Classbook").get(i)));
            } catch (NullPointerException x) {}
        }
	}
	
	public String getPictureURL()
	{
		return distInfo.getDistrictBaseURL() + "personPicture.jsp?personID=" + personID;
	}
	
	//TODO: Load news items
	public String getInfoString()
	{
		String userInfo = "Information for " + firstName + " " + middleName + " " + lastName + ":\nStudent Number: " + studentNumber + "\nPerson ID: " + personID + "\nPicture URL: " + getPictureURL() + "\nIs Guardian? " + isGuardian + "\n\n===Calendars===";
		
		for(Calendar c : calendars)
		{
			userInfo += "\n" + c.getInfoString();
		}
		
		return userInfo;
	}
}
