package database;

import java.util.Vector;

import org.apache.log4j.Logger;

public class AuditSecurity_Data {

	public String excelTab;
	public String sqlStmt;
	
	public String surrogate;
	public String windowName;
	public String dbTable;
	public String keyValue;
	public String action;
	public String columnName;
	public String origValue;
	public String modValue;
	public String auditName;
	public String auditDate;

	public String delim = "|";
	
	public String toString() {
		String out = surrogate + delim + windowName + delim;
		out += dbTable + delim + keyValue + delim + action + delim;
		out += columnName + delim + origValue + delim + modValue + delim;
		out += auditName + delim + auditDate;

		return out;
	}
	
	
	public Vector<String> v_header(){
		
		Vector<String> columnHeader = new Vector<String>();
		
		columnHeader.add("A.surrogate");
		columnHeader.add("B.windowName");
		columnHeader.add("C.dbTable");
		columnHeader.add("D.keyValue");
		columnHeader.add("E.action");
		columnHeader.add("F.columnName");
		columnHeader.add("G.origValue");
		columnHeader.add("H.modValue");
		columnHeader.add("I.auditName");
		columnHeader.add("J.auditDate");
				
		return columnHeader;
	}
	
}
