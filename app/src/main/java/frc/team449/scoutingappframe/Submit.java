package frc.team449.scoutingappframe;

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
        if (!MainActivity.match.checkData().equals("")) {
            Intent viewErrors = new Intent(this, FixErrors.class);
            startActivity(viewErrors);
        }
        else {
            try {
                // Writes data to files
                BluetoothHelper.getInstance().write(MainActivity.match.toString());

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
