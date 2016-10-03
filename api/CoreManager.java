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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import com.fasterxml.jackson.databind.ObjectMapper;

import district.DistrictInfo;

public class CoreManager
{
	private ObjectMapper mapper = new ObjectMapper();
	private String cookies = "";
	private DistrictInfo distInfo;
    private String districtCode;

	public CoreManager(String districtCode)
	{
        this.districtCode = districtCode;
        try
        {
            distInfo = mapper.readValue(new URL("https://mobile.infinitecampus.com/mobile/checkDistrict?districtCode=" + districtCode), DistrictInfo.class);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
	}
	
	public DistrictInfo getDistrictInfo()
	{
		return distInfo;
	}
	
	public String getDistrictCode()
	{
		return districtCode;
	}
	
	public boolean attemptLogin(String user, String pass, DistrictInfo distInfo)
	{
		try
		{
            String encodedUser = URLEncoder.encode( user, "UTF-8" );
            String encodedPass = URLEncoder.encode( pass, "UTF-8" );

			URL loginURL = new URL( distInfo.getDistrictBaseURL() + "verify.jsp?nonBrowser=true&username=" + encodedUser + "&password=" + encodedPass + "&appName=" + distInfo.getDistrictAppName());
            System.out.println("Attempting login with url: " + loginURL);
			String response = getContent(loginURL, true);
            System.out.println("Responded with: " + response);
			if(response.trim().equalsIgnoreCase("<authentication>success</authentication>"))
                System.out.println("Trim returning true");
				return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	public String getContent(URL url, boolean altercookies)
	{
		String s = "";
		try
		{
			HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
			con.setRequestProperty("Cookie", cookies); //Retain our session
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String input;
			while ((input = br.readLine()) != null)
			{
				s += input + "\n";
			}
			br.close();
			
			StringBuilder sb = new StringBuilder();

			// find the cookies in the response header from the first request
			List<String> cookie = con.getHeaderFields().get("Set-Cookie");
			if (cookie != null) {
			    for (String cooki : cookie) {
			        if (sb.length() > 0) {
			            sb.append("; ");
			        }

			        // only want the first part of the cookie header that has the value
			        String value = cooki.split(";")[0];
			        sb.append(value);
			    }
			}
			if(altercookies)
				cookies = sb.toString();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return s;
	}
}
