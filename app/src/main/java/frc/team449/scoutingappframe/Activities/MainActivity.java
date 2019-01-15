package frc.team449.scoutingappframe.Activities;

import android.content.Intent;
import android.os.Bundle;

import frc.team449.scoutingappframe.Model.Match;
import frc.team449.scoutingappframe.Prematch;

public class MainActivity extends BaseActivity {

    public static Match match;

    // Calls activity to go to prematch pages
    // and creates a PowerUpDatabase object
    // This activity is automatically called when the app is opened
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        match = new Match();

//        if (!BluetoothHelper.getInstance().isConnected()) {
//            Log.i("MainActivity.onCreate", "Attempting to initialize bluetooth connection.");
//            try {
//                boolean connected = BluetoothHelper.getInstance().initializeConnection(MASTERNAME);
//                if (connected) Log.i("MainActivity.onCreate", "Initialized bluetooth connection.");
//                else Log.i("MainActivity.onCreate", "Failed to initialize bluetooth connection.");
//            } catch (IOException e) {
//                e.printStackTrace();
//                Log.e("MainActivity.onCreate", "IOException when initializing bluetooth connection.");
//            }
//        }

        Intent initPrematch = new Intent(this, Prematch.class);
        startActivity(initPrematch);
    }
}