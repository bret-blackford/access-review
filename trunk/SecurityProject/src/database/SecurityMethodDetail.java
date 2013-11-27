package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Vector;

import misc.BooleanTest;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * 
 * @author mblackford  M. Bret Blackford (mBret)
 *
 */
public class SecurityMethodDetail extends SqlSelection_Parent{
	
	static Logger log = Logger.getLogger(SecurityMethodDetail.class);
	
	
	public String getSql() {
		return sql;
	}
	
	public void setSql(String _sql) {
		sql = _sql;
	}
	
	public Vector processResultSet(ResultSet rs) {

		objName = "SecurityGroupAccessByMethod";
		log.info("in SecurityMethodDetail.processResultSet()");
		dataVector = new Vector();
		int count = 0;

		SecurityMethodDetail_Data data;

		try {
			while (rs.next()) {
				data = new SecurityMethodDetail_Data();
				
				data.securityGroup 	= rs.getString("securitygroup");
				data.claSS 			= rs.getString("CLASS");
				data.method 		= rs.getString("method");
				data.dbTable_st 	= rs.getString("st_dbtable");
				data.read 			= BooleanTest.test( rs.getString("readaccess") );
				data.insert 		= BooleanTest.test( rs.getString("insertaccess") );
				data.update 		= BooleanTest.test( rs.getString("updateaccess") );
				data.delete 		= BooleanTest.test( rs.getString("deleteaccess") );
				data.dbTable_sc 	= rs.getString("sc_dbtable");
				data.dbColumn 		= rs.getString("dbcolumn");
				data.enabled 		= BooleanTest.test( rs.getString("enabled") );

				log.info("|[" + count++ + "]|" + data.toString());
				dataVector.add(data);
			}
		} catch (SQLException e) {
			log.error(" ERROR in SecurityMethodDetail.processResultSet()", e);
		}
		return dataVector;
	}
	
	public void printExcelWorksheet(Workbook _workbook, Vector _inVec) {
		
		log.info("in SecurityMethodDetail.printExcelWorksheet()");
	
		workbook = _workbook;
		sheet = workbook.createSheet("3.SecurityMethodDetail");
		
		addHeaders();
		
		Iterator itrRow = _inVec.iterator();
		
		while ( itrRow.hasNext() ) {
			
			Row row = sheet.createRow(rowCount);
			int cellCount = 0;
			
			SecurityMethodDetail_Data dataRow = (SecurityMethodDetail_Data)itrRow.next();
			
			Cell cellA = row.createCell(cellCount++);
			cellA.setCellValue(dataRow.securityGroup);
			Cell cellB = row.createCell(cellCount++);
			cellB.setCellValue(dataRow.claSS);
			Cell cellC = row.createCell(cellCount++);
			cellC.setCellValue(dataRow.method);
			Cell cellD = row.createCell(cellCount++);
			cellD.setCellValue(dataRow.dbTable_st);
			Cell cellE = row.createCell(cellCount++);
			cellE.setCellValue(dataRow.read);
			//checkCell(cellE);
			Cell cellF = row.createCell(cellCount++);
			cellF.setCellValue(dataRow.insert);
			//checkCell(cellF);
			Cell cellG = row.createCell(cellCount++);
			cellG.setCellValue(dataRow.update);
			//checkCell(cellG);
			Cell cellH = row.createCell(cellCount++);
			cellH.setCellValue(dataRow.delete);
			//checkCell(cellH);
			Cell cellI = row.createCell(cellCount++);
			cellI.setCellValue(dataRow.dbTable_sc);
			Cell cellJ = row.createCell(cellCount++);
			cellJ.setCellValue(dataRow.dbColumn);
			Cell cellK = row.createCell(cellCount++);
			cellK.setCellValue(dataRow.enabled);
			//checkCell(cellK);
			
			
			log.info("[" + rowCount + "]" + dataRow.toString());
			rowCount++;
		}
		format();
	}
	
	private void addHeaders() {
		
		log.info("in SecurityMethodDetail.addHeaders()");
		
		Vector vec = new SecurityMethodDetail_Data().v_header();
		Row row = sheet.createRow(rowCount++);
		int cellCount_H = 0;
		numColumns = vec.size();
		
		Iterator column = vec.iterator();
		
		while( column.hasNext() ) {
			String data = (String)column.next();
			Cell cell = row.createCell(cellCount_H);
			cell.setCellValue(data);
			
			cellCount_H++;
		}
	}
	
	
}
