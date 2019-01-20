package frc.team449.scoutingappframe.helpers;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import java.io.IOException;

import frc.team449.scoutingappframe.R;
import frc.team449.scoutingappframe.activities.Submitted;
import frc.team449.scoutingappframe.model.Match;

public class SubmitHelper {
    public static void submit(final Context ctxt){
        String errors = Match.getInstance().checkData();
        if (!errors.equals("")) {
            PopupHelper.info(ctxt.getString(R.string.data_errors_title),errors,(AppCompatActivity) ctxt);
        }
        else {
            if (BluetoothHelper.getInstance().isConnected()) {
                submitData(ctxt);
            } else {
                PopupHelper.prompt(ctxt.getString(R.string.not_connected_title), ctxt.getString(R.string.not_connected_prompt),
                        ctxt.getString(R.string.bluetooth_popup_connect_button), new Runnable() {
                            @Override
                            public void run() {
                                PopupHelper.bluetoothPopup((AppCompatActivity) ctxt);
                            }
                        }, ctxt.getString(R.string.bluetooth_warning_ignore_button), new Runnable() {
                            @Override
                            public void run() {
                                submitData(ctxt);
                            }
                        }, (AppCompatActivity) ctxt);
            }
        }
    }

    private static void submitData(final Context ctxt){

        //TODO: Store backup locally

        if (BluetoothHelper.getInstance().isConnected()) {
            try {
                BluetoothHelper.getInstance().write(Match.getInstance().toString(ctxt));
            } catch (IOException e) {
                e.printStackTrace();
                PopupHelper.info(ctxt.getString(R.string.bluetooth_setup_title),
                        ctxt.getString(R.string.bluetooth_send_failed_prompt),(AppCompatActivity) ctxt);
            }
        }

        // Reset the match data
        Match.getInstance().reset();

        // Go to confirmation page
        Intent toSubmitted = new Intent(ctxt, Submitted.class);
        ctxt.startActivity(toSubmitted);
    }
}
