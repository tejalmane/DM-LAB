import java.io.*;
import java.util.*;

//frquent itemset from given transaction data
public class expt_6 {
    Vector<String> candidates = new Vector<String>();
    List<String> itemSet = new ArrayList<String>();
    List<String> finalFrequentItemSet = new ArrayList<>();
    HashMap<String, Integer> frequentItems = new HashMap<String, Integer>();
    String newLine = System.getProperty("line.separator");
    int itemsCount, countItemOccurrence = 0, displayFrequentItemSetNumber = 2, displayTransactionNumber = 1;

    public static void main(String args[]) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int noOfTransactions, minimumSupport;
        List<String> transactions = new ArrayList<String>();
        String newLine = System.getProperty("line.separator");
        System.out.println(newLine + "'APRIORI ALGORITHM'");
        System.out.print("Enter the Minimum Support(enter count of minimum support in number not in %) = ");
        minimumSupport = Integer.parseInt(br.readLine());
        Scanner sc = new Scanner(new File("./demo/Experiment 6/data_file.txt"));
        while (sc.hasNextLine()) {
            String str = sc.nextLine();
            transactions.add(str);
        }
        noOfTransactions = transactions.size();
        expt_6 a = new expt_6();
        a.display(noOfTransactions, transactions,
                minimumSupport);
    }

    public void display(int noOfTransactions, List<String> transactions, int minimumSupport) {
        for (int i = 0; i < noOfTransactions; i++) {
            String str = transactions.get(i);
            String[] words = str.split(" ");
            int count = words.length;
            for (int j = 0; j < count; j++) {
                if (i == 0) {
                    itemSet.add(words[j]);
                } else {
                    if (!(itemSet.contains(words[j]))) {
                        itemSet.add(words[j]);
                    }
                }
            }
        } // Displaying items as they occur in transactions
        itemsCount = itemSet.size();
        System.out.println(newLine + "No of Items = " + itemsCount);
        System.out.println("No of Transactions =" + noOfTransactions);
        System.out.println("Minimum Support =" + minimumSupport);
        System.out.println("'Items present in the Database'");
        for (String i : itemSet) {
            System.out.println("------> " + i);
        }
        System.out.println(newLine + "TRANSACTION ITEMSET");
        for (String i : transactions) {
            System.out.println("Transaction" + displayTransactionNumber + " = " + i);
            displayTransactionNumber++;
        }
        firstFrequentItemSet(noOfTransactions, transactions, minimumSupport);
    }

    public void firstFrequentItemSet(int noOfTransactions, List<String> transactions, int minimumSupport) {
        System.out.println();
        System.out.println("Frequent Itemset 1");
        for (int items = 0; items < itemSet.size(); items++) {
            countItemOccurrence = 0;
            String itemStr = itemSet.get(items);
            for (int t = 0; t < noOfTransactions; t++) {
                String transactionStr = transactions.get(t);
                if (transactionStr.contains(itemStr)) {
                    countItemOccurrence++;
                }
            } // count each item
            if (countItemOccurrence >= minimumSupport) {
                System.out.println(itemStr + " => support =" + countItemOccurrence);
                finalFrequentItemSet.add(itemStr);
                frequentItems.put(itemStr,
                        countItemOccurrence);
            }
        }
        aprioriStart(noOfTransactions, transactions, minimumSupport);
    }

    public void aprioriStart(int noOfTransactions, List<String> transactions, int minimumSupport) {
        int itemsetNumber = 1;
        for (int i = 0; i < finalFrequentItemSet.size(); i++) {
            String str = finalFrequentItemSet.get(i);
            candidates.add(str);
        }
        do {
            itemsetNumber++;
            generateCombinations(itemsetNumber);
            checkFrequentItems(noOfTransactions, transactions, minimumSupport);
        } while (candidates.size() > 1);
    }

    private void generateCombinations(int itr) {
        Vector<String> candidatesTemp = new Vector<String>();
        String s1, s2;
        StringTokenizer strToken1, strToken2;
        if (itr == 2) {
            for (int i = 0; i < candidates.size(); i++) {
                strToken1 = new StringTokenizer(candidates.get(i));
                s1 = strToken1.nextToken();
                for (int j = i + 1; j < candidates.size(); j++) {
                    strToken2 = new StringTokenizer(candidates.elementAt(j));
                    s2 = strToken2.nextToken();
                    String addString = s1 + " " + s2;
                    candidatesTemp.add(addString);
                }
            }
        } else {
            for (int i = 0; i < candidates.size(); i++) {
                for (int j = i + 1; j < candidates.size(); j++) {
                    s1 = new String();
                    s2 = new String();
                    strToken1 = new StringTokenizer(candidates.get(i));
                    strToken2 = new StringTokenizer(candidates.get(j));
                    for (int s = 0; s < itr - 2; s++) {
                        s1 = s1 + " " + strToken1.nextToken();
                        s2 = s2 + " " + strToken2.nextToken();
                    }
                    if (s2.compareToIgnoreCase(s1) == 0) {
                        String addString = (s1 + " " +
                                strToken1.nextToken() + " " + strToken2.nextToken()).trim();
                        candidatesTemp.add(addString);
                    }
                }
            }
        }
        candidates.clear();
        candidates = new Vector<String>(candidatesTemp);
        candidatesTemp.clear();
        System.out.println();
    }

    public void checkFrequentItems(int noOfTransactions, List<String> transactions, int minimumSupport) {
        List<String> combList = new ArrayList<String>();
        for (int i = 0; i < candidates.size(); i++) {
            String str = candidates.get(i);
            combList.add(str);
        }
        int c = 0;
        // if (combList.size() != 0)
        // System.out.println("Frequent Itemset " + displayFrequentItemSetNumber);
        for (int i = 0; i < combList.size(); i++) {
            String str = combList.get(i);
            String[] words = str.split(" ");
            int count = words.length;
            int flag = 0, itemSetOccurence = 0;
            for (int t = 0; t < noOfTransactions; t++) {
                String transac = transactions.get(t);
                for (int j = 0; j < count; j++) {
                    String wordStr = words[j];
                    if (transac.contains(wordStr)) {
                        flag++;
                    }
                }
                if (flag == count) {
                    itemSetOccurence++;
                }
                flag = 0;
            }
            if (itemSetOccurence >= minimumSupport) {
                if (c == 0)
                    System.out.println("Frequent Itemset " + displayFrequentItemSetNumber);
                c++;
                System.out.println(str + " => support =" + itemSetOccurence);
                frequentItems.put(str, itemSetOccurence);
                finalFrequentItemSet.add(str);
            }
            itemSetOccurence = 0;
        }
        displayFrequentItemSetNumber++;
    }

}
