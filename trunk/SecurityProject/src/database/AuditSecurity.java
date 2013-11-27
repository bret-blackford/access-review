package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

public class AuditSecurity extends SqlSelection_Parent {

static Logger log = Logger.getLogger(AuditSecurity.class);

	
	public String getSql() {
		return sql;
	}
	
	public void setSql(String _sql) {
		sql = _sql;
	}
	
	public Vector processResultSet(ResultSet rs) {

		objName = "UserGroup";
		log.info("in AuditSecurity.processResultSet()");
		dataVector = new Vector();
		int count = 0;

		AuditSecurity_Data data;

		try {
			while (rs.next()) {
				data = new AuditSecurity_Data();
				
				data.surrogate 	= rs.getString("surrogate");
				data.windowName = rs.getString("windowname");
				data.dbTable 	= rs.getString("dbtable");
				data.keyValue 	= rs.getString("keyvalue");
				data.action 	= rs.getString("action");
				data.columnName = rs.getString("columnname");
				data.origValue 	= rs.getString("origvalue");
				data.modValue 	= rs.getString("modvalue");
				data.auditName 	= rs.getString("auditname");
				data.auditDate 	= rs.getString("auditdate");

				log.info("{" + count++ + "}: " + data.toString());
				dataVector.add(data);
			}
		} catch (SQLException e) {
			log.error(" ERROR in AuditSecurity.processResultSet()", e);
		}
		return dataVector;
	}
	
	public void printExcelWorksheet(Workbook _workbook, Vector _inVec) {

		workbook = _workbook;
		sheet = workbook.createSheet("4.AuditSecurity");
		log.info("in AuditSecurity.printExcelWorksheet() ...");

		addHeaders();
		
		Iterator itrRow = _inVec.iterator();
		
		while (itrRow.hasNext()) {
			
			Row row = sheet.createRow(rowCount);
			int cellCount = 0;
			
			AuditSecurity_Data dataRow = (AuditSecurity_Data)itrRow.next();
			
			Cell cellA = row.createCell(cellCount++);
			cellA.setCellValue(dataRow.surrogate);
			Cell cellB = row.createCell(cellCount++);
			cellB.setCellValue(dataRow.windowName);
			Cell cellC = row.createCell(cellCount++);
			cellC.setCellValue(dataRow.dbTable);
			Cell cellD = row.createCell(cellCount++);
			cellD.setCellValue(dataRow.keyValue);
			Cell cellE = row.createCell(cellCount++);
			cellE.setCellValue(dataRow.action);
			Cell cellF = row.createCell(cellCount++);
			cellF.setCellValue(dataRow.columnName);
			Cell cellG = row.createCell(cellCount++);
			cellG.setCellValue(dataRow.origValue);
			Cell cellH = row.createCell(cellCount++);
			cellH.setCellValue(dataRow.modValue);
			Cell cellI = row.createCell(cellCount++);
			cellI.setCellValue(dataRow.auditName);
			Cell cellJ = row.createCell(cellCount++);
			cellJ.setCellValue(dataRow.auditDate);

			log.info( "AuditSecurity.printExcelWorksheet() {{" + rowCount + "}}" + dataRow.toString() );
			rowCount++;
		}
		format();
	}
	
	private void addHeaders() {
		
		log.info("in AuditSecurity.addHeaders()");
		
		Vector vec = new AuditSecurity_Data().v_header();
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
