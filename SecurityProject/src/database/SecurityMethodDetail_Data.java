package database;

import java.util.Vector;

/**
 * 
 * @author mblackford  M. Bret Blackford (mBret)
 *
 */
public class SecurityMethodDetail_Data {

	public String securityGroup;
	public String claSS;
	public String method;
	public String dbTable_st;
	public String read;
	public String insert;
	public String update;
	public String delete;
	public String dbTable_sc;
	public String dbColumn;
	public String enabled;
	
	public String delim = "|";
	
	public String toString() {
		String out = securityGroup + delim;
		out += claSS + delim + method + delim + dbTable_st + delim;
		out += read + delim + insert + delim + update + delim + delete + delim;
		out += dbTable_sc + delim + dbTable_sc + delim + dbColumn + delim + enabled;

		return out;
	}
	
	public String header() {
		String out = "securityGroup" + delim;
		out += "claSS" + delim + "method" + delim + "dbTable_st" + delim;
		out += "read" + delim + "insert" + delim + "update" + delim + "delete" + delim;
		out += "dbTable_sc" + delim + "dbColumn" + delim + "enabled";
		
		return out;
	}
	
	public Vector<String> v_header(){
		
		Vector<String> columnHeader = new Vector<String>();
		
		columnHeader.add("A.SecurityGroup");
		columnHeader.add("B.Class");
		columnHeader.add("C.Method");
		columnHeader.add("D.ST.DbTable");
		columnHeader.add("E.READ");
		columnHeader.add("F.INSERT");
		columnHeader.add("G.UPDATE");
		columnHeader.add("H.DELETE");
		columnHeader.add("I.SC.DbTable");
		columnHeader.add("J.dbColumn");
		columnHeader.add("K.enabled");
				
		return columnHeader;
	}
}
