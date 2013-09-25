/*RobotWebpagePermissions
Creator: Joshua Chittle
Contributors: willisj (via shameless code theft)

Description: 
Contains and allows access to information regarding Robots.txt webpage permissions.
If a domain is queried which is not already contained by the object, Robots.txt is fetched and parsed prior to returning.
*/

import java.util.StringTokenizer;
import java.net.URL;
import java.util.Arrays;

public class RobotWebpagePermissions
{
	private static Pattern p = Pattern.compile("(Disallow|Allow|User-Agent):\\s*([^#\n]*)\\s*");
	
	private String _name;
	private PathTreeMap<String, Boolean> _isAllowedMap;
	
	public Robot(String name)
	{
		this._name = name;
		this._isAllowedMap = new PathTreeMap<String, Boolean>
	}
	
	public String getName()
	{
		return this._name;
	}
	
	public void add(String url, bool isAllowed)
	{
		this._isAllowedMap.put(Robot.toPathArray(url), new Boolean(isAllowed));
	}
	
	public bool isAllowed(String url)
	{
		if(!this._isAllowedMap.containsKey(Robot.GetHostOf(url)))
		{
			this.parseAndAdd(url);
		}
		return this._isAllowedMap.get(Robot.toPathArray(url));
	}
	
	private void parseAndAdd(String url)
	{
		this.parseAndAdd(new URL(url));
	}
	
	private void parseAndAdd(URL url)
	{
		URL robotsURL = new URL(new URL(url.getHost()), "robots.txt");
		
		BufferedReader reader = new BufferedReader(robotsURL);
		
		Matcher lineMatcher;
		String field;
		String value;
		String currentUserAgent;
		
		while(reader.ready())
		{
			lineMatcher = p.matcher(reader.readLine());

			field = m.group(1);
			value = m.group(2);

			if(m.matches())
			{
				if(currentUserAgent.equals("*") || currentUserAgent.equals(this.getName()))
				{
					if (field.equalsIgnoreCase("disallow"))
					{
						this.add(Robot.pushFront(url.getHost(), Robots.toPathArray(value)), new Boolean(false));
					}
					else if(field.equalsIgnoreCase("allow"))
					{
						this.add(Robot.pushFront(ulr.getHost(), Robots.toPathArray(value)), new Boolean(true));
					}
				}
				
				if (field.equalsIgnoreCase("user-agent")) 
				{
					currentUserAgent = value.trim();
				}
			}
		}
	}
	
	private static String[] pushFront(String string, String[] array)
	{
		String[] newArray = new String[array.length + 1];
		
		newArray[0] = string;
		System.arraycopy(array, 0, newArray, 1, array.length);
		
		return newArray;
	}
	
	private static String[] toDomainPathArray(String url)
	{
		return Robot.pushFront(Robot.getHostOf(url), Robot.toPathArray(url.getPath()));
	}
	
	private static String[] toPathArray(String relativePath)
	{
		StringTokenizer tokenizer = new StringTokenize(relativePath, "/");
		String[] pathArray = new String[tokenizer.countTokens()];
		
		for(int i = 0; tokenizer.hasMoreTokens(); ++i)
		{
			pathArray[i] = tokenizer.nextToken();
		}
		
		return pathArray;
	}
	
	private static String getHostOf(String url)
	{
		return new URL(url).getHost();
	}
}
