import java.io.*;
import java.util.*;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

//correlation analysis
public class expt_8 {
    public static void main(String[] args) throws IOException {
        // Write output in file
        String a_global = "";
        String b_global = "";

        XSSFWorkbook workbook = new XSSFWorkbook();

        // Creating a blank Excel sheet
        XSSFSheet sheet1 = workbook.createSheet("student Details");

        // Creating an empty TreeMap of string and Object][]
        // type
        Map<String, Object[]> data = new LinkedHashMap<String, Object[]>();
        int l = 0;
        int x1 = 0;
        try {

            // Reading file from local directory
            File excel = new File("./demo/Experiment 8/data_file.xlsx");
            FileInputStream fis = new FileInputStream(excel);
            XSSFWorkbook book = new XSSFWorkbook(fis);
            XSSFSheet sheet = book.getSheetAt(0);
            Iterator<Row> itr = sheet.iterator();
            System.out.println("---------The data set input from file-------");

            while (itr.hasNext()) {
                String st1 = "";
                String st2 = "";
                Row row = itr.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    switch (cell.getCellType()) {
                        case STRING:
                            String s = cell.getStringCellValue();
                            if (l % 2 == 0) {
                                st1 = s;
                                l++;
                            } else {
                                st2 = s;
                                l++;

                            }
                            System.out.print(s + "\t");
                            break;
                        case NUMERIC:
                            Integer k = (int) (cell.getNumericCellValue());
                            if (l % 2 == 0) {
                                st1 = Integer.toString(k);
                                a_global += st1;
                                a_global += " ";
                                l++;
                            } else {
                                st2 = Integer.toString(k);
                                b_global += st2;
                                b_global += " ";
                                l++;

                            }
                            System.out.print(k + "\t");
                            break;
                        case BOOLEAN:
                            Boolean b = cell.getBooleanCellValue();
                            if (l % 2 == 0) {
                                st1 = Boolean.toString(b);
                                a_global += st1;
                                a_global += " ";
                                l++;
                            } else {
                                st2 = Boolean.toString(b);
                                b_global += st2;
                                b_global += " ";
                                l++;

                            }
                            System.out.print(b + "\t");
                            break;

                    }
                }
                System.out.println("");
                data.put(Integer.toString(++x1),
                        new Object[] { st1, st2 });

            }

        }

        // Catch block to handle exceptions
        catch (Exception e) {

            // Display the exception along with line number
            // using printStackTrace() method
            e.printStackTrace();
        }

        String[] fields_A = a_global.split(" "); // Splits at the space
        int i = 0;
        int[] arr_A = new int[fields_A.length];
        for (String str : fields_A) {
            int x = Integer.parseInt(str); // prints out name
            arr_A[i++] = x;
        }
        // for (int a : arr_A) {
        // System.out.print(a + " ");
        // }

        String[] fields_B = b_global.split(" "); // Splits at the space
        int j = 0;
        int[] arr_B = new int[fields_B.length];
        for (String str : fields_B) {
            int x = Integer.parseInt(str); // prints out name
            arr_B[j++] = x;
        }
        // System.out.println("");
        // for (int a : arr_B) {
        // System.out.print(a + " ");
        // }
        int a_b = 0;
        int a = 0;
        int b = 0;
        for (int z = 0; z < arr_A.length; z++) {
            if (arr_A[z] == 1 && arr_B[z] == 1)
                a_b++;
            if (arr_A[z] == 1)
                a++;
            if (arr_B[z] == 1)
                b++;
        }
        float c = a * b;
        float corr = (a_b / c);
        System.out.println("\n-------------------------------------");
        System.out.println("Correlation between A and B : " + corr);
        System.out.println("-------------------------------------");

        data.put(Integer.toString(++x1),
                new Object[] { " ", " " });
        data.put(Integer.toString(++x1),
                new Object[] { "Correlation Between A and B", Float.toString(corr) });

        Set<String> keyset = data.keySet();
        int rownum = 0;

        for (String key : keyset) {

            // Creating a new row in the sheet
            Row row = sheet1.createRow(rownum++);

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
                    new File("./demo/Experiment 8/output_file.xlsx"));
            workbook.write(out);

            // Closing file output connections
            out.close();

            // Console message for successful execution of
            // program
            System.out.println(
                    "output_file.xlsx written successfully on disk.");
        }

        // Catch block to handle exceptions
        catch (Exception e) {

            // Display exceptions along with line number
            // using printStackTrace() method
            e.printStackTrace();
        }

    }
}
