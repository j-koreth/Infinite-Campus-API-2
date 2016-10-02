import classbook.ClassbookManager;
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
