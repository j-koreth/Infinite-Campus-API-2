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

public class ClassbookTask
{
	public String taskID;
	public String name;
	public float weight;
	public boolean isWeighted = false;
	// hasValidGroup
	// hasValidWeightedGroup
	// locked
	public boolean gradeBookPosted = false;
	public int taskSeq;
	public int termID;
	public String termName;
	public int termSeq;
	public float totalPointsPossible;
	public float pointsEarned;
	public float percentage;
	public String letterGrade;
	public String formattedPercentage;
	public int curveID;

	public ArrayList<ClassbookTask> tasks = new ArrayList<ClassbookTask>();
	public ArrayList<ClassbookGroup> groups = new ArrayList<ClassbookGroup>();

	public ClassbookTask(Element task)
	{

		name = task.getAttributeValue("name");
		taskID = task.getAttributeValue("taskID");
		weight = Float.parseFloat(task.getAttributeValue("weight"));
		isWeighted = task.getAttributeValue("isWeighted").equalsIgnoreCase("true");
		gradeBookPosted = task.getAttributeValue("gradeBookPosted").equalsIgnoreCase("true");
		taskSeq = Integer.parseInt(task.getAttributeValue("taskSeq"));
		termID = Integer.parseInt(task.getAttributeValue("termID"));
		termName = task.getAttributeValue("termName");
		termSeq = Integer.parseInt(task.getAttributeValue("termSeq"));
		totalPointsPossible = Float.parseFloat(task.getAttributeValue("totalPointsPossible"));
		pointsEarned = Float.parseFloat(task.getAttributeValue("pointsEarned"));
		percentage = Float.parseFloat(task.getAttributeValue("percentage"));
		letterGrade = task.getAttributeValue("letterGrade");
		formattedPercentage = task.getAttributeValue("formattedPercentage");
		
		try
		{
			for (int i = 0; i < task.getFirstChildElement("groups").getChildElements("ClassbookGroup").size(); i++)
				groups.add(new ClassbookGroup(task.getFirstChildElement("groups").getChildElements("ClassbookGroup").get(i)));
		}
		catch(NumberFormatException e){e.printStackTrace();}
		catch (NullPointerException e){};
		
		try
		{
			curveID = Integer.parseInt(task.getAttributeValue("curveID"));
		
			for (int i = 0; i < task.getFirstChildElement("tasks").getChildElements("ClassbookTask").size(); i++)
				tasks.add(new ClassbookTask(task.getFirstChildElement("tasks").getChildElements("ClassbookTask").get(i)));
		}
		catch (Exception e) {}
		
		letterGrade = (letterGrade == null ? "?" : letterGrade);
		formattedPercentage = (formattedPercentage == null ? "?" : formattedPercentage);
	}

	public String getInfoString()
	{
		String str =  "Task: " + name + ", " + termName + " " + letterGrade + " " + formattedPercentage + "%";
		for(ClassbookTask t : tasks)
			str +=  "\n\t" + t.getInfoString().replace("\n", "\n\t");
		for(ClassbookGroup b : groups)
			str += "\n\t" + b.getInfoString().replace("\n", "\n\t");
		return str;
	}
}
