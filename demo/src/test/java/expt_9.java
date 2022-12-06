import java.lang.*;
import java.util.*;
import java.io.*;
import java.lang.*;
import java.text.DecimalFormat;
import java.util.*;
import java.io.*;

import org.apache.commons.math3.util.Precision;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class expt_9 {
    public static float get_euclidean_distance(float x1, float y1, float x2, float y2) {
        float x_diff = x1 - x2;
        float y_diff = y1 - y2;
        double e = Math.sqrt(Double.valueOf(x_diff * x_diff) + Double.valueOf(y_diff * y_diff));
        return (float) e;
    }

    public static void main(String[] args) throws Exception {

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
        try {

            // Reading file from local directory
            File excel = new File("./demo/Experiment 9/data_file.xlsx");
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
                            column_header.add("" + st1);
                            data_file.put(st1, "");
                            System.out.print(s + "\t");
                            break;
                        case NUMERIC:
                            Integer k = (int) (cell.getNumericCellValue());
                            // st1 = Integer.toString(k);
                            // data_global = data_global + st1 + " ";
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

        //////////////////////////////////////

        int row_count = data_file.size();
        float[][] pts = new float[row_count + 1][row_count + 1];
        int cnt_of_dist_pt = 0;
        float center_x = 0;
        float center_y = 0;
        for (Map.Entry<String, String> e : data_file.entrySet()) {
            String[] tmp = e.getValue().split(" ");
            pts[cnt_of_dist_pt][0] = Float.parseFloat(tmp[0]);
            pts[cnt_of_dist_pt][1] = Float.parseFloat(tmp[1]);
            center_x += Float.parseFloat(tmp[0]);
            center_y += Float.parseFloat(tmp[1]);
            cnt_of_dist_pt++;
        }
        center_x = (center_x / cnt_of_dist_pt);
        center_y = (center_y / cnt_of_dist_pt);
        pts[cnt_of_dist_pt][0] = center_x;
        pts[cnt_of_dist_pt][1] = center_y;
        cnt_of_dist_pt++;
        String c_x = Float.toString(center_x);
        String c_y = Float.toString(center_y);

        data_file.put("Center", c_x + " " + c_y);
        int cnt = 0;
        column_header.add("Center");
        float[][] distance_matrix = new float[cnt_of_dist_pt][cnt_of_dist_pt];
        Object[] obj = new Object[column_header.size() + 1];
        obj[0] = " ";
        for (int i = 0; i < column_header.size(); i++) {
            obj[i + 1] = column_header.get(i);
        }
        data.put(Integer.toString(++x1), obj);
        int i = 0;
        for (Map.Entry<String, String> e : data_file.entrySet()) {

            String tmp_st = e.getValue();
            String[] tmp = tmp_st.split(" ");
            float[] pt = new float[2];
            pt[0] = Float.parseFloat(tmp[0]);
            pt[1] = Float.parseFloat(tmp[1]);

            obj = new Object[column_header.size() + 1];
            obj[0] = column_header.get(i);
            for (int j = 0; j < cnt_of_dist_pt; j++) {
                distance_matrix[i][j] = get_euclidean_distance(pt[0], pt[1], pts[j][0], pts[j][1]);
                obj[j + 1] = distance_matrix[i][j];
            }
            data.put(Integer.toString(++x1), obj);
            i++;
        }

        /////////////////////////////

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
                    new File("./demo/Experiment 9/output_file.xlsx"));
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
