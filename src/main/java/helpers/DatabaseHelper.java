package helpers;

import jdk.internal.util.xml.impl.Input;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class DatabaseHelper {

    public HashMap<String, ArrayList<String>> categories;

    BufferedReader csvReader;
    FileWriter csvWriter;

    static String DB_PATH;

    public DatabaseHelper() throws IOException {
        String dir = System.getProperty("user.home");
        DB_PATH = dir+"/Documents/BMOStatementScanner/categories.csv";

        createDatabaseDir();
        categories = new HashMap<>();
        updateHashMap();
    }

    public boolean addKeyword(String category, String keyword) throws IOException {


        csvWriter = new FileWriter(DB_PATH);

        boolean res = true;

        for (String k : categories.keySet()) {
            if (categories.get(k).contains(keyword)) {
                res = false;
            }
        }

        if (res) {
            csvWriter.append(category);
            csvWriter.append(",");
            csvWriter.append(keyword);
            csvWriter.append("\n");
        }

        csvWriter.close();

        updateHashMap();

        return res;
    }

    public void updateHashMap() throws IOException {

        csvReader = new BufferedReader(new FileReader(DB_PATH));
        String row;
        while ((row = csvReader.readLine()) != null) {
            ;
            String[] data = row.split(",");

            if (!categories.containsKey(data[0])) {
                categories.put(data[0], new ArrayList<>());
            }

            categories.get(data[0]).add(data[1]);
        }

        csvReader.close();
    }

    public String[] getCategories() {

        String[] categoryArray = new String[categories.size()];
        int i = 0;
        for (String s : categories.keySet()) {
            categoryArray[i] = s;
            i++;
        }

        return categoryArray;
    }

    public String[] getKeywords(String category) {
        String[] keywordArray = new String[categories.get(category).size()];
        int i = 0;
        for (String s : categories.get(category)) {
            keywordArray[i] = s;
            i++;
        }
        return keywordArray;
    }

    public HashMap<String, ArrayList<String>> getCategoryHashMap() {
        return categories;
    }

    public void createDatabaseDir() throws IOException {
        String dir = System.getProperty("user.home");
        new File(dir+"/Documents/BMOStatementScanner").mkdir();
        new File(dir+"/Documents/BMOStatementScanner/categories.csv").createNewFile();
    }

}


