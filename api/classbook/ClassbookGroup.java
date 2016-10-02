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

import java.util.ArrayList;

import nu.xom.Element;

public class ClassbookGroup
{
	public String activityID;
	public String name;
	public float weight;
	public int seq;
	public boolean notGraded = false;
	//hidePortal
	//hasValidScore
	//composite
	//calcEclude
	public int termID;
	public int taskID;
	public float percentage;
	public String formattedPercentage;
	public String letterGrade;
	public float pointsEarned;
	public float totalPointsPossible;
	
	public ArrayList<ClassbookActivity> activities = new ArrayList<ClassbookActivity>();
	
	public ClassbookGroup(Element group)
	{
		activityID = group.getAttributeValue("activityID");
		name = group.getAttributeValue("name");
		weight = Float.parseFloat(group.getAttributeValue("weight"));
		seq = Integer.parseInt(group.getAttributeValue("seq"));
		notGraded = group.getAttributeValue("notGraded").equalsIgnoreCase("true");
		termID = Integer.parseInt(group.getAttributeValue("termID"));
		taskID = Integer.parseInt(group.getAttributeValue("taskID"));
		percentage = Float.parseFloat(group.getAttributeValue("percentage"));
		formattedPercentage = group.getAttributeValue("formattedPercentage");
		letterGrade = group.getAttributeValue("letterGrade");
		pointsEarned = Float.parseFloat(group.getAttributeValue("pointsEarned"));
		totalPointsPossible = Float.parseFloat(group.getAttributeValue("totalPointsPossible"));
		
		for(int i = 0; i < group.getFirstChildElement("activities").getChildElements("ClassbookActivity").size(); i++)
			activities.add(new ClassbookActivity(group.getFirstChildElement("activities").getChildElements("ClassbookActivity").get(i)));
		
		
		letterGrade = (letterGrade == null ? "?" : letterGrade);
		formattedPercentage = (formattedPercentage == null ? "?" : formattedPercentage);
	}
	
	public String getInfoString()
	{
		String str = name + (name.length() < 16 ? "\t" : "") + "\t(" + (weight > 0 ? "Weight: " + weight + ", " : "") + "Grade: " + letterGrade + ", " + formattedPercentage + "%)";
		
		for(ClassbookActivity a : activities)
			str += "\n\t" + a.getInfoString().replace("\n", "\n\t");
		
		return str;
	}
}
