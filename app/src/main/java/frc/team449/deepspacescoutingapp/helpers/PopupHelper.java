package frc.team449.deepspacescoutingapp.helpers;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import java.io.FileNotFoundException;
import java.io.IOException;

import frc.team449.deepspacescoutingapp.R;
import frc.team449.deepspacescoutingapp.fragments.BluetoothSetupFragment;
import frc.team449.deepspacescoutingapp.fragments.PopupFragment;

public class PopupHelper {

    public static void info(String title, String info, AppCompatActivity ctxt) {
        FragmentManager fm = ctxt.getSupportFragmentManager();
        PopupFragment fragment = PopupFragment.newInstance(title,info,ctxt.getString(R.string.info_popup_button));
        fragment.show(fm, "dialog");
    }

    public static void prompt(String title, String info, String button1Text, Runnable action1,
                              String button2Text, Runnable action2,AppCompatActivity ctxt) {
        FragmentManager fm = ctxt.getSupportFragmentManager();
        PopupFragment fragment = PopupFragment.newInstance(title,info,button1Text,button2Text,action1,action2);
        fragment.show(fm, "dialog");
    }

    public static void bluetoothPopup(AppCompatActivity ctxt) {
        if (!BluetoothHelper.getInstance().isConnected()) {
            FragmentManager fm = ctxt.getSupportFragmentManager();
            DialogFragment fragment = new BluetoothSetupFragment();
            fragment.show(fm, "dialog");
        } else {
            info("Bluetooth Connected","Bluetooth is already connected.\nIf that is false, or you are having issues, try restarting the app (and maybe the server)", ctxt);
        }
    }

    public static void uploader(final AppCompatActivity ctxt) {
        prompt("Data Upload", "Do you want to upload all data on this device, or only the new data that has not been uploaded? (You probably want the latter)\n(Uploading the new data will also clear it)\nTo cancel, press anywhere outside of this dialogue.",
                "All Data", new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (BluetoothHelper.getInstance().isConnected())
                                BluetoothHelper.getInstance().write(FileHelper.getFromFile("alldata.csv", ctxt));
                            else
                                bluetoothPopup(ctxt);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }, "New Data", new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (BluetoothHelper.getInstance().isConnected()) {
                                BluetoothHelper.getInstance().write(FileHelper.getFromFile("newdata.csv", ctxt));
                                FileHelper.clearFile("newdata.csv", ctxt);
                            } else {
                                bluetoothPopup(ctxt);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }, ctxt);

    }

    public static void clearData(final AppCompatActivity ctxt) {
        prompt("Clear Data", "Do you want to clear all locally stored data, or just data which has not been uploaded?\nTo cancel, press anywhere outside of this dialogue.",
                "All Data", new Runnable() {
                    @Override
                    public void run() {
                        try {
                            FileHelper.clearFile("alldata.csv", ctxt);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }, "New Data", new Runnable() {
                    @Override
                    public void run() {
                        try {
                            FileHelper.clearFile("newdata.csv", ctxt);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }, ctxt);
    }
}
