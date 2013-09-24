import java.util.Vector;
import java.util.Date;

public class Domain{
	Date date = new Date();
	 
	
	int crawlDelay =0;
	String userAgent = "*";
	Vector<String> disallowed;
	long lastAccess;
	
	public Domain(String robotsFile){
		// TODO: everything, right here
	}
	
	public void setLastAccessTime(){
		lastAccess = date.getTime();
	}
	
	public long checkTimeRemaining(){
		if(crawlDelay > 0)
			return 0;
		return crawlDelay - ((date.getTime() - lastAccess) / 1000);
	}
}
