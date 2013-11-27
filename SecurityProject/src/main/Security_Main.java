package main;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import misc.ElapsedTime;
import misc.Processing;

import org.apache.log4j.Logger;

/**
 * This application will periodically (monthly) check for any security changes associated
 * with the Allegro deal capture system.  
 * The application uses .jdbc to make Db calls to retrieve security data and uses Apache POI 
 * to write details to an Excel spreadsheet (Apache's log4j is also used for logging).
 * An external property file -- allegro.security.properties.txt -- is used for the name and
 * location of the Excel file created as well as the SQL statements used for the varios
 * Db calls.
 * 
 * @author mblackford
 * @author Michael Bret Blackford (mBret)
 *
 */
public class Security_Main {
	
	private static Logger log = Logger.getLogger(Security_Main.class);
	public static Properties props = null;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		log.info("====START====================");
		log.info("====in Security_Main.main()==\n");
		ElapsedTime metrics = new ElapsedTime();
		
		props = new Properties();
		if( args != null && args.length > 0 ) {
			try {
				props.load( new FileInputStream(args[0]) );
			} catch(FileNotFoundException e) {
				log.error("DUDE! No such file!");
				log.error(e);
			} catch(IOException e) {
				log.error("EXCEPTION while getting result set", e); 
				e.printStackTrace(); 
			}
		} else {
			log.info("No parameters used. No PROPERTY file passed in.");
		}
		
		
		Processing process = new Processing(props);
		process.getData();
		
		log.info("====DONE====================");
		log.info("====DONE====================\n");
		log.info("Security_Main.main() took [" + metrics.elapsedTime() + "]ms or [" +  metrics.elapsedTime_s() + "]seconds");
		log.info("");
	}

}
