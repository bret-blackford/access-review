package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Vector;

import misc.BooleanTest;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

public class UserGroups extends SqlSelection_Parent {

	static Logger log = Logger.getLogger(UserAccess.class);

	
	public String getSql() {
		return sql;
	}
	
	public void setSql(String _sql) {
		sql = _sql;
	}
	
	public Vector processResultSet(ResultSet rs) {

		objName = "UserGroup";
		log.info("in UserGroup.processResultSet()");
		dataVector = new Vector();
		int count = 0;

		UserGroups_Data data;

		try {
			while (rs.next()) {
				data = new UserGroups_Data();
				
				data.securityGroup 	= rs.getString("securitygroup");
				data.userID 		= rs.getString("userid");
				data.name 			= rs.getString("name");
				data.status 		= rs.getString("status");
				data.company 		= rs.getString("company");

				log.info("{" + count++ + "}" + data.toString());
				dataVector.add(data);
			}
		} catch (SQLException e) {
			log.error(" ERROR in UserGroup.processResultSet()", e);
		}
		return dataVector;
	}
	
	public void printExcelWorksheet(Workbook _workbook, Vector _inVec) {

		workbook = _workbook;
		sheet = workbook.createSheet("3.UserGroupAccess");
		log.info("in UserGroup.printExcelWorksheet() ...");

		addHeaders();
		
		Iterator itrRow = _inVec.iterator();
		
		while (itrRow.hasNext()) {
			
			Row row = sheet.createRow(rowCount);
			int cellCount = 0;
			
			UserGroups_Data dataRow = (UserGroups_Data)itrRow.next();
			
			Cell cellA = row.createCell(cellCount++);
			cellA.setCellValue(dataRow.securityGroup);
			Cell cellB = row.createCell(cellCount++);
			cellB.setCellValue(dataRow.userID);
			Cell cellC = row.createCell(cellCount++);
			cellC.setCellValue(dataRow.name);
			Cell cellD = row.createCell(cellCount++);
			cellD.setCellValue(dataRow.status);
			Cell cellE = row.createCell(cellCount++);
			cellE.setCellValue(dataRow.company);

			log.info( "UserGroup.printExcelWorksheet() {{" + rowCount + "}} " + dataRow.toString() );
			rowCount++;
		}
		format();
	}
	
	private void addHeaders() {
		
		log.info("in UserGroups.addHeaders()");
		
		Vector vec = new UserGroups_Data().v_header();
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
