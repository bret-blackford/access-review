package misc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Properties;
import java.util.Vector;

import main.Security_Main;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import database.AdminAccess;
import database.AuditSecurity;
import database.DbAccess;
import database.SecurityGroupAccessByMethod_Data;
import database.SecurityMethodDetail;
import database.SqlStmts;
import database.SqlStmts_Data;
import database.UserAccess;
import database.UserGroups;

import database.SecurityGroupAccessByMethod;


/**
 * Used to manage the processing of the option model calculations.
 * 1) get data from Db, 2) calculate option valuations, 3) print output to file
 * 
 * @author mblackford mBret Bret Blackford
 *
 */
public class Processing {
	
	static Logger log = Logger.getLogger(Processing.class);
	
	Vector dataVector1;
	Vector dataVector2 = new Vector();
	Vector dataVector2a = new Vector();
	Vector dataVector3 = new Vector();
	Vector dataVector4 = new Vector();
	Vector dataVector5 = new Vector();
	Vector sqlVec = new Vector();
	Properties props = null;
	
	String sql_table;
	String sql_userAccess;
	String sql_userGroups;
	String sql_auditTable;
	String sql_accessDetail;
	String sql_adminAccess;
	
	public Processing(Properties _props) {
		props = _props;
		
		sql_table = props.getProperty("db.sql.security.table");
		sql_userAccess = props.getProperty("db.sql.security.userAccess");
		sql_userGroups = props.getProperty("db.sql.security.userGroups");
		sql_auditTable = props.getProperty("db.sql.security.auditTable");
		sql_accessDetail = props.getProperty("db.sql.security.accessDetail");
		sql_adminAccess = props.getProperty("db.sql.security.adminAccess");
	}

	public void getData(){
		
		log.info("in Processing.getData()");
		log.info(" Getting ready for the hard stuff ...");
		ElapsedTime metrics = new ElapsedTime();
		
		DbAccess db = new DbAccess(props);
		try {
			ElapsedTime time1 = new ElapsedTime();
			log.info(" (1) First let's check table security by Group");
			SecurityGroupAccessByMethod selectObj = new SecurityGroupAccessByMethod();
			selectObj.setSql(sql_table);
			dataVector1 = db.makeDbCall(selectObj);
			long time_1 = time1.elapsedTime();
			log.info("Processing.getData() SecurityGroupAccessByMethod transaction took [" + time_1 + "]ms " );
			
			ElapsedTime time2 = new ElapsedTime();
			log.info(" (2) Second let's check user access");
			UserAccess accessObj = new UserAccess();
			accessObj.setSql(sql_userAccess);
			dataVector2 = db.makeDbCall(accessObj);
			long time_2 = time2.elapsedTime();
			log.info("Processing.getData() UserAccess transaction took [" + time_2 + "]ms " );
			
			
			ElapsedTime time2a = new ElapsedTime();
			log.info(" (3) Third let's check a user's Group access");
			UserGroups userGroupsObj = new UserGroups();
			userGroupsObj.setSql(sql_userGroups);
			dataVector2a = db.makeDbCall(userGroupsObj);
			long time_2a = time2a.elapsedTime();
			log.info("Processing.getData() UserAccess transaction took [" + time_2a + "]ms " );
			
			ElapsedTime time4 = new ElapsedTime();
			log.info(" (4) Fourth let's check the audit table for access changes");
			AuditSecurity auditObj = new AuditSecurity();
			auditObj.setSql(sql_auditTable);
			dataVector4 = db.makeDbCall(auditObj);
			long time_4 = time4.elapsedTime();
			log.info("Processing.getData() UserAccess transaction took [" + time_4 + "]ms " );
			
			ElapsedTime time5 = new ElapsedTime();
			log.info(" (5) Fith let's check Admin access");
			AdminAccess adminObj = new AdminAccess();
			adminObj.setSql(sql_adminAccess);
			dataVector5 = db.makeDbCall(adminObj);
			long time_5 = time5.elapsedTime();
			log.info("Processing.getData() adminObj transaction took [" + time_5 + "]ms " );
			
//			ElapsedTime time4 = new ElapsedTime();
//			log.info(" (4) Fourth let's check access detail");
//			SecurityMethodDetail methodObj = new SecurityMethodDetail();
//			methodObj.setSql(props.getProperty("db.sql.security.accessDetail"));
//			dataVector3 = db.makeDbCall(methodObj);
//			long time_4 = time3.elapsedTime();
//			log.info("Processing.getData() SecurityMethodDetail transaction took [" + time_4 + "]ms " );			
			
			getSql();
			
			WriteToExcel write = new WriteToExcel(props);
			write.write(dataVector1, selectObj);
			write.write(dataVector2, accessObj);
			write.write(dataVector2a, userGroupsObj);
			//write.write(dataVector3, methodObj);
			write.write(dataVector4, auditObj);
			write.write(dataVector5, adminObj);
			write.write(sqlVec, new SqlStmts());
			
			write.writeFile();
			
			log.info("Processing.getData() SecurityGroupAccessByMethod transaction took [" + time_1 + "]ms or [" + time_1/1000 + "]seconds" );
			log.info("Processing.getData() UserAccess transaction took [" + time_2 + "]ms or [" + time_2/1000 + "]seconds" );
			log.info("Processing.getData() UserGroups transaction took [" + time_2a + "]ms or [" + time_2a/1000 + "]seconds" );
			//log.info("Processing.getData() SecurityMethodDetail transaction took [" + time_3 + "]ms or [" + time_3/1000 + "]seconds" );
			log.info("Processing.getData() auditObj transaction took [" + time_4 + "]ms or [" + time_4/1000 + "]seconds" );
			log.info("Processing.getData() adminObj transaction took [" + time_5 + "]ms or [" + time_5/1000 + "]seconds" );
			
			log.info("Processing.getData() transaction took [" + metrics.elapsedTime() + "]ms or [" + metrics.elapsedTime_s() + "]seconds" );
			
		} catch (ClassNotFoundException e) {
			log.error(" *** ERROR 1 *** ");
			e.printStackTrace();
		} catch (SQLException e) {
			log.error(" *** ERROR 2 *** ");
			e.printStackTrace();
		} catch (Exception e) {
			log.error(" *** ERROR 3 *** ");
			e.printStackTrace();
		}
	}
	
	public void getSql() {
		SqlStmts_Data sql1 = new SqlStmts_Data();
		sql1.excelTab = "1.SecurityGroupAccessByMethod";
		sql1.sqlStmt = sql_table;
		sqlVec.add(sql1);
		
		SqlStmts_Data sql2 = new SqlStmts_Data();
		sql2.excelTab = "2.UserAccess";
		sql2.sqlStmt =sql_userAccess;
		sqlVec.add(sql2);
		
		SqlStmts_Data sql3 = new SqlStmts_Data();
		sql3.excelTab = "3.UserGroups";
		sql3.sqlStmt = sql_userGroups;
		sqlVec.add(sql3);
		
		SqlStmts_Data sql4 = new SqlStmts_Data();
		sql4.excelTab = "4.AuditSecurity";
		sql4.sqlStmt = sql_auditTable;
		sqlVec.add(sql4);
		
		SqlStmts_Data sql5 = new SqlStmts_Data();
		sql5.excelTab = "5.AdminAccess";
		sql5.sqlStmt = sql_adminAccess;
		sqlVec.add(sql5);
	}
	
}
