package database;

import java.util.Vector;

import org.apache.log4j.Logger;

public class UserGroups_Data {
	
	static Logger log = Logger.getLogger(UserGroups_Data.class);
	
	public String excelTab;
	public String sqlStmt;
	
	public String securityGroup;
	public String userID;
	public String name;
	public String status;
	public String company;

	public String delim = "|";
	
	public String toString() {
		String out = securityGroup + delim;
		out += userID + delim + name + delim;
		out += status + delim + company;

		return out;
	}
	
	
	public Vector<String> v_header(){
		
		Vector<String> columnHeader = new Vector<String>();
		
		columnHeader.add("A.SecurityGroup");
		columnHeader.add("B.UserID");
		columnHeader.add("C.Name");
		columnHeader.add("D.Status");
		columnHeader.add("E.Company");
				
		return columnHeader;
	}
}
