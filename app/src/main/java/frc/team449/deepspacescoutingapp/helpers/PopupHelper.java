package frc.team449.deepspacescoutingapp.helpers;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

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
//        if (!BluetoothHelper.getInstance().isConnected()) {
        FragmentManager fm = ctxt.getSupportFragmentManager();
        DialogFragment fragment = new BluetoothSetupFragment();
        fragment.show(fm, "dialog");
//        }
    }
}
