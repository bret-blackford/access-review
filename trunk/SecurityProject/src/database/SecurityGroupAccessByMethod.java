package database;

import java.sql.ResultSet;
import org.apache.poi.ss.usermodel.CellStyle;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Vector;

import misc.BooleanTest;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * 
 * @author mblackford M. Bret Blackford (mBret)
 *
 */
public class SecurityGroupAccessByMethod extends SqlSelection_Parent {

	static Logger log = Logger.getLogger(SecurityGroupAccessByMethod.class);
	

	public String getSql() {
		return sql;
	}
	
	public void setSql(String _sql) {
		sql = _sql;
	}

	
	public Vector processResultSet(ResultSet rs) {

		objName = "SecurityGroupAccessByMethod";
		log.info("in SecurityGroupAccessByMethod.processResultSet()");
		dataVector = new Vector();
		int count = 0;

		SecurityGroupAccessByMethod_Data data;

		try {
			while (rs.next()) {
				data = new SecurityGroupAccessByMethod_Data();
				
				data.sgSecurityGroup = rs.getString("securitygroup");
				data.stDbTable =  rs.getString("dbtable");
				data.stReadAccess = BooleanTest.test( rs.getString("readaccess") );
				data.stInsertAccess = BooleanTest.test( rs.getString("insertaccess") );
				data.stUpdateAccess = BooleanTest.test( rs.getString("updateaccess") );
				data.stDeleteAccess = BooleanTest.test( rs.getString("deleteaccess") );

				log.info("[" + count++ + "]" + data.toString());
				dataVector.add(data);
			}

		} catch (SQLException e) {
			log.error(" ERROR in SecurityGroupAccessByMethod.processResultSet()", e);
		}
		log.info(" leaving SecurityGroupAccessByMethod.processResultSet()  ");
		return dataVector;
	}
	
	public void printExcelWorksheet(Workbook _workbook, Vector _inVec) {

		workbook = _workbook;
		sheet = workbook.createSheet("1.SecurityGroupAccessByMethod");
		
		log.info("in SecurityGroupAccessByMethod.printExcelWorksheet() ...");
		log.info(" received vector sized [" + _inVec.size() + "]");
				
		addHeaders();
		
		Iterator itrRow = _inVec.iterator();
		
		while (itrRow.hasNext()) {
			
			Row row = sheet.createRow(rowCount);
			int cellCount = 0;
			
			SecurityGroupAccessByMethod_Data dataRow = (SecurityGroupAccessByMethod_Data)itrRow.next();
			
			Cell cellA = row.createCell(cellCount++);
			cellA.setCellValue(dataRow.sgSecurityGroup);
			Cell cellB = row.createCell(cellCount++);
			cellB.setCellValue(dataRow.stDbTable);
			Cell cellC = row.createCell(cellCount++);
			cellC.setCellValue(dataRow.stReadAccess);
			checkCell(cellC);
			Cell cellD = row.createCell(cellCount++);
			cellD.setCellValue(dataRow.stInsertAccess);
			checkCell(cellD);
			Cell cellE = row.createCell(cellCount++);
			cellE.setCellValue(dataRow.stUpdateAccess);
			checkCell(cellE);
			Cell cellF = row.createCell(cellCount++);
			cellF.setCellValue(dataRow.stDeleteAccess);
			checkCell(cellF);

			log.info("[" + rowCount + "]" + dataRow.toString());
			rowCount++;
		}
		format();
	}
	
	private void addHeaders() {
		
		log.info("in SecurityGroupAccessByMethod.addHeaders()");
		
		Vector vec = new SecurityGroupAccessByMethod_Data().v_header();
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
