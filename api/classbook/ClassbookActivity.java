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

import nu.xom.Element;

public class ClassbookActivity
{
	public String activityID;
	public String name;
	public String abbrev;
	public String dueDate;
	public String assignedDate;
	public double totalPoints;
	public boolean active;
	public boolean notGraded;
	//hidePortal
	//seq
	public float weight;
	public String scoringType;
	public boolean validScore;
	public String scoreID;
	public float score;
	public boolean late;
	public boolean missing;
	public boolean incomplete;
	public boolean turnedIn;
	public boolean exempt;
	public boolean cheated;
	public boolean dropped;
	public float percentage;
	public String letterGrade;
	public float weightedScore;
	public float weightedTotalPoints;
	public float weightedPercentage;
	public double numericScore;
	public boolean wysiwygSubmission;
	public boolean onlineAssessment;
	
	public ClassbookActivity(Element activity)
	{
		activityID = activity.getAttributeValue("activityID");
		name = activity.getAttributeValue("name");
		abbrev = activity.getAttributeValue("abbrev");
		dueDate = activity.getAttributeValue("dueDate");
		assignedDate = activity.getAttributeValue("assignedDate");
		totalPoints = Double.parseDouble(activity.getAttributeValue("totalPoints"));
		active = activity.getAttributeValue("active").equalsIgnoreCase("true");
		notGraded = activity.getAttributeValue("notGraded").equalsIgnoreCase("true");
		weight = Float.parseFloat(activity.getAttributeValue("weight"));
		scoringType = activity.getAttributeValue("scoringType");
		validScore = activity.getAttributeValue("validScore").equalsIgnoreCase("validScore");
		scoreID = activity.getAttributeValue("scoreID");
		score = Float.parseFloat(activity.getAttributeValue("score"));
		late = activity.getAttributeValue("late").equalsIgnoreCase("true");
		missing = activity.getAttributeValue("missing").equalsIgnoreCase("true");
		incomplete = activity.getAttributeValue("incomplete").equalsIgnoreCase("true");
		turnedIn = activity.getAttributeValue("turnedIn").equalsIgnoreCase("true");
		cheated = activity.getAttributeValue("cheated").equalsIgnoreCase("true");
		dropped = activity.getAttributeValue("dropped").equalsIgnoreCase("true");
		percentage = Float.parseFloat(activity.getAttributeValue("percentage"));
		letterGrade = activity.getAttributeValue("letterGrade");
		weightedScore = Float.parseFloat(activity.getAttributeValue("weightedScore"));
		weightedTotalPoints = Float.parseFloat(activity.getAttributeValue("weightedTotalPoints"));
		weightedPercentage = Float.parseFloat(activity.getAttributeValue("weightedPercentage"));
		numericScore = Double.parseDouble(activity.getAttributeValue("numericScore"));
		wysiwygSubmission = activity.getAttributeValue("wysiwygSubmission").equalsIgnoreCase("true");
		onlineAssessment = activity.getAttributeValue("onlineAssessment").equalsIgnoreCase("true");
	}
	
	public String getInfoString()
	{
		return name + "\t\t" + letterGrade + " " + weightedPercentage + "%" + "\t(" + score + "/" + (int)totalPoints + ")";
	}
}
