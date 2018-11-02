package frc.team449.scoutingapptemplate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.Locale;

public class Auto extends Activity {

    // Text fields that display current value
    private TextView autoBallVal;
    // Input fields
    private CheckBox noAuto;
    private CheckBox movedForward;

    // Displays auto page on activity call
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.autonomous_page);
        // Create and set data trackers to values from Database
        TextView matchTitle = findViewById(R.id.matchTitle);
        TextView teamTitle = findViewById(R.id.teamTitle);
        if (MainActivity.db.matchNumber != 0) {
            matchTitle.setText("Match " + Prematch.getMatchNum());
        }
        if (MainActivity.db.teamNumber != 0) {
            teamTitle.setText("Team " + Prematch.getTeamNum());
        }
        noAuto = findViewById(R.id.noAuto);
        noAuto.setChecked(MainActivity.db.noAuto);
        movedForward = findViewById(R.id.movedForward);
        movedForward.setChecked(MainActivity.db.movedForward);
        autoBallVal = findViewById(R.id.autoBall);
        autoBallVal.setText(String.format(Locale.US, "%d", MainActivity.db.autoBalls));
    }


    public void plusAutoCrates(View v) {
        MainActivity.db.autoBalls++;
        autoBallVal.setText(String.format(Locale.US, "%d", MainActivity.db.autoBalls));
    }

    public void minusAutoCrates(View v) {
        if (MainActivity.db.autoBalls > 0) {
            MainActivity.db.autoBalls--;
            autoBallVal.setText(String.format(Locale.US, "%d", MainActivity.db.autoBalls));
        }
    }

    // Calls activity to go to teleop page
    public void toTeleop(View v) {
        // Save values into Database
        MainActivity.db.noAuto = noAuto.isChecked();
        MainActivity.db.movedForward = movedForward.isChecked();
        // Switch pages
        Intent toTeleop = new Intent(this, Teleop.class);
        startActivity(toTeleop);
    }

    // Calls activity to go to prematch page
    public void toPrematch(View v) {
        // Save values to Database
        MainActivity.db.noAuto = noAuto.isChecked();
        MainActivity.db.movedForward = movedForward.isChecked();
        // Switches pages
        Intent toPrematch = new Intent(this, Prematch.class);
        startActivity(toPrematch);
    }
}
