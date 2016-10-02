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

public class Classbook
{
	public String termName;
	public String courseNumber;
	public String courseName;
	public String sectionNumber;
	public String teacherDisplay;
	
	public ArrayList<ClassbookTask> tasks = new ArrayList<ClassbookTask>();
	public Classbook(Element classbook)
	{
		termName = classbook.getAttributeValue("termName");
		courseNumber = classbook.getAttributeValue("courseNumber");
		courseName = classbook.getAttributeValue("courseName");
		sectionNumber = classbook.getAttributeValue("sectionNumber");
		teacherDisplay = classbook.getAttributeValue("teacherDisplay");

		for(int i = 0; i < classbook.getFirstChildElement("tasks").getChildElements("ClassbookTask").size(); i++)
			tasks.add(new ClassbookTask(classbook.getFirstChildElement("tasks").getChildElements("ClassbookTask").get(i)));
	}
	
	public String getInfoString()
	{
		String str = "\nTasks for " + courseName + ", with teacher " + teacherDisplay + " and class ID " + courseNumber + ", " + termName;
		for(ClassbookTask t : tasks)
			str += "\n" + t.getInfoString();
		return str;
	}
}
