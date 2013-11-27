package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

public class AdminAccess extends SqlSelection_Parent {

static Logger log = Logger.getLogger(AdminAccess.class);

	
	public String getSql() {
		return sql;
	}
	
	public void setSql(String _sql) {
		sql = _sql;
	}
	
	public Vector processResultSet(ResultSet rs) {

		objName = "AdminAccess";
		log.info("in AdminAccess.processResultSet()");
		dataVector = new Vector();
		int count = 0;

		AdminAccess_Data data;

		try {
			while (rs.next()) {
				data = new AdminAccess_Data();
				
				data.securityGroup 	= rs.getString("securitygroup");
				data.userID 		= rs.getString("userid");
				data.creationdate 	= rs.getString("creationdate");
				data.method 		= rs.getString("method");
				data.enabled 		= rs.getString("enabled");
				data.email			= rs.getString("email");
				data.name 			= rs.getString("NAME");
				data.status 		= rs.getString("status");

				log.info("{" + count++ + "}: " + data.toString());
				dataVector.add(data);
			}
		} catch (SQLException e) {
			log.error(" ERROR in AdminAccess.processResultSet()", e);
		}
		return dataVector;
	}
	
	public void printExcelWorksheet(Workbook _workbook, Vector _inVec) {

		workbook = _workbook;
		sheet = workbook.createSheet("5.AdminAccess");
		log.info("in AdminAccess.printExcelWorksheet() ...");

		addHeaders();
		
		Iterator itrRow = _inVec.iterator();
		
		while (itrRow.hasNext()) {
			
			Row row = sheet.createRow(rowCount);
			int cellCount = 0;
			
			AdminAccess_Data dataRow = (AdminAccess_Data)itrRow.next();
			
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
			cellF.setCellValue(dataRow.email);
			Cell cellG = row.createCell(cellCount++);
			cellG.setCellValue(dataRow.name);
			Cell cellH = row.createCell(cellCount++);
			cellH.setCellValue(dataRow.status);


			log.info( "AdminAccess.printExcelWorksheet() {{" + rowCount + "}}: " + dataRow.toString() );
			rowCount++;
		}
		format();
	}
	
	private void addHeaders() {
		
		log.info("in AdminAccess.addHeaders()");
		
		Vector vec = new AdminAccess_Data().v_header();
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
