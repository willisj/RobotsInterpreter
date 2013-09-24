import java.util.HashMap;
import java.util.Map;

public class RobotsInterpreter {

	Map<String, Domain> domains = new HashMap<String, Domain>();

	public RobotsInterpreter() {

	}

	// the crawler can make this call and sleep the thread for this many seconds
	// in order to obey robots.txt precisely
	public long checkTimeRemaining(String domain) {
		return domains.get(domain).checkTimeRemaining();
	}
	
	public void setLastAccessTime(String domain){
		domains.get(domain).setLastAccessTime();
	}
	
	public void addFile(String domain, String file){
		domains.put(domain,new Domain(file));
	}
}
