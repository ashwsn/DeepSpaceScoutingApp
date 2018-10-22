package frc.team449.scoutingapptemplate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Auto extends Activity {

    // Data trackers
    // Text fields that display current value
    // Input fields

    // Displays auto page on activity call
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.autonomous_page);
        // Create and set data trackers to values from Database
    }

    // Calls activity to go to teleop page
    public void toTeleop(View v) {
        // Save values into Database
        // Switch pages
        Intent toTeleop = new Intent(this, Teleop.class);
        startActivity(toTeleop);
    }

    // Calls activity to go to prematch page
    public void toPrematch(View v) {
        // Save values to Database
        // Switches pages
        Intent toPrematch = new Intent(this, Prematch.class);
        startActivity(toPrematch);
    }
}
