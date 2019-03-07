package frc.team449.deepspacescoutingapp.helpers;

import android.content.Context;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileHelper {

    public static void addToFile(String filename, String match, Context ctxt) throws IOException {
        File file = new File(ctxt.getFilesDir(), filename);
        FileWriter fw = new FileWriter(file, true);
        BufferedWriter bufferedWriter = new BufferedWriter(fw);
        bufferedWriter.write(match + "\n");
        bufferedWriter.close();
        fw.close();
    }

    public static String getFromFile(String filename, Context ctxt) {
        File file = new File(ctxt.getFilesDir(), filename);
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;
            StringBuilder all = new StringBuilder();
            while ((st = br.readLine()) != null)
                all.append(st).append("\n");
            return all.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void clearFile(String filename, Context ctxt) throws IOException {
        File file = new File(ctxt.getFilesDir(), filename);
        FileWriter fw = new FileWriter(file, false);
        BufferedWriter bufferedWriter = new BufferedWriter(fw);
        bufferedWriter.write("");
        bufferedWriter.close();
        fw.close();
    }
}
