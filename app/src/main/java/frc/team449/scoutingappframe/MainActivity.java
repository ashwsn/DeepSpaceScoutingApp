package frc.team449.scoutingappframe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;

public class MainActivity extends Activity {

    //add app functionality to change this in app
    private static final String MASTERNAME = "essuomelpmap";

    public static Database db;

    // Calls activity to go to prematch pages
    // and creates a PowerUpDatabase object
    // This activity is automatically called when the app is opened
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new Database();

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