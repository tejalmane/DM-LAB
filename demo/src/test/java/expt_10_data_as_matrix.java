import java.lang.*;
import java.text.DecimalFormat;
import java.util.*;
import java.io.*;

import org.apache.commons.math3.util.Precision;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class expt_10_data_as_matrix {
    public static float get_euclidean_distance(float x1, float y1, float x2, float y2) {
        float x_diff = x1 - x2;
        float y_diff = y1 - y2;
        double e = Math.sqrt(Double.valueOf(x_diff * x_diff) + Double.valueOf(y_diff * y_diff));
        return (float) e;
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
        ArrayList<String> column_header = new ArrayList<>();
        LinkedHashMap<String, String> data_file = new LinkedHashMap<>();
        int row_count = 0;
        float[][] pts;
        int cnt_of_dist_pt = 0;
        float[][] distance_matrix = new float[cnt_of_dist_pt][cnt_of_dist_pt];

        try {

            // Reading file from local directory
            File excel = new File("./demo/Experiment 10/data_file(matrix_format).xlsx");
            FileInputStream fis = new FileInputStream(excel);
            XSSFWorkbook book = new XSSFWorkbook(fis);
            XSSFSheet sheet = book.getSheetAt(0);
            Iterator<Row> itr = sheet.iterator();
            System.out.println("---------The data set input from file-------");
            int first_row = 0;
            System.out.print(" " + "\t");
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
                            if (!st1.isEmpty() && first_row == 0) {
                                column_header.add("" + st1);

                            }
                            data_file.put(st1, "");
                            System.out.print(s + "\t");
                            break;
                        case NUMERIC:
                            Float k = (float) (cell.getNumericCellValue());
                            // st1 = Integer.toString(k);
                            // data_global = data_globFal + st1 + " ";
                            // String val = data_file.getOrDefault(st1, "")
                            data_file.put(st1, data_file.getOrDefault(st1, "") + k + " ");
                            System.out.print(k + "\t");
                            break;
                        case BOOLEAN:
                            Boolean b = cell.getBooleanCellValue();
                            // st1 = Boolean.toString(b);
                            System.out.print(b + "\t");
                            break;

                    }
                }
                System.out.println("");
                if (first_row != 0) {
                    String[] tmp = data_file.get(st1).split(" ");
                    for (int i = 0; i < column_header.size(); i++) {
                        distance_matrix[cnt_of_dist_pt][i] = Float.parseFloat(tmp[i]);
                    }
                    cnt_of_dist_pt++;
                } else {
                    row_count = column_header.size();
                    pts = new float[row_count][row_count];
                    distance_matrix = new float[column_header.size()][column_header.size()];

                }
                first_row++;
                // data.put(Integer.toString(++x1),
                // new Object[] { st1 });

            }

        }

        // Catch block to handle exceptions
        catch (Exception e) {

            // Display the exception along with line number
            // using printStackTrace() method
            e.printStackTrace();
        }

        // int row_count = data_file.size();
        // float[][] pts = new float[row_count][row_count];
        // int cnt_of_dist_pt = 0;
        // for (Map.Entry<String, String> e : data_file.entrySet()) {
        //     String[] tmp = e.getValue().split(" ");
        //     pts[cnt_of_dist_pt][0] = Float.parseFloat(tmp[0]);
        //     pts[cnt_of_dist_pt][1] = Float.parseFloat(tmp[1]);
        //     cnt_of_dist_pt++;
        // }
        int cnt = 0;

        Object[] obj = new Object[column_header.size() + 1];
        obj[0] = "O";
        for (int i = 0; i < column_header.size(); i++) {
            obj[i + 1] = column_header.get(i);
        }
        data.put(Integer.toString(++x1), obj);
        for (int m = 0; m < cnt_of_dist_pt - 1; m++) {
            // data = new LinkedHashMap<String, Object[]>();
            obj = new Object[column_header.size() + 1];
            obj[0] = column_header.get(m);
            for (int j = 0; j < cnt_of_dist_pt - 1; j++) {
                // distance_matrix[i][j] = get_euclidean_distance(pt[0], pt[1], pts[j][0],
                // pts[j][1]);
                obj[j + 1] = distance_matrix[m][j];
            }
            data.put(Integer.toString(++x1), obj);

        }
        int i = 0;
        data.put(Integer.toString(++x1), new Object[] { "" });
        data.put(Integer.toString(++x1), new Object[] { "" });
        data.put(Integer.toString(++x1), new Object[] { "" });

        // }

        int z = cnt_of_dist_pt;

        while (z > 2) {
            float min = Integer.MAX_VALUE;
            int row = 0;
            int column = 0;
            for (int j = 1; j < z; j++) {
                for (int k = 0; k < j; k++) {
                    if (min > distance_matrix[j][k]) {
                        min = distance_matrix[j][k];
                        row = j;
                        column = k;
                    }

                }
            }
            if (row > column) {
                int tmp = row;
                row = column;
                column = tmp;
            }

            for (int j = 0; j < z; j++) {
                for (int k = row + j; k < z - 1; k++) {
                    if (k == row) {
                        if (distance_matrix[j][k] > distance_matrix[j][column]) {
                            distance_matrix[j][k] = distance_matrix[j][column];
                        }
                    } else if (k >= column && (j!=k)) {
                        distance_matrix[j][k] = distance_matrix[j][k + 1];
                    }
                }
            }
            for (int j = row; j < z - 1; j++) {
                // if (j == row) {
                // if (distance_matrix[i][k] > distance_matrix[i][column]) {
                // distance_matrix[i][k]=distance_matrix[i][column];
                // }
                // } else {
                // distance_matrix[i][k]=distance_matrix[i][k+1];
                // }
                for (int k = 0; k < z; k++) {
                    if (j == row) {
                        if (distance_matrix[j][k] > distance_matrix[row][k]) {
                            distance_matrix[j][k] = distance_matrix[row][k];
                        }
                    } else if (j >= column && (j!=k)) {
                        distance_matrix[j][k] = distance_matrix[j + 1][k];
                    }
                }
            }

            String get_row_head = column_header.get(row) + column_header.get(column);
            // column_header.replaceA(row, get_row_head);
            // column_header.remove(column_header.get(row+1));
            // column_header.remove(column_header.get(column-1));
            obj = new Object[z + 1];
            obj[0] = "O";
            for (int c = 0; c < z - 1; c++) {
                if (c == row) {
                    obj[c + 1] = get_row_head;
                    column_header.remove(row);
                    column_header.add(row, get_row_head);
                    column_header.remove(column);

                } else
                    obj[c + 1] = column_header.get(c);
            }
            data.put(Integer.toString(++x1), obj);

            // ---------------------------------
            // write distance matrix to excel file
            // ---------------------------------
            for (int m = 0; m < z - 1; m++) {
                // data = new LinkedHashMap<String, Object[]>();
                obj = new Object[column_header.size() + 1];
                obj[0] = column_header.get(m);
                for (int j = 0; j < z - 1; j++) {
                    // distance_matrix[i][j] = get_euclidean_distance(pt[0], pt[1], pts[j][0],
                    // pts[j][1]);
                    obj[j + 1] = distance_matrix[m][j];
                }
                data.put(Integer.toString(++x1), obj);

            }
            data.put(Integer.toString(++x1), new Object[] { "" });
            data.put(Integer.toString(++x1), new Object[] { "" });
            data.put(Integer.toString(++x1), new Object[] { "" });

            // ---------------------------------
            z--;

        }

        Set<String> keyset = data.keySet();
        int rownum = 0;

        for (String key : keyset) {

            // Creating a new row in the sheet
            Row row = sheet1.createRow(rownum++);

            Object[] objArr = data.get(key);

            int cellnum = 0;

            for (Object obj1 : objArr) {

                // This line creates a cell in the next
                // column of that row
                Cell cell = row.createCell(cellnum++);

                if (obj1 instanceof String)
                    cell.setCellValue((String) obj1);

                else if (obj1 instanceof Integer)
                    cell.setCellValue((Integer) obj1);

                else if (obj1 instanceof Float)
                    cell.setCellValue((Float) obj1);
            }
        }

        // Try block to check for exceptions
        try {

            // Writing the workbook
            FileOutputStream out = new FileOutputStream(
                    new File("./demo/Experiment 10/output_file(matrix_format).xlsx"));
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
