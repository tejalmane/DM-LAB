import java.lang.*;
import java.text.DecimalFormat;
import java.util.*;
import java.io.*;

import org.apache.commons.math3.util.Precision;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

// normaliation of data
public class expt_2_z_score_normalization {
    public static void main(String[] args) throws Exception {
        // ----------------------------------------------------------------------------------------------------
        XSSFWorkbook workbook = new XSSFWorkbook();

        // Creating a blank Excel sheet
        XSSFSheet sheet1 = workbook.createSheet("student Details");

        // Creating an empty TreeMap of string and Object][]
        // type
        Map<String, Object[]> data = new LinkedHashMap<String, Object[]>();
        int l = 0;
        int x1 = 0;
        String data_global = "";
        try {

            // Reading file from local directory
            File excel = new File("./demo/Experiment 2/z_score_normalization/data_file(z_score_normaliztion).xlsx");
            FileInputStream fis = new FileInputStream(excel);
            XSSFWorkbook book = new XSSFWorkbook(fis);
            XSSFSheet sheet = book.getSheetAt(0);
            Iterator<Row> itr = sheet.iterator();
            System.out.println("---------The data set input from file-------");

            while (itr.hasNext()) {
                String st1 = "";
                Row row = itr.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    switch (cell.getCellType()) {
                        case STRING:
                            String s = cell.getStringCellValue();
                            st1 = s;
                            System.out.print(s + "\t");
                            break;
                        case NUMERIC:
                            Integer k = (int) (cell.getNumericCellValue());
                            st1 = Integer.toString(k);
                            data_global = data_global + st1 + " ";
                            System.out.print(k + "\t");
                            break;
                        case BOOLEAN:
                            Boolean b = cell.getBooleanCellValue();
                            st1 = Boolean.toString(b);
                            System.out.print(b + "\t");
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

        String[] fields = data_global.split(" "); // Splits at the space
        int i = 0;
        int[] arr = new int[fields.length];
        for (String str : fields) {
            int x = Integer.parseInt(str); // prints out name
            arr[i++] = x;
        }
        System.out.println("---------The data set input from file-------");
        for (int a : arr) {
            System.out.print(a + " ");
        }
        System.out.println();
        System.out.println("-------------Five Point Summary(Box Plot)--------");
        Arrays.sort(arr);
        System.out.println("Sorted Data");
        int sum = 0;
        for (int a : arr) {
            System.out.print(a + " ");
            sum += a;
        }
        int n = arr.length;
        double mean = Double.valueOf((double) sum / n);
        double sd_sum = 0;
        for (int j = 0; j < n; j++) {
            sd_sum += (arr[j] - mean) * (arr[j] - mean);
        }
        double std_dev = Math.sqrt(sd_sum / n);
        DecimalFormat df = new DecimalFormat("#.##");
        std_dev = Precision.round(std_dev, 2);
        data.put(Integer.toString(++x1),
                new Object[] { "Marks", "Z-score normalized marks " });
        //
        Double normalized_marks = 0d;
        for (int j = 0; j < n; j++) {
            Double n1 = Double.valueOf(arr[j] - mean);
            // Double n2 = Double.valueOf(old_max - old_min);
            normalized_marks = Double.valueOf(n1 / std_dev);
            // Precision.round(normalized_marks, 2);
            data.put(Integer.toString(++x1),
                    new Object[] { arr[j], df.format(normalized_marks) });

        }
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

                else if (obj instanceof Float)
                    cell.setCellValue((Float) obj);
            }
        }

        // Try block to check for exceptions
        try {

            // Writing the workbook
            FileOutputStream out = new FileOutputStream(
                    new File("./demo/Experiment 2/z_score_normalization/output_file(z_score_normalization).xlsx"));
            workbook.write(out);

            // Closing file output connections
            out.close();

            // Console message for successful execution of
            // program
            System.out.println(
                    "\n output_file.xlsx written successfully on disk.\n");
        }

        // Catch block to handle exceptions
        catch (Exception e) {

            // Display exceptions along with line number
            // using printStackTrace() method
            e.printStackTrace();
        }
    }
}
