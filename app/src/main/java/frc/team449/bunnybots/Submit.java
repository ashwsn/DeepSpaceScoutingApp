package frc.team449.bunnybots;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Submit extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!MainActivity.db.checkData().equals("")) {
            Intent viewErrors = new Intent(this, FixErrors.class);
            startActivity(viewErrors);
        }
        else {
            try {
                // Writes data to files
                File root = Environment.getExternalStorageDirectory();
                File dir = new File (root.getAbsolutePath() + "/download");
                dir.mkdirs();
                // File with data yet to be pushed from kindle
                BufferedWriter toPushWriter = new BufferedWriter(
                        new FileWriter(root.getAbsolutePath() + "/download/" + "dataToPush.csv", true));
                // File with all data previously pushed from kindle
//                BufferedWriter pushedWriter = new BufferedWriter(
//                        new FileWriter(root.getAbsolutePath() + "/download/" + "dataPushed.csv", true));
                String csvString = MainActivity.db.toString();
                toPushWriter.append(csvString + "\n");
                toPushWriter.close();

                // Go to confirmation page
                Intent toSubmitted = new Intent(this, Submitted.class);
                startActivity(toSubmitted);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
