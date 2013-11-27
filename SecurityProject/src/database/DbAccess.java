package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Properties;
import java.util.Vector;

import misc.CheckString;
import misc.ElapsedTime;
import misc.Processing;

import org.apache.log4j.Logger;

/**
 * Class written to provide jdbc Db access to an
 * Allegro 7.6 table in Db PRODACI1
 * The class is not generic but written specifically for
 * use on the Arch Coal Allegro Db implementation. To make
 * more generic you would need to pass in the Db url string.
 * 
 * jdbc requires that the ojdbc6.jar be added to the Project Libraries - Build Path
 * 
 * @author mblackford mBret Bret Blackford
 *
 */
public class DbAccess {
	
	static Logger log = Logger.getLogger(DbAccess.class);
	
	Connection conn;
	Vector resultVector = new Vector();
	
    String serverName = "acidb1.aci.corp.net";
    String portNumber = "1522";
    String sid = "prodaci1";
    String url = "jdbc:oracle:thin:@" + serverName + ":" + portNumber + ":" + sid;
    String username = "blackfordm";
    String password = "--";
    String selectSQL = "";
    String dbDriver = "";
    
    public DbAccess(Properties _props) {
    	serverName = _props.getProperty("db.server");
    	portNumber = _props.getProperty("db.port");
    	sid = _props.getProperty("db.sid");
    	url = _props.getProperty("db.dataSource");
    	username = _props.getProperty("db.userName");
    	password = _props.getProperty("db.passWord");
    	selectSQL = _props.getProperty("db.sql.security");
    	dbDriver = _props.getProperty("db.driver");
    }
    
    
	private void makeConnection() throws ClassNotFoundException, SQLException {

		log.info("\t  in DbAccess.makeConnection()");
		log.info("\t   Going to try the connection now ...");
		log.info("\t         url-[" + url + "]");
		log.info("\t    username-[" + username + "]");
		//log.info("\t    password-[" + password + "]");
		log.info("\t    dbDriver-[" + dbDriver + "]");
		
		//Class.forName("oracle.jdbc.driver.OracleDriver");
		Class.forName(dbDriver);
		conn = DriverManager.getConnection(url, username, password);
		
		log.info("Connection done !! \n");
	}
    
    public Vector makeDbCall(SqlSelection_Parent sqlObject) throws SQLException, ClassNotFoundException {
        
		log.info("in DbAccess.makeDbCall()");
		ElapsedTime metrics = new ElapsedTime();

		makeConnection();

		String sql = CheckString.check( sqlObject.getSql() );
		//sql = selectSQL;
		log.info("in DbAcess.makeDbCall(" + sql + ") \n");

		// creating PreparedStatement object to execute query
		PreparedStatement preStatement = conn.prepareStatement(sql);

		ResultSet result = preStatement.executeQuery();

		Vector dataVector = sqlObject.processResultSet(result);
		
		log.info(" leaving DbAccess.makeDbCall(" + sqlObject.objName + ") \n\n" );
		log.info(" DbAccess.makeDbCall() transaction took [" + metrics.elapsedTime() + "]ms or [" + metrics.elapsedTime_s() + "]seconds \n" );
		
		return dataVector;
    }
    
    public ResultSet makeSqlCall(String sql) throws ClassNotFoundException, SQLException {
    	
    	log.info("in DbAccess.makeSqlCall()");
    	log.debug(" SQL=[" + sql + "] \n");
    	
    	//makeConnection();
    	PreparedStatement preStatement = conn.prepareStatement(sql);
    	ResultSet  result = preStatement.executeQuery();
    	return result;
    }

}
