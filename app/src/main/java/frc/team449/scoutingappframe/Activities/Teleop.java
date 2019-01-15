package frc.team449.scoutingappframe.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.TextView;

import frc.team449.scoutingappframe.Prematch;
import frc.team449.scoutingappframe.R;

public class Teleop extends BaseActivity {

    // Input fields
    private CheckBox achievedNothing;
    private RadioGroup dead;


    // Displays teleop page on activity call
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teleop_page);
        // Create and set data trackers to their Database values
        TextView matchTitle = findViewById(R.id.matchTitle);
        TextView teamTitle = findViewById(R.id.teamTitle);
        if (MainActivity.db.matchNumber != 0) {
            matchTitle.setText("Match " + Prematch.getMatchNum());
        }
        if (MainActivity.db.teamNumber != 0) {
            teamTitle.setText("Team " + Prematch.getTeamNum());
        }
        achievedNothing = findViewById(R.id.achievedNothing);
        achievedNothing.setChecked(MainActivity.db.achievedNothing);
        dead = findViewById(R.id.dead);
        switch (MainActivity.db.dead) {
            case 0:
                dead.clearCheck();
                break;
            case 1:
                dead.check(R.id.deadNone);
                break;
            case 2:
                dead.check(R.id.deadPart);
                break;
            case 3:
                dead.check(R.id.deadAll);
                break;
        }
    }

    // Calls activity to go to auto page
    public void toAuto(View v) {
        // Save values to Database
        MainActivity.db.achievedNothing = achievedNothing.isChecked();
        switch (dead.getCheckedRadioButtonId()) {
            case R.id.deadNone:
                MainActivity.db.dead = 1;
                break;
            case R.id.deadPart:
                MainActivity.db.dead = 2;
                break;
            case R.id.deadAll:
                MainActivity.db.dead = 3;
                break;
            default:
                MainActivity.db.dead = 0;
                break;
        }
        // Switches pages
        Intent toAuto = new Intent(this, Auto.class);
        startActivity(toAuto);
    }

    // Calls activity to go to endgame page
    public void toEndgame(View v) {
        // Save values to Database
        MainActivity.db.achievedNothing = achievedNothing.isChecked();
        switch (dead.getCheckedRadioButtonId()) {
            case R.id.deadNone:
                MainActivity.db.dead = 1;
                break;
            case R.id.deadPart:
                MainActivity.db.dead = 2;
                break;
            case R.id.deadAll:
                MainActivity.db.dead = 3;
                break;
            default:
                MainActivity.db.dead = 0;
                break;
        }
        // Switches pages
        Intent toEndgame = new Intent(this, Endgame.class);
        startActivity(toEndgame);
    }
}
