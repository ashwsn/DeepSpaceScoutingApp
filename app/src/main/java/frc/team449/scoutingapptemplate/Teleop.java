package frc.team449.scoutingapptemplate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Teleop extends Activity {

    // Data trackers
    // Text fields that display current value
    // Input fields

    // Displays teleop page on activity call
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teleop_page);
        // Create and set data trackers to their Database values
    }

    // Calls activity to go to auto page
    public void toAuto(View v) {
        // Save values to PowerUpDatabase
        // Switches pages
        Intent toAuto = new Intent(this, Auto.class);
        startActivity(toAuto);
    }

    // Calls activity to go to endgame page
    public void toEndgame(View v) {
        // Save values to PowerUpDatabase
        // Switches pages
        Intent toEndgame = new Intent(this, Endgame.class);
        startActivity(toEndgame);
    }
}
