package frc.team449.scoutingappframe;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;

import java.util.Set;

public class MainActivity extends Activity {

    public static Match match;
    public static BluetoothAdapter adapter;
    public static Set<BluetoothDevice> pairedDevices;
    public static boolean bluetooth;

    // Calls activity to go to prematch pages
    // and creates a PowerUpDatabase object
    // This activity is automatically called when the app is opened
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        match = new Match();
        adapter = BluetoothAdapter.getDefaultAdapter();
        // Enables bluetooth and resets discovery
//        if (adapter == null) {
//            bluetooth = false;
//            Log.i("onCreate", "no bluetooth");
//        }
//        else {
//            bluetooth = true;
//            if (!adapter.isEnabled()) {
//                adapter.enable();
//            }
//            if (adapter.isDiscovering()) {
//                adapter.cancelDiscovery();
//            }
//        }
        Intent initPrematch = new Intent(this, Prematch.class);
        startActivity(initPrematch);
    }
}