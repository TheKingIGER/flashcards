package com.TheKing.flashcard;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Lars on 09.02.2018.
 */

public class ListItem {

    private static final int LEFT_IDX = 0; // Erste Spalte der CSV
    private static final int RIGHT_IDX = 1; // Zweite Spalte der CSV

    private String filePath; // Variable zum File Path

    public String getFilePath() {
        return filePath;
    }

    // Klasse für die Item Pairs
    public static class ItemPair {
        public String left;
        public String right;

        public ItemPair(String left, String right) {
            this.left = left;
            this.right = right;
        }
    }

    private ItemPair header; // Erste Zeile der CSV
    private List<ItemPair> itemPairs = new ArrayList<>(); // Liste der Item Pairs

    public ListItem(String filePath) {
        this.filePath = filePath;
        readFile();
    }

    public List<ItemPair> getItemPairs() {
        return itemPairs;
    }

    // Einlesen der CSV
    private static List<List<String>> getFileContent(File file) throws IOException {

        List<List<String>> fileContentList = new ArrayList<>(); // Neue Liste

        if(file.exists()) {

            // Einlesen der CSV
            InputStream inputStream = new FileInputStream(file);
            Reader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader br = new BufferedReader(inputStreamReader);

            String line;

            // Lesen jeder Zeile in der CSV
            while ((line = br.readLine()) != null) {
                if(line.contains(";")) { // Überprüfung ob in der Zeile ein ; vorhanden ist
                    ArrayList<String> values = new ArrayList<>(Arrays.asList(line.split(";")));
                    fileContentList.add(values); // Hinzufügen der Zeile zur Liste
                }
            }

            br.close();
        }

        return fileContentList;  // Return Liste mit Inhalt der CSV
    }

    // Verarbeitung der CSV
    private void readFile()
    {
        File file = new File(App.getListRootDir(), filePath);
        List<String> list = new ArrayList<>();
        List<List<String>> lines = null;

        try {
            lines = getFileContent(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // check that that lines is not null and that the list contains something
        if(lines != null && lines.size() > 0)
        {
            // Store header separate
            List<String> headerLine = lines.get(0);
            header = new ItemPair(headerLine.get(LEFT_IDX), headerLine.get(RIGHT_IDX));
            lines.remove(0);
            for(List<String> line: lines) {
                itemPairs.add(new ItemPair(line.get(LEFT_IDX).trim(), line.get(RIGHT_IDX).trim()));
            }
        }
    }

    public int getNumberOfItems() {
        return itemPairs.size();
    }

    // Linker Header
    public String getLeftHeader() {
        return header.left;
    }

    // Rechter Header
    public String getRightHeader() {
        return header.right;
    }
}
