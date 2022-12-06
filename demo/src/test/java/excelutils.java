// Java Program to Illustrate Writing
// Data to Excel File using Apache POI

// Import statements
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

// Main class
public class excelutils {

	// Main driver method
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		try {

			// Reading file from local directory
			File excel = new File("gfgcontribute.xlsx");
			FileInputStream fis = new FileInputStream(excel);
			XSSFWorkbook book = new XSSFWorkbook(fis);
			XSSFSheet sheet = book.getSheetAt(0);
			Iterator<Row> itr = sheet.iterator();
			while (itr.hasNext()) {
				Row row = itr.next();
				Iterator<Cell> cellIterator = row.cellIterator();
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					switch (cell.getCellType()) {
						case STRING:
							System.out.print(cell.getStringCellValue() + "\t");
							break;
						case NUMERIC:
							System.out.print(cell.getNumericCellValue() + "\t");
							break;
						case BOOLEAN:
							System.out.print(cell.getBooleanCellValue() + "\t");
							break;

					}
				}
				System.out.println("");

			}

		}

		// Catch block to handle exceptions
		catch (Exception e) {

			// Display the exception along with line number
			// using printStackTrace() method
			e.printStackTrace();
		}

		// Blank workbook
		XSSFWorkbook workbook = new XSSFWorkbook();

		// Creating a blank Excel sheet
		XSSFSheet sheet = workbook.createSheet("student Details");

		// Creating an empty TreeMap of string and Object][]
		// type
		Map<String, Object[]> data = new TreeMap<String, Object[]>();

		// Writing data to Object[]
		// using put() method
		data.put("1",
				new Object[] { "ID", "NAME", "LASTNAME" });
		data.put("2",
				new Object[] { 1, "Pankaj", "Kumar" });
		data.put("3",
				new Object[] { 2, "Prakashni", "Yadav" });
		data.put("4", new Object[] { 3, "Ayan", "Mondal" });
		data.put("5", new Object[] { 4, "Virat", "kohli" });

		// Iterating over data and writing it to sheet
		Set<String> keyset = data.keySet();

		int rownum = 0;

		for (String key : keyset) {

			// Creating a new row in the sheet
			Row row = sheet.createRow(rownum++);

			Object[] objArr = data.get(key);

			int cellnum = 0;

			for (Object obj : objArr) {

				// This line creates a cell in the next
				// column of that row
				Cell cell = row.createCell(cellnum++);

				if (obj instanceof String)
					cell.setCellValue((String) obj);

				else if (obj instanceof Integer)
					cell.setCellValue((Integer) obj);
			}
		}

		// Try block to check for exceptions
		try {

			// Writing the workbook
			FileOutputStream out = new FileOutputStream(
					new File("gfgcontribute.xlsx"));
			workbook.write(out);

			// Closing file output connections
			out.close();

			// Console message for successful execution of
			// program
			System.out.println(
					"gfgcontribute.xlsx written successfully on disk.");
		}

		// Catch block to handle exceptions
		catch (Exception e) {

			// Display exceptions along with line number
			// using printStackTrace() method
			e.printStackTrace();
		}
	}
}
