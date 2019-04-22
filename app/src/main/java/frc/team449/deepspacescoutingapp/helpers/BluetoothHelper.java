package frc.team449.deepspacescoutingapp.helpers;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.ParcelUuid;
import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BluetoothHelper {
    private static BluetoothHelper bluetoothHelper = new BluetoothHelper();

    public static BluetoothHelper getInstance(){
        return bluetoothHelper;
    }

    private BluetoothAdapter blueAdapter = BluetoothAdapter.getDefaultAdapter();
    private BluetoothSocket socket;
    private OutputStream outputStream;

    private String defaultMaster;

    public boolean isConnected() {
        return write(" ");
    }

    public List<String> getPairedDevices(){
        List<String> paired = new ArrayList<>();
        if (blueAdapter != null) {
            if (blueAdapter.isEnabled()) {
                //These are the devices that the tablet is paired with
                for (BluetoothDevice device : blueAdapter.getBondedDevices()){
                    paired.add(device.getName());
                }
            } else {
                Log.e("BluetoothHelper.initCon", "Bluetooth is disabled.");
            }
        } else Log.e("BluetoothHelper.initCon","blueAdapter is null");
        return paired;
    }

    public void initializeConnection() throws IOException {
        initializeConnection(defaultMaster);
    }

    public void initializeConnection(String targetMasterName) throws IOException {
        defaultMaster = targetMasterName;
        if (blueAdapter != null) {
            if (blueAdapter.isEnabled()) {
                //These are the devices that the tablet is paired with
                Set<BluetoothDevice> bondedDevices = blueAdapter.getBondedDevices();
                if(bondedDevices.size() > 0) {
                    for (BluetoothDevice device : bondedDevices) {
                        if (device.getName().equals(targetMasterName)) {
                            Log.i("BluetoothHelper.initCon", "Attempting to connect to "+device.getName());
                            ParcelUuid[] uuids = device.getUuids();
                            //Can't use this because it gives port of -1
                            //https://stackoverflow.com/questions/18657427/ioexception-read-failed-socket-might-closed-bluetooth-on-android-4-3
                            //BluetoothSocket socket = device.createRfcommSocketToServiceRecord(uuids[0].getUuid());
                            try {
                                //see second answer to stack overflow link above
                                socket =(BluetoothSocket) device.getClass().getMethod("createRfcommSocket", new Class[] {int.class}).invoke(device,1);
                            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                                e.printStackTrace();
                                Log.e("BluetoothHelper.initCon","Error connecting to "+device.getName());
                            }
                            socket.connect();
                            outputStream = socket.getOutputStream();
                            Log.i("BluetoothHelper.initCon", "Connected to "+device.getName());
                        }
                    }
                } else {
                    Log.e("BluetoothHelper.initCon", "No appropriate paired devices.");
                }
            } else {
                Log.e("BluetoothHelper.initCon", "Bluetooth is disabled.");
                /*Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBluetooth, 0);
            Log.i("myStuff", "Bluetooth Enabled");*/
            }
        } else Log.e("BluetoothHelper.initCon","blueAdapter is null");
    }

    public boolean write(String str) {
        if (socket != null && socket.isConnected()) {
            try {
                // Only submit one line at a time so it doesn't exceed the size and get cut off
                for (String s : str.split("\n")) {
                    outputStream.write(s.getBytes());
                    outputStream.flush();
                }
                return true;
            } catch (NullPointerException e) {
                e.printStackTrace();
                Log.e("BluetoothHelper.write", "outputStream is null");
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("BluetoothHelper.write", "IOExecption, socket is probably closed on the other end");
            }
        } else {
            Log.e("BluetoothHelper.write","Not connected to a device.");
        }
        return false;
    }
}
