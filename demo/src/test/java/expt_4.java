import java.lang.*;
import java.text.DecimalFormat;
import java.util.*;
import java.io.*;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

//T and D weight
public class expt_4 {
    public static void main(String[] args) throws IOException {
        // ----------------------------------------------------------------------------------------------------
        XSSFWorkbook workbook = new XSSFWorkbook();

        // Creating a blank Excel sheet
        XSSFSheet sheet1 = workbook.createSheet("student Details");

        // Creating an empty TreeMap of string and Object][]
        // type
        Map<String, Object[]> data = new LinkedHashMap<String, Object[]>();
        int l = 0;
        int x1 = 0;
        int row_count = 0;
        int column_count = 0;
        int row_total1 = 0;
        int row_total2 = 0;
        int column_total1 = 0;
        int column_total2 = 0;
        int[][] input_data = new int[2][2];
        String[] str = new String[5];

        try {

            // Reading file from local directory
            File excel = new File("./demo/Experiment 4/data_file.xlsx");
            FileInputStream fis = new FileInputStream(excel);
            XSSFWorkbook book = new XSSFWorkbook(fis);
            XSSFSheet sheet = book.getSheetAt(0);
            Iterator<Row> itr = sheet.iterator();
            System.out.println("---------The data set input from file-------");
            while (itr.hasNext()) {
                String st1 = "";
                Integer k = 0;
                column_count = 0;
                Row row = itr.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {

                    Cell cell = cellIterator.next();
                    switch (cell.getCellType()) {
                        case STRING:
                            String s = cell.getStringCellValue();
                            st1 = s;
                            str[l] = st1;
                            l++;
                            System.out.print(s + "\t");
                            break;
                        case NUMERIC:
                            k = (int) (cell.getNumericCellValue());
                            input_data[row_count - 1][column_count - 1] = k;
                            System.out.print(k + "\t");
                            break;
                        case BOOLEAN:
                            Boolean b = cell.getBooleanCellValue();
                            st1 = Boolean.toString(b);
                            System.out.print(b + "\t");
                            break;

                    }
                    if (row_count == 1)
                        row_total1 += k;
                    if (row_count == 2)
                        row_total2 += k;
                    if (column_count == 1)
                        column_total1 += k;
                    if (column_count == 2)
                        column_total2 += k;

                    column_count++;
                }
                row_count++;

                System.out.println("");

            }

        }

        // Catch block to handle exceptions
        catch (Exception e) {

            // Display the exception along with line number
            // using printStackTrace() method
            e.printStackTrace();
        }

        DecimalFormat df = new DecimalFormat("#.##");
        // CellStyle.fo
        data.put(Integer.toString(++x1),
                new Object[] { str[0], str[1], "t_weight</b>", "d_weight", str[2], "t_weight", "d_weight",
                        "Total",
                        "t_weight", "d_weight" });

        for (int i = 0; i < 2; i++) {
            if (i == 0) {

                data.put(Integer.toString(++x1),
                        new Object[] { str[3], input_data[0][0],
                                df.format((Float.valueOf(input_data[0][0]) / Float.valueOf(row_total1)) * 100),
                                df.format((Float.valueOf(input_data[0][0]) / Float.valueOf(column_total1)) * 100),
                                input_data[0][1],
                                df.format((Float.valueOf(input_data[0][1]) / Float.valueOf(row_total2)) * 100),
                                df.format((Float.valueOf(input_data[0][1]) / Float.valueOf(column_total2)) * 100),
                                row_total1,
                                df.format((Float.valueOf(row_total1) / Float.valueOf(row_total1)) * 100),
                                df.format(
                                        (Float.valueOf(row_total1) / Float.valueOf(row_total1 + row_total2)) * 100) });
            }

            else
                data.put(Integer.toString(++x1),
                        new Object[] { str[4], input_data[1][0],
                                df.format((Float.valueOf(input_data[1][0]) / Float.valueOf(row_total1)) * 100),
                                df.format((Float.valueOf(input_data[1][0]) / Float.valueOf(column_total1)) * 100),
                                input_data[1][1],
                                df.format((Float.valueOf(input_data[1][1]) / Float.valueOf(row_total2)) * 100),
                                df.format((Float.valueOf(input_data[1][1]) / Float.valueOf(column_total2)) * 100),
                                row_total2,
                                df.format((Float.valueOf(row_total2) / Float.valueOf(row_total2)) * 100),
                                df.format(
                                        (Float.valueOf(row_total2) / Float.valueOf(row_total1 + row_total2)) * 100) });
            // System.out.print(input_data[i][j] + " ");
        }
        // data.put(Integer.toString(++x1), new Object[] { "", "" });

        data.put(Integer.toString(++x1),
                new Object[] { "Total", column_total1,
                        df.format((Float.valueOf(column_total1) / Float.valueOf(row_total1 + row_total2)) * 100),
                        df.format((Float.valueOf(column_total1) / Float.valueOf(column_total1)) * 100),
                        column_total2,
                        df.format((Float.valueOf(column_total2) / Float.valueOf(row_total2 + row_total1)) * 100),
                        df.format((Float.valueOf(column_total2) / Float.valueOf(column_total2)) * 100),
                        row_total2 + row_total1,
                        df.format((Float.valueOf(row_total2 + row_total1) / Float.valueOf(row_total2 + row_total1))
                                * 100),
                        df.format((Float.valueOf(column_total1 + column_total2)
                                / Float.valueOf(column_total1 + column_total2))
                                * 100) });

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
                    new File("./demo/Experiment 4/output_file.xlsx"));
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
