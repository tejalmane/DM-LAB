import java.io.IOException;
import java.lang.*;
import java.text.DecimalFormat;
import java.util.*;
import java.io.*;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class expt_12 {
    public static void main(String[] args) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();

        // Creating a blank Excel sheet
        XSSFSheet sheet1 = workbook.createSheet("student Details");

        // Creating an empty TreeMap of string and Object][]
        // type
        Map<String, Object[]> data = new LinkedHashMap<String, Object[]>();
        List<String> transactions = new ArrayList<String>();
        String[] str = new String[5];
        try {

            // Reading file from local directory
            File excel = new File("./demo/Experiment 12/data_file.xlsx");
            FileInputStream fis = new FileInputStream(excel);
            XSSFWorkbook book = new XSSFWorkbook(fis);
            XSSFSheet sheet = book.getSheetAt(0);
            Iterator<Row> itr = sheet.iterator();
            System.out.println("---------The data set input from file-------");
            while (itr.hasNext()) {
                String st1 = "";
                String c = "";
                Integer k = 0;
                Row row = itr.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    switch (cell.getCellType()) {
                        case STRING:
                            String s = cell.getStringCellValue();
                            st1 = s;
                            break;
                        case NUMERIC:
                            k = (int) (cell.getNumericCellValue());
                            st1 = Integer.toString(k);
                            break;
                        case BOOLEAN:
                            Boolean b = cell.getBooleanCellValue();
                            st1 = Boolean.toString(b);
                            ;
                            break;

                    }
                    c += st1;
                    c += " ";
                }
                transactions.add(c);
                // System.out.println(c);

            }

        }

        // Catch block to handle exceptions
        catch (Exception e) {

            // Display the exception along with line number
            // using printStackTrace() method
            e.printStackTrace();
        }

        LinkedHashMap<String, String> column_types = new LinkedHashMap<>();
        LinkedHashMap<String, String> itemSet = new LinkedHashMap<>();
        String values = transactions.get(0);
        String[] column_name = values.split(" ");
        for (int i = 1; i < transactions.size(); i++) {
            String val = transactions.get(i);
            String[] arr = val.split(" ");
            // int j=0;
            // if (i != 0)
            for (int j = 0; j < arr.length; j++) {
                String s = column_types.getOrDefault(column_name[j], "");
                // String[] s1 = s.split(" ");
                if (!s.contains(arr[j])) {
                    String ss = "";
                    if (!s.equals("")) {
                        ss = s + " " + arr[j];
                    } else
                        ss = arr[j];
                    column_types.put(column_name[j], ss);
                }
            }
        }
        LinkedHashMap<String, Double> z = new LinkedHashMap<>();
        String last_column = column_types.get(column_name[column_name.length - 1]);
        String[] ar = last_column.split(" ");
        int k = 0;
        int last_col_cnt1 = 0;
        int last_col_cnt2 = 0;

        for (int i = 1; i < transactions.size(); i++) {
            String s = transactions.get(i);
            String[] a = s.split(" ");
            if (a[a.length - 1].equals(ar[0]))
                last_col_cnt1 += 1;
            else if (a[a.length - 1].equals(ar[1]))
                last_col_cnt2 += 1;
        }
        for (Map.Entry<String, String> entry : column_types.entrySet()) {
            if (k != column_types.size() - 1) {
                String val = entry.getValue();
                int cnt_cls1 = 0;
                int tot1 = 0;
                int cnt_cls2 = 0;
                int tot2 = 0;
                String[] arr = val.split(" ");
                for (int i = 0; i < arr.length; i++) {
                    cnt_cls1 = 0;
                    cnt_cls2 = 0;
                    for (int j = 1; j < transactions.size(); j++) {
                        String s = transactions.get(j);
                        String[] a = s.split(" ");
                        if (a[k].equals(arr[i]) && a[a.length - 1].equals(ar[0]))
                            cnt_cls1 += 1;
                        else if (a[k].equals(arr[i]) && a[a.length - 1].equals(ar[1]))
                            cnt_cls2 += 1;
                    }
                    String tot = cnt_cls1 + " " + cnt_cls2;
                    tot1 += cnt_cls1;
                    tot2 += cnt_cls2;
                    itemSet.put(arr[i], tot);
                }
                for (int i = 0; i < arr.length; i++) {
                    String s = itemSet.get(arr[i]);
                    String[] str1 = s.split(" ");
                    z.put(arr[i] + "_" + column_name[k] + "_" + ar[0],
                            Double.valueOf(Integer.parseInt(str1[0])) / tot1);
                    z.put(arr[i] + "_" + column_name[k] + "_" + ar[1],
                            Double.valueOf(Integer.parseInt(str1[1])) / tot2);
                }
                k++;
            }
        }
        // for (Map.Entry<String, Double> entry : z.entrySet()) {
        // System.out.println(entry.getKey() + " " + entry.getValue());
        // }
        double ans1 = 1;
        double ans2 = 1;

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Required case: ");
        for (int i = 0; i < column_name.length - 1; i++) {
            System.out.print("Enter possibility of " + column_name[i] + ": ");
            String input = sc.nextLine();
            input = input.replaceAll(" ", "");

            ans1 = ans1 * z.get(input + "_" + column_name[i] + "_" + ar[0]);
            ans2 = ans2 * z.get(input + "_" + column_name[i] + "_" + ar[1]);
        }
        double probability_last_col_1 = Double.valueOf(last_col_cnt1) / (last_col_cnt1 + last_col_cnt2);

        double probability_last_col_2 = last_col_cnt2 / Double.valueOf((last_col_cnt1 + last_col_cnt2));
        ans1 *= probability_last_col_1;
        ans2 *= probability_last_col_2;

        System.out.println("Probability of " + ar[0] + " : " + ans1);
        System.out.println("Probability of " + ar[1] + " : " + ans2);
        if(ans1>ans2){
            System.out.println("-----------------------------------------------------------------------");
            System.out.println("The possibility of given case study will be "+ar[0]);
            System.out.println("-----------------------------------------------------------------------");
        }else{
            System.out.println("-----------------------------------------------------------------------");
            System.out.println("The possibility of given case study will be "+ar[1]);
            System.out.println("-----------------------------------------------------------------------");
        }

        Row row = sheet1.createRow(0);

        Cell cell = row.createCell(0);

        cell.setCellValue((String) ("Probability of " + ar[0] + " : "));
        cell = row.createCell(1);

        cell.setCellValue((Double) ans1);

        row = sheet1.createRow(1);

        cell = row.createCell(0);

        cell.setCellValue((String) ("Probability of " + ar[1] + " : "));
        cell = row.createCell(1);
        cell.setCellValue((Double) ans2);

        // Try block to check for exceptions
        try

        {

            // Writing the workbook
            FileOutputStream out = new FileOutputStream(
                    new File("./demo/Experiment 12/output_file.xlsx"));
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
