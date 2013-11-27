package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Vector;

import misc.BooleanTest;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * 
 * @author mblackford  M. Bret Blackford (mBret)
 *
 */
public class UserAccess extends SqlSelection_Parent {

	static Logger log = Logger.getLogger(UserAccess.class);

	
	public String getSql() {
		return sql;
	}
	
	public void setSql(String _sql) {
		sql = _sql;
	}
	
	public Vector processResultSet(ResultSet rs) {

		objName = "UserAccess";
		log.info("in UserAccess.processResultSet()");
		dataVector = new Vector();
		int count = 0;

		UserAccess_Data data;

		try {
			while (rs.next()) {
				data = new UserAccess_Data();
				
				data.securityGroup 	= rs.getString("securitygroup");
				data.userID 		= rs.getString("userID");
				data.creationdate 	= rs.getString("creationdate");
				data.method 		= rs.getString("method");
				data.enabled 		= BooleanTest.test( rs.getString("enabled") );
				data.creationName 	= rs.getString("creationName");
				data.email 			= rs.getString("email");
				data.name 			= rs.getString("name");
				data.status 		= rs.getString("status");

				log.info("{" + count++ + "}" + data.toString());
				dataVector.add(data);
			}
		} catch (SQLException e) {
			log.error(" ERROR in UserAccess.processResultSet()", e);
		}
		return dataVector;
	}
	
	public void printExcelWorksheet(Workbook _workbook, Vector _inVec) {

		workbook = _workbook;
		sheet = workbook.createSheet("2.UserAccess");
		log.info("in UserAccess.printExcelWorksheet() ...");

		addHeaders();
		
		Iterator itrRow = _inVec.iterator();
		
		while (itrRow.hasNext()) {
			
			Row row = sheet.createRow(rowCount);
			int cellCount = 0;
			
			UserAccess_Data dataRow = (UserAccess_Data)itrRow.next();
			
			Cell cellA = row.createCell(cellCount++);
			cellA.setCellValue(dataRow.securityGroup);
			Cell cellB = row.createCell(cellCount++);
			cellB.setCellValue(dataRow.userID);
			Cell cellC = row.createCell(cellCount++);
			cellC.setCellValue(dataRow.creationdate);
			Cell cellD = row.createCell(cellCount++);
			cellD.setCellValue(dataRow.method);
			Cell cellE = row.createCell(cellCount++);
			cellE.setCellValue(dataRow.enabled);
			Cell cellF = row.createCell(cellCount++);
			cellF.setCellValue(dataRow.creationName);
			Cell cellG = row.createCell(cellCount++);
			cellG.setCellValue(dataRow.email);
			Cell cellH = row.createCell(cellCount++);
			cellH.setCellValue(dataRow.name);
			Cell cellI = row.createCell(cellCount++);
			cellI.setCellValue(dataRow.status);

			log.info( "{{" + rowCount + "}}" + dataRow.toString() );
			rowCount++;
		}
		format();
	}
	
	private void addHeaders() {
		
		log.info("in UserAccess.addHeaders()");
		
		Vector vec = new UserAccess_Data().v_header();
		Row row = sheet.createRow(rowCount++);
		int cellCount_H = 0;
		numColumns = vec.size();
		
		Iterator column = vec.iterator();
		
		while(column.hasNext()) {
			String data = (String)column.next();
			Cell cell = row.createCell(cellCount_H);
			cell.setCellValue(data);
			
			cellCount_H++;
		}
	}
}
