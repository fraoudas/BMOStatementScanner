package core;

import helpers.DatabaseHelper;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import models.Transaction;

import java.util.*;
import java.io.File;
import java.io.IOException;

public class StatementParser  {

    static String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    static List<String> listMonths = Arrays.asList(months);
    static DatabaseHelper db;

    public static HashMap<String, HashMap<String, Float>> transactionsRecorded;
    public static ArrayList<Transaction> transactionsNotRecorded;

    public StatementParser() throws IOException {

    }

    public static ArrayList<Transaction> parse(File f) {

        ArrayList<Transaction> transactionArrayList = new ArrayList<>();
        boolean readingEnabled = false;

        //Variables used for creating the transaction object
        char type;
        String desc = "";
        float amount;

        try {
            PDDocument doc = PDDocument.load(f);
            String[] rawText = new PDFTextStripper().getText(doc).split("\\r?\\n");
            doc.close();

            for (int i = 0; i < rawText.length; i++) {

                Transaction t;
                desc = "";

                if (rawText[i].equals("DATE DESCRIPTION REFERENCE NO. AMOUNT ($)")) {
                    readingEnabled = true;
                } else if (rawText[i].equals("Important information about your BMO Mastercard account")) {
                    readingEnabled = false;
                }

                if (listMonths.contains(rawText[i].substring(0, 3)) & rawText[i].length() > 29 & readingEnabled) {
                    String[] splitLine = rawText[i].split("\\s+");

                    //Getting date
                    String month = convertMonth(splitLine[0].substring(0,3));
                    String day = convertDay(splitLine[1]);
                    String date = "2020-"+month+"-"+day;

                    //Getting amount
                    if (splitLine[splitLine.length - 1].equals("CR")) {
                        type = 'C';
                        amount = Float.parseFloat(splitLine[splitLine.length - 2].replace(",", ""));

                        //Getting description
                        for (int j = 4; j < splitLine.length - 2; j++) {
                            desc += splitLine[j]+" ";
                        }
                    } else {
                        type = 'D';
                        amount = Float.parseFloat(splitLine[splitLine.length - 1].replace(",", ""));

                        //Getting description
                        for (int j = 4; j < splitLine.length - 1; j++) {
                            desc += splitLine[j]+" ";
                        }
                    }

                    t = new Transaction(type, desc, amount, date);

                    transactionArrayList.add(t);
                }
            }

        } catch (IOException e) {
            System.out.println("File not found.");
        }

        return transactionArrayList;
    }

    public static String convertMonth(String s) {
        String num = "";
        if (s.equals("Jan")) {
            num = "01";
        } else if (s.equals("Feb")) {
            num = "02";
        } else if (s.equals("Mar")) {
            num = "03";
        } else if (s.equals("Apr")) {
            num = "04";
        } else if (s.equals("May")) {
            num = "05";
        } else if (s.equals("Jun")) {
            num = "06";
        } else if (s.equals("Jul")) {
            num = "07";
        } else if (s.equals("Aug")) {
            num = "08";
        } else if (s.equals("Sep")) {
            num = "09";
        } else if (s.equals("Oct")) {
            num = "10";
        } else if (s.equals("Nov")) {
            num = "11";
        } else if (s.equals("Dec")) {
            num = "12";
        }
        return num;
    }

    public static String convertDay(String s) {
        String num = String.valueOf(s);
        if (s.equals("1")) {
            num = "01";
        } else if (s.equals("2")) {
            num = "02";
        } else if (s.equals("3")) {
            num = "03";
        } else if (s.equals("4")) {
            num = "04";
        } else if (s.equals("5")) {
            num = "05";
        } else if (s.equals("6")) {
            num = "06";
        } else if (s.equals("7")) {
            num = "07";
        } else if (s.equals("8")) {
            num = "08";
        } else if (s.equals("9")) {
            num = "09";
        }
        return num;
    }

    public static void read(ArrayList<Transaction> allTransactions) throws IOException {
        transactionsRecorded = new HashMap<>();
        db = new DatabaseHelper();
        transactionsNotRecorded = new ArrayList<>();

        HashMap<String, ArrayList<String>> categories = db.getCategoryHashMap();
        String category = "";

        boolean keywordFound = false;

        for (Transaction t : allTransactions) {
            for (String s : categories.keySet()) {
                for (int i = 0; i < categories.get(s).size(); i++) {
                    if (t.getDesc().contains(categories.get(s).get(i))) {
                        keywordFound = true;
                        category = String.valueOf(s);
                        if (!transactionsRecorded.keySet().contains(category)) {
                            transactionsRecorded.put(category, createMonthlyHashMap());
                        }

                        Float newSum;
                        if (t.getType() == 'D') {
                            newSum = transactionsRecorded.get(category).get(t.getMonth()) + Float.valueOf(t.getAmount());
                        } else {
                            newSum = transactionsRecorded.get(category).get(t.getMonth()) - Float.valueOf(t.getAmount());
                        }

                        transactionsRecorded.get(category).put(t.getMonth(), newSum);
                        break;
                    }
                }
                if (keywordFound) {
                    break;
                }
            }

            if (keywordFound) {
                keywordFound = false;
            } else {
                transactionsNotRecorded.add(t);
            }

        }

    }

    public static HashMap<String, Float> createMonthlyHashMap() {
        HashMap<String, Float> res = new HashMap<>();
        res.put("01", new Float(0));
        res.put("02", new Float(0));
        res.put("03", new Float(0));
        res.put("04", new Float(0));
        res.put("05", new Float(0));
        res.put("06", new Float(0));
        res.put("07", new Float(0));
        res.put("08", new Float(0));
        res.put("09", new Float(0));
        res.put("10", new Float(0));
        res.put("11", new Float(0));
        res.put("12", new Float(0));

        return res;

    }

    public static ArrayList<Transaction> getNotRecorded() {
        return transactionsNotRecorded;
    }


}
