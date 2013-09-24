import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;



public class RobotsInterpreterTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String file = requestPage("http://www.uwindsor.ca/robots.txt");
		
		RobotsInterpreter x = new RobotsInterpreter();
		x.addFile("uwindsor.ca", file);
		
		System.out.println("Setting access time");
		x.setLastAccessTime("uwindsor.ca");
		
		System.out.println("Time remaining: " + x.checkTimeRemaining("uwindsor.ca"));
		
		System.out.println("Re-created Robots from parse: \n------------------------------------\n"+x.getParsedFile("uwindsor.ca"));
		
	}
	
	public static String requestPage(String url) {
		
			BufferedReader in;
			String inputLine;
			StringBuffer response = new StringBuffer();

			try { // open the stream to the URL
				in = new BufferedReader(new InputStreamReader(new URL(url).openStream()));
			} catch (IOException e1) {
				return "";
			}

			try { // iterate over input stream until EOF
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine + "\n");
				}
				in.close();
			} catch (IOException e) {
				return "";
			}

		return response.toString();
	}

	
}
