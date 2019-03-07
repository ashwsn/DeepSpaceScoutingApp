package frc.team449.deepspacescoutingapp.helpers;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.IOException;

import frc.team449.deepspacescoutingapp.R;
import frc.team449.deepspacescoutingapp.activities.Submitted;
import frc.team449.deepspacescoutingapp.model.Match;

public class SubmitHelper {
    public static void submit(final Context ctxt) {
        String errors = Match.getInstance().checkData();
        if (!errors.equals("")) {
            PopupHelper.info(ctxt.getString(R.string.data_errors_title), errors, (AppCompatActivity) ctxt);
        } else {
            String softErrors = Match.getInstance().softCheck();
            if (!softErrors.equals("")) {
                PopupHelper.prompt("Is this data correct?", softErrors, "Fix It", new Runnable() {
                    @Override
                    public void run() {
                    }
                }, "Submit", new Runnable() {
                    @Override
                    public void run() {
                        finalSubmit(ctxt);
                    }
                }, (AppCompatActivity) ctxt);
            } else {
                finalSubmit(ctxt);
            }
        }
    }

    private static void finalSubmit(final Context ctxt) {
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

    private static void submitData(final Context ctxt) {

        String data = Match.getInstance().toString(ctxt);

        if (Match.getInstance().isReplacement()) {
            data = "REPLACE" + Match.getOldMatchString() + "\n" + data;
        }

        // Submit over Bluetooth
        boolean written = BluetoothHelper.getInstance().write(data);


        // Make a local backup
        try {
            FileHelper.addToFile("alldata.csv",data,ctxt);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("SubmitHelper.submitData", "IO Error while making local backup");
        }
        if (!written) {
            try {
                FileHelper.addToFile("newdata.csv",data,ctxt);
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("SubmitHelper.submitData", "IO Error while making local backup");
            }
        }

        // Reset the match data
        Match.getInstance().reset();
        Match.getInstance().incMatch(ctxt);

        // Go to confirmation page
        Intent toSubmitted = new Intent(ctxt, Submitted.class);
        ctxt.startActivity(toSubmitted);

    }

}
