/*RobotWebpagePermissions
Creator: Joshua Chittle
Contributors: willisj (via shameless code theft), code debugging

Description: 
Contains and allows access to information regarding Robots.txt webpage permissions.
If a domain is queried which is not already contained by the object, Robots.txt is fetched and parsed prior to returning.
 */

import java.util.StringTokenizer;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RobotWebpagePermissions {
	private static Pattern p = Pattern
			.compile("(Disallow|Allow|User-Agent):\\s*([^#\n]*)\\s*");

	private String _name;
	private PathTreeMap<String, Boolean> _isAllowedMap;

	public RobotWebpagePermissions(String name)
	{
		this._name = name;
		this._isAllowedMap = new PathTreeMap<String, Boolean>(true);
	}

	public String getName() {
		return this._name;
	}

	public void add(String url, boolean isAllowed) {
		this._isAllowedMap.put(RobotWebpagePermissions.toPathArray(url),
				new Boolean(isAllowed));
	}

	public boolean isAllowed(String url) {
		try {
			if (!this._isAllowedMap.containsKey(RobotWebpagePermissions
					.getHostOf(url)))
				this.parseAndAdd(url);

		} catch (MalformedURLException e) {
			return true; // no robots.txt found
		}
		return this._isAllowedMap.get(RobotWebpagePermissions.toPathArray(url));
	}

	private void parseAndAdd(String url) throws MalformedURLException {
		this.parseAndAdd(new URL(url));
	}

	private void parseAndAdd(URL url) {
		URL robotsURL = new URL(new URL(url.getHost()), "robots.txt");

		BufferedReader reader = new BufferedReader(new InputStreamReader(
				robotsURL.openStream()));

		Matcher lineMatcher;
		String field;
		String value;
		String currentUserAgent;

		while (reader.ready()) {
			lineMatcher = p.matcher(reader.readLine());

			field = lineMatcher.group(1);
			value = lineMatcher.group(2);

			if (lineMatcher.matches()) {
				if (currentUserAgent.equals("*")
						|| currentUserAgent.equals(this.getName())) {
					if (field.equalsIgnoreCase("disallow")) {
						this.add(RobotWebpagePermissions.pushFront(
								url.getHost(),
								RobotWebpagePermissions.toPathArray(value)),
								new Boolean(false));
					} else if (field.equalsIgnoreCase("allow")) {
						this.add(RobotWebpagePermissions.pushFront(
								url.getHost(),
								RobotWebpagePermissions.toPathArray(value)),
								new Boolean(true));
					}
				}

				if (field.equalsIgnoreCase("user-agent")) {
					currentUserAgent = value.trim();
				}
			}
		}
	}

	private static String[] pushFront(String string, String[] array) {
		String[] newArray = new String[array.length + 1];

		newArray[0] = string;
		System.arraycopy(array, 0, newArray, 1, array.length);

		return newArray;
	}

	private static String[] toDomainPathArray(String url)
			throws MalformedURLException {
		return RobotWebpagePermissions.pushFront(
				RobotWebpagePermissions.getHostOf(url),
				RobotWebpagePermissions.toPathArray(new URL(url).getPath()));
	}

	private static String[] toPathArray(String relativePath) {
		StringTokenizer tokenizer = new StringTokenizer(relativePath, "/");
		String[] pathArray = new String[tokenizer.countTokens()];

		for (int i = 0; tokenizer.hasMoreTokens(); ++i) {
			pathArray[i] = tokenizer.nextToken();
		}

		return pathArray;
	}

	private static String getHostOf(String url) throws MalformedURLException {
		return new URL(url).getHost();
	}
}
