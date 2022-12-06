import java.lang.*;
import java.util.*;
import java.io.*;

class expt_3 {
    public static Formatter fmt = new Formatter();
    
    public static List<String> list = new ArrayList<String>();

    public static void main(String[] args) throws Exception {
        // BufferedReader br = new BufferedReader(new FileReader("D:/DM lab/demo/src/test/java/data1.csv"));
        BufferedReader br = new BufferedReader(new FileReader("./demo/Experiment 3/data_file1.csv"));
        ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
        String line = "";
        HashSet<String> hs = new HashSet<>();

        while ((line = br.readLine()) != null) {
            String str[] = line.split(",");
            if (!str[str.length - 1].equalsIgnoreCase("class"))
                hs.add(str[str.length - 1]);
            ArrayList<String> temp = new ArrayList<>();
            for (String s : str)
                temp.add(s);
            data.add(temp);
        }
        list = new ArrayList<String>(hs);
        System.out.println(hs);
        int class_type1 = 0, class_type2 = 0;
        for (int i = 1; i < data.size(); i++) {
            if (data.get(i).get(data.get(i).size() - 1).equals(list.get(0)))// considering target value as 5th attribute
            class_type1++;
            else
            class_type2++;
        }
        double totRecord = data.size()-1;
        double infoGain = -((1.0 * class_type1 / totRecord) * (Math.log(1.0 * class_type1 / totRecord)) / Math.log(2))
                - ((1.0 * class_type2 / totRecord) * (Math.log(1.0 * class_type2 / totRecord) / Math.log(2)));

        PrintWriter out = new PrintWriter(new File("./demo/Experiment 3/output_file.csv"));
        out.printf("%s,%s", "Attribute", "Info_Gain");

        fmt.format("%15s\t %15s\n", "Attribute", "Info_Gain");
        out.printf("\n");

        for (int i = 0; i < data.get(0).size() ; i++) {
            double result = calulate_infoGain_giniIndex(i, infoGain, data);
            out.printf("%s,%.3f\n", data.get(0).get(i), result);
        }
        System.out.println(fmt);

        out.close();
    }

    private static double calulate_infoGain_giniIndex(int i, double infoGain, ArrayList<ArrayList<String>> data) {
        Set<String> attribute = new HashSet<>();
        for(int l=1;l<data.size();l++){
            ArrayList<String> x = data.get(l);
            attribute.add(x.get(i));
        }
        Map<String, double[]> total = new HashMap<>();

        double result ;

        for (String x : attribute) {
            total.put(x, new double[2]);
        }
        for(int l=1;l<data.size();l++){
            ArrayList<String> x = data.get(l);
            if (x.get(data.get(i).size() - 1).equals((list.get(0))))
                total.get(x.get(i))[0]++;
            else
                total.get(x.get(i))[1]++;


        }

        //loop for calculating total entropy of an attribute
        double totalE = 0.0;
        for (Map.Entry<String, double[]> x : total.entrySet()) {
            double total1 = x.getValue()[0] + x.getValue()[1];
            if (x.getValue()[0] == 0 || x.getValue()[1] == 0)
                continue;
            double temp = -((1.0 * x.getValue()[0] / total1) * (Math.log(1.0 * x.getValue()[0] / total1)) / Math.log(2))
                    - ((1.0 * x.getValue()[1] / total1) * (Math.log(1.0 * x.getValue()[1] / total1) / Math.log(2)));
            double x1 = total1 / (data.size()-1);
            totalE = totalE+ (x1* temp);
        }

        double gain = infoGain - totalE;

        result = gain;
        fmt.format("%14s\t %14s\n", data.get(0).get(i), gain);
        return result;
    }

}
