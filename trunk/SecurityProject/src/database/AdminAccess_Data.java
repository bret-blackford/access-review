package database;

import java.util.Vector;

public class AdminAccess_Data {

	public String excelTab;
	public String sqlStmt;
	
	public String securityGroup;
	public String userID;
	public String creationdate;
	public String method;
	public String enabled;
	public String email;
	public String name;
	public String status;

	public String delim = "|";
	
	public String toString() {
		String out = securityGroup + delim + userID + delim;
		out += creationdate + delim + method + delim + enabled + delim;
		out += email + delim + name + delim + status;

		return out;
	}
	
	
	public Vector<String> v_header(){
		
		Vector<String> columnHeader = new Vector<String>();
		
		columnHeader.add("A.securityGroup");
		columnHeader.add("B.userID");
		columnHeader.add("C.creationdate");
		columnHeader.add("D.method");
		columnHeader.add("E.enabled");
		columnHeader.add("F.email");
		columnHeader.add("G.name");
		columnHeader.add("H.status");
				
		return columnHeader;
	}
}
