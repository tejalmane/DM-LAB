import java.lang.*;
import java.util.*;
import java.io.*;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

//summary of given dataset
class expt_5 {

    static int cal_median(int arr[], int l, int r) {
        int len = r - l + 1;
        int median = 0;
        if (len % 2 == 1)
            median = arr[(l + len / 2)];
        else {
            int median1 = arr[l + len / 2];
            int median2 = arr[(l + len / 2) - 1];
            median = (median1 + median2) / 2;
        }
        return median;
    }

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
            File excel = new File("./demo/Experiment 5/data_file.xlsx");
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
                data.put(Integer.toString(++x1),
                        new Object[] { st1 });

            }

        }

        // Catch block to handle exceptions
        catch (Exception e) {

            // Display the exception along with line number
            // using printStackTrace() method
            e.printStackTrace();
        }

        // -----------------------------------------------------------------------------------------------------
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
        for (int a : arr) {
            System.out.print(a + " ");
        }
        System.out.println();
        int n = arr.length;
        // System.out.println("1.MAX VALUE--------->" + arr[n - 1]);
        // System.out.println("1.MAX VALUE--------->" + arr[n - 1]);

        // System.out.println("2.MIN VALUE--------->" + arr[0]);
        // System.out.println("2.MIN VALUE--------->" + arr[0]);

        // System.out.println("3.MEDIAN---------->" + arr[(n + 1) / 2 - 1]);
        // System.out.println("3.MEDIAN---------->" + cal_median(arr, 0, arr.length -
        // 1));

        // System.out.println("4.QUARTILE 1--------->" + arr[(n + 1) / 4 - 1]);
        int q1, q3, median, IQ;
        double lowerWhisers, upperWhisers;
        median = cal_median(arr, 0, arr.length - 1);
        int q1_term = Math.round(0.25f * (n + 1));
        int q3_term = Math.round(0.75f * (n + 1));

        // if (arr.length % 2 == 0) {
        // q1 = cal_median(arr, 0, (arr.length / 2) - 1);
        // // System.out.println("4.QUARTILE 1--------->" + q1);
        // q3 = cal_median(arr, arr.length / 2, arr.length - 1);
        // // System.out.println("5.QUARTILE 3--------->" + q3);
        // } else {
        // q1 = cal_median(arr, 0, (n / 2) - 1);
        // // System.out.println("4.QUARTILE 1--------->" + q1);
        // q3 = cal_median(arr, (n / 2) + 1, n - 1);
        // // System.out.println("5.QUARTILE 3--------->" + q3);
        // }
        q1 = arr[q1_term - 1];
        q3 = arr[q3_term - 1];
        IQ = q3 - q1;
        lowerWhisers = q1 - 1.5 * IQ;
        upperWhisers = q3 + 1.5 * IQ;
        System.out.println("1.Lowerwhisers\t--------->\t" + lowerWhisers);
        System.out.println("2.MIN VALUE\t--------->\t" + arr[0]);
        System.out.println("3.LOWER QUARTILE--------->\t" + q1);
        System.out.println("4.MEDIAN\t--------->\t" + median);
        System.out.println("5.UPPER QUARTILE--------->\t" + q3);
        System.out.println("6.MAX VALUE\t--------->\t" + arr[n - 1]);
        System.out.println("7.Upperwhisers\t--------->\t" + upperWhisers);

        data.put(Integer.toString(++x1),
                new Object[] { " ", " " });

        data.put(Integer.toString(++x1),
                new Object[] { "Lowerwhisers", "MIN VALUE", "LOWER QUARTILE", "MEDIAN", "UPPER QUARTILE", "MAX VALUE",
                        "Upperwhisers" });

        data.put(Integer.toString(++x1),
                new Object[] { Double.toString(lowerWhisers), Integer.toString(arr[0]), Integer.toString(q1),
                        Integer.toString(median), Integer.toString(q3), Integer.toString(arr[n - 1]),
                        Double.toString(upperWhisers) });

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
                    new File("./demo/Experiment 5/output_file.xlsx"));
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