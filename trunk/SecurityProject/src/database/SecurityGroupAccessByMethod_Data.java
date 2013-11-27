package database;

import java.util.Vector;

/**
 * 
 * @author mblackford M. Bret Blackford (mBret)
 *
 */
public class SecurityGroupAccessByMethod_Data {
	
	public String sgSecurityGroup;
	public String stDbTable;
	public String stReadAccess;
	public String stInsertAccess;
	public String stUpdateAccess;
	public String stDeleteAccess;
	
	String delim = "|";

	
	public String toString() {
		String out = sgSecurityGroup + delim;
		out += stDbTable + delim + stReadAccess + delim;
		out += stInsertAccess + delim + stUpdateAccess + delim;
		out += stDeleteAccess;

		return out;
	}
	
	public String header() {
		String out = "SecurityGroup" + delim + "DbTable" + delim;
		out += "Read" + delim + "Insert" + delim + "Update" + delim;
		out += "Delete";
		
		return out;
	}
	
	public Vector<String> v_header(){
		
		Vector<String> columnHeader = new Vector<String>();
		
		columnHeader.add("A.SecurityGroup");
		columnHeader.add("B.DbTable");
		columnHeader.add("C.READ");
		columnHeader.add("D.INSERT");
		columnHeader.add("E.UPDATE");
		columnHeader.add("F.DELETE");
				
		return columnHeader;
	}
}
