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

import classbook.ClassbookManager;
import classbook.Student;
import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.ParsingException;

import java.io.*;
import java.net.URL;

public class Main {
    static PrintWriter out;

    public static void main(String[] args) throws IOException, ParsingException {

        String username;
        String password;
        String districtCode;

        FileManagement.deleteGrades();
        if(!FileManagement.getExisting()) {
            FileManagement.saveDistrictCode();
            FileManagement.saveUsername();
            FileManagement.savePasswords();
        }
        districtCode = FileManagement.getDistrictCode();
        username = FileManagement.getUsername();
        password = FileManagement.getPassword();

        CoreManager core = new CoreManager(districtCode);

        boolean successfulLogin = core.attemptLogin(username, password, core.getDistrictInfo());
        if(!successfulLogin){
            FileManagement.deleteExisting();
            System.out.println("Invalid Username/Password or District Code");
            System.exit(0);
        }

        try
        {
            out = new PrintWriter(new BufferedWriter(new FileWriter("grades.txt")));
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return;
        }

        URL timeURL = new URL(core.getDistrictInfo().getDistrictBaseURL() + "/prism?x=portal.PortalOutline&appName=" + core.getDistrictInfo().getDistrictAppName());
        Builder builder = new Builder();
        Document doc = builder.build(new ByteArrayInputStream(core.getContent(timeURL, false).getBytes()));
        Element root = doc.getRootElement();
        Student user = new Student(root.getFirstChildElement("PortalOutline").getFirstChildElement("Family").getFirstChildElement("Student"), core.getDistrictInfo());

        System.out.println(user.getInfoString());

        URL gradesURL = new URL(core.getDistrictInfo().getDistrictBaseURL() + "/prism?&x=portal.PortalClassbook-getClassbookForAllSections&mode=classbook&personID=" + user.personID + "&structureID=" + user.calendars.get(0).schedules.get(0).id + "&calendarID=" + user.calendars.get(0).calendarID);
        Document doc2 = builder.build(new ByteArrayInputStream(core.getContent(gradesURL, false).getBytes()));
        ClassbookManager manager = new ClassbookManager(doc2.getRootElement().getFirstChildElement("SectionClassbooks"));
        System.out.println(manager.getInfoString());

        out.close();

        System.out.println("User info dump successful!");
        System.out.println("Press any key to exit...");

        System.in.read();
    }
}
