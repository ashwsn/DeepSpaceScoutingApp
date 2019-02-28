package frc.team449.deepspacescoutingapp.helpers;

import android.content.Context;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class FileHelper {

    public static void addToFile(String filename, String match, Context ctxt) throws IOException {
        File file = new File(ctxt.getFilesDir(), filename);
        FileWriter fw = new FileWriter(file, true);
        BufferedWriter bufferedWriter = new BufferedWriter(fw);
        bufferedWriter.write(match + "\n");
        bufferedWriter.close();
        fw.close();
    }

    public static String getFromFile(String filename, Context ctxt) throws FileNotFoundException {
        File file = new File(ctxt.getFilesDir(), filename);
        try {
            return new Scanner(file).useDelimiter("\\Z").next();
        } catch (NoSuchElementException e) {
            Log.i("FileHelper.getFromFile","File is empty");
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
