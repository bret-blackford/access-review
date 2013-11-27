package database;

import java.util.Iterator;
import java.util.Vector;

import misc.WriteToExcel;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * 
 * @author mblackford  M. Bret Blackford (mBret)
 *
 */
public class UserAccess_Data {
	
	static Logger log = Logger.getLogger(UserAccess_Data.class);
	
	public String securityGroup;
	public String userID;
	public String creationdate;
	public String method;
	public String enabled;
	public String creationName;
	public String email;
	public String name;
	public String status;
	
	public String delim = "|";
	
	public String toString() {
		String out = securityGroup + delim;
		out += userID + delim + creationdate + delim;
		out += method + delim + enabled + delim;
		out += creationName + delim + email + delim;
		out += name + delim + status;

		return out;
	}
	
	public String header() {
		String out = "SecurityGroup" + delim + "userID" + delim;
		out += "creationdate" + delim + "method" + delim + "enabled" + delim;
		out += "creationName" + delim + "email" + delim + "name" + delim + "status";
		
		return out;
	}
	
	public Vector<String> v_header(){
		
		Vector<String> columnHeader = new Vector<String>();
		
		columnHeader.add("A.SecurityGroup");
		columnHeader.add("B.UserID");
		columnHeader.add("C.CreationDate");
		columnHeader.add("D.Method");
		columnHeader.add("E.Enabled");
		columnHeader.add("F.CreationName");
		columnHeader.add("G.E-Mail");
		columnHeader.add("H.Name");
		columnHeader.add("I.Status");
				
		return columnHeader;
	}
	
	

}
