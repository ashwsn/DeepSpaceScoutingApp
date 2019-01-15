package frc.team449.scoutingappframe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.io.IOException;

import frc.team449.scoutingappframe.Activities.BaseActivity;
import frc.team449.scoutingappframe.Activities.MainActivity;
import frc.team449.scoutingappframe.Activities.Submitted;
import frc.team449.scoutingappframe.Helpers.BluetoothHelper;

public class Submit extends BaseActivity {

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
//                File root = Environment.getExternalStorageDirectory();
//                File dir = new File (root.getAbsolutePath() + "/download");
//                dir.mkdirs();
//                // File with data yet to be pushed from kindle
//                BufferedWriter toPushWriter = new BufferedWriter(
//                        new FileWriter(root.getAbsolutePath() + "/download/" + "dataToPush.csv", true));
//                // File with all data previously pushed from kindle
////                BufferedWriter pushedWriter = new BufferedWriter(
////                        new FileWriter(root.getAbsolutePath() + "/download/" + "dataPushed.csv", true));
//                String csvString = MainActivity.db.toString();
//                toPushWriter.append(csvString + "\n");
//                toPushWriter.close();

                BluetoothHelper.getInstance().write(MainActivity.db.toString());

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
