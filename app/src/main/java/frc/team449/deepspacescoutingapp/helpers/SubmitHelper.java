package frc.team449.deepspacescoutingapp.helpers;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.IOException;

import frc.team449.deepspacescoutingapp.R;
import frc.team449.deepspacescoutingapp.activities.Submitted;
import frc.team449.deepspacescoutingapp.activities.base_activites.BaseActivity;
import frc.team449.deepspacescoutingapp.model.ErrorInfo;
import frc.team449.deepspacescoutingapp.model.Match;

public class SubmitHelper {
    public static void submit(final BaseActivity activity) {
        final ErrorInfo errors = Match.getInstance().checkData();
        if (!errors.getErrorString().equals("")) {
            PopupHelper.prompt(activity.getString(R.string.data_errors_title), errors.getErrorString(), "Fix It", new Runnable() {
                @Override
                public void run() {
                    activity.startActivity(new Intent(activity, errors.getPageToGoTo()));
                }
            }, "", new Runnable() {
                @Override
                public void run() {}
            }, activity);
        } else {
            final ErrorInfo softErrors = Match.getInstance().softCheck();
            if (!softErrors.getErrorString().equals("")) {
                PopupHelper.prompt("Is this data correct?", softErrors.getErrorString(), "Fix It", new Runnable() {
                    @Override
                    public void run() {
                        activity.startActivity(new Intent(activity, softErrors.getPageToGoTo()));
                    }
                }, "Submit", new Runnable() {
                    @Override
                    public void run() {
                        finalSubmit(activity);
                    }
                }, activity);
            } else {
                finalSubmit(activity);
            }
        }
    }

    private static void finalSubmit(final BaseActivity activity) {
        activity.showProgressBar();
        if (BluetoothHelper.getInstance().isConnected()) {
            submitData(activity);
        } else {
            final Thread t = new Thread(new Runnable() {
            @Override
                public void run() {
                    try {
                        BluetoothHelper.getInstance().initializeConnection();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (BluetoothHelper.getInstance().isConnected()) {
                        submitData(activity);
                    } else {
                        PopupHelper.prompt(activity.getString(R.string.not_connected_title), activity.getString(R.string.not_connected_prompt),
                                activity.getString(R.string.bluetooth_popup_connect_button), new Runnable() {
                                    @Override
                                    public void run() {
                                        PopupHelper.bluetoothPopup(activity);
                                    }
                                }, activity.getString(R.string.bluetooth_warning_ignore_button), new Runnable() {
                                    @Override
                                    public void run() {
                                        submitData(activity);
                                    }
                                }, activity);
                    }
                    activity.hideProgressBar();
                }
            });
            t.start();
        }
    }

    private static void submitData(final BaseActivity activity) {
        String data = Match.getInstance().toString(activity);

        if (Match.getInstance().isReplacement()) {
            data = "REPLACE" + Match.getOldMatchString() + "\n" + data;
        }

        // Submit over Bluetooth
        boolean written = BluetoothHelper.getInstance().write(data);


        // Make a local backup
        try {
            FileHelper.addToFile("alldata.csv",data,activity);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("SubmitHelper.submitData", "IO Error while making local backup");
        }
        if (!written) {
            try {
                FileHelper.addToFile("newdata.csv",data,activity);
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("SubmitHelper.submitData", "IO Error while making local backup");
            }
        }

        // Reset the match data
        Match.getInstance().reset();
        Match.getInstance().incMatch(activity);

        // Go to confirmation page
        Intent toSubmitted = new Intent(activity, Submitted.class);
        activity.startActivity(toSubmitted);

    }
}
