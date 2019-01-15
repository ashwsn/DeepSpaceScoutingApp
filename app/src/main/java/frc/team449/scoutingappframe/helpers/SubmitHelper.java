package frc.team449.scoutingappframe.helpers;

import android.content.Context;
import android.content.Intent;

import java.io.IOException;

import frc.team449.scoutingappframe.activities.FixErrors;
import frc.team449.scoutingappframe.activities.Submitted;
import frc.team449.scoutingappframe.model.Match;

public class SubmitHelper {
    public static void submit(Context ctxt){
        if (!Match.getInstance().checkData().equals("")) {
            Intent viewErrors = new Intent(ctxt, FixErrors.class);
            ctxt.startActivity(viewErrors);
        }
        else {
            try {
                // Writes data to files
                BluetoothHelper.getInstance().write(Match.getInstance().toString(ctxt));

                //TODO: Store backup locally

                // Reset the match data
                Match.getInstance().reset();

                // Go to confirmation page
                Intent toSubmitted = new Intent(ctxt, Submitted.class);
                ctxt.startActivity(toSubmitted);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
